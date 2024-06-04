package com.group7.dbms;
import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.JsonArray;

import java.math.BigDecimal;

import spark.Request;
import spark.Response;
import spark.Spark;

public class ProductController {
    private ProductsDAO productsDAO;

    public ProductController(ProductsDAO productsDAO) {
        this.productsDAO = productsDAO;
    }

    public void ignite() {

        // get by id
        Spark.get("/products/:product-id", (req, res) -> {
            Long id = Long.parseLong(req.params(":product-id"));
            Product product = productsDAO.getByID(id);
            if (product != null) {
                res.type("application/json");
                return ProductView.dump(product);
            } else {
                res.status(404);
                return "Product not found";
            }
        });
        
        // get all
        Spark.get("/products/", (req, res) -> {
            res.type("application/json");
            return ProductView.dump(productsDAO.getAllProducts());
        });

        Spark.redirect.get("/products", "/products/");

        // add product
        Spark.post("/products/", (req, res) -> {
            JsonObject json = Json.parse(req.body()).asObject();
            Product product = ProductDeserializer.deserialize(json);
            product = productsDAO.save(product);
            res.status(201);
            return ProductView.dump(product);
        });

        // update product
        Spark.put("/products/:product-id", (req, res) -> {
            Long id = Long.parseLong(req.params(":product-id"));
            JsonObject json = Json.parse(req.body()).asObject();
            Product product = ProductDeserializer.deserialize(json);
            //product.setId(id);
            Product oldProduct = productsDAO.getByID(id);
            oldProduct.setName(product.getName());
            oldProduct.setPrice(product.getPrice());
            oldProduct.setDescription(product.getDescription());
            productsDAO.update(oldProduct);
            return ProductView.dump(oldProduct);
        });

        // delete product
        Spark.delete("/products/:product-id", (req, res) -> {
            Long id = Long.parseLong(req.params(":product-id"));
            productsDAO.remove(id);
            return "Product deleted";
        });
     }
}