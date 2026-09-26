package com.pelotheheart.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ManagePantryItems extends AppCompatActivity {
    Button addIngredientBtn, updateIngredientBtn, delIngredientBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_pantry_items);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // get buttons on activity
        addIngredientBtn = findViewById(R.id.addIngredientsBtn);
        updateIngredientBtn = findViewById(R.id.updateIngredientsBtn);
        delIngredientBtn = findViewById(R.id.delIngredientsBtn);

        // add button action listeners
        addIngredientBtn.setOnClickListener(v->{
            Intent i = new Intent(ManagePantryItems.this, AddIngredientActivity.class);
            i.putExtra("mode", "add"); // navigates to add ingredient activity with intent to add new ingredient
            startActivity(i);
        });


        updateIngredientBtn.setOnClickListener(v->{
            Intent i = new Intent(ManagePantryItems.this, PantryItemsView.class);
            i.putExtra("mode", "update"); // Set mode to modify header text
            startActivity(i);

        });

        delIngredientBtn.setOnClickListener(v->{
            Intent i = new Intent(ManagePantryItems.this, PantryItemsView.class);
            i.putExtra("mode", "delete");  // set mode to modify header text
            startActivity(i);
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