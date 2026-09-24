package com.pelotheheart.smartpantrymanager;
import java.util.ArrayList;

public class Recipe {

    private int recipeID;
    private String title, description;
    private ArrayList<String> ingredients;

    public Recipe(){

    }
    public Recipe(int id, String title, String description, ArrayList<String> ingredients){

        recipeID =id;
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
    }

    public Recipe(String title, String description, ArrayList<String> ingredients){
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
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

    public ArrayList<String> getIngredients(){
        return ingredients;
    }
}
