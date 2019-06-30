package com.example.androidwallet.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.androidwallet.constants.Constants;
import com.example.androidwallet.presenter.customViews.BaseTextView;
import com.example.androidwallet.presenter.customViews.CustomButton;
import com.example.androidwallet.presenter.customViews.CustomEditText;
import com.example.androidwallet.sharedPrefs.SharedPrefsWallet;
import com.example.androidwallet.volleySingleton.VolleySingleton;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidwallet.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
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

    Button send, receive, sendTransaction;

    ImageView qrCode;

    BaseTextView publicAddress;

    // TextInputLayout textInputLayoutTo, textInputLayoutAmount;

    CustomEditText to, amount;

    ProgressBar progressBar;

    RelativeLayout progressLayout;

    BottomSheetBehavior bottomSheetBehavior;

    CustomButton paste;

    View bottomSheetSend;

    LinearLayout sendLayout, receiveLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        initViews();
        generateBitmap();
        actionViews();
    }

    void initViews() {
        context = this;
        backButton = findViewById(R.id.back_icon);
        label = findViewById(R.id.wallet_label);
        layout = findViewById(R.id.topLayout);
        balance = findViewById(R.id.balance);
        address = findViewById(R.id.address);
        progressBar = findViewById(R.id.progress);
        progressLayout = findViewById(R.id.darkLayout);
        send = findViewById(R.id.send_button);
        receive = findViewById(R.id.receive_button);
//        textInputLayoutTo = findViewById(R.id.textInputLayoutTo);
//        textInputLayoutAmount = findViewById(R.id.textInputLayoutAmount);
        to = findViewById(R.id.editTextTo);
        amount = findViewById(R.id.amountEditText);
        sendTransaction = findViewById(R.id.send_transaction_button);
        paste = findViewById(R.id.paste_button);

        qrCode = findViewById(R.id.publicKeyQR);
        publicAddress = findViewById(R.id.publicKey);
        publicAddress.setText(getIntent().getStringExtra(Constants.ADDRESS));
        sendLayout = findViewById(R.id.sendLayout);
        receiveLayout = findViewById(R.id.recLayout);

        layout.setBackgroundColor(getIntent().getIntExtra(Constants.COlOR_ID, 0));
        label.setText(getIntent().getStringExtra(Constants.WALLET_NAME_KEY));
        balance.setText(getIntent().getStringExtra(Constants.BALANCE));
        address.setText("ADDRESS: " + getIntent().getStringExtra(Constants.ADDRESS));


        // for save node otherwise it will be empty
        transactionObject = getIntent().getStringExtra(Constants.TRANSACTION_OBJECT);

        send.setBackgroundColor(getIntent().getIntExtra(Constants.COlOR_ID, 0));
        receive.setBackgroundColor(getIntent().getIntExtra(Constants.COlOR_ID, 0));

        bottomSheetSend = findViewById(R.id.sendBottomSheet);

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetSend);


//        textInputLayoutTo.setBoxStrokeColor(getIntent().getIntExtra(Constants.COlOR_ID,0));
//        textInputLayoutAmount.setBoxStrokeColor(getIntent().getIntExtra(Constants.COlOR_ID,0));


    }

    void actionViews() {
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
                if (!amount.getText().toString().isEmpty()) {
                    if (label.getText().toString().equals("BITCOIN")) {
                        if (Float.parseFloat(getIntent().getStringExtra(Constants.BALANCE)) < Float.parseFloat(amount.getText().toString())) {
                            Toast.makeText(context, "Amount not valid please try again", Toast.LENGTH_SHORT).show();
                            amount.setText("");
                        }
//                        else if(Float.parseFloat(amount.getText().toString()) < 0.0001){
//                            amount.setError("PUT VALUE GREATER THAN OR EQUALS TO 0.0001");
//                            amount.setText("");
//                        }
                    } else if (Float.parseFloat(getIntent().getStringExtra(Constants.BALANCE)) < Float.parseFloat(amount.getText().toString())) {
                        Toast.makeText(context, "Amount not valid please try again", Toast.LENGTH_SHORT).show();
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
                if (!to.getText().toString().isEmpty() && !amount.getText().toString().isEmpty()) {
                    if (label.getText().toString().equals("SAVE NODE")) {
                        sendTransactionSaveNode();
                    } else if (label.getText().toString().equals("BITCOIN") && (Float.parseFloat(amount.getText().toString()) >= 0.0001)) {
                        sendTransactionBitcoin();
                    } else if (label.getText().toString().equals("BITCOIN") && (Float.parseFloat(amount.getText().toString()) < 0.0001)) {
                        Toast.makeText(context, "ENTER AMOUNT GREATER THAN 0.0001", Toast.LENGTH_SHORT).show();
                        amount.setText("");
                    } else if (label.getText().toString().equals("BCH")) {
                        sendTransactionBCH();
                    }
                }
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                receiveLayout.setVisibility(View.GONE);
                sendLayout.setVisibility(View.VISIBLE);
            }
        });

        paste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                try {
                    ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
                    to.setText(item.getText());

                } catch (Exception e) {
                    Toast.makeText(context, "NOTHING TO PASTE", Toast.LENGTH_SHORT).show();
                }


            }
        });

        receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                receiveLayout.setVisibility(View.VISIBLE);
                sendLayout.setVisibility(View.GONE);
            }
        });
    }

    void sendTransactionSaveNode() {
        progressLayout.setVisibility(View.VISIBLE);
        final JSONObject transactionObjectSend = makeJSONForTransactionSaveNode();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.TRANSFER_SAVE_NODE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                to.getText().clear();
                progressLayout.setVisibility(View.INVISIBLE);
                amount.getText().clear();
                Toast.makeText(context, "TRANSACTION COMPLETED", Toast.LENGTH_SHORT).show();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public byte[] getBody() {
                try {
                    return transactionObjectSend.toString().getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return new byte[0];
            }
        };
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

    JSONObject makeJSONForTransactionSaveNode() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("from_address", getIntent().getStringExtra(Constants.ADDRESS));
            jsonObject.put("from_private_key", transactionObject);
            jsonObject.put("value", amount.getText().toString());
            jsonObject.put("toAddress", to.getText().toString());
            Log.d(TAG, jsonObject.toString());
            return jsonObject;

        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        return null;
    }

    void sendTransactionBitcoin() {
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("from_address", SharedPrefsWallet.getStrings(context, Constants.BTC_WALLET_PUBLIC_ADDRESS));
            jsonObject.put("from_private_key", SharedPrefsWallet.getStrings(context, Constants.BTC_WALLET_PRIVATE_ADDRESS));
            jsonObject.put("to_address", to.getText().toString());
            jsonObject.put("value", String.valueOf(Float.parseFloat(amount.getText().toString()) * 100000000));
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        progressLayout.setVisibility(View.VISIBLE);
        Log.d(TAG, jsonObject.toString());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.TRANSFER_BTC_TEST_NET, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                to.getText().clear();
                progressLayout.setVisibility(View.INVISIBLE);
                amount.getText().clear();
                Toast.makeText(context, "TRANSACTION COMPLETED", Toast.LENGTH_SHORT).show();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public byte[] getBody() {
                try {
                    return jsonObject.toString().getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return new byte[0];
            }
        };
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

    void sendTransactionBCH() {
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("from_address", SharedPrefsWallet.getStrings(context, Constants.BCH_WALLET_PUBLIC_ADDRESS));
            jsonObject.put("from_private_key", SharedPrefsWallet.getStrings(context, Constants.BCH_WALLET_PRIVATE_ADDRESS));
            jsonObject.put("to_address", to.getText().toString());
            jsonObject.put("value", String.valueOf(Float.parseFloat(amount.getText().toString()) * 100000000));
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        progressLayout.setVisibility(View.VISIBLE);
        Log.d(TAG, jsonObject.toString());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.TRANSFER_BCH_TEST_NET, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                to.getText().clear();
                progressLayout.setVisibility(View.INVISIBLE);
                amount.getText().clear();
                Toast.makeText(context, "TRANSACTION COMPLETED", Toast.LENGTH_SHORT).show();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public byte[] getBody() {
                try {
                    return jsonObject.toString().getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return new byte[0];
            }
        };
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            finish();
        }
    }

    void generateBitmap() {
        String text = getIntent().getStringExtra(Constants.ADDRESS);// Whatever you need to encode in the QR code
        Log.d(TAG, text);
        try{
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            qrCode.setImageBitmap(bitmap);


        }catch (Exception e){
            Log.d(TAG, e.toString());
        }
    }
}
