package com.example.restaurantapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOrders extends SQLiteOpenHelper {

    private static final int DB_VERSION = 5;
    private static final String DB_NAME = "DbOrders.db";

    public static final String ORDER_TABLE_NAME = "orders";
    public static final String ORDER_COLUMN_ID = "id";
    public static final String ORDER_ORDER_ID = "order_id";
    public static final String ORDER_WHO_ORDERED = "customer_id";
    public static final String ORDER_DISH_NAMES = "dish_names";
    public static final String ORDER_COMMENTS = "comments";
    public static final String ORDER_ADDRESS = "address";
    public static final String ORDER_TELEPHONE = "telephone";
    public static final String ORDER_TOTAL_PRICE = "total_price";
    public static final String ORDER_TABLE_NUMBER = "table_number";
    public static final String ORDER_STATUS = "status";
    public static final String ORDER_WAY_OF_SERVING = "way_of_serving";
    public static final String ORDER_PICKUP_TIME = "pickup_time";

    public DbOrders(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String INITIALIZE_TABLE = String.format(
                "CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s INTEGER, %s INTEGER, %s INTEGER, %s INTEGER, %s INTEGER, %s TEXT)", ORDER_TABLE_NAME, ORDER_COLUMN_ID, ORDER_ORDER_ID, ORDER_WHO_ORDERED, ORDER_DISH_NAMES, ORDER_COMMENTS, ORDER_ADDRESS, ORDER_TELEPHONE, ORDER_TOTAL_PRICE, ORDER_TABLE_NUMBER, ORDER_STATUS, ORDER_WAY_OF_SERVING, ORDER_PICKUP_TIME
        );

        db.execSQL(INITIALIZE_TABLE);
        System.out.println("[INFO] Initialized order db.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", ORDER_TABLE_NAME));
        onCreate(db);
    }

    public Cursor getOrders()
    {
        String q = String.format("SELECT * FROM %s", ORDER_TABLE_NAME);
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(q, null);
    }

    public void addOrder(Order order)
    {
        ContentValues cv = new ContentValues();

        cv.put(ORDER_ORDER_ID, order.getId().toString());
        cv.put(ORDER_WHO_ORDERED, order.getCustomerID().toString());
        cv.put(ORDER_DISH_NAMES, order.getDishNames());
        cv.put(ORDER_COMMENTS, order.getComments());
        cv.put(ORDER_ADDRESS, order.getAddress());
        cv.put(ORDER_TELEPHONE, order.getTelephone());
        cv.put(ORDER_TOTAL_PRICE, order.getTotalPrice());
        cv.put(ORDER_TABLE_NUMBER, order.getTableNumber());
        cv.put(ORDER_STATUS, order.getStatus().ordinal());
        cv.put(ORDER_WAY_OF_SERVING, order.getWayOfServing().ordinal());
        cv.put(ORDER_PICKUP_TIME, order.getPickupTime());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(ORDER_TABLE_NAME, null, cv);
        db.close();
    }

    public void deleteOrder(Order order)
    {
        String uuidString = order.getId().toString();

        String q = String.format("SELECT * FROM %s WHERE %s = %s", ORDER_TABLE_NAME, ORDER_ORDER_ID, uuidString);

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ORDER_TABLE_NAME, ORDER_ORDER_ID + " = ?", new String[]{String.valueOf(uuidString)});
        db.close();
    }

    public void updateOrder(Order order)
    {
        ContentValues cv = new ContentValues();

        cv.put(ORDER_ORDER_ID, order.getId().toString());
        cv.put(ORDER_WHO_ORDERED, order.getCustomerID().toString());
        cv.put(ORDER_DISH_NAMES, order.getDishNames());
        cv.put(ORDER_COMMENTS, order.getComments());
        cv.put(ORDER_ADDRESS, order.getAddress());
        cv.put(ORDER_TELEPHONE, order.getTelephone());
        cv.put(ORDER_TOTAL_PRICE, order.getTotalPrice());
        cv.put(ORDER_TABLE_NUMBER, order.getTableNumber());
        cv.put(ORDER_STATUS, order.getStatus().ordinal());
        cv.put(ORDER_WAY_OF_SERVING, order.getWayOfServing().ordinal());
        cv.put(ORDER_PICKUP_TIME, order.getPickupTime());

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(ORDER_TABLE_NAME, cv, ORDER_ORDER_ID + " = ?", new String[]{String.valueOf(order.getId().toString())});
        db.close();
    }

}
