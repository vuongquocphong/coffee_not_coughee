package com.example.coffee_not_coughee;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.coffee_not_coughee.Model.CartModel;
import com.example.coffee_not_coughee.Model.CoffeeModel;
import com.example.coffee_not_coughee.Model.UserModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.coffee_not_coughee.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    public List<CoffeeModel> ongoing_orders;
    public List<CoffeeModel> history_orders;
    public List<CoffeeModel> rewardHisList;
    private UserModel currentUser;
    private CartModel cart;
    public NavHostFragment navHostFragment;
    private BottomNavigationView bottomNav;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cart = new CartModel();

        ongoing_orders = new ArrayList<>();

        history_orders = new ArrayList<>();

        rewardHisList = new ArrayList<>();

        currentUser = new UserModel("John Weak", "weakjohn@gmail.com", "0123456789", "123 Main St");
        currentUser.cup_count = 7;
        currentUser.points = 10000;

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        // Initialize the bottom navigation bar

        bottomNav = binding.bottomNavigationView;
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
        navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNav, navController);
        navController.addOnDestinationChangedListener(
                (controller, destination, arguments) -> {
                    if (destination.getId() == R.id.HomePageFragment || destination.getId() == R.id.RewardFragment || destination.getId() == R.id.MyOrderFragment) {
                        bottomNav.setVisibility(View.VISIBLE);
                    } else {
                        bottomNav.setVisibility(View.GONE);
                    }
                }
        );
    }

    public CartModel getCart() {
        return cart;
    }

    public UserModel getCurrentUser() {
        return currentUser;
    }
}