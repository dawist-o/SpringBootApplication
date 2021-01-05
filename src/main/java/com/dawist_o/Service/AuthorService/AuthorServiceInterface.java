package com.dawist_o.Service.AuthorService;

import com.dawist_o.model.Author;

import java.util.List;

public interface AuthorServiceInterface {

    Author getById(Long id);

    Author getByName(String name);

    void save(Author author);

    void deleteById(Long id);

    List<Author> getAll();

    boolean existsById(Long id);
}
