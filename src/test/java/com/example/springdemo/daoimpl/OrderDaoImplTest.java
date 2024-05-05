package com.example.springdemo.daoimpl;

import com.example.springdemo.entity.*;
import com.example.springdemo.repository.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderDaoImplTest {
    @Autowired
    private OrderDaoImpl orderDaoImpl;
    @Autowired
    private CartListDaoImpl cartListDaoImpl;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderListRepository orderListRepository;

    @Autowired
    private CartListRepository cartListRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    static int testUserId=1;
    static int realUserId=27;
    static int[] testBookIds={6,7,8};
    @Test
    void AddNewOrderFromCartsSuccess()
    {
        // Add books to cart
        for(int i=0;i<testBookIds.length;i++)
        {
            cartListDaoImpl.Add(testBookIds[i],testUserId);
            cartListDaoImpl.Add(testBookIds[i],testUserId);
        }

        List<CartlistEntity> lis=cartListRepository.getCartlistEntitiesByUserId(testUserId);
        List<TableOrderEntity> orderList=orderRepository.getAllByUserId(testUserId);
        // Add new order
        orderDaoImpl.AddNewOrder(testUserId, new java.sql.Timestamp(System.currentTimeMillis()));

        assertEquals(0, cartListRepository.getCartlistEntitiesByUserId(testUserId).size());
        assertEquals(orderList.size()+1, orderRepository.getAllByUserId(testUserId).size());
    }

    @Test
    void AddNewOrderFromCartsFail()
    {
        List<CartlistEntity> lis=cartListRepository.getCartlistEntitiesByUserId(testUserId);
        List<TableOrderEntity> orderList=orderRepository.getAllByUserId(testUserId);
        assertEquals(0,lis.size());
        // Add new order
        orderDaoImpl.AddNewOrder(testUserId, new java.sql.Timestamp(System.currentTimeMillis()));
        assertEquals(0, cartListRepository.getCartlistEntitiesByUserId(testUserId).size());
        assertEquals(orderList.size(), orderRepository.getAllByUserId(testUserId).size());
    }

    @Test
    void AddNewOrderFailDueToNoInventory()
    {
        BookEntity book=bookRepository.getBookEntityById(testBookIds[0]);
        book.setInventory(0);
        bookRepository.save(book);
        List<TableOrderEntity> orderList=orderRepository.getAllByUserId(testUserId);
        cartListDaoImpl.Add(testBookIds[0],testUserId);
        assertEquals(orderList.size(), orderRepository.getAllByUserId(testUserId).size());
    }

    @Test
    void AddNewOrderSuccess()
    {
        List<TableOrderEntity> orderList=orderRepository.getAllByUserId(testUserId);
        orderDaoImpl.AddNewOrder(testBookIds[0], testUserId, new java.sql.Timestamp(System.currentTimeMillis()));
        assertEquals(orderList.size()+1, orderRepository.getAllByUserId(testUserId).size());
    }

    @Test
    void TestGetAllOrders()
    {
        List<TableOrderEntity> orderList=orderRepository.getAllByUserId(realUserId);
        List<TableOrderInAntTableEntity> result=orderDaoImpl.getAllOrders(realUserId);
        assertEquals(orderList.size(), result.size());

        orderList = orderRepository.findAll();
        result = orderDaoImpl.getAllOrders();
        assertEquals(orderList.size(), result.size());
    }

    @Test
    void TestSearchOrderWithAUser()
    {
        List<TableOrderInAntTableEntity> lis;
        Timestamp start=new Timestamp(0);
        Timestamp end=new Timestamp(System.currentTimeMillis());

        lis=orderDaoImpl.SearchOrder(realUserId,start,end,"可怕");
        assertNotEquals(0,lis.size());
    }

    @Test
    void TestSearchOrder()
    {
        List<TableOrderInAntTableEntity> lis;
        Timestamp start=new Timestamp(0);
        Timestamp end=new Timestamp(System.currentTimeMillis());

        lis=orderDaoImpl.SearchOrder(start,end,"可怕");
        assertNotEquals(0,lis.size());
    }

    @Test
    void TestAdminBookStatistics()
    {
        var start=new Timestamp(0);
        var end=new Timestamp(System.currentTimeMillis());
        var lis = orderDaoImpl.AdminBookStatistics(start,end);
        assertNotEquals(0,lis.size());
    }
}