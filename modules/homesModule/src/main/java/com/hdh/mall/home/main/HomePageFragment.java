package com.hdh.mall.home.main;

import android.Manifest;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.hdh.android.mail.base.BaseApplication;
import com.hdh.android.mail.base.Constant;
import com.hdh.android.mail.base.basic.WebViewActivity;
import com.hdh.android.mail.base.bean.AccountExtendDTO;
import com.hdh.android.mail.base.bean.AccountFinanceInfo;
import com.hdh.android.mail.base.bean.BannerBean;
import com.hdh.android.mail.base.fragments.BaseMvpFragment;
import com.hdh.android.mail.base.http.Result;
import com.hdh.android.mail.base.modular.config.ParmaBundle;
import com.hdh.android.mail.base.modular.module.homes.HomesIntent;
import com.hdh.android.mail.base.modular.module.market.MarketIntent;
import com.hdh.common.status.StatusBarUtil;
import com.hdh.common.util.HSON;
import com.hdh.common.util.LogUtil;
import com.hdh.common.util.MoneyUtil;
import com.hdh.mall.home.R;
import com.hdh.mall.home.R2;
import com.hdh.mall.home.main.adapter.HomeHotGoodAdapter;
import com.hdh.mall.home.main.bean.AnnouncementBean;
import com.hdh.mall.home.main.bean.HomeCategoryItem;
import com.hdh.mall.home.main.bean.StatisticsFinanceBean;
import com.hdh.mall.home.main.contract.HomeContract;
import com.hdh.mall.home.main.presenter.HomePresenter;
import com.hdh.mall.home.main.view.HomeHeaderView;

import com.hdh.widget.GridDecoration;
import com.hdh.widget.dialog.CustomAlertDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by albert on 2017/9/25.
 */

public class HomePageFragment extends BaseMvpFragment<HomeContract.Presenter>
        implements HomeContract.View,
        HomeHeaderView.OnCategoryClickListener,
        HomeHotGoodAdapter.OnBannerClickListener {
    private static final String LOG_TAG = "HomePageFragment";

    @BindView(R2.id.recyclerView)
    RecyclerView homeHotRecommentRcv;
    @BindView(R2.id.swipeRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    private HomeHotGoodAdapter mGooddAdater;
    private HomeHeaderView mHeaderView;
    private AccountExtendDTO mAccountExtendDTO;
    private RxPermissions mRxPermissions;
    private boolean isFristTime = true;
    private AccountFinanceInfo peaFinanceInfo;
    private int noticsPageIndex = 1;

    @Override
    protected void bindView(View mView) {
        super.bindView(mView);
        setHasOptionsMenu(true);
        boolean darkMode = StatusBarUtil.lightMode1(getActivity());
        smartRefreshLayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
        smartRefreshLayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
    }

    @Override
    protected void bindData() {

        mRxPermissions = new RxPermissions(mActivity);
        mRxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION).subscribe();
        isFristTime = false;

        mGooddAdater = new HomeHotGoodAdapter(getContext());
        mHeaderView = new HomeHeaderView(mContext, Constant.ROLE_CONSUMER_TOURISTS);
        mGooddAdater.addHeaderView(mHeaderView);
        GridDecoration itemDecoration = new GridDecoration(getContext(), GridDecoration.VERTICAL, true, true, true, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        homeHotRecommentRcv.addItemDecoration(itemDecoration);
        homeHotRecommentRcv.setLayoutManager(gridLayoutManager);
        homeHotRecommentRcv.setAdapter(mGooddAdater);
        mHeaderView.setInitInfo();

    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_home_page;
    }

    @Override
    protected void bindEvent() {
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if (mAccountExtendDTO != null) {
                    getGlodIntegral();
                }
            }
        });

        smartRefreshLayout.setEnableLoadmore(false);

        mHeaderView.setOnCategoryClickListener(this);
        mGooddAdater.setOnBannerClickListener(this);
    }


    public static HomePageFragment getInstance() {
        HomePageFragment hf = new HomePageFragment();
        return hf;
    }


    @Override
    protected void bindMvp() {
        if (mPresenter == null)
            mPresenter = new HomePresenter(this);
        mPresenter.onAttach();
    }

    private void getGlodIntegral() {
        int role = BaseApplication.get().getUserManager().getmUserRole();
        String accountId = BaseApplication.get().getUserManager().getAccountId();
    }


    @Override
    public void onResume() {
        super.onResume();
        LogUtil.i(LOG_TAG, "onResume");

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && !isFristTime) {
            mAccountExtendDTO = BaseApplication.get().getUserManager().getAccountProvider();
            if (mAccountExtendDTO != null) {
                getGlodIntegral();
                mHeaderView.setInitInfoZero();
            }

        }
        LogUtil.i(LOG_TAG, "onHiddenChanged hidden：" + hidden + " isFristTime:" + isFristTime);
    }


    @Override
    public void onQueryBannerCallback(List<BannerBean> data, String msg) {
        LogUtil.i(LOG_TAG, "onQueryBannerCallback msg:" + msg);
        if (!TextUtils.isEmpty(msg)) {
            showToast(msg);
            stopLoading(smartRefreshLayout, false);
        } else {
            mGooddAdater.setNewData(data);
            stopLoading(smartRefreshLayout, true);
        }
    }

    @Override
    public void onQueryAccountFinance(String message) {
        LogUtil.i(LOG_TAG, "onQueryAccountFinance msg:" + message);
        if (!TextUtils.isEmpty(message)) {
            showToast(message);
        } else {
            if (!TextUtils.isEmpty(BaseApplication.getSecondLevelCacheKit().getAsString(Constant.ACCOUNT_FINANCE_ALL_INFO))) {
                LogUtil.i(LOG_TAG, "account_finance_all_info:" + BaseApplication.getSecondLevelCacheKit().getAsString(Constant.ACCOUNT_FINANCE_ALL_INFO));

                Result<List<AccountFinanceInfo>> result = HSON.parse(BaseApplication.getSecondLevelCacheKit().getAsString(Constant.ACCOUNT_FINANCE_ALL_INFO)
                        , new TypeToken<Result<List<AccountFinanceInfo>>>() {
                        });

                for (int i = 0; i < result.data.size(); i++) {
                }
            }
        }
    }

    @Override
    public void onQueryAccountFinanceConsum(String consumValue, String message) {
        LogUtil.i(LOG_TAG, "onQueryAccountFinanceConsum message:" + message + " consumValue:" + consumValue);
        if (!TextUtils.isEmpty(message)) {
            showToast(message);
        } else {
            if (!TextUtils.isEmpty(consumValue)) {
                mHeaderView.bindAccountConsumFinance("1", MoneyUtil.getMoneyYuanDouble(consumValue));
            }
        }
    }



    @Override
    public void queryAccountFinancePerformance(String performanceValue, String message) {
        LogUtil.i(LOG_TAG, "queryAccountFinancePerformance msg:" + message);
        if (!TextUtils.isEmpty(message)) {
            showToast(message);
        } else {
            if (!TextUtils.isEmpty(performanceValue)) {
                mHeaderView.bindAccountConsumFinance("2", MoneyUtil.getMoneyYuan(performanceValue));
            }
        }
    }

    @Override
    public void queryAnnouncementListCallback(List<AnnouncementBean> announcementBeen, String msg) {
        LogUtil.i(LOG_TAG, "queryAnnouncementListCallback msg:" + msg);
        if (!TextUtils.isEmpty(msg)) {
            showToast(msg);
            List<AnnouncementBean> list = new ArrayList<>();
            AnnouncementBean announcementBean = new AnnouncementBean();
            announcementBean.setParentId("test");
            announcementBean.setTitle("app上线，欢迎使用。");
            list.add(announcementBean);
            mHeaderView.setNoticeView(list);
        } else {
            mHeaderView.setNoticeView(announcementBeen);
        }

    }

    @Override
    public void queryFinancePerformanceCallback(StatisticsFinanceBean statisticsFinanceBean, String msg) {
        LogUtil.i(LOG_TAG, "queryFinancePerformanceCallback msg:" + msg);
        if (!TextUtils.isEmpty(msg)) {
            showToast(msg);
        } else {
            if (statisticsFinanceBean != null) {
                mHeaderView.bindAccountConsumFinance(MoneyUtil.getMoneyYuan(statisticsFinanceBean.getShopAmount()));
            } else {
                mHeaderView.bindAccountConsumFinance("0");
            }
        }
    }

    @Override
    public void logout() {
        mHeaderView.setInitInfo();
        showToast(getResources().getString(R.string.logout_tips));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.i(LOG_TAG, "onDestroy   ");
    }

    @Override
    public void onCategoryClickListener(int type) {
        if (isLogin()) {
            if (isVerified()) {
                //这种情况,有token,但是认为没有完善信息,直接留在当前页面,需要手动登录
                BaseApplication.getSecondLevelCacheKit().put(Constant.LOGIN_FROME_TYPE, "home");
            } else {
                switch (type) {
                    case HomeCategoryItem.TYPE_BUYBACK:
                        HomesIntent.gotoBuyBack();
                        break;
                    case HomeCategoryItem.TYPE_RECOMMEND:
                        HomesIntent.gotoRecommend();
                        break;
                    case HomeCategoryItem.TYPE_RECHARGE:
                        if (isConsumer()) {
                            new CustomAlertDialog(getActivity()).builder().setTitle("提示")
                                    .setMsg("目前等级不能预存，请先升级成为会员").setPositiveButton("确定",
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
//                                            BecomeVipActivity.start(getContext());
                                            HomesIntent.gotoBecomVip();
                                        }
                                    }).setNegativeButton(getResources().getString(R.string.update_canel), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            }).setCanceledOnTouchOutside(false).setCancelable(false).show();

                        } else {
                        }
                        break;
                    case HomeCategoryItem.TYPE_BECOME_VIP:
                        HomesIntent.gotoBecomVip();
                        break;
                }
            }
        } else {
            BaseApplication.getSecondLevelCacheKit().put(Constant.LOGIN_FROME_TYPE, "home");
        }

    }

    @Override
    public void onIntergalClickListener(int type) {

        ParmaBundle parmaBundle = new ParmaBundle();
        if (isLogin()) {
            if (isVerified()) {
                //这种情况,有token,但是认为没有完善信息,直接留在当前页面,需要手动登录
                BaseApplication.getSecondLevelCacheKit().put(Constant.LOGIN_FROME_TYPE, "home");
            } else {


            }
        } else {
            BaseApplication.getSecondLevelCacheKit().put(Constant.LOGIN_FROME_TYPE, "home");
        }

    }


    @Override
    public void onClickBanner(BannerBean bean) {
        switch (bean.type) {
            case BannerBean.BANNER_TYPE_URL:
                if (!TextUtils.isEmpty(bean.url) && (bean.url.startsWith("http://") || bean
                        .url.startsWith("https://"))) {
                    WebViewActivity.start(mContext, null, bean.url);
                }
                break;

            case BannerBean.BANNER_TYPE_GOOD:
                LogUtil.i(LOG_TAG, "bean.infoId " + bean.infoId);
                MarketIntent.gotoGoodDetail(bean.infoId);
                break;
        }

    }

    @Override
    public void reportBannerStatisticsCallback(String message) {
        LogUtil.i(LOG_TAG, "reportBannerStatisticsCallback message:" + message);

    }
}
