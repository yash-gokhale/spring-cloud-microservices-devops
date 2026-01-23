package org.example.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.config.GatewayPropertiesConfig;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderClient {
    private final GatewayPropertiesConfig gatewayPropertiesConfig;

    public String orderUrl(){
        return gatewayPropertiesConfig.getBaseUrl() + gatewayPropertiesConfig.getServices().getOrderService();
    }

    public String paymentUrl(){
        return gatewayPropertiesConfig.getBaseUrl() + gatewayPropertiesConfig.getServices().getPaymentService();
    }

    public String accountUrl(){
        return gatewayPropertiesConfig.getBaseUrl() + gatewayPropertiesConfig.getServices().getAccountService();
    }
}
