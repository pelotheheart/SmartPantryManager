package com.pelotheheart.smartpantrymanager;

import android.os.Bundle;
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

            // Link home screen buttons
            manageItemsBtn = v.findViewById(R.id.manageItemsBtn);
            viewItemsBtn = v.findViewById(R.id.viewPantryItemsBtn);
            recipeCollectionBtn = v.findViewById(R.id.recipeCollectionBtn);


            return insets;
        });
    }




}