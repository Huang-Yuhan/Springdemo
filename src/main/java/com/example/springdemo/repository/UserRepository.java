package com.example.springdemo.repository;

import com.example.springdemo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    UserEntity getUserEntityByUserId(Integer Id);
}
