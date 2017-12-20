package com.hdh.android.mail.base.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.hdh.android.mail.base.BaseApplication;
import com.hdh.android.mail.base.Constant;
import com.hdh.android.mail.base.event.Event;
import com.hdh.common.status.StatusBarUtil;
import com.hdh.common.util.LogUtil;
import com.hdh.common.util.app.AppKeyBoardMgr;
import com.hdh.widget.EmptyView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;


/**
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/8 18:53
 */
public abstract class BaseActivity extends AppCompatActivity implements EmptyView.OnReloadListener {
    private static final String LOG_TAG = "BaseActivity";
    public Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewBefore(savedInstanceState);
        BaseApplication.getActivityManager().pushOneActivity(this);
        setContentView(setLayoutId());
        context = this;
        bindView();
        handleIntent(getIntent(), savedInstanceState);
        bindFragment();
        bindMVP();
        bindData();
        bindEvent();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }


    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }


    @CallSuper
    protected void bindView() {
        ButterKnife.bind(this);
        buildStatusBar();
    }

    /**
     * 绑定presenter
     */
    protected void bindMVP() {
    }

    protected void bindFragment() {
    }

    /**
     * 绑定事件
     */
    protected abstract void bindEvent();

    /**
     * 初始化数据
     */
    protected abstract void bindData();

    /**
     * 初始化状态栏
     */
    protected void buildStatusBar() {
        boolean darkMode = StatusBarUtil.lightMode1(this);
    }

    /**
     * 处理传入的intent
     *
     * @param intent
     * @param savedInstanceState
     */
    protected void handleIntent(Intent intent, Bundle savedInstanceState) {
    }

    protected void setContentViewBefore(Bundle savedInstanceState) {
    }

    /**
     * 对应layout
     *
     * @return
     */
    @LayoutRes
    public abstract int setLayoutId();


    @Override
    public void onReload() {
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        BaseApplication.getActivityManager().popOneActivity(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消消息注册
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

        BaseApplication.getActivityManager().popOneActivity(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();

            if (isShouldHideInput(v, ev)) {
                AppKeyBoardMgr.hideSoftInput(this, v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && ((v instanceof EditText))) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
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
                && (BaseApplication.get().getUserManager().getAccountProvider().role == Constant.ROLE_MERCHANT_1 ||
                BaseApplication.get().getUserManager().getAccountProvider().role == Constant.ROLE_MERCHANT_2 ||
                BaseApplication.get().getUserManager().getAccountProvider().role == Constant.ROLE_MERCHANT_3)) {
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
     * 是否登录
     *
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
     *
     * @return ture 则没有实名认证
     *
     */
    public boolean isVerified() {
        if (BaseApplication.get().getUserManager().getAccountProvider() != null
                && (BaseApplication.get().getUserManager().getAccountProvider().userCommonDTO == null
                || TextUtils.isEmpty(BaseApplication.get().getUserManager().getAccountProvider().userCommonDTO.identityNum)
                || "0".equals(BaseApplication.get().getUserManager().getAccountProvider().userCommonDTO.identityNum))) {

            LogUtil.i(LOG_TAG, "identityNum :" + BaseApplication.get().getUserManager().getAccountProvider().userCommonDTO.identityNum);
            return true;
        }
        return false;
    }

}
