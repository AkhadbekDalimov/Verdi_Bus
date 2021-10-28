package uz.asbt.digid.digidservice.interceptor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import uz.asbt.digid.common.models.log.MipAddressLog;
import uz.asbt.digid.common.models.rest.Response;
import uz.asbt.digid.common.models.rest.mip.GetAddressRequest;
import uz.asbt.digid.common.models.rest.mip.GetAddressResponse;
import uz.asbt.digid.common.service.logger.ILogger;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;

@Component("mipAddressLoggerInterceptor")
public class MipAddressRequestResponseLoggingInterceptor implements ClientHttpRequestInterceptor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ILogger<MipAddressLog> incomeLogger;

    @Autowired
    public MipAddressRequestResponseLoggingInterceptor(@Qualifier("mipAddressLogger") final ILogger<MipAddressLog> incomeLogger) {
        this.incomeLogger = incomeLogger;
    }

    @Override
    public ClientHttpResponse intercept(final HttpRequest request,
                                        final byte[] body,
                                        final ClientHttpRequestExecution execution) throws IOException {
        logRequest(request, body);
        final Date beginDate = new Date(System.currentTimeMillis());
        final ClientHttpResponse response = execution.execute(request, body);
        logResponse(response);
        log(body, response, beginDate);
        return response;
    }

    private void log(final byte[] body,
                     final ClientHttpResponse response,
                     final Date beginDate) {
        try {
            logger.info("Begin LOG");
            final MipAddressLog integration = new MipAddressLog();
            final String requestBody = new String(body, "UTF-8");
            final GetAddressRequest request = new ObjectMapper().readValue(requestBody, GetAddressRequest.class);
            integration.setGuid(request.getGuid());
            integration.setSerialNumber(request.getSerialNumber());
            integration.setRequestDate(beginDate);
            integration.setResponseDate(new Date(System.currentTimeMillis()));
            integration.setRequest(request);
            final String responseBody = StreamUtils.copyToString(response.getBody(), Charset.defaultCharset());
            final Response<GetAddressResponse> result = new ObjectMapper().readValue(responseBody, new TypeReference<Response<GetAddressResponse>>() {});
            integration.setResponse(result);
            incomeLogger.info(integration);
        } catch (final Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    private void logRequest(final HttpRequest request, final byte[] body) throws IOException {
        logger.info("==========================request to MIP AddressInfo Service begin==============================");
        logger.info("URI         : {}", request.getURI());
        logger.info("Method      : {}", request.getMethod());
        logger.info("Headers     : {}", request.getHeaders());
        logger.info("Request body: {}", new String(body, "UTF-8"));
        logger.info("==========================request to MIP AddressInfo Service end================================");
    }

    private void logResponse(final ClientHttpResponse response) throws IOException {
        logger.info("========================response from MIP AddressInfo Service begin=============================");
        logger.info("Status code  : {}", response.getStatusCode());
        logger.info("Status text  : {}", response.getStatusText());
        logger.info("Headers      : {}", response.getHeaders());
        logger.info("Response body: {}", StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
        logger.info("=======================response from MIP AddressInfo Service end================================");
    }

}
