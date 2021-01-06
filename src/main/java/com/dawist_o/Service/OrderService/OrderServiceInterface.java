package com.dawist_o.Service.OrderService;

import com.dawist_o.model.Order;

import java.util.List;

public interface OrderServiceInterface {

    List<Order> getAllOrders();

    Order getOrderById();

}
