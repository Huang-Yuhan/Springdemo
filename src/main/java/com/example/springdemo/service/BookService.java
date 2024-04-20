package com.example.springdemo.service;

import com.example.springdemo.entity.BookEntity;
import com.example.springdemo.entity.BookInAntTableEntity;

import java.sql.Timestamp;
import java.util.List;

public interface BookService {
    BookEntity findBookById(Integer id);

    List<BookEntity> getBooks();

    List<BookEntity> delBook(Integer id);

    void addBook(BookEntity bookEntity);

    List<BookEntity> searchBooks(String prefix);

    List<BookInAntTableEntity> searchBooksByDate(Integer UserId,Timestamp beginTime, Timestamp endTime);
}
