package com.example.coffee_not_coughee.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffee_not_coughee.MainActivity;
import com.example.coffee_not_coughee.Model.CoffeeModel;
import com.example.coffee_not_coughee.R;

import java.util.List;

public class RedeemAdapter extends RecyclerView.Adapter<RedeemAdapter.RedeemViewHolder> {

    private List<CoffeeModel> redeemItemList;

    public RedeemAdapter(List<CoffeeModel> redeemItemList) {
        this.redeemItemList = redeemItemList;
    }

    @NonNull
    @Override
    public RedeemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.redeem_item, parent, false);
        return new RedeemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RedeemViewHolder holder, int position) {
        CoffeeModel redeemItem = redeemItemList.get(position);
        holder.redeemItemImage.setImageResource(redeemItem.coffeeImage);
        holder.redeemItemName.setText(redeemItem.coffeeName);
        holder.redeemItemPoints.setText(redeemItem.exchangePoints + " pts");
    }

    @Override
    public int getItemCount() {
        return redeemItemList.size();
    }

    public class RedeemViewHolder extends RecyclerView.ViewHolder {
        private ImageView redeemItemImage;
        private TextView redeemItemName;
        private TextView redeemItemPoints;
        private CardView redeemItemButton;

        public RedeemViewHolder(@NonNull View itemView) {
            super(itemView);
            redeemItemImage = itemView.findViewById(R.id.redeemItemImage);
            redeemItemName = itemView.findViewById(R.id.redeemItemName);
            redeemItemPoints = itemView.findViewById(R.id.redeemItemPoints);
            redeemItemButton = itemView.findViewById(R.id.redeemItemButton);
            redeemItemButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity mainActivity = (MainActivity) v.getContext();
                    if (mainActivity.getCurrentUser().points < redeemItemList.get(getBindingAdapterPosition()).exchangePoints) {
                        Toast.makeText(v.getContext(), "Not enough points to redeem reward!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mainActivity.getCurrentUser().points -= redeemItemList.get(getBindingAdapterPosition()).exchangePoints;
                    Toast.makeText(v.getContext(), "Redeemed successfully!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
