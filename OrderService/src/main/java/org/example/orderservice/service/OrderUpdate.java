package org.example.orderservice.service;

import org.example.common.dto.PaymentRequest;
import org.example.common.dto.PaymentResponse;
import org.example.orderservice.Repository.OrderRepository;
import org.example.orderservice.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.example.common.constants.Constants.PAYMENT_SERVICE_URL;

@Service
public class OrderUpdate {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderRepository orderRepository;

    public String updateOrder(){
        List<Order> orders = orderRepository.findAll().stream().filter(order -> order.getPaymentStatus().equals("PENDING")).toList();
        if(orders.isEmpty())
            return null;
        for(Order order: orders){
            PaymentRequest request = new PaymentRequest(order.getOrderid(), order.getTotalAmount(), "CREDIT_CARD", order.getCustomerEmail());
            PaymentResponse response = restTemplate.postForObject(PAYMENT_SERVICE_URL, request, PaymentResponse.class);
            assert response != null;
            order.setPaymentStatus(response.getPaymentStatus());
            if(response.getPaymentStatus().equals("SUCCESS"))
                order.setOrderStatus("CONFIRMED");
            else
                order.setOrderStatus("CANCELLED");
            orderRepository.save(order);
        }
    return "Orders updated successfully";
    }
}
