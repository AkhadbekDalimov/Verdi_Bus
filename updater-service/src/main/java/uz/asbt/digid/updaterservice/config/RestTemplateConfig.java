package uz.asbt.digid.updaterservice.config;

import lombok.RequiredArgsConstructor;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

  @Qualifier("loggingInterceptor")
  @Autowired
  private ClientHttpRequestInterceptor requestInterceptor;

  @Value("${auth.username}")
  private String username;
  @Value("${auth.password}")
  private String password;

  @LoadBalanced
  @Bean("mainTemplate")
  public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
    int timeout = 10000;
    return restTemplateBuilder
      .requestFactory(() -> new BufferingClientHttpRequestFactory(getClientHttpRequestFactory(timeout)))
      .basicAuthentication(username, password)
      .interceptors(requestInterceptor)
      .build();
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

}
