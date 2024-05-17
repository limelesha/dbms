package com.group7.dbms;

import java.util.List;

import org.hibernate.SessionFactory;

public class HibernateRecipesDAO implements RecipesDAO {
    private SessionFactory sessionFactory;

    HibernateRecipesDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

     /**
     * Object by id value or null if not found
     */
    @Override
    public Recipe getByID(Long id) {
        // fromTransaction() existed all the way along :skull:
        return sessionFactory.fromTransaction(session -> {
            return session.get(Recipe.class, id);
        });
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return sessionFactory.fromTransaction(session -> {
            return session.createQuery(
                "From Recipe", Recipe.class
            ).list();
        });
    }

    @Override
    public Recipe save(Recipe recipe) {
        return sessionFactory.fromTransaction(session -> {
            session.persist(recipe);
            session.flush();
            return recipe;
        });
    }

    @Override
    public void update(Recipe recipe) {
        sessionFactory.inTransaction(session -> {
            session.merge(recipe);
        });
    }

    @Override
    public void remove(Recipe recipe) {
        sessionFactory.inTransaction(session -> {
            session.remove(recipe);
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