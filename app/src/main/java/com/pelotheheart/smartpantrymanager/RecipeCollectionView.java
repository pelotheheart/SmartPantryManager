package com.pelotheheart.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class RecipeCollectionView extends AppCompatActivity {
    Button addRecipeBtn, suggestedRecipeBtn, viewAllRecipeBtn;
    ListView recipeListView;
    TextView feedbackView;
    SmartPantryDB dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recipe_collection_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // db helper instance
        dbhelper = new SmartPantryDB(this);


        // get layout components
        addRecipeBtn = findViewById(R.id.addRecipeBtn);
        suggestedRecipeBtn = findViewById(R.id.suggestedRecipeBtn);
        viewAllRecipeBtn = findViewById(R.id.viewAllRecipesBtn);
        recipeListView = findViewById(R.id.recipeLV);
        feedbackView = findViewById(R.id.feedbackTVRecipeCol);


        // autoload suggested recipes when activity is created
        loadSuggestedRecipes();

        // add action listeners for buttons
        addRecipeBtn.setOnClickListener(v->{
            Intent i = new Intent(RecipeCollectionView.this, AddRecipeActivity.class);
            startActivity(i); // navigate to activity for adding new recipe
        });

        suggestedRecipeBtn.setOnClickListener(v->{
            loadSuggestedRecipes(); //

        });


        viewAllRecipeBtn.setOnClickListener(v->{
            // view all recipes
            // get all from db
            recipeListView.setAdapter(null);// clear list viw adapter
            ArrayList<Recipe> recipes = dbhelper.getAllRecipes();

            if(recipes.isEmpty()){
                feedbackView.setText("No recipes found");
            }else{
                feedbackView.setText("Viewing all recipes");
            // Recipe collection list view adapter
            RecipeCollectionAdapter rca = new RecipeCollectionAdapter(this, recipes);
            recipeListView.setAdapter(rca);

            }
        });



    }

    private void loadSuggestedRecipes(){

        // get suggested recipes based on available ingredients
        recipeListView.setAdapter(null);// clear list view adapter

        // view suggested recipes
        // get all suggested recipes from db
        ArrayList<Recipe> recipes = dbhelper.getSuggestedRecipes();

        if(recipes.isEmpty()){
            feedbackView.setText("No suggested recipes found");
        }else{
            feedbackView.setText("Viewing suggested recipes");
            // Recipe collection list view adapter
            RecipeCollectionAdapter rcSugg = new RecipeCollectionAdapter(this, recipes);
            recipeListView.setAdapter(rcSugg);
        }


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