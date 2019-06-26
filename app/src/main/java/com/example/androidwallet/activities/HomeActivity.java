package com.example.androidwallet.activities;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteBindOrColumnIndexOutOfRangeException;
import android.os.Bundle;

import com.example.androidwallet.adapter.CoinAdapter;
import com.example.androidwallet.constants.Constants;
import com.example.androidwallet.model.CryptoModel;
import com.example.androidwallet.sharedPrefs.SharedPrefsWallet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.androidwallet.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    CoinAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<CryptoModel> list;
    Context context;
    ImageView logOut;

    String TAG = HomeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();
        actionViews();

    }

    void initViews(){
        context = this;
        list = new ArrayList<>();

        logOut = findViewById(R.id.logout);

        CryptoModel model;
        model = new CryptoModel();
        model.name = "BITCOIN";
        model.colorId = ContextCompat.getColor(context,R.color.bitcoin);
        list.add(model);

        model = new CryptoModel();
        model.name = "ETHEREUM";
        model.colorId = ContextCompat.getColor(context,R.color.ethereum);
        list.add(model);

        model = new CryptoModel();
        model.name = "BITCOIN CASH";
        model.colorId = ContextCompat.getColor(context,R.color.bitcoinCash);
        list.add(model);

        model = new CryptoModel();
        model.name = "SAVE NODE";
        model.colorId = ContextCompat.getColor(context,R.color.saveNode);
        model.balance = getSaveNodeBalance();
        model.address = "Sca3sbLJV8kJbRceKyTivaPVjiWaq3BgCd";
        model.walletJsonObject = getValueFromWalletResponse();
        list.add(model);

        recyclerView = findViewById(R.id.recyclerView);

        adapter = new CoinAdapter(context,list);

        recyclerView.setLayoutManager(new LinearLayoutManager(context,RecyclerView.VERTICAL,false));

        recyclerView.setAdapter(adapter);
    }

    void actionViews(){
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefsWallet.putString(context, Constants.STATUS,Constants.OFFLINE);
                startActivity(new Intent(context,MainActivity.class));
                finish();
            }
        });
    }

    String getSaveNodeBalance(){
        String balanceObject = SharedPrefsWallet.getStrings(context,Constants.SAVE_NODE_WALLET_BALANCE_KEY);
        try{
            JSONObject jsonObject = new JSONObject(balanceObject);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONObject result = data.optJSONObject("result");
            if(result == null){
                Log.d(TAG, "BALANCE: " + "0");
                return "0";
            }else{
                String balance = result.optString("balance");
                Log.d(TAG, "BALANCE: " + balance);
                return balance;

            }
        }catch (Exception e){
            Log.d(TAG, e.getMessage());
        }
        return "";
    }

    String getValueFromWalletResponse(){
        String object = SharedPrefsWallet.getStrings(context,Constants.SAVE_NODE_WALLET_OBJECT_KEY);
        try{
            JSONObject jsonObject = new JSONObject(object);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONObject secret = data.getJSONObject("secret");
            if(secret != null){
                Log.d(TAG, "SECRET: " + secret.toString());
                //return secret.toString(); //actual account


                //for testing purpose
                return "{\"address\":\"7a723ea4e922073af0398be9d47c9ae8477b6d21\",\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"f8f55af6ac35cfd8583292a130123f9497b455ffccec990e1c8e49e8f569d848\",\"cipherparams\":{\"iv\":\"03dd0c57839da6f32f7fbd9f164ede4b\"},\"mac\":\"08a43ac0567e5ca230a62371dcef8befaedd57df03d88486403ba50afa252ba8\",\"kdf\":\"pbkdf2\",\"kdfparams\":{\"c\":262144,\"dklen\":32,\"prf\":\"hmac-sha256\",\"salt\":\"39527e6498b5ea3bde7dd7a6921575b51c9857052ecb94989c00568ff90fb0ee\"}},\"id\":\"e24c7d82-6d3b-4bfa-bf6d-615cbeb52c79\",\"version\":3}";
            }else{
                return "";
            }
        }catch (Exception e){
            Log.d(TAG, e.getMessage());
        }
        return  "";
    }

}
