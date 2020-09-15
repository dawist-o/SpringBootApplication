package com.dawist_o.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Data // creates setters, getters and toString automatically
public class Book {

    public Book(String title, String author, String resume, String fullText, int views) {
        this.title = title;
        this.author = author;
        this.resume = resume;
        this.fullText = fullText;
        this.views = views;
    }

    private String title;
    private String author;
    private String resume;
    private String fullText;
    private int views;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
}
