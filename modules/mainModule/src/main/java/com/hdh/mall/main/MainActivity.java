package com.hdh.mall.main;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hdh.android.mail.base.BaseApplication;
import com.hdh.android.mail.base.Constant;
import com.hdh.android.mail.base.activitys.BaseMvpActivity;
import com.hdh.android.mail.base.modular.module.homes.HomesService;
import com.hdh.android.mail.base.modular.module.market.MarketService;
import com.hdh.android.mail.base.modular.module.me.MeService;
import com.hdh.android.mail.base.modular.provider.IMainProvider;
import com.hdh.common.util.LogUtil;
import com.hdh.common.util.app.AppNetworkUtil;
import com.hdh.common.util.app.AppUtil;
import com.hdh.common.util.view.ToastUtil;
import com.hdh.common.util.view.ViewFindUtils;
import com.hdh.mall.main.bean.UpdateInfoBean;
import com.hdh.mall.main.contract.MainContract;
import com.hdh.mall.main.presenter.MainPresenter;
import com.hdh.mall.main.service.DownloadTask;
import com.hdh.widget.BottomTabEntity;
import com.hdh.widget.dialog.CustomAlertDialog;

import java.util.ArrayList;

@Route(path = IMainProvider.MIAN_ACT_MAIN_HOME)
public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainContract.View {
    public static String TAG = MainActivity.class.getSimpleName();
    private static String LOG_TAG = "MainActivity1";


    /**
     * 底部导航tab layout
     */
    private CommonTabLayout mMenuTab;
    private ArrayList<CustomTabEntity> mTabEntities;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private int[] mTitles;

    /**
     * viewpager切换的fragment
     */
    private Fragment[] mFragmentListList;

    /**
     * viewpage tab导航状态图标
     */
    private int[] mIconSelectIds;
    private int[] mIconUnselectIds;

    private View mDecorView;
    private int currentTabIndex;

    private long lastTime;
    private DownloadTask task;
    private boolean isMerchant;

    @Override
    public int setLayoutId() {
        return R.layout.main_activity_main;
    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected void bindEvent() {


    }

    @Override
    protected void bindData() {

        long currentTime = System.currentTimeMillis();

        String oldTime = BaseApplication.getSecondLevelCacheKit().getAsString(Constant.UPDATE_TIME);

        if (TextUtils.isEmpty(oldTime)) {
            LogUtil.i(TAG, "oldTime:" + oldTime + " currentTime:" + currentTime);

            if (AppNetworkUtil.isNetworkAvailable(context)) {
                BaseApplication.getSecondLevelCacheKit().put(Constant.UPDATE_TIME, currentTime + "");
            }
        } else {
            long counttime = currentTime - Long.parseLong(oldTime);
            long days = counttime / (1000 * 60 * 60 * 24);
            if (days >= 1) {
                if (AppNetworkUtil.isNetworkAvailable(context)) {
                    BaseApplication.getSecondLevelCacheKit().put(Constant.UPDATE_TIME, currentTime + "");
                }
            }
            LogUtil.i(TAG, "oldTime:" + oldTime + " currentTime:" + currentTime + " days:" + days);
        }


    }

    @Override
    protected void bindView() {
        super.bindView();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        LogUtil.i(LOG_TAG, "bindView");
        mTabEntities = new ArrayList<>();
        LogUtil.i(LOG_TAG, "bindView 2");


        mFragmentListList = new Fragment[]{
                HomesService.getHomesFrgment(),
//                MerchantService.getMerchantFrgment(),
                MarketService.getMarketFragment(),
                MeService.getMeFrgment()
        };
        mTitles = new int[]{R.string.home_page, /*R.string.merchant_page, */R.string.market_page, R.string.mine_page};
        mIconSelectIds = new int[]{
                R.drawable.home_page_red, /*R.drawable.home_merchant_red, */R.drawable.home_market_red, R.drawable.home_me_red};
        mIconUnselectIds = new int[]{
                R.drawable.home_page_black,/* R.drawable.home_merchant_black, */R.drawable.home_market_black, R.drawable.home_me_black};


        for (int i = 0; i < mTitles.length; i++) {
            mFragments.add(mFragmentListList[i]);
        }
        for (int i = 0; i < mTitles.length; i++) {
            String title = getResources().getString(mTitles[i]);
            mTabEntities.add(new BottomTabEntity(title, mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mDecorView = getWindow().getDecorView();
        mMenuTab = ViewFindUtils.find(mDecorView, R.id.main_tab);
        mMenuTab.setTabData(mTabEntities, this, R.id.mian_frame_content, mFragments);
        LogUtil.i(LOG_TAG, "bindView 3");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (task != null) {
            task.cancel(true);
        }
        LogUtil.i(LOG_TAG, "onDestroy");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.i(LOG_TAG, "onResume");


        mMenuTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switch (position) {
                    case 2://我
                        if (isLogin()) {
                            if (isVerified()) {
                                //这种情况,有token,但是认为没有完善信息,直接留在当前页面,需要手动登录
                                BaseApplication.getSecondLevelCacheKit().put(Constant.LOGIN_FROME_TYPE, "main");
                            } else {
                                mMenuTab.setCurrentTab(position);
                                BaseApplication.getSecondLevelCacheKit().put(Constant.LOGIN_FROME_TYPE, "me");
                            }

                        } else {
                            BaseApplication.getSecondLevelCacheKit().put(Constant.LOGIN_FROME_TYPE, "main");
                        }
                        break;

                    case 0:
                        BaseApplication.getSecondLevelCacheKit().put(Constant.LOGIN_FROME_TYPE, "main");
                        mMenuTab.setCurrentTab(position);
                        break;

                    case 1:
                        BaseApplication.getSecondLevelCacheKit().put(Constant.LOGIN_FROME_TYPE, "market");
                        mMenuTab.setCurrentTab(position);
                        break;
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        String type = BaseApplication.getSecondLevelCacheKit().getAsString(Constant.LOGIN_FROME_TYPE);
        if (!TextUtils.isEmpty(type)) {
            LogUtil.i(LOG_TAG, "type:" + type);
            switch (type) {
                case "main":
                    mMenuTab.setCurrentTab(0);
                    break;
                case "merchant":
//                        mMenuTab.setCurrentTab(1);
//                        break;
                case "market":
                    mMenuTab.setCurrentTab(1);
                    break;
                case "me":
                    mMenuTab.setCurrentTab(2);
                    break;

            }
        }
//        }


    }


    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastTime > 3 * 1000) {
            lastTime = System.currentTimeMillis();
            ToastUtil.show(this, "再按一次退出应用");
        } else {
            BaseApplication.getActivityManager().AppExit(this);
            super.onBackPressed();
        }
    }


    @Override
    public void checkApkUpdateCallback(final UpdateInfoBean updateInfoBean, String msg) {


        LogUtil.i(TAG, "checkApkUpdateCallback   checkApkUpdateCallback");
        if (updateInfoBean == null && !TextUtils.isEmpty(msg)) {
            showToast(msg);
        }

        boolean isUpdate;
        String oldVersionName = "";
        String versionName = AppUtil.getAppVersionName(this);
        try {
            if (BaseApplication.getSecondLevelCacheKit().getAsSerializable(Constant.IS_CHECK_NORLMAL_UPDATE) == null) {
                isUpdate = false;
            } else {
                isUpdate = BaseApplication.getSecondLevelCacheKit().getAsSerializable(Constant.IS_CHECK_NORLMAL_UPDATE);
            }
            oldVersionName = BaseApplication.getSecondLevelCacheKit().getAsString(Constant.APK_VERSION_NAME_OLD);
            if (TextUtils.isEmpty(oldVersionName)) {
                oldVersionName = versionName;
                BaseApplication.getSecondLevelCacheKit().put(Constant.APK_VERSION_NAME_OLD, versionName);
            }

            if (updateInfoBean != null) {
                if (!TextUtils.isEmpty(updateInfoBean.versionName)
                        && !TextUtils.isEmpty(updateInfoBean.toggle)
                        && !TextUtils.isEmpty(updateInfoBean.type)) {

                    if (!oldVersionName.equals(updateInfoBean.versionName)) {
                        isUpdate = false;
                    }

                    LogUtil.i(TAG, "cuuu versionName:" + versionName + " updateInfoBean versionName:" + updateInfoBean.versionName);
                    if (versionName.compareTo(updateInfoBean.versionName) < 0 & updateInfoBean.toggle.equals("1")) {
                        //强制更新
                        if ("2".equals(updateInfoBean.type)) {
                            BaseApplication.getSecondLevelCacheKit().remove(Constant.UPDATE_TIME);
                            new CustomAlertDialog(this).builder().setTitle(updateInfoBean.title)
                                    .setMsg(updateInfoBean.msg).setPositiveButton(getResources().getString(R.string.update_sure),
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            task = new DownloadTask(MainActivity.this);
                                            task.execute(new String[]{updateInfoBean.url});
                                        }
                                    }).setCanceledOnTouchOutside(false).setCancelable(false)
                                    .show();
                        } else if ("1".equals(updateInfoBean.type) & !isUpdate) {//建议更新
                            new CustomAlertDialog(this).builder().setTitle(updateInfoBean.title)
                                    .setMsg(updateInfoBean.msg).setPositiveButton(getResources().getString(R.string.update_sure),
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            task = new DownloadTask(MainActivity.this);
                                            task.execute(new String[]{updateInfoBean.url});

                                            BaseApplication.getSecondLevelCacheKit().put(Constant.IS_CHECK_NORLMAL_UPDATE, false);
                                            BaseApplication.getSecondLevelCacheKit().put(Constant.APK_VERSION_NAME_OLD, updateInfoBean.versionName);
                                        }
                                    }).setNegativeButton(getResources().getString(R.string.update_canel), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    BaseApplication.getSecondLevelCacheKit().put(Constant.IS_CHECK_NORLMAL_UPDATE, true);
                                    BaseApplication.getSecondLevelCacheKit().put(Constant.APK_VERSION_NAME_OLD, updateInfoBean.versionName);

                                }
                            }).setCanceledOnTouchOutside(false).setCancelable(false).show();


                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void logout() {
        showToast(getResources().getString(R.string.logout_tips));
    }


}

