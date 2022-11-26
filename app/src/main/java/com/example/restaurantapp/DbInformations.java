package com.example.restaurantapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbInformations extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "DbInformations.db";

    public static final String INFORMATION_TABLE_NAME = "reservations";
    public static final String INFORMATION_COLUMN_ID = "id";
    public static final String INFORMATION_TEXT = "text";

    public DbInformations(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String INITIALIZE_TABLE = String.format(
                "CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT)", INFORMATION_TABLE_NAME, INFORMATION_COLUMN_ID, INFORMATION_TEXT
        );

        db.execSQL(INITIALIZE_TABLE);
        System.out.println("[INFO] Initialized informations db.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", INFORMATION_TABLE_NAME));
        onCreate(db);
    }

    public void initialize()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(INFORMATION_COLUMN_ID, 0);
        cv.put(INFORMATION_TEXT, "Wprowadź godziny otwarcia...");

        db.insert(INFORMATION_TABLE_NAME, null, cv);
    }

    public Cursor getInformation()
    {
        String q = String.format("SELECT * FROM %s", INFORMATION_TABLE_NAME);
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(q, null);
    }

    public void updateInformation(String text)
    {
        ContentValues cv = new ContentValues();

        cv.put(INFORMATION_TEXT, text);

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(INFORMATION_TABLE_NAME, cv, INFORMATION_COLUMN_ID + " = ?", new String[]{String.valueOf(0)});
        db.close();
    }

}
