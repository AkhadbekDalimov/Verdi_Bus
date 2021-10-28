package uz.asbt.digid.updaterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableAsync
@Configuration
@EnableScheduling
@ComponentScan(basePackages = {
        "uz.asbt.digid.updaterservice"
})
@PropertySources({
        @PropertySource(value = "classpath:exception.properties", encoding = "UTF-8"),
        @PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
})
@EnableJpaRepositories({
        "uz.asbt.digid.updaterservice.repository"
})
@EntityScan(basePackages = {
        "uz.asbt.digid.common.models.entity",
        "uz.asbt.digid.updaterservice"
})
@EnableSwagger2
@SpringBootApplication(exclude = {RabbitAutoConfiguration.class})
@EnableDiscoveryClient
@EnableEurekaClient
@EnableZuulProxy
public class DigidUpdaterApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigidUpdaterApplication.class, args);
    }

}
