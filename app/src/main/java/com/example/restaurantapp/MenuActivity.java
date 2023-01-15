package com.example.restaurantapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.UUID;

public class MenuActivity extends AppCompatActivity implements DishAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    private List<Dish> mDishes = DishContainer.get(this).getDishes();
    private DishContainer mDishContainer;
    private DishAdapter mDishAdapter;
    private DbMenu mDbMenu;
    private SearchView svDishSearcher;

    @Override
    protected void onCreate(Bundle savedInstanceBundle)
    {
        super.onCreate(savedInstanceBundle);

        setContentView(R.layout.menu_activity);

        mDbMenu = new DbMenu(this);
        mDishContainer.get(this);
        initialize();

        recyclerView = findViewById(R.id.rvDishContainer);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDishAdapter = new DishAdapter(this, mDishes);
        mDishAdapter.setClickListener(this);
        recyclerView.setAdapter(mDishAdapter);

        svDishSearcher = findViewById(R.id.svDishSearcher);
        svDishSearcher.setQueryHint("Wyszukaj danie...");

        svDishSearcher.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mDishAdapter.getFilter().filter(newText);
                return false;
            }
        });

        svDishSearcher.setOnCloseListener(() -> {
            initialize();
            mDishAdapter.notifyDataSetChanged();
            return false;
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        initialize();
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position)
    {
        Intent newActivity = new Intent(view.getContext(), DishActivity.class);
        newActivity.putExtra("DishUUID", mDishAdapter.getItem(position).getId().toString());
        view.getContext().startActivity(newActivity);
    }

    private void initialize()
    {
        Cursor c = mDbMenu.getMenu();
        mDishes.clear();

        if (c.getCount() != 0)
        {
            while (c.moveToNext())
            {
                UUID dishId = UUID.fromString(c.getString(1));
                String dishName = c.getString(2);
                String dishIngredients = c.getString(3);
                String dishRecepie = c.getString(4);
                int dishPrice = c.getInt(5);
                int dishWeight = c.getInt(6);
                int dishTimeToPrepare = c.getInt(7);
                String dishAllergens = c.getString(8);

                mDishes.add(new Dish(dishId, dishName, dishIngredients, dishRecepie, dishPrice, dishWeight, dishTimeToPrepare, dishAllergens));
            }
        }
    }
}
