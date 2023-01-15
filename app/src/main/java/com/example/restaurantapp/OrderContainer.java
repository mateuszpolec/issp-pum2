package com.example.restaurantapp;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderContainer {

    private static OrderContainer sOrderContainer;

    private List<Order> mOrders;
    private DbUsers mDbUsers;

    public static OrderContainer get(Context context)
    {
        if (sOrderContainer == null)
        {
            sOrderContainer = new OrderContainer(context);
        }

        return sOrderContainer;
    }

    private OrderContainer(Context context) { mOrders = new ArrayList<>(); }

    public List<Order> getOrders() { return mOrders; }

    public Order getOrder(UUID id)
    {
        for (Order order : mOrders)
        {
            if (order.getId().equals(id))
            {
                return order;
            }
        }

        return null;
    }

    public void addOrder(Order order) { mOrders.add(order); }

    public void deleteOrder(UUID id)
    {
        for (Order order : mOrders)
        {
            if (order.getId().equals(id))
            {
                mOrders.remove(order);
                break;
            }
        }
    }
}
