package com.example.restaurantapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.UUID;

public class DbUsers extends SQLiteOpenHelper {

    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "DbUsers.db";

    public static final String USER_TABLE_NAME = "users";
    public static final String USER_COLUMN_ID = "id";
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "name";
    public static final String USER_PASSWORD = "password";
    public static final String USER_EMAIL = "email";
    public static final String USER_PERMISSION = "permission";

    public DbUsers(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String INITIALIZE_TABLE = String.format(
                "CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s INTEGER)", USER_TABLE_NAME, USER_COLUMN_ID, USER_ID, USER_NAME, USER_PASSWORD, USER_EMAIL, USER_PERMISSION
        );

        db.execSQL(INITIALIZE_TABLE);
        System.out.println("[INFO] Initialized users db.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", USER_TABLE_NAME));
        onCreate(db);
    }

    public Cursor getUsers()
    {
        String q = String.format("SELECT * FROM %s", USER_TABLE_NAME);

        SQLiteDatabase db = this.getWritableDatabase();

        return db.rawQuery(q, null);
    }

    public void addUser(User user)
    {
        ContentValues cv = new ContentValues();

        cv.put(USER_ID, user.getId().toString());
        cv.put(USER_NAME, user.getName());
        cv.put(USER_PASSWORD, user.getPassword());
        cv.put(USER_EMAIL, user.getEmail());
        cv.put(USER_PERMISSION, user.getPermission().ordinal());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(USER_TABLE_NAME, null, cv);
        db.close();
    }

    public void deleteUser(User user)
    {
        String uuidString = user.getId().toString();

        String q = String.format("SELECT * FROM %s WHERE %s = %s", USER_TABLE_NAME, USER_ID, uuidString);

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(USER_TABLE_NAME, USER_ID + " = ?", new String[]{String.valueOf(uuidString)});
        db.close();
    }

    public void updateUser(User user)
    {
        ContentValues cv = new ContentValues();

        cv.put(USER_ID, user.getId().toString());
        cv.put(USER_NAME, user.getName());
        cv.put(USER_PASSWORD, user.getPassword());
        cv.put(USER_EMAIL, user.getEmail());
        cv.put(USER_PERMISSION, user.getPermission().ordinal());

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(USER_TABLE_NAME, cv, USER_ID + " = ?", new String[]{String.valueOf(user.getId().toString())});
        db.close();
    }

    public void Initialize()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String CLEAR_DB = String.format("DELETE FROM %s", USER_TABLE_NAME);

        db.execSQL(CLEAR_DB);

        ContentValues cvOwner = new ContentValues();
        ContentValues cvCook = new ContentValues();
        ContentValues cvWaiter = new ContentValues();

        cvOwner.put(USER_ID, UUID.randomUUID().toString());
        cvOwner.put(USER_NAME, "Owner");
        cvOwner.put(USER_PASSWORD, "Owner");
        cvOwner.put(USER_EMAIL, "Owner@restaurant.com");
        cvOwner.put(USER_PERMISSION, Permissions.Type.OWNER.ordinal());

        cvCook.put(USER_ID, UUID.randomUUID().toString());
        cvCook.put(USER_NAME, "Cook");
        cvCook.put(USER_PASSWORD, "Cook");
        cvCook.put(USER_EMAIL, "Cook@restaurant.com");
        cvCook.put(USER_PERMISSION, Permissions.Type.COOK.ordinal());

        cvWaiter.put(USER_ID, UUID.randomUUID().toString());
        cvWaiter.put(USER_NAME, "Waiter");
        cvWaiter.put(USER_PASSWORD, "Waiter");
        cvWaiter.put(USER_EMAIL, "Waiter@restaurant.com");
        cvWaiter.put(USER_PERMISSION, Permissions.Type.WAITER.ordinal());

        db.insert(USER_TABLE_NAME, null, cvOwner);
        db.insert(USER_TABLE_NAME, null, cvCook);
        db.insert(USER_TABLE_NAME, null, cvWaiter);
    }

}
