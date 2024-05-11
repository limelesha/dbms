package com.group7.dbms;

import com.google.gson.Gson;

import spark.Request;
import spark.Response;

interface Deserializer<T> {
    public T fromJson(String json, Class<T> objectClass);
}

public class ProductController {

    private ProductsDAO productsDAO;
    private Deserializer<Product> deserializer;

    public ProductController(
        ProductsDAO productsDAO,
        Deserializer deserializer
    ) {
        this.productsDAO = productsDAO;
        this.deserializer = deserializer;
    }

    public Object getByID(Request req, Response res) {
        Long id = Long.valueOf(req.params(":product-id"));
        res.status(200);
        return productsDAO.getByID(id);
    }

    public Object getAllProducts(Request req, Response res) {
        res.status(200);
        return productsDAO.getAllProducts();
    }

    public Object save(Request req, Response res) {
        Product product = deserializer.fromJson(req.body(), Product.class);
        productsDAO.save(product);
        res.status(201);
        return res.body();
    }

    public Object update(Request req, Response res) {
        Product product = deserializer.fromJson(req.body(), Product.class);
        productsDAO.update(product);
        res.status(200);
        return res.body();
    }

    public Object remove(Request req, Response res) {
        Long id = Long.parseLong(req.body());
        productsDAO.remove(id);
        res.status(200);
        return res.body();
    }
}