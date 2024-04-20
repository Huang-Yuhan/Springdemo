package com.example.springdemo.serviceimpl;

import com.example.springdemo.dao.OrderDao;
import com.example.springdemo.entity.BookInAntTableEntity;
import com.example.springdemo.entity.OrderlistEntity;
import com.example.springdemo.entity.TableOrderEntity;
import com.example.springdemo.entity.TableOrderInAntTableEntity;
import com.example.springdemo.service.OrderService;
import com.example.springdemo.utils.Codes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public void AddNewOrder(Integer userId, Timestamp purchaseTime) {
        orderDao.AddNewOrder(Codes.Decode(userId),purchaseTime);
    }

    @Override
    public void AddNewOrder(Integer bookId, Integer userId ,Timestamp purchaseTime){
        orderDao.AddNewOrder(bookId,Codes.Decode(userId),purchaseTime);
    }

    @Override
    public List<TableOrderInAntTableEntity> getAllOrders(Integer UserId) {
        List<TableOrderInAntTableEntity> lis= orderDao.getAllOrders(Codes.Decode(UserId));
        return lis;
    }

    @Override
    public List<TableOrderInAntTableEntity> getAllOrders() {
        return orderDao.getAllOrders();
    }

    @Override
    public List<TableOrderInAntTableEntity> SearchOrder(Integer userId, Timestamp beginTime, Timestamp endTime, String prefix) {
        List<TableOrderInAntTableEntity> lis= orderDao.SearchOrder(Codes.Decode(userId),beginTime,endTime,prefix);
        for(int i=0;i<lis.size();i++)
        {
            List<OrderlistEntity> tmp=lis.get(i).getOrderlist();
            for(int j=0;j<tmp.size();j++)
            {
                tmp.get(j).setUserId(Codes.Code(tmp.get(j).getUserId()));
            }
        }
        return lis;
    }

    @Override
    public List<TableOrderInAntTableEntity> SearchOrder(Timestamp beginTime, Timestamp endTime, String prefix) {
        List<TableOrderInAntTableEntity> lis= orderDao.SearchOrder(beginTime,endTime,prefix);
        for(int i=0;i<lis.size();i++)
        {
            List<OrderlistEntity> tmp=lis.get(i).getOrderlist();
            for(int j=0;j<tmp.size();j++)
            {
                tmp.get(j).setUserId(Codes.Code(tmp.get(j).getUserId()));
            }
        }
        return lis;
    }

    @Override
    public List<BookInAntTableEntity> AdminBookStatistics(Timestamp beginTime, Timestamp endTime) {
        return orderDao.AdminBookStatistics(beginTime,endTime);
    }
}
