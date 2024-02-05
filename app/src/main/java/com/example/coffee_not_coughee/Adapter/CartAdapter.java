package com.example.coffee_not_coughee.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffee_not_coughee.Model.CoffeeModel;
import com.example.coffee_not_coughee.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CoffeeModel> cartItems;

    public CartAdapter(List<CoffeeModel> cartItems) {
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_coffee_item, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CoffeeModel coffee = cartItems.get(position);
        holder.bind(coffee);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    // CartViewHolder class
    public static class CartViewHolder extends RecyclerView.ViewHolder {
        // Views in cart_coffee_item layout
        private ImageView cartItemCoffeeImage;
        private TextView cartItemCoffeeName;
        private TextView cartItemCoffeeDetails;
        private TextView cartItemCoffeeQuantity;
        private TextView cartItemCoffeeTotalPrice;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize views
            cartItemCoffeeImage = itemView.findViewById(R.id.cartItemCoffeeImage);
            cartItemCoffeeName = itemView.findViewById(R.id.cartItemCoffeeName);
            cartItemCoffeeDetails = itemView.findViewById(R.id.cartItemCoffeeDetails);
            cartItemCoffeeQuantity = itemView.findViewById(R.id.cartItemCoffeeQuantity);
            cartItemCoffeeTotalPrice = itemView.findViewById(R.id.cartItemCoffeeTotalPrice);
        }

        public void bind(CoffeeModel coffee) {
            // Bind coffee data to the views
            cartItemCoffeeImage.setImageResource(coffee.coffeeImage);
            cartItemCoffeeName.setText(coffee.coffeeName);
            cartItemCoffeeDetails.setText(coffee.getCoffeeDetails());
            cartItemCoffeeQuantity.setText(String.format("x %d", coffee.quantity));
            cartItemCoffeeTotalPrice.setText(String.format("$%.2f", coffee.coffeePrice * coffee.quantity));
        }
    }
}
