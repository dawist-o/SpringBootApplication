package com.dawist_o.model;

import com.dawist_o.model.user.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders")
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "customer_name")
    private String customerName;
    private String address;
    private String comment;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "books_orders",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books;
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "app_user_id"
    )
    private AppUser appUser;

    public void addBook(Book book) {
        books.add(book);
        book.getOrders().add(this);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public Order(String customerName, String address, String comment, AppUser appUser) {
        this.customerName = customerName;
        this.address = address;
        this.comment = comment;
        this.appUser = appUser;
        books = new LinkedList<>();
    }

    public Order(String customerName, String address, String comment) {
        this.customerName = customerName;
        this.address = address;
        this.comment = comment;
        books = new LinkedList<>();
    }

}
