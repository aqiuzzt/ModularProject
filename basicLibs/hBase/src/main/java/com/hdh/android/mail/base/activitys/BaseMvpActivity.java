package com.hdh.android.mail.base.activitys;


import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import com.hdh.android.mail.base.BaseApplication;
import com.hdh.android.mail.base.R;
import com.hdh.android.mail.base.inte.IPresenter;
import com.hdh.common.util.LogUtil;
import com.hdh.common.util.view.ToastUtil;
import com.hdh.android.mail.base.view.ToolBarX;
import com.hdh.widget.kprogresshud.KProgressHUD;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * MVP activity基类
 *
 * @param <P>
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/8 18:56
 */
public abstract class BaseMvpActivity<P extends IPresenter> extends BaseActivity {
    private static final String LOG_TAG = "BaseMvpActivity";
    public P mPresenter;

    Toolbar mToolbar;
    private ToolBarX mToolBarX;
    private KProgressHUD mKprogressHUD;


    @Override
    protected void bindMVP() {
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.onAttach();
        }
    }

    @Override
    protected void bindView() {
        super.bindView();
        mToolbar = findViewById(R.id.toolbar);
        mKprogressHUD = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
    }

    @Override
    public void setTitle(CharSequence title) {
        //设置标题
        getToolBarX().setTitle(title);
    }


    /**
     * 创建V对应的presenter
     *
     * @return
     */
    public abstract P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDetach();
    }

    /**
     * 返回一个Toolbar的操作类,如果有需要请扩展此类
     *
     * @return
     */
    public ToolBarX getToolBarX() {
        if (mToolBarX == null) {
            mToolBarX = new ToolBarX(mToolbar, this);
        }
        return mToolBarX;
    }


    /**
     * 加载对话框j
     *
     * @param text
     */
    public void showLoading(String text) {

        mKprogressHUD.setLabel(text).setCancellable(false);
        if (!mKprogressHUD.isShowing())
            mKprogressHUD.show();
    }

    /**
     * 加载对话框j
     *
     * @param text
     */
    public void showLoadingCancel(String text) {

        mKprogressHUD.setLabel(text).setCancellable(true);
        if (!mKprogressHUD.isShowing())
            mKprogressHUD.show();
    }

    public void hideLoading() {
        if (mKprogressHUD != null)
            mKprogressHUD.dismiss();
    }

    /**
     * 显示正在加载
     */
    public void showProgress() {
        LogUtil.i(LOG_TAG, "showProgress");
        showLoading("正在加载...");
    }

    /**
     * 隐藏正在加载
     */
    public void hideProgress() {
        LogUtil.i(LOG_TAG, "hideProgress");
        hideLoading();
    }


    /**
     * 显示toast
     *
     * @param message 显示的消息
     */
    protected void showToast(String message) {
        if (!TextUtils.isEmpty(message)) {
            ToastUtil.show(BaseApplication.get(), message);
        }
    }


    /**
     * 是否禁止上拉下拉
     *
     * @param mSwipeRefreshLayout
     * @param enable
     */
    public void enableSmartRefresh(SmartRefreshLayout mSwipeRefreshLayout, boolean enable) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setEnableRefresh(enable);
            mSwipeRefreshLayout.setEnableLoadmore(enable);
        }
    }


    /**
     * 停止上拉下拉刷新
     *
     * @param mSwipeRefreshLayout
     * @param success             是否成功刷新
     */
    public void stopLoading(SmartRefreshLayout mSwipeRefreshLayout, boolean success) {
        if (mSwipeRefreshLayout != null) {
            if (mSwipeRefreshLayout.isRefreshing()) {
                LogUtil.i(LOG_TAG, "stopLoading isRefreshing");
                if (success) {
                    mSwipeRefreshLayout.finishRefresh(1000);
                } else {
                    mSwipeRefreshLayout.finishRefresh(false);
                }
                mSwipeRefreshLayout.setLoadmoreFinished(false);
                mSwipeRefreshLayout.setEnableLoadmore(true);

            }
            if (mSwipeRefreshLayout.isLoading()) {
                LogUtil.i(LOG_TAG, "stopLoading isLoading");
                if (success) {
                    mSwipeRefreshLayout.finishLoadmore(1000);
                } else {
                    mSwipeRefreshLayout.finishLoadmore(false);
                }
                mSwipeRefreshLayout.finishLoadmore(1000);
                mSwipeRefreshLayout.setLoadmoreFinished(false);
                mSwipeRefreshLayout.setEnableLoadmore(true);
            }
        }
    }

}
