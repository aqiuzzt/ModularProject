package com.hdh.mall.market.contract;


import com.hdh.android.mail.base.bean.SaleAttributesBean;
import com.hdh.android.mail.base.inte.IActivityView;
import com.hdh.android.mail.base.inte.IModel;
import com.hdh.android.mail.base.inte.IPresenter;

import java.util.List;

public interface GoodsDetailContract {

    interface View extends IActivityView {

        void refreshGoodDetail(String msg);

        void dismiss();
    }

    interface Presenter extends IPresenter {
        /**
         * 请求商品详情
         *
         * @param goodsId
         */
        void requestGoodsDetail(String goodsId);

        /**
         * 添加到购物差
         *
         * @param selectAttribute
         */
        void addToCart(List<SaleAttributesBean> selectAttribute, long count);
    }

    interface Model extends IModel {
    }
}
