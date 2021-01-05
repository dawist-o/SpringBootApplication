package com.dawist_o.dao.BookDao;


import com.dawist_o.model.Book;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Transactional
public class BookDaoImpl implements BookDao {

    @PersistenceContext
    private Session session;

    @Override
    public Book getById(Long id) {
        return session.find(Book.class, id);
    }

    @Override
    public void save(Book book) {
        session.persist(book);
    }

    @Override
    public void deleteById(Long id) {
        Book load = session.get(Book.class, id);
        session.remove(load);
        session.flush();
    }

    @Override
    public List<Book> findAll() {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);

        Root<Book> rootEntry = cq.from(Book.class);
        CriteriaQuery<Book> all = cq.select(rootEntry);

        TypedQuery<Book> allQuery = session.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    public boolean existsById(Long id) {
        return session.find(Book.class, id) != null;
    }
}
