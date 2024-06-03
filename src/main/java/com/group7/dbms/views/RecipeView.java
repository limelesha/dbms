package com.group7.dbms;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.JsonArray;


public class RecipeView {

    public static String dump(Recipe recipe) {
        return full(recipe).toString();
    }

    public static String dump(Iterable<Recipe> recipes) {
        JsonArray json = Json.array();
        for (Recipe recipe : recipes)
            json.add(partial(recipe));
        return json.toString();
    }

    private static JsonValue full(Recipe recipe) {
        return Json.object()
            .add("id", recipe.getId())
            .add("product", recipe.getProduct().getId())
            .add("instructions", recipe.getInstructions());
    }

    private static JsonValue partial(Recipe recipe) {
        return Json.object()
            .add("id", recipe.getId())
            .add("product", recipe.getProduct().getId());
    }

}
