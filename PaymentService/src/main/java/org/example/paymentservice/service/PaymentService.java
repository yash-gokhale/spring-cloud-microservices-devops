package org.example.paymentservice.service;

import org.example.common.dto.AccountRequest;
import org.example.common.dto.AccountResponse;
import org.example.common.dto.PaymentRequest;
import org.example.common.dto.PaymentResponse;
import org.example.paymentservice.model.PaymentDetails;
import org.example.paymentservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.example.common.constants.Constants.ACCOUNT_PATH;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private WebClient webClient;


    public PaymentResponse makePayment(PaymentRequest request){
        PaymentResponse response = new PaymentResponse();
        PaymentDetails paymentDetails = new PaymentDetails();
        AccountResponse accountResponse;
        AccountRequest accountRequest = new AccountRequest(null, request.getCustomerEmail(), request.getAmount());

        response.setTransactionId(UUID.randomUUID().toString());
        response.setOrderId(request.getOrderId());

        paymentDetails.setTransactionId(response.getTransactionId());
        paymentDetails.setPaymentDate(LocalDateTime.now());
        paymentDetails.setOrderId(request.getOrderId());
        paymentDetails.setPaymentType("Wallet");

        //accountResponse = restTemplate.postForObject("http://localhost:9000" + ACCOUNT_PATH + "/update", accountRequest, AccountResponse.class);
        accountResponse = webClient.post().uri("http://localhost:9000" + ACCOUNT_PATH + "/update").bodyValue(accountRequest).retrieve().bodyToMono(AccountResponse.class).block();

        assert accountResponse != null;
        response.setAmount(accountResponse.getFinalPrice());

        response.setCustomerId(accountResponse.getCustomerId());

        if(accountResponse.isHasSufficientBalance()){
            response.setPaymentStatus("SUCCESS");
            response.setMessage("Payment processed successfully");
            paymentDetails.setPaymentStatus("PAYMENT_SUCCESS");
        }
        else{
            response.setPaymentStatus("FAILED");
            response.setMessage("Payment failed");
            paymentDetails.setPaymentStatus("PAYMENT_FAILED");
        }

        paymentRepository.save(paymentDetails);
        return response;

    }
}
