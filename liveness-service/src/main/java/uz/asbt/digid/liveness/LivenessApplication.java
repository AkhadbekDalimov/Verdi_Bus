package uz.asbt.digid.liveness;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableAsync
@EnableSwagger2
@EnableSwagger2Doc
@EnableEurekaClient
@EnableZuulProxy
@PropertySources({
  @PropertySource(value = "classpath:exception.properties", encoding = "UTF-8"),
  @PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
})
@EnableDiscoveryClient
@ComponentScan({"uz.asbt.digid.common.*", "uz.asbt.digid.liveness.*", "org.springframework.amqp.*"})
@EnableJpaRepositories(basePackages = {
  "uz.asbt.digid.common.repository",
  "uz.asbt.digid.liveness.*"
})
@EntityScan(basePackages = {
  "uz.asbt.digid.common.models.*",
  "uz.asbt.digid.liveness.dto.*"
})
@SpringBootApplication
public class LivenessApplication {

  public static void main(String[] args) {
    SpringApplication.run(LivenessApplication.class, args);
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
}
