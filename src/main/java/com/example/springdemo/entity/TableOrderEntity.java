package com.example.springdemo.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.TimeZone;

@Entity
@Table(name = "table_order", schema = "bookstore", catalog = "")
public class TableOrderEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "order_id")
    private int orderId;
    @Basic
    @Column(name = "total_price")
    private Double totalPrice;
    @Basic
    @Column(name = "user_id")
    private Integer userId;

    @OneToMany(mappedBy = "tableOrderEntity",cascade = CascadeType.ALL)
    private List<OrderlistEntity> orderlist;
    @Basic
    @Column(name = "purchase_time")
    private Timestamp purchaseTime;



    public TableOrderEntity() {

    }



    public TableOrderEntity(Integer orderListCount, Double totalPrice, Integer userId) {
        this.totalPrice = totalPrice;
        this.userId = userId;
    }

    public List<OrderlistEntity> getOrderlist() {
        return orderlist;
    }

    public void setOrderlist(List<OrderlistEntity> orderlist) {
        this.orderlist = orderlist;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TableOrderEntity that = (TableOrderEntity) o;

        if (orderId != that.orderId) return false;
        if (totalPrice != null ? !totalPrice.equals(that.totalPrice) : that.totalPrice != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderId;
        result = 31 * result + (totalPrice != null ? totalPrice.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }

    public Timestamp getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(Timestamp purchaseTime) {
        this.purchaseTime = purchaseTime;
    }
}
