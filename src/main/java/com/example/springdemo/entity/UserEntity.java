package com.example.springdemo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user", schema = "bookstore", catalog = "")
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id")
    private int userId;
    @Basic
    @Column(name = "nickname")
    private String nickname;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "tel")
    private String tel;
    @Basic
    @Column(name = "address")
    private String address;
    @Basic
    @Column(name = "state")
    private String state;
    @Basic
    @Column(name = "type")
    private String type;

    public UserEntity() {

    }

    public UserEntity(String name, String state, String type) {
        this.name = name;
        this.nickname=name;
        this.state = state;
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (userId != that.userId) return false;
        if (nickname != null ? !nickname.equals(that.nickname) : that.nickname != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (tel != null ? !tel.equals(that.tel) : that.tel != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (tel != null ? tel.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
