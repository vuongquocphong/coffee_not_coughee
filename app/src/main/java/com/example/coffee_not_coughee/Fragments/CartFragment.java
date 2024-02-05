package com.example.coffee_not_coughee.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffee_not_coughee.Adapter.CartAdapter;
import com.example.coffee_not_coughee.MainActivity;
import com.example.coffee_not_coughee.Model.CartModel;
import com.example.coffee_not_coughee.Model.CoffeeModel;
import com.example.coffee_not_coughee.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class CartFragment extends Fragment {

    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private CartModel cart;
    private View checkoutButton;
    private TextView totalPrice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        totalPrice = view.findViewById(R.id.totalPrice);
        checkoutButton = view.findViewById(R.id.checkoutButton);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cart.getItems() == null || cart.getItems().size() == 0) {
                    Snackbar.make(v, "Your cart is empty!", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                placeOrder();
            }
        });

        // Initialize the RecyclerView
        recyclerView = view.findViewById(R.id.cartRecycler);

        // Get the cart from MainActivity
        MainActivity mainActivity = (MainActivity) requireActivity();
        cart = mainActivity.getCart();

        // Create and set the adapter
        adapter = new CartAdapter(cart.getItems());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Add swipe-to-delete functionality to the RecyclerView
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);

        updateTotalPrice();

        return view;
    }



    private class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
        public SwipeToDeleteCallback() {
            super(0, ItemTouchHelper.LEFT);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            // No drag and drop functionality needed, so return false
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            // Remove the item from the cart and update the adapter
            int position = viewHolder.getBindingAdapterPosition();
            CoffeeModel deletedCoffee = cart.getItems().get(position);
            cart.removeItem(deletedCoffee);
            adapter.notifyItemRemoved(position);
            updateTotalPrice();

            showUndoSnackbar(deletedCoffee, position);
        }

        private void showUndoSnackbar(CoffeeModel coffee, int position) {
            View view = recyclerView.getRootView();
            Snackbar snackbar = Snackbar.make(view, "Item deleted", Snackbar.LENGTH_LONG);
            snackbar.setAction("Undo", v -> {
                // Undo the deletion and reinsert the item into the cart
                // Update the total price after undo
                undoRemoveItem(coffee, position);
            });

            // Add a callback to dismiss the Snackbar
            snackbar.addCallback(new Snackbar.Callback() {
                @Override
                public void onDismissed(Snackbar snackbar, int event) {
                    if (event != DISMISS_EVENT_ACTION) {
                        // Snackbar was dismissed without an undo action, perform any other necessary cleanup
                        // (e.g., permanently delete the item from the cart)
                    }
                }
            });

            snackbar.show();
        }
    }

    private void updateTotalPrice() {
        double totalPriceContent = 0;
        List<CoffeeModel> items = cart.getItems();

        for (CoffeeModel coffee : items) {
            totalPriceContent += coffee.coffeePrice * coffee.quantity;
        }

        // Update the total price TextView
        totalPrice.setText(String.format("$%.2f", totalPriceContent));
    }

    private void undoRemoveItem(CoffeeModel coffee, int position) {
        // Reinsert the item into the cart
        cart.getItems().add(position, coffee);
        adapter.notifyItemInserted(position);

        // Update the total price after undo
        updateTotalPrice();
    }

    private void placeOrder() {

        MainActivity mainActivity = (MainActivity) getActivity();

        mainActivity.ongoing_orders.addAll(cart.getItems());

        int cup_to_add = 0;

        for (CoffeeModel coffee : cart.getItems()) {
            cup_to_add += coffee.quantity;
        }

        mainActivity.getCurrentUser().cup_count += cup_to_add;

        int point_to_add = 0;

        for (CoffeeModel coffee : cart.getItems()) {
            if (coffee.coffeeSize == CoffeeModel.CoffeeSize.SMALL) {
                point_to_add += 40 * coffee.quantity;
            } else if (coffee.coffeeSize == CoffeeModel.CoffeeSize.MEDIUM) {
                point_to_add += 50 * coffee.quantity;
            } else if (coffee.coffeeSize == CoffeeModel.CoffeeSize.LARGE) {
                point_to_add += 60 * coffee.quantity;
            }
        }

        mainActivity.getCurrentUser().points += (point_to_add + mainActivity.getCurrentUser().cup_count / 8 * 1000);

        mainActivity.getCurrentUser().cup_count %= 8;

        mainActivity.rewardHisList.addAll(cart.getItems());

        cart.clearCart();

        NavHostFragment.findNavController(this).navigate(R.id.action_CartFragment_to_OrderSuccessFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}