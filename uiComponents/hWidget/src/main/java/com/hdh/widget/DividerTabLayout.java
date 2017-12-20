package com.hdh.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Px;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import static android.support.v7.widget.LinearLayoutCompat.SHOW_DIVIDER_NONE;


/**
 * tab布局
 */
public class DividerTabLayout extends TabLayout {

    private int mShowDividers;
    private int mDividerPadding;
    private Drawable mDivider;

    public DividerTabLayout(Context context) {
        this(context, null);
    }

    public DividerTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DividerTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DividerTabLayout, defStyleAttr, 0);
        mShowDividers = a.getInt(R.styleable.DividerTabLayout_android_showDividers, SHOW_DIVIDER_NONE);
        mDividerPadding = a.getDimensionPixelSize(R.styleable.DividerTabLayout_android_dividerPadding, 0);
        mDivider = a.getDrawable(R.styleable.DividerTabLayout_android_divider);
        a.recycle();
        setDividerDrawable(mDivider, mShowDividers, mDividerPadding);
    }

    public void setDividerDrawable(@DrawableRes int drawableRes, @LinearLayoutCompat.DividerMode int showDividers, @Px int dividerPadding) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), drawableRes);
        setDividerDrawable(drawable, showDividers, dividerPadding);
    }

    public void setDividerDrawable(Drawable drawable, @LinearLayoutCompat.DividerMode int showDividers, @Px int dividerPadding) {
        LinearLayout mTabStrip = (LinearLayout) getChildAt(0);
        if (mTabStrip != null) {
            mTabStrip.setDividerDrawable(drawable);
            mTabStrip.setShowDividers(showDividers);
            if (dividerPadding > 0) {
                mTabStrip.setDividerPadding(dividerPadding);
            }
        }
    }
}
