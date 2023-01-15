package com.example.restaurantapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

public class LoginActivity extends AppCompatActivity {

    private DbUsers mDbUsers;

    EditText etUserName;
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        mDbUsers = new DbUsers(this);
    }

    public void buttonLoginPressed(View view)
    {
        System.out.println("[INFO][LoginActivity] Button login pressed.");

        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etUserPassword);

        String userName = etUserName.getText().toString();
        String userPassword = etPassword.getText().toString();

        Cursor c = mDbUsers.getUsers();

        // O(n)

        while (c.moveToNext())
        {
            String cursorUUID = c.getString(1);
            String cursorUserName = c.getString(2);
            String cursorPassword = c.getString(3);

            if (userName.equals(cursorUserName) && userPassword.equals(cursorPassword))
            {
                System.out.println("[INFO][LoginActivity] User found.");

                String cursorUserEmail = c.getString(4);
                Permissions.Type permission = Permissions.Type.values()[c.getInt(5)];

                User u = new User(UUID.fromString(cursorUUID), userName, userPassword, cursorUserEmail, permission);

                CurrentUser.user = u;

                if (u.IsEmployee()) {
                    Intent employeeActivity = new Intent(view.getContext(), EmployeeActivity.class);
                    view.getContext().startActivity(employeeActivity);
                }
                else
                {
                    Intent userActivity = new Intent(view.getContext(), UserActivity.class);
                    view.getContext().startActivity(userActivity);
                }
            }
        }
    }

    public void buttonRegisterPressed(View view)
    {
        Intent registerActivity = new Intent(view.getContext(), RegisterActivity.class);
        view.getContext().startActivity(registerActivity);
    }

    public void buttonResetDatabasePressed(View view)
    {
        mDbUsers.Initialize();
    }
}
