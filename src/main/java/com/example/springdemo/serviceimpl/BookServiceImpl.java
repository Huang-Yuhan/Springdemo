package com.example.springdemo.serviceimpl;

import com.example.springdemo.dao.BookDao;
import com.example.springdemo.entity.BookEntity;
import com.example.springdemo.entity.BookInAntTableEntity;
import com.example.springdemo.service.BookService;
import com.example.springdemo.utils.Codes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDao bookDao;

    @Override
    public BookEntity findBookById(Integer id){return bookDao.findOne(id);}

    @Override
    public List<BookEntity> getBooks(){return bookDao.getBooks();}

    @Override
    public List<BookEntity> delBook(Integer id) {
        return bookDao.delBook(id);
    }

    @Override
    public void addBook(BookEntity bookEntity) {
        bookDao.addBook(bookEntity);
    }

    @Override
    public List<BookEntity> searchBooks(String prefix) {
        return bookDao.searchBooks(prefix);
    }

    @Override
    public List<BookInAntTableEntity> searchBooksByDate(Integer UserId,Timestamp beginTime, Timestamp endTime) {
        return bookDao.searchBooksByDate(Codes.Decode(UserId),beginTime,endTime);
    }
}
