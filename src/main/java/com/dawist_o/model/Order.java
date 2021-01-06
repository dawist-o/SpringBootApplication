package com.dawist_o.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Table(name="orders")
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="customer_name")
    private String customerName;
    private String address;
    private String comment;

    @ManyToMany(mappedBy = "order")
    private List<Book> books;

    public Order(String customerName, String address, String comment) {
        this.customerName = customerName;
        this.address = address;
        this.comment = comment;
    }

}
