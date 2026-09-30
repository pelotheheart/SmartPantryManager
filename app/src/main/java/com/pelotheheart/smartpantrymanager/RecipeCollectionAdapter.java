package com.pelotheheart.smartpantrymanager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;

public class RecipeCollectionAdapter extends ArrayAdapter<Recipe> {

    public RecipeCollectionAdapter(@NonNull Context c, ArrayList<Recipe> data){
        super(c, 0, data); // Pass app context and recipe list to parent constructor
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){

        View cv = convertView; // get current row

        if(cv == null){ // check if current row is reusable or create a new one
            cv = LayoutInflater.from(getContext()).inflate(R.layout.lv_recipecollection, parent, false);
        }

        Recipe recipe  = getItem(position); // get  recipe at position

        if (recipe == null)
            return cv;  // return empty view if no recipe is found

        // Get list view components and set ingredient name

        TextView recipeName = cv.findViewById(R.id.lvRecipeName);
        recipeName.setText(String.format("%s", recipe.getTitle()));

        Button viewDetailsBtn = cv.findViewById(R.id.viewRecipeDetailBtn); // get recipe button to view details

        viewDetailsBtn.setOnClickListener(v->{ // to navigate to recipe detail activity
            Intent i = new Intent(getContext(), RecipeDetailActivity.class);
            i.putExtra("recipeID", recipe.getRecipeID()); // pass recipe ID through intent

            getContext().startActivity(i);  // navigate to recipe detail activity when button is clicked
        });


        return cv;
    }

}
