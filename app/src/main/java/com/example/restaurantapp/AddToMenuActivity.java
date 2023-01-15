package com.example.restaurantapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

public class AddToMenuActivity extends AppCompatActivity {

    private DbMenu mDbMenu;

    private EditText etDishName;
    private EditText etDishIngredients;
    private EditText etDishRecepie;
    private EditText etDishPrice;
    private EditText etDishWeight;
    private EditText etDishTimeToPrepare;
    private EditText etAllergens;

    @Override
    protected void onCreate(Bundle savedInstanceBundle)
    {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.add_to_menu_activity);

        etDishName = findViewById(R.id.etDishName);
        etDishIngredients = findViewById(R.id.etDishIngredients);
        etDishRecepie = findViewById(R.id.etDishRecepie);
        etDishPrice = findViewById(R.id.etDishPrice);
        etDishWeight = findViewById(R.id.etDishWeight);
        etDishTimeToPrepare = findViewById(R.id.etDishTimeToPrepare);
        etAllergens = findViewById(R.id.etAllergens);

        mDbMenu = new DbMenu(this);
    }

    public void buttonAddToMenuPressed(View view)
    {
        UUID dishId = UUID.randomUUID();
        String dishName = etDishName.getText().toString();
        String dishIngredients = etDishIngredients.getText().toString();
        String dishRecepie = etDishRecepie.getText().toString();
        int dishPrice = Integer.parseInt(etDishPrice.getText().toString());
        int dishWeight = Integer.parseInt(etDishWeight.getText().toString());
        int dishTimeToPrepare = Integer.parseInt(etDishTimeToPrepare.getText().toString());
        String dishAllergens = etAllergens.getText().toString();

        Dish dish = new Dish(dishId, dishName, dishIngredients, dishRecepie, dishPrice, dishWeight, dishTimeToPrepare, dishAllergens);

        mDbMenu.addDish(dish);

        Toast.makeText(AddToMenuActivity.this, "Dodano danie", Toast.LENGTH_SHORT).show();
    }

}
