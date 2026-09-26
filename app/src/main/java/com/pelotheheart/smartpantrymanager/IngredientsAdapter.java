package com.pelotheheart.smartpantrymanager;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;

public class IngredientsAdapter extends ArrayAdapter<Ingredient> {

    public IngredientsAdapter(@NonNull Context c,  ArrayList<Ingredient> data){
        super(c, 0, data); // Pass app context and ingredients list to parent constructor
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){

        View cv = convertView; // get current row

        if(cv == null){ // check if current row is reusable or create a new one
            cv = LayoutInflater.from(getContext()).inflate(R.layout.lv_item, parent, false);
        }

        Ingredient ingredient = getItem(position); // get  ingredient at position

        if (ingredient == null)
            return cv;  // return empty view if no ingredient is found

        // Get list view components and set ingredient values - name, qty and expiry
        ImageView icon = cv.findViewById(R.id.ingredIcon);
        icon.setImageResource(R.drawable.shopping_bag_small);

        TextView titleView = cv.findViewById(R.id.listItem_title);
        titleView.setText(String.format("Name: %s", ingredient.getIngredientName()));

        TextView qtyView = cv.findViewById(R.id.listitem_qty);
        qtyView.setText(String.format("Qty: %s", String.valueOf(ingredient.getIngredientQty())));

        TextView xdtView = cv.findViewById(R.id.listItem_xdt);
        xdtView.setText(String.format("Expiry: %s", ingredient.getExpiryDate()));

        return cv;
    }
}
