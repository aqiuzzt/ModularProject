package com.hdh.android.mail.base.fragments;

import android.util.Log;

import com.hdh.android.mail.base.inte.IFragmentView;
import com.hdh.android.mail.base.inte.IPresenter;
import com.hdh.common.util.LogUtil;


/**
 *  MVP activity基类
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/8 18:54
 */

public abstract class BaseMvpFragment<P extends IPresenter> extends BaseFragment implements IFragmentView<P> {
    private static final String LOG_TAG = "BaseMvpFragment";

    protected P mPresenter;

    @Override
    public void onDetach() {
        super.onDetach();
        if (mPresenter != null) mPresenter.onDetach();
    }

    @Override
    protected void bindMvp() {
        if (mPresenter != null) mPresenter.onAttach();
    }

    @Override
    public void setPresenter(P presenter) {
        LogUtil.e(LOG_TAG, "setPresenter() called with: presenter = [" + presenter.getClass().getSimpleName() + "]");
        mPresenter = presenter;
    }
}
