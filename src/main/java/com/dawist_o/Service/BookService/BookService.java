package com.dawist_o.Service.BookService;


import com.dawist_o.dao.AuthorDao.AuthorDao;
import com.dawist_o.dao.BookDao.BookDao;
import com.dawist_o.model.Author;
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

    @Autowired
    private AuthorDao authorDao;

    @Override
    public List<Author> getAllAuthors(){
        return authorDao.findAll();
    }

    @Override
    public Author getAuthorByNameOrCreateNew(String author){
        Author authorByName = authorDao.getByName(author);
        if (authorByName == null) {
            authorByName = new Author(author.trim(), "");
            authorDao.save(authorByName);
        }
        return  authorByName;
    }

    @Override
    public Book getBookById(Long id) {
        log.info("In BookService method getByID: " + id);
        return bookDao.getById(id);
    }

    @Override
    public void save(Book book) {
        log.info("In BookService method save: " + book);
        bookDao.save(book);
    }

    @Override
    public void deleteBookById(Long id) {
        log.info("In BookService method delete: " + id);
        bookDao.deleteById(id);
    }

    @Override
    public List<Book> getAllBooks() {
        log.info("In BookService method getAll");
        return bookDao.findAll();
    }

    @Override
    public boolean existsBookById(Long id) {
        return bookDao.existsById(id);
    }
}
