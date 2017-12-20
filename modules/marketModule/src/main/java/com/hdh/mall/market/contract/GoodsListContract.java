package com.hdh.mall.market.contract;


import com.hdh.android.mail.base.bean.GoodsCategoryBean;
import com.hdh.android.mail.base.inte.IFragmentView;
import com.hdh.android.mail.base.inte.IModel;
import com.hdh.android.mail.base.inte.IPresenter;

import java.util.List;

public interface GoodsListContract {

    interface View extends IFragmentView<Presenter> {
        /**
         * 刷新商品类别
         * @param list
         */
        void refreshCategory(List<GoodsCategoryBean> list);

    }

    interface Presenter extends IPresenter {

        /**
         * 查询商品类别
         */
        void preGoodsCategoryRequest(String type);

    }

    interface Model extends IModel {

    }
}
