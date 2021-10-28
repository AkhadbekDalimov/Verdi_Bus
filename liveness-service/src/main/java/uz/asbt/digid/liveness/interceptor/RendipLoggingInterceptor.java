package uz.asbt.digid.liveness.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Component("rendipLoggingInterceptor")
@Slf4j
public class RendipLoggingInterceptor implements ClientHttpRequestInterceptor {

  @Override
  public ClientHttpResponse intercept(final HttpRequest request,
                                      final byte[] body,
                                      final ClientHttpRequestExecution execution) throws IOException {
    logRequest(request, body);
    ClientHttpResponse response = execution.execute(request, body);
    logResponse(response);
    return response;
  }

  private void logRequest(final HttpRequest request, final byte[] body) throws IOException {
    log.info("==========================request to Rendip Recognize Service begin==============================");
    log.info("URI         : {}",        request.getURI());
    log.info("Method      : {}",        request.getMethod());
    log.info("Headers     : {}",        request.getHeaders());
    log.info("Request body length: {}", new String(body, StandardCharsets.UTF_8).length());
    log.info("==========================request to Rendip Recognize Service end================================");
  }

  private void logResponse(final ClientHttpResponse response) throws IOException {
    log.info("========================response from Rendip Recognize Service begin=============================");
    log.info("Status code  : {}", response.getStatusCode());
    log.info("Status text  : {}", response.getStatusText());
    log.info("Headers      : {}", response.getHeaders());
    log.info("Response body: {}", StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
    log.info("=======================response from Rendip Recognize Service end================================");
  }

}
