package com.example.restaurantapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

public class DishActivity extends AppCompatActivity {

    DishContainer mDishContainer;
    Dish mCurrentDish;
    private DbMenu mDbMenu;

    private EditText etDishName;
    private EditText etDishIngredients;
    private EditText etDishRecepie;
    private EditText etDishPrice;
    private EditText etDishWeight;
    private EditText etDishTimeToPrepare;

    private Button bEditDish;
    private Button bDeleteDish;

    @Override
    protected void onCreate(Bundle savedInstanceBundle)
    {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.dish_activity);

        etDishName = findViewById(R.id.etDishName);
        etDishIngredients = findViewById(R.id.etDishIngredients);
        etDishRecepie = findViewById(R.id.etDishRecepie);
        etDishPrice = findViewById(R.id.etDishPrice);
        etDishWeight = findViewById(R.id.etDishWeight);
        etDishTimeToPrepare = findViewById(R.id.etDishTimeToPrepare);
        bEditDish = findViewById(R.id.bEditDish);
        bDeleteDish = findViewById(R.id.bDeleteDish);

        mDbMenu = new DbMenu(this);

        mDishContainer = mDishContainer.get(this);
        Intent intent = getIntent();

        UUID DishUUID = UUID.fromString(getIntent().getStringExtra("DishUUID"));
        mCurrentDish = mDishContainer.getDish(DishUUID);

        etDishName.setText(mCurrentDish.getName());
        etDishIngredients.setText(mCurrentDish.getIngredients());
        etDishRecepie.setText(mCurrentDish.getRecipe());
        etDishPrice.setText(Integer.toString(mCurrentDish.getPrice()));
        etDishWeight.setText(Integer.toString(mCurrentDish.getWeight()));
        etDishTimeToPrepare.setText(Integer.toString(mCurrentDish.getTimeToPrepare()));

        if (MainActivity.CurrentUser != null)
        {
            if (MainActivity.CurrentUser.IsEmployee())
            {
                bEditDish.setVisibility(View.VISIBLE);
                bDeleteDish.setVisibility(View.VISIBLE);
            }
        }
    }

    public void buttonEditDishPressed(View view)
    {
        String dishName = etDishName.getText().toString();
        String dishIngredients = etDishIngredients.getText().toString();
        String dishRecepie = etDishRecepie.getText().toString();
        int dishPrice = Integer.parseInt(etDishPrice.getText().toString());
        int dishWeight = Integer.parseInt(etDishWeight.getText().toString());
        int dishTimeToPrepare = Integer.parseInt(etDishTimeToPrepare.getText().toString());

        mCurrentDish.setName(dishName);
        mCurrentDish.setIngredients(dishIngredients);
        mCurrentDish.setRecipe(dishRecepie);
        mCurrentDish.setPrice(dishPrice);
        mCurrentDish.setWeight(dishWeight);
        mCurrentDish.setTimeToPrepare(dishTimeToPrepare);

        mDbMenu.updateDish(mCurrentDish);

        Toast.makeText(DishActivity.this, "Zaktualizowano danie", Toast.LENGTH_SHORT).show();
    }

    public void buttonDeleteDishPressed(View view)
    {
        mDbMenu.deleteDish(mCurrentDish);
        finish();
    }

}
