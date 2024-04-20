package com.example.springdemo.daoimpl;

import com.example.springdemo.dao.OrderDao;
import com.example.springdemo.entity.*;
import com.example.springdemo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

@Repository
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderListRepository orderListRepository;

    @Autowired
    private CartListRepository cartListRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void AddNewOrder(Integer userId, Timestamp purchaseTime) {

        List<CartlistEntity> lis=cartListRepository.getCartlistEntitiesByUserId(userId);
        if(lis.size()==0)return;

        for (int i=0;i<lis.size();i++)
        {
            BookEntity book=bookRepository.getBookEntityById(lis.get(i).getBook().getId());
            if(book.getInventory()<lis.get(i).getCount())
            {
                return;
            }
        }

        for (int i=0;i<lis.size();i++)
        {
            BookEntity book=bookRepository.getBookEntityById(lis.get(i).getBook().getId());
            book.setInventory(book.getInventory()-lis.get(i).getCount());
            bookRepository.save(book);
        }

        lis.forEach(o->cartListRepository.delete(o));

        //创建一个新的order

        TableOrderEntity orderEntity=new TableOrderEntity();
        orderEntity.setUserId(userId);
        orderEntity.setPurchaseTime(purchaseTime);
        orderRepository.save(orderEntity);
        int orderId=orderEntity.getOrderId();
        double totalPrice=0;

        List<OrderlistEntity> orderlistEntityList = new ArrayList<>();

        for(int i=0;i<lis.size();i++)
        {
            OrderlistEntity tmp= new OrderlistEntity(orderId,lis.get(i).getCount(),lis.get(i).getBook(),lis.get(i).getTotalprice(),userId);
            tmp.setTableOrderEntity(orderEntity);
            totalPrice+=lis.get(i).getTotalprice();
            orderlistEntityList.add(tmp);
        }
        for (OrderlistEntity orderlistEntity : orderlistEntityList) {
            orderListRepository.save(orderlistEntity);
        }
        orderEntity.setTotalPrice(totalPrice);
        orderEntity.setOrderlist(orderlistEntityList);
        orderRepository.save(orderEntity);


    }

    @Override
    public void AddNewOrder(Integer bookId, Integer userId, Timestamp purchaseTime){
        BookEntity book=bookRepository.getBookEntityById(bookId);
        if(book.getInventory()<1)return;
        else book.setInventory(book.getInventory()-1);
        TableOrderEntity orderEntity=new TableOrderEntity(1,book.getPrice().doubleValue(),userId);
        List<OrderlistEntity> lis=new ArrayList<>();
        orderEntity.setPurchaseTime(purchaseTime);
        orderRepository.save(orderEntity);
        OrderlistEntity orderlistEntity=new OrderlistEntity(orderEntity.getOrderId(),1,book,book.getPrice().doubleValue(),userId);
        orderlistEntity.setTableOrderEntity(orderEntity);
        lis.add(orderlistEntity);
        orderEntity.setOrderlist(lis);
        orderlistEntity.setTableOrderEntity(orderEntity);
        orderListRepository.save(orderlistEntity);
        orderRepository.save(orderEntity);

    }

    @Override
    public List<TableOrderInAntTableEntity> getAllOrders(Integer UserId) {
        List<TableOrderEntity>  lis=orderRepository.getAllByUserId(UserId);
        List<TableOrderInAntTableEntity> res=new ArrayList<>();
        for(int i=0;i<lis.size();i++)
        {
            TableOrderInAntTableEntity tmp=new TableOrderInAntTableEntity();
            tmp.setOrderId(lis.get(i).getOrderId());
            tmp.setPurchaseTime(lis.get(i).getPurchaseTime());
            tmp.setTotalPrice(lis.get(i).getTotalPrice());
            tmp.setUserName(userRepository.getUserEntityByUserId(lis.get(i).getUserId()).getName());
            tmp.setOrderlist(lis.get(i).getOrderlist());
            res.add(tmp);
        }
        return res;
    }

    @Override
    public List<TableOrderInAntTableEntity> getAllOrders() {
        List<TableOrderEntity> lis= orderRepository.findAll();
        List<TableOrderInAntTableEntity> res=new ArrayList<>();
        for(int i=0;i<lis.size();i++)
        {
            TableOrderInAntTableEntity tmp=new TableOrderInAntTableEntity();
            tmp.setOrderId(lis.get(i).getOrderId());
            tmp.setPurchaseTime(lis.get(i).getPurchaseTime());
            tmp.setTotalPrice(lis.get(i).getTotalPrice());
            tmp.setUserName(userRepository.getUserEntityByUserId(lis.get(i).getUserId()).getName());
            tmp.setOrderlist(lis.get(i).getOrderlist());
            res.add(tmp);
        }
        return res;
    }

    @Override
    public List<TableOrderInAntTableEntity> SearchOrder(Integer userId, Timestamp beginTime, Timestamp endTime, String prefix) {
        if(prefix==null)prefix="";
        else prefix=prefix.toLowerCase();
        List<TableOrderEntity> lis=orderRepository.getAllByUserId(userId);
        List<TableOrderEntity> res=new ArrayList<>();
        for(int i=0;i<lis.size();i++)
        {
            if(lis.get(i).getPurchaseTime().after(beginTime) &&lis.get(i).getPurchaseTime().before(endTime))
            {
                List<OrderlistEntity> orderlistEntityList = lis.get(i).getOrderlist();
                for(int j=0;j<orderlistEntityList.size();j++)
                {
                    if(orderlistEntityList.get(j).getBook().getName().toLowerCase().contains(prefix))
                    {
                        res.add(lis.get(i));
                        break;
                    }
                }
            }
        }

        List<TableOrderInAntTableEntity> reslis=new ArrayList<>();

        for(int i=0;i<res.size();i++)
        {
            TableOrderInAntTableEntity tmp=new TableOrderInAntTableEntity();
            tmp.setOrderId(res.get(i).getOrderId());
            tmp.setPurchaseTime(res.get(i).getPurchaseTime());
            tmp.setTotalPrice(res.get(i).getTotalPrice());
            tmp.setUserName(userRepository.getUserEntityByUserId(res.get(i).getUserId()).getName());
            tmp.setOrderlist(res.get(i).getOrderlist());
            reslis.add(tmp);
        }
        return reslis;
    }

    @Override
    public List<TableOrderInAntTableEntity> SearchOrder(Timestamp beginTime, Timestamp endTime, String prefix) {
        if(prefix==null)prefix="";
        else prefix=prefix.toLowerCase();
        List<TableOrderEntity> lis=orderRepository.findAll();
        List<TableOrderEntity> res=new ArrayList<>();
        for(int i=0;i<lis.size();i++)
        {
            if(lis.get(i).getPurchaseTime().after(beginTime) &&lis.get(i).getPurchaseTime().before(endTime))
            {
                List<OrderlistEntity> orderlistEntityList = lis.get(i).getOrderlist();
                for(int j=0;j<orderlistEntityList.size();j++)
                {
                    if(orderlistEntityList.get(j).getBook().getName().toLowerCase().contains(prefix))
                    {
                        res.add(lis.get(i));
                        break;
                    }
                }
            }
        }
        List<TableOrderInAntTableEntity> reslis=new ArrayList<>();

        for(int i=0;i<res.size();i++)
        {
            TableOrderInAntTableEntity tmp=new TableOrderInAntTableEntity();
            tmp.setOrderId(res.get(i).getOrderId());
            tmp.setPurchaseTime(res.get(i).getPurchaseTime());
            tmp.setTotalPrice(res.get(i).getTotalPrice());
            tmp.setUserName(userRepository.getUserEntityByUserId(res.get(i).getUserId()).getName());
            tmp.setOrderlist(res.get(i).getOrderlist());
            reslis.add(tmp);
        }
        return reslis;
    }

    @Override
    public List<BookInAntTableEntity> AdminBookStatistics(Timestamp beginTime, Timestamp endTime) {
        List<TableOrderEntity> lis=orderRepository.findAll();
        List<TableOrderEntity> orderres=new ArrayList<>();
        for (int i=0;i<lis.size();i++)
        {
            if(lis.get(i).getPurchaseTime().after(beginTime) &&lis.get(i).getPurchaseTime().before(endTime))
            {
                orderres.add(lis.get(i));
            }
        }
        Map<Integer,Integer> map=new HashMap<>();
        for(int i=0;i<orderres.size();i++)
        {
            List<OrderlistEntity> orderlistEntityList = orderres.get(i).getOrderlist();
            for(int j=0;j<orderlistEntityList.size();j++)
            {
                if(map.containsKey(orderlistEntityList.get(j).getBook().getId()))
                {
                    map.put(orderlistEntityList.get(j).getBook().getId(),map.get(orderlistEntityList.get(j).getBook().getId())+orderlistEntityList.get(j).getBookCount());
                }
                else
                {
                    map.put(orderlistEntityList.get(j).getBook().getId(),orderlistEntityList.get(j).getBookCount());
                }
            }
        }
        List<BookInAntTableEntity> res=new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            BookEntity bookEntity=bookRepository.getBookEntityById(entry.getKey());
            res.add(new BookInAntTableEntity(
                    bookEntity.getId(),
                    bookEntity.getName(),
                    bookEntity.getAuthor(),
                    0,
                    bookEntity.getPrice(),
                    bookEntity.getType(),
                    bookEntity.getDescription(),
                    bookEntity.getImage(),
                    bookEntity.getIsbn(),
                    entry.getValue()
            ));
        }
        res.sort((o1,o2)->o2.getCount()-o1.getCount());
        return res;
    }
}
