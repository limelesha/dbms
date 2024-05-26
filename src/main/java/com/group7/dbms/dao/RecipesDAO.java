package com.group7.dbms;

import java.util.List;


public interface RecipesDAO {
    public Recipe getByID(Long id);
    public List<Recipe> getByProductId(Long id);
    public List<Recipe> getAllRecipes();
    public Recipe save(Recipe recipe);
    public void update(Recipe recipe);
    public void remove(Long id);
    public void remove(Recipe recipe);
}