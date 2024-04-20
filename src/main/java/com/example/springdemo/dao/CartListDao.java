package com.example.springdemo.dao;

import com.example.springdemo.entity.CartlistEntity;

import java.util.List;

public interface CartListDao {
    List<CartlistEntity> Add(Integer bookId,Integer UserId);

    List<CartlistEntity> getCartLists(Integer UserId);


    List<CartlistEntity> DelCart(Integer bookId,Integer UesrId);
}
