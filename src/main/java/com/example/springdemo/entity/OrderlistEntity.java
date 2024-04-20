package com.example.springdemo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.awt.print.Book;
import java.text.DecimalFormat;
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Entity
@Table(name = "orderlist", schema = "bookstore", catalog = "")
public class OrderlistEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "order_list_id")
    private int orderListId;

    @Basic
    @Column(name = "order_id")
    private Integer orderId;
    @Basic
    @Column(name = "book_count")
    private Integer bookCount;

    @Basic
    @Column(name = "total_price")
    private Double totalPrice;
    @Basic
    @Column(name = "user_id")
    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookEntity book;

    @JsonBackReference
    @ManyToOne
    @JoinColumn( name = "order_id", referencedColumnName ="order_id",insertable = false,updatable = false)
    private TableOrderEntity tableOrderEntity;

    public TableOrderEntity getTableOrderEntity() {
        return tableOrderEntity;
    }

    public void setTableOrderEntity(TableOrderEntity tableOrderEntity) {
        this.tableOrderEntity = tableOrderEntity;
    }

    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    public OrderlistEntity() {
    }

    public OrderlistEntity(Integer orderId, Integer bookCount, BookEntity book, Double totalPrice) {
        this.orderId = orderId;
        this.bookCount = bookCount;
        this.totalPrice = totalPrice;
        this.book=book;
    }

    public int getOrderListId() {
        return orderListId;
    }

    public void setOrderListId(int orderListId) {
        this.orderListId = orderListId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getBookCount() {
        return bookCount;
    }

    public void setBookCount(Integer bookCount) {
        this.bookCount = bookCount;
    }


    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderlistEntity that = (OrderlistEntity) o;

        if (orderListId != that.orderListId) return false;
        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;
        if (bookCount != null ? !bookCount.equals(that.bookCount) : that.bookCount != null) return false;
        if (totalPrice != null ? !totalPrice.equals(that.totalPrice) : that.totalPrice != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderListId;
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + (bookCount != null ? bookCount.hashCode() : 0);
        result = 31 * result + (totalPrice != null ? totalPrice.hashCode() : 0);
        return result;
    }



    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public OrderlistEntity(Integer orderId, int bookCount, BookEntity book, Double totalPrice, Integer userId) {
        this.orderId = orderId;
        this.bookCount = bookCount;
        this.book = book;
        this.totalPrice = totalPrice;
        this.userId = userId;
    }
}
