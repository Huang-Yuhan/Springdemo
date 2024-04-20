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

    public void setCartlistid(int cartlistid) {
        this.cartlistid = cartlistid;
    }

    /*public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }*/



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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartlistEntity that = (CartlistEntity) o;

        if (cartlistid != that.cartlistid) return false;
       // if (bookid != that.bookid) return false;
        if (userId != that.userId) return false;
        if (count != null ? !count.equals(that.count) : that.count != null) return false;
        if (totalprice != null ? !totalprice.equals(that.totalprice) : that.totalprice != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cartlistid;
       // result = 31 * result + bookid;
        result = 31 * result + (count != null ? count.hashCode() : 0);
        result = 31 * result + (totalprice != null ? totalprice.hashCode() : 0);
        result = 31 * result + userId;
        return result;
    }
}
