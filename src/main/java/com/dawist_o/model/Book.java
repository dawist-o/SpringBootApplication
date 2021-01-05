package com.dawist_o.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;



@Entity
@Table(name = "books")
@NoArgsConstructor
@Data // creates setters, getters and toString automatically
public class Book {

    public Book(Author author, String fulltext, String title, String resume) {
        this(author,fulltext,title,resume,0);
    }

    public Book(Author author, String fulltext, String title, String resume, int views) {
        this.fulltext = fulltext;
        this.title = title;
        this.author = author;
        this.resume = resume;
        this.views = views;
    }

    @Column(name = "full_text")
    private String fulltext;
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;
    private String resume;
    private int views;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
