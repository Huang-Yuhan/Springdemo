package com.example.springdemo.daoimpl;

import com.example.springdemo.dao.CartListDao;
import com.example.springdemo.entity.BookEntity;
import com.example.springdemo.entity.CartlistEntity;
import com.example.springdemo.repository.BookRepository;
import com.example.springdemo.repository.CartListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Repository
public class CartListDaoImpl implements CartListDao {
    @Autowired
    private CartListRepository cartlist;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<CartlistEntity> Add(Integer bookId,Integer UserId)
    {
        List<CartlistEntity> lis=cartlist.getCartlistEntitiesByUserId(UserId);
        if(bookId.equals(0))return lis;
        AtomicBoolean flag= new AtomicBoolean(false);
        lis.stream().filter(o->o.getBook().getId()==bookId).forEach(
                f->
                {
                    flag.set(true);
                    int count=f.getCount();
                    f.setCount(count+1);
                    f.setTotalprice(f.getTotalprice()/count*(count+1));
                    cartlist.save(f);
                }
        );

        if(!flag.get())//需要添加
        {
            BookEntity book = bookRepository.getBookEntityById(bookId);
            CartlistEntity cartlistEntity=new CartlistEntity();
            cartlistEntity.setTotalprice(book.getPrice().doubleValue());
            cartlistEntity.setUserId(UserId);
            cartlistEntity.setCount(1);
            cartlistEntity.setBook(book);
            cartlist.save(cartlistEntity);

            lis.add(cartlistEntity);
        }

        return lis;
    }

    @Override
    public List<CartlistEntity> getCartLists(Integer UserId){
        return cartlist.getCartlistEntitiesByUserId(UserId);
    }


    @Override
    public List<CartlistEntity> DelCart(Integer bookId,Integer UserId) {
        List<CartlistEntity> lis=cartlist.getCartlistEntitiesByUserId(UserId);
        lis.stream().filter(o->o.getBook().getId()==bookId
        ).forEach(f->
                {
                    int count=f.getCount();
                    f.setCount(count-1);
                    f.setTotalprice(f.getTotalprice()/count*(count-1));
                    if(count>1)
                    {
                        cartlist.save(f);
                    }
                    else
                    {
                        cartlist.deleteById(f.getCartlistid());
                    }
                }
        );

        lis.removeIf(o->o.getCount()==0);
        return lis;
    }
}
