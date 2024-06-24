package com.group7.dbms;

import java.util.List;
import java.util.LinkedList;

import org.hibernate.SessionFactory;

public class HibernateOrderItemsDAO implements OrderItemsDAO {
    private SessionFactory sessionFactory;

    HibernateOrderItemsDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Object by id value or null if not found
     */
    @Override
    public OrderItem getByID(Long id) {
        return sessionFactory.fromTransaction(session -> {
            return session.get(OrderItem.class, id);
        });
    }

    @Override
    public List<OrderItem> getByOrderId(Long id) {
        return sessionFactory.fromTransaction(session -> {
            return session.createQuery(
                "FROM OrderItem WHERE order.id = " + id, OrderItem.class
            ).list();
        });
    }

    @Override
    public List<OrderItem> getByProductId(Long id) {
        return sessionFactory.fromTransaction(session -> {
            return session.createQuery(
                "FROM OrderItem WHERE product.id = " + id, OrderItem.class
            ).list();
        });
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        return sessionFactory.fromTransaction(session -> {
            return session.createQuery(
                "FROM OrderItem", OrderItem.class
            ).list();
        });
    }

    @Override
    public OrderItem save(OrderItem orderItem) {
        return sessionFactory.fromTransaction(session -> {
            session.persist(orderItem);
            session.flush();
            return orderItem;
        });
    }

    @Override
    public void update(OrderItem orderItem) {
        sessionFactory.inTransaction(session -> {
            session.merge(orderItem);
        });
    }

    @Override
    public void remove(OrderItem orderItem) {
        sessionFactory.inTransaction(session -> {
            session.remove(orderItem);
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