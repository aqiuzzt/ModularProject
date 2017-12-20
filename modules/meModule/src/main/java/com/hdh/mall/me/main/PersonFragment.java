package com.hdh.mall.me.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdh.android.mail.base.BaseApplication;
import com.hdh.android.mail.base.Constant;
import com.hdh.android.mail.base.bean.AccountExtendDTO;
import com.hdh.android.mail.base.fragments.BaseMvpFragment;
import com.hdh.android.mail.base.modular.module.account.AccountIntent;
import com.hdh.android.mail.base.modular.module.me.MeIntent;
import com.hdh.android.mail.base.utils.ClearCache;
import com.hdh.android.mail.base.utils.ProfileStrorageUtil;
import com.hdh.common.status.StatusBarUtil;
import com.hdh.common.util.CheckUtil;
import com.hdh.common.util.LogUtil;
import com.hdh.mall.me.R;
import com.hdh.mall.me.R2;
import com.hdh.mall.me.main.bean.UserInfoBean;
import com.hdh.mall.me.main.contract.PersonContract;
import com.hdh.mall.me.main.presenter.PersonPresenter;
import com.hdh.widget.banner.BannerImageLoader;
import com.hdh.widget.dialog.DialogFactory;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class PersonFragment extends BaseMvpFragment<PersonContract.Presenter> implements PersonContract.View {
    public static final int REQUEST_ACCOUNT_SETTING = 100;
    @BindView(R2.id.status_bar_ll)
    LinearLayout statusBarLl;
    @BindView(R2.id.per_head_image)
    RoundedImageView mHeadImage;

    UserInfoBean mUserInfo;
    Unbinder unbinder;
    @BindView(R2.id.per_nickname_tv)
    TextView mNickNameTv;
    @BindView(R2.id.per_account_tv)
    TextView mAccountTv;
    @BindView(R2.id.per_phone_tv)
    TextView mPhoneTv;
    private AccountExtendDTO mAccountExtendDTO;
    private boolean isFristTime = true;
    private static final String LOG_TAG = "PersonFragment";


    public static PersonFragment getInstance() {
        PersonFragment hf = new PersonFragment();
        return hf;
    }


    @Override
    protected void bindEvent() {
    }

    @Override
    protected void bindData() {
        CheckUtil.checkNotNull(mPresenter);
        mAccountExtendDTO = BaseApplication.get().getUserManager().getAccountProvider();
        if (mAccountExtendDTO != null) {
        }

        isFristTime = false;


    }

    @Override
    protected void bindMvp() {
        if (mPresenter == null) mPresenter = new PersonPresenter(this);
        mPresenter.onAttach();
    }

    @Override
    public void refresh() {

    }

    @Override
    protected void bindView(View view) {
        super.bindView(view);
        int statusBar = StatusBarUtil.getStatusBarHeight(getActivity());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                statusBar);
        statusBarLl.setLayoutParams(params);
        boolean darkMode = StatusBarUtil.lightMode1(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshUserInfo();
        LogUtil.i(LOG_TAG, "onResume");
    }

    @Override
    protected int setLayoutId() {
        return R.layout.me_fragment_person;
    }


    @OnClick({
            R2.id.per_setting_iv,
            R2.id.per_logout_iv,
            R2.id.home_notices_ll,
            R2.id.home_about_ll,
            R2.id.per_head_image_ll,
    })
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.per_setting_iv) {
            AccountIntent.gotoAccountSetting(getActivity(), REQUEST_ACCOUNT_SETTING);

        } else if (i == R.id.per_logout_iv) {
            DialogFactory.getOkCancelDialog(mContext, "确定要退出登录吗？", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showLoading("正在退出...");
                }
            }, false);

        } else if (i == R.id.home_about_ll) {
            MeIntent.gotoAboutMe();

        } else if (i == R.id.home_contracts_ll) {
            MeIntent.gotoCustomerServices();

        } else if (i == R.id.home_notices_ll) {
            MeIntent.gotoMarketAnnouncement();
        }
    }

    public void refreshUserInfo() {


        BannerImageLoader.displayAvatarRole(getContext(), ProfileStrorageUtil.getAvatar(), mHeadImage, R.drawable.ic_avatar);
        if (ProfileStrorageUtil.getUserInfo() != null
                && ProfileStrorageUtil.getUserInfo().userCommonDTO != null
                && ProfileStrorageUtil.getUserInfo().accountCommonDTO != null) {
            LogUtil.i(LOG_TAG, "refreshUserInfo:" + ProfileStrorageUtil.getAvatar());
            mAccountTv.setText(ProfileStrorageUtil.getUserInfo().accountCommonDTO.account);
            mNickNameTv.setText(ProfileStrorageUtil.getUserInfo().userCommonDTO.realName);
            mPhoneTv.setText(ProfileStrorageUtil.getUserInfo().accountCommonDTO.mobile);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_ACCOUNT_SETTING:
                    refreshUserInfo();
                    break;
            }
        }
    }

    @Override
    public void requestCallback(String msg) {
        showToast(msg);
        hideLoading();
    }

    @Override
    public void requestLogoutCallBack(String msg) {
        hideLoading();
        if (!TextUtils.isEmpty(msg)) {
            showToast(msg);
        } else {

            ProfileStrorageUtil.logout();
            BaseApplication.getSecondLevelCacheKit().put(Constant.LOGIN_FROME_TYPE, "main");
            BaseApplication.get().getUserManager().clear();
            ClearCache.clearAll();
        }

    }

    @Override
    public void verifyLoginPwdCallback(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            showToast(msg);
        } else {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void logout() {
        showToast(getResources().getString(R.string.logout_tips));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.i(LOG_TAG, "onDestroy   ");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && !isFristTime) {
            mAccountExtendDTO = BaseApplication.get().getUserManager().getAccountProvider();
            if (mAccountExtendDTO != null) {
            }

        }
        LogUtil.i(LOG_TAG, "onHiddenChanged hidden：" + hidden + " isFristTime:" + isFristTime);
    }


}
