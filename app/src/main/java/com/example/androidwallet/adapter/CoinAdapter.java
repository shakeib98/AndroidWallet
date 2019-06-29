package com.example.androidwallet.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidwallet.R;
import com.example.androidwallet.activities.WalletActivity;
import com.example.androidwallet.constants.Constants;
import com.example.androidwallet.model.CryptoModel;
import com.example.androidwallet.presenter.customViews.BaseTextView;

import java.util.ArrayList;
import java.util.List;

public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.CoinViewHolder> {

    List<CryptoModel> list;
    Context context;

    public CoinAdapter(Context context,ArrayList<CryptoModel> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CoinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CoinViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.coin_adapter_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CoinViewHolder holder, final int position) {
        holder.background.setBackgroundColor(list.get(position).colorId);
        Log.d("REC VIEW", list.get(position).name);
        holder.name.setText(list.get(position).name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // temporary to avoid crashes
                if((list.get(position).name.equals("SAVE NODE")) || (list.get(position).name.equals("BITCOIN"))){
                    Intent intent = new Intent(context,WalletActivity.class);
                    intent.putExtra(Constants.WALLET_NAME_KEY,list.get(position).name);
                    intent.putExtra(Constants.COlOR_ID,list.get(position).colorId);
                    intent.putExtra(Constants.BALANCE,list.get(position).balance);
                    Log.d("BALANCE IN ADAPTER", list.get(position).balance);
                    intent.putExtra(Constants.ADDRESS,list.get(position).address);
                    intent.putExtra(Constants.TRANSACTION_OBJECT,list.get(position).walletJsonObject);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CoinViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout background;
        BaseTextView name;
        public CoinViewHolder(@NonNull View itemView) {
            super(itemView);
            background = itemView.findViewById(R.id.wallet_card);
            name = itemView.findViewById(R.id.wallet_name);
        }
    }
}
