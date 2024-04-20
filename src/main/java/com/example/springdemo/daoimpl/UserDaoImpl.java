package com.example.springdemo.daoimpl;

import com.example.springdemo.dao.UserDao;
import com.example.springdemo.entity.TableOrderEntity;
import com.example.springdemo.entity.UserAuthEntity;
import com.example.springdemo.entity.UserEntity;
import com.example.springdemo.entity.UserPurchaseEntity;
import com.example.springdemo.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    @Autowired
    private CartListRepository cartListRepository;

    @Autowired
    private OrderListRepository orderListRepository;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuthRepository userAuthRepository;

    @Override
    public Integer check(String account, String password) {
        UserAuthEntity user=userAuthRepository.getUserAuthEntityByUsername(account);
        if(user==null||!user.getPassword().equals(password))return 0;
        else return user.getUserId();
    }

    @Override
    public String UserName(Integer Id) {
        UserEntity user=userRepository.getUserEntityByUserId(Id);
        if(user==null)return "";
        else return user.getName();
    }

    @Override
    public Integer Register(String Account, String Password,String Email) {
        if(userAuthRepository.getUserAuthEntitiesByUsername(Account).isEmpty())
        {
            //the account is usable

            UserEntity newUser=new UserEntity("new user","allow","user");
            newUser.setAddress(Email);
            userRepository.save(newUser);

            UserAuthEntity newUserAuth = new UserAuthEntity(newUser.getUserId(),Account,Password);
            userAuthRepository.save(newUserAuth);

            return newUser.getUserId();
        }else return 0;
    }

    @Override
    public Integer CheckIsAuth(Integer UserId) {
        UserEntity userEntity=userRepository.getUserEntityByUserId(UserId);
        if(userEntity==null)return 0;
        if(userEntity.getState().equals("ban"))return 3;
        if(userEntity.getType().equals("user"))return 1;
        else return  2;
    }

    @Override
    public List<UserEntity> GetUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<UserEntity> BanUsers(Integer UserId) {
        List<UserEntity> lis=userRepository.findAll();
        for(int i=0;i<lis.size();i++)
        {
            if(lis.get(i).getUserId()==UserId)
            {
                if(lis.get(i).getType().equals("admin"))continue;
                lis.get(i).setState("ban");
                userRepository.save(lis.get(i));
            }
        }
        return lis;
    }

    @Override
    public List<UserEntity> RecoverUsers(Integer UserId) {
        List<UserEntity> lis=userRepository.findAll();
        for(int i=0;i<lis.size();i++)
        {
            if(lis.get(i).getUserId()==UserId)
            {
                lis.get(i).setState("allow");
                userRepository.save(lis.get(i));
            }
        }
        return lis;
    }

    @Override
    public List<UserEntity> deleteUser(Integer UserId) {
        List<UserEntity> lis=userRepository.findAll();
        int preCount,count;
        preCount=lis.size();
        lis.removeIf(i->i.getUserId()==UserId&&i.getType().equals("admin")==false);
        count=lis.size();
        if(preCount!=count) {
            cartListRepository.deleteByUserId(UserId);
            orderListRepository.deleteByUserId(UserId);
            orderRepository.deleteByUserId(UserId);
            userAuthRepository.deleteById(UserId);
            userRepository.deleteById(UserId);
        }
        return lis;
    }

    @Override
    public UserEntity GetUserById(Integer UserId) {
        return userRepository.getUserEntityByUserId(UserId);
    }

    @Override
    public UserEntity UpdateUserInfo(UserEntity userEntity) {
        UserEntity user=userRepository.getUserEntityByUserId(userEntity.getUserId());
        if(user==null)return null;
        user.setAddress(userEntity.getAddress());
        user.setName(userEntity.getName());
        user.setTel(userEntity.getTel());
        user.setNickname(userEntity.getNickname());
        userRepository.save(user);
        return user;
    }

    @Override
    public List<UserPurchaseEntity> GetAdminUserStatistics(Timestamp beginTime, Timestamp endTime) {
        List<UserPurchaseEntity> res=new ArrayList<>();

        List<TableOrderEntity> lis=orderRepository.findAll();
        Map<Integer, BigDecimal> map = new HashMap<>();
        for(int i=0;i<lis.size();i++)
        {
            if(lis.get(i).getPurchaseTime().after(beginTime)&&lis.get(i).getPurchaseTime().before(endTime)) {
                if (map.containsKey(lis.get(i).getUserId())) {
                    map.put(
                            lis.get(i).getUserId(),
                            map.get(lis.get(i).getUserId()).add(BigDecimal.valueOf(lis.get(i).getTotalPrice()))
                    );
                } else {
                    map.put(lis.get(i).getUserId(), BigDecimal.valueOf(lis.get(i).getTotalPrice()));
                }
            }
        }

        for (Map.Entry<Integer, BigDecimal> entry : map.entrySet()) {
            UserEntity user=userRepository.getUserEntityByUserId(entry.getKey());
            if(user==null)continue;
            UserPurchaseEntity userPurchaseEntity=new UserPurchaseEntity(
                    user.getUserId(),
                    user.getName(),
                    entry.getValue()
            );
            res.add(userPurchaseEntity);
        }
        res.sort(
                new Comparator<>() {
                    @Override
                    public int compare(UserPurchaseEntity o1, UserPurchaseEntity o2) {
                        return o2.getPurchasePrice().compareTo(o1.getPurchasePrice());
                    }
                }
        );
        return res;
    }
}
