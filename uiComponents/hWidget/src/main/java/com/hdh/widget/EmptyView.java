package com.hdh.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Desc: 展位视图 , 定义了 网络连接失败/正在加载/没有数据/数据加载异常
 */
public class EmptyView extends FrameLayout {
    public static final int STATE_NORMAL = 0x00;
    public static final int STATE_LOADING = 0x01;
    public static final int STATE_NODATA = 0x02;
    public static final int STATE_ERROR = 0x03;
    public static final int STATE_NONET = 0x04;
    @ViewState
    private int mViewState = -1;
    private ViewStub stubLoading, stubNoNet, stubError, stubEmpty;
    private View viewLoading, viewNoNet, viewError, viewEmpty;
    private OnReloadListener mOnReloadListener;

    private int noDataIcon = -1;
    private int noNetIcon = -1;
    private int errorTip = -1;
    private int emptyTip = -1;
    private int loadingTip = -1;
    private int noNetTip = -1;
    private int noPermissionTip = -1;
    private OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOnReloadListener != null) {
                show(STATE_LOADING);
                mOnReloadListener.onReload();
            }
        }
    };

    public EmptyView(Context context) {
        super(context);
        init();
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();

    }

    public void setOnReloadListener(OnReloadListener listener) {
        this.mOnReloadListener = listener;
    }

    public void setLocation(boolean b) {
        ViewGroup.LayoutParams lp = getLayoutParams();
        if (lp instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) lp;
            if (b) {
                rl.addRule(RelativeLayout.BELOW, R.id.toolbar);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    rl.removeRule(RelativeLayout.BELOW);
                } else {
                    rl.addRule(RelativeLayout.BELOW, 0);
                }
            }
        }
    }

    private void init() {
        inflate(getContext(), R.layout.layout_placeholder_view, this);
        stubLoading = (ViewStub) findViewById(R.id.vs_ph_loading);
        stubEmpty = (ViewStub) findViewById(R.id.vs_ph_empty);
        stubError = (ViewStub) findViewById(R.id.vs_ph_error);
        stubNoNet = (ViewStub) findViewById(R.id.vs_ph_no_net);
        show(STATE_NORMAL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @ViewState
    public int getViewState() {
        return mViewState;
    }

    private void setViewState(@ViewState int state) {
        if (mViewState == state) {
            return;
        }
        this.mViewState = state;
        hideAllLayout();
        switch (state) {
            case STATE_LOADING:
                if (viewLoading == null) {
                    stubLoading.inflate();
                    viewLoading = findViewById(R.id.inflated_id_ph_loading);
                } else {
                    viewLoading.setVisibility(View.VISIBLE);
                }
                setListener(false);
                if (loadingTip != -1)
                    ((TextView) viewLoading.findViewById(R.id.ph_tv_loading)).setText(loadingTip);
                break;
            case STATE_NODATA:
                if (viewEmpty == null) {
                    stubEmpty.inflate();
                    viewEmpty = findViewById(R.id.inflated_id_ph_empty);
                } else {
                    viewEmpty.setVisibility(VISIBLE);
                }
                if (emptyTip != -1)
                    ((TextView) viewEmpty.findViewById(R.id.ph_tv_no_data)).setText(emptyTip);
                if (noDataIcon != -1) // TODO: 2016/8/17
                    setListener(true);
                break;
            case STATE_ERROR:
                if (viewError == null) {
                    stubError.inflate();
                    viewError = findViewById(R.id.inflated_id_ph_error);
                } else {
                    viewError.setVisibility(View.VISIBLE);
                }
                setListener(true);
                if (errorTip != -1)
                    ((TextView) viewError.findViewById(R.id.ph_tv_error)).setText(errorTip);
                break;
            case STATE_NONET:
                if (viewNoNet == null) {
                    stubNoNet.inflate();
                    viewNoNet = findViewById(R.id.inflated_id_ph_no_net);
                } else {
                    viewNoNet.setVisibility(View.VISIBLE);
                }
                if (noNetIcon != -1) // TODO: 2016/8/17
                    if (noNetTip != -1)
                        ((TextView) viewNoNet.findViewById(R.id.ph_tv_no_network)).setText(noNetTip);
                setListener(true);
                break;
            case STATE_NORMAL: // NORMAL
                setVisibility(GONE);
                setListener(false);
                break;
        }
    }

    public void show(@ViewState int state) {
        setViewState(state);
        if (mViewState != STATE_NORMAL)
            setVisibility(View.VISIBLE);
    }

    public void dismiss() {
        setViewState(STATE_NORMAL);
    }

    private void setListener(boolean b) {
        setOnClickListener(b ? mOnClickListener : null);
    }

    private void hideAllLayout() {
        if (viewLoading != null) viewLoading.setVisibility(View.GONE);
        if (viewEmpty != null) viewEmpty.setVisibility(View.GONE);
        if (viewError != null) viewError.setVisibility(View.GONE);
        if (viewNoNet != null) viewNoNet.setVisibility(View.GONE);
        setVisibility(GONE);
    }

    public void setEmptyTip(@StringRes int emptyTip) {
        this.emptyTip = emptyTip;
    }

    public void setErrorTip(@StringRes int errorTip) {
        this.errorTip = errorTip;
    }

    public void setLoadingTip(@StringRes int loadingTip) {
        this.loadingTip = loadingTip;
    }

    public void setNoDataIcon(@DrawableRes int noDataIcon) {
        this.noDataIcon = noDataIcon;
    }

    public void setNoNetIcon(@DrawableRes int noNetIcon) {
        this.noNetIcon = noNetIcon;
    }

    public void setNoNetTip(@StringRes int noNetTip) {
        this.noNetTip = noNetTip;
    }


    @IntDef(value = {STATE_NORMAL, STATE_LOADING, STATE_NODATA, STATE_ERROR, STATE_NONET})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewState {
    }

    public interface OnReloadListener {
        /**
         * 当PlaceholderView 的状态为可以点击重新加载时调用这个方法
         */
        void onReload();
    }
}
