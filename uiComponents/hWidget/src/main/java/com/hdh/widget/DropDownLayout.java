package com.hdh.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

/**
 * Created by Martin on 2017/3/17.
 */

public class DropDownLayout extends LinearLayout {
    private int maskColor = 0x8c000000;
    private int maxHeight;
    private View mContentView;
    private View maskView;
    private Context context;
    private boolean maskAdded;
    private static final long ANIM_TIME = 250L;
    private AnimationSet animation;
    private boolean first = true;
    private boolean shown;

    public DropDownLayout(@NonNull Context context) {
        this(context, null);
    }

    public DropDownLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropDownLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DropDownLayout, defStyleAttr, 0);
        maskColor = a.getColor(R.styleable.DropDownLayout_maskColor, maskColor);
        maxHeight = a.getDimensionPixelOffset(R.styleable.DropDownLayout_maxHeight, 500);
        a.recycle();
        this.context = context;
        setOrientation(VERTICAL);
        setVisibility(GONE);
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//
//        if (heightMode == MeasureSpec.EXACTLY) {
//            heightSize = heightSize <= maxHeight ? heightSize
//                    : maxHeight;
//        }
//        if (heightMode == MeasureSpec.UNSPECIFIED) {
//            heightSize = heightSize <= maxHeight ? heightSize
//                    : maxHeight;
//        }
//        if (heightMode == MeasureSpec.AT_MOST) {
//            heightSize = heightSize <= maxHeight ? heightSize
//                    : maxHeight;
//        }
//        int maxHeightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize,
//                heightMode);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setContentView(View contentView) {
        removeAllViews();
        this.mContentView = contentView;
        addView(contentView, 0);
        if (maskView == null) {
            maskView = new View(context);
            maskView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            maskView.setBackgroundColor(maskColor);
            maskView.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View v) {
                    hide();
                }
            });
            animation = (AnimationSet) AnimationUtils.loadAnimation(context, R.anim.anim_slide_dropdown_top);
        }
        if (!maskAdded) {
            addView(maskView, 1);
            maskAdded = true;
        }

    }

    public void show() {
        if (mContentView == null)
            throw new IllegalArgumentException("must set content view first!");
        ViewCompat.setAlpha(maskView, 0);
        setVisibility(VISIBLE);
        maskView.animate().cancel();
        if (!first) {
            mContentView.clearAnimation();
            ViewCompat.setTranslationY(mContentView, -mContentView.getMeasuredHeight());
            mContentView.animate().cancel();
            mContentView.animate().translationY(0).setDuration(ANIM_TIME).start();
        } else {
            mContentView.setAnimation(animation);
            first = false;
        }
        maskView.animate().alpha(1.0f).setDuration(ANIM_TIME).setListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

            }
        }).start();
        shown = true;
    }

    public void toggle() {
        if (shown) {
            hide();
        } else {
            show();
        }
    }

    @Override public boolean isShown() {
        return shown;
    }

    public void hide() {
        if (mContentView == null)
            throw new IllegalArgumentException("must set content view first!");
        mContentView.animate().cancel();
        maskView.animate().cancel();
        mContentView.animate().translationY(-mContentView.getMeasuredHeight()).setDuration(ANIM_TIME).start();
        maskView.animate().alpha(0).setListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setVisibility(GONE);
            }
        }).setDuration(ANIM_TIME).start();
        shown = false;
        if (mOnHideListener != null) {
            mOnHideListener.onHide();
        }
    }

    public boolean isShowing() {
        return shown;
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    public void handleOnBackPressed() {
        hide();
    }

    public interface onHideListener {
        void onHide();
    }

    private onHideListener mOnHideListener;

    public void setOnHideListener(onHideListener onHideListener) {
        mOnHideListener = onHideListener;
    }
}
