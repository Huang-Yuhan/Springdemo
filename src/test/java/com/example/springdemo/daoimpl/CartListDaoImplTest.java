package com.example.springdemo.daoimpl;

import com.example.springdemo.entity.CartlistEntity;
import com.example.springdemo.repository.BookRepository;
import com.example.springdemo.repository.CartListRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CartListDaoImplTest {
    @Autowired
    private CartListRepository cartlist;

    @Autowired
    private CartListDaoImpl cartlistDaoImpl;

    @Autowired
    private BookRepository bookRepository;


    static int testUserId =1;

    static Stream AddSuccessTestCases() {
        return Stream.of(
                Arguments.of(6,testUserId),
                Arguments.of(7,testUserId),
                Arguments.of(8,testUserId)
        );
    }

    static Stream AddFailTestCases() {
        return Stream.of(
                Arguments.of(0,testUserId),
                Arguments.of(0,testUserId),
                Arguments.of(0,testUserId)
        );
    }

    @ParameterizedTest
    @MethodSource("AddSuccessTestCases")
    void AddSuccess(Integer bookId,Integer UserId) {
        List<CartlistEntity> lis=cartlist.getCartlistEntitiesByUserId(UserId);
        List<CartlistEntity> newLis = cartlistDaoImpl.Add(bookId,UserId);
        assertEquals(lis.size()+1,newLis.size());
        //在newLis中查找是否有bookId
        boolean flag = false;
        for(CartlistEntity cartlistEntity : newLis){
            if(cartlistEntity.getBook().getId()==bookId){
                flag = true;
                break;
            }
        }
        //继续添加，检查数量是否增加
        if(flag){
            List<CartlistEntity> newLis2 = cartlistDaoImpl.Add(bookId,UserId);
            for (CartlistEntity cartlistEntity : newLis2) {
                if (cartlistEntity.getBook().getId() == bookId) {
                    assertEquals(cartlistEntity.getCount(), 2);
                    break;
                }
            }
        }

    }

    @ParameterizedTest
    @MethodSource("AddFailTestCases")
    void AddFail(Integer bookId,Integer UserId) {
        List<CartlistEntity> lis=cartlist.getCartlistEntitiesByUserId(UserId);
        List<CartlistEntity> newLis = cartlistDaoImpl.Add(bookId,UserId);
        assertEquals(lis.size(),newLis.size());
    }

    @Test
    void TestGetCartLists() {
        List<CartlistEntity> lis=cartlist.getCartlistEntitiesByUserId(testUserId);
        List<CartlistEntity> newLis = cartlistDaoImpl.getCartLists(testUserId);
        assertEquals(lis.size(),newLis.size());
    }

    @Test
    void TestDelCartOne() {
        //对testUserId的购物车先进行添加操作

        List<Integer> bookids = new ArrayList<>();
        bookids.add(6);
        bookids.add(7);
        bookids.add(8);

        for(Integer bookId : bookids){
            List<CartlistEntity> lis=cartlist.getCartlistEntitiesByUserId(testUserId);
            List<CartlistEntity> newLis = cartlistDaoImpl.Add(bookId,testUserId);
            assertEquals(lis.size()+1,newLis.size());
        }

        //对testUserId的购物车进行删除操作
        for(Integer bookId : bookids){
            List<CartlistEntity> lis=cartlist.getCartlistEntitiesByUserId(testUserId);
            List<CartlistEntity> newLis = cartlistDaoImpl.DelCart(bookId,testUserId);
            assertEquals(lis.size()-1,newLis.size());
        }
    }

    @Test
    void TestDelCartTwo() {
        //对testUserId的购物车先进行添加操作,每种书添加两次
        List<Integer> bookids = new ArrayList<>();
        bookids.add(6);
        bookids.add(7);
        bookids.add(8);

        for(Integer bookId : bookids){
            List<CartlistEntity> lis=cartlist.getCartlistEntitiesByUserId(testUserId);
            List<CartlistEntity> newLis = cartlistDaoImpl.Add(bookId,testUserId);
            assertEquals(lis.size()+1,newLis.size());
            List<CartlistEntity> newLis2 = cartlistDaoImpl.Add(bookId,testUserId);
            assertEquals(newLis.size(),newLis2.size());
        }

        //对testUserId的购物车进行删除操作
        for(Integer bookId : bookids){
            List<CartlistEntity> lis=cartlist.getCartlistEntitiesByUserId(testUserId);
            List<CartlistEntity> newLis = cartlistDaoImpl.DelCart(bookId,testUserId);
            //检查该书的数量是否减少
            assertEquals(lis.size(),newLis.size());
            for(CartlistEntity cartlistEntity : newLis){
                if(cartlistEntity.getBook().getId()==bookId){
                    assertEquals(cartlistEntity.getCount(),1);
                    break;
                }
            }

            List<CartlistEntity> newLis2 = cartlistDaoImpl.DelCart(bookId,testUserId);
            //检查该书是否被删除
            assertEquals(newLis.size()-1,newLis2.size());
            boolean flag = true;
            for(CartlistEntity cartlistEntity : newLis2){
                if(cartlistEntity.getBook().getId()==bookId){
                    flag = false;
                    break;
                }
            }
            assertTrue(flag);
        }
    }
}