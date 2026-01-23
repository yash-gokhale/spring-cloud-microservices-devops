package org.example.paymentservice.repository;

import org.example.paymentservice.model.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentDetails, String> {
}
