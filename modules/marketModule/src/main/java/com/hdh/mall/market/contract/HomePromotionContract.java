package com.hdh.mall.market.contract;


import com.hdh.android.mail.base.inte.IFragmentView;
import com.hdh.android.mail.base.inte.IModel;
import com.hdh.android.mail.base.inte.IPresenter;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gzhjhc.com">Contact me.</a>
 * @since 2017/11/13 10:13
 */

public interface HomePromotionContract {
    interface View extends IFragmentView<Presenter> {

        void reportBannerStatisticsCallback(String message);

    }

    interface Presenter extends IPresenter {
        /**
         * 广告统计
         * @param infoId 需统计的业务id
         * @param type 数据类型,1: 普通URL跳转, 2:跳转到商品详情, 3:跳转到商家, 4:无
         */
        void reportBannerStatistics(String infoId, int type);

    }

    interface Model extends IModel {

    }
}
