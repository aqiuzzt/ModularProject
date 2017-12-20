package com.hdh.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.inputmethod.EditorInfo;


import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * Desc:
 * User: tiansj
 */
public class PasswordInputView extends AppCompatEditText {

    private static final int defaultContMargin = 3;
    private static final int defaultSplitLineWidth = 3;

    private int borderColor = 0xFFC4C4C4;
    private float borderWidth = 3;
    private float borderRadius = 0;

    private int passwordLength = 6;
    private int passwordColor = 0xFFC4C4C4;
    private float passwordWidth = 3;
    private float passwordRadius = 3;
    private float itemHeight = 32;

    private Paint passwordPaint = new Paint(ANTI_ALIAS_FLAG);
    private Paint borderPaint = new Paint(ANTI_ALIAS_FLAG);
    private int textLength;


    private InputCompleteListener mCompleteListener;
    private RectF rect;
    private RectF rectIn;

    public PasswordInputView(Context context, AttributeSet attrs) {
        super(context, attrs);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        passwordWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, passwordWidth, dm);
        itemHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, itemHeight, dm);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PasswordInputView, 0, 0);
        if (a.getDimension(R.styleable.PasswordInputView_pivItemHeight, itemHeight) > itemHeight) {
            itemHeight = a.getDimension(R.styleable.PasswordInputView_pivItemHeight, itemHeight);
        }

        a.recycle();

        borderPaint.setStrokeWidth(borderWidth);
        borderPaint.setColor(borderColor);
        passwordPaint.setStrokeWidth(passwordWidth);
        passwordPaint.setStyle(Paint.Style.FILL);
        passwordPaint.setColor(passwordColor);
        setBackgroundDrawable(null);
        InputFilter[] filters = {new InputFilter.LengthFilter(passwordLength)};
        setFilters(filters);
        setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        setCursorVisible(false);
    }

    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rect = new RectF(0, getPaddingTop(), w, h - getPaddingBottom());
        rectIn = new RectF(rect.left + defaultContMargin, rect.top + defaultContMargin,
                rect.right - defaultContMargin, rect.bottom - defaultContMargin);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        Log.d("jsy", "width :" + width + ",height:" + height);
        // 外边框
        borderPaint.setColor(borderColor);
        canvas.drawRoundRect(rect, borderRadius, borderRadius, borderPaint);

        // 内容区
        borderPaint.setColor(Color.WHITE);
        canvas.drawRoundRect(rectIn, borderRadius, borderRadius, borderPaint);

        // 分割线
        borderPaint.setColor(borderColor);
        borderPaint.setStrokeWidth(defaultSplitLineWidth);
        for (int i = 1; i < passwordLength; i++) {
            float x = width * i / passwordLength;
            canvas.drawLine(x, 0 + getPaddingTop(), x, height - getPaddingBottom() - borderWidth, borderPaint);
        }

        // 密码
        float cx, cy = height / 2;
        float half = width / passwordLength / 2;
        for (int i = 0; i < textLength; i++) {
            cx = width * i / passwordLength + half;
            canvas.drawCircle(cx, cy, passwordWidth, passwordPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int width = widthSpecSize;
        int height = heightSpecSize;


        switch (widthSpecMode) {
            case MeasureSpec.AT_MOST:
                width = (int) itemHeight * passwordLength + getPaddingRight() + getPaddingLeft();
                break;
            case MeasureSpec.EXACTLY:
            case MeasureSpec.UNSPECIFIED:
                width = widthSpecSize;
                break;
        }

        switch (heightSpecMode) {
            case MeasureSpec.AT_MOST:
                height = (int) itemHeight + getPaddingBottom() + getPaddingBottom();
                break;
            case MeasureSpec.EXACTLY:
            case MeasureSpec.UNSPECIFIED:
                height = heightSpecSize;
                break;
        }
        Log.d("jsy", "width :" + width + ",height:" + height + ",widthSpecSize:" + widthSpecSize + ",heightSpecSize:" + heightSpecSize + ",Itemheight=" + itemHeight);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        this.textLength = text.toString().length();
        invalidate();
        if (textLength == 6) {
            if (mCompleteListener != null) {
                mCompleteListener.onComplete(text.toString());
            }
        }
    }


    public void clear() {
        setText("");
        this.textLength = 0;
        invalidate();
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        borderPaint.setColor(borderColor);
        invalidate();
    }

    public float getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
        borderPaint.setStrokeWidth(borderWidth);
        invalidate();
    }

    public float getBorderRadius() {
        return borderRadius;
    }

    public void setBorderRadius(float borderRadius) {
        this.borderRadius = borderRadius;
        invalidate();
    }

    public int getPasswordLength() {
        return passwordLength;
    }

    public void setPasswordLength(int passwordLength) {
        this.passwordLength = passwordLength;
        invalidate();
    }

    public int getPasswordColor() {
        return passwordColor;
    }

    public void setPasswordColor(int passwordColor) {
        this.passwordColor = passwordColor;
        passwordPaint.setColor(passwordColor);
        invalidate();
    }

    public float getPasswordWidth() {
        return passwordWidth;
    }

    public void setPasswordWidth(float passwordWidth) {
        this.passwordWidth = passwordWidth;
        passwordPaint.setStrokeWidth(passwordWidth);
        invalidate();
    }

    public float getPasswordRadius() {
        return passwordRadius;
    }

    public void setPasswordRadius(float passwordRadius) {
        this.passwordRadius = passwordRadius;
        invalidate();
    }

    public InputCompleteListener getCompleteListener() {
        return mCompleteListener;
    }

    public void setCompleteListener(InputCompleteListener mCompleteListener) {
        this.mCompleteListener = mCompleteListener;
    }

    public interface InputCompleteListener {
        void onComplete(String str);
    }
}
