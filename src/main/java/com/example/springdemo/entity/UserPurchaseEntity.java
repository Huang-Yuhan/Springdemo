package com.example.springdemo.entity;

import java.math.BigDecimal;

public class UserPurchaseEntity {
    private Integer id;
    private String name;
    private BigDecimal purchasePrice;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
}
