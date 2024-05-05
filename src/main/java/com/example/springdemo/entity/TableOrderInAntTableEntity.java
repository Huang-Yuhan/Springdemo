package com.example.springdemo.entity;

import java.sql.Timestamp;
import java.util.List;

public class TableOrderInAntTableEntity {
    private int orderId;
    private List<OrderlistEntity> orderlist;
    private Double totalPrice;
    private String userName;
    private Timestamp purchaseTime;

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<OrderlistEntity> getOrderlist() {
        return orderlist;
    }

    public void setOrderlist(List<OrderlistEntity> orderlist) {
        this.orderlist = orderlist;
    }


    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }


    public void setPurchaseTime(Timestamp purchaseTime) {
        this.purchaseTime = purchaseTime;
    }
}
