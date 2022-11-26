package com.example.restaurantapp;

import android.content.Context;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DishContainer {

    private static DishContainer sDishContainer;

    private List<Dish> mDishes;
    private DbUsers mDbUsers;

    public static DishContainer get(Context context)
    {
        if (sDishContainer == null)
        {
            sDishContainer = new DishContainer(context);
        }

        return sDishContainer;
    }

    private DishContainer(Context context)
    {
        mDishes = new ArrayList<>();
    }

    public List<Dish> getDishes()
    {
        return mDishes;
    }

    public Dish getDish(UUID id)
    {
        for (Dish dish : mDishes)
        {
            if (dish.getId().equals(id))
            {
                return dish;
            }
        }

        return null;
    }

    public void addDish(Dish dish)
    {
        mDishes.add(dish);
    }

    public void deleteDish(UUID id)
    {
        for (Dish dish : mDishes)
        {
            if (dish.getId().equals(id))
            {
                mDishes.remove(dish);
                break;
            }
        }
    }
}
