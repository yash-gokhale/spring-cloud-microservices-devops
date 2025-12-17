package org.example.orderservice.service;

import org.example.common.dto.PaymentRequest;
import org.example.common.dto.PaymentResponse;
import org.example.orderservice.Repository.OrderRepository;
import org.example.orderservice.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.example.common.constants.Constants.PAYMENT_SERVICE_URL;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Order createOrder(Order order) {
        // Business logic for creating an order
        double totalAmount = order.getQuantity() * order.getPricePerUnit();
        order.setTotalAmount(totalAmount);
        order.setOrderStatus("CREATED");
        order.setPaymentStatus("PENDING");
        order.setOrderDate(LocalDate.now());

        Order savedOrder = orderRepository.save(order);

        PaymentRequest request = new PaymentRequest(savedOrder.getOrderid(), totalAmount, "CREDIT_CARD", savedOrder.getCustomerEmail());

        PaymentResponse response = restTemplate.postForObject(PAYMENT_SERVICE_URL, request, PaymentResponse.class);

        savedOrder.setPaymentStatus(response.getPaymentStatus());

        if(response.getPaymentStatus().equals("SUCCESS"))
            savedOrder.setOrderStatus("CONFIRMED");
        else
            savedOrder.setOrderStatus("CANCELLED");




        return orderRepository.save(savedOrder);
    }

}
