package com.example.springdemo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_auth", schema = "bookstore", catalog = "")
public class UserAuthEntity {
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id")
    private int userId;
    @Basic
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "password")
    private String password;

    public int getUserId() {
        return userId;
    }


    public String getPassword() {
        return password;
    }


    public UserAuthEntity(int userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public UserAuthEntity() {
    }
}
