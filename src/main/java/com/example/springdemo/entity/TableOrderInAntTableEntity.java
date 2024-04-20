package com.example.springdemo.entity;

import java.sql.Timestamp;
import java.util.List;

public class TableOrderInAntTableEntity {
    private int orderId;
    private List<OrderlistEntity> orderlist;
    private Double totalPrice;
    private String userName;
    private Timestamp purchaseTime;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<OrderlistEntity> getOrderlist() {
        return orderlist;
    }

    public void setOrderlist(List<OrderlistEntity> orderlist) {
        this.orderlist = orderlist;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Timestamp getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(Timestamp purchaseTime) {
        this.purchaseTime = purchaseTime;
    }
}
