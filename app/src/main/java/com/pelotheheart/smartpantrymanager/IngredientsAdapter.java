package com.pelotheheart.smartpantrymanager;

import static androidx.core.content.ContextCompat.createAttributionContext;
import static androidx.core.content.ContextCompat.startActivities;
import static androidx.core.content.ContextCompat.startActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

        Button editBtn = cv.findViewById(R.id.editItemBtn);
        Button delBtn = cv.findViewById(R.id.delItemBtn);

        // Add click listeners for buttons
        editBtn.setOnClickListener(v->{

            Intent editIntent = new Intent(getContext(), AddIngredientActivity.class);
            // Pass ingredient data to be edited
            editIntent.putExtra("mode", "update"); //
            editIntent.putExtra("id", ingredient.getIngredientID());
            editIntent.putExtra("name", ingredient.getIngredientName());
            editIntent.putExtra("qty", ingredient.getIngredientQty());
            editIntent.putExtra("xdt", ingredient.getExpiryDate());
            editIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            getContext().startActivity(editIntent); // navigate to edit ingredient details

        });


        delBtn.setOnClickListener(v->{
            // Create confirmation dialog box for delete action
            AlertDialog.Builder confirmDialog = new AlertDialog.Builder(getContext());
            confirmDialog.setTitle("DELETE INGREDIENT");
            confirmDialog.setMessage(String.format("Do you want to delete ingredient %s?", ingredient.getIngredientName()));
            // action buttons - yes and no
            confirmDialog.setPositiveButton("Yes",  (DialogInterface.OnClickListener) (dialog, which) -> {

                // delete ingredient when the user clicks yes
                SmartPantryDB db  = new SmartPantryDB(getContext());
                if(db.deleteIngredient(ingredient)){
                    remove(ingredient); // remove ingredient object from adapter
                    notifyDataSetChanged(); // refresh listview when ingredient is deleted
                    Toast.makeText(getContext(), "Ingredient deleted", Toast.LENGTH_LONG).show();
                }else{// show error message if failed to delete
                    Toast.makeText(getContext(), "Failed to delete ingredient", Toast.LENGTH_LONG).show();

                }
            });

            confirmDialog.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                // Close dialog box if user clicks no
                dialog.cancel();
            });

            // Create and show dialog box
            confirmDialog.create();
            confirmDialog.show();

        });

        return cv;
    }
}
