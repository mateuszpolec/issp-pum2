package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    static User CurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonStartLoginActivity(View view)
    {
        Intent loginActivity = new Intent(view.getContext(), LoginActivity.class);
        view.getContext().startActivity(loginActivity);
    }

    public void buttonShowMenu(View view)
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