package com.dawist_o.dao.OrderDao;

import com.dawist_o.model.Order;

import java.util.List;

public interface OrderDao {

    List<Order> findAll();

    Order getById(Long id);

    void save(Order order);

    void deleteById(Long id);

    boolean existsById(Long id);

}
