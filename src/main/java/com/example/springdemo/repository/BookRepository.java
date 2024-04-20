package com.example.springdemo.repository;

import com.example.springdemo.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Book;
import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity,Integer> {
    BookEntity getBookEntityById(Integer Id);
}
