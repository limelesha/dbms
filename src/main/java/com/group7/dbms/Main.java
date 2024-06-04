package com.group7.dbms;

import java.math.BigDecimal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.SessionFactory;

import spark.Spark;



public class Main {

    private static Gson partialRepresentationGson;
    private static Gson fullRepresentationGson;

    private static ProductsDAO productsDAO;
    private static RecipesDAO recipesDAO;
    private static BakeriesDAO bakeriesDAO;

    public static void main(String[] args)
    throws Exception {
        partialRepresentationGson = setUpGson(RepresentationType.PARTIAL);
        fullRepresentationGson = setUpGson(RepresentationType.FULL);
        SessionFactory sessionFactory = setUpSessionFactory();
        productsDAO = new HibernateProductsDAO(sessionFactory);
        recipesDAO = new HibernateRecipesDAO(sessionFactory);
        bakeriesDAO = new HibernateBakeriesDAO(sessionFactory);
        ProductController productController = new ProductController(productsDAO);
        RecipeController recipeController = new RecipeController(recipesDAO, productsDAO);
        BakeryController bakeryController = new BakeryController(bakeriesDAO);
        
        productController.ignite();
        recipeController.ignite();
        bakeryController.ignite();

        Spark.awaitInitialization();
    }

    private static SessionFactory setUpSessionFactory()
    throws Exception {
        SessionFactory sessionFactory;
        final StandardServiceRegistry registry =
            new StandardServiceRegistryBuilder()
            .build();
            sessionFactory = new MetadataSources(registry)
                .addAnnotatedClass(Bakery.class)
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Employee.class)
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Product.class)
                .addAnnotatedClass(Recipe.class)
                .addAnnotatedClass(Feedback.class)
                .buildMetadata()
                .buildSessionFactory();
        
        return sessionFactory;
    }

    public static Gson setUpGson(RepresentationType type) {
        return new GsonBuilder()
            .setExclusionStrategies(new RepresentationExclusionStrategy(type))
            .create();
    }

    // private static void closeSessionFactory() {
    //     if (sessionFactory != null)
    //         sessionFactory.close();
    // }

}
