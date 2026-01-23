package org.example.accountservice.service;

import lombok.RequiredArgsConstructor;
import org.example.common.dto.TopUpRequest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopUpRequestProducer {
    private final JmsTemplate jmsTemplate;

    public void sendRequest(TopUpRequest topUpRequest) {
        jmsTemplate.convertAndSend("topup.request.queue", topUpRequest);
    }
}
