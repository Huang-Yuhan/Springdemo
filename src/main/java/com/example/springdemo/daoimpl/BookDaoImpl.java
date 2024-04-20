package com.example.springdemo.daoimpl;

import com.example.springdemo.dao.BookDao;
import com.example.springdemo.entity.BookEntity;
import com.example.springdemo.entity.BookInAntTableEntity;
import com.example.springdemo.entity.OrderlistEntity;
import com.example.springdemo.entity.TableOrderEntity;
import com.example.springdemo.repository.BookRepository;
import com.example.springdemo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoImpl implements BookDao {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public BookEntity findOne(Integer id){return bookRepository.getBookEntityById(id);}

    @Override
    public List<BookEntity> getBooks(){return bookRepository.findAll();}

    @Override
    public List<BookEntity> delBook(Integer id) {
        bookRepository.deleteById(id);
        return bookRepository.findAll();
    }

    @Override
    public void addBook(BookEntity bookEntity) {
        bookRepository.save(bookEntity);
    }

    @Override
    public List<BookEntity> searchBooks(String prefix) {
        if(prefix==null)prefix="";
        else prefix=prefix.toLowerCase();
        List<BookEntity> bookEntityList=bookRepository.findAll();
        List<BookEntity> result=new ArrayList<>();
        for(int i=0;i<bookEntityList.size();i++){
            if(bookEntityList.get(i).getName().toLowerCase().contains(prefix)){
                result.add(bookEntityList.get(i));
            }
        }
        return result;
    }

    @Override
    public List<BookInAntTableEntity> searchBooksByDate(Integer UserId,Timestamp beginTime, Timestamp endTime) {
        List<TableOrderEntity> orderEntities = orderRepository.getAllByUserId(UserId);
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < orderEntities.size(); i++) {
            if (orderEntities.get(i).getPurchaseTime().after(beginTime) && orderEntities.get(i).getPurchaseTime().before(endTime)) {
                List<OrderlistEntity> lis=orderEntities.get(i).getOrderlist();
                for(int j=0;j<lis.size();j++){
                    if(!map.containsKey(lis.get(j).getBook().getId()))map.put(lis.get(j).getBook().getId(),0);
                    map.put(
                            lis.get(j).getBook().getId(),
                            lis.get(j).getBookCount()+map.get(lis.get(j).getBook().getId())
                    );
                }
            }
        }
        List<BookInAntTableEntity> bookEntities = new ArrayList<>();

        for(Map.Entry<Integer,Integer> entry:map.entrySet()){
            BookEntity bookEntity = bookRepository.getBookEntityById(entry.getKey());
            bookEntities.add(new BookInAntTableEntity(
                    bookEntity.getId(),
                    bookEntity.getName(),
                    bookEntity.getAuthor(),
                    bookEntity.getInventory(),
                    bookEntity.getPrice(),
                    bookEntity.getType(),
                    bookEntity.getDescription(),
                    bookEntity.getImage(),
                    bookEntity.getIsbn(),
                    entry.getValue()
            ));
        }

        bookEntities.sort((o1, o2) -> o2.getCount()-o1.getCount());

        return bookEntities;
    }



}
