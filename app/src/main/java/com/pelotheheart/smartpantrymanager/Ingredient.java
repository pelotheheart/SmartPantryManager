package com.pelotheheart.smartpantrymanager;

import androidx.annotation.NonNull;

public class Ingredient {
    private int ingredientID;

    private String ingredientName;
    private  int ingredientQty;
    private  String expiryDate;

    public Ingredient(){}

    public Ingredient(int id, String name,String xdt, int qty){
        ingredientID = id;
        ingredientName =name;
        expiryDate = xdt;
        ingredientQty = qty;
    }

    public Ingredient(String name,String xdt, int qty){
        ingredientName =name;
        expiryDate = xdt;
        ingredientQty = qty;
    }
    // Setters
    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public void setIngredientQty(int ingredientQty) {
        this.ingredientQty = ingredientQty;
    }
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
    // Getters

    public int getIngredientID() {
        return ingredientID;
    }

    public String getIngredientName() {
        return ingredientName;
    }
    public int getIngredientQty() {
        return ingredientQty;
    }
    public String getExpiryDate() {
        return expiryDate;
    }

    @NonNull
    @Override
    public String toString() {
        return getIngredientName()+" | "+getIngredientQty()+" | "+getExpiryDate();
    }
}
