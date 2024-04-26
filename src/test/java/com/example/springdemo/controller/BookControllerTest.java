package com.example.springdemo.controller;

import com.example.springdemo.entity.BookEntity;
import com.example.springdemo.entity.BookInAntTableEntity;
import com.example.springdemo.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {
    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @Test
    void getBooks() {
        BookEntity book = new BookEntity();
        book.setName("Sample Book");
        List<BookEntity> expectedBooks = Arrays.asList(book);
        when(bookService.getBooks()).thenReturn(expectedBooks);

        List<BookEntity> result = bookController.getBooks(new HashMap<>());
        assertEquals(expectedBooks, result);
        verify(bookService).getBooks();
    }

    @Test
    void test1() {
        BookEntity book = new BookEntity();
        book.setName("Test Book");
        List<BookEntity> expectedBooks = Arrays.asList(book);
        when(bookService.getBooks()).thenReturn(expectedBooks);

        List<BookEntity> result = bookController.test();
        assertEquals(expectedBooks, result);
        verify(bookService).getBooks();
    }

    @Test
    void getBook() {
        BookEntity expectedBook = new BookEntity();
        expectedBook.setId(1);
        when(bookService.findBookById(1)).thenReturn(expectedBook);

        BookEntity result = bookController.getBook(1);
        assertEquals(expectedBook, result);
        verify(bookService).findBookById(1);
    }

    @Test
    void delBooks() {
        // 设置模拟数据
        int bookId = 1;
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(bookId));
        List<BookEntity> expected = new ArrayList<>();

        // 配置Mock行为
        when(bookService.delBook(bookId)).thenReturn(expected);
        when(bookService.getBooks()).thenReturn(expected);

        // 执行测试方法
        List<BookEntity> result = bookController.delBooks(params);

        // 验证行为和结果
        verify(bookService).delBook(bookId);
        verify(bookService).getBooks();
        assertEquals(expected, result);
    }

    @Test
    void addBook() {
        BookEntity newBook = new BookEntity();
        newBook.setId(1);
        newBook.setName("New Book");
        List<BookEntity> expectedBooks = Arrays.asList(newBook);
        when(bookService.getBooks()).thenReturn(expectedBooks);

        List<BookEntity> result = bookController.addBook(new HashMap<>() {{
            put("id", "1");
            put("name", "New Book");
            put("author", "Author");
            put("price", "100");
            put("type", "Fiction");
            put("description", "Description");
            put("inventory", "5");
            put("image", "image.jpg");
            put("isbn", "123456789");
        }});
        assertEquals(expectedBooks, result);
        verify(bookService).addBook(any(BookEntity.class));
    }

    @Test
    void searchBooks() {
        BookEntity book = new BookEntity();
        book.setName("Search Book");
        List<BookEntity> expectedBooks = Arrays.asList(book);
        when(bookService.searchBooks("prefix")).thenReturn(expectedBooks);

        List<BookEntity> result = bookController.searchBooks(new HashMap<>() {{
            put("prefix", "prefix");
        }});
        assertEquals(expectedBooks, result);
        verify(bookService).searchBooks("prefix");
    }

    @Test
    void searchBooksByDate() {
        // 设置模拟数据
        Timestamp beginTime = Timestamp.valueOf("2023-01-01 00:00:00");
        Timestamp endTime = Timestamp.valueOf("2023-01-02 00:00:00");
        int userId = 1;
        Map<String, String> params = new HashMap<>();
        params.put("beginTime", beginTime.toString());
        params.put("endTime", endTime.toString());
        params.put("UserId", String.valueOf(userId));
        List<BookInAntTableEntity> expected = new ArrayList<>();

        // 配置Mock行为
        when(bookService.searchBooksByDate(userId, beginTime, endTime)).thenReturn(expected);

        // 执行测试方法
        List<BookInAntTableEntity> result = bookController.searchBooksByDate(params);

        // 验证行为和结果
        verify(bookService).searchBooksByDate(userId, beginTime, endTime);
        assertEquals(expected, result);
    }
}