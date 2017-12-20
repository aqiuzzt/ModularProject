package com.hdh.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


/**
 * 26个字母列表
 */
public class MaterialSlideBar extends View {
    private static final long DURATION = 250;
    private final float singleTextWidth;
    private final float singleTextHeight;
    private ColorStateList textColor;
    private int textSize = 16;
    private int selectedColor = Color.RED;
    private int pressedColor = R.color.color_bg_yellow;
    private int normalColor = Color.BLACK;
    private boolean textSelectedBold;
    private int textGap = 0;
    private Paint mPaint;
    private String[] array;
    private int width;
    private int height;
    private int index = -1;
    private TextView mTipsView;

    public MaterialSlideBar(Context context) {
        this(context, null);
    }

    public MaterialSlideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialSlideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = null;
        try {
            a = context.obtainStyledAttributes(attrs, R.styleable.MaterialSlideBar, defStyleAttr, 0);
            textSize = a.getDimensionPixelSize(R.styleable.MaterialSlideBar_android_textSize, textSize);
            textColor = a.getColorStateList(R.styleable.MaterialSlideBar_android_textColor);
            textGap = a.getDimensionPixelSize(R.styleable.MaterialSlideBar_textGap, textGap);
            textSelectedBold = a.getBoolean(R.styleable.MaterialSlideBar_textSelectedBold, false);
        } finally {
            if (a != null) {
                a.recycle();
            }
        }
        if (textColor != null) {
            normalColor = textColor.getDefaultColor();
            pressedColor = textColor.getColorForState(new int[]{android.R.attr.state_pressed}, pressedColor);
            selectedColor = textColor.getColorForState(new int[]{android.R.attr.state_selected}, selectedColor);
        }
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(normalColor);
        mPaint.setTextSize(textSize);
        mPaint.setTypeface(Typeface.DEFAULT);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setFakeBoldText(true);
        array = new String[]{"A", "B", "C", "D", "E", "F",
                "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        Rect rect = new Rect();
        mPaint.getTextBounds("W", 0, 1, rect);
        singleTextWidth = mPaint.measureText("W");
        singleTextHeight = Math.abs(mPaint.getFontMetrics().ascent) + mPaint.getFontMetrics().descent + Math.abs(mPaint.getFontMetrics().leading);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        //设置一个默认值，就是这个View的默认宽度为500，这个看我们自定义View的要求
        int result = 0;
        if (specMode == MeasureSpec.AT_MOST) {//相当于我们设置为wrap_content
            result = (int) (getPaddingLeft() + singleTextWidth + getPaddingRight());
        } else if (specMode == MeasureSpec.EXACTLY) {//相当于我们设置为match_parent或者为一个具体的值
            result = specSize;
        } else {
            result = specSize;
        }
        return result;
    }

    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int result = 0;
        if (specMode == MeasureSpec.AT_MOST) {
            result = (int) (getPaddingTop() + getPaddingBottom() + (singleTextHeight + textGap) * array.length);
        } else if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = specSize;
        }
        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int num = array.length;
        int singleHeight = height / num;
        for (int i = 0; i < num; i++) {
            if (i == index) {
                mPaint.setColor(selectedColor);
                mPaint.setFakeBoldText(textSelectedBold);
            } else {
                mPaint.setColor(normalColor);
                mPaint.setFakeBoldText(false);
            }

            float x = getPaddingLeft() + width / 2;
//            float x = getPaddingLeft() + (width - mPaint.measureText(array[i])) / 2;
//            float y = getPaddingTop() +
//                    ((singleHeight + textGap) * i + (singleHeight / 2 + (Math.abs(mPaint.getFontMetrics().ascent) - mPaint.getFontMetrics().descent) / 2));
            float y = getPaddingTop() + singleHeight * (i + 1) - mPaint.measureText(array[i]) / 2;
            canvas.drawText(array[i], x, y, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //当前选中字母的索引
        final int index = (int) (event.getY() / getHeight() * array.length);
        //老的索引
        int oldIndex = this.index;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if (index != oldIndex && index >= 0 && index < array.length) {
                    this.index = index;
                    if (mOnSlideIndexChangeListener != null) {//监听回调
                        mOnSlideIndexChangeListener.onChanged(index, array[index]);
                    }
                    if (mTipsView != null) {
                        mTipsView.setText(array[index]);
                        mTipsView.animate().cancel();
                        mTipsView.animate().alpha(1).setDuration(DURATION).start();
                    }
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mTipsView != null) {
                    mTipsView.animate().cancel();
                    mTipsView.animate().alpha(0).setDuration(DURATION).start();
                }
                this.index = -1;
                invalidate();
                break;
            default:
                break;
        }
        return true;
    }

    private OnSlideIndexChangeListener mOnSlideIndexChangeListener;

    public void setOnSlideIndexChangeListener(OnSlideIndexChangeListener onSlideIndexChangeListener) {
        mOnSlideIndexChangeListener = onSlideIndexChangeListener;
    }

    public interface OnSlideIndexChangeListener {
        void onChanged(int index, String letter);
    }

    public void setTipsView(TextView tipsView) {
        this.mTipsView = tipsView;
    }
}
