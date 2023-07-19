package com.plantstore.models.api;

import java.util.List;

public class CreateOrderRequest {
    private Long userId;
    private List<CreateOrderItemRequest> orderItems;

    public CreateOrderRequest() {
    }

    public CreateOrderRequest(Long userId, List<CreateOrderItemRequest> orderItems) {
        this.userId = userId;
        this.orderItems = orderItems;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<CreateOrderItemRequest> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<CreateOrderItemRequest> orderItems) {
        this.orderItems = orderItems;
    }
}
