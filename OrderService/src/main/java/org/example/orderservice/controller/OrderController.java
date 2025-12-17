package org.example.orderservice.controller;

import org.example.orderservice.Repository.OrderRepository;
import org.example.orderservice.model.Order;
import org.example.orderservice.service.OrderService;
import org.example.orderservice.service.OrderUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderUpdate orderUpdate;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order){
        return ResponseEntity.ok(orderService.createOrder(order));
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
    public ResponseEntity<String> updateOrder(){
        return ResponseEntity.ok(orderUpdate.updateOrder());
    }

    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable("id") long orderId){
        orderRepository.deleteById(orderId);
        return "Order deleted by id " + orderId;
    }


}
