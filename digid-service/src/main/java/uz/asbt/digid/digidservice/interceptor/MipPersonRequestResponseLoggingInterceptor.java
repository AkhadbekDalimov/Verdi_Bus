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
import uz.asbt.digid.common.models.log.MipPersonLog;
import uz.asbt.digid.common.models.rest.Response;
import uz.asbt.digid.common.models.rest.mip.PersonInfoRequest;
import uz.asbt.digid.common.models.rest.mip.PersonInfoResponse;
import uz.asbt.digid.common.service.logger.ILogger;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component("mipPersonLoggerInterceptor")
public class MipPersonRequestResponseLoggingInterceptor implements ClientHttpRequestInterceptor {

    private final ILogger<MipPersonLog> incomeLogger;

    @Autowired
    public MipPersonRequestResponseLoggingInterceptor(@Qualifier("mipPersonLogger") final ILogger<MipPersonLog> incomeLogger) {
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
            final MipPersonLog integration = new MipPersonLog();
            final String requestBody = new String(body, "UTF-8");
            final PersonInfoRequest request = new ObjectMapper().readValue(requestBody, PersonInfoRequest.class);
            integration.setGuid(request.getGuid());
            integration.setSerialNumber(request.getSerialNumber());
            integration.setRequestDate(beginDate);
            integration.setResponseDate(new Date(System.currentTimeMillis()));
            integration.setRequest(request);
            final String responseBody = StreamUtils.copyToString(response.getBody(), Charset.defaultCharset());
            final Response<PersonInfoResponse> result = new ObjectMapper().readValue(responseBody, new TypeReference<Response<PersonInfoResponse>>() {});
            integration.setResponse(result);
            incomeLogger.info(integration);
        } catch (final Exception ex) {
            log.error(ex.getMessage());
        }
    }

    private void logRequest(final HttpRequest request, final byte[] body) {
        log.info("==========================request to MIP PersonInfo Service begin==============================");
        log.info("URI         : {}", request.getURI());
        log.info("Method      : {}", request.getMethod());
        log.info("Headers     : {}", request.getHeaders());
        log.info("Request body: {}", new String(body, StandardCharsets.UTF_8));
        log.info("==========================request to MIP PersonInfo Service end================================");
    }

    private void logResponse(final ClientHttpResponse response) throws IOException {
        log.info("========================response from MIP PersonInfo Service begin=============================");
        log.info("Status code  : {}", response.getStatusCode());
        log.info("Status text  : {}", response.getStatusText());
        log.info("Headers      : {}", response.getHeaders());
        log.info("Response body: {}", StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
        log.info("=======================response from MIP PersonInfo Service end================================");
    }

}
