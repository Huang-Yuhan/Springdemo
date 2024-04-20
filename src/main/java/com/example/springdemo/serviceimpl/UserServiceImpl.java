package com.example.springdemo.serviceimpl;

import com.example.springdemo.dao.UserDao;
import com.example.springdemo.daoimpl.UserDaoImpl;
import com.example.springdemo.entity.UserEntity;
import com.example.springdemo.entity.UserPurchaseEntity;
import com.example.springdemo.service.UserService;
import com.example.springdemo.utils.Codes;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDao userDao;
    @Override
    public String check(String account,String password) {
        Integer userid= userDao.check(account,password);
        if(userid.equals(0))return " ";
        else {
            return Integer.toString(Codes.Code(userid));
        }
    }

    @Override
    public String getName(Integer Id) {
        return userDao.UserName(Codes.Decode(Id));
    }

    @Override
    public Integer Register(String account, String password,String email)
    {
        return Codes.Code(userDao.Register(account,password,email));
    }

    @Override
    public Integer CheckIsAuth(Integer Id) {
        return userDao.CheckIsAuth(Codes.Decode(Id));
    }

    @Override
    public List<UserEntity> GetUsers() {
        List<UserEntity> users= userDao.GetUsers();
        for(int i=0;i<users.size();i++)
        {
            Integer real_Id=users.get(i).getUserId()*4563+12312;
            users.get(i).setUserId(real_Id);
        }
        return users;
    }

    @Override
    public List<UserEntity> BanUsers(Integer UserId) {
        List<UserEntity> users=userDao.BanUsers(Codes.Decode(UserId));
        for(int i=0;i<users.size();i++)
        {
            users.get(i).setUserId(Codes.Code(users.get(i).getUserId()));
        }
        return users;
    }

    @Override
    public List<UserEntity> RecoverUsers(Integer UserId) {
        List<UserEntity> users= userDao.RecoverUsers(Codes.Decode(UserId));
        for(int i=0;i<users.size();i++)
        {
            users.get(i).setUserId(Codes.Code(users.get(i).getUserId()));
        }
        return users;
    }

    @Override
    public List<UserEntity> deleteUser(Integer UserId) {
        List<UserEntity> users=userDao.deleteUser(Codes.Decode(UserId));
        for(int i=0;i<users.size();i++)
        {
            users.get(i).setUserId(Codes.Code(users.get(i).getUserId()));
        }
        return users;
    }

    @Override
    public UserEntity GetUserById(int userId) {
        UserEntity user = userDao.GetUserById(Codes.Decode(userId));
        user.setUserId(Codes.Code(user.getUserId()));
        return user;
    }

    @Override
    public UserEntity UpdateUserInfo(UserEntity userEntity) {
        userEntity.setUserId(Codes.Decode(userEntity.getUserId()));
        return userDao.UpdateUserInfo(userEntity);
    }

    @Override
    public List<UserPurchaseEntity> GetAdminUserStatistics(Timestamp beginTime, Timestamp endTime) {
        List<UserPurchaseEntity> lis=userDao.GetAdminUserStatistics(beginTime,endTime);
        for (int i=0;i<lis.size();i++)
        {
            lis.get(i).setId(Codes.Code(lis.get(i).getId()));
        }
        return lis;
    }

}
