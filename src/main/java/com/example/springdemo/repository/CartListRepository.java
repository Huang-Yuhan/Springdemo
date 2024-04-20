package com.example.springdemo.repository;

import com.example.springdemo.entity.CartlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Book;
import java.util.List;

public interface CartListRepository extends JpaRepository<CartlistEntity,Integer> {


    //CartlistEntity getCartlistEntityByBookEntity_id(Integer id);

    List<CartlistEntity> getCartlistEntitiesByUserId(Integer id);
    
    void deleteByUserId(Integer id);

}
