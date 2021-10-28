package uz.asbt.digid.monitoringservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@EnableHystrixDashboard
@EnableDiscoveryClient
@SpringBootApplication
@EnableEurekaClient
public class MonitoringServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(MonitoringServiceApplication.class, args);
  }

}
