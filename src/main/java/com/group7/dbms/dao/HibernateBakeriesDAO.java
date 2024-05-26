package com.group7.dbms;

import java.util.List;

import org.hibernate.SessionFactory;

public class HibernateBakeriesDAO implements BakeriesDAO {
    private SessionFactory sessionFactory;

    HibernateBakeriesDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    /**
     * Object by id value or null if not found
     */
    @Override
    public Bakery getByID(Long id) {
        return sessionFactory.fromTransaction(session -> {
            return session.get(Bakery.class, id);
        });
    }

    @Override
    public List<Bakery> getAllBakeries() {
        return sessionFactory.fromTransaction(session -> {
            return session.createQuery(
                "FROM Bakery", Bakery.class
            ).list();
        });
    }

    @Override
    public Bakery save(Bakery bakery) {
        return sessionFactory.fromTransaction(session -> {
            session.persist(bakery);
            session.flush();
            return bakery;
        });
    }

    @Override
    public void update(Bakery bakery) {
        sessionFactory.inTransaction(session -> {
            session.merge(bakery);
        });
    }

    @Override
    public void remove(Bakery bakery) {
        sessionFactory.inTransaction(session -> {
            session.remove(bakery);
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