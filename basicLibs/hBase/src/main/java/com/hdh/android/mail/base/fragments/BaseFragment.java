package com.hdh.android.mail.base.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hdh.android.mail.base.BaseApplication;
import com.hdh.android.mail.base.Constant;
import com.hdh.android.mail.base.event.Event;
import com.hdh.common.util.LogUtil;
import com.hdh.common.util.view.ToastUtil;
import com.hdh.widget.kprogresshud.KProgressHUD;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/8 18:53
 */
public abstract class BaseFragment extends Fragment {
    private static final String LOG_TAG = "BaseFragment";
    private Unbinder mUnBinder;
    private View mRootView;
    protected Context mContext;
    protected AppCompatActivity mActivity;
    private KProgressHUD mKprogressHUD;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        this.mActivity = (AppCompatActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(setLayoutId(), container, false);
        } else {
            if (container != null) container.removeView(mRootView);
        }
        mUnBinder = ButterKnife.bind(this, mRootView);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        handleIntent(savedInstanceState);
        bindView(view);
        bindMvp();
        bindData();
        bindEvent();
        mKprogressHUD = KProgressHUD.create(mContext).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
    }


    protected void bindView(View view) {

    }

    protected void bindMvp() {

    }

    protected abstract void bindEvent();

    protected abstract void bindData();

    protected void handleIntent(Bundle savedInstanceState) {
    }

    @LayoutRes
    protected abstract int setLayoutId();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mRootView != null) ((ViewGroup) mRootView.getParent()).removeView(mRootView);
        mUnBinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
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

    public void showLoading(String text) {
        mKprogressHUD.setLabel(text).setCancellable(false);
        if (!mKprogressHUD.isShowing()) {
            mKprogressHUD.show();
        }
    }

    public void hideLoading() {
        if (mKprogressHUD != null) {
            mKprogressHUD.dismiss();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
    }


    public void showToast(String message) {
        LogUtil.i(LOG_TAG, "message:" + message);
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
    private void enableSmartRefresh(SmartRefreshLayout mSwipeRefreshLayout, boolean enable) {
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


    /**
     * 是否登录
     * @return
     */
    public boolean isLogin() {
        String accessToken = BaseApplication.get().getUserManager().getAccessToken();
        LogUtil.i(LOG_TAG, "accessToken :" + accessToken);
        if (!TextUtils.isEmpty(accessToken)) {
            return true;
        }
        return false;
    }

    /**
     * 有token,但是认为没有完善信息,直接留在当前页面,需要手动登录
     * @return
     */
    public boolean isVerified(){
        String accessToken = BaseApplication.get().getUserManager().getAccessToken();
        LogUtil.i(LOG_TAG, "accessToken :" + accessToken);
        if (BaseApplication.get().getUserManager().getAccountProvider() != null
                && (BaseApplication.get().getUserManager().getAccountProvider().userCommonDTO == null
                || TextUtils.isEmpty(BaseApplication.get().getUserManager().getAccountProvider().userCommonDTO.identityNum)
                || "0".equals(BaseApplication.get().getUserManager().getAccountProvider().userCommonDTO.identityNum))) {
            return true;
        }
        return false;
    }


    /**
     * 是否是商家
     *
     * @return
     */
    public boolean isSeller() {
        if (BaseApplication.get().getUserManager().getAccountProvider() != null
                && !TextUtils.isEmpty(String.valueOf(BaseApplication.get().getUserManager().getAccountProvider().role))
                && (BaseApplication.get().getUserManager().getAccountProvider().role == Constant.ROLE_MERCHANT )) {
            return true;
        }
        return false;
    }

    /**
     * 是否是军人家属
     *
     * @return ROLE_CONSUMER
     */
    public boolean isConsumer() {
        if (BaseApplication.get().getUserManager().getAccountProvider() != null
                && !TextUtils.isEmpty(String.valueOf(BaseApplication.get().getUserManager().getAccountProvider().role))
                && (BaseApplication.get().getUserManager().getAccountProvider().role == Constant.ROLE_CONSUMER)) {
            return true;
        }
        return false;
    }


    /**
     * 是否是军人家属
     *
     * @return ROLE_CONSUMER
     */
    public boolean isVip() {
        if (BaseApplication.get().getUserManager().getAccountProvider() != null
                && !TextUtils.isEmpty(String.valueOf(BaseApplication.get().getUserManager().getAccountProvider().role))
                && (BaseApplication.get().getUserManager().getAccountProvider().role == Constant.ROLE_VIP)) {
            return true;
        }
        return false;
    }


}
