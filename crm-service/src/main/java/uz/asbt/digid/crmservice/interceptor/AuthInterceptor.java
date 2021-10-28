package uz.asbt.digid.crmservice.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import uz.asbt.digid.common.models.entity.auth.Auth;
import uz.asbt.digid.common.models.log.ComingLog;
import uz.asbt.digid.common.repository.AuthRepository;
import uz.asbt.digid.common.service.logger.ILogger;
import uz.asbt.digid.common.utils.HttpUtil;
import uz.asbt.digid.common.wrapper.RequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthInterceptor implements HandlerInterceptor {

  @Value("${spring.application.name}")
  private String serviceName;

  private final AuthRepository authRepository;

  @Qualifier("comingLogger")
  private final ILogger<ComingLog> iLogger;

  @Override
  public boolean preHandle(final HttpServletRequest request,
                           final HttpServletResponse response,
                           final Object handler) {
    try {
      log.info("Auth interceptor");
      if (!request.getMethod().equals("OPTIONS")) {
        final RequestWrapper requestWrapper = new RequestWrapper(request);
        logRequest(requestWrapper, requestWrapper.getBody());
        final String authorization = requestWrapper.getHeader("authorization");
        log.info("Authorization: {}", authorization);
        if (authorization.toLowerCase().startsWith("basic")) {
          log.info("Basic auth: {}", authorization);
          final String base64Credentials = authorization.substring("Basic".length()).trim();
          final byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
          final String credentials = new String(credDecoded, StandardCharsets.UTF_8);
          // credentials = username:password
          final String[] values = credentials.split(":", 2);
          final String login = values[0];
          final String password = values[1];
          if (!login.equals("") && !password.equals("")) {
            final Auth auth = authRepository.findByLoginAndPasswordAndStatus(login, password, 1);
            if (auth == null) {
              throw new IllegalArgumentException("Can't authorize");
            }
          }
          log.info("Successfully logged");
          return true;
        }
      } else {
        return true;
      }
    } catch (final Exception ex) {
      log.error(ex.getMessage());
    }
    log.info("Error while login");
    response.setStatus(403);
    return false;
  }

  private void logRequest(final HttpServletRequest request, final String body) {
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
  }


}
