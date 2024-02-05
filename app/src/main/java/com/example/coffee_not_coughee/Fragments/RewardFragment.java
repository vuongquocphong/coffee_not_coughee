package com.example.coffee_not_coughee.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffee_not_coughee.Adapter.CoffeeAdapter;
import com.example.coffee_not_coughee.Adapter.RewardAdapter;
import com.example.coffee_not_coughee.MainActivity;
import com.example.coffee_not_coughee.Model.CoffeeModel;
import com.example.coffee_not_coughee.R;
import com.example.coffee_not_coughee.databinding.FragmentRewardBinding;

import java.util.ArrayList;
import java.util.List;

public class RewardFragment extends Fragment {
    private MainActivity mainActivity;
    private FragmentRewardBinding binding;
    private RewardAdapter rewardAdapter;
    private List<CoffeeModel> rewardHistoryList;

    ImageView loyal_cup_1;
    ImageView loyal_cup_2;
    ImageView loyal_cup_3;
    ImageView loyal_cup_4;
    ImageView loyal_cup_5;
    ImageView loyal_cup_6;
    ImageView loyal_cup_7;
    ImageView loyal_cup_8;
    private TextView cup_counts;
    private TextView pointsCount;

    private ImageView redeemNavButton;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentRewardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize RecyclerView and its adapter

        mainActivity = (MainActivity) getActivity();
        rewardHistoryList = mainActivity.rewardHisList;

        RecyclerView rewardHistoryRecyclerView = binding.rewardHistoryRecyclerView;
        rewardHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

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

        pointsCount = view.findViewById(R.id.rewardPointCount);

        redeemNavButton = view.findViewById(R.id.redeemNavButton);

        // Update the loyal cups
        updateLoyalCount();

        // Update the ImageSrc of loyal cups base on the number of stamps
        updateLoyalImageSrc();

        // Update the points
        updatePoint();

        // Create and set the adapter
        rewardAdapter = new RewardAdapter(rewardHistoryList);
        rewardHistoryRecyclerView.setAdapter(rewardAdapter);

        redeemNavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(RewardFragment.this)
                        .navigate(R.id.action_RewardFragment_to_RedeemFragment);
            }
        });
    }

    private void updateLoyalCount() {
        int stamps = ((MainActivity)getActivity()).getCurrentUser().cup_count % 8;
        cup_counts.setText(String.format("%d/8", stamps));
    }

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

    private void updatePoint() {
        int points = ((MainActivity)getActivity()).getCurrentUser().points;
        pointsCount.setText(String.format("%d", points));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
