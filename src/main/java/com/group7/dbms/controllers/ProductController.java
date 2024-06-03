package com.group7.dbms;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.JsonArray;

import java.math.BigDecimal;

import spark.Request;
import spark.Response;
import spark.Spark;

interface Deserializer<T> {
    public T fromJson(String json, Class<T> objectClass);
}

public class ProductController {
    private ProductsDAO productsDAO;
    private ProductView productView;
    //private Deserializer<Product> deserializer;
    // private static Gson partialRepresentationGson;
    // private static Gson fullRepresentationGson;

    public ProductController(ProductsDAO productsDAO) {
        this.productsDAO = productsDAO;
        productView = new ProductView();
        //this.deserializer = deserializer;
        // partialRepresentationGson = Main.setUpGson(RepresentationType.PARTIAL);
        // fullRepresentationGson = Main.setUpGson(RepresentationType.FULL);
    }

    public void ignite() {

        // get by id
        Spark.get("/products/:product-id", (req, res) -> {
            Long id = Long.parseLong(req.params(":product-id"));
            Product product = productsDAO.getByID(id);
            if (product != null) {
                res.type("application/json");
                return productView.dump(product);
            } else {
                res.status(404);
                return "Product not found";
            }
        });

        
        // get all
        Spark.get("/products/", (req, res) -> {
            res.type("application/json");
            return productView.dump(productsDAO.getAllProducts());
        });

        Spark.redirect.get("/products", "/products/");

        // add product
        Spark.post("/products/", (req, res) -> {
            //Product product = deserializer.fromJson(req.body(), Product.class);
            JsonObject json = Json.parse(req.body()).asObject();
            Product product = deserialize(json);
            product = productsDAO.save(product);
            res.status(201);
            return "Product created";
        });

        // update product
        Spark.put("/products/:product-id", (req, res) -> {
            Long id = Long.parseLong(req.params(":product-id"));
            JsonObject json = Json.parse(req.body()).asObject();
            Product product = new Product();
            product = deserialize(json);
            product.setId(id);
            productsDAO.update(product);
            return "Product updated";
        });

        // delete product
        Spark.delete("/products/:product-id", (req, res) -> {
            Long id = Long.parseLong(req.params(":product-id"));
            productsDAO.remove(id);
            return "Product deleted";
        });
     }

     private Product deserialize(JsonObject json) {
        Product product = new Product();
        product.setName(json.getString("name", ""));
        product.setPrice(new BigDecimal(json.getString("price", "0")));
        product.setDescription(json.getString("description", ""));
        return product;
     }
}