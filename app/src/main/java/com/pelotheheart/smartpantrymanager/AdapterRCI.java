package com.pelotheheart.smartpantrymanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;

public class AdapterRCI extends ArrayAdapter<Ingredient> {

    private final ArrayList<Ingredient> selectedIngredients = new ArrayList<>(); // to store selected ingredients for recipe
    public AdapterRCI(@NonNull Context c, ArrayList<Ingredient> data){
        super(c, 0, data); // Pass app context and ingredients list to parent constructor
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){

        View cv = convertView; // get current row

        if(cv == null){ // check if current row is reusable or create a new one
            cv = LayoutInflater.from(getContext()).inflate(R.layout.lv_recipeingredients, parent, false);
        }

        Ingredient ingredient  = getItem(position); // get  ingredient at position

        if (ingredient == null)
            return cv;  // return empty view if no ingredient is found

        // Get list view components and set ingredient name
        ImageView icon = cv.findViewById(R.id.ingredIcon);
        icon.setImageResource(R.drawable.shopping_bag_small);

        TextView titleView = cv.findViewById(R.id.ingredientName);
        titleView.setText(String.format("%s", ingredient.getIngredientName()));

        CheckBox ingredientCB = cv.findViewById(R.id.ingredientCheckBox); // get ingredient checkbox

        ingredientCB.setOnCheckedChangeListener(null); // prevent unintended checks on ingredients when scrolling
        ingredientCB.setChecked(selectedIngredients.contains(ingredient)); // if current ingredient was selected, set checkbox checked

        ingredientCB.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // add listener for ingredient checkbox
            if(isChecked){  // if checked and not in selectedIngredients, add to selectedIngredients
                if(!selectedIngredients.contains(ingredient)){
                    selectedIngredients.add(ingredient);
                }
            } else { // otherwise remove from selectedIngredients
                selectedIngredients.remove(ingredient);
            }
        });


        return cv;
    }

    public ArrayList<Ingredient> getRecipeIngredients(){
        return selectedIngredients; // method return selected recipe ingredients
    }
}
