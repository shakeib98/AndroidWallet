package com.example.androidwallet.activities;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteBindOrColumnIndexOutOfRangeException;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.androidwallet.adapter.CoinAdapter;
import com.example.androidwallet.constants.Constants;
import com.example.androidwallet.model.CryptoModel;
import com.example.androidwallet.sharedPrefs.SharedPrefsWallet;
import com.example.androidwallet.volleySingleton.VolleySingleton;
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
import android.widget.ProgressBar;

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
    String balance = "";

    ProgressBar progressBar;

    String TAG = HomeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();
        actionViews();
        getBalance();

    }

    void initViews(){
        context = this;
        list = new ArrayList<>();

        logOut = findViewById(R.id.logout);

        CryptoModel model;
//        model = new CryptoModel();
//        model.name = "BITCOIN";
//        model.colorId = ContextCompat.getColor(context,R.color.bitcoin);
//        model.balance = this.balance;
//        model.address = SharedPrefsWallet.getStrings(context,Constants.BTC_WALLET_PUBLIC_ADDRESS);
//        list.add(model);

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

        //model.address = SharedPrefsWallet.getStrings(context,Constants.SAVE_NODE_WALLET_ADDRESS);
        model.walletJsonObject = getValueFromWalletResponse();
        list.add(model);

        recyclerView = findViewById(R.id.recyclerView);

        adapter = new CoinAdapter(context,list);

        progressBar = findViewById(R.id.progress);

        recyclerView.setVisibility(View.INVISIBLE);

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

    void getBTCbalance(){
        final String[] balance = {""};
        StringRequest stringRequest = new StringRequest(Request.Method.GET,Constants.GET_BALANCE_BTC_TEST_NET+SharedPrefsWallet.getStrings(context,Constants.BTC_WALLET_PUBLIC_ADDRESS),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        SharedPrefsWallet.putString(context,Constants.SAVE_NODE_WALLET_BALANCE_KEY,response);
                        progressBar.setVisibility(View.INVISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        CryptoModel model;
                        model = new CryptoModel();
//                        try{
//                            JSONObject jsonObject = new JSONObject(response);
//                            JSONObject dataObject = jsonObject.getJSONObject("data");
//                            SharedPrefsWallet.putString(context,Constants.SAVE_NODE_WALLET_ADDRESS,dataObject.optString("address"));
//                            Log.d(TAG, "Address: "+ SharedPrefsWallet.getStrings(context,Constants.SAVE_NODE_WALLET_ADDRESS));
//                        } catch (Exception e){
//
//                        }
//                        startActivity(new Intent(InputPinActivity.this,HomeActivity.class));
//                        finish();
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject dataObject = jsonObject.getJSONObject("data");

                            /*** because sometimes API data returns null object ***/
                            if(dataObject == null){
                                model.balance = SharedPrefsWallet.getStrings(context,Constants.BTC_WALLET_BALANCE_KEY);
                            }else{
                                JSONObject walletObject = dataObject.getJSONObject("wallet");
                                balance[0] = String.valueOf(walletObject.getInt("balance"));
                                SharedPrefsWallet.putString(context,Constants.BTC_WALLET_BALANCE_KEY,balance[0]);
                            }

                            model.name = "BITCOIN";
                            model.colorId = ContextCompat.getColor(context,R.color.bitcoin);
                           // model.balance = balance[0];
                            model.address = SharedPrefsWallet.getStrings(context,Constants.BTC_WALLET_PUBLIC_ADDRESS);
                            list.add(model);
                            adapter.notifyDataSetChanged();
                            Log.d(TAG, "balance: " + balance[0]);


                        }catch (Exception e){
                            Log.d(TAG, e.toString());
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.toString());
//                        Log.d(TAG, String.valueOf(error.networkResponse.statusCode));
                    }
                });
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);

        this.balance = balance[0];
    }


    String getValueFromWalletResponse(){
        String object = SharedPrefsWallet.getStrings(context,Constants.SAVE_NODE_WALLET_OBJECT_KEY);
        try{
            JSONObject jsonObject = new JSONObject(object);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONObject secret = data.getJSONObject("secret");
            if(secret != null){
                Log.d(TAG, "SECRET: " + secret.toString());
               // return secret.toString(); //actual account


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

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }
    void getBalance(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET,Constants.GET_BALANCE_SAVE_NODE+"SPaWzGJgQ6ThNH71GF1V2qTtRdTdxVV1JG",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        SharedPrefsWallet.putString(context,Constants.SAVE_NODE_WALLET_BALANCE_KEY,response);
                        getBTCbalance();
//                        try{
//                            JSONObject jsonObject = new JSONObject(response);
//                            JSONObject dataObject = jsonObject.getJSONObject("data");
//                            SharedPrefsWallet.putString(context,Constants.SAVE_NODE_WALLET_ADDRESS,dataObject.optString("address"));
//                            Log.d(TAG, "Address: "+ SharedPrefsWallet.getStrings(context,Constants.SAVE_NODE_WALLET_ADDRESS));
//                        } catch (Exception e){
//
//                        }
//                        startActivity(new Intent(InputPinActivity.this,HomeActivity.class));
//                        finish();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.toString());
//                        Log.d(TAG, String.valueOf(error.networkResponse.statusCode));
                    }
                });
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

}
