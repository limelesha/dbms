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
            .add("id", Json.value(product.getId()))
            .add("name", Json.value(product.getName()))
            .add("description", Json.value(product.getDescription()))
            .add("price", Json.value(product.getPrice().doubleValue()));
    }

    private static JsonValue partial(Product product) {
        return Json.object()
            .add("id", Json.value(product.getId()))
            .add("name", Json.value(product.getName()))
            .add("price", Json.value(product.getPrice().doubleValue()));
    }

}
