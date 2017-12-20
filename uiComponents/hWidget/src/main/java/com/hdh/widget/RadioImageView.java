package com.hdh.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;


/**
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/24 14:27
 */
public class RadioImageView extends AppCompatImageView {
    private Drawable mDrawable;
    private float mRadio;

    public RadioImageView(Context context) {
        super(context);
        init(context);
    }

    public RadioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RadioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mDrawable = getDrawable();
        initDrawable();
    }

    private void initDrawable() {
        if (mDrawable != null) {
            int mDrawableWidth = mDrawable.getIntrinsicWidth();
            int mDrawableHeight = mDrawable.getIntrinsicHeight();
            Log.d("RadioImageView", "Drawable Size:" + mDrawableWidth + "=====" + mDrawableHeight);
            mRadio = mDrawableWidth / (mDrawableHeight * 1.0f);
        }
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        this.mDrawable = drawable;
        initDrawable();
        super.setImageDrawable(drawable);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = (int) (width / mRadio);
        setMeasuredDimension(width, height);
    }
}
