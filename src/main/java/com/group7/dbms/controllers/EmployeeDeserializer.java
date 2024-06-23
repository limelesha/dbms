package com.group7.dbms;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.JsonArray;


public class EmployeeDeserializer {
    public static Long extractLocationId(JsonObject json) {
        return json.getLong("location", 0);
    }
    public static Role extractRole(JsonObject json) {
        return Role.valueOf(json.getString("role", ""));
    }
}