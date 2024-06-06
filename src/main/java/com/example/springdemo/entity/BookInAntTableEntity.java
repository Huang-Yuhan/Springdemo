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

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getInventory() {
        return inventory;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getIsbn() {
        return isbn;
    }

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

    public Integer getCount() {
        return count;
    }

}
