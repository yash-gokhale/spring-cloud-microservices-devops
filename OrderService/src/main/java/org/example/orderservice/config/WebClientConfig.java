package org.example.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.filter(internalKeyFilter()).build();
    }

    private ExchangeFilterFunction internalKeyFilter() {
        return  (request, next) -> {
            ClientRequest newRequest = ClientRequest.from(request)
                    .build();
            return next.exchange(newRequest);
        };
    }
}
