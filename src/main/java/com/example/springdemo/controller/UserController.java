package com.example.springdemo.controller;

import com.alibaba.fastjson.JSON;
import com.example.springdemo.dao.UserDao;
import com.example.springdemo.entity.UserEntity;
import com.example.springdemo.entity.UserPurchaseEntity;
import com.example.springdemo.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import java.io.FileDescriptor;
import java.sql.Time;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.sql.Timestamp;


@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private WebApplicationContext applicationContext;

    @RequestMapping("/Login")
    String  getUserId(@RequestBody Map<String,String> param)
    {
        String account=param.get("Account");
        String password= param.get("Password");
        System.out.println(this);
        return JSON.toJSONString(userService.check(account,password));
    }

    @RequestMapping("/GetUserName")
    String getUserName(@RequestBody Map<String,String> param)
    {
        String ans=userService.getName(Integer.parseInt(param.get("Id")));
        return JSON.toJSONString(ans);
    }
    @RequestMapping("/Register")
    String  Register(@RequestBody Map<String,String> param)
    {
        String account= param.get("Account");
        String password=param.get("Password");
        String email=param.get("Email");
        int result= userService.Register(account,password,email);
        if(result==0)return JSON.toJSONString(" ");
        return JSON.toJSONString(Integer.toString(result));
    }
    @RequestMapping("/CheckUserAuth")
    String CheckIsAuth(@RequestBody Map<String,String> param)
    {
        String request = param.get( "UserId" );
        Integer usertype = 0;
        try{
            usertype=userService.CheckIsAuth(Integer.parseInt(request));
        }catch (NumberFormatException e)
        {
            //e.printStackTrace();
            usertype=0;
        }
        String INVALD_USER="INVALID_USER";
        String USER="USER";
        String ADMIN="ADMIN";
        String BAN="BAN";
        String response=null;
        if(usertype.equals(0))response=INVALD_USER;
        if(usertype.equals(1))response=USER;
        if(usertype.equals(2))response=ADMIN;
        if(usertype.equals(3))response=BAN;
        System.out.println(response);
        return JSON.toJSONString(response);
    }

    @RequestMapping("/GetUsers")
    List<UserEntity> GetUsers()
    {
        return userService.GetUsers();
    }

    @RequestMapping("/Ban")
    List<UserEntity> BanUsers(@RequestBody Map<String,String> param)
    {
        return userService.BanUsers(Integer.parseInt(param.get("UserId")));
    }
    @RequestMapping("/Recover")
    List<UserEntity> RecoverUsers(@RequestBody Map<String,String> param)
    {
        return userService.RecoverUsers(Integer.parseInt(param.get("UserId")));
    }

    @RequestMapping("/Delete")
    List<UserEntity> deleteUser(@RequestBody Map<String,String> param)
    {
        return userService.deleteUser(Integer.parseInt(param.get("UserId")));
    }

    @RequestMapping("/GetUserById")
    UserEntity GetUserById
            (@RequestBody Map<String,String> param)
    {
        return userService.GetUserById(Integer.parseInt(param.get("UserId")));
    }

    @RequestMapping("/UpdateUserInfo")
    UserEntity UpdateUserInfo(@RequestBody Map<String,String> param)
    {
        UserEntity userEntity=new UserEntity();
        userEntity.setUserId(Integer.parseInt(param.get("UserId")));
        userEntity.setName(param.get("name"));
        userEntity.setAddress(param.get("address"));
        userEntity.setTel(param.get("tel"));
        userEntity.setNickname(param.get("nickname"));
        return userService.UpdateUserInfo(userEntity);
    }

    @RequestMapping("/AdminUserStatistics")
    List<UserPurchaseEntity> GetAdminUserStatistics(@RequestBody Map<String,String> param)
    {
        Timestamp beginTime=Timestamp.valueOf(param.get("beginTime"));
        Timestamp endTime=Timestamp.valueOf(param.get("endTime"));
        return userService.GetAdminUserStatistics(beginTime,endTime);
    }
    @RequestMapping("/Logout")
    void Logout()
    {
        System.out.println(this);
        //计时器返回值
    }
}
