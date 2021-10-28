package uz.asbt.digid.mipservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.transport.http.HttpsUrlConnectionMessageSender;

@Configuration
public class WebServiceConfig {

    @Autowired
    @Qualifier("mipPersonLoggerInterceptor")
    private ClientInterceptor personInterceptor;

    @Autowired
    @Qualifier("mipAddressLoggerInterceptor")
    private ClientInterceptor addressInterceptor;

    @Autowired
    @Qualifier("mipGniLoggerInterceptor")
    private ClientInterceptor gniInterceptor;

    @Value("${asbt.mib.server}")
    private String url;

    @Bean(name = "webServicePersonTemplate")
    public WebServiceTemplate webServicePersonTemplate() {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        HttpsUrlConnectionMessageSender sender = new HttpsUrlConnectionMessageSender();
        sender.setHostnameVerifier((s, sslSession) -> true);
        webServiceTemplate.setMessageSender(sender);
        webServiceTemplate.setInterceptors(new ClientInterceptor[]{personInterceptor});
        return webServiceTemplate;
    }

    @Bean(name = "webServiceAddressTemplate")
    public WebServiceTemplate webServiceAddressTemplate() {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        HttpsUrlConnectionMessageSender sender = new HttpsUrlConnectionMessageSender();
        sender.setHostnameVerifier((s, sslSession) -> true);
        webServiceTemplate.setMessageSender(sender);
        webServiceTemplate.setInterceptors(new ClientInterceptor[]{addressInterceptor});
        return webServiceTemplate;
    }

    @Bean(name = "webServiceGniTemplate")
    public WebServiceTemplate webServiceGniTemplate() {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        HttpsUrlConnectionMessageSender sender = new HttpsUrlConnectionMessageSender();
        sender.setHostnameVerifier((s, sslSession) -> true);
        webServiceTemplate.setMessageSender(sender);
        webServiceTemplate.setInterceptors(new ClientInterceptor[]{gniInterceptor});
        return webServiceTemplate;
    }

}
