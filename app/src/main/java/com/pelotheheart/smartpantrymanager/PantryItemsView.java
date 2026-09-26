package com.pelotheheart.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Objects;

public class PantryItemsView extends AppCompatActivity {
    ListView pantryItemsLV;
    SmartPantryDB dbHelper;
    TextView headerTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantry_items_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        headerTxt = findViewById(R.id.pantryItemsHeaderTxt); // Get textview for header text
        String mode = getIntent().getStringExtra("mode"); // get intent mode set by which button user clicked to navigate

        if (mode.equals("update")){ // Set header text depending on intent mode
            headerTxt.setText(R.string.updateItemHeaderTxt);

        }else if(mode.equals("delete")){
            headerTxt.setText(R.string.deleteItemHeaderTxt);
        }else{
            headerTxt.setText(R.string.headerTextPantryItems);
        }


        dbHelper = new SmartPantryDB(this); // database helper instance
        initListView();
    }

    protected void initListView(){
        pantryItemsLV = findViewById(R.id.pantryItemsListView); // reference to list view

        ArrayList<Ingredient> ingredientsList = dbHelper.getIngredients(); // get all ingredients

        ArrayAdapter<Ingredient> lvAdapter = new IngredientsAdapter(this, ingredientsList); // Ingredients adapter instance

        pantryItemsLV.setAdapter(lvAdapter); // assign adapter instance to listview
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