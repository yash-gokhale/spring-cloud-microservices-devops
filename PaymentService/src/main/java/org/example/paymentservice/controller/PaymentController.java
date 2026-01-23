package org.example.paymentservice.controller;

import org.example.common.dto.PaymentRequest;
import org.example.common.dto.PaymentResponse;
import org.example.paymentservice.repository.PaymentRepository;
import org.example.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentRepository paymentRepository;

    @PostMapping
    public ResponseEntity<PaymentResponse> getPaymentStatus(@RequestBody PaymentRequest request){
        return ResponseEntity.ok(paymentService.makePayment(request));
    }

    @DeleteMapping
    public String deleteALlPayments(){
        paymentRepository.deleteAll();
        return "All payments are deleted";
    }
}
