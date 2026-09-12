package com.shopsphere.dto;

import com.shopsphere.entity.OrderStatus;

public class OrderStatusRequest {

    private OrderStatus status;

    public OrderStatusRequest() {
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}