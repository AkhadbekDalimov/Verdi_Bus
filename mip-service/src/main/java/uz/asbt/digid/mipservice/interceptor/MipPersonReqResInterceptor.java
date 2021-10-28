package uz.asbt.digid.mipservice.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

@Component("mipPersonLoggerInterceptor")
public class MipPersonReqResInterceptor implements ClientInterceptor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
        logger.info("############## SOAP Person Info Request Begin #####################");
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            messageContext.getRequest().writeTo(buffer);
            String payload = buffer.toString(java.nio.charset.StandardCharsets.UTF_8.name());
            logger.info("Request: {}", payload);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        logger.info("############## SOAP Person Info Request End #######################");
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
        logger.info("############## SOAP Person Info Response Begin #####################");
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            messageContext.getResponse().writeTo(buffer);
            String payload = buffer.toString(java.nio.charset.StandardCharsets.UTF_8.name());
            logger.info("Response: {}", payload);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        logger.info("############## SOAP Person Info Response End #######################");
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
        logger.info("############## SOAP Person Info FAULT Begin #####################");
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            messageContext.getResponse().writeTo(buffer);
            String payload = buffer.toString(java.nio.charset.StandardCharsets.UTF_8.name());
            logger.info("FAULT: {}", payload);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        logger.info("############## SOAP Person Info FAULT End #######################");
        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Exception e) throws WebServiceClientException {

    }

}
