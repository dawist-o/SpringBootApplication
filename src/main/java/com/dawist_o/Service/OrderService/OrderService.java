package com.dawist_o.Service.OrderService;

import com.dawist_o.dao.OrderDao.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderService {

    @Autowired
    private OrderDao orderDao;
}
