package uz.asbt.digid.updaterservice.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import uz.asbt.digid.common.exception.CustomException;
import uz.asbt.digid.common.models.entity.Client;
import uz.asbt.digid.common.models.entity.Device;
import uz.asbt.digid.common.models.rest.Response;
import uz.asbt.digid.common.wrapper.RequestWrapper;
import uz.asbt.digid.updaterservice.dto.CurrentVersion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class AuthInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);

    @Autowired
    @Qualifier("mainTemplate")
    private RestTemplate restTemplate;
    @Autowired
    private Environment env;
    @Autowired
    private MessageSource messageSource;

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
                RequestWrapper requestWrapper = new RequestWrapper(request);
                log.info("Device authentication");
                log.info("Model service info not null");
                final CurrentVersion currentVersion =
                  Optional.ofNullable(getCurrentVersion(requestWrapper.getBody())).orElseThrow(IllegalArgumentException::new);
                Client client = getClient(currentVersion.getDeviceSerialNumber());
                if (null == client)
                    throw new CustomException(
                      env.getProperty("ex8887", Integer.class),
                      messageSource.getMessage("message_8887", null, request.getLocale())
                    );
                Device device = client.getDevice();
                log.info("Device info {}", device.toString());
                String pubKey = client.getClientPubKey();
                String signString = currentVersion.getSignString();
                log.info("Public key from database: {}", pubKey);
                log.info("Sign string from request: {}", signString);
                if (checkSign(signString, pubKey, currentVersion)) {
                    return true;
                }
            } else {
                return true;
            }
        } catch (CustomException ex) {
            log.error("CustomException: " + ex.getMessage());
        } catch (RestClientException ex){
            log.error("RestClientException: " + ex.getMessage());
        } catch (Exception ex) {
            log.error("Exception: " + ex.getMessage());
        }
        response.setStatus(403);
        return false;
    }

    @SneakyThrows
    private Client getClient(final String serialNumber) {
        URI uri = new URI("http://crm-service/client/ru/findBySerial/" + serialNumber);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<Response<Client>> responseEntity = restTemplate.exchange(
          uri,
          HttpMethod.GET,
          httpEntity,
          new ParameterizedTypeReference<Response<Client>>() {}
        );
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)
          && Optional.ofNullable(responseEntity.getBody()).orElseThrow(IllegalArgumentException::new).getCode() == 0) {
            return Optional.ofNullable(responseEntity.getBody()).orElseThrow(IllegalArgumentException::new).getResponse();
        }
        throw new IllegalArgumentException();
    }

    private CurrentVersion getCurrentVersion(final String body) throws Exception {
        return new ObjectMapper().readValue(body, CurrentVersion.class);
    }

    public static boolean checkSign(final String signString,
                                    final String pubKey,
                                    final CurrentVersion currentVersion) {
        return signString.equals(generateSign(pubKey, currentVersion));
    }


    public static String generateSign(final String pubKey,
                                      final CurrentVersion currentVersion) {
        final StringBuilder builder = new StringBuilder();
        builder.append(currentVersion.getDeviceSerialNumber());
        builder.append(currentVersion.getCurrentClientVersion());
        builder.append(pubKey);
        return getHashString(builder.toString());
    }

    @SneakyThrows(NoSuchAlgorithmException.class)
    public static String getHashString(final String str) {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashInBytes = md.digest(str.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
