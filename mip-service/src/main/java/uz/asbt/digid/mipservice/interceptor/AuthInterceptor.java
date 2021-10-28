package uz.asbt.digid.mipservice.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import uz.asbt.digid.common.utils.AuthUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${auth.username}")
    private String login;

    @Value("${auth.password}")
    private String password;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("Start interceptor");
        if (!request.getDispatcherType().name().equals("REQUEST")) {
            logger.info("true");
            return true;
        }
        if (!request.getMethod().equals("OPTIONS")) {
            try {
                final String authorization = request.getHeader("authorization");
                if (null != authorization) {
                    logger.info("Authorization: {}", authorization);
                    if (authorization.toLowerCase().startsWith("basic")) {
                        logger.info("Basic auth: {}", authorization);
                        final String[] values = AuthUtils.parseBasicAuth(authorization);
                        final String login = values[0];
                        final String password = values[1];
                        logger.info(String.format("Login: %s, password: %s", login, password));
                        logger.info(String.format("Login: %s, password: %s", this.login, this.password));
                        if (!login.equals("") && !password.equals("")) {
                            if (login.equals(this.login) && password.equals(this.password)) {
                                logger.info("Successfully logged");
                                return true;
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                logger.error(ex.getMessage());
            }
        }
        response.setStatus(HttpStatus.FORBIDDEN.value());
        return false;
    }
}
