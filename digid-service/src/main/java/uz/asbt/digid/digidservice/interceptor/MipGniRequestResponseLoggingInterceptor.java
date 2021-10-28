package uz.asbt.digid.digidservice.interceptor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import uz.asbt.digid.common.models.log.MipGniLog;
import uz.asbt.digid.common.models.rest.Response;
import uz.asbt.digid.common.models.rest.mip.TaxInfoRequest;
import uz.asbt.digid.common.models.rest.mip.TaxInfoResponse;
import uz.asbt.digid.common.service.logger.ILogger;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component("mipGniLoggerInterceptor")
public class MipGniRequestResponseLoggingInterceptor implements ClientHttpRequestInterceptor {

    private final ILogger<MipGniLog> incomeLogger;

    @Autowired
    public MipGniRequestResponseLoggingInterceptor(@Qualifier("mipGniLogger") final ILogger<MipGniLog> incomeLogger) {
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
            log.info("Begin LOG");
            final MipGniLog integration = new MipGniLog();
            final String requestBody = new String(body, StandardCharsets.UTF_8);
            final TaxInfoRequest request = new ObjectMapper().readValue(requestBody, TaxInfoRequest.class);
            integration.setGuid(request.getGuid());
            integration.setSerialNumber(request.getSerialNumber());
            integration.setRequestDate(beginDate);
            integration.setResponseDate(new Date(System.currentTimeMillis()));
            integration.setRequest(request);
            final String responseBody = StreamUtils.copyToString(response.getBody(), Charset.defaultCharset());
            final Response<TaxInfoResponse> result = new ObjectMapper().readValue(responseBody, new TypeReference<Response<TaxInfoResponse>>() {});
            integration.setResponse(result);
            incomeLogger.info(integration);
        } catch (final Exception ex) {
            log.error(ex.getMessage());
        }
    }

    private void logRequest(final HttpRequest request, final byte[] body) {
        log.info("==========================request to MIP TaxInfo Service begin==============================");
        log.info("URI         : {}", request.getURI());
        log.info("Method      : {}", request.getMethod());
        log.info("Headers     : {}", request.getHeaders());
        log.info("Request body: {}", new String(body, StandardCharsets.UTF_8));
        log.info("==========================request to MIP TaxInfo Service end================================");
    }

    private void logResponse(final ClientHttpResponse response) throws IOException {
        log.info("========================response from MIP TaxInfo Service begin=============================");
        log.info("Status code  : {}", response.getStatusCode());
        log.info("Status text  : {}", response.getStatusText());
        log.info("Headers      : {}", response.getHeaders());
        log.info("Response body: {}", StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
        log.info("=======================response from MIP TaxInfo Service end================================");
    }

}
