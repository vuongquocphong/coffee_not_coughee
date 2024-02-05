package com.example.coffee_not_coughee.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffee_not_coughee.Model.CoffeeModel;
import com.example.coffee_not_coughee.R;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MyOrderViewHolder> {

    private List<CoffeeModel> orderList;

    public MyOrderAdapter(List<CoffeeModel> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public MyOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new MyOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderViewHolder holder, int position) {

        CoffeeModel order = orderList.get(position);
        holder.bind(order);

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    static class MyOrderViewHolder extends RecyclerView.ViewHolder {
        TextView coffeeName;
        TextView customerAddress;
        TextView totalPrice;
        RecyclerView orderItemRecycler;

        MyOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            coffeeName = itemView.findViewById(R.id.coffeeName);
            customerAddress = itemView.findViewById(R.id.customerAddress);
            totalPrice = itemView.findViewById(R.id.totalPrice);
        }

        void bind(final CoffeeModel order) {
            coffeeName.setText(String.format("%s - %s  x%d", order.coffeeName, order.coffeeSize, order.quantity));
            customerAddress.setText("Address: " + order.address);
            totalPrice.setText(String.format("$%.2f", order.coffeePrice * order.quantity));
        }
    }


    public List<CoffeeModel> getOrderList() {
        return orderList;
    }

    public void removeItem(int position) {
        orderList.remove(position);
        notifyItemRemoved(position);
    }
}