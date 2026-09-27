package com.pelotheheart.smartpantrymanager;
import java.util.ArrayList;

public class Recipe {

    private int recipeID;
    private String title, description;
    private ArrayList<Ingredient> ingredients;

    public Recipe(){

    }

    public Recipe(int id, String title, String description){
        recipeID =id;
        this.title = title;
        this.description = description;
    }

    public Recipe(String title, String description, ArrayList<Ingredient> ingredients){
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
    }

    // Setters
    public  void setIngredients(ArrayList<Ingredient> ingredientsList){
        this.ingredients =ingredientsList;
    }

    // Getters

    public int getRecipeID() {
        return recipeID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription(){
        return  description;
    }

    public ArrayList<Ingredient> getIngredients(){
        return ingredients;
    }
}
