package com.dawist_o.Service.AuthorService;

import com.dawist_o.dao.AuthorDao.AuthorDao;
import com.dawist_o.model.Author;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AuthorService implements AuthorServiceInterface {

    @Autowired
    private AuthorDao authorDao;

    @Override
    public Author getById(Long id) {
        log.info("In  AuthorService method getByID: " + id);
        return authorDao.getById(id);
    }

    @Override
    public Author getByName(String name) {
        log.info("In  AuthorService method getByName: " + name);
        return authorDao.getByName(name);
    }

    @Override
    public void save(Author author) {
        log.info("In  AuthorService method save: " + author);
        authorDao.save(author);
    }

    @Override
    public void deleteById(Long id) {
        log.info("In  AuthorService method deleteByID: " + id);
        authorDao.deleteById(id);
    }

    @Override
    public List<Author> getAll() {
        List<Author> all = authorDao.findAll();
        log.info("In  AuthorService method getAll: " + all);
        return all;
    }

    @Override
    public boolean existsById(Long id) {
        log.info("In  AuthorService method existsByID: " + id);
        return authorDao.existsById(id);
    }
}
