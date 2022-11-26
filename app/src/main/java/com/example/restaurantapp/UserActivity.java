package com.example.restaurantapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceBundle)
    {
        super.onCreate(savedInstanceBundle);

        setContentView(R.layout.user_activity);
    }

    public void buttonShowMenuPressed(View view)
    {
        Intent menuActivity = new Intent(view.getContext(), MenuActivity.class);
        view.getContext().startActivity(menuActivity);
    }

    public void buttonShowInformations(View view)
    {
        Intent informationsActivity = new Intent(view.getContext(), InformationsActivity.class);
        view.getContext().startActivity(informationsActivity);
    }
}
