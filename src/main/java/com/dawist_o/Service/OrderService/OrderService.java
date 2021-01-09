package com.dawist_o.Service.OrderService;

import com.dawist_o.dao.BookDao.BookDao;
import com.dawist_o.dao.OrderDao.OrderDao;
import com.dawist_o.model.Book;
import com.dawist_o.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OrderService implements OrderServiceInterface {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private BookDao bookDao;

    @Override
    public Book getBookById(Long id) {
        log.info("In OrderService method getBookById: " + id);
        return bookDao.getById(id);
    }

    @Override
    public void save(Order order) {
        log.info("In OrderService method save: " + order);
        orderDao.save(order);
    }


    @Override
    public List<Order> getAllOrders() {
        log.info("In OrderService method getAllOrders:");
        return orderDao.findAll();
    }

    @Override
    public Order getOrderById(Long id) {
        log.info("In OrderService method getOrderById: " + id);
        return orderDao.getById(id);
    }

    @Override
    public boolean existsOrderById(Long id) {
        log.info("In OrderService method existsOrderById: " + id);
        return orderDao.existsById(id);
    }

    @Override
    public void deleteOrderById(Long id) {
        log.info("In OrderService method deleteOrderById: " + id);
        orderDao.deleteById(id);
    }
}
