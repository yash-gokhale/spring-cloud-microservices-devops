package org.example.paymentservice.service;

import org.example.common.dto.PaymentRequest;
import org.example.common.dto.PaymentResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {
    public PaymentResponse paymentDetails(PaymentRequest request){
        PaymentResponse response = new PaymentResponse();

        response.setAmount(request.getAmount());

        response.setOrderId(request.getOrderId());

        response.setPaymentStatus("SUCCESS");

        response.setTransactionId(UUID.randomUUID().toString());

        response.setMessage("Payment processed successfully");

        return response;

    }
}
