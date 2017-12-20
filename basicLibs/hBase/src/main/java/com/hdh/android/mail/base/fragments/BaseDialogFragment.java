package com.hdh.android.mail.base.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;

import com.hdh.android.mail.base.BaseApplication;
import com.hdh.common.util.view.ToastUtil;
import com.hdh.widget.kprogresshud.KProgressHUD;


/**
 * 业务对话框基类
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/8 18:53
 */
public abstract class BaseDialogFragment extends DialogFragment {
    public Context context;
    private KProgressHUD mKprogressHUD;


    /**
     * 初始化数据
     */
    protected abstract void bindData();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        mKprogressHUD = KProgressHUD.create(context).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
        bindData();

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
        showLoading("正在加载...");
    }

    /**
     * 隐藏正在加载
     */
    public void hideProgress() {
        hideLoading();
    }


    public void showToast(String message) {
        if (!TextUtils.isEmpty(message)) {
            ToastUtil.show(BaseApplication.get(), message);
        }
    }

}
