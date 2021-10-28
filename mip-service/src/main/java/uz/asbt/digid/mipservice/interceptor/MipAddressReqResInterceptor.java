package uz.asbt.digid.mipservice.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;

import java.io.ByteArrayOutputStream;

@Component("mipAddressLoggerInterceptor")
public class MipAddressReqResInterceptor implements ClientInterceptor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
        logger.info("############## SOAP Address Info Request Begin #####################");
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            messageContext.getRequest().writeTo(buffer);
            String payload = buffer.toString(java.nio.charset.StandardCharsets.UTF_8.name());
            logger.info("Request: {}", payload);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        logger.info("############## SOAP Address Info Request End #######################");
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
        logger.info("############## SOAP Address Info Response Begin #####################");
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            messageContext.getResponse().writeTo(buffer);
            String payload = buffer.toString(java.nio.charset.StandardCharsets.UTF_8.name());
            logger.info("Response: {}", payload);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        logger.info("############## SOAP Address Info Response End #######################");
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
        logger.info("############## SOAP Address Info FAULT Begin #####################");
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            messageContext.getResponse().writeTo(buffer);
            String payload = buffer.toString(java.nio.charset.StandardCharsets.UTF_8.name());
            logger.info("FAULT: {}", payload);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        logger.info("############## SOAP Address Info FAULT End #######################");
        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Exception e) throws WebServiceClientException {

    }

}
