package com.example.springdemo.service;

import com.example.springdemo.entity.UserEntity;
import com.example.springdemo.entity.UserPurchaseEntity;
import org.apache.catalina.User;

import java.sql.Timestamp;
import java.util.List;

public interface UserService {
    String check(String account,String password);      //no such user return " " else return some code
    String getName(Integer Id);

    Integer Register(String account,String password,String Email);

    Integer CheckIsAuth(Integer Id);

    List<UserEntity> GetUsers();

    List<UserEntity> BanUsers(Integer UserId);

    List<UserEntity> RecoverUsers(Integer UserId);

    List<UserEntity> deleteUser(Integer UserId);

    UserEntity GetUserById(int userId);

    UserEntity UpdateUserInfo(UserEntity userEntity);

    List<UserPurchaseEntity> GetAdminUserStatistics(Timestamp beginTime, Timestamp endTime);
}
