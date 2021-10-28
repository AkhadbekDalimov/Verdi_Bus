package uz.asbt.digid.digidservice.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import uz.asbt.digid.common.models.log.ComingLog;
import uz.asbt.digid.common.service.logger.ILogger;
import uz.asbt.digid.common.utils.HttpUtil;
import uz.asbt.digid.common.wrapper.RequestWrapper;
import uz.asbt.digid.digidservice.model.Token;
import uz.asbt.digid.digidservice.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RefreshScope
public class TokenInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${spring.application.name}")
    private String serviceName;

    @Value("${token.secretKey}")
    private String secretKey;

    @Autowired
    @Qualifier("comingLogger") private ILogger<ComingLog> iLogger;

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) {
        try {
            if (!request.getMethod().equals("OPTIONS")) {
                logger.info("Not OPTIONS {}", request.getMethod());
                final RequestWrapper requestWrapper = new RequestWrapper(request);

                logRequest(requestWrapper, requestWrapper.getBody());

                logger.info("Content-Type: {}", requestWrapper.getContentType());
                logger.info("Body {}", requestWrapper.getBody());
                logger.info("Servlet path: {}", requestWrapper.getServletPath());
                if (requestWrapper.getServletPath().equals("/token")) {
                    return true;
                }
                final String authorization = requestWrapper.getHeader("authorization");
                if (null != authorization) {
                    logger.info("Authorization: {}", authorization);
                    if (authorization.toLowerCase().startsWith("bearer")) {
                        logger.info("Bearer auth: {}", authorization);
                        final String tokenValue = authorization.substring("Bearer".length()).trim();
                        if (tokenValue.equals("")) {
                            throw new IllegalArgumentException("Token can't be empty");
                        }
                        logger.info("Token string value is: {}", tokenValue);
                        final WebUtils webUtils = new WebUtils();
                        final Token token = webUtils.parseToken(secretKey, tokenValue, Token.class);
                        if (token == null)
                            throw new IllegalArgumentException("Can't parse token");
                        logger.info("Token is: {}", token.toString());
                        logger.info("Successfully logged");
                        return true;
                    }
                }
            }
        } catch (final Exception ex) {
            logger.error(ex.getMessage());
        }
        response.setStatus(HttpStatus.FORBIDDEN.value());
        return false;
    }

    private void logRequest(final HttpServletRequest request, final String body) {
        final String url = request.getRequestURL().toString();
        final String method = request.getMethod();
        final String headers = HttpUtil.headers((request)).toString();
        final String parsedBody = body.replaceAll("\n", "").replaceAll("\\s+", "");
        logger.info("===========================Begin log coming request=================================");
        logger.info("URI         : {}", url);
        logger.info("Method      : {}", method);
        logger.info("Headers     : {}", headers);
        logger.info("Request body: {}", parsedBody);
        logger.info("==========================End log coming request====================================");
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
