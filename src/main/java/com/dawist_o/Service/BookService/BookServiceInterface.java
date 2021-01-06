package com.dawist_o.Service.BookService;

import com.dawist_o.model.Author;
import com.dawist_o.model.Book;

import java.util.List;

public interface BookServiceInterface {
    Book getBookById(Long id);

    void save(Book book);

    void deleteBookById(Long id);

    List<Book> getAllBooks();

    boolean existsBookById(Long id);

    List<Author> getAllAuthors();

    Author getAuthorByNameOrCreateNew(String author);
}
