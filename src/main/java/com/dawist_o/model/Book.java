package com.dawist_o.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data // creates setters, getters and toString automatically
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;
    private String author;
    private String resume;
    private String fullText;
    private int views;

    public Book(String title, String author, String resume, String fullText, int views) {
        this.title = title;
        this.author = author;
        this.resume = resume;
        this.fullText = fullText;
        this.views = views;
    }

    public Book() {
    }
}
