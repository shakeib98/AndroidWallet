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

import android.view.View;
import android.widget.ImageView;

import com.example.androidwallet.R;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    CoinAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<CryptoModel> list;
    Context context;
    ImageView logOut;

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

}
