package com.dawist_o.repository;

import com.dawist_o.model.Book;
import org.springframework.data.repository.CrudRepository;


public interface BookRepo extends CrudRepository<Book, Long> {
}
