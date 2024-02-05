package com.example.coffee_not_coughee.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffee_not_coughee.R;
import com.example.coffee_not_coughee.Model.CoffeeModel;

import java.util.List;

public class CoffeeAdapter extends RecyclerView.Adapter<CoffeeAdapter.CoffeeViewHolder> {

    private List<CoffeeModel> coffeeList;
    private OnItemClickListener listener;

    public CoffeeAdapter(List<CoffeeModel> coffeeList, OnItemClickListener listener) {
        this.coffeeList = coffeeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CoffeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coffee, parent, false);
        return new CoffeeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CoffeeViewHolder holder, int position) {
        CoffeeModel coffee = coffeeList.get(position);
        holder.bind(coffee, listener);
    }

    @Override
    public int getItemCount() {
        return coffeeList.size();
    }

    static class CoffeeViewHolder extends RecyclerView.ViewHolder {
        private ImageView coffeeImageView;
        private TextView coffeeNameTextView;

        CoffeeViewHolder(@NonNull View itemView) {
            super(itemView);
            coffeeImageView = itemView.findViewById(R.id.Item_coffee_image);
            coffeeNameTextView = itemView.findViewById(R.id.Item_coffee_name);
        }

        void bind(final CoffeeModel coffee, final OnItemClickListener listener) {
            coffeeImageView.setImageResource(coffee.coffeeImage);
            coffeeNameTextView.setText(coffee.coffeeName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(coffee);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(CoffeeModel coffee);
    }
}
