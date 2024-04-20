package com.example.springdemo.repository;

import com.example.springdemo.entity.OrderlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderListRepository extends JpaRepository<OrderlistEntity,Integer> {
    List<OrderlistEntity> getAllByUserId(Integer UserId);

    void deleteByUserId(Integer id);

}
