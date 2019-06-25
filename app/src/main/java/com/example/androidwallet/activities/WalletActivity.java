package com.example.androidwallet.activities;

import android.content.Context;
import android.os.Bundle;

import com.example.androidwallet.constants.Constants;
import com.example.androidwallet.presenter.customViews.BaseTextView;
import com.example.androidwallet.presenter.customViews.CustomButton;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.androidwallet.R;
import com.google.android.material.textfield.TextInputLayout;

public class WalletActivity extends AppCompatActivity {

    ImageButton backButton;
    Context context;

    BaseTextView label;
    LinearLayout layout;

    Button send,receive;

    TextInputLayout textInputLayoutTo, textInputLayoutAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        initViews();
        actionViews();
    }

    void initViews(){
        context = this;
        backButton = findViewById(R.id.back_icon);
        label = findViewById(R.id.wallet_label);
        layout = findViewById(R.id.topLayout);
//        send = findViewById(R.id.send_button);
//        receive = findViewById(R.id.receive_button);
        textInputLayoutTo = findViewById(R.id.textInputLayoutTo);
        textInputLayoutAmount = findViewById(R.id.textInputLayoutAmount);

        layout.setBackgroundColor(getIntent().getIntExtra(Constants.COlOR_ID,0));
        label.setText(getIntent().getStringExtra(Constants.WALLET_NAME_KEY));

//        send.setBackgroundColor(getIntent().getIntExtra(Constants.COlOR_ID,0));
//        receive.setBackgroundColor(getIntent().getIntExtra(Constants.COlOR_ID,0));

        textInputLayoutTo.setBoxStrokeColor(getIntent().getIntExtra(Constants.COlOR_ID,0));
        textInputLayoutAmount.setBoxStrokeColor(getIntent().getIntExtra(Constants.COlOR_ID,0));

    }

    void actionViews(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
