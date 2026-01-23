package org.example.orderservice.config;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "api-gateway")
@Getter
@Setter
public class GatewayPropertiesConfig {

    private String baseUrl;
    private Services services;

    @Getter
    @Setter
    public static class Services {
        private String orderService;
        private String paymentService;
        private String accountService;
    }

}

