package com.example.springdemo.controller;

import com.example.springdemo.entity.CartlistEntity;
import com.example.springdemo.service.CartListService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class CartListControllerTest {

    @Mock
    private CartListService cartListService;

    @InjectMocks
    private CartListController cartListController;

    @Test
    void getCartLists() {
        Integer userId = 1;
        CartlistEntity cart = new CartlistEntity(); // 假设CartlistEntity有合适的构造函数或者方法设置属性
        List<CartlistEntity> cartList = Arrays.asList(cart);

        when(cartListService.getCartLists(userId)).thenReturn(cartList);

        Map<String, String> param = new HashMap<>();
        param.put("UserId", userId.toString());
        assertEquals(cartList, cartListController.getCartLists(param));
    }

    @Test
    void addCartList() {
        Integer userId = 1;
        Integer bookId = 101;
        CartlistEntity cart = new CartlistEntity();
        List<CartlistEntity> cartList = Arrays.asList(cart);

        when(cartListService.Add(bookId, userId)).thenReturn(cartList);

        Map<String, String> param = new HashMap<>();
        param.put("bookId", bookId.toString());
        param.put("UserId", userId.toString());
        assertEquals(cartList, cartListController.AddCartList(param));
    }

    @Test
    void delCartLists() {
        Integer userId = 1;
        Integer bookId = 101;
        CartlistEntity cart = new CartlistEntity();
        List<CartlistEntity> cartList = Arrays.asList(cart);

        when(cartListService.DelCart(bookId, userId)).thenReturn(cartList);

        Map<String, String> param = new HashMap<>();
        param.put("bookId", bookId.toString());
        param.put("UserId", userId.toString());
        assertEquals(cartList, cartListController.DelCartLists(param));
    }
}