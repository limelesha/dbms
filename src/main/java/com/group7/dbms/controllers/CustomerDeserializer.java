package com.group7.dbms;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.JsonArray;


public class CustomerDeserializer {
    // public static Long extractPersonId(JsonObject json) {
    //     return json.getLong("person", 0);
    // }
    public static String extractDeliveryAddress(JsonObject json) {
        return json.getString("deliveryAddress", "");
    }
    public static Person extractPerson(JsonObject json) {
        Person person = new Person();
        person.setName(json.getString("name", ""));
        person.setEmail(json.getString("email", ""));
        person.setPasswordHash(json.getString("passwordHash", ""));
        return person;
    }
}