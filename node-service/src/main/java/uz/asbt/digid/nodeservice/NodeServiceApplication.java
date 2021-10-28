package uz.asbt.digid.nodeservice;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableSwagger2Doc
@EnableDiscoveryClient
@EnableEurekaClient
@EnableZuulProxy
public class NodeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NodeServiceApplication.class, args);
    }

}
