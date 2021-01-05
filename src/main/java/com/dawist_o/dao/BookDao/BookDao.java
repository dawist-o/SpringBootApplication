package com.dawist_o.dao.BookDao;

import com.dawist_o.model.Book;

import java.util.List;

public interface BookDao {
    Book getById(Long id);

    void save(Book book);

    void deleteById(Long id);

    List<Book> findAll();

    boolean existsById(Long id);
}
