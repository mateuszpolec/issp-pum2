package com.example.restaurantapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbMenu extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "DbMenu.db";

    // TODO: Dodać alergeny.

    public static final String MENU_TABLE_NAME = "menu";
    public static final String MENU_COLUMN_ID = "id";
    public static final String MENU_DISH_ID = "dish_id";
    public static final String MENU_NAME = "name";
    public static final String MENU_INGREDIENTS = "ingredients";
    public static final String MENU_RECIPE = "recipe";
    public static final String MENU_PRICE = "price";
    public static final String MENU_WEIGHT = "weight";
    public static final String MENU_TIME_TO_PREPARE = "time_to_prepare";

    public DbMenu(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String INITIALIZE_TABLE = String.format(
                "CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s INTEGER, %s INTEGER, %s INTEGER)", MENU_TABLE_NAME, MENU_COLUMN_ID, MENU_DISH_ID, MENU_NAME, MENU_INGREDIENTS, MENU_RECIPE, MENU_PRICE, MENU_WEIGHT, MENU_TIME_TO_PREPARE
        );

        db.execSQL(INITIALIZE_TABLE);
        System.out.println("[INFO] Initialized menu db.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", MENU_TABLE_NAME));
        onCreate(db);
    }

    public Cursor getMenu()
    {
        String q = String.format("SELECT * FROM %s", MENU_TABLE_NAME);
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(q, null);
    }

    public void addDish(Dish dish)
    {
        ContentValues cv = new ContentValues();

        cv.put(MENU_DISH_ID, dish.getId().toString());
        cv.put(MENU_NAME, dish.getName());
        cv.put(MENU_INGREDIENTS, dish.getIngredients());
        cv.put(MENU_RECIPE, dish.getRecipe());
        cv.put(MENU_PRICE, dish.getPrice());
        cv.put(MENU_WEIGHT, dish.getWeight());
        cv.put(MENU_TIME_TO_PREPARE, dish.getTimeToPrepare());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(MENU_TABLE_NAME, null, cv);
        db.close();

        System.out.println("[INFO][DbMenu] Added new dish with name: " + dish.getName());
    }

    public void deleteDish(Dish dish)
    {
        String uuidString = dish.getId().toString();

        String q = String.format("SELECT * FROM %s WHERE %s = %s", MENU_TABLE_NAME, MENU_DISH_ID, uuidString);

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MENU_TABLE_NAME, MENU_DISH_ID + " = ?", new String[]{String.valueOf(uuidString)});
        db.close();
    }

    public void updateDish(Dish dish)
    {
        ContentValues cv = new ContentValues();

        cv.put(MENU_DISH_ID, dish.getId().toString());
        cv.put(MENU_NAME, dish.getName());
        cv.put(MENU_INGREDIENTS, dish.getIngredients());
        cv.put(MENU_RECIPE, dish.getRecipe());
        cv.put(MENU_PRICE, dish.getPrice());
        cv.put(MENU_WEIGHT, dish.getWeight());
        cv.put(MENU_TIME_TO_PREPARE, dish.getTimeToPrepare());

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(MENU_TABLE_NAME, cv, MENU_DISH_ID + " = ?", new String[]{String.valueOf(dish.getId().toString())});
        db.close();
    }

}
