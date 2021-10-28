package uz.asbt.digid.loggerservice;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableAsync
@EnableSwagger2Doc
@EnableScheduling
@EnableEurekaClient
@PropertySources({
        @PropertySource(value = "classpath:exception.properties", encoding = "UTF-8"),
        @PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
})
@EnableRabbit
@EnableSwagger2
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScans({
        @ComponentScan("uz.asbt.digid.common.*"),
        @ComponentScan("org.springframework.amqp.*")
})
@EnableJpaRepositories(basePackages = {
        "uz.asbt.digid.common.repository",
        "uz.asbt.digid.loggerservice.repository"
})
@EntityScan(basePackages = {
        "uz.asbt.digid.common.models.entity",
})
public class LoggerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoggerServiceApplication.class, args);
    }

}
