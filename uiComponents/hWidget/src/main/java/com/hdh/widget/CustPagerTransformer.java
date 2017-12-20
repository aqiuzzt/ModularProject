package com.hdh.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.hdh.common.util.DisplayUtil;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gzhjhc.com">Contact me.</a>
 * @since 2017/11/11 22:59
 */

public class CustPagerTransformer implements ViewPager.PageTransformer {

    private int maxTranslateOffsetX;
    private ViewPager viewPager;
    private int maxTranslateOffsetValues = 80;
    private float offsetRateValues = 0.28f;

    public int getMaxTranslateOffsetValues() {
        return maxTranslateOffsetValues;
    }

    public void setMaxTranslateOffsetValues(int maxTranslateOffsetValues) {
        this.maxTranslateOffsetValues = maxTranslateOffsetValues;
    }

    public float getOffsetRateValues() {
        return offsetRateValues;
    }

    public void setOffsetRateValues(float offsetRateValues) {
        this.offsetRateValues = offsetRateValues;
    }

    public CustPagerTransformer(Context context) {
        this.maxTranslateOffsetX = DisplayUtil.dip2px(context, maxTranslateOffsetValues);
    }

    @Override
    public void transformPage(View view, float position) {
        if (viewPager == null) {
            viewPager = (ViewPager) view.getParent();
        }
        int leftInScreen = view.getLeft() - viewPager.getScrollX();
        int centerXInViewPager = leftInScreen + view.getMeasuredWidth() / 2;
        int offsetX = centerXInViewPager - viewPager.getMeasuredWidth() / 2;
        float offsetRate = (float) offsetX * offsetRateValues / viewPager.getMeasuredWidth();
        float scaleFactor = 1 - Math.abs(offsetRate);
        if (scaleFactor > 0) {
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            view.setTranslationX(-maxTranslateOffsetX * offsetRate);
        }
    }

}
