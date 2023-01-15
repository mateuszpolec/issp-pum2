package com.example.restaurantapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class EmployeeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.employee_activity);
    }

    public void buttonShowMenuPressed(View view)
    {
        System.out.println("[INFO][LoginActivity] Button show menu pressed.");

        Intent menuActivity = new Intent(view.getContext(), MenuActivity.class);
        view.getContext().startActivity(menuActivity);
    }

    public void buttonAddToMenuPressed(View view)
    {
        System.out.println("[INFO][LoginActivity] Button add to menu pressed.");

        Intent addToMenuActivity = new Intent(view.getContext(), AddToMenuActivity.class);
        view.getContext().startActivity(addToMenuActivity);
    }

    public void buttonShowInformationsPressed(View view)
    {
        Intent showInformations = new Intent(view.getContext(), InformationsActivity.class);
        view.getContext().startActivity(showInformations);
    }

    public void buttonUpdateInformationsPressed(View view)
    {
        Intent updateInformations = new Intent(view.getContext(), InformationsAddActivity.class);
        view.getContext().startActivity(updateInformations);
    }

    public void buttonAddOrderPressed(View view)
    {
        Intent addOrderActivity = new Intent(view.getContext(), OrderAddEmployeeActivity.class);
        view.getContext().startActivity(addOrderActivity);
    }

    public void buttonShowOrdersPressed(View view)
    {
        Intent showOrdersActivity = new Intent(view.getContext(), OrderListActivity.class);
        view.getContext().startActivity(showOrdersActivity);
    }

}
