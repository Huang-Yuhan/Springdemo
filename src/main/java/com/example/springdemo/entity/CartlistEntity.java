package com.example.springdemo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "cartlist", schema = "bookstore", catalog = "")
public class CartlistEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "cartlistid")
    private int cartlistid;
    @Basic
    @Column(name = "count")
    private Integer count;
    @Basic
    @Column(name = "totalprice")
    private Double totalprice;
    @Basic
    @Column(name = "user_id")
    private int userId;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private  BookEntity book;



    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    public int getCartlistid() {
        return cartlistid;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Double totalprice) {
        this.totalprice = totalprice;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
