package org.example.eurekaserver;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(EurekaServerApplication.class, args);
    }
}
