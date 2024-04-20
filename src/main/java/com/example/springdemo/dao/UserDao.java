package com.example.springdemo.dao;

import com.example.springdemo.entity.UserEntity;
import com.example.springdemo.entity.UserPurchaseEntity;

import java.sql.Timestamp;
import java.util.List;

public interface UserDao {
    Integer check(String account,String password);

    String UserName(Integer Id);

    Integer Register(String Account,String Password,String Email);

    Integer CheckIsAuth(Integer UserId);

    List<UserEntity> GetUsers();

    List<UserEntity> BanUsers(Integer UserId);

    List<UserEntity> RecoverUsers(Integer UserId);

    List<UserEntity> deleteUser(Integer UserId);

    UserEntity GetUserById(Integer UserId);

    UserEntity UpdateUserInfo(UserEntity userEntity);

    List<UserPurchaseEntity> GetAdminUserStatistics(Timestamp beginTime, Timestamp endTime);
}
