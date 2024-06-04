package com.group7.dbms;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.JsonArray;
import java.math.BigDecimal;


public class RecipeDeserializer {
    public static String extractInstruction(JsonObject json) {
        return json.getString("instructions", "");
    }

    public static Long extractProductId(JsonObject json) {
        return json.getLong("product", 0);
    }
}