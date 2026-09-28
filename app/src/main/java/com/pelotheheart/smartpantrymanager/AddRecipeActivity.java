package com.pelotheheart.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddRecipeActivity extends AppCompatActivity {
    EditText recipeNameED, recipeDescED;
    Button nextActBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_recipe);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Get layout components
        recipeNameED = findViewById(R.id.recipeNameED);
        recipeDescED = findViewById(R.id.recipeInstructionsED);
        nextActBtn = findViewById(R.id.nextActivityBtn);

        // Add click listener for button
        nextActBtn.setOnClickListener(v->{
            // clear error flag
            boolean errors = false;
            // Remove any previous errors
            recipeNameED.setError(null);
            recipeDescED.setError(null);

            String recipeName = recipeNameED.getText().toString();
            String recipeInstructions = recipeDescED.getText().toString();

            // basic check if user provided input for recipe name and instructions
            if (recipeName.trim().isBlank()){
                recipeNameED.setError("Please enter recipe name");
                errors = true;
            }

            if(recipeInstructions.trim().isBlank()){
                recipeDescED.setError("Please recipe description & instructions");
                errors = true;
            }

            if(!errors){ // proceed to add recipe ingredients if no errors from input
                Intent i = new Intent(AddRecipeActivity.this, AddRecipeIngredientsActivity.class);
                i.putExtra("recipeName", recipeName);
                i.putExtra("recipeInstructions", recipeInstructions);

                startActivity(i);
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