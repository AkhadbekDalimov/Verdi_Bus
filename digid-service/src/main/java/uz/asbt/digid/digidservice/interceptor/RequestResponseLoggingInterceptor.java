package uz.asbt.digid.digidservice.interceptor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import uz.asbt.digid.common.models.log.Integration;
import uz.asbt.digid.common.models.rest.NibbdRequest;
import uz.asbt.digid.common.models.rest.PhysicalResponse;
import uz.asbt.digid.common.service.logger.ILogger;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component("loggerInterceptor")
public class RequestResponseLoggingInterceptor implements ClientHttpRequestInterceptor {

    private final ILogger<Integration> incomeLogger;

    @Value("${log.request.length}")
    private Integer maxLength;

    @Autowired
    public RequestResponseLoggingInterceptor(@Qualifier("integrationLogger") final ILogger<Integration> incomeLogger) {
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
            final Integration integration = new Integration();
            final String requestBody = new String(body, "UTF-8");
            final NibbdRequest physicalRequest = new ObjectMapper().readValue(requestBody, NibbdRequest.class);
            integration.setGuid(physicalRequest.getGuid());
            integration.setSerialNumber(physicalRequest.getSerialNumber());
            integration.setIncomeDate(beginDate);
            integration.setOutcomeDate(new Date(System.currentTimeMillis()));
            integration.setRequest(physicalRequest);
            final String responseBody = StreamUtils.copyToString(response.getBody(), Charset.defaultCharset());
            final PhysicalResponse result = new ObjectMapper().readValue(responseBody, new TypeReference<PhysicalResponse>() {});
            integration.setResponse(result);
            incomeLogger.info(integration);
        } catch (final Exception ex) {
            log.error(ex.getMessage());
        }
    }

    private void logRequest(final HttpRequest request, final byte[] body) throws IOException {
        log.info("===========================request to Nibbd Service begin=================================");
        log.info("URI         : {}", request.getURI());
        log.info("Method      : {}", request.getMethod());
        log.info("Headers     : {}", request.getHeaders());
        log.info("Request body: {}", substringBody(body));
        log.info("==========================request to Nibbd Service end====================================");
    }

    private void logResponse(final ClientHttpResponse response) throws IOException {
        log.info("============================response from Nibbd Service begin=============================");
        log.info("Status code  : {}", response.getStatusCode());
        log.info("Status text  : {}", response.getStatusText());
        log.info("Headers      : {}", response.getHeaders());
        log.info("Response body: {}", StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
        log.info("=======================response from Nibbd Service end====================================");
    }

    private String substringBody(final byte[] body) {
        final String str = new String(body, StandardCharsets.UTF_8);
        if (str.length() >= maxLength)
            return str.substring(0, maxLength - 1);
        return str;
    }
}
