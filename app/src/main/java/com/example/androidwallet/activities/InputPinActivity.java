package com.example.androidwallet.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.androidwallet.constants.Constants;
import com.example.androidwallet.presenter.customViews.BaseTextView;
import com.example.androidwallet.presenter.customViews.CustomButton;
import com.example.androidwallet.sharedPrefs.SharedPrefsWallet;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.androidwallet.R;

public class InputPinActivity extends AppCompatActivity {


    View digit1, digit2, digit3, digit4, digit5, digit6;

    CustomButton num1, num2, num3, num4, num5, num6, num7, num8, num9, num0;

    ImageButton delete;


    int counter = 0;

    String pin = "";

    String confirmPin = "";

    boolean flag1 = false, flag2 = false, flag3 = false, flag4 = false, flag5 = false, flag6 = false, signIn, signUp;

    Context context;

    SharedPrefsWallet sharedPrefsWallet;


    String TAG = InputPinActivity.class.getName();

    BaseTextView title, desciption;

    String intentValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_pin);

        initViews();
        actionViews();
    }

    @SuppressLint("SetTextI18n")
    void initViews() {


        digit1 = findViewById(R.id.digit1);
        digit2 = findViewById(R.id.digit2);
        digit3 = findViewById(R.id.digit3);
        digit4 = findViewById(R.id.digit4);
        digit5 = findViewById(R.id.digit5);
        digit6 = findViewById(R.id.digit6);


        num0 = findViewById(R.id.num0);
        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        num3 = findViewById(R.id.num3);
        num4 = findViewById(R.id.num4);
        num5 = findViewById(R.id.num5);
        num6 = findViewById(R.id.num6);
        num7 = findViewById(R.id.num7);
        num8 = findViewById(R.id.num8);
        num9 = findViewById(R.id.num9);
        delete = findViewById(R.id.delete);

        context = InputPinActivity.this;

        title = findViewById(R.id.title);
        desciption = findViewById(R.id.description);


        sharedPrefsWallet = new SharedPrefsWallet(context);

        try {
            Intent intent = getIntent();
            if (intent.getStringExtra(Constants.LOGIN_CREATE_KEY).equals(Constants.LOGIN_VALUE)) {
                signIn = true;
                intentValue = Constants.LOGIN_VALUE;
                title.setText("ENTER PIN");
                desciption.setText("Enter PIN to access your wallet");
                Log.d(TAG, title.getText().toString());
                ;
            } else {
                signUp = true;
                intentValue = Constants.CREATE_VALUE;
                Log.d(TAG, title.getText().toString());
            }
        } catch (Exception e) {
            Log.d(TAG, "EMPTY INTENT");

        }

    }

    void actionViews() {
//        keyboard.setShowDecimal(false);

        //      int[] pinDigitButtonColors = getResources().getIntArray(R.array.pin_digit_button_colors);
        //    keyboard.setButtonTextColor(pinDigitButtonColors);

        num0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counter != 6) {
                    counter++;
                    pin = pin + "0";
                    Log.d(TAG, pin);
                    if (!flag1) {
                        digit1.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag1 = true;
                    } else if (flag1 && !flag2) {
                        digit2.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag2 = true;
                    } else if (flag1 && flag2 && !flag3) {
                        digit3.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag3 = true;
                    } else if (flag1 && flag2 && flag3 && !flag4) {
                        digit4.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag4 = true;
                    } else if (flag1 && flag2 && flag3 && flag4 && !flag5) {
                        digit5.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag5 = true;
                    } else if (flag1 && flag2 && flag3 && flag4 && flag5 && !flag6) {
                        digit6.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag6 = true;
                    }
                    if (counter == 6) {
                        // SharedPrefsWallet.putString(context, Constants.PIN_KEY,pin);
                        if (signUp && confirmPin.isEmpty()) {
                            counter = 0;
                            confirmPin = pin;
                            pin = "";
                            setDotsEmpty();
                            Toast.makeText(context, "ENTER PIN AGAIN", Toast.LENGTH_SHORT).show();
                        } else if (signUp && !confirmPin.isEmpty()) {
                            checkPINS(confirmPin, pin);
                        }else if(signIn){
                            Log.d(TAG, "IN CONDITION");
                            checkPINFromSharedPrefs(pin);
                        }
                        //Log.d(TAG, SharedPrefsWallet.getStrings(context, Constants.PIN_KEY));
                    }
                }
            }
        });

        num1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counter != 6) {
                    counter++;
                    pin = pin + "1";
                    Log.d(TAG, pin);
                    if (!flag1) {
                        digit1.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag1 = true;
                    } else if (flag1 && !flag2) {
                        digit2.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag2 = true;


                    } else if (flag1 && flag2 && !flag3) {
                        digit3.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag3 = true;


                    } else if (flag1 && flag2 && flag3 && !flag4) {
                        digit4.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag4 = true;


                    } else if (flag1 && flag2 && flag3 && flag4 && !flag5) {
                        digit5.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag5 = true;


                    } else if (flag1 && flag2 && flag3 && flag4 && flag5 && !flag6) {
                        digit6.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag6 = true;

                        if (counter == 6) {
                            //SharedPrefsWallet.putString(context, Constants.PIN_KEY, pin);
                            if (signUp && confirmPin.isEmpty()) {
                                counter = 0;
                                confirmPin = pin;
                                pin = "";
                                setDotsEmpty();
                                Toast.makeText(context, "ENTER PIN AGAIN", Toast.LENGTH_SHORT).show();
                            } else if (signUp && !confirmPin.isEmpty()) {
                                checkPINS(confirmPin, pin);
                            }else if(signIn){
                                Log.d(TAG, "IN CONDITION");
                                checkPINFromSharedPrefs(pin);
                            }
                           // Log.d(TAG, SharedPrefsWallet.getStrings(context, Constants.PIN_KEY));
                        }
                    }
                }
            }
        });

        num2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counter != 6) {
                    counter++;
                    pin = pin + "2";
                    Log.d(TAG, pin);
                    if (!flag1) {
                        digit1.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag1 = true;
                    } else if (flag1 && !flag2) {
                        digit2.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag2 = true;
                    } else if (flag1 && flag2 && !flag3) {
                        digit3.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag3 = true;
                    } else if (flag1 && flag2 && flag3 && !flag4) {
                        digit4.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag4 = true;
                    } else if (flag1 && flag2 && flag3 && flag4 && !flag5) {
                        digit5.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag5 = true;
                    } else if (flag1 && flag2 && flag3 && flag4 && flag5 && !flag6) {
                        digit6.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag6 = true;
                    }

                    if (counter == 6) {
//                        SharedPrefsWallet.putString(context, Constants.PIN_KEY, pin);
//
//                        Log.d(TAG, SharedPrefsWallet.getStrings(context, Constants.PIN_KEY));

                        if(signUp && confirmPin.isEmpty()){
                            counter = 0;
                            confirmPin = pin;
                            pin = "";
                            setDotsEmpty();
                            Toast.makeText(context,"ENTER PIN AGAIN",Toast.LENGTH_SHORT).show();
                        }else if(signUp && !confirmPin.isEmpty()){
                            checkPINS(confirmPin,pin);
                        }else if(signIn){
                            checkPINFromSharedPrefs(pin);
                        }
                    }
                }
            }
        });

        num3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counter != 6) {
                    counter++;
                    pin = pin + "3";
                    Log.d(TAG, pin);
                    if (!flag1) {
                        digit1.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag1 = true;
                    } else if (flag1 && !flag2) {
                        digit2.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag2 = true;


                    } else if (flag1 && flag2 && !flag3) {
                        digit3.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag3 = true;


                    } else if (flag1 && flag2 && flag3 && !flag4) {
                        digit4.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag4 = true;


                    } else if (flag1 && flag2 && flag3 && flag4 && !flag5) {
                        digit5.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag5 = true;


                    } else if (flag1 && flag2 && flag3 && flag4 && flag5 && !flag6) {
                        digit6.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag6 = true;


                    }

                    if (counter == 6) {
//                        SharedPrefsWallet.putString(context, Constants.PIN_KEY, pin);
//
//                        Log.d(TAG, SharedPrefsWallet.getStrings(context, Constants.PIN_KEY));

                        if(signUp && confirmPin.isEmpty()){
                            counter = 0;
                            confirmPin = pin;
                            pin = "";
                            setDotsEmpty();
                            Toast.makeText(context,"ENTER PIN AGAIN",Toast.LENGTH_SHORT).show();
                        }else if(signUp && !confirmPin.isEmpty()){
                            checkPINS(confirmPin,pin);
                        }else if(signIn){
                            checkPINFromSharedPrefs(pin);
                        }
                    }
                }
            }
        });

        num4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counter != 6) {
                    counter++;
                    pin = pin + "4";
                    Log.d(TAG, pin);
                    if (!flag1) {
                        digit1.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag1 = true;
                    } else if (flag1 && !flag2) {
                        digit2.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag2 = true;


                    } else if (flag1 && flag2 && !flag3) {
                        digit3.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag3 = true;


                    } else if (flag1 && flag2 && flag3 && !flag4) {
                        digit4.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag4 = true;


                    } else if (flag1 && flag2 && flag3 && flag4 && !flag5) {
                        digit5.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag5 = true;


                    } else if (flag1 && flag2 && flag3 && flag4 && flag5 && !flag6) {
                        digit6.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag6 = true;


                    }

                    if (counter == 6) {
//                        SharedPrefsWallet.putString(context, Constants.PIN_KEY, pin);
//
//                        Log.d(TAG, SharedPrefsWallet.getStrings(context, Constants.PIN_KEY));

                        if(signUp && confirmPin.isEmpty()){
                            counter = 0;
                            confirmPin = pin;
                            pin = "";
                            setDotsEmpty();
                            Toast.makeText(context,"ENTER PIN AGAIN",Toast.LENGTH_SHORT).show();
                        }else if(signUp && !confirmPin.isEmpty()){
                            checkPINS(confirmPin,pin);
                        }else if(signIn){
                            checkPINFromSharedPrefs(pin);
                        }
                    }
                }
            }
        });

        num5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (counter != 6) {
                    counter++;
                    pin = pin + "5";
                    Log.d(TAG, pin);
                    if (!flag1) {
                        digit1.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag1 = true;
                    } else if (flag1 && !flag2) {
                        digit2.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag2 = true;


                    } else if (flag1 && flag2 && !flag3) {
                        digit3.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag3 = true;


                    } else if (flag1 && flag2 && flag3 && !flag4) {
                        digit4.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag4 = true;


                    } else if (flag1 && flag2 && flag3 && flag4 && !flag5) {
                        digit5.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag5 = true;


                    } else if (flag1 && flag2 && flag3 && flag4 && flag5 && !flag6) {
                        digit6.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag6 = true;


                    }

                    if (counter == 6) {
//                        SharedPrefsWallet.putString(context, Constants.PIN_KEY, pin);
//
//                        Log.d(TAG, SharedPrefsWallet.getStrings(context, Constants.PIN_KEY));

                        if(signUp && confirmPin.isEmpty()){
                            counter = 0;
                            confirmPin = pin;
                            pin = "";
                            setDotsEmpty();
                            Toast.makeText(context,"ENTER PIN AGAIN",Toast.LENGTH_SHORT).show();
                        }else if(signUp && !confirmPin.isEmpty()){
                            checkPINS(confirmPin,pin);
                        }else if(signIn){
                            checkPINFromSharedPrefs(pin);
                        }
                    }
                }
            }
        });

        num6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counter != 6) {
                    counter++;
                    pin = pin + "6";
                    Log.d(TAG, pin);
                    if (!flag1) {
                        digit1.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag1 = true;
                    } else if (flag1 && !flag2) {
                        digit2.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag2 = true;


                    } else if (flag1 && flag2 && !flag3) {
                        digit3.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag3 = true;


                    } else if (flag1 && flag2 && flag3 && !flag4) {
                        digit4.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag4 = true;


                    } else if (flag1 && flag2 && flag3 && flag4 && !flag5) {
                        digit5.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag5 = true;


                    } else if (flag1 && flag2 && flag3 && flag4 && flag5 && !flag6) {
                        digit6.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag6 = true;


                    }

                    if (counter == 6) {
//                        SharedPrefsWallet.putString(context, Constants.PIN_KEY, pin);
//
//                        Log.d(TAG, SharedPrefsWallet.getStrings(context, Constants.PIN_KEY));
                        if(signUp && confirmPin.isEmpty()){
                            counter = 0;
                            confirmPin = pin;
                            pin = "";
                            setDotsEmpty();
                            Toast.makeText(context,"ENTER PIN AGAIN",Toast.LENGTH_SHORT).show();
                        }else if(signUp && !confirmPin.isEmpty()){
                            checkPINS(confirmPin,pin);
                        }else if(signIn){
                            checkPINFromSharedPrefs(pin);
                        }
                    }
                }
            }
        });

        num7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counter != 6) {
                    counter++;
                    pin = pin + "7";
                    Log.d(TAG, pin);
                    if (!flag1) {
                        digit1.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag1 = true;
                    } else if (flag1 && !flag2) {
                        digit2.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag2 = true;


                    } else if (flag1 && flag2 && !flag3) {
                        digit3.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag3 = true;


                    } else if (flag1 && flag2 && flag3 && !flag4) {
                        digit4.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag4 = true;


                    } else if (flag1 && flag2 && flag3 && flag4 && !flag5) {
                        digit5.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag5 = true;


                    } else if (flag1 && flag2 && flag3 && flag4 && flag5 && !flag6) {
                        digit6.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag6 = true;


                    }

                    if (counter == 6) {
//                        SharedPrefsWallet.putString(context, Constants.PIN_KEY, pin);
//
//                        Log.d(TAG, SharedPrefsWallet.getStrings(context, Constants.PIN_KEY));

                        if(signUp && confirmPin.isEmpty()){
                            counter = 0;
                            confirmPin = pin;
                            pin = "";
                            setDotsEmpty();
                            Toast.makeText(context,"ENTER PIN AGAIN",Toast.LENGTH_SHORT).show();
                        }else if(signUp && !confirmPin.isEmpty()){
                            checkPINS(confirmPin,pin);
                        }else if(signIn){
                            checkPINFromSharedPrefs(pin);
                        }
                    }
                }
            }
        });

        num8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counter != 6) {
                    counter++;
                    pin = pin + "8";
                    Log.d(TAG, pin);
                    if (!flag1) {
                        digit1.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag1 = true;
                    } else if (flag1 && !flag2) {
                        digit2.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag2 = true;


                    } else if (flag1 && flag2 && !flag3) {
                        digit3.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag3 = true;
                    } else if (flag1 && flag2 && flag3 && !flag4) {
                        digit4.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag4 = true;
                    } else if (flag1 && flag2 && flag3 && flag4 && !flag5) {
                        digit5.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag5 = true;


                    } else if (flag1 && flag2 && flag3 && flag4 && flag5 && !flag6) {
                        digit6.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag6 = true;
                    }

                    if (counter == 6) {
//                        SharedPrefsWallet.putString(context, Constants.PIN_KEY, pin);
//
//                        Log.d(TAG, SharedPrefsWallet.getStrings(context, Constants.PIN_KEY));
                        if(signUp && confirmPin.isEmpty()){
                            counter = 0;
                            confirmPin = pin;
                            pin = "";
                            setDotsEmpty();
                            Toast.makeText(context,"ENTER PIN AGAIN",Toast.LENGTH_SHORT).show();
                        }else if(signUp && !confirmPin.isEmpty()){
                            checkPINS(confirmPin,pin);
                        }else if(signIn){
                            checkPINFromSharedPrefs(pin);
                        }
                    }
                }
            }
        });

        num9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counter != 6) {
                    counter++;
                    pin = pin + "9";
                    Log.d(TAG, pin);
                    if (!flag1) {
                        digit1.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag1 = true;
                    } else if (flag1 && !flag2) {
                        digit2.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag2 = true;

                    } else if (flag1 && flag2 && !flag3) {
                        digit3.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag3 = true;

                    } else if (flag1 && flag2 && flag3 && !flag4) {
                        digit4.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag4 = true;

                    } else if (flag1 && flag2 && flag3 && flag4 && !flag5) {
                        digit5.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag5 = true;


                    } else if (flag1 && flag2 && flag3 && flag4 && flag5 && !flag6) {
                        digit6.setBackground(getDrawable(R.drawable.ic_pin_dot_filled_white));
                        flag6 = true;
                    }

                    if (counter == 6) {
//                        SharedPrefsWallet.putString(context, Constants.PIN_KEY, pin);
//
//                        Log.d(TAG, SharedPrefsWallet.getStrings(context, Constants.PIN_KEY));
                        if(signUp && confirmPin.isEmpty()){
                            counter = 0;
                            confirmPin = pin;
                            pin = "";
                            setDotsEmpty();
                            Toast.makeText(context,"ENTER PIN AGAIN",Toast.LENGTH_SHORT).show();
                        }else if(signUp && !confirmPin.isEmpty()){
                            checkPINS(confirmPin,pin);
                        }else if(signIn){
                            checkPINFromSharedPrefs(pin);
                        }
                    }
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(counter!=0){
                    pin = pin.substring(0,pin.length()-1);
                    counter--;
                    if(flag1 && !flag2 && !flag3 && !flag4 && !flag5 && !flag6) {
                        flag1 = false;
                        digit1.setBackground(getDrawable(R.drawable.ic_pin_dot_empty));

                    }
                    else if(flag2 && flag1 && !flag3 && !flag4 && !flag5 && !flag6) {
                        flag2 = false;
                        digit2.setBackground(getDrawable(R.drawable.ic_pin_dot_empty));

                    }
                    else if(flag1 && flag2 && flag3 && !flag4 && !flag5 && !flag6) {
                        flag3 = false;
                        digit3.setBackground(getDrawable(R.drawable.ic_pin_dot_empty));

                    }
                    else if(flag1 && flag2 && flag3 && flag4 && !flag5 && !flag6) {
                        flag4 = false;
                        digit4.setBackground(getDrawable(R.drawable.ic_pin_dot_empty));

                    }
                    else if(flag1 && flag2 && flag3 && flag4 && flag5 && !flag6) {
                        flag5 = false;
                        digit5.setBackground(getDrawable(R.drawable.ic_pin_dot_empty));

                    }
                    else if(flag1 && flag2 && flag3 && flag4 && flag5 && flag6) {
                        flag6 = false;
                        digit6.setBackground(getDrawable(R.drawable.ic_pin_dot_empty));
                    }
                }
            }
        });


    }

    void checkPINS(String confirmPin, String pin) {
        if (confirmPin.equals(pin)) {
            Toast.makeText(context, "PIN IS OKAY", Toast.LENGTH_SHORT).show();
            Log.d(TAG, confirmPin);
            SharedPrefsWallet.putString(context,Constants.PIN_KEY,pin);
            setDotsEmpty();
            startActivity(new Intent(InputPinActivity.this,MainActivity.class));
            finish();
        } else {
            Toast.makeText(context, "INVALID PIN", Toast.LENGTH_SHORT).show();
            setDotsEmpty();
            this.confirmPin = "";
            this.pin = "";
            this.counter = 0;
        }
    }

    void checkPINFromSharedPrefs(String pin){
        if(pin.equals(SharedPrefsWallet.getStrings(context,Constants.PIN_KEY))){
            Toast.makeText(context,"PIN IS OKAY!",Toast.LENGTH_SHORT).show();
            SharedPrefsWallet.putString(context,Constants.STATUS,Constants.ONLINE);
            startActivity(new Intent(InputPinActivity.this,HomeActivity.class));
            finish();
        }else{
            Toast.makeText(context,"INVALID PIN",Toast.LENGTH_SHORT).show();
            setDotsEmpty();
            setFlagsTrue();
            this.pin = "";
            this.counter = 0;
        }
    }

    void setDotsEmpty() {
        digit1.setBackground(getDrawable(R.drawable.ic_pin_dot_empty));
        digit2.setBackground(getDrawable(R.drawable.ic_pin_dot_empty));
        digit3.setBackground(getDrawable(R.drawable.ic_pin_dot_empty));
        digit4.setBackground(getDrawable(R.drawable.ic_pin_dot_empty));
        digit5.setBackground(getDrawable(R.drawable.ic_pin_dot_empty));
        digit6.setBackground(getDrawable(R.drawable.ic_pin_dot_empty));

        setFlagsTrue();
    }

    void setFlagsTrue() {
        flag1 = false;
        flag2 = false;
        flag3 = false;
        flag4 = false;
        flag5 = false;
        flag6 = false;
    }

}
