package com.pelotheheart.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SmartPantryDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "smartPantryDB";
    private static int VERSION  = 1;
    // Tables in the DB
    private final String RECIPE_TABLE = "recipe_collection", INGREDIENTS_TABLE = "ingredients", RECIPE_INGREDIENTS_TABLE ="recipe_ingredients";
    // Column names for ingredients table
    private final String COL_INGR_ID="id", COL_INGR_NAME ="name", COL_INGR_QTY = "qty", COL_XDT = "expiry_date";
    //Column names for recipes table
    private  final String COL_REC_ID = "id", COL_REC_TITLE =  "title", COL_REC_DESC = "instructions";

    public SmartPantryDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);

    }

    @Override
    public void onConfigure(SQLiteDatabase db){
        db.execSQL("PRAGMA foreign_keys = ON"); // Enable foreign keys
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL to create ingredients table
        String ingred_table_sql = "CREATE TABLE "+INGREDIENTS_TABLE+" ("
                +COL_INGR_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +COL_INGR_NAME+" TEXT NOT NULL,"
                +COL_INGR_QTY+" INTEGER NOT NULL,"
                +COL_XDT+" TEXT)";

        // SQL to create recipes table
        String recipes_table_sql = "CREATE TABLE "+RECIPE_TABLE+" ("
                +COL_REC_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +COL_REC_TITLE+" TEXT NOT NULL,"
                +COL_REC_DESC+" TEXT NOT NULL)";

        // SQL to create recipe ingredients table (Joins ingredients with recipes)
        String recipe_ingredients_sql = "CREATE TABLE "+RECIPE_INGREDIENTS_TABLE+ " ("
                +"rec_id INTEGER NOT NULL,"
                +"ingr_id INTEGER NOT NULL,"
                +"FOREIGN KEY (rec_id) REFERENCES "+RECIPE_TABLE+" ("+COL_REC_ID+"),"
                +"FOREIGN KEY (ingr_id) REFERENCES "+INGREDIENTS_TABLE+" ("+COL_INGR_ID+"))";
        // Execute sql
        db.execSQL(ingred_table_sql);
        db.execSQL(recipes_table_sql);
        db.execSQL(recipe_ingredients_sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertIngredient(Ingredient ingredient){
        SQLiteDatabase db = this.getWritableDatabase(); // Get database to insert values

        ContentValues cv  = new ContentValues(); // Create map of column names and values to be inserted
        cv.put(COL_INGR_NAME, ingredient.getIngredientName());
        cv.put(COL_INGR_QTY, ingredient.getIngredientQty());
        cv.put(COL_XDT, ingredient.getExpiryDate());

        return db.insert(INGREDIENTS_TABLE, null, cv) != -1;// execute query and return true if successful otherwise false
    }

    public boolean updateIngredient(Ingredient ingredient){
        SQLiteDatabase db = this.getWritableDatabase(); // Get database to update ingredient details
        ContentValues cv = new ContentValues();

        cv.put(COL_INGR_NAME, ingredient.getIngredientName());
        cv.put(COL_INGR_QTY, ingredient.getIngredientQty());
        cv.put(COL_XDT, ingredient.getExpiryDate());

        // return true if ingredient row was updated, otherwise false
        return db.update(INGREDIENTS_TABLE, cv, COL_INGR_ID+"=?", new String[]{ String.valueOf(ingredient.getIngredientID()) }) > 0;
    }

    public boolean deleteIngredient(Ingredient ingredient){
        SQLiteDatabase db = this.getWritableDatabase(); // Get database to delete items

        return db.delete(INGREDIENTS_TABLE, COL_INGR_ID+"=?", new String[]{ String.valueOf(ingredient.getIngredientID()) }) > 0;
    }

    public ArrayList<Ingredient> getIngredients(){
        ArrayList<Ingredient> ingredients = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase(); // Get database to retrieve ingredients

        Cursor results = db.rawQuery("SELECT * FROM "+INGREDIENTS_TABLE, null); // execute query

        if(results.moveToFirst()){ // check if any results are returned
            int idCol = results.getColumnIndex(COL_INGR_ID);
            int nameCOl = results.getColumnIndex(COL_INGR_NAME);
            int qtyCol = results.getColumnIndex(COL_INGR_QTY);
            int xdtCol = results.getColumnIndex(COL_XDT);
            do{
                ingredients.add(new Ingredient(

                        results.getInt(idCol),
                        results.getString(nameCOl),
                        results.getString(xdtCol),
                        results.getInt(qtyCol)

                ));

            }while(results.moveToNext());
        }

        results.close();
        return ingredients;
    }

    public boolean insertRecipe(Recipe recipe){



      return true;
    }

    public boolean updateRecipe(Recipe recipe){

        return true;
    }
    public Recipe getRecipe(){
        return new Recipe();
    }
    public ArrayList<Recipe> getAllRecipes(){
        return new ArrayList<>();
    }
    public boolean deletRecipe(Recipe recipe){
        return true;
    }

}
