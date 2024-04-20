package com.example.springdemo.controller;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.springdemo.entity.BookEntity;
import com.example.springdemo.entity.BookInAntTableEntity;
import com.example.springdemo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    @RequestMapping("/getBooks")
    public List<BookEntity> getBooks(@RequestBody Map<String,String> params)
    {
        return bookService.getBooks();
    }

    @RequestMapping("/test")
    public List<BookEntity> test()
    {
        return bookService.getBooks();
    }
    @RequestMapping("/getBook")
    public BookEntity getBook(@RequestParam("id") Integer id){return bookService.findBookById(id);}

    @RequestMapping("/delBook")
    public List<BookEntity> delBooks(@RequestBody Map<String,String> params)
    {
        bookService.delBook(Integer.parseInt(params.get("id")));
        return bookService.getBooks();
    }

    @RequestMapping("/addBook")
    public List<BookEntity> addBook(@RequestBody Map<String,String> params)
    {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setName(params.get("name"));
        bookEntity.setAuthor(params.get("author"));
        bookEntity.setPrice(BigDecimal.valueOf(Integer.parseInt(params.get("price"))));
        bookEntity.setType(params.get("type"));
        bookEntity.setDescription(params.get("description"));
        bookEntity.setInventory(Integer.parseInt(params.get("inventory")));
        bookEntity.setImage(params.get("image"));
        bookEntity.setIsbn(params.get("isbn"));
        bookEntity.setId(Integer.parseInt(params.get("id")));
        bookService.addBook(bookEntity);
        return bookService.getBooks();
    }

    @RequestMapping("/searchBooks")
    public List<BookEntity> searchBooks(@RequestBody Map<String,String> params)
    {
        return bookService.searchBooks(params.get("prefix"));
    }

    @RequestMapping("/searchBooksByDate")
    public List<BookInAntTableEntity> searchBooksByDate(@RequestBody Map<String,String> params)
    {
        Timestamp beginTime=Timestamp.valueOf(params.get("beginTime"));
        Timestamp endTime=Timestamp.valueOf(params.get("endTime"));
        Integer UserId=Integer.parseInt(params.get("UserId"));
        return bookService.searchBooksByDate(UserId,beginTime,endTime);
    }
}
