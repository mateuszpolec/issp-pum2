package com.example.restaurantapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private DbUsers mDbUsers;

    EditText etUserName;
    EditText etUserPassword;
    EditText etUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        mDbUsers = new DbUsers(this);
    }

    public void buttonRegisterPressed(View view)
    {
        etUserName = findViewById(R.id.etUserName);
        etUserPassword = findViewById(R.id.etUserPassword);
        etUserEmail = findViewById(R.id.etUserPassword);

        String userName = etUserName.getText().toString();
        String userPassword = etUserPassword.getText().toString();
        String userEmail = etUserEmail.getText().toString();

        boolean correct = true;
        Cursor c = mDbUsers.getUsers();

        while (c.moveToNext())
        {
            String cursorUserName = c.getString(2);
            String cursorEmail = c.getString(4);

            System.out.println(cursorUserName);

            if (userName.equals(cursorUserName))
            {
                System.out.println("This user already exists!");
                correct = false;
                break;
            }

            if (userEmail.equals(cursorEmail))
            {
                System.out.println("Account with that mail already exists!");
                correct = false;
                break;
            }
        }

        if (correct)
        {
            User u = new User();

            u.setName(userName);
            u.setPassword(userPassword);
            u.setEmail(userEmail);

            mDbUsers.addUser(u);
        }
    }


}
