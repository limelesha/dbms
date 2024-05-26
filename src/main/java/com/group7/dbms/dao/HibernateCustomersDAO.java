package com.group7.dbms;

import java.util.List;

import org.hibernate.SessionFactory;

public class HibernateCustomersDAO implements CustomersDAO {
    private SessionFactory sessionFactory;

    HibernateCustomersDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    /**
     * Object by id value or null if not found
     */
    @Override
    public Customer getByID(Long id) {
        List<Customer> customers = sessionFactory.fromTransaction(session -> {
            return session.createQuery(
                "FROM Customer WHERE person.id = " + id, Customer.class
            ).list();
        });
        return customers.get(0);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return sessionFactory.fromTransaction(session -> {
            return session.createQuery(
                "FROM Customer", Customer.class
            ).list();
        });
    }

    @Override
    public Customer save(Customer customer) {
        return sessionFactory.fromTransaction(session -> {
            session.persist(customer);
            session.flush();
            return customer;
        });
    }

    @Override
    public void update(Customer customer) {
        sessionFactory.inTransaction(session -> {
            session.merge(customer);
        });
    }

    @Override
    public void remove(Customer customer) {
        sessionFactory.inTransaction(session -> {
            session.remove(customer);
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