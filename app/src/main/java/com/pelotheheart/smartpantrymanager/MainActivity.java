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

public class MainActivity extends AppCompatActivity {
    Button manageItemsBtn, viewItemsBtn, recipeCollectionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Link home screen buttons
        manageItemsBtn = findViewById(R.id.manageItemsBtn);
        viewItemsBtn = findViewById(R.id.viewPantryItemsBtn);
        recipeCollectionBtn = findViewById(R.id.recipeCollectionBtn);

        manageItemsBtn.setOnClickListener(V->{
            startActivity(new Intent(MainActivity.this, ManagePantryItems.class)); // Button click navigates to Manage Pantry Items activity

        });

        viewItemsBtn.setOnClickListener(V->{
            Intent i = new Intent(MainActivity.this, PantryItemsView.class);
            i.putExtra("mode", "view");
            startActivity(i); // Button click navigates to View Pantry Items activity
        });

        recipeCollectionBtn.setOnClickListener(V->{
            startActivity(new Intent(MainActivity.this, PantryItemsView.class)); // Button click navigates to Recipe Collection activity
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