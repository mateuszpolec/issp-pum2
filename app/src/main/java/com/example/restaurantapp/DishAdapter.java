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

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.DishViewHolder> implements Filterable {

    private List<Dish> mDishes;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public DishAdapter(Context context, List<Dish> dishes)
    {
        this.mInflater = LayoutInflater.from(context);
        this.mDishes = dishes;
    }

    public class DishViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        public TextView mDishText;
        public DishAdapter mDishAdapter;

        public DishViewHolder(@NonNull View itemView, DishAdapter adapter)
        {
            super(itemView);
            this.mDishText = itemView.findViewById(R.id.tvDish);
            itemView.setOnClickListener(this);
            this.mDishAdapter = adapter;
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
    public DishAdapter.DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.dish_recyclerview_row, parent, false);
        return new DishViewHolder(view, this);
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull DishAdapter.DishViewHolder viewHolder, int position)
    {
        Dish current = mDishes.get(position);
        viewHolder.mDishText.setText(current.getName());
    }

    @Override
    public int getItemCount()
    {
        return mDishes.size();
    }

    Dish getItem(int id)
    {
        return mDishes.get(id);
    }

    void setClickListener(ItemClickListener itemClickListener)
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
            List<Dish> f = new ArrayList<>();

            if (constraint.toString().isEmpty())
            {
                f.addAll(mDishes);
            }
            else
            {
                for (Dish d : mDishes)
                {
                    if (d.getName().toLowerCase().contains(constraint.toString().toLowerCase()))
                    {
                        f.add(d);
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
            mDishes.clear();
            mDishes.addAll((Collection<? extends Dish>) fr.values);
            notifyDataSetChanged();
        }
    };


    public interface ItemClickListener
    {
        void onItemClick(View view, int position);
    }


}
