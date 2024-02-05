package com.example.coffee_not_coughee.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffee_not_coughee.Adapter.RedeemAdapter;
import com.example.coffee_not_coughee.Model.CoffeeModel;
import com.example.coffee_not_coughee.R;
import com.example.coffee_not_coughee.databinding.FragmentRedeemBinding;

import java.util.ArrayList;
import java.util.List;

public class RedeemFragment extends Fragment {

    private FragmentRedeemBinding binding;
    private RedeemAdapter redeemAdapter;
    private List<CoffeeModel> redeemItemList;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentRedeemBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize RecyclerView and its adapter
        RecyclerView recyclerViewRedeem = binding.recyclerViewRedeem;
        recyclerViewRedeem.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Sample data for the RecyclerView
        redeemItemList = new ArrayList<>();
        redeemItemList.add(new CoffeeModel(R.drawable.americano, "Americano", 3.00f, 1000));
        redeemItemList.add(new CoffeeModel(R.drawable.cappucino, "Cappuccino", 3.00f, 1000));
        redeemItemList.add(new CoffeeModel(R.drawable.mocha, "Mocha", 3.25f, 1000));
        redeemItemList.add(new CoffeeModel(R.drawable.flat_white, "Flat White", 3.50f, 1000));

        // Create and set the adapter
        redeemAdapter = new RedeemAdapter(redeemItemList);
        recyclerViewRedeem.setAdapter(redeemAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
