package com.example.springdemo.dao;

import com.example.springdemo.entity.BookInAntTableEntity;
import com.example.springdemo.entity.OrderlistEntity;
import com.example.springdemo.entity.TableOrderEntity;
import com.example.springdemo.entity.TableOrderInAntTableEntity;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

public interface OrderDao {
    void AddNewOrder(Integer userId, Timestamp purchaseTime);

    void AddNewOrder(Integer bookId, Integer userId,Timestamp purchaseTime);

    List<TableOrderInAntTableEntity> getAllOrders(Integer UserId);

    List<TableOrderInAntTableEntity> getAllOrders();

    List<TableOrderInAntTableEntity> SearchOrder(Integer userId, Timestamp beginTime, Timestamp endTime, String prefix);

    List<TableOrderInAntTableEntity> SearchOrder(Timestamp beginTime, Timestamp endTime, String prefix);

    List<BookInAntTableEntity> AdminBookStatistics(Timestamp beginTime, Timestamp endTime);
}
