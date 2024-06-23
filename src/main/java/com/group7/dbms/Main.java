package com.group7.dbms;

import java.math.BigDecimal;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.SessionFactory;

import spark.Spark;

public class Main {

    private static ProductsDAO productsDAO;
    private static RecipesDAO recipesDAO;
    private static BakeriesDAO bakeriesDAO;
    private static CustomersDAO customersDAO;
    private static EmployeesDAO employeesDAO;
    private static FeedbackDAO feedbackDAO;
    public static void main(String[] args) 
    throws Exception {
        SessionFactory sessionFactory = setUpSessionFactory();
        productsDAO = new HibernateProductsDAO(sessionFactory);
        recipesDAO = new HibernateRecipesDAO(sessionFactory);
        bakeriesDAO = new HibernateBakeriesDAO(sessionFactory);
        customersDAO = new HibernateCustomersDAO(sessionFactory);
        employeesDAO = new HibernateEmployeesDAO(sessionFactory);
        feedbackDAO = new HibernateFeedbackDAO(sessionFactory);
        ProductController productController = new ProductController(productsDAO);
        RecipeController recipeController = new RecipeController(recipesDAO, productsDAO);
        BakeryController bakeryController = new BakeryController(bakeriesDAO);
        CustomerController customerController = new CustomerController(customersDAO);
        EmployeeController employeeController = new EmployeeController(employeesDAO);
        FeedbackController feedbackController = new FeedbackController(feedbackDAO);

        productController.ignite();
        recipeController.ignite();
        bakeryController.ignite();
        customerController.ignite();
        employeeController.ignite();
        feedbackController.ignite();

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
                .addAnnotatedClass(Feedback.class)
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(OrderItem.class)
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Product.class)
                .addAnnotatedClass(Recipe.class)
                .buildMetadata()
                .buildSessionFactory();
        
        return sessionFactory;
    }

    // public static Gson setUpGson(RepresentationType type) {
    //     return new GsonBuilder()
    //         .setExclusionStrategies(new RepresentationExclusionStrategy(type))
    //         .create();
    // }

    // private static void closeSessionFactory() {
    //     if (sessionFactory != null)
    //         sessionFactory.close();
    // }

}
