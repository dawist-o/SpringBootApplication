package com.dawist_o.Service.BookService;


import com.dawist_o.dao.BookDao.BookDao;
import com.dawist_o.model.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class BookService implements BookServiceInterface {

    @Autowired
    private BookDao bookDao;

    @Override
    public Book getById(Long id) {
        log.info("In BookService method getByID: " + id);
        return bookDao.getById(id);
    }

    @Override
    public void save(Book book) {
        log.info("In BookService method save: " + book);
        bookDao.save(book);
    }

    @Override
    public void deleteById(Long id) {
        log.info("In BookService method delete: " + id);
        bookDao.deleteById(id);
    }

    @Override
    public List<Book> getAll() {
        log.info("In BookService method getAll");
        return bookDao.findAll();
    }

    @Override
    public boolean existsById(Long id) {
        return bookDao.existsById(id);
    }
}
