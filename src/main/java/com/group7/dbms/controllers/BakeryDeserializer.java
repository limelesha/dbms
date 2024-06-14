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
        String openTime = json.getString("openTime", "00:00");
        bakery.setOpenTime(LocalTime.parse(openTime, DateTimeFormatter.ISO_LOCAL_TIME));
        String closeTime = json.getString("closeTime", "00:00");
        bakery.setCloseTime(LocalTime.parse(closeTime, DateTimeFormatter.ISO_LOCAL_TIME));
        return bakery;
    }
}