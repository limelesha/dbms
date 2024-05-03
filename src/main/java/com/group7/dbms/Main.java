package com.group7.dbms;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.SessionFactory;


public class Main {

    public static SessionFactory sessionFactory;

    static {
        setUpSessionFactory();
    }

    public static void main(String[] args) {}

    private static void setUpSessionFactory() {
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
        }
    }

    private static void closeSessionFactory() {
        if (sessionFactory != null)
            sessionFactory.close();
    }

}
