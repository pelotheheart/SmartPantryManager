package com.pelotheheart.smartpantrymanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;
import java.util.Objects;

public class AddIngredientActivity extends AppCompatActivity {
    TextView headerTxt, resultView;
    EditText xdtPicker, qtyED, nameED;
    Button saveIngredientBtn;
    SmartPantryDB dbHelper; // DB helper instance
    int ingredientID = 0;
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
        //
        dbHelper = new SmartPantryDB(this); // DB helper instance
        // Get layout components
        headerTxt = findViewById(R.id.addUpdateTitle);
        saveIngredientBtn = findViewById(R.id.saveIngredBtn);
        qtyED = findViewById(R.id.ingredientQtyED);
        nameED = findViewById(R.id.ingredientNameED);
        xdtPicker = findViewById(R.id.xdtPicker);
        resultView = findViewById(R.id.resultView);
        // call to set header text depending on mode ADD | UPDATE
        setHeaderText();
        initDatePicker();


        saveIngredientBtn.setOnClickListener(v->{
            //Clear any previous errors and reset text color to black
            boolean errors = false;
            resultView.setText("");
            resultView.setTextColor(Color.BLACK);
            nameED.setError(null);
            qtyED.setError(null);
            xdtPicker.setError(null);
            // Get user input
            String ingredientName =  nameED.getText().toString();
            int qty = getIntQty(qtyED.getText().toString());
            String expiry = xdtPicker.getText().toString();

            if(!isValidInput(ingredientName)) {
                errors = true; // if any field is empty, set error flag to true
                resultView.append("Ingredient name cannot be empty\n");
                nameED.setError("Ingredient name cannot be empty");
            }

            if(!isValidInput(expiry)){
                errors = true;
                resultView.append("Ingredient expiry date cannot be empty\n");
                xdtPicker.setError("Ingredient expiry date cannot be empty");
            }

            if(qty == -1){
                errors = true;
                resultView.append("Invalid Ingredient quantity\n");
                qtyED.setError("Invalid Ingredient quantity");
            }


            if(!errors){



                if(Objects.equals(getIntent().getStringExtra("mode"), "update")){
                    // If we're in update ingredient mode proceed to update existing ingredient
                    Ingredient updatedIngredient = new Ingredient(ingredientID,ingredientName, expiry, qty);

                    if(dbHelper.updateIngredient(updatedIngredient)){
                        // Show success message if ingredient added successfully
                        resultView.setText("Ingredient Updated Successfully");
                        resultView.setTextColor(Color.GREEN);
                        // Clear input fields
                        nameED.setText("");
                        qtyED.setText("");
                        xdtPicker.setText("");

                    }else{
                        //
                        resultView.setText("Error updating ingredient. Try again");
                        resultView.setTextColor(Color.RED);
                    }


                }else{ // If we're in add new ingredient mode proceed to add new ingredient

                    Ingredient newIngredient = new Ingredient(ingredientName, expiry, qty);

                    if(dbHelper.insertIngredient(newIngredient)){
                        // Show success message if ingredient added successfully
                        resultView.setText("Ingredient Added");
                        resultView.setTextColor(Color.GREEN);
                        // Clear input fields
                        nameED.setText("");
                        qtyED.setText("");
                        xdtPicker.setText("");

                    }else{
                        //
                        resultView.setText("Error adding ingredient. Try again");
                        resultView.setTextColor(Color.RED);
                    }
                }


            }else{
                resultView.setTextColor(Color.RED);
            }

        });

    }

    private void setHeaderText(){
        // get mode to see if activity is to add or update ingredients
        String mode = getIntent().getStringExtra("mode");

        if(Objects.equals(mode, "update")){ // set header text to UPDATE INGREDIENTS if mode is update
            headerTxt.setText(R.string.headerTxtUpdate);
            initInputFields();


        }else{ // otherwise header text is ADD INGREDIENT
            headerTxt.setText(R.string.headerTxtAdd);

        }
    }

    private void initInputFields(){
        ingredientID =  getIntent().getIntExtra("id", 0);
        String name = getIntent().getStringExtra("name");
        int qty = getIntent().getIntExtra("qty", 0);
        String xdt = getIntent().getStringExtra("xdt");
        // Initialize input fields with values from DB
        nameED.setText(name);
        qtyED.setText(String.valueOf(qty));
        xdtPicker.setText(xdt);

    }
    private void initDatePicker(){
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

    }

    public boolean isValidInput(String input){ // Ensure that input fields are not empty
        return !input.trim().isBlank(); // return true if input fields are not empty
    }

    public int getIntQty(String qty){
        int result;
        try{
            result = Integer.parseInt(qty);
        }catch(Exception x){
            result = -1; // return negative if conversion failed
        }
        // converts String input qty to int
        return result;
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