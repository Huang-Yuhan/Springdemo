package com.example.springdemo.serviceimpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.springdemo.dao.OrderDao;
import com.example.springdemo.entity.*;
import com.example.springdemo.utils.Codes;

import ch.qos.logback.core.util.COWArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.aspectj.lang.annotation.Before;

import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

class OrderServiceImplTest {
    @Mock
    private OrderDao orderDao;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addNewOrder() {
        // arrange
        Timestamp purchaseTime = Timestamp.valueOf("2021-01-02 00:00:00.0");
        Integer userId = 1;

        //act
        orderService.AddNewOrder(Codes.Code(userId), purchaseTime);

        verify(orderDao, times(1)).AddNewOrder(userId, purchaseTime);
    }

    @Test
    void testAddNewOrder() {
        // arrange
        Timestamp purchaseTime = Timestamp.valueOf("2021-01-02 00:00:00.0");
        Integer bookId = 1;
        Integer userId = 1;

        //act
        orderService.AddNewOrder(bookId, Codes.Code(userId), purchaseTime);

        verify(orderDao, times(1)).AddNewOrder(bookId, userId, purchaseTime);
    }

    @Test
    void getAllOrders() {
        // Create a list of orders
        List<TableOrderInAntTableEntity> orders = new ArrayList<>();
        orders.add(new TableOrderInAntTableEntity());
        orders.add(new TableOrderInAntTableEntity());

        // Mock the behavior of the orderDao.getAllOrders() method
        when(orderDao.getAllOrders(Codes.Decode(1))).thenReturn(orders);

        // Call the method under test
        List<TableOrderInAntTableEntity> result = orderService.getAllOrders(1);

        // Verify the result
        assertEquals(2, result.size());
        verify(orderDao, times(1)).getAllOrders(Codes.Decode(1));
    }

    @Test
    void testGetAllOrders() {
        // Create a list of orders
        List<TableOrderInAntTableEntity> orders = new ArrayList<>();
        orders.add(new TableOrderInAntTableEntity());
        orders.add(new TableOrderInAntTableEntity());

        // Mock the behavior of the orderDao.getAllOrders() method
        when(orderDao.getAllOrders()).thenReturn(orders);

        // Call the method under test
        List<TableOrderInAntTableEntity> result = orderService.getAllOrders();

        // Verify the result
        assertEquals(2, result.size());
        verify(orderDao, times(1)).getAllOrders();
    }

    @Test
    void searchOrder() {
        // Arrange
        List<TableOrderInAntTableEntity> orders = new ArrayList<>();
        TableOrderInAntTableEntity entity = new TableOrderInAntTableEntity();
        List<OrderlistEntity> orderlist = new ArrayList<>();
        OrderlistEntity orderlistEntity = new OrderlistEntity();
        orderlistEntity.setUserId(1);
        orderlist.add(orderlistEntity);
        entity.setOrderlist(orderlist);
        orders.add(entity);

        String prefix = "test";

        // Mock
        when(orderDao.SearchOrder(Codes.Decode(1), Timestamp.valueOf("2021-01-01 00:00:00.0"),
                Timestamp.valueOf("2021-01-03 00:00:00.0"), prefix)).thenReturn(orders);

        // Act
        List<TableOrderInAntTableEntity> result = orderService.SearchOrder(1,
                Timestamp.valueOf("2021-01-01 00:00:00.0"),
                Timestamp.valueOf("2021-01-03 00:00:00.0"), prefix);

        // Assert
        assertEquals(1, result.size());
        verify(orderDao, times(1)).SearchOrder(Codes.Decode(1), Timestamp.valueOf("2021-01-01 00:00:00.0"),
                Timestamp.valueOf("2021-01-03 00:00:00.0"), prefix);
    }

    @Test
    void testSearchOrder() {
        // Arrange
        List<TableOrderInAntTableEntity> orders = new ArrayList<>();
        TableOrderInAntTableEntity entity = new TableOrderInAntTableEntity();
        List<OrderlistEntity> orderlist = new ArrayList<>();
        OrderlistEntity orderlistEntity = new OrderlistEntity();
        orderlistEntity.setUserId(1);
        orderlist.add(orderlistEntity);
        entity.setOrderlist(orderlist);
        orders.add(entity);

        String prefix = "test";

        when(orderDao.SearchOrder(Timestamp.valueOf("2021-01-01 00:00:00.0"),
                Timestamp.valueOf("2021-01-03 00:00:00.0"), prefix)).thenReturn(orders);

        List<TableOrderInAntTableEntity> result = orderService.SearchOrder(
                Timestamp.valueOf("2021-01-01 00:00:00.0"),
                Timestamp.valueOf("2021-01-03 00:00:00.0"), prefix);

        assertEquals(1, result.size());
        verify(orderDao, times(1)).SearchOrder(Timestamp.valueOf("2021-01-01 00:00:00.0"),
                Timestamp.valueOf("2021-01-03 00:00:00.0"), prefix);
    }

    @Test
    void adminBookStatistics() {
        // Create a list of books
        List<BookInAntTableEntity> books = new ArrayList<>();
        BookEntity bookEntity = new BookEntity();
        BookInAntTableEntity entity = new BookInAntTableEntity(
                bookEntity.getId(),
                bookEntity.getName(),
                bookEntity.getAuthor(),
                bookEntity.getInventory(),
                bookEntity.getPrice(),
                bookEntity.getType(),
                bookEntity.getDescription(),
                bookEntity.getImage(),
                bookEntity.getIsbn(),
                1);
        books.add(entity);

        // Mock the behavior of the orderDao.AdminBookStatistics() method
        when(orderDao.AdminBookStatistics(Timestamp.valueOf("2021-01-01 00:00:00.0"),
                Timestamp.valueOf("2021-01-03 00:00:00.0"))).thenReturn(books);

        // Call the method under test
        List<BookInAntTableEntity> result = orderService.AdminBookStatistics(Timestamp.valueOf("2021-01-01 00:00:00.0"),
                Timestamp.valueOf("2021-01-03 00:00:00.0"));

        // Verify the result
        assertEquals(1, result.size());
        verify(orderDao, times(1)).AdminBookStatistics(Timestamp.valueOf("2021-01-01 00:00:00.0"),
                Timestamp.valueOf("2021-01-03 00:00:00.0"));
    }
}