package uz.asbt.digid.digidservice.config;

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
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Value("${auth.username}")
    private String username;

    @Value("${auth.password}")
    private String password;

    @Autowired
    @Qualifier("loggerInterceptor")
    private ClientHttpRequestInterceptor requestInterceptor;

    @Autowired
    @Qualifier("mipPersonLoggerInterceptor")
    private ClientHttpRequestInterceptor mipPersonRequestInterceptor;

    @Autowired
    @Qualifier("mipAddressLoggerInterceptor")
    private ClientHttpRequestInterceptor mipAddressRequestInterceptor;

    @Autowired
    @Qualifier("mipGniLoggerInterceptor")
    private ClientHttpRequestInterceptor mipGniRequestInterceptor;

    private ClientHttpRequestFactory getClientHttpRequestFactory(final int timeout) {
        final RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout)
                .setSocketTimeout(timeout)
                .build();
        final CloseableHttpClient client = HttpClientBuilder.create()
                .setDefaultRequestConfig(config)
                .build();
        return new HttpComponentsClientHttpRequestFactory(client);
    }

    @Primary
    @LoadBalanced
    @Bean("mainRestTemplate")
    public RestTemplate mainTemplate(final RestTemplateBuilder restTemplateBuilder){
        final int timeout = 20000;
        return restTemplateBuilder
                .requestFactory(() -> new BufferingClientHttpRequestFactory(getClientHttpRequestFactory(timeout)))
                .basicAuthentication(username, password)
                .build();
    }

    @LoadBalanced
    @Bean("mipPersonRestTemplate")
    public RestTemplate mipPersonRestTemplate(final RestTemplateBuilder restTemplateBuilder) {
        final int timeout = 5000;
        return restTemplateBuilder
                .requestFactory(() -> new BufferingClientHttpRequestFactory(getClientHttpRequestFactory(timeout)))
                .basicAuthentication(username, password)
                .interceptors(mipPersonRequestInterceptor)
                .build();
    }

    @LoadBalanced
    @Bean("mipAddressRestTemplate")
    public RestTemplate mipAddressRestTemplate(final RestTemplateBuilder restTemplateBuilder) {
        final int timeout = 5000;
        return restTemplateBuilder
                .requestFactory(() -> new BufferingClientHttpRequestFactory(getClientHttpRequestFactory(timeout)))
                .basicAuthentication(username, password)
                .interceptors(mipAddressRequestInterceptor)
                .build();
    }

    @LoadBalanced
    @Bean("mipGniRestTemplate")
    public RestTemplate mipGniRestTemplate(final RestTemplateBuilder restTemplateBuilder) {
        final int timeout = 5000;
        return restTemplateBuilder
                .requestFactory(() -> new BufferingClientHttpRequestFactory(getClientHttpRequestFactory(timeout)))
                .basicAuthentication(username, password)
                .interceptors(mipGniRequestInterceptor)
                .build();
    }

    @Bean("integrationRestTemplate")
    public RestTemplate integrationTemplate(final RestTemplateBuilder restTemplateBuilder) {
        final int timeout = 35000;
        return restTemplateBuilder
                .requestFactory(() -> new BufferingClientHttpRequestFactory(getClientHttpRequestFactory(timeout)))
                .interceptors(requestInterceptor)
                .build();
    }

}
