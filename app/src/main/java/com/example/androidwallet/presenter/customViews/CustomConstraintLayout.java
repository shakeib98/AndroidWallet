package com.example.androidwallet.presenter.customViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.androidwallet.R;

public class CustomConstraintLayout extends ConstraintLayout {
    public static final String TAG = CustomConstraintLayout.class.getName();
    private Paint trianglesPaintBlack;
    private Path pathBlack;
    private Paint trianglesPaint;
    private Path path;
    private float mXfract = 0f;
    private float mYfract = 0f;

    private int width;
    private int height;
    private boolean created;

    public CustomConstraintLayout(Context context) {
        super(context);
        init();
    }

    public CustomConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        trianglesPaintBlack = new Paint();
        trianglesPaint = new Paint();
        trianglesPaintBlack.setStyle(Paint.Style.FILL);
        pathBlack = new Path();
        path = new Path();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (w != 0 && !created) {
            created = true;
            width = w;
            height = h;
            createTriangles(w, h);

            trianglesPaint.setShader(new LinearGradient(0, 0, w, 0, getContext().getColor(R.color.logo_gradient_start),
                    getContext().getColor(R.color.logo_gradient_end), Shader.TileMode.MIRROR));
            trianglesPaintBlack.setShadowLayer(10.0f, 5f, 5f, getContext().getColor(R.color.gray_shadow));

            invalidate();
        }

    }

    private void createTriangles(int w, int h) {
        pathBlack.moveTo(0, 0);
        path.moveTo(0, 0);

        pathBlack.lineTo(w, 0);
        path.lineTo(w + 1, 0);

        pathBlack.lineTo(-1, h / 4);
        pathBlack.lineTo(0, h / 4 + 2);
        path.lineTo(0, h / 4 + 1);

        pathBlack.lineTo(w-1, h / 2 - h / 8);
        path.lineTo(w, h / 2 - h / 8 - 1);

        pathBlack.lineTo(0, h-1);
        path.lineTo(1, h);
    }

    public void setYFraction(final float fraction) {
        mYfract = fraction;
        float translationY = getHeight() * fraction;
        setTranslationY(translationY);
    }

    public float getYFraction() {
        return mYfract;
    }

    public void setXFraction(final float fraction) {
        mXfract = fraction;
        float translationX = getWidth() * fraction;
        setTranslationX(translationX);
    }

    public float getXFraction() {
        return mXfract;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        long start = System.currentTimeMillis();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        // Correct any translations set before the measure was set
        setTranslationX(mXfract * width);
        setTranslationY(mYfract * height);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(pathBlack, trianglesPaintBlack);
        canvas.drawPath(path, trianglesPaint);

    }
}
