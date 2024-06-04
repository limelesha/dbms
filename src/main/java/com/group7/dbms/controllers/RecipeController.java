package com.group7.dbms;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.JsonArray;
// import com.google.gson.Gson;
// import com.google.gson.GsonBuilder;

import java.util.List;

import spark.Request;
import spark.Response;
import spark.Spark;

public class RecipeController {
    private ProductsDAO productsDAO;
    private RecipesDAO recipesDAO;
    // private Deserializer<Recipe> deserializer;
    // private static Gson partialRepresentationGson;
    // private static Gson fullRepresentationGson;

    

    public RecipeController(RecipesDAO recipesDAO, ProductsDAO productsDAO) {
        this.recipesDAO = recipesDAO;
        this.productsDAO = productsDAO;
    }

    public void ignite() {

        // get by id
        Spark.get("/recipes/:recipe-id", (req, res) -> {
            Long id = Long.parseLong(req.params(":recipe-id"));
            Recipe recipe = recipesDAO.getByID(id);
            if (recipe != null) {
                res.type("application/json");
                return RecipeView.dump(recipe);
            } else {
                res.status(404);
                return "Recipe not found";
            }
        });

        // get by product id 
        Spark.get("/products/:product-id/recipes/", (req, res) -> {
            Long id = Long.parseLong(req.params(":product-id"));
            Product product = productsDAO.getByID(id);
            if (product != null) {
                List<Recipe> recipes = recipesDAO.getByProductId(id);
                if (!recipes.isEmpty()) {
                    res.type("application/json");
                    return RecipeView.dump(recipes);
                } else {
                    res.status(404);
                    return "Recipe not found";
                }
                
            } else {
                res.status(404);
                return "Product not found";
            }
        });

        // get all
        Spark.get("/recipes/", (req, res) -> {
            res.type("application/json");
            return RecipeView.dump(recipesDAO.getAllRecipes());
        });

        Spark.redirect.get("/recipes", "/recipes/");

        // add 
        Spark.post("/recipes/", (req, res) -> {
            JsonObject json = Json.parse(req.body()).asObject();
            Recipe recipe = new Recipe();
            String instructions = RecipeDeserializer.extractInstruction(json);
            Long productId = RecipeDeserializer.extractProductId(json);
            Product product = productsDAO.getByID(productId);
            recipe.setInstructions(instructions);
            recipe.setProduct(product);
            recipe = recipesDAO.save(recipe);
            res.status(201);
            return RecipeView.dump(recipe);
        });

        // update 
        Spark.put("/recipes/:recipe-id", (req, res) -> {
            Long id = Long.parseLong(req.params(":recipe-id"));
            JsonObject json = Json.parse(req.body()).asObject();
            Recipe recipe = recipesDAO.getByID(id);
            String instructions = RecipeDeserializer.extractInstruction(json);
            
            recipe.setInstructions(instructions);
            
            recipesDAO.update(recipe);
            return RecipeView.dump(recipe);
        });

        // delete 
        Spark.delete("/recipes/:recipe-id", (req, res) -> {
            Long id = Long.parseLong(req.params(":recipe-id"));
            recipesDAO.remove(id);
            return "Recipe deleted";
        });
    }
}
