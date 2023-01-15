package com.example.restaurantapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.UUID;

public class OrderListActivity extends AppCompatActivity implements OrderAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    private List<Order> mOrders = OrderContainer.get(this).getOrders();
    private OrderContainer mOrderContainer;
    private OrderAdapter mOrderAdapter;
    private DbOrders mDbOrders;
    private SearchView svOrderSearcher;

    @Override
    protected void onCreate(Bundle savedInstanceBundle)
    {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.order_list_activity);

        mDbOrders = new DbOrders(this);
        mOrderContainer.get(this);
        initialize();

        recyclerView = findViewById(R.id.rvOrderContainer);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mOrderAdapter = new OrderAdapter(this, mOrders);
        mOrderAdapter.setClickListener(this);
        recyclerView.setAdapter(mOrderAdapter);

        svOrderSearcher = findViewById(R.id.svOrderSearcher);
        svOrderSearcher.setQueryHint("Wyszukaj zamówienie...");

        svOrderSearcher.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mOrderAdapter.getFilter().filter(newText);
                return false;
            }
        });

        svOrderSearcher.setOnCloseListener(() -> {
            initialize();
            mOrderAdapter.notifyDataSetChanged();
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
        Intent newActivity = new Intent(view.getContext(), OrderUserActivity.class);
        newActivity.putExtra("OrderUUID", mOrderAdapter.getItem(position).getId().toString());
        view.getContext().startActivity(newActivity);
    }

    private void initialize()
    {
        System.out.println("Initialize.");

        Cursor c = mDbOrders.getOrders();
        mOrders.clear();

        if (c.getCount() != 0)
        {
            while (c.moveToNext())
            {
                UUID orderId = UUID.fromString(c.getString(1));
                UUID customerId = UUID.fromString(c.getString(2));
                String dishNames = c.getString(3);
                String comments = c.getString(4);
                String address = c.getString(5);
                int telephone = c.getInt(6);
                int totalPrice = c.getInt(7);
                int tableNumber = c.getInt(8);
                Order.Status status = Order.Status.values()[c.getInt(9)];
                Order.WayOfServing wayOfServing = Order.WayOfServing.values()[c.getInt(10)];
                String pickupTime = c.getString(11);

                if (!CurrentUser.user.IsEmployee())
                {
                    if (customerId.equals(CurrentUser.user.getId())) {
                        mOrders.add(new Order(orderId, customerId, dishNames, comments, address, telephone, totalPrice, tableNumber, status, wayOfServing, pickupTime));
                    }
                }
                else
                {
                    mOrders.add(new Order(orderId, customerId, dishNames, comments, address, telephone, totalPrice, tableNumber, status, wayOfServing, pickupTime));
                }
            }
        }
    }

}
