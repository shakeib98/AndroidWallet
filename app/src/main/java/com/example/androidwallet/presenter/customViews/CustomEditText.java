package com.example.androidwallet.presenter.customViews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.example.androidwallet.R;
import com.example.androidwallet.manager.FontManager;
import com.example.androidwallet.utils.Utils;

@SuppressLint("AppCompatCustomView") // we don't need to support older versions
public class CustomEditText extends EditText {
    private static final String TAG = CustomEditText.class.getName();
    private final int ANIMATION_DURATION = 200;
    private int currentX = 0;
    private int currentY = 0;
    private boolean isBreadButton; //meaning is has the special animation and shadow

    public CustomEditText(Context context) {
        super(context);
    }

    public CustomEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public CustomEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context ctx, AttributeSet attrs) {
        TypedArray attributes = ctx.obtainStyledAttributes(attrs, R.styleable.BREdit);
        String customFont = attributes.getString(R.styleable.BREdit_customEFont);
        FontManager.setCustomFont(ctx, this, Utils.isNullOrEmpty(customFont) ? "CircularPro-Medium.otf" : customFont);
        attributes.recycle();
    }

}
