package com.dawist_o.dao.AuthorDao;

import com.dawist_o.model.Author;

import java.util.List;

public interface AuthorDao {
    Author getById(Long id);

    Author getByName(String name);

    void save(Author author);

    void deleteById(Long id);

    List<Author> findAll();

    boolean existsById(Long id);
}
