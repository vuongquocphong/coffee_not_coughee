package com.example.coffee_not_coughee.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.coffee_not_coughee.Adapter.MyOrderAdapter;
import com.example.coffee_not_coughee.MainActivity;
import com.example.coffee_not_coughee.Model.CoffeeModel;
import com.example.coffee_not_coughee.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;

import java.util.List;

public class MyOrderFragment extends Fragment {

    // Inside the MyOrderFragment class
    private RecyclerView onGoingOrderRecycler;
    private RecyclerView historyOrderRecycler;
    private MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_order, container, false);

        // Initialize the mainActivity variable correctly
        if (getActivity() instanceof MainActivity) {
            mainActivity = (MainActivity) getActivity();
        }

        // Initialize the RecyclerViews
        onGoingOrderRecycler = view.findViewById(R.id.onGoingOrderRecycler);
        historyOrderRecycler = view.findViewById(R.id.historyOrderRecycler);

        // Set the layout managers for the RecyclerViews (assuming you want to use LinearLayoutManager)
        onGoingOrderRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        historyOrderRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Set the adapters for the RecyclerViews
        MyOrderAdapter onGoingOrderAdapter = new MyOrderAdapter(getOnGoingOrders());
        MyOrderAdapter historyOrderAdapter = new MyOrderAdapter(getHistoryOrders());

        onGoingOrderRecycler.setAdapter(onGoingOrderAdapter);
        historyOrderRecycler.setAdapter(historyOrderAdapter);

        // Set the initial visibility of the RecyclerViews
        onGoingOrderRecycler.setVisibility(View.VISIBLE);
        historyOrderRecycler.setVisibility(View.GONE);

        // Add the click listeners for the tab selection
        TextView onGoingTab = view.findViewById(R.id.onGoingOrderTab);
        TextView historyTab = view.findViewById(R.id.historyOrderTab);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new MyOrderFragment.SwipeToDeleteCallback(onGoingOrderAdapter));
        itemTouchHelper.attachToRecyclerView(onGoingOrderRecycler);

        onGoingTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGoingOrderRecycler.setVisibility(View.VISIBLE);
                historyOrderRecycler.setVisibility(View.GONE);

                // Update the text color of the tabs
                onGoingTab.setTextColor(getResources().getColor(R.color.loyalty_homepage_background, null));
                historyTab.setTextColor(getResources().getColor(R.color.gray, null));
            }
        });

        historyTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGoingOrderRecycler.setVisibility(View.GONE);
                historyOrderRecycler.setVisibility(View.VISIBLE);

                // Update the text color of the tabs
                historyTab.setTextColor(getResources().getColor(R.color.loyalty_homepage_background, null));
                onGoingTab.setTextColor(getResources().getColor(R.color.gray, null));
            }
        });

        return view;
    }

    public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

        private MyOrderAdapter adapter;

        public SwipeToDeleteCallback(MyOrderAdapter adapter) {
            super(0, ItemTouchHelper.LEFT);
            this.adapter = adapter;
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            // No drag and drop functionality needed, so return false
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            // Remove the item from the ongoing orders and move it to history orders
            int position = viewHolder.getBindingAdapterPosition();
            CoffeeModel movedCoffee = adapter.getOrderList().get(position);
            adapter.removeItem(position);

            // Add the moved coffee to the history orders list
            mainActivity.history_orders.add(movedCoffee);

            // Show a snack bar to undo the action
            showUndoSnackbar(movedCoffee, position);
        }

        private void showUndoSnackbar(CoffeeModel coffee, int position) {
            View view = onGoingOrderRecycler.getRootView();
            Snackbar snackbar = Snackbar.make(view, "Item moved to history", Snackbar.LENGTH_LONG);
            snackbar.setAction("Undo", v -> {
                // Undo the deletion and reinsert the item into the cart
                mainActivity.ongoing_orders.add(position, coffee);
                mainActivity.history_orders.remove(coffee);
                adapter.notifyItemInserted(position);
            });

            // Add a callback to dismiss the Snackbar
            snackbar.addCallback(new Snackbar.Callback() {
                @Override
                public void onDismissed(Snackbar snackbar, int event) {
                    if (event != DISMISS_EVENT_ACTION) {
                        // Snack bar was dismissed without an undo action, perform any other necessary cleanup
                        // (e.g., permanently delete the item from the cart)
                    }
                }
            });

            snackbar.show();
        }
    }


    // Replace these methods with your actual data retrieval methods
    private List<CoffeeModel> getOnGoingOrders() {
        return mainActivity.ongoing_orders;
    }

    private List<CoffeeModel> getHistoryOrders() {
        return mainActivity.history_orders;
    }

}