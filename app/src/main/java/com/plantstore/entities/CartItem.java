package com.plantstore.entities;

public class CartItem {
    private Long id;
    private Long cartId;
    private Plant plant;
    private Integer amount;

    public CartItem() {
    }

    public CartItem(Long id, Long cartId, Plant plant, Integer amount) {
        this.id = id;
        this.cartId = cartId;
        this.plant = plant;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
