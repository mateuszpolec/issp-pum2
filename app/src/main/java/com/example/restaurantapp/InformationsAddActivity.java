package com.example.restaurantapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class InformationsAddActivity extends AppCompatActivity {

    private DbInformations mDbInformations;

    private EditText etInformations;

    @Override
    protected void onCreate(Bundle savedInstanceBundle)
    {
        super.onCreate(savedInstanceBundle);

        setContentView(R.layout.informations_add_activity);

        etInformations = findViewById(R.id.etInformations);

        // Do it only once to initialize the informations.
        mDbInformations = new DbInformations(this);

        Cursor c = mDbInformations.getInformation();

        if (c.getCount() == 0)
        {
            mDbInformations.initialize();
        }

        // Get cursor one more time to fill the data to immadietly modify it.

        c = mDbInformations.getInformation();

        if (c.moveToFirst())
        {
            etInformations.setText(c.getString(1));
        }
    }

    public void buttonUpdateInformationsPressed(View view)
    {
        mDbInformations.updateInformation(etInformations.getText().toString());

        Toast.makeText(InformationsAddActivity.this, "Zaktualizowano godziny otwarcia", Toast.LENGTH_SHORT).show();
    }

}
