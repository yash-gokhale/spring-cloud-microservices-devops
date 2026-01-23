package org.example.orderservice.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.common.dto.TopUpRequest;
import org.example.orderservice.Repository.OrderRepository;
import org.example.orderservice.model.Order;
import org.example.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody Order order){
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    @PostMapping("/batch")
    public List<String> createOrders(@RequestBody List<Order> orders){
        List<String> ordersMessage = new ArrayList<>();
         for(Order order: orders){
             ordersMessage.add(orderService.createOrder(order));
         }
         return ordersMessage;
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable("id") long orderId){
        return orderRepository.findById(orderId).orElse(null);
    }

    @GetMapping
    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    @PutMapping
    public ResponseEntity<String> retryPendingOrder(){
        return ResponseEntity.ok(orderService.retryPendingOrder());
    }

    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable("id") long orderId){
        orderService.deleteOrderById(orderId);
        return "Order deleted by id " + orderId;
    }

    @DeleteMapping
    public String deleteOrders() {
            orderRepository.deleteAll();
        return "Orders deleted successfully";
    }


}
