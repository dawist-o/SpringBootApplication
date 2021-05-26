package com.dawist_o.controllers;

import com.dawist_o.service.OrderService.OrderService;
import com.dawist_o.model.Book;
import com.dawist_o.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedList;
import java.util.List;

@Controller
public class OrdersController {

    private final OrderService orderService;

    @Autowired
    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public String getAllorders(Model model) {
        model.addAttribute("title", "Orders");
        List<Order> allOrders = orderService.getAllOrders();
        model.addAttribute("orders", allOrders);
        return "orders/orders";
    }

    @GetMapping("/create_order")
    public String createOrder(Model model) {
        model.addAttribute("title", "Create order");
        return "orders/create_order";
    }

    @PostMapping("/create_order")
    public String createOrderPost(@RequestParam String customerName, @RequestParam String address,
                                  @RequestParam String comment, @RequestParam String books) {
        String[] bookIds = books.trim().split(" ");
        Order order = new Order(customerName, address, comment);

        for (String bookId : bookIds) {
            order.addBook(orderService.getBookById(Long.parseLong(bookId)));
        }

        orderService.save(order);
        return "redirect:/orders";
    }

    @PostMapping("/order/{id}/delete")
    public String deleteOrder(@PathVariable(name = "id") Long id) {
        orderService.deleteOrderById(id);
        return "redirect:/orders";
    }

    @GetMapping("/order/{id}")
    public String orderInfo(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("order", orderService.getOrderById(id));
        return "orders/order_info";
    }

    @GetMapping("/order/{id}/edit")
    public String editOrderPosr(@PathVariable(name = "id") Long id, Model model) {
        if (!orderService.existsOrderById(id)) return "redirect:/orders";

        Order orderById = orderService.getOrderById(id);
        model.addAttribute("order", orderById);
        StringBuilder books = new StringBuilder();
        orderById.getBooks().forEach(book -> books.append(book.getId()).append(" "));
        model.addAttribute("books", books.toString());
        return "orders/order_edit";
    }

    @PostMapping("/order/{id}/edit")
    public String editOrderPosr(@PathVariable(name = "id") Long id, @RequestParam String customerName,
                                @RequestParam String address, @RequestParam String comment,
                                @RequestParam String books) {

        Order orderById = orderService.getOrderById(id);
        orderById.setCustomerName(customerName);
        orderById.setAddress(address);
        orderById.setComment(comment);

        List<Book> booksList = new LinkedList<>();
        for (String bookId : books.trim().split(" ")) {
            booksList.add(orderService.getBookById(Long.parseLong(bookId)));
        }
        orderById.setBooks(booksList);

        orderService.save(orderById);
        return "redirect:/order/" + id;
    }
}
