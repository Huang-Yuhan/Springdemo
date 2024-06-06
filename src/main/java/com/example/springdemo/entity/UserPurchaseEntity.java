package com.example.springdemo.entity;

import java.math.BigDecimal;

public class UserPurchaseEntity{
    private Integer id;
    private String name;
    private BigDecimal purchasePrice;

    public String getName() {
        return name;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public UserPurchaseEntity(Integer id, String name, BigDecimal pucharasePrice) {
        this.id = id;
        this.name = name;
        this.purchasePrice = pucharasePrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
