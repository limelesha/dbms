package com.group7.dbms;

import java.math.BigDecimal;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.SessionFactory;

import spark.Spark;
import spark.Request;
import spark.Response;

public class Main {

    private static ProductsDAO productsDAO;
    private static RecipesDAO recipesDAO;
    private static BakeriesDAO bakeriesDAO;
    private static CustomersDAO customersDAO;
    private static EmployeesDAO employeesDAO;
    private static FeedbackDAO feedbackDAO;
    private static PersonsDAO personsDAO;
    public static void main(String[] args) 
    throws Exception {
        SessionFactory sessionFactory = setUpSessionFactory();
        productsDAO = new HibernateProductsDAO(sessionFactory);
        recipesDAO = new HibernateRecipesDAO(sessionFactory);
        bakeriesDAO = new HibernateBakeriesDAO(sessionFactory);
        customersDAO = new HibernateCustomersDAO(sessionFactory);
        employeesDAO = new HibernateEmployeesDAO(sessionFactory);
        feedbackDAO = new HibernateFeedbackDAO(sessionFactory);
        personsDAO = new HibernatePersonsDAO(sessionFactory);
        ProductController productController = new ProductController(productsDAO);
        RecipeController recipeController = new RecipeController(recipesDAO, productsDAO);
        BakeryController bakeryController = new BakeryController(bakeriesDAO);
        CustomerController customerController = new CustomerController(customersDAO);
        EmployeeController employeeController = new EmployeeController(employeesDAO, bakeriesDAO);
        FeedbackController feedbackController = new FeedbackController(feedbackDAO, personsDAO, productsDAO);
        LoginController loginController = new LoginController(personsDAO, employeesDAO, customersDAO);
        productController.ignite();
        recipeController.ignite();
        bakeryController.ignite();
        customerController.ignite();
        employeeController.ignite();
        feedbackController.ignite();
        loginController.ignite();

        Spark.before("/recipes/*", (req, res) -> {
            Person person = req.session().attribute("person");
            if (!(employeesDAO.isEmployee(person))) {
                res.status(403);
                Spark.halt("Not allowed");
            }
        });
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
}
