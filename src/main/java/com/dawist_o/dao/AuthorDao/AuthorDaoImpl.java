package com.dawist_o.dao.AuthorDao;


import com.dawist_o.model.Author;
import com.dawist_o.model.Book;
import lombok.extern.log4j.Log4j;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Transactional
public class AuthorDaoImpl implements AuthorDao {

    @PersistenceContext
    private Session session;

    @Override
    public Author getById(Long id) {
        return session.get(Author.class, id);
    }

    @Override
    public Author getByName(String name) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Author> criteria = builder.createQuery(Author.class);
        Root<Author> from = criteria.from(Author.class);

        criteria.select(from);
        criteria.where(builder.equal(from.get("name"), name));

        TypedQuery<Author> typed = session.createQuery(criteria);
        Author author;
        try {
            author = typed.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

        return author;
    }

    @Override
    public void save(Author author) {
        session.persist(author);
    }

    @Override
    public void deleteById(Long id) {
        Author load = session.find(Author.class, id);
        session.remove(load);
        session.flush();
    }

    @Override
    public List<Author> findAll() {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Author> cq = cb.createQuery(Author.class);

        Root<Author> rootEntry = cq.from(Author.class);
        CriteriaQuery<Author> all = cq.select(rootEntry);

        TypedQuery<Author> allQuery = session.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    public boolean existsById(Long id) {
        return session.find(Author.class, id) != null;
    }
}
