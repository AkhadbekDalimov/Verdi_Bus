package uz.asbt.digid.digidservice.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.HandlerInterceptor;
import uz.asbt.digid.common.enums.DeviceSerialType;
import uz.asbt.digid.common.enums.DeviceStatus;
import uz.asbt.digid.common.exception.CustomException;
import uz.asbt.digid.common.models.dto.ClientDTO;
import uz.asbt.digid.common.models.dto.DeviceDTO;
import uz.asbt.digid.common.models.entity.Device;
import uz.asbt.digid.common.models.entity.auth.Login;
import uz.asbt.digid.common.models.entity.client.ModelPersonAnswere;
import uz.asbt.digid.common.models.entity.client.ModelServiceInfo;
import uz.asbt.digid.common.models.entity.client.ServiceInfo;
import uz.asbt.digid.common.models.log.ComingLog;
import uz.asbt.digid.common.repository.LoginRepository;
import uz.asbt.digid.common.service.logger.ILogger;
import uz.asbt.digid.common.utils.HttpUtil;
import uz.asbt.digid.common.wrapper.RequestWrapper;
import uz.asbt.digid.digidservice.model.dto.AllDocReadsRequest;
import uz.asbt.digid.digidservice.service.ICrmService;
import uz.asbt.digid.digidservice.util.AuthUtils;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Enumeration;
import java.util.Optional;

@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

  private static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);

  @Value("${spring.application.name}")
  private String serviceName;

  @Autowired
  private ICrmService crmService;
  @Autowired
  private Environment env;
  @Autowired
  private MessageSource messageSource;
  @Autowired
  private LoginRepository loginRepository;
  @Autowired
  @Qualifier("comingLogger")
  private ILogger<ComingLog> iLogger;

  @Override
  public boolean preHandle(final HttpServletRequest request,
                           final HttpServletResponse response,
                           final Object handler) {
    try {
      log.info("Begin Auth Interceptor");
      if (!request.getDispatcherType().name().equals("REQUEST")) {
        log.info("true");
        return true;
      }
      if (!request.getMethod().equals("OPTIONS")) {
        log.info("Not OPTIONS {}", request.getMethod());
        final RequestWrapper requestWrapper = new RequestWrapper(request);

        logRequest(requestWrapper, requestWrapper.getBody());

        //Check Basic auth
        final String authorization = requestWrapper.getHeader("authorization");
        if (null != authorization && !authorization.equals("")) {
          log.info("Authorization: {}", authorization);
          if (authorization.toLowerCase().startsWith("basic")) {
            log.info("Basic auth: {}", authorization);
            final String[] values = AuthUtils.parseBasicAuth(authorization);
            final String login = values[0];
            final String password = values[1];
            if (!login.equals("") && !password.equals("")) {
              final Login result = loginRepository.findByUsernameAndPasswordAndActive(login, password, 1);
              final Device device = Optional.ofNullable(result)
                .flatMap(res -> Optional.ofNullable(res.getClient()))
                .flatMap(client -> Optional.ofNullable(client.getDevice()))
                .orElseThrow(() -> new AuthException("Can't authorize"));
              if (device.getStatus() != DeviceStatus.ACTIVE.getStatus()) {
                log.info("Can't authorize");
                throw new AuthException("Can't authorize");
              }
            } else {
              log.info("Can't authorize");
              throw new AuthException("Can't authorize");
            }
            log.info("Successfully logged");
            return true;
          }
        }
            log.info("Device authentication {}", request.getRequestURI());
            final ModelPersonAnswere personAnswere;
            if(request.getRequestURI().contains("findAllReads")) {
              final AllDocReadsRequest allDocReadsRequest = getAllDocReadsRequest(requestWrapper.getBody());
              personAnswere = allDocReadsRequest.getPerson();
            } else {
              personAnswere = getPersonAnswere(requestWrapper.getBody());
            }
            log.info("{}", personAnswere);
            final Optional<ModelServiceInfo> modelServiceInfo = Optional.ofNullable(personAnswere.getModelServiceInfo());
            if (modelServiceInfo.isPresent()) {
              final ModelServiceInfo infoResult = modelServiceInfo.get();
              log.info("Model service info not null");
              log.info("{}", modelServiceInfo);
              final Optional<ServiceInfo> serviceInfo = Optional.ofNullable(infoResult.getServiceInfo());
              if (serviceInfo.isPresent()) {
                final ServiceInfo resultServiceInfo = serviceInfo.get();
                final ClientDTO client;
                if (resultServiceInfo.getScannerSerial().contains(DeviceSerialType.MOBILE_CLIENT.getType())) {
                  client = crmService.findBySerialNumber(resultServiceInfo.getScannerSerial(), request.getLocale());
                } else {
                  client = crmService.findByMacAddressAndDevice(resultServiceInfo.getClientMAC(),
                    resultServiceInfo.getScannerSerial(), request.getLocale());
                }
                if (null == client)
                  throw new CustomException(
                    env.getProperty("ex8887", Integer.class),
                    messageSource.getMessage("message_8887", null, request.getLocale())
                  );
                final DeviceDTO device = client.getDevice();
                log.info("Device info {}", device);
                if (device.isTest()
                  && (device.getStatus() == DeviceStatus.DISTRIBUTED.getStatus()
                  || device.getStatus() == DeviceStatus.ACTIVE.getStatus())) {
                  return true;
                }
                if (device.getStatus() != DeviceStatus.ACTIVE.getStatus()
                  || !device.getSerialNumber().equals(serviceInfo.get().getScannerSerial())) {
                  throw new CustomException(
                    env.getProperty("ex8101", Integer.class),
                    messageSource.getMessage("message_8101", null, request.getLocale())
                  );
                }
                log.info("Device is not test");
                final String pubKey = client.getClientPubKey();
                final String signString = personAnswere.getSignString();
                log.info("Sign string from request 3: {}", personAnswere);
                if (HttpUtil.checkSign(signString, pubKey, personAnswere)) {
//                if (true) {
                  log.info("Successfully verify string by guid: {}", personAnswere.getRequestGuid());
                  return true;
                }
              }
            }

      } else {
        return true;
      }
    } catch (final CustomException ex) {
      log.error("CustomException: " + ex.getMessage());
    } catch (final RestClientException ex) {
      log.error("RestClientException: " + ex.getMessage());
    } catch (final Exception ex) {
      log.error("Exception: " + ex.getMessage());
    }
    response.setStatus(403);
    return false;
  }

  private void logRequest(final HttpServletRequest request, final String body) {
    try {
      final String url = request.getRequestURL().toString();
      final String method = request.getMethod();
      final String headers = HttpUtil.headers((request)).toString();
      final String parsedBody = body.replaceAll("\n", "").replaceAll("\\s+", "");
      log.info("===========================Begin log coming request=================================");
      log.info("URI         : {}", url);
      log.info("Method      : {}", method);
      log.info("Headers     : {}", headers);
      log.info("Request body: {}", parsedBody);
      log.info("==========================End log coming request====================================");
      final ComingLog comingLog = new ComingLog();
      comingLog.setUrl(url);
      comingLog.setMethod(method);
      comingLog.setHeaders(headers);
      comingLog.setBody(parsedBody);
      comingLog.setServiceName(serviceName);
      comingLog.setRequestDate(new Date(System.currentTimeMillis()));
      iLogger.info(comingLog);
    } catch (final Exception ex) {
      log.error("Exception in logRequest() --> " + ex.getMessage());
    }
  }

  private boolean isHeaderExist(final Enumeration<String> names, final String name) {
    while (names.hasMoreElements()) {
      if (names.nextElement().equals(name))
        return true;
    }
    return false;
  }

  @SneakyThrows
  private ModelPersonAnswere getPersonAnswere(final String body) {
    return new ObjectMapper().readValue(body, ModelPersonAnswere.class);
  }

  @SneakyThrows
  private AllDocReadsRequest getAllDocReadsRequest(final String body) {
    return new ObjectMapper().readValue(body, AllDocReadsRequest.class);
  }
}
