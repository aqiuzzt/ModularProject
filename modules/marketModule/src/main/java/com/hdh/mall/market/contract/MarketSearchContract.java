package com.hdh.mall.market.contract;


import com.hdh.android.mail.base.bean.GoodsBean;
import com.hdh.android.mail.base.inte.IActivityView;
import com.hdh.android.mail.base.inte.IModel;
import com.hdh.android.mail.base.inte.IPresenter;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by albert on 2017/10/8.
 */

public interface MarketSearchContract {

    interface View extends IActivityView {


        void onGoodSearchCallback(List<GoodsBean> goodsBeenList, String msg);

    }

    interface Presenter extends IPresenter {

        /**
         * 获取商品
         *
         */
        void goodSearchGoodsList(String goodName, int pageIndex, int pageSize);
    }

    interface Model extends IModel {


        Observable<String> requestGoodsList(int pageIndex, int pageSize, String goodName);
    }
}
