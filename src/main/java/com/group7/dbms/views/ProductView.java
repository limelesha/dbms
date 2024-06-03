package com.group7.dbms;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.JsonArray;


public class ProductView {

    public static String dump(Product product) {
        return full(product).toString();
    }

    public static String dump(Iterable<Product> products) {
        JsonArray json = Json.array();
        for (Product product : products)
            json.add(partial(product));
        return json.toString();
    }

    private static JsonValue full(Product product) {
        return Json.object()
            .add("id", product.getId())
            .add("name", product.getName())
            .add("description", product.getDescription())
            .add("price", product.getPrice().doubleValue());
    }

    private static JsonValue partial(Product product) {
        return Json.object()
            .add("id", product.getId())
            .add("name", product.getName())
            .add("price", product.getPrice().doubleValue());
    }

}
