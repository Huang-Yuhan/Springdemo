package com.example.springdemo.serviceimpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.springdemo.dao.CartListDao;
import com.example.springdemo.entity.CartlistEntity;
import com.example.springdemo.utils.Codes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.aspectj.lang.annotation.Before;

import java.util.ArrayList;
import java.util.List;

class CartListServiceImplTest {
    @Mock
    private CartListDao cartListDao;

    @InjectMocks
    private CartListServiceImpl cartListService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void add() {
        // Arrange
        List<CartlistEntity> list = new ArrayList<>();
        CartlistEntity cartlistEntity = new CartlistEntity();
        cartlistEntity.setUserId(1);
        list.add(cartlistEntity);

        // Mock
        when(cartListDao.Add(1, Codes.Decode(1))).thenReturn(list);

        // Act
        List<CartlistEntity> result = cartListService.Add(1, 1);

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void getCartLists() {
        // Arrange
        List<CartlistEntity> list = new ArrayList<>();
        CartlistEntity cartlistEntity1 = new CartlistEntity();
        CartlistEntity cartlistEntity2 = new CartlistEntity();
        cartlistEntity1.setUserId(1);
        cartlistEntity2.setUserId(1);
        list.add(cartlistEntity1);
        list.add(cartlistEntity2);

        // Mock
        when(cartListDao.getCartLists(Codes.Decode(1))).thenReturn(list);

        // Act
        List<CartlistEntity> result = cartListService.getCartLists(1);

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void delCart() {
        // Arrange
        List<CartlistEntity> list = new ArrayList<>();
        CartlistEntity cartlistEntity = new CartlistEntity();
        cartlistEntity.setUserId(2);
        list.add(cartlistEntity);

        // Mock
        when(cartListDao.DelCart(1, 1)).thenReturn(list);

        // Act
        List<CartlistEntity> result = cartListService.DelCart(1, Codes.Code(1));

        // Assert
        assertEquals(1, result.size());
    }
}