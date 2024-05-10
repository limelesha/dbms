package com.group7.dbms;

import java.util.List;

import org.hibernate.SessionFactory;

public class HibernateProductsDAO implements ProductsDAO {
    private SessionFactory sessionFactory;

    HibernateProductsDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    /**
     * Object by id value or null if not found
     */
    @Override
    public Product getByID(Long id) {
        // fromTransaction() existed all the way along :skull:
        return sessionFactory.fromTransaction(session -> {
            return session.get(Product.class, id);
        });
    }

    @Override
    public List<Product> getAllProducts() {
        return sessionFactory.fromTransaction(session -> {
            return session.createQuery(
                "From Product", Product.class
            ).list();
        });
    }

    @Override
    public Long save(Product product) {
        return sessionFactory.fromTransaction(session -> {
            session.persist(product);
            session.flush();
            return product.getId();
        });
    }

    @Override
    public void update(Product product) {
        sessionFactory.inTransaction(session -> {
            session.merge(product);
        });
    }

    @Override
    public void remove(Product product) {
        sessionFactory.inTransaction(session -> {
            session.remove(product);
        });
    }

    /**
     * Remove by identifier
     */
    @Override
    public void remove(Long id) {
        Product product = new Product();
        product.setId(id);
        remove(product);
    }
}