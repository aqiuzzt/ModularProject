package com.hdh.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Desc: 没有滑动条的gridview
 * Author:zack
 * Date:2017/1/4
 */

public class NoScrollGridView extends GridView {
    public NoScrollGridView(Context context) {
        super(context);

    }

    public NoScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
