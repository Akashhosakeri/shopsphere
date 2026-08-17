package com.shopsphere.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.shopsphere.service.OrderService;
import com.shopsphere.dto.OrderResponse;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{userId}")
    public OrderResponse placeOrder(@PathVariable Long userId) {

    return orderService.placeOrder(userId);
    }

    @GetMapping("/user/{userId}")
    public List<OrderResponse> getOrdersByUser(@PathVariable Long userId) {

    return orderService.getOrdersByUser(userId);
    }

    @GetMapping("/{orderId}")
    public OrderResponse getOrderById(@PathVariable Long orderId) {

    return orderService.getOrderById(orderId);
    }
}