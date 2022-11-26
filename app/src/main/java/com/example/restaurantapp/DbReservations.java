package com.example.restaurantapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbReservations extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "DbReservations.db";

    public static final String RESERVATION_TABLE_NAME = "reservations";
    public static final String RESERVATION_COLUMN_ID = "id";
    public static final String RESERVATION_RESERVATION_ID = "reservation_id";
    public static final String RESERVATION_DATE = "date";
    public static final String RESERVATION_COMMENTS = "comments";
    public static final String RESERVATION_COUNT = "count";

    public DbReservations(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String INITIALIZE_TABLE = String.format(
                "CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s INTEGER)", RESERVATION_TABLE_NAME, RESERVATION_COLUMN_ID, RESERVATION_RESERVATION_ID, RESERVATION_DATE, RESERVATION_COMMENTS, RESERVATION_COUNT
        );

        db.execSQL(INITIALIZE_TABLE);
        System.out.println("[INFO] Initialized reservation db.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", RESERVATION_TABLE_NAME));
        onCreate(db);
    }

    public Cursor getMenu()
    {
        String q = String.format("SELECT * FROM %s", RESERVATION_TABLE_NAME);
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(q, null);
    }

    public void addDish(Reservation reservation)
    {
        ContentValues cv = new ContentValues();

        cv.put(RESERVATION_RESERVATION_ID, reservation.getId().toString());
        cv.put(RESERVATION_DATE, reservation.getDate().toString());
        cv.put(RESERVATION_COMMENTS, reservation.getComments());
        cv.put(RESERVATION_COUNT, reservation.getCount());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(RESERVATION_TABLE_NAME, null, cv);
        db.close();
    }

    public void deleteDish(Reservation reservation)
    {
        String uuidString = reservation.getId().toString();

        String q = String.format("SELECT * FROM %s WHERE %s = %s", RESERVATION_TABLE_NAME, RESERVATION_RESERVATION_ID, uuidString);

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(RESERVATION_TABLE_NAME, RESERVATION_RESERVATION_ID + " = ?", new String[]{String.valueOf(uuidString)});
        db.close();
    }

    public void updateDish(Reservation reservation)
    {
        ContentValues cv = new ContentValues();

        cv.put(RESERVATION_RESERVATION_ID, reservation.getId().toString());
        cv.put(RESERVATION_DATE, reservation.getDate().toString());
        cv.put(RESERVATION_COMMENTS, reservation.getComments());
        cv.put(RESERVATION_COUNT, reservation.getCount());

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(RESERVATION_TABLE_NAME, cv, RESERVATION_RESERVATION_ID + " = ?", new String[]{String.valueOf(reservation.getId().toString())});
        db.close();
    }

}
