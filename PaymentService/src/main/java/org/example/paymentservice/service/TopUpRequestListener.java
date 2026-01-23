package org.example.paymentservice.service;

import lombok.RequiredArgsConstructor;
import org.example.common.dto.TopUpRequest;
import org.example.common.dto.TopUpResponse;
import org.example.paymentservice.model.PaymentDetails;
import org.example.paymentservice.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TopUpRequestListener {

    private final JmsTemplate jmsTemplate;

    @Autowired
    private PaymentRepository paymentRepository;

    Logger log = LoggerFactory.getLogger(TopUpRequestListener.class);

    @JmsListener(destination = "topup.request.queue")
    public void processTopUpRequest(TopUpRequest topUpRequest) {
        PaymentDetails paymentDetails = new PaymentDetails(UUID.randomUUID().toString(), null, "TOP-UP_SUCCESS", null );
        paymentDetails.setPaymentDate(LocalDateTime.now());
        sendTopUpResponse(paymentRepository.save(paymentDetails),topUpRequest);

    }

    private void sendTopUpResponse(PaymentDetails paymentDetails, TopUpRequest topUpRequest) {
        TopUpResponse topUpResponse = new TopUpResponse();
        topUpResponse.setTopUpStatus(paymentDetails.getPaymentStatus());
        topUpResponse.setTransactionId(paymentDetails.getTransactionId());
        topUpResponse.setCustomerId(topUpRequest.getCustomerId());
        topUpResponse.setFinalPrice(topUpRequest.getAmount());
        log.info("Sending Top-Up Response: {}", topUpResponse);
           jmsTemplate.convertAndSend("topup.response.queue", topUpResponse);

    }

}
