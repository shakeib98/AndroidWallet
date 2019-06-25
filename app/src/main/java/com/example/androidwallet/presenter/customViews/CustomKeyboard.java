package com.example.androidwallet.presenter.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;

import com.example.androidwallet.R;
import com.example.androidwallet.utils.Utils;

import java.util.ArrayList;

public class CustomKeyboard extends LinearLayout implements View.OnClickListener{
    public static final String TAG = CustomKeyboard.class.getName();
    private OnInsertListener mKeyInsertListener;
    private ImageButton mDeleteButton;
    private ArrayList<Button> mPinButtons;
    private static final int LAST_NUMBER_INDEX = 9;
    private static final int DECIMAL_INDEX = 10;

    public CustomKeyboard(Context context) {
        super(context);
        init(null);
    }

    public CustomKeyboard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public CustomKeyboard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        View root = inflate(getContext(), R.layout.pin_pad, this);

        boolean showAlphabet = false;
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.BRKeyboard);
        final int attributeCount = attributes.getIndexCount();
        for (int i = 0; i < attributeCount; ++i) {
            int attr = attributes.getIndex(i);
            switch (attr) {
                case R.styleable.BRKeyboard_showAlphabet:
                    showAlphabet = attributes.getBoolean(attr, false);
                    break;
            }
        }
        attributes.recycle();

        this.setWillNotDraw(false);

        mDeleteButton = root.findViewById(R.id.delete);

        mPinButtons = new ArrayList<>();
        mPinButtons.add((Button) root.findViewById(R.id.num0));
        mPinButtons.add((Button) root.findViewById(R.id.num1));
        mPinButtons.add((Button) root.findViewById(R.id.num2));
        mPinButtons.add((Button) root.findViewById(R.id.num3));
        mPinButtons.add((Button) root.findViewById(R.id.num4));
        mPinButtons.add((Button) root.findViewById(R.id.num5));
        mPinButtons.add((Button) root.findViewById(R.id.num6));
        mPinButtons.add((Button) root.findViewById(R.id.num7));
        mPinButtons.add((Button) root.findViewById(R.id.num8));
        mPinButtons.add((Button) root.findViewById(R.id.num9));
        mPinButtons.add((Button) root.findViewById(R.id.decimal));

        int bottomPaddingDimen = getContext().getResources().getInteger(R.integer.pin_keyboard_bottom_padding);
        int bottomPaddingPixels = Utils.getPixelsFromDps(getContext(), bottomPaddingDimen);

        for (int i = 0; i < mPinButtons.size(); i++) {
            Button button = mPinButtons.get(i);
            button.setOnClickListener(this);

            if (i <= LAST_NUMBER_INDEX) {
                button.setText(getText(i, showAlphabet));
            }

            if (showAlphabet) {
                button.setPadding(0, 0, 0, bottomPaddingPixels);
            }
        }

        mDeleteButton.setOnClickListener(this);
        if (showAlphabet) {
            mDeleteButton.setPadding(0, 0, 0, bottomPaddingPixels);
        }

        invalidate();
    }

    private CharSequence getText(int index, boolean showAlphabet) {
        SpannableString span1 = new SpannableString(String.valueOf(index));
        if (showAlphabet) {

            SpannableString span2;
            switch (index) {
                case 2:
                    span2 = new SpannableString("ABC");
                    break;
                case 3:
                    span2 = new SpannableString("DEF");
                    break;
                case 4:
                    span2 = new SpannableString("GHI");
                    break;
                case 5:
                    span2 = new SpannableString("JKL");
                    break;
                case 6:
                    span2 = new SpannableString("MNO");
                    break;
                case 7:
                    span2 = new SpannableString("PQRS");
                    break;
                case 8:
                    span2 = new SpannableString("TUV");
                    break;
                case 9:
                    span2 = new SpannableString("WXYZ");
                    break;
                default:
                    span2 = new SpannableString(" ");
                    break;
            }

            span1.setSpan(new RelativeSizeSpan(1f), 0, 1, 0);
            span2.setSpan(new RelativeSizeSpan(0.35f), 0, span2.length(), 0);
            return TextUtils.concat(span1, "\n", span2);
        } else {
            return span1;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        invalidate();
    }

    public void setOnInsertListener(OnInsertListener listener) {
        mKeyInsertListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mKeyInsertListener != null) {
            mKeyInsertListener.onKeyInsert(v instanceof ImageButton ? "" : ((Button) v).getText().toString());
            Log.d(TAG,((Button)v).getText().toString() );
        }

    }

    public interface OnInsertListener {
        void onKeyInsert(String key);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setBRKeyboardColor(int color) {
        setBackgroundColor(getContext().getColor(color));
    }

    public void setBRButtonBackgroundResId(int resId) {
        for (Button button : mPinButtons) {
            button.setBackgroundResource(resId);
        }
        mDeleteButton.setBackgroundResource(resId);
        invalidate();
    }

    public void setShowDecimal(boolean showDecimal) {
        mPinButtons.get(DECIMAL_INDEX).setVisibility(showDecimal ? VISIBLE : GONE);
        invalidate();
    }

    /**
     * Change the background of a specific button
     *
     * @param color the color to be used
     */
    public void setDeleteButtonBackgroundColor(int color) {
        mDeleteButton.setBackgroundColor(color);
        invalidate();
    }

    public void setDeleteImage(int resourceId) {
        mDeleteButton.setImageDrawable(getResources().getDrawable(resourceId));
        invalidate();
    }

    public void setButtonTextColor(int[] colors) {
        for (int i = 0; i < mPinButtons.size(); i++) {
            mPinButtons.get(i).setTextColor(colors[i]);
        }

        invalidate();
    }
}
