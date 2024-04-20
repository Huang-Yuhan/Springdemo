package com.example.springdemo.serviceimpl;

import com.example.springdemo.dao.CartListDao;
import com.example.springdemo.entity.CartlistEntity;
import com.example.springdemo.service.CartListService;
import com.example.springdemo.utils.Codes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CartListServiceImpl implements CartListService {
    @Autowired
    private CartListDao cartListDao;

    @Override
    public List<CartlistEntity> Add(Integer bookId,Integer UserId)
    {
        List<CartlistEntity> lis=cartListDao.Add(bookId,Codes.Decode(UserId));
        lis.forEach(o->o.setUserId(Codes.Code(o.getUserId())));
        return lis;
    }

    @Override
    public List<CartlistEntity> getCartLists(Integer UserId)
    {
        List<CartlistEntity> lis= cartListDao.getCartLists(Codes.Decode(UserId));
        for(int i=0;i<lis.size();i++)
        {
            lis.get(i).setUserId(Codes.Code(lis.get(i).getUserId()));
        }
        return lis;
    }

    @Override
    public List<CartlistEntity> DelCart(Integer bookId,Integer UserId) {
        List<CartlistEntity> lis= cartListDao.DelCart(bookId,Codes.Decode(UserId));
        for(int i=0;i<lis.size();i++)
        {
            lis.get(i).setUserId(Codes.Code(lis.get(i).getUserId()));
        }
        return lis;
    }
}
