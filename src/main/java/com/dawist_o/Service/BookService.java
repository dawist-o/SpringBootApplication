package com.dawist_o.Service;


import com.dawist_o.model.Book;
import com.dawist_o.repository.BookRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BookService implements BookServiceInterface {

    @Autowired
    private BookRepo bookRepo;

    @Override
    public Book getById(Long id) {
        log.info("In BookService method getByID: " + id);
        return bookRepo.findById(id).orElseThrow();
    }

    @Override
    public void save(Book book) {
        ;
        log.info("In BookService method save: " + book);
        bookRepo.save(book);
    }

    @Override
    public void deleteById(Long id) {
        log.info("In BookService method delete: " + id);
        bookRepo.deleteById(id);
    }

    @Override
    public List<Book> getAll() {
        log.info("In BookService method getAll");
        return bookRepo.findAll();
    }

    @Override
    public boolean existsById(Long id) {
        return bookRepo.existsById(id);
    }
}
