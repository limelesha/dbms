package com.group7.dbms;

import java.util.List;

import org.hibernate.SessionFactory;

public class HibernateFeedbackDAO implements FeedbackDAO {
    private SessionFactory sessionFactory;

    HibernateFeedbackDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

     /**
     * Object by id value or null if not found
     */
    @Override
    public Feedback getByID(Long id) {
        // fromTransaction() existed all the way along :skull:
        return sessionFactory.fromTransaction(session -> {
            return session.get(Feedback.class, id);
        });
    }

    @Override
    public List<Feedback> getByProductId(Long id) {
        return sessionFactory.fromTransaction(session -> {
            return session.createQuery(
                "FROM Feedback WHERE product.id = " + id, Feedback.class
            ).list();
        });
    }

    @Override
    public List<Feedback> getByPersonId(Long id) {
        return sessionFactory.fromTransaction(session -> {
            return session.createQuery(
                "FROM Feedback WHERE author.id = " + id, Feedback.class
            ).list();
        });
    }

    @Override
    public List<Feedback> getAllFeedback() {
        return sessionFactory.fromTransaction(session -> {
            return session.createQuery(
                "FROM Feedback"
            ).list();
        });
    }

    @Override
    public Feedback save(Feedback feedback) {
        return sessionFactory.fromTransaction(session -> {
            session.persist(feedback);
            session.flush();
            return feedback;
        });
    }

    @Override
    public void update(Feedback feedback) {
        sessionFactory.inTransaction(session -> {
            session.merge(feedback);
        });
    }

    @Override
    public void remove(Feedback feedback) {
        sessionFactory.inTransaction(session -> {
            session.remove(feedback);
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