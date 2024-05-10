package com.group7.dbms;

import com.google.gson.Gson;

import spark.Request;
import spark.Response;


public class ProductController {

    private ProductsDAO productsDAO;


    public ProductController(ProductsDAO productsDAO) {
        this.productsDAO = productsDAO;
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
        //TODO implement deserialization
        res.status(501);
        return res.body();
    }

    public Object update(Request req, Response res) {
        //TODO implement deserialization
        res.status(501);
        return res.body();
    }

    public Object remove(Request req, Response res) {
        //TODO implement deserialization or is removal done by Id?
        res.status(501);
        return res.body();
    }
}