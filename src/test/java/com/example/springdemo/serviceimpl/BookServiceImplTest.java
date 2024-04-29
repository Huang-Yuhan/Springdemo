package com.example.springdemo.serviceimpl;

import com.example.springdemo.dao.BookDao;
import com.example.springdemo.entity.BookEntity;
import com.example.springdemo.entity.BookInAntTableEntity;
import com.example.springdemo.serviceimpl.BookServiceImpl;
import com.example.springdemo.utils.Codes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BookServiceImplTest {

    @Mock
    private BookDao bookDao;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindBookById() {
        // arrange
        BookEntity expected = new BookEntity();
        expected.setId(1);

        // mock
        when(bookDao.findOne(1)).thenReturn(expected);

        // act
        BookEntity result = bookService.findBookById(1);

        // assert
        verify(bookDao, times(1)).findOne(1);
        assertEquals(expected, result);
    }

    @Test
    void getBooks() {
        // arrange
        BookEntity book = new BookEntity();
        List<BookEntity> expectedBooks = Arrays.asList(book);

        // mock
        when(bookDao.getBooks()).thenReturn(expectedBooks);

        // act
        List<BookEntity> result = bookService.getBooks();

        // assert
        assertEquals(expectedBooks, result);
        verify(bookDao, times(1)).getBooks();
    }

    @Test
    void delBook() {
        // arrange
        int bookId = 1;
        List<BookEntity> expected = new ArrayList<>();

        // mock
        when(bookDao.delBook(bookId)).thenReturn(expected);

        // act
        List<BookEntity> result = bookService.delBook(bookId);

        // assert
        verify(bookDao, times(1)).delBook(bookId);
        assertEquals(expected, result);
    }

    @Test
    void addBook() {
        // arrange
        BookEntity book = new BookEntity();

        // act
        bookService.addBook(book);

        // assert
        verify(bookDao, times(1)).addBook(book);
    }

    @Test
    void searchBooks() {
        // arrange
        String prefix = "test";
        BookEntity book = new BookEntity();
        List<BookEntity> expectedBooks = Arrays.asList(book);

        // mock
        when(bookDao.searchBooks(prefix)).thenReturn(expectedBooks);

        // act
        List<BookEntity> result = bookService.searchBooks(prefix);

        // assert
        assertEquals(expectedBooks, result);
        verify(bookDao, times(1)).searchBooks(prefix);
    }

    @Test
    void searchBooksByDate() {
        // arrange
        int userId = 1;
        Timestamp beginTime = Timestamp.valueOf("2021-01-01 00:00:00.0");
        Timestamp endTime = Timestamp.valueOf("2022-01-01 00:00:00.0");
        List< BookInAntTableEntity> expectedBooks = new ArrayList<>();

        // mock
        when(bookDao.searchBooksByDate(userId, beginTime, endTime)).thenReturn(expectedBooks);

        // act
        List<BookInAntTableEntity> result = bookService.searchBooksByDate(Codes.Code(userId), beginTime, endTime);

        // assert
        assertEquals(expectedBooks, result);
        verify(bookDao, times(1)).searchBooksByDate(userId, beginTime, endTime);
    }

    // 其他测试方法...
}