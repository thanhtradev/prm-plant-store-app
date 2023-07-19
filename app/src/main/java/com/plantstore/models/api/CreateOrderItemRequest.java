package com.plantstore.models.api;

public class CreateOrderItemRequest {
    private Long plantId;
    private Integer quantity;

    public CreateOrderItemRequest(Long plantId, Integer quantity) {
        this.plantId = plantId;
        this.quantity = quantity;
    }

    public CreateOrderItemRequest() {
    }

    public Long getPlantId() {
        return plantId;
    }

    public void setPlantId(Long plantId) {
        this.plantId = plantId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
