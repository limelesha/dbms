package com.group7.dbms;

import java.util.List;

import org.hibernate.SessionFactory;

public class HibernateOrdersDAO implements OrdersDAO {
    private SessionFactory sessionFactory;

    HibernateOrdersDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    /**
     * Object by id value or null if not found
     */
    @Override
    public Order getByID(Long id) {
        return sessionFactory.fromTransaction(session -> {
            return session.get(Order.class, id);
        });
    }

    @Override
    public List<Order> getAllOrders() {
        return sessionFactory.fromTransaction(session -> {
            return session.createQuery(
                "FROM Order", Order.class
            ).list();
        });
    }

    @Override
    public Order save(Order order) {
        return sessionFactory.fromTransaction(session -> {
            session.persist(order);
            session.flush();
            return order;
        });
    }

    @Override
    public void update(Order order) {
        sessionFactory.inTransaction(session -> {
            session.merge(order);
        });
    }

    @Override
    public void remove(Order order) {
        sessionFactory.inTransaction(session -> {
            session.remove(order);
        });
    }

    /**
     * Remove by identifier
     */
    @Override
    public void remove(Long id) {
        remove(getByID(id));
    }
}