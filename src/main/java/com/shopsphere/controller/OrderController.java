package com.shopsphere.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.shopsphere.service.OrderService;
import com.shopsphere.dto.OrderResponse;
import com.shopsphere.dto.OrderStatusRequest;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public OrderResponse placeOrder(
            Authentication authentication) {

        return orderService.placeOrder(
                authentication.getName()
        );
    }

    @GetMapping
    public List<OrderResponse> getOrdersByUser(
            Authentication authentication) {

        return orderService.getOrdersByUser(
                authentication.getName()
        );
    }

    @GetMapping("/{orderId}")
    public OrderResponse getOrderById(
            Authentication authentication,
            @PathVariable Long orderId) {

        return orderService.getOrderById(
                authentication.getName(),
                orderId
        );
    }

    @PutMapping("/{orderId}/cancel")
    public OrderResponse cancelOrder(
            Authentication authentication,
            @PathVariable Long orderId) {

        return orderService.cancelOrder(
                authentication.getName(),
                orderId
        );
    }

    @PutMapping("/{orderId}/status")
    public OrderResponse updateOrderStatus(
            Authentication authentication,
            @PathVariable Long orderId,
            @RequestBody OrderStatusRequest request) {

        return orderService.updateOrderStatus(
                authentication.getName(),
                orderId,
                request.getStatus()
        );
    }
}