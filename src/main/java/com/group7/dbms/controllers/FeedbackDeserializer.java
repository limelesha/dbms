package com.group7.dbms;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.JsonArray;

public class FeedbackDeserializer {
    public static Long extractAuthorId(JsonObject json) {
        return json.getLong("authorId", 0);
    }
    public static int extractRating(JsonObject json) {
        return json.getInt("rating", 3);
    }
    public static String extractComment(JsonObject json) {
        return json.getString("comment", "");
    }
}