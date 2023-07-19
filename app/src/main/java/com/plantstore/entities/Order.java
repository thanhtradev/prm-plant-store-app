package com.plantstore.entities;

import java.util.Date;
import java.util.List;

public class Order {
    private Long id;
    private Date date;
    private Double total;
    private String status;
    private List<OrderItem> orderItems;

    public Order() {
    }

    public Order(Long id, Date date, Double total,  String status, List<OrderItem> orderItems) {
        this.id = id;
        this.date = date;
        this.total = total;
        this.status = status;
        this.orderItems = orderItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
