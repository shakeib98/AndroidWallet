package com.example.androidwallet.activities;

import android.content.Context;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.androidwallet.constants.Constants;
import com.example.androidwallet.presenter.customViews.BaseTextView;
import com.example.androidwallet.presenter.customViews.CustomButton;
import com.example.androidwallet.volleySingleton.VolleySingleton;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.androidwallet.R;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class WalletActivity extends AppCompatActivity {

    ImageButton backButton;
    Context context;

    BaseTextView label, balance, address;
    LinearLayout layout;

    String transactionObject;

    String TAG = WalletActivity.class.getName();

    Button send,receive,sendTransaction;

    TextInputLayout textInputLayoutTo, textInputLayoutAmount;

    EditText to, amount;

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
        balance = findViewById(R.id.balance);
        address = findViewById(R.id.address);
//        send = findViewById(R.id.send_button);
//        receive = findViewById(R.id.receive_button);
        textInputLayoutTo = findViewById(R.id.textInputLayoutTo);
        textInputLayoutAmount = findViewById(R.id.textInputLayoutAmount);
        to = findViewById(R.id.editTextTo);
        amount = findViewById(R.id.amountEditText);
        sendTransaction = findViewById(R.id.send_transaction_button);

        layout.setBackgroundColor(getIntent().getIntExtra(Constants.COlOR_ID,0));
        label.setText(getIntent().getStringExtra(Constants.WALLET_NAME_KEY));
        balance.setText("BALANCE: " +getIntent().getStringExtra(Constants.BALANCE));
        address.setText("ADDRESS: "+ getIntent().getStringExtra(Constants.ADDRESS));

        transactionObject = getIntent().getStringExtra(Constants.TRANSACTION_OBJECT);

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
        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!amount.getText().toString().isEmpty()){
                    if(Float.parseFloat(getIntent().getStringExtra(Constants.BALANCE)) < Float.parseFloat(amount.getText().toString())){
                        amount.setError("AMOUNT NOT VALID PLEASE ENTER AGAIN");
                        amount.setText("");
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        sendTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!to.getText().toString().isEmpty() && !amount.getText().toString().isEmpty()){
                    sendTransaction();
                }
            }
        });
    }

    void sendTransaction(){
        final JSONObject transactionObject = makeJSONForTransaction();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.TRANSFER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                to.getText().clear();
                amount.getText().clear();
                Toast.makeText(context,"TRANSACTION COMPLETED",Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/json");
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public byte[] getBody()     {
                try {
                    return transactionObject.toString().getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return new byte[0];
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    JSONObject makeJSONForTransaction(){
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("from_address",getIntent().getStringExtra(Constants.ADDRESS));
            jsonObject.put("from_private_key",transactionObject);
            jsonObject.put("value",amount.getText().toString());
            jsonObject.put("toAddress",to.getText().toString());
            Log.d(TAG, jsonObject.toString());
            return jsonObject;

        }catch (Exception e){
            Log.d(TAG, e.getMessage());
        }
        return null;
    }

}
