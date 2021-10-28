package uz.asbt.digid.crmservice.interceptor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import uz.asbt.digid.common.models.log.CrmLog;
import uz.asbt.digid.common.models.log.CrmRequest;
import uz.asbt.digid.common.models.log.CrmResponse;
import uz.asbt.digid.common.service.logger.ILogger;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Component("crmLoggerInterceptor")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CrmRequestResponseInterceptor implements ClientHttpRequestInterceptor {

  @Qualifier("crmLogger")
  ILogger<CrmLog> logger;

  @Override
  public ClientHttpResponse intercept(final HttpRequest request,
                                      final byte[] body,
                                      final ClientHttpRequestExecution execution) throws IOException {
    logRequest(request, body);
    final Date startDate = new Date(System.currentTimeMillis());
    final ClientHttpResponse response = execution.execute(request, body);
    logResponse(response);
    log(request, body, startDate, response);
    return response;
  }

  private void log(final HttpRequest request, final byte[] requestBody, final Date startDate, final ClientHttpResponse response) {
    try {
      final CrmLog crmLog = new CrmLog();
      final CrmRequest crmRequest = new CrmRequest();
      crmRequest.setUrl(request.getURI().toString());
      crmRequest.setMethod(request.getMethodValue());
      final String reqStr = new String(requestBody, StandardCharsets.UTF_8);
      crmRequest.setBody(new ObjectMapper().readValue(reqStr, new TypeReference<Object>() {
      }));
      crmRequest.setRequestDate(startDate);

      final CrmResponse crmResponse = new CrmResponse();
      crmResponse.setStatus(response.getStatusCode().toString());
      crmResponse.setMessage(response.getStatusText());
      final String resStr = StreamUtils.copyToString(response.getBody(), Charset.defaultCharset());
      crmResponse.setBody(new ObjectMapper().readValue(resStr, new TypeReference<Object>() {
      }));
      crmResponse.setResponseDate(new Date(System.currentTimeMillis()));

      crmLog.setGuid(UUID.randomUUID().toString());
      crmLog.setRequest(crmRequest);
      crmLog.setResponse(crmResponse);

      logger.info(crmLog);
    } catch (final Exception ex) {
      log.error(ex.getMessage());
    }
  }

  private void logRequest(final HttpRequest request, final byte[] body) throws IOException {
    log.info("===========================request to CRM Service begin=================================");
    log.info("URI         : {}", request.getURI());
    log.info("Method      : {}", request.getMethod());
    log.info("Headers     : {}", request.getHeaders());
    log.info("Request body: {}", new String(body, "UTF-8"));
    log.info("==========================request to CRM Service end====================================");
  }

  private void logResponse(final ClientHttpResponse response) throws IOException {
    log.info("============================response from CRM Service begin=============================");
    log.info("Status code  : {}", response.getStatusCode());
    log.info("Status text  : {}", response.getStatusText());
    log.info("Headers      : {}", response.getHeaders());
    log.info("Response body: {}", StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
    log.info("=======================response from CRM Service end====================================");
  }

}
