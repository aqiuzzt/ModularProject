package com.hdh.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;



/**
 * Created by Martin on 2017/3/18.
 */

public class LineTextView extends android.support.v7.widget.AppCompatTextView {

    public LineTextView(Context context) {
        super(context);
    }

    public LineTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LineTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //&& isSelected()
        if (getText() != null && isSelected()) {
            int oldColor = getPaint().getColor();
            float textWidth = getPaint().measureText(String.valueOf(getText()));
            getPaint().setColor(ContextCompat.getColor(getContext(), R.color.color_bg_yellow));
            int left = 0;
            if (getGravity() == Gravity.CENTER || getGravity() == Gravity.CENTER_HORIZONTAL) {
                left = (int) ((getMeasuredWidth() - textWidth) / 2);
            }
            canvas.drawRect(left, getMeasuredHeight() - 2, left + textWidth, getMeasuredHeight(), getPaint());
            getPaint().setColor(oldColor);
        }
    }
}
