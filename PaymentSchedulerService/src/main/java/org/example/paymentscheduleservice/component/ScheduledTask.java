package org.example.paymentscheduleservice.component;

import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

import static org.example.common.constants.Constants.ORDER_SERVICE_URL;

@Component
public class ScheduledTask {
    private final Logger logger = Logger.getLogger(ScheduledTask.class.getName());

    private final RestTemplate restTemplate;

    public ScheduledTask (RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Scheduled(fixedRate = 60000) // Runs every 60 seconds
    public void run() {
        try {
            logger.info("Scheduled task started: Calling Order Service to update orders.");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBZG1pbiIsImlhdCI6MTc2NTE5MDI2NSwiZXhwIjoxNzY1Mjc2NjY1fQ.qLUlEeyQoqCHsyEwEZWk7MjWxouLlY9RHlMJYHjOvg4");

            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(ORDER_SERVICE_URL, HttpMethod.PUT, entity, String.class);

            if(response.getBody() != null) {
                logger.info("Scheduled task completed: Order Service updated.");
            }
            else
                logger.info("Scheduled task completed: No orders to update.");
        }
        catch (Exception e){
            System.err.println("Error occurred while calling Order Service: " + e.getMessage());
        }

    }


}
