package com.dawist_o.Service.OrderService;

import com.dawist_o.dao.BookDao.BookDao;
import com.dawist_o.dao.OrderDao.OrderDao;
import com.dawist_o.model.Book;
import com.dawist_o.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements OrderServiceInterface {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private BookDao bookDao;

    @Override
    public Book getBookById(Long id){
        return bookDao.getById(id);
    }

    @Override
    public void save(Order order) {
        orderDao.save(order);
    }


    @Override
    public List<Order> getAllOrders() {
        return orderDao.findAll();
    }

    @Override
    public Order getOrderById(Long id) {
        return orderDao.getById(id);
    }
    @Override
    public boolean existsOrderById(Long id) {
        return orderDao.existsById(id);
    }

    @Override
    public void deleteOrderById(Long id) {
        orderDao.deleteById(id);
    }
}
