package com.dawist_o.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "authors")
@NoArgsConstructor
@Data
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books;
    private String name;
    private String biography;
    public Author(String name){
        this(name,"");
    }

    public Author(String name, String biography) {
        this.books = new LinkedList<>();
        this.name = name;
        this.biography = biography;
    }

    public void addBook(Book book) {
        books.add(book);
    }
}


