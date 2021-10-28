package uz.asbt.digid.digidservice;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableAsync
@EnableSwagger2
@EnableSwagger2Doc
@EnableScheduling
@EnableEurekaClient
@EnableZuulProxy
@PropertySources({
  @PropertySource(value = "classpath:exception.properties", encoding = "UTF-8"),
  @PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
})
@EnableDiscoveryClient
@ComponentScan({"uz.asbt.digid.common.*", "uz.asbt.digid.digidservice.*", "org.springframework.amqp.*"})
@EnableJpaRepositories(basePackages = {
  "uz.asbt.digid.common.repository",
  "uz.asbt.digid.digidservice.repository"
})
@EntityScan(basePackages = {
  "uz.asbt.digid.common.models.entity",
  "uz.asbt.digid.digidservice.model.entity"
})
@EnableCaching
public class DigIdServiceApplication {

  public static void main(final String[] args) {
    SpringApplication.run(DigIdServiceApplication.class, args);
  }

}
