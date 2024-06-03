package com.group7.dbms;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.JsonArray;


public class FeedbackView {

    public static String dump(Feedback feedback) {
        return full(feedback).toString();
    }

    public static String dump(Iterable<Feedback> feedbackPool) {
        JsonArray json = Json.array();
        for (Feedback feedback : feedbackPool)
            json.add(full(feedback));
        return json.toString();
    }

    private static JsonValue full(Feedback feedback) {
        return Json.object()
            .add("authorId", feedback.getAuthor().getId())
            .add("authorName", feedback.getAuthor().getName())
            .add("product", feedback.getProduct().getId())
            .add("rating", feedback.getRating())
            .add("comment", feedback.getComment());
    }

}
