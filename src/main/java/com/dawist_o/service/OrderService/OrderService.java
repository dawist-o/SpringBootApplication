package com.dawist_o.service.OrderService;

import com.dawist_o.dao.OrderDao.OrderDao;
import com.dawist_o.model.Book;
import com.dawist_o.model.Order;
import com.dawist_o.model.user.AppUser;
import com.dawist_o.service.BookService.BookService;
import com.dawist_o.service.userService.AppUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class OrderService implements OrderServiceInterface {

    private final OrderDao orderDao;
    private final BookService bookService;
    private final AppUserService appUserService;

    @Override
    public Book getBookById(Long id) {
        log.info("In OrderService method getBookById: " + id);
        return bookService.getBookById(id);
    }

    @Override
    public void save(Order order) {
        log.info("In OrderService method save: " + order);
        orderDao.save(order);
    }


    @Override
    public List<Order> getAllOrders(Principal principal) {
        List<Order> orders;
        AppUser appUser = appUserService.loadUserByUsername(principal.getName());
        orders = switch (appUser.getAppUserRole()) {
            case ADMIN -> orderDao.findAll();
            default -> orderDao.getUserOrders(appUser.getId());
        };
        log.info("In OrderService method getAllOrders: " + orders);
        return orders;
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
