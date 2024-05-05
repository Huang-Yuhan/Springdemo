package com.example.springdemo.daoimpl;

import com.example.springdemo.entity.BookEntity;
import com.example.springdemo.entity.BookInAntTableEntity;
import com.example.springdemo.repository.BookRepository;
import com.example.springdemo.repository.CartListRepository;
import com.example.springdemo.repository.OrderListRepository;
import com.example.springdemo.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional              //用于测试完后数据库回滚
class BookDaoImplTest {
    @Autowired
    private BookDaoImpl bookDaoImpl;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderListRepository orderListRepository;

    @Autowired
    private CartListRepository cartListRepository;

    static Stream<Integer> findOneSuccessProvider() {
        return Stream.of(6,7, 8,9,10);
    }

    @ParameterizedTest
    @MethodSource("findOneSuccessProvider")
    void findOneSuccess(Integer id) {
        assertNotNull(bookRepository.getBookEntityById(id));
        assertNotNull(bookDaoImpl.findOne(id));
        assertEquals(bookRepository.getBookEntityById(id), bookDaoImpl.findOne(id));
    }

    static Stream<Integer> findOneFailByNotExsistProvider() {
        return Stream.of(1,2,3,4,5);
    }

    @ParameterizedTest
    @MethodSource("findOneFailByNotExsistProvider")
    void findOneFailByNotExsist(Integer id) {
        assertNull(bookRepository.getBookEntityById(id));
        assertNull(bookDaoImpl.findOne(id));
    }

    @Test
    void getBooks() {
        assertEquals(bookRepository.findAll(), bookDaoImpl.getBooks());
    }

    static Stream<Integer> delBookSuccessProvider() {
        return Stream.of(6,7, 8,9,10);
    }

    void ClearAllBooksInOrder()
    {
        orderListRepository.deleteAll();
        orderRepository.deleteAll();
        cartListRepository.deleteAll();
    }

    @ParameterizedTest
    @MethodSource("delBookSuccessProvider")
    void delBookSuccess(Integer id) {
        //为了避免order中的外键约束，先删除order中的数据
        ClearAllBooksInOrder();
        assertNotNull(bookRepository.getBookEntityById(id));
        int preCount = bookRepository.findAll().size();
        bookDaoImpl.delBook(id);
        assertNull(bookRepository.getBookEntityById(id));
        assertEquals(preCount - 1, bookRepository.findAll().size());
    }

    @ParameterizedTest
    @MethodSource("delBookSuccessProvider")
    void delBookFailWithForeignKey(Integer id) {
        assertNotNull(bookRepository.getBookEntityById(id));
        int preCount = bookRepository.findAll().size();
        List<BookEntity> bookEntityList;
        assertThrowsExactly(org.springframework.dao.DataIntegrityViolationException.class, () -> {
            bookDaoImpl.delBook(id);
        });

    }

    static Stream<Integer> delBookFailByNotExsistProvider() {
        return Stream.of(1,2,3,4,5);
    }

    @ParameterizedTest
    @MethodSource("delBookFailByNotExsistProvider")
    void delBookFailByNotExsist(Integer id) {
        assertNull(bookRepository.getBookEntityById(id));
        int preCount = bookRepository.findAll().size();
        bookDaoImpl.delBook(id);
        assertNull(bookRepository.getBookEntityById(id));
        assertEquals(preCount, bookRepository.findAll().size());
    }

    static Stream<BookEntity> addBookProvider() {
        return Stream.of(
                new BookEntity("9787115428028", "Java编程思想", "计算机", "Bruce Eckel",  new BigDecimal("108.00"), "Java编程思想", 100, "https://img3.doubanio.com/view/subject/l/public/s27283837.jpg"),
                new BookEntity("1234567891012", "C++ Primer", "计算机", "Stanley B. Lippman",  new BigDecimal("108.00"), "C++ Primer", 100, "https://img3.doubanio.com/view/subject/l/public/s27283837.jpg")
        );
    }

    @ParameterizedTest
    @MethodSource("addBookProvider")
    void addBook(BookEntity bookEntity) {
        int preCount = bookRepository.findAll().size();
        bookDaoImpl.addBook(bookEntity);
        assertEquals(preCount + 1, bookRepository.findAll().size());
        //由于ID是自动生成的，所以无法直接比较两个对象是否相等
        var tmp = bookRepository.findAll().stream().filter(
                x -> x.getIsbn().compareTo(bookEntity.getIsbn())==0
        ).toList();
        assertNotNull(tmp);
        assertEquals(1, tmp.size());
        bookEntity.setId(tmp.get(0).getId());
        assertEquals(bookEntity, tmp.get(0));
    }

    static Stream<String> searchBooksProvider() {
        return Stream.of(
                "可怕",
                "历史",
                "Unity",
                "深入理解",
                "图灵计算机",
                "计算机"
        );
    }

    @ParameterizedTest
    @MethodSource("searchBooksProvider")
    void searchBooks(String prefix) {
        if(prefix==null)prefix="";
        else prefix=prefix.toLowerCase();
        List<BookEntity> bookEntityList=bookRepository.findAll();
        List<BookEntity> result=new ArrayList<>();
        for(int i=0;i<bookEntityList.size();i++){
            if(bookEntityList.get(i).getName().toLowerCase().contains(prefix)){
                result.add(bookEntityList.get(i));
            }
        }
        assertEquals(result, bookDaoImpl.searchBooks(prefix));
    }

    static Stream<String> searchBooksFailProvider() {
        return Stream.of(null, "", "2314");
    }

    @ParameterizedTest
    @MethodSource("searchBooksFailProvider")
    void searchBooksFail(String prefix) {
        if(prefix==null)prefix="";
        else prefix=prefix.toLowerCase();
        List<BookEntity> bookEntityList=bookRepository.findAll();
        List<BookEntity> result=new ArrayList<>();
        for(int i=0;i<bookEntityList.size();i++){
            if(bookEntityList.get(i).getName().toLowerCase().contains(prefix)){
                result.add(bookEntityList.get(i));
            }
        }
        assertEquals(result, bookDaoImpl.searchBooks(prefix));
    }

    static Stream searchBooksByDateEmpty() {
        return Stream.of(
                Arguments.of(1, "2021-01-01 00:00:00", "2021-12-31 23:59:59"),
                Arguments.of(6, "2024-01-01 00:00:00", "2021-12-31 23:59:59")
        );
    }

    static Stream searchBooksByDateProvider() {
        return Stream.of(
                Arguments.of(27, "2021-01-01 00:00:00", "2024-12-31 23:59:59")
        );
    }

    @ParameterizedTest
    @MethodSource("searchBooksByDateEmpty")
    void searchBooksByDateEmpty(Integer userId, String beginTime, String endTime) {
        List<BookInAntTableEntity> bookEntities = bookDaoImpl.searchBooksByDate(userId, Timestamp.valueOf(beginTime), Timestamp.valueOf(endTime));
        assertEquals(0, bookEntities.size());
    }

    @ParameterizedTest
    @MethodSource("searchBooksByDateProvider")
    void searchBooksByDate(Integer userId, String beginTime, String endTime) {
        List<BookInAntTableEntity> bookEntities = bookDaoImpl.searchBooksByDate(userId, Timestamp.valueOf(beginTime), Timestamp.valueOf(endTime));
        assertNotNull(bookEntities);
        assertTrue(bookEntities.size() > 0);
    }


}