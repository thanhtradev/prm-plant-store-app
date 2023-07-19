package com.plantstore.entities;

import java.util.List;

public class Cart {
    private Long id;
    private Long userId;
    private List<Long> cartItemIds;
    private Double totalPrice;

    public Cart() {
    }

    public Cart(Long id, Long userId, List<Long> cartItemIds, Double totalPrice) {
        this.id = id;
        this.userId = userId;
        this.cartItemIds = cartItemIds;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getCartItemIds() {
        return cartItemIds;
    }

    public void setCartItemIds(List<Long> cartItemIds) {
        this.cartItemIds = cartItemIds;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
