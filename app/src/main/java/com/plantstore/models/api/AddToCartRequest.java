package com.plantstore.models.api;

public class AddToCartRequest {
    private Long userId;
    private Long plantId;
    private Integer amount;

    public AddToCartRequest() {
    }

    public AddToCartRequest(Long userId, Long plantId, Integer amount) {
        this.userId = userId;
        this.plantId = plantId;
        this.amount = amount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPlantId() {
        return plantId;
    }

    public void setPlantId(Long plantId) {
        this.plantId = plantId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
