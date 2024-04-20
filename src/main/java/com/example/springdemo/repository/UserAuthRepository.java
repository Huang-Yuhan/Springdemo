package com.example.springdemo.repository;

import com.example.springdemo.entity.UserAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAuthRepository extends JpaRepository<UserAuthEntity,Integer> {
    UserAuthEntity getUserAuthEntityByUsername(String UserName);
    List<UserAuthEntity> getUserAuthEntitiesByUsername(String UserName);

    UserAuthEntity getUserAuthEntityByUserId(Integer UserId);
}
