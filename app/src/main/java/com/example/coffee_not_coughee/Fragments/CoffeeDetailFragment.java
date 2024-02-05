package com.example.coffee_not_coughee.Fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.coffee_not_coughee.Adapter.CoffeeAdapter;
import com.example.coffee_not_coughee.MVVM.CoffeeDetailViewModel;
import com.example.coffee_not_coughee.MainActivity;
import com.example.coffee_not_coughee.Model.CartModel;
import com.example.coffee_not_coughee.Model.CoffeeModel;
import com.example.coffee_not_coughee.R;
import com.example.coffee_not_coughee.databinding.FragmentCoffeeDetailBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CoffeeDetailFragment extends Fragment {
    private FragmentCoffeeDetailBinding binding;
    private CoffeeModel coffee;
    private CoffeeDetailViewModel viewModel;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        viewModel = new ViewModelProvider(this).get(CoffeeDetailViewModel.class);
        binding = FragmentCoffeeDetailBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get the arguments from the bundle
        Bundle args = getArguments();
        if (args != null) {
            int coffeeImage = args.getInt("coffeeImage", 0);
            String coffeeName = args.getString("coffeeName", "");
            float price = args.getFloat("coffeePrice", 0f);
            int quantity = args.getInt("quantity", 1);

            // Create a new coffee object
            if (viewModel.getCoffee() == null) {
                coffee = new CoffeeModel(coffeeImage, coffeeName, price, 1000);
                coffee.quantity = quantity;
                viewModel.setCoffee(coffee);
            } else {
                coffee = viewModel.getCoffee();
            }

            updateTotalPrice();
            binding.singleShotCheckBox.setChecked(true);

            // Set the image, name, and price of the coffee
            binding.coffeeImage.setImageResource(coffee.coffeeImage);
            binding.coffeeName.setText(coffee.coffeeName);
            binding.quantityText.setText(String.valueOf(coffee.quantity));

            updateSizeSelectedImageSrc();
            updateIceAmountSelectedImageSrc();


            // Set the click listeners for the buttons
            binding.increaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    increaseQuantity();
                }
            });

            binding.decreaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    decreaseQuantity();
                }
            });

            binding.smallSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateSize(CoffeeModel.CoffeeSize.SMALL);
                    updateCoffeePrice();
                }
            });

            binding.mediumSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateSize(CoffeeModel.CoffeeSize.MEDIUM);
                    updateCoffeePrice();
                }
            });

            binding.largeSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateSize(CoffeeModel.CoffeeSize.LARGE);
                    updateCoffeePrice();
                }
            });

            binding.takeAwayCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateTakeAway();
                }
            });

            binding.singleShotCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.singleShotCheckBox.setChecked(true);
                    updateShotType(CoffeeModel.ShotType.SINGLE);
                    updateCoffeePrice();
                    updateTotalPrice();
                    binding.doubleShotCheckBox.setChecked(false);
                }
            });

            binding.doubleShotCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.doubleShotCheckBox.setChecked(true);
                    updateShotType(CoffeeModel.ShotType.DOUBLE);
                    updateCoffeePrice();
                    updateTotalPrice();
                    binding.singleShotCheckBox.setChecked(false);
                }
            });

            binding.lessIceSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateIceAmount(CoffeeModel.IceType.LESS);
                }
            });

            binding.mediumIceSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateIceAmount(CoffeeModel.IceType.NORMAL);
                }
            });

            binding.muchIceSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateIceAmount(CoffeeModel.IceType.MORE);
                }
            });

            binding.addToCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addToCart();
                }
            });

            binding.detailsCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavHostFragment.findNavController(CoffeeDetailFragment.this)
                            .navigate(R.id.action_CoffeeDetailFragment_to_CartFragment);
                }
            });
        }
    }

    private void increaseQuantity() {
        coffee.quantity++;
        binding.quantityText.setText(String.valueOf(coffee.quantity));
        updateTotalPrice();
    }

    private void decreaseQuantity() {
        if (coffee.quantity > 1) {
            coffee.quantity--;
            binding.quantityText.setText(String.valueOf(coffee.quantity));

            // Update the total price
            updateTotalPrice();
        }
    }

    private void updateTotalPrice() {
        double totalPrice = coffee.coffeePrice * coffee.quantity;
        binding.totalPrice.setText(String.format("$%.2f", totalPrice));
    }

    private void updateSizeSelectedImageSrc() {
        switch (coffee.coffeeSize) {
            case SMALL:
                binding.smallSelect.setImageResource(R.drawable.coffee_detail_size_selected_cup);
                binding.mediumSelect.setImageResource(R.drawable.coffee_detail_size_normal_cup);
                binding.largeSelect.setImageResource(R.drawable.coffee_detail_size_normal_cup);
                break;
            case MEDIUM:
                binding.smallSelect.setImageResource(R.drawable.coffee_detail_size_normal_cup);
                binding.mediumSelect.setImageResource(R.drawable.coffee_detail_size_selected_cup);
                binding.largeSelect.setImageResource(R.drawable.coffee_detail_size_normal_cup);
                break;
            case LARGE:
                binding.smallSelect.setImageResource(R.drawable.coffee_detail_size_normal_cup);
                binding.mediumSelect.setImageResource(R.drawable.coffee_detail_size_normal_cup);
                binding.largeSelect.setImageResource(R.drawable.coffee_detail_size_selected_cup);
                break;
        }
    }

    private void updateIceAmountSelectedImageSrc() {
        switch (coffee.iceType) {
            case LESS:
                binding.lessIceSelect.setImageResource(R.drawable.less_ice_selected);
                binding.mediumIceSelect.setImageResource(R.drawable.normal_ice_normal);
                binding.muchIceSelect.setImageResource(R.drawable.much_ice_normal);
                break;
            case NORMAL:
                binding.lessIceSelect.setImageResource(R.drawable.less_ice_normal);
                binding.mediumIceSelect.setImageResource(R.drawable.normal_ice_selected);
                binding.muchIceSelect.setImageResource(R.drawable.much_ice_normal);
                break;
            case MORE:
                binding.lessIceSelect.setImageResource(R.drawable.less_ice_normal);
                binding.mediumIceSelect.setImageResource(R.drawable.normal_ice_normal);
                binding.muchIceSelect.setImageResource(R.drawable.much_ice_selected);
                break;
        }
    }

    private void updateSize(CoffeeModel.CoffeeSize size) {
        coffee.coffeeSize = size;
        updateCoffeePrice();
        updateSizeSelectedImageSrc();
        updateTotalPrice();
    }

    private void updateIceAmount(CoffeeModel.IceType iceType) {
        coffee.iceType = iceType;
        updateIceAmountSelectedImageSrc();
    }

    private void updateTakeAway() {
        coffee.isTakeAway = !coffee.isTakeAway;
    }

    private void updateShotType(CoffeeModel.ShotType shotType) {
        coffee.shotType = shotType;
    }

    private void updateCoffeePrice() {
        if (coffee.coffeeSize == CoffeeModel.CoffeeSize.SMALL) {
            coffee.coffeePrice = coffee.coffeeBasePrice * 0.8f;
        } else if (coffee.coffeeSize == CoffeeModel.CoffeeSize.LARGE) {
            coffee.coffeePrice = coffee.coffeeBasePrice * 1.2f;
        } else {
            coffee.coffeePrice = coffee.coffeeBasePrice;
        }
        if (coffee.shotType == CoffeeModel.ShotType.DOUBLE) {
            coffee.coffeePrice += 1.0f;
        }
    }

    private void addToCart() {
        MainActivity mainActivity = (MainActivity) requireActivity();
        CartModel cart = mainActivity.getCart();
        coffee.address = mainActivity.getCurrentUser().address;
        cart.addItem(coffee);

        Toast.makeText(getContext(), "Added to cart", Toast.LENGTH_SHORT).show();
        NavHostFragment.findNavController(this).popBackStack();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}