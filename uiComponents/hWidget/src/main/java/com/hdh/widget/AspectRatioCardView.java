package com.hdh.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.hdh.common.util.LogUtil;


/**
 * 自定义首页卡片
 *
 * @author albert  <a href="mailto:zhengting.zhou@gzhjhc.com">Contact me.</a>
 * @since 2017/11/11 22:57
 */

public class AspectRatioCardView extends CardView {

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    private float ratio = 0;
    private int minHeight;

    public AspectRatioCardView(Context context) {
        this(context, null);
    }

    public AspectRatioCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AspectRatioCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioCardView, defStyleAttr, 0);
        minHeight = a.getDimensionPixelOffset(R.styleable.AspectRatioCardView_minHeight, 0);
        ratio = a.getFloat(R.styleable.AspectRatioCardView_ratio, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (ratio > 0) {
            int ratioHeight = (int) (getMeasuredWidth() * ratio);
            setMeasuredDimension(getMeasuredWidth(), ratioHeight);
            ViewGroup.LayoutParams lp = getLayoutParams();
            lp.height = ratioHeight;
            LogUtil.i("AspectRatioCardView", "minHeight:" + minHeight + " lp.height:" + lp.height);
            setLayoutParams(lp);
        }
    }
}