package com.plantstore.entities;

public class OrderItem {
    private Long id;
    private Plant plant;
    private Integer quantity;

    public OrderItem() {
    }

    public OrderItem(Long id, Plant plant, Integer quantity) {
        this.id = id;
        this.plant = plant;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
