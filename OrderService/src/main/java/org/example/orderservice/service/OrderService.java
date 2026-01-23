package org.example.orderservice.service;

import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.common.dto.AccountRequest;
import org.example.common.dto.PaymentRequest;
import org.example.common.dto.PaymentResponse;
import org.example.orderservice.Repository.OrderRepository;
import org.example.orderservice.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {


    private static final Logger log = LogManager.getLogger(OrderService.class);
    @Autowired
    private OrderRepository orderRepository;


    @Autowired
    private WebClient webClient;

    @Autowired
    private OrderClient orderClient;


    public String createOrder(Order order){

        String message = "";
        double totalAmount = order.getQuantity() * order.getPricePerUnit();
        AccountRequest accountRequest = new AccountRequest(order.getCustomerName(), order.getCustomerEmail(), totalAmount);
        //Boolean isAccountAvailable =  restTemplate.getForObject(orderClient.accountUrl() + "/exists/" + order.getCustomerEmail(), Boolean.class);
        Boolean isAccountAvailable = webClient.get().uri(orderClient.accountUrl()+ "/exists/" + order.getCustomerEmail()).retrieve().bodyToMono(Boolean.class).block();
        if(Boolean.FALSE.equals(isAccountAvailable)) {
             //restTemplate.postForObject(orderClient.accountUrl() + "/create", accountRequest, String.class);
            webClient.post().uri(orderClient.accountUrl() + "/create").bodyValue(accountRequest).retrieve().toBodilessEntity().block();
        }
        
            order.setTotalAmount(totalAmount);
            order.setOrderDate(LocalDateTime.now());
            order.setPaymentStatus("PENDING");
            order.setOrderStatus("IN_PROGRESS");
            Order savedOrder = orderRepository.save(order);

            PaymentRequest request = new PaymentRequest(savedOrder.getOrderid(), totalAmount, null, savedOrder.getCustomerEmail());

            try {
                //PaymentResponse response = restTemplate.postForObject(orderClient.paymentUrl(), request, PaymentResponse.class);
                PaymentResponse response = webClient.post().uri(orderClient.paymentUrl()).bodyValue(request).retrieve().bodyToMono(PaymentResponse.class).block();

                assert response != null;
                savedOrder.setCustomerId(response.getCustomerId());
                savedOrder.setTotalAmount(response.getAmount());

                if (response.getPaymentStatus().equals("SUCCESS")) {
                    savedOrder.setOrderStatus("CONFIRMED");
                    savedOrder.setRemarks("Order completed. Payment Success.");
                    savedOrder.setPaymentStatus("SUCCESS");
                    message = "Order created with ID: " + order.getOrderid() + " and Customer email: " + order.getCustomerEmail();
                }
                else {
                    savedOrder.setRemarks("Recent order is cancelled due to insufficient balance in wallet.");
                    savedOrder.setPaymentStatus("FAILED");
                    savedOrder.setOrderStatus("CANCELLED");
                    message = "Order could not be created due to insufficient balance";
                }
            } catch (Exception e) {
                log.info(e.getMessage());
            }
            
            orderRepository.save(savedOrder);
        

        return message;
    }


    public String retryPendingOrder(){

        List<Order> orders = orderRepository.findByPaymentStatus("PENDING");
        if(orders.isEmpty())
            return "No Pending Orders...";
        return orders.stream().map(this::createOrder).reduce((a,b)->a + b).get();
        }



    public void deleteOrderById(long orderId){
        if (!orderRepository.existsById(orderId)) {
            throw new EntityNotFoundException("Order not found with id: " + orderId);
        }

        orderRepository.deleteById(orderId);
    }

}
