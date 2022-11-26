package com.example.restaurantapp;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class InformationsActivity extends AppCompatActivity {

    private DbInformations mDbInformations;

    private TextView tvInformations;

    @Override
    protected void onCreate(Bundle savedInstanceBundle)
    {
        super.onCreate(savedInstanceBundle);

        setContentView(R.layout.informations_activity);

        tvInformations = findViewById(R.id.tvInformations);

        mDbInformations = new DbInformations(this);

        Cursor c = mDbInformations.getInformation();

        if (c.getCount() > 0)
        {
            if (c.moveToFirst())
            {
                tvInformations.setText(c.getString(1));
            }
        }


    }

}
