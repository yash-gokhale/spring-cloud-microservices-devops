package org.example.paymentservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "payment_details")
public class PaymentDetails {
    @Id
    private String transactionId;
    private Long orderId;
    private String paymentStatus;
    private LocalDateTime paymentDate;
    private String paymentType;
    
    public PaymentDetails(){}

    public PaymentDetails(String transactionId, Long orderId, String paymentStatus, String paymentType) {
        this.transactionId = transactionId;
        this.orderId = orderId;
        this.paymentStatus = paymentStatus;
        this.paymentType = paymentType;
    }

}


