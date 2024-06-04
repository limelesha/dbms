package com.group7.dbms;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.JsonArray;


public class BakeryDeserializer {
    public static Bakery deserialize(JsonObject json) {
        Bakery bakery = new Bakery();
        bakery.setAddress(json.getString("address", ""));
        String openTime = json.get("openTime") != null ? json.get("openTime").toString() : "00:00";
        bakery.setOpenTime(LocalTime.parse(openTime, DateTimeFormatter.ISO_LOCAL_TIME));
        String closeTime = json.get("closeTime") != null ? json.get("closeTime").toString() : "00:00";
        bakery.setCloseTime(LocalTime.parse(closeTime, DateTimeFormatter.ISO_LOCAL_TIME));
        return bakery;
    }
}