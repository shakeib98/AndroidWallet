package com.example.androidwallet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.androidwallet.R;
import com.example.androidwallet.constants.Constants;
import com.example.androidwallet.presenter.customViews.CustomButton;
import com.example.androidwallet.sharedPrefs.SharedPrefsWallet;

import java.io.Console;

public class MainActivity extends AppCompatActivity {

    private String className = MainActivity.class.getName();

    CustomButton signIn,signUp;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        actionViews();
    }

    private void initViews(){
        if(SharedPrefsWallet.getStrings(this,Constants.STATUS).equals(Constants.ONLINE)){
            startActivity(new Intent(this,HomeActivity.class));
            finish();
        }

        signIn = findViewById(R.id.button_new_wallet);
        signUp = findViewById(R.id.button_recover_wallet);
    }

    private void actionViews(){
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this,InputPinActivity.class);
                intent.putExtra(Constants.LOGIN_CREATE_KEY,Constants.LOGIN_VALUE);
                startActivity(intent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this,InputPinActivity.class);
                intent.putExtra(Constants.LOGIN_CREATE_KEY,Constants.CREATE_VALUE);
                startActivity(intent);
            }
        });
    }

}
