package com.example.springdemo.controller;

import com.example.springdemo.entity.BookInAntTableEntity;
import com.example.springdemo.entity.TableOrderInAntTableEntity;
import com.example.springdemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/MyOrder")
    public List<TableOrderInAntTableEntity>
    getOrder(@RequestBody Map<String,String> param)
    {
        return orderService.getAllOrders(Integer.parseInt(param.get("UserId")));
    }

    @RequestMapping("/AddOrder")
    public void AddOrder(@RequestBody Map<String,String> param)
    {
        Integer bookId = param.get("bookId") == null ? null : Integer.parseInt(param.get("bookId"));
        Integer userId = param.get("UserId") == null ? null : Integer.parseInt(param.get("UserId"));
        Timestamp purchaseTime = param.get("purchaseTime") == null ? null : Timestamp.valueOf(param.get("purchaseTime"));
        System.out.println("\n\n\nbackend receive order!!\n\n\n\n");
        orderService.AddNewOrder(bookId,userId,purchaseTime);
        //kafkaTemplate.send("takeOrder",bookId.toString()+","+userId.toString()+","+purchaseTime.toString());
    }

    @RequestMapping("/CartToOrder")
    public void ToOrder(@RequestBody Map<String,String> param)
    {
        Integer userId = param.get("UserId") == null ? null : Integer.parseInt(param.get("UserId"));
        Timestamp purchaseTime = param.get("purchaseTime") == null ? null : Timestamp.valueOf(param.get("purchaseTime"));
        System.out.println("\n\n\nbackend receive cart to order\n\n\n\n");
        orderService.AddNewOrder(userId,purchaseTime);
    }

    @RequestMapping("/SearchOrderUser")
    public List<TableOrderInAntTableEntity> SearchOrder(@RequestBody Map<String,String> param)
    {
        Integer UserId = param.get("UserId") == null ? null : Integer.parseInt(param.get("UserId"));
        Timestamp beginTime = param.get("beginTime") == null ? null : Timestamp.valueOf(param.get("beginTime"));
        Timestamp endTime = param.get("endTime") == null ? null : Timestamp.valueOf(param.get("endTime"));
        String prefix = param.get("prefix") == null ? null : param.get("prefix");
        return orderService.SearchOrder(UserId,beginTime,endTime,prefix);
    }

    @RequestMapping("/GetAllOrder")
    public List<TableOrderInAntTableEntity> GetAllOrder()
    {
        return orderService.getAllOrders();
    }

    @RequestMapping("/SearchOrderAdmin")
    public List<TableOrderInAntTableEntity> SearchOrderAdmin(@RequestBody Map<String,String> param)
    {
        Timestamp beginTime = param.get("beginTime") == null ? null : Timestamp.valueOf(param.get("beginTime"));
        Timestamp endTime = param.get("endTime") == null ? null : Timestamp.valueOf(param.get("endTime"));
        String prefix = param.get("prefix") == null ? null : param.get("prefix");
        return orderService.SearchOrder(beginTime,endTime,prefix);
    }

    @RequestMapping("/AdminBookStatistics")
    public List<BookInAntTableEntity> AdminBookStatistics(@RequestBody Map<String,String> param)
    {
        Timestamp beginTime = param.get("beginTime") == null ? null : Timestamp.valueOf(param.get("beginTime"));
        Timestamp endTime = param.get("endTime") == null ? null : Timestamp.valueOf(param.get("endTime"));
        return orderService.AdminBookStatistics(beginTime,endTime);
    }
}
