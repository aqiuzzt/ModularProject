package com.hdh.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * 自定义ItemDecoration
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/23 18:05
 */
public class DefaultDecoration extends RecyclerView.ItemDecoration {

    private boolean both;
    private int mColor;
    private int mHeight;
    private int mMarginLeft;
    private Context mContext;
    private Paint mPaint;
    private int withoutItemCount;

    public DefaultDecoration(Context context, @ColorRes int color, @DimenRes int height, @DimenRes int marginLeft) {
        this.mContext = context;
        this.mColor = color;
        this.mHeight = mContext.getResources().getDimensionPixelSize(height);
        mColor = ContextCompat.getColor(context, color);
        mMarginLeft = mContext.getResources().getDimensionPixelSize(marginLeft);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(mColor);
    }

    public DefaultDecoration(Context context, @ColorRes int color, @DimenRes int height, @DimenRes int marginLeft, int withoutItemCount) {
        this.mContext = context;
        this.mColor = color;
        this.mHeight = mContext.getResources().getDimensionPixelSize(height);
        mColor = ContextCompat.getColor(context, color);
        mMarginLeft = mContext.getResources().getDimensionPixelSize(marginLeft);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(mColor);
        this.withoutItemCount = withoutItemCount;
    }

    public DefaultDecoration(Context context, @ColorRes int color, @DimenRes int height, @DimenRes int marginLeft, boolean both) {
        this.mContext = context;
        this.mColor = color;
        this.mHeight = mContext.getResources().getDimensionPixelSize(height);
        mColor = ContextCompat.getColor(context, color);
        mMarginLeft = mContext.getResources().getDimensionPixelSize(marginLeft);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(mColor);
        this.both = both;
    }

    public DefaultDecoration(Context context) {
        this(context, R.color.colorDividerLine, R.dimen.dimenDividerHeight_7dp, R.dimen.dimenDividerHeight_0dp);
    }

    public DefaultDecoration(Context context, @DimenRes int height) {
        this(context, R.color.colorDividerLine, height, R.dimen.default_left_margin_divider);

    }

    public DefaultDecoration(Context context, @DimenRes int height, @DimenRes int marginLeft) {
        this(context, R.color.colorDividerLine, height, marginLeft);
    }

    @Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = mMarginLeft;
        outRect.bottom = mHeight;
        if (both) outRect.right = mMarginLeft;
    }

    @Override public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int left = parent.getPaddingLeft() + mMarginLeft;
        int right = parent.getWidth() - parent.getPaddingRight() - (both ? mMarginLeft : 0);
        for (int i = 0; i < parent.getChildCount() - withoutItemCount; i++) {
            View child = parent.getChildAt(i);
            int top = child.getBottom();
            int bottom = top + mHeight;
            if (i == parent.getChildCount() - 1) {
                left = parent.getPaddingLeft();
            }
            c.drawRect(left, top, right, bottom, mPaint);
        }
    }
}
