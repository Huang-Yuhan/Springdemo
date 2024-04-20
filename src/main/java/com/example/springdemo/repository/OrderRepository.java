package com.example.springdemo.repository;

import com.example.springdemo.entity.TableOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<TableOrderEntity,Integer> {
    void deleteByUserId(Integer id);

    List<TableOrderEntity> getAllByUserId(Integer userId);
}
