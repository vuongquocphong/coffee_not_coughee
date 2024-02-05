package com.example.coffee_not_coughee.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.coffee_not_coughee.MainActivity;
import com.example.coffee_not_coughee.R;
import com.example.coffee_not_coughee.databinding.FragmentOrderSuccessBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class OrderSuccessFragment extends Fragment {

    private Button backHomeButton;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_order_success, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backHomeButton = (Button) getView().findViewById(R.id.backHomeButton);

        backHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(OrderSuccessFragment.this)
                        .navigate(R.id.action_OrderSuccessFragment_to_HomePageFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}