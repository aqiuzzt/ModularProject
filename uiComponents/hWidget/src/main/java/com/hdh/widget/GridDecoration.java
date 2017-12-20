package com.hdh.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.hdh.common.util.LogUtil;
import com.hdh.common.util.view.UIUtil;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gzhjhc.com">Contact me.</a>
 * @since 2017/12/4 14:59
 */

public class GridDecoration extends RecyclerView.ItemDecoration {
    public static final int VERTICAL = 1;
    public static final int HORIZONAL = 0;
    private boolean mIsHeaderNeedDivider = true;//纵向才有效
    private int mHorizonalDividerOffset;
    private int mVerticalDividerOffset;
    private int mColor;
    private int mOrientation;
    private boolean mHasHeader = false;

    public GridDecoration(Context context, int orientation, boolean hasHeader, boolean isHeaderNeedDivider) {
        mOrientation = orientation;
        mColor = R.color.colorDividerLine;
        mHasHeader = hasHeader;
        mIsHeaderNeedDivider = isHeaderNeedDivider;
        mHorizonalDividerOffset = UIUtil.dp2px(context, 2);
        mVerticalDividerOffset = UIUtil.dp2px(context, 2);
    }

    public GridDecoration(Context context, int orientation, boolean hasHeader, boolean isHeaderNeedDivider, boolean isVerticelDiv, boolean isHorizonDiv) {
        mOrientation = orientation;
        mColor = R.color.colorDividerLine;
        mHasHeader = hasHeader;
        mIsHeaderNeedDivider = isHeaderNeedDivider;
        if (isHorizonDiv) {
            mHorizonalDividerOffset = UIUtil.dp2px(context, 2);
        } else {
            mHorizonalDividerOffset = UIUtil.dp2px(context, 1);
        }
        if (isVerticelDiv) {
            mVerticalDividerOffset = UIUtil.dp2px(context, 2);
        } else {
            mVerticalDividerOffset = UIUtil.dp2px(context, 1);
        }
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        final int spanCount = getSpanCount(parent);
        final int childCount = parent.getAdapter().getItemCount();
        final int adapterPosition = parent.getChildAdapterPosition(view);
        outRect.set(0, 0, mHorizonalDividerOffset, mVerticalDividerOffset);
        if (isLastColumn(adapterPosition, spanCount, childCount)) {
            outRect.right = 0;
        }
        if (isLastRow(adapterPosition, spanCount, childCount)) {
            outRect.bottom = 0;
        }
        if (mHasHeader && !mIsHeaderNeedDivider && mOrientation == VERTICAL && adapterPosition == 0) {
            outRect.bottom = 0;
        }
        LogUtil.d("jsy  outRect " + outRect.toString() + " position " + adapterPosition);
    }

    private boolean isLastColumn(int position, int spanCount, int childCount) {
        if (this.mOrientation == VERTICAL) {
            if (mHasHeader) {
                return (position) % spanCount == 0;
            } else {
                return (position + 1) % spanCount == 0;
            }
        } else {
            int lastColumnCount = childCount % spanCount;
            lastColumnCount = lastColumnCount == 0 ? spanCount : lastColumnCount;
            return position >= childCount - lastColumnCount;
        }
    }

    private boolean isLastRow(int position, int spanCount, int childCount) {
        if (this.mOrientation == VERTICAL) {
            if (mHasHeader) {
                childCount--;
                int lastColumnCount = childCount % spanCount;
                lastColumnCount = lastColumnCount == 0 ? spanCount : lastColumnCount;
                return (position - 1) >= childCount - lastColumnCount;
            } else {
                int lastColumnCount = childCount % spanCount;
                lastColumnCount = lastColumnCount == 0 ? spanCount : lastColumnCount;
                return position >= childCount - lastColumnCount;
            }
        } else {
            return (position + 1) % spanCount == 0;
        }
    }

    private int getSpanCount(RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        } else {
            throw new UnsupportedOperationException("the GridDividerItemDecoration can only be used in the RecyclerView which use a GridLayoutManager or StaggeredGridLayoutManager");
        }
    }
}