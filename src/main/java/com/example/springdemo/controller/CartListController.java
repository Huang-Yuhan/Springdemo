package com.example.springdemo.controller;

import com.example.springdemo.entity.CartlistEntity;
import com.example.springdemo.service.CartListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class CartListController {
    @Autowired
    private CartListService cartListService;

    @RequestMapping("/getCartLists")
    public List<CartlistEntity> getCartLists(@RequestBody Map<String,String> param)
    {
        return cartListService.getCartLists(Integer.parseInt(param.get("UserId")));
    }

    @RequestMapping("/AddCartLists")
    public List<CartlistEntity> AddCartList(@RequestBody Map<String,String> param)
    {
        int bookid=(Integer.parseInt(param.get("bookId")));
        int userId=(Integer.parseInt(param.get("UserId")));
        return cartListService.Add(bookid,userId);
    }

    @RequestMapping("/DelCartLists")
    public List<CartlistEntity> DelCartLists(@RequestBody Map<String,String> param)
    {
        return cartListService.DelCart(
                Integer.parseInt(param.get("bookId")),
                Integer.parseInt(param.get("UserId"))
        );
    }
}
