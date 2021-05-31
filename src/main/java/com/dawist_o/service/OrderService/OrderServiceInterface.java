package com.dawist_o.service.OrderService;

import com.dawist_o.model.Book;
import com.dawist_o.model.Order;

import java.security.Principal;
import java.util.List;

public interface OrderServiceInterface {

    List<Order> getAllOrders(Principal principal);

    Book getBookById(Long id);


    void save(Order oreder);

    Order getOrderById(Long id);

    void deleteOrderById(Long id);

    boolean existsOrderById(Long id);
}
