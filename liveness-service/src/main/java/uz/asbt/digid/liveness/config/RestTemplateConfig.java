package uz.asbt.digid.liveness.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

  private final ClientHttpRequestInterceptor interceptor;

  @Autowired
  public RestTemplateConfig(@Qualifier("rendipLoggingInterceptor") ClientHttpRequestInterceptor interceptor) {
    this.interceptor = interceptor;
  }

  private ClientHttpRequestFactory getClientHttpRequestFactory(int timeout) {
    RequestConfig config = RequestConfig.custom()
                             .setConnectTimeout(timeout)
                             .setConnectionRequestTimeout(timeout)
                             .setSocketTimeout(timeout)
                             .build();
    CloseableHttpClient client = HttpClientBuilder.create()
                                   .setDefaultRequestConfig(config)
                                   .build();
    return new HttpComponentsClientHttpRequestFactory(client);
  }

  @Bean("rendipRestTemplate")
  public RestTemplate rendipRestTemplate(RestTemplateBuilder restTemplateBuilder) {
    int timeout = 15000;
    return restTemplateBuilder
             .additionalMessageConverters(new ResourceHttpMessageConverter())
             .requestFactory(() -> new BufferingClientHttpRequestFactory(getClientHttpRequestFactory(timeout)))
             .interceptors(interceptor)
             .build();
  }

}
