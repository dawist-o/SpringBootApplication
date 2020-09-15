package com.dawist_o.Service;

import com.dawist_o.model.Book;

import java.util.List;

public interface BookServiceInterface {
    Book getById(Long id);
    void save(Book book);
    void deleteById(Long id);
    List<Book> getAll();
    boolean existsById(Long id);
}
