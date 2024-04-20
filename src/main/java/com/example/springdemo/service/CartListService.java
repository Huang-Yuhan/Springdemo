package com.example.springdemo.service;

import com.example.springdemo.entity.CartlistEntity;

import java.util.List;

public interface CartListService {

    List<CartlistEntity> Add(Integer bookId,Integer UserId);

    List<CartlistEntity> getCartLists(Integer UserId);

    List<CartlistEntity> DelCart(Integer bookId,Integer UserId);
}
