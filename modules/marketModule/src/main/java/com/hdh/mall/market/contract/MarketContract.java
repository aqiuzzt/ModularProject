package com.hdh.mall.market.contract;


import com.hdh.android.mail.base.bean.BannerBean;
import com.hdh.android.mail.base.bean.GoodsBean;
import com.hdh.android.mail.base.bean.GoodsCategoryBean;
import com.hdh.android.mail.base.http.Result;
import com.hdh.android.mail.base.inte.IFragmentView;
import com.hdh.android.mail.base.inte.IModel;
import com.hdh.android.mail.base.inte.IPresenter;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/12/4 10:56
 */
public interface MarketContract {

    interface View extends IFragmentView<Presenter> {
        void refreshBannerView(int type, List<BannerBean> list);

        void refreshCategoryView(List<GoodsCategoryBean> list);


        void onQueryBannerAndCategoryDataCallback(String msg);


        void reportBannerStatisticsCallback(String message);

        void requestGoodsListCallback(Result<List<GoodsBean>> result, String msg);
    }

    interface Presenter extends IPresenter {
        /**
         * 初始化banner和类别数据
         */
        void onQueryBannerAndCategoryData();

        /**
         * 广告统计
         *
         * @param infoId 需统计的业务id
         * @param type   数据类型,1: 普通URL跳转, 2:跳转到商品详情, 3:跳转到商家, 4:无
         */
        void reportBannerStatistics(String infoId, int type);

        void requestGoodsList(String since, int pageSize, String categoryId);
    }

    interface Model extends IModel {
        Observable<String> getMarketBanner(int type);

        /**
         * 广告统计
         *
         * @param infoId 需统计的业务id
         * @param type   数据类型,1: 普通URL跳转, 2:跳转到商品详情, 3:跳转到商家, 4:无
         */
        Observable<String> reportBannerStatistics(String infoId, int type);

        Observable<String> requestGoodsList(String since, int pageSize, String categoryId);


    }
}
