package com.shopsphere.dto;

import java.math.BigDecimal;

import com.shopsphere.entity.OrderStatus;

public class OrderResponse {

    private Long id;
    private BigDecimal totalAmount;
    private OrderStatus status;

    public OrderResponse() {
    }

    public OrderResponse(
            Long id,
            BigDecimal totalAmount,
            OrderStatus status) {

        this.id = id;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}