package com.hdh.mall.market;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hdh.android.mail.base.BaseApplication;
import com.hdh.android.mail.base.Constant;
import com.hdh.android.mail.base.basic.WebViewActivity;
import com.hdh.android.mail.base.bean.BannerBean;
import com.hdh.android.mail.base.bean.GoodsBean;
import com.hdh.android.mail.base.bean.GoodsCategoryBean;
import com.hdh.android.mail.base.fragments.BaseMvpFragment;
import com.hdh.android.mail.base.http.Result;
import com.hdh.android.mail.base.modular.module.market.MarketIntent;
import com.hdh.android.mail.base.view.ToolBarX;
import com.hdh.common.status.StatusBarUtil;
import com.hdh.common.util.LogUtil;
import com.hdh.mall.market.contract.MarketContract;
import com.hdh.mall.market.presenter.MarketPresenter;
import com.hdh.mall.market.view.MarketSearchToolbar;
import com.hdh.widget.ScrollChangeScrollView;
import com.hdh.widget.banner.Banner;
import com.hdh.widget.banner.BannerImageLoader;
import com.hdh.widget.banner.listener.OnBannerListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by albert on 2017/10/30.
 */

public class MarketsFragment extends BaseMvpFragment<MarketContract.Presenter>
        implements MarketContract.View {
    @BindView(R2.id.toolbar)
    MarketSearchToolbar mSearchToolbar;
    private ToolBarX mToolbarX;
    @BindView(R2.id.banner)
    Banner mBanner;
    @BindView(R2.id.status_bar_ll)
    LinearLayout statusBarLl;
    @BindView(R2.id.market_sv)
    ScrollChangeScrollView marketSv;
    @BindView(R2.id.market_add_integral_tv)
    TextView marketAddIntegralTV;
    @BindView(R2.id.market_consumer_integral_tv)
    TextView marketConsumerTv;

    @BindView(R2.id.market_add_integral_ll)
    LinearLayout marketAddIntegralLl;
    @BindView(R2.id.market_consumer_integral_ll)
    LinearLayout marketConsumerLl;
    @BindView(R2.id.market_spread_banner_iv1)
    ImageView spreadIv1;
    @BindView(R2.id.market_spread_banner_iv2)
    ImageView spreadIv2;
    @BindView(R2.id.market_spread_banner_iv3)
    ImageView spreadIv3;
    @BindView(R2.id.market_spread_banner_iv4)
    ImageView spreadIv4;

    @BindView(R2.id.swipeRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;

    private boolean isUpdateBanner, isUpdateSpread, isUpdateCategory;

    private List<GoodsCategoryBean> goodsCategoryBeanList;
    private static final String LOG_TAG = "MarketsFragment";

    public static MarketsFragment getInstance() {
        MarketsFragment hf = new MarketsFragment();
        return hf;
    }

    @Override
    public void logout() {
        showToast(getResources().getString(R.string.logout_tips));
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.i(LOG_TAG, "onResume");
    }


    @Override
    protected void bindView(View mView) {
        super.bindView(mView);
        //处理状态栏
        int statusBar = StatusBarUtil.getStatusBarHeight(getActivity());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                statusBar);
        statusBarLl.setLayoutParams(params);
        boolean darkMode = StatusBarUtil.lightMode1(getActivity());
        smartRefreshLayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
        smartRefreshLayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作

        mToolbarX = new ToolBarX(mSearchToolbar);
        mToolbarX.setDisplayHomeAsUpEnabled(false);
        mSearchToolbar.setEndSrc(R.drawable.market_ic_shopping_market_cart);
        mSearchToolbar.setSearchIcon(R.drawable.market_ic_market_search);


    }

    @Override
    public void refreshBannerView(int type, final List<BannerBean> list) {

        if (type == 2) {
            isUpdateBanner = true;
            ArrayList<String> urls = new ArrayList<>();
            for (BannerBean bean : list) {
                urls.add(bean.image);
            }
            mBanner.setImages(urls).setImageLoader(new BannerImageLoader()).isAutoPlay(true).start();
            mBanner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    if (list != null) {
                        BannerBean bean = list.get(position);
                        clickBanner(bean);

                    }
                }
            });
        } else {
            isUpdateSpread = true;
            if (list != null && list.size() == 4) {
                if (!TextUtils.isEmpty(list.get(0).image)) {
                    BannerImageLoader.display(mContext, list.get(0).image, spreadIv1);
                } else {
                    BannerImageLoader.display(mContext, "", spreadIv1);
                }

                if (!TextUtils.isEmpty(list.get(1).image)) {
                    BannerImageLoader.display(mContext, list.get(1).image, spreadIv2);
                } else {
                    BannerImageLoader.display(mContext, "", spreadIv2);
                }

                if (!TextUtils.isEmpty(list.get(2).image)) {
                    BannerImageLoader.display(mContext, list.get(2).image, spreadIv3);
                } else {
                    BannerImageLoader.display(mContext, "", spreadIv3);
                }

                if (!TextUtils.isEmpty(list.get(3).image)) {
                    BannerImageLoader.display(mContext, list.get(3).image, spreadIv4);
                } else {
                    BannerImageLoader.display(mContext, "", spreadIv4);
                }

                spreadIv1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickBanner(list.get(0));
                    }
                });
                spreadIv2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickBanner(list.get(1));
                    }
                });
                spreadIv3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickBanner(list.get(2));
                    }
                });
                spreadIv4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickBanner(list.get(3));
                    }
                });

            }

        }

        if (isUpdateSpread && isUpdateBanner && isUpdateCategory) {
            stopLoading(smartRefreshLayout, true);
        }
    }


    @Override
    public void refreshCategoryView(final List<GoodsCategoryBean> list) {
        isUpdateCategory = true;
        goodsCategoryBeanList = list;
        if (list != null && list.size() == 2) {
            marketAddIntegralTV.setText(list.get(0).name);
            marketConsumerTv.setText(list.get(1).name);

            marketAddIntegralLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MarketIntent.gotoMarketType(list.get(0));
                }
            });

            marketConsumerLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MarketIntent.gotoMarketType(list.get(1));
                }
            });
        }

        if (isUpdateSpread && isUpdateBanner && isUpdateCategory) {
            stopLoading(smartRefreshLayout, true);
        }

    }


    @Override
    public void onQueryBannerAndCategoryDataCallback(String msg) {
        showToast(msg);
        stopLoading(smartRefreshLayout, false);
        isUpdateCategory = false;
        isUpdateSpread = false;
        isUpdateBanner = false;
    }


    @Override
    protected void bindMvp() {
        if (mPresenter == null)
            mPresenter = new MarketPresenter(this);
        mPresenter.onAttach();
    }

    @Override
    protected void bindEvent() {

        mSearchToolbar.setTextViewTextColor(getResources().getColor(R.color.white));
        mSearchToolbar.setSearchBarBackgroundColor(R.drawable.market_drawable_market_search_bg);

        mSearchToolbar.setOnEndClickListener(new MarketSearchToolbar.OnEndClickListener() {
            @Override
            public void onClick(View view) {

                if (isLogin()) {
                    if (isVerified()) {
                        //这种情况,有token,但是认为没有完善信息,直接留在当前页面,需要手动登录
                        BaseApplication.getSecondLevelCacheKit().put(Constant.LOGIN_FROME_TYPE, "market");
                    } else {
                        MarketIntent.gotoShoppingCart();
                    }

                } else {
                    BaseApplication.getSecondLevelCacheKit().put(Constant.LOGIN_FROME_TYPE, "market");
                }

            }
        });

        mSearchToolbar.setOnSearchViewClickListener(new MarketSearchToolbar.OnSearchViewClickListener() {
            @Override
            public void onClick(View view) {
                MarketIntent.gotoMarketSearch();
            }
        });


        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
            }
        });

        smartRefreshLayout.setEnableLoadmore(false);
    }

    @Override
    public void reportBannerStatisticsCallback(String message) {
        LogUtil.i(LOG_TAG, "reportBannerStatisticsCallback message:" + message);

    }

    @Override
    public void requestGoodsListCallback(Result<List<GoodsBean>> result, String msg) {

    }

    @Override
    protected void bindData() {

    }

    @Override
    public int setLayoutId() {
        return R.layout.market_fragment_home_market;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.i(LOG_TAG, "onDestroy   ");
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
        }
        LogUtil.i(LOG_TAG, "onHiddenChanged hidden：" + hidden);
    }


    /**
     * 点击商城推广
     *
     * @param bean
     */
    private void clickBanner(BannerBean bean) {
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

}