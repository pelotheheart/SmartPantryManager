package com.pelotheheart.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class AddRecipeIngredientsActivity extends AppCompatActivity {
    Button saveRecipeBtn;
    ListView ingredientsLV;
    SmartPantryDB dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_recipe_ingredients);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Get layout components
        saveRecipeBtn = findViewById(R.id.saveRecipeBtn);
        ingredientsLV = findViewById(R.id.ingredientsListView);

        // DBhelper instance
        dbHelper = new SmartPantryDB(this);

        ArrayList<Ingredient> ingredientsList = dbHelper.getIngredients(); // get all ingredients

       AdapterRCI rciAdapter = new AdapterRCI(this, ingredientsList); // Ingredients adapter instance

        ingredientsLV.setAdapter(rciAdapter); // assign adapter instance to listview

        // Get recipe name and instructions from previous screen through intent
        String recipeName = getIntent().getStringExtra("recipeName");
        String recipeInstructions = getIntent().getStringExtra("recipeInstructions");

        // add action listener for save recipe button
        saveRecipeBtn.setOnClickListener(v->{
            // Create new recipe instance with recipe name, instructions and selected ingredients
            Recipe newRecipe = new Recipe(recipeName, recipeInstructions,rciAdapter.getRecipeIngredients() );

            // Insert new recipe into DB
            if(dbHelper.insertRecipe(newRecipe)){
                // if successful navigate to recipe collection activity
                startActivity(new Intent(AddRecipeIngredientsActivity.this, RecipeCollectionView.class));
            }else{ // otherwise display error in toast
                Toast.makeText(AddRecipeIngredientsActivity.this, "Failed To Add Recipe + Ingredients. Try again", Toast.LENGTH_LONG).show();
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){ // Create App Menu
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // set click listeners for menu items
        int id = item.getItemId();

        // check which item was clicked and navigate to correct activity
        if (id == R.id.menu_home) {
            startActivity(new Intent(this, MainActivity.class));
            return true;
        } else if (id == R.id.menu_manage_items) {
            startActivity(new Intent(this, ManagePantryItems.class));
            return true;
        } else if (id == R.id.menu_view_items) {
            startActivity(new Intent(this, PantryItemsView.class));
            return true;
        } else if (id == R.id.menu_recipe_collection) {
            startActivity(new Intent(this, RecipeCollectionView.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}