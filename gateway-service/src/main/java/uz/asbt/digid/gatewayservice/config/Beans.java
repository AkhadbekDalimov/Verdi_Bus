package uz.asbt.digid.gatewayservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uz.asbt.digid.gatewayservice.filter.ErrorFilter;
import uz.asbt.digid.gatewayservice.filter.PostFilter;
import uz.asbt.digid.gatewayservice.filter.PreFilter;
import uz.asbt.digid.gatewayservice.filter.RouteFilter;

@Configuration
public class Beans {

    @Bean
    public PreFilter preFilter() {
        return new PreFilter();
    }
    @Bean
    public PostFilter postFilter() {
        return new PostFilter();
    }
    @Bean
    public ErrorFilter errorFilter() {
        return new ErrorFilter();
    }
    @Bean
    public RouteFilter routeFilter() {
        return new RouteFilter();
    }

}
