package uz.asbt.digid.crmservice.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Autowired
    @Qualifier("crmLoggerInterceptor")
    private ClientHttpRequestInterceptor requestInterceptor;

    @Value("${crm.integration.disable-ssl-validation}")
    private boolean isSSLDisabled;

    @Bean("crmRestTemplate")
    public RestTemplate crmRestTemplate(final RestTemplateBuilder restTemplateBuilder){
        return restTemplateBuilder
                .requestFactory(() -> new BufferingClientHttpRequestFactory(getClientHttpRequestFactory()))
                .interceptors(requestInterceptor)
                .build();
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        final int timeout = 60000;
        final RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout)
                .setSocketTimeout(timeout)
                .build();
        final CloseableHttpClient client;
        if (isSSLDisabled) {
            client = HttpClientBuilder.create()
                    .setDefaultRequestConfig(config)
                    .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                    .build();
        } else {
            client = HttpClientBuilder.create()
                    .setDefaultRequestConfig(config)
                    .build();
        }
        return new HttpComponentsClientHttpRequestFactory(client);
    }

}
