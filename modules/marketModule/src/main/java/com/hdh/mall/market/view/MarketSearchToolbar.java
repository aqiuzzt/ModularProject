package com.hdh.mall.market.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdh.mall.market.R;
import com.hdh.mall.market.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by albert on 2017/10/8.
 */

public class MarketSearchToolbar extends Toolbar {
    private static final String TAG = "SearchToolbar";

    @BindView(R2.id.iv_search)
    ImageView mIvSearch;
    @BindView(R2.id.et_search)
    View mEtSearch;
    @BindView(R2.id.iv_search_end)
    ImageView mIvSearchEnd;
    @BindView(R2.id.ll_search_container)
    View mContentView;
    @BindView(R2.id.iv_search_start_back)
    ImageView mIvSearchStartBack;
    private int srcEnd;
    private boolean enable;


    public MarketSearchToolbar(Context context) {
        this(context, null);
    }

    public MarketSearchToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarketSearchToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = null;
        try {
            a = context.obtainStyledAttributes(attrs, R.styleable.SearchToolbar, defStyleAttr, 0);
            srcEnd = a.getResourceId(R.styleable.SearchToolbar_srcEnd, 0);
            enable = a.getBoolean(R.styleable.SearchToolbar_editEnable, false);
        } finally {
            if (a != null) {
                a.recycle();
            }
        }
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.setFactory2(new LayoutInflater.Factory2() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                if (!enable && "EditText".equals(name)) {
                    return new TextView(context, attrs);
                }
                return null;
            }

            @Override
            public View onCreateView(String name, Context context, AttributeSet attrs) {
                return null;
            }
        });
        inflater.inflate(R.layout.market_layout_market_search_view, this);
        ButterKnife.bind(this);
        bindView();
    }

    @OnClick({R2.id.iv_search_end, R2.id.ll_search_container, R2.id.iv_search_start_back})
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.iv_search_end) {
            if (mOnEndClickListener != null) {
                mOnEndClickListener.onClick(view);
            }

        } else if (i == R.id.ll_search_container) {
            if (mOnSearchViewClickListener != null) {
                mOnSearchViewClickListener.onClick(view);
            }

        } else if (i == R.id.iv_search_start_back) {
            if (mOnStartClickListener != null) {
                mOnStartClickListener.onClick(view);
            }

        }
    }

    private void bindView() {
        mEtSearch.setEnabled(enable);
        if (srcEnd == 0) {
            mIvSearchEnd.setVisibility(GONE);
        } else {
            mIvSearchEnd.setVisibility(VISIBLE);
            mIvSearchEnd.setImageResource(srcEnd);
        }
    }

    public EditText getEditText() {
        return (EditText) mEtSearch;
    }

    public void setEditable(boolean editable) {
        mEtSearch.setEnabled(editable);
    }

    public void setStartSrc(@DrawableRes int res) {
        mIvSearchStartBack.setVisibility(VISIBLE);
        mIvSearchStartBack.setImageResource(res);
    }

    public void setEndSrc(@DrawableRes int res) {
        mIvSearchEnd.setVisibility(VISIBLE);
        mIvSearchEnd.setImageResource(res);
    }

    public void setSearchIcon(@DrawableRes int res) {
        mIvSearch.setBackgroundResource(res);
    }

    public void setTextViewTextColor(@ColorInt int color) {
        ((TextView) mEtSearch).setTextColor(color);
        ((TextView) mEtSearch).setHintTextColor(color);
    }

    public void setEditTextColor(@ColorInt int color) {
        ((EditText) mEtSearch).setTextColor(color);
        ((EditText) mEtSearch).setHintTextColor(color);
    }

    public void setSearchBarBackgroundColor(@DrawableRes int color) {
        mContentView.setBackgroundResource(color);
    }


    private OnEndClickListener mOnEndClickListener;
    private OnSearchViewClickListener mOnSearchViewClickListener;


    public void setOnStartClickListener(OnStartClickListener onStartClickListener) {
        mOnStartClickListener = onStartClickListener;
    }


    public void setOnEndClickListener(OnEndClickListener onEndClickListener) {
        mOnEndClickListener = onEndClickListener;
    }

    public void setOnSearchViewClickListener(OnSearchViewClickListener onSearchViewClickListener) {
        this.mOnSearchViewClickListener = onSearchViewClickListener;
    }

    private OnStartClickListener mOnStartClickListener;

    public interface OnStartClickListener {
        void onClick(View view);
    }

    public interface OnEndClickListener {
        void onClick(View view);
    }

    public interface OnSearchViewClickListener {
        void onClick(View view);
    }
}
