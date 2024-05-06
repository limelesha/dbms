package com.group7.dbms;

import java.math.BigDecimal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.SessionFactory;


public class Main {

    private static Gson partialRepresentationGson;
    private static Gson fullRepresentationGson;

    private static ProductsDAO productsDAO;

    public static void main(String[] args)
    throws Exception {
        partialRepresentationGson = setUpGson(RepresentationType.PARTIAL);
        fullRepresentationGson = setUpGson(RepresentationType.FULL);
        SessionFactory sessionFactory = setUpSessionFactory();
        productsDAO = new HibernateProductsDAO(sessionFactory);

        Product product = new Product(
            "Donut",
            "Tasty donut",
            new BigDecimal(4.99)
        );
        productsDAO.save(product);
        System.out.println(
            fullRepresentationGson.toJson(productsDAO.getAllProducts())
        );

        sessionFactory.close();
    }

    private static SessionFactory setUpSessionFactory()
    throws Exception {
        SessionFactory sessionFactory;
        final StandardServiceRegistry registry =
            new StandardServiceRegistryBuilder()
            .build();
        try {
            sessionFactory = new MetadataSources(registry)
                .addAnnotatedClass(Product.class)
                .buildMetadata()
                .buildSessionFactory();
        } catch (Exception exception) {
            StandardServiceRegistryBuilder.destroy(registry);
            throw(exception);
        }
        return sessionFactory;
    }

    private static Gson setUpGson(RepresentationType type) {
        return new GsonBuilder()
            .setExclusionStrategies(new RepresentationExclusionStrategy(type))
            .create();
    }

    // private static void closeSessionFactory() {
    //     if (sessionFactory != null)
    //         sessionFactory.close();
    // }

}
