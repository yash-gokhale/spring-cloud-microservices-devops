package org.example.paymentscheduleservice.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.logging.Logger;

import static org.example.common.constants.Constants.ORDER_PATH;

@Component
public class ScheduledTask {

    @Autowired
    private WebClient webClient;

    private final Logger logger = Logger.getLogger(ScheduledTask.class.getName());

    @Scheduled(fixedRate = 60000) // Runs every 60 seconds
    public void run() {
        try {
            logger.info("Scheduled task started: Calling Order Service to update orders.");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBZG1pbiIsImlhdCI6MTc2NTE5MDI2NSwiZXhwIjoxNzY1Mjc2NjY1fQ.qLUlEeyQoqCHsyEwEZWk7MjWxouLlY9RHlMJYHjOvg4");

            HttpEntity<?> entity = new HttpEntity<>(headers);

            //ResponseEntity<String> response = restTemplate.exchange("http://localhost:9000" + ORDER_PATH, HttpMethod.PUT, entity, String.class);
            ResponseEntity<String> response = webClient.put().uri("http://localhost:9000" + ORDER_PATH).retrieve().toEntity(String.class).block();

            assert response != null;
            logger.info(response.getBody());
        }

        catch (Exception e){
            System.err.println("Error occurred while calling Order Service: " + e.getMessage());
        }

    }


}
