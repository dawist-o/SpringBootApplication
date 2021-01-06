package com.dawist_o.dao.OrderDao;

import com.dawist_o.model.Author;
import com.dawist_o.model.Order;
import com.dawist_o.model.Order;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
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
public class OrderDaoImpl implements OrderDao {

    @PersistenceContext
    private Session session;

    @Override
    public List<Order> findAll() {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);

        Root<Order> rootEntry = cq.from(Order.class);
        CriteriaQuery<Order> all = cq.select(rootEntry);

        TypedQuery<Order> allQuery = session.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    public Order getById(Long id) {
        return session.get(Order.class, id);
    }

    @Override
    public void save(Order order) {
        session.persist(order);
    }

    @Override
    public void deleteById(Long id) {
        Order load = session.find(Order.class, id);
        session.remove(load);
        session.flush();
    }

    @Override
    public boolean existsById(Long id) {
        return session.get(Order.class, id) != null;
    }
}
