package com.hdh.mall.market.contract;


import com.hdh.android.mail.base.bean.OrderPayAppDTO;
import com.hdh.android.mail.base.inte.IActivityView;
import com.hdh.android.mail.base.inte.IModel;
import com.hdh.android.mail.base.inte.IPresenter;

public interface PaymentContract {

    interface View extends IActivityView {
        void onPayCallback(String thirdPayDTO, int type, String msg);
    }

    interface Presenter extends IPresenter {
        /**
         * 调用第三方支付
         * @param orderPayAppDTO
         * @param type
         */
        void payByThird(OrderPayAppDTO orderPayAppDTO, int type);
    }

    interface Model extends IModel {

    }
}
