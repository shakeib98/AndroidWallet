package com.example.androidwallet.presenter.customViews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.androidwallet.R;
import com.example.androidwallet.manager.FontManager;
import com.example.androidwallet.utils.Utils;

@SuppressLint("AppCompatCustomView")
public class BaseTextView extends TextView {
    private static final String TAG = BaseTextView.class.getName();

    public BaseTextView(Context context) {
        super(context);
    }

    public BaseTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BaseTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public BaseTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context ctx, AttributeSet attrs) {
        TypedArray attributes = ctx.obtainStyledAttributes(attrs, R.styleable.BaseTextView);
        String customFont = attributes.getString(R.styleable.BaseTextView_customTFont);
        FontManager.setCustomFont(ctx, this, Utils.isNullOrEmpty(customFont) ? "CircularPro-Book.otf" : customFont);
        attributes.recycle();
        setLineSpacing(0, 1.3f);

        //setTextDirection(TEXT_DIRECTION_LTR);
    }
}
