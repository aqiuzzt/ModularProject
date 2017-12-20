package com.hdh.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdh.common.util.LogUtil;



/**
 * 搜索bar
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/30 15:23
 */
public class SearchToolbar extends Toolbar {
    private static final String TAG = "SearchToolbar";
    ImageView mIvSearchStart;

    TextView mSearchCityTv;

    ImageView mIvSearchStartBack;

    LinearLayout startSearchLl;
    ImageView mIvSearch;
    View mEtSearch;
    ImageView mIvSearchEnd;
    View mContentView;
    private int srcStart;
    private int srcEnd;
    private boolean enable;
    private boolean isSelect;

    public SearchToolbar(Context context) {
        this(context, null);
    }

    public SearchToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = null;
        try {
            a = context.obtainStyledAttributes(attrs, R.styleable.SearchToolbar, defStyleAttr, 0);
            srcStart = a.getResourceId(R.styleable.SearchToolbar_srcStart, 0);
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
        View view = inflater.inflate(R.layout.layout_search_view, this);

        mIvSearchStart = view.findViewById(R.id.iv_search_start);
        mSearchCityTv = view.findViewById(R.id.iv_search_city_tv);
        mIvSearchStartBack = view.findViewById(R.id.iv_search_start_back);
        startSearchLl = view.findViewById(R.id.search_start_ll);
        mIvSearch = view.findViewById(R.id.iv_search);
        mEtSearch = view.findViewById(R.id.et_search);
        mIvSearchEnd = view.findViewById(R.id.iv_search_end);
        mContentView = view.findViewById(R.id.ll_search_container);

        bindView();
        onClick();
    }


    public void onClick() {
        mIvSearchStart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnStartClickListener != null) {
                    mOnStartClickListener.onClick();
                }
            }
        });

        mIvSearchEnd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnEndClickListener != null) {
                    mOnEndClickListener.onClick();
                }
            }
        });

        mContentView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnSearchViewClickListener != null) {
                    mOnSearchViewClickListener.onClick();
                }
            }
        });

        mIvSearchStartBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnStartClickListener != null) {
                    mOnStartClickListener.onClick();
                }
            }
        });


        startSearchLl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnStartClickListener != null) {
                    mOnStartClickListener.onClick();
                }
            }
        });
    }

    private void bindView() {
        mEtSearch.setEnabled(enable);
        if (srcStart == 0) {
            startSearchLl.setVisibility(GONE);
        } else {
            startSearchLl.setVisibility(VISIBLE);
        }
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

    public void setStartSrc() {
        startSearchLl.setVisibility(VISIBLE);
    }

    public void setStartSelected(boolean selected) {
        LogUtil.i(TAG, "setStartSelected selected:" + selected);
        mIvSearchStart.setSelected(selected);
    }

    public boolean getStartSelected() {
        return mIvSearchStart.isSelected();
//        return isSelect;
    }

    public void setEndSrc(@DrawableRes int res) {
        mIvSearchEnd.setVisibility(VISIBLE);
        mIvSearchEnd.setImageResource(res);
    }


    private OnStartClickListener mOnStartClickListener;
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

    public interface OnStartClickListener {
        void onClick();
    }

    public interface OnEndClickListener {
        void onClick();
    }

    public interface OnSearchViewClickListener {
        void onClick();
    }
}
