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

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.RewardViewHolder> {

    private List<CoffeeModel> rewardHistoryList;

    public RewardAdapter(List<CoffeeModel> rewardHistoryList) {
        this.rewardHistoryList = rewardHistoryList;
    }

    @NonNull
    @Override
    public RewardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reward_history_item, parent, false);
        return new RewardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RewardViewHolder holder, int position) {
        CoffeeModel rewardHistory = rewardHistoryList.get(position);
        int points_per_coffee;
        if (rewardHistory.coffeeSize == CoffeeModel.CoffeeSize.SMALL) {
            points_per_coffee = 40;
        }
        else if (rewardHistory.coffeeSize == CoffeeModel.CoffeeSize.MEDIUM) {
            points_per_coffee = 50;
        }
        else {
            points_per_coffee = 60;
        }
        holder.rewardHisItemName.setText(String.format("%s - %s x %d", rewardHistory.coffeeName, rewardHistory.coffeeSize, rewardHistory.quantity));
        holder.rewardHisItemDetail.setText(rewardHistory.getCoffeeDetails());
        holder.rewardHisItemPoints.setText(String.format("+ %d Pts", points_per_coffee * rewardHistory.quantity));
    }

    @Override
    public int getItemCount() {
        return rewardHistoryList.size();
    }

    public class RewardViewHolder extends RecyclerView.ViewHolder {
        private TextView rewardHisItemName;
        private TextView rewardHisItemDetail;
        private TextView rewardHisItemPoints;

        public RewardViewHolder(@NonNull View itemView) {
            super(itemView);
            rewardHisItemName = itemView.findViewById(R.id.rewardHisItemName);
            rewardHisItemDetail = itemView.findViewById(R.id.rewardHisItemDetail);
            rewardHisItemPoints = itemView.findViewById(R.id.rewardHisItemPts);
        }
    }
}
