package com.example.restaurantapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderAddEmployeeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;

    private EditText etComments;
    private EditText etTableNumber;
    private EditText etTime;
    private EditText etTelephoneNumber;
    private TextView tvCurrentOrder;
    private TextView tvCurrentPrice;
    private TextView tvCurrentAllergens;

    private DbMenu mDbMenu;
    private DbOrders mDbOrders;
    private List<Dish> mDishes = DishContainer.get(this).getDishes();
    private List<Dish> mCurrentOrderDishes;
    private int mCurrentSpinnerIndex;
    private int mCurrentTotalPrice;

    private UUID userUUID;

    @Override
    protected void onCreate(Bundle savedInstanceBundle)
    {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.order_add_employee_activity);

        userUUID = CurrentUser.user.getId();

        mDbMenu = new DbMenu(this);
        mDbOrders = new DbOrders(this);

        mCurrentTotalPrice = 0;

        etTelephoneNumber = findViewById(R.id.etTelephoneNumber);
        etComments = findViewById(R.id.etComments);
        etTableNumber = findViewById(R.id.etTableNumber);
        etTime = findViewById(R.id.etTime);

        tvCurrentOrder = findViewById(R.id.tvCurrentOrder);
        tvCurrentPrice = findViewById(R.id.tvCurrentPrice);
        tvCurrentAllergens = findViewById(R.id.tvCurrentAllergens);

        spinner = (Spinner) findViewById(R.id.sSelectDish);
        initializeSpinner();

        if (CurrentUser.user.IsEmployee())
        {
            initializeEmployee();
        }
        else
        {
            initializeUser();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id)
    {
        mCurrentSpinnerIndex = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
        // TODO Auto-generated method stub
    }

    private void initializeEmployee()
    {
        etTime.setVisibility(View.GONE);
        etTelephoneNumber.setVisibility(View.GONE);
    }

    private void initializeUser()
    {
        etTableNumber.setVisibility(View.GONE);
    }

    private void initializeSpinner()
    {
        Cursor c = mDbMenu.getMenu();
        mDishes.clear();

        mCurrentOrderDishes = new ArrayList<Dish>();

        ArrayList<String> dishes = new ArrayList<String>();

        while(c.moveToNext())
        {
            // Prepare spinner.
            dishes.add(c.getString(2));

            // Prepare data in array to get the dish when selected on spinner.
            UUID dishId = UUID.fromString(c.getString(1));
            String dishName = c.getString(2);
            String dishIngredients = c.getString(3);
            String dishRecepie = c.getString(4);
            int dishPrice = c.getInt(5);
            int dishWeight = c.getInt(6);
            int dishTimeToPrepare = c.getInt(7);
            String dishAllergens = c.getString(8);

            mDishes.add(new Dish(dishId, dishName, dishIngredients, dishRecepie, dishPrice, dishWeight, dishTimeToPrepare, dishAllergens));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(OrderAddEmployeeActivity.this, android.R.layout.simple_spinner_dropdown_item, dishes);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void buttonAddToBasketPressed(View view)
    {
        Dish dish = mDishes.get(mCurrentSpinnerIndex);

        String currentOrderText = tvCurrentOrder.getText().toString();

        String dishName = dish.getName();

        currentOrderText += dishName + ", ";

        tvCurrentOrder.setText(currentOrderText);
        mCurrentTotalPrice += dish.getPrice();

        String currentPriceText = tvCurrentPrice.getText().toString();

        String totalPrice = Integer.toString(mCurrentTotalPrice);

        currentPriceText = "Aktualna cena: " + totalPrice + " zł";
        tvCurrentPrice.setText(currentPriceText);

        mCurrentOrderDishes.add(mDishes.get(mCurrentSpinnerIndex));

        String tempAllergens = "";

        // Update allergens list.
        for (Dish d : mCurrentOrderDishes)
        {
            tempAllergens += d.getAllergens() + ", ";
        }

        tvCurrentAllergens.setText("Lista alergenów: " + tempAllergens);
    }

    public void buttonFinishOrderPressed(View view)
    {
        String orderDishNames = "";
        String orderComments = etComments.getText().toString();

        for (Dish d : mCurrentOrderDishes)
        {
            orderDishNames += d.getName() + " ";
        }

        if (CurrentUser.user.IsEmployee())
        {
            int orderTableNumber = Integer.parseInt(etTableNumber.getText().toString());

            Order order = new Order(UUID.randomUUID(), userUUID, orderDishNames, orderComments, "Restauracja", 000000000, mCurrentTotalPrice, orderTableNumber, Order.Status.ORDERED, Order.WayOfServing.IN_RESTAURANT, "00:00");
            mDbOrders.addOrder(order);
        }
        else
        {
            int orderTelephoneNumber = Integer.parseInt(etTelephoneNumber.getText().toString());
            String orderPickupTime = etTime.getText().toString();

            Order order = new Order(UUID.randomUUID(), userUUID, orderDishNames, orderComments, "Restauracja", orderTelephoneNumber, mCurrentTotalPrice, -1, Order.Status.ORDERED, Order.WayOfServing.TAKEAWAY, orderPickupTime);
            mDbOrders.addOrder(order);
        }

        Toast.makeText(OrderAddEmployeeActivity.this, "Zamówienie pomyślnie dodane!", Toast.LENGTH_LONG).show();
    }
}
