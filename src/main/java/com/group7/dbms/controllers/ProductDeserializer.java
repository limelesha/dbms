package com.group7.dbms;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.JsonArray;
import java.math.BigDecimal;


public class ProductDeserializer {
    public static Product deserialize(JsonObject json) {
        Product product = new Product();
        product.setName(json.getString("name", ""));
        String price = json.get("price") != null ? json.get("price").toString() : "0";
        product.setPrice(new BigDecimal(price));
        product.setDescription(json.getString("description", ""));
        return product;
    }
}