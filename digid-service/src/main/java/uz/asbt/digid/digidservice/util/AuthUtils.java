package uz.asbt.digid.digidservice.util;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import uz.asbt.digid.common.exception.CustomException;
import uz.asbt.digid.common.models.entity.auth.Login;
import uz.asbt.digid.common.models.entity.client.Answere;
import uz.asbt.digid.common.models.entity.client.ModelPersonAnswere;
import uz.asbt.digid.common.models.entity.client.ModelServiceInfo;
import uz.asbt.digid.common.models.entity.client.ServiceInfo;
import uz.asbt.digid.common.repository.LoginRepository;
import uz.asbt.digid.digidservice.common.Status;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Locale;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component("authUtils")
public class AuthUtils {

    LoginRepository loginRepository;
    Environment env;
    MessageSource messageSource;

    public static String[] parseBasicAuth(final String authorization) {
        final String base64Credentials = authorization.substring("Basic".length()).trim();
        final byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        final String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        return credentials.split(":", 2);
    }

    public void checkAuth(final ModelPersonAnswere person,
                          final HttpHeaders headers,
                          final Locale locale) {
        if (person.getModelServiceInfo() == null) {
            log.info("Service info is null");
            final String basicAuth = Objects.requireNonNull(headers.get("authorization")).stream().findFirst().orElse(null);
            log.info("Authorization not null");
            if (null != basicAuth) {
                log.info("Basic auth: {}", basicAuth);
                final String[] values = AuthUtils.parseBasicAuth(basicAuth);
                if (values.length < 2) {
                    throw new CustomException(8102);
                }
                final String login = values[0];
                final String password = values[1];
                if (!login.equals("") && !password.equals("")) {
                    log.info(String.format("Login: %s, Password: %s", login, password));
                    person.setModelServiceInfo(serviceInfo(login, password, locale));
                    log.info("ModelServiceInfo: {}", person.getModelServiceInfo());
                } else {
                    throw new CustomException(8102);
                }
            }
        }
    }

    private ModelServiceInfo serviceInfo(final String username, final String password, final Locale locale) {
        log.info("Begin getting ServiceInfo");
        final Login login = loginRepository.findByUsernameAndPasswordAndActive(
                username,
                password,
                Status.ACTIVE.ordinal());
        log.info("Login model: {}", login.toString());
        if (null != login.getClient()) {
            log.info("Login not null");
            if (null != login.getClient().getDevice()) {
                log.info("Device: {}", login.getClient().getDevice());
                final ModelServiceInfo modelServiceInfo = new ModelServiceInfo();
                final Answere answere = new Answere();
                answere.setAnswereId(env.getProperty("ex200", Integer.class));
                answere.setAnswereComment(messageSource.getMessage("message_200", null, locale));
                modelServiceInfo.setAnswere(answere);
                final ServiceInfo serviceInfo = new ServiceInfo();
                serviceInfo.setScannerSerial(login.getClient().getDevice().getSerialNumber());
                modelServiceInfo.setServiceInfo(serviceInfo);
                log.info(modelServiceInfo.toString());
                return modelServiceInfo;
            }
            return null;
        }
        return null;
    }

}
