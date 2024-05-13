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
        Long id = Long.parseLong(req.params(":product-id"));
        res.status(200);
        return productsDAO.getByID(id);
    }

    public Object getAllProducts(Request req, Response res) {
        res.status(200);
        return productsDAO.getAllProducts();
    }

    public Object save(Request req, Response res) {
        Product product = deserializer.fromJson(req.body(), Product.class);
        product = productsDAO.save(product);
        res.status(201);
        return product;
    }

    public Object update(Request req, Response res) {
        Long id = Long.parseLong(req.params(":product-id"));
        Product product = deserializer.fromJson(req.body(), Product.class);
        product.setId(id);
        productsDAO.update(product);
        res.status(200);
        return "";
    }

    public Object remove(Request req, Response res) {
        Long id = Long.parseLong(req.params(":product-id"));
        productsDAO.remove(id);
        res.status(200);
        return "";
    }
}