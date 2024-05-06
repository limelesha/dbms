package com.group7.dbms;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

// import com.group7.dbms.models.Product;

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
        try(Session session =  sessionFactory.openSession();)
        {
            Transaction transaction = session.beginTransaction();
            Product result  = session.get(Product.class, id);
            transaction.commit();
            return result;
        } catch (HibernateException e) {
            //TODO handle the exception properly
            throw(e);
        }
    }

    @Override
    public List<Product> getAllProducts() {
        try(Session session =  sessionFactory.openSession();)
        {
            Transaction transaction = session.beginTransaction();
            List<Product> result = session.createQuery(
                "FROM Product", Product.class
            ).list();
            transaction.commit();
            return result;
        } catch (HibernateException e) {
            //TODO handle the exception properly
            throw(e);
        }
    }

    @Override
    public void save(Product product) {
        sessionFactory.inTransaction(session -> {
            session.persist(product);
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
        remove(getByID(id));
    }
}