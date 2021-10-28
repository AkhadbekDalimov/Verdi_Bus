package uz.asbt.digid.gatewayservice.config;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

@Primary
@Component
@Configuration
@EnableSwagger2Doc
public class SwaggerConfig implements SwaggerResourcesProvider {

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        resources.add(swaggerResource("digid-service", "/digid-service/v2/api-docs", "2.0"));
        resources.add(swaggerResource("crm-service", "/crm-service/v2/api-docs", "2.0"));
        resources.add(swaggerResource("logger-service", "/logger-service/v2/api-docs", "2.0"));
        resources.add(swaggerResource("updater-service", "/updater-service/v2/api-docs", "2.0"));
        resources.add(swaggerResource("node-service", "/node-service/v2/api-docs", "2.0"));
        resources.add(swaggerResource("mip-service", "/mip-service/v2/api-docs", "2.0"));
        resources.add(swaggerResource("liveness-service", "/liveness-service/v2/api-docs", "2.0"));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}
