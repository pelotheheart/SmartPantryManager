package com.pelotheheart.smartpantrymanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;
import java.util.Objects;

public class AddIngredientActivity extends AppCompatActivity {
    TextView headerTxt;
    EditText xdtPicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_ingredient);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Get layout components
        headerTxt = findViewById(R.id.addUpdateTitle);
        xdtPicker = findViewById(R.id.xdtPicker);

        // add click listener for date picker
        xdtPicker.setOnClickListener(v->{
            // Method gets ingredient expiry date from user

            Calendar c = Calendar.getInstance(); // to set default date in date picker
            int year = c.get(Calendar.YEAR), month = c.get(Calendar.MONTH), day = c.get(Calendar.DAY_OF_MONTH);

            //
            DatePickerDialog dtDial = new DatePickerDialog(AddIngredientActivity.this,
                    (view , y , m, d)->{
                        m += 1; // adjust value of month because of 0 based index

                        String dtFormat = String.format("%04d-%02d-%02d", y, m, d); // set date format
                        xdtPicker.setText(dtFormat); // display formatted date in layout
                    }, year, month, day);

            dtDial.show(); // display date picker dialog
        });

        // get mode to see if activity is to add or update ingredients
        String mode = getIntent().getStringExtra("mode");

        if(Objects.equals(mode, "add")){ // set header text to ADD INGREDIENTS if mode is add
            headerTxt.setText(R.string.headerTxtAdd);

        }else{ // otherwise header text is UPDATE INGREDIENT
            headerTxt.setText(R.string.headerTxtUpdate);
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