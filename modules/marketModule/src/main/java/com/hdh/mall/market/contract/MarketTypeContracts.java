package com.hdh.mall.market.contract;

import com.hdh.android.mail.base.bean.GoodsBean;
import com.hdh.android.mail.base.bean.GoodsCategoryBean;
import com.hdh.android.mail.base.inte.IFragmentView;
import com.hdh.android.mail.base.inte.IModel;
import com.hdh.android.mail.base.inte.IPresenter;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by albert on 2017/10/27.
 */

public interface MarketTypeContracts {

    interface View extends IFragmentView<Presenter> {

        void refreshCategoryView(List<GoodsCategoryBean> list);

        void onQueryCategoryDataCallback(String msg);

        void requestGoodsListCallback(List<GoodsBean> goodsBeanList, String msg);

        void requestDefaultConfig(String msg);

    }

    interface Presenter extends IPresenter {
        /**
         * 类别数据
         */
        void onQueryCategoryData(String prantId);

        /**
         * 获取商品
         *
         * @param categoryId
         */
        void requestGoodsList(int pageIndex, int pageSize, String categoryId);

        void requestDefaultConfig(String type);

    }

    interface Model extends IModel {


        Observable<String> requestGoodsList(int pageIndex, int pageSize, String categoryId);

        Observable<String> requestDefaultConfig(String type);

    }
}
