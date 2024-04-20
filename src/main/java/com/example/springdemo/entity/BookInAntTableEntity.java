package com.example.springdemo.entity;

import java.math.BigDecimal;

public class BookInAntTableEntity {
    private Integer id;
    private String name;
    private String author;
    private Integer inventory;
    private BigDecimal price;
    private String type;
    private String description;
    private String image;
    private String isbn;
    private Integer count;

    public BookInAntTableEntity(Integer id, String name, String author, Integer inventory, BigDecimal price, String type, String description, String image, String isbn, Integer count) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.inventory = inventory;
        this.price = price;
        this.type = type;
        this.description = description;
        this.image = image;
        this.isbn = isbn;
        this.count = count;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
