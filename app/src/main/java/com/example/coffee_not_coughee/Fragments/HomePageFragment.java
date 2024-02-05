    package com.example.coffee_not_coughee.Fragments;

    import android.os.Build;
    import android.os.Bundle;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.view.WindowInsets;
    import android.widget.ImageView;
    import android.widget.LinearLayout;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;
    import androidx.navigation.NavController;
    import androidx.navigation.fragment.NavHostFragment;
    import androidx.navigation.ui.NavigationUI;
    import androidx.recyclerview.widget.GridLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.coffee_not_coughee.MainActivity;
    import com.example.coffee_not_coughee.R;
    import com.example.coffee_not_coughee.Adapter.CoffeeAdapter;
    import com.example.coffee_not_coughee.Model.CoffeeModel;
    import com.google.android.material.bottomnavigation.BottomNavigationView;
    import com.google.android.material.navigation.NavigationView;

    import java.util.ArrayList;
    import java.util.List;

    public class HomePageFragment extends Fragment {

        private RecyclerView recyclerView;
        private CoffeeAdapter adapter;
        private View ProfileButton;
        private View cartButton;
        private TextView userName;
        private TextView cup_counts;
        ImageView loyal_cup_1;
        ImageView loyal_cup_2;
        ImageView loyal_cup_3;
        ImageView loyal_cup_4;
        ImageView loyal_cup_5;
        ImageView loyal_cup_6;
        ImageView loyal_cup_7;
        ImageView loyal_cup_8;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_home_page, container, false);

            // Initial the loyal cups
            loyal_cup_1 = view.findViewById(R.id.loyaltyCup1);
            loyal_cup_2 = view.findViewById(R.id.loyaltyCup2);
            loyal_cup_3 = view.findViewById(R.id.loyaltyCup3);
            loyal_cup_4 = view.findViewById(R.id.loyaltyCup4);
            loyal_cup_5 = view.findViewById(R.id.loyaltyCup5);
            loyal_cup_6 = view.findViewById(R.id.loyaltyCup6);
            loyal_cup_7 = view.findViewById(R.id.loyaltyCup7);
            loyal_cup_8 = view.findViewById(R.id.loyaltyCup8);

            // Set the loyal cups
            loyal_cup_1.setImageResource(R.drawable.loyalty_cup_not_checked);
            loyal_cup_2.setImageResource(R.drawable.loyalty_cup_not_checked);
            loyal_cup_3.setImageResource(R.drawable.loyalty_cup_not_checked);
            loyal_cup_4.setImageResource(R.drawable.loyalty_cup_not_checked);
            loyal_cup_5.setImageResource(R.drawable.loyalty_cup_not_checked);
            loyal_cup_6.setImageResource(R.drawable.loyalty_cup_not_checked);
            loyal_cup_7.setImageResource(R.drawable.loyalty_cup_not_checked);
            loyal_cup_8.setImageResource(R.drawable.loyalty_cup_not_checked);

            cup_counts = view.findViewById(R.id.loyaltyCardCount);
            // Initialize the RecyclerView
            recyclerView = view.findViewById(R.id.recyclerView);

            // Create and set the adapter
            adapter = new CoffeeAdapter(getCoffeeData(), new CoffeeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(CoffeeModel coffee) {
                    // Handle the click event here, e.g., navigate to the Coffee Detail Fragment
                    CoffeeDetailFragment coffeeDetailFragment = new CoffeeDetailFragment();
                    Bundle args = new Bundle();

                    // Pass the coffee name and price to the Coffee Detail Fragment
                    args.putInt("coffeeImage", coffee.coffeeImage);
                    args.putString("coffeeName", coffee.coffeeName);
                    args.putFloat("coffeePrice", coffee.coffeePrice);
                    args.putInt("quantity", coffee.quantity);
                    coffeeDetailFragment.setArguments(args);

                    NavHostFragment.findNavController(HomePageFragment.this)
                            .navigate(R.id.action_HomePageFragment_to_CoffeeDetailFragment, args);
                }
            });

            cartButton = view.findViewById(R.id.homepageCartButton);
            cartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavHostFragment.findNavController(HomePageFragment.this)
                            .navigate(R.id.action_HomePageFragment_to_CartFragment);
                }
            });

            ProfileButton = view.findViewById(R.id.homepageProfileButton);
            ProfileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavHostFragment.findNavController(HomePageFragment.this)
                            .navigate(R.id.action_HomePageFragment_to_ProfileFragment);
                }
            });

            userName = view.findViewById(R.id.homepageUserName);
            userName.setText(((MainActivity)getActivity()).getCurrentUser().full_name);

            // Update the RecyclerView layout manager to use a 2x2 grid layout
            GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);

            // Update the loyal cups
            updateLoyalCount();

            // Update the ImageSrc of loyal cups base on the number of stamps
            updateLoyalImageSrc();

            return view;
        }

        @Override
        public void onResume() {
            super.onResume();

            // Update the loyal cups
            updateLoyalCount();

            // Update the ImageSrc of loyal cups base on the number of stamps
            updateLoyalImageSrc();

            // Update the user name
            userName.setText(((MainActivity)getActivity()).getCurrentUser().full_name);
        }

        private void updateLoyalCount() {
            int stamps = ((MainActivity)getActivity()).getCurrentUser().cup_count % 8;
            cup_counts.setText(String.format("%d/8", stamps));
        }

        // Update the ImageSrc of loyal cups base on the number of stamps
        private void updateLoyalImageSrc() {
            int stamps = ((MainActivity)getActivity()).getCurrentUser().cup_count % 8;
            if (stamps >= 1) {
                loyal_cup_1.setImageResource(R.drawable.loyalty_cup_checked);
            }
            if (stamps >= 2) {
                loyal_cup_2.setImageResource(R.drawable.loyalty_cup_checked);
            }
            if (stamps >= 3) {
                loyal_cup_3.setImageResource(R.drawable.loyalty_cup_checked);
            }
            if (stamps >= 4) {
                loyal_cup_4.setImageResource(R.drawable.loyalty_cup_checked);
            }
            if (stamps >= 5) {
                loyal_cup_5.setImageResource(R.drawable.loyalty_cup_checked);
            }
            if (stamps >= 6) {
                loyal_cup_6.setImageResource(R.drawable.loyalty_cup_checked);
            }
            if (stamps == 7) {
                loyal_cup_7.setImageResource(R.drawable.loyalty_cup_checked);
            }

        }

        // Dummy data for the coffee list
        private List<CoffeeModel> getCoffeeData() {
            List<CoffeeModel> coffeeList = new ArrayList<>();
            coffeeList.add(new CoffeeModel(R.drawable.americano, "Americano", 3.00f, 1000));
            coffeeList.add(new CoffeeModel(R.drawable.cappucino, "Cappuccino", 3.00f, 1000));
            coffeeList.add(new CoffeeModel(R.drawable.mocha, "Mocha", 3.25f, 1000));
            coffeeList.add(new CoffeeModel(R.drawable.flat_white, "Flat White", 3.50f, 1000));
            return coffeeList;
        }
    }
