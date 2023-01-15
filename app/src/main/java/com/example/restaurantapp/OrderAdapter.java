package com.example.restaurantapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> implements Filterable {

    private List<Order> mOrders;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public OrderAdapter(Context context, List<Order> orders)
    {
        this.mInflater = LayoutInflater.from(context);
        this.mOrders = orders;
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView mOrderText;
        public OrderAdapter mOrderAdapter;

        public OrderViewHolder(@NonNull View itemView, OrderAdapter adapter)
        {
            super(itemView);
            this.mOrderText = itemView.findViewById(R.id.tvOrder);
            itemView.setOnClickListener(this);
            this.mOrderAdapter = adapter;
        }

        @Override
        public void onClick(View view)
        {
            if (mClickListener != null)
            {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    @NonNull
    @Override
    public OrderAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.order_recyclerview_row, parent, false);
        return new OrderViewHolder(view, this);
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.OrderViewHolder viewHolder, int position)
    {
        Order current = mOrders.get(position);
        viewHolder.mOrderText.setText(current.getDishNames());
    }

    @Override
    public int getItemCount()
    {
        return mOrders.size();
    }

    Order getItem(int id)
    {
        return mOrders.get(id);
    }

    void setClickListener(OrderAdapter.ItemClickListener itemClickListener)
    {
        this.mClickListener = itemClickListener;
    }

    @Override
    public Filter getFilter()
    {
        return filter;
    }

    Filter filter = new Filter()
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Order> f = new ArrayList<>();

            if (constraint.toString().isEmpty())
            {
                f.addAll(mOrders);
            }
            else
            {
                for (Order o : mOrders)
                {
                    if (o.getDishNames().toLowerCase().contains(constraint.toString().toLowerCase()))
                    {
                        f.add(o);
                    }
                }
            }

            FilterResults fr = new FilterResults();
            fr.values = f;
            return fr;
        }

        @Override
        protected void publishResults(final CharSequence constraint, FilterResults fr)
        {
            mOrders.clear();
            mOrders.addAll((Collection<? extends Order>) fr.values);
            notifyDataSetChanged();
        }
    };


    public interface ItemClickListener
    {
        void onItemClick(View view, int position);
    }
}
