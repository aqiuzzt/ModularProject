package com.hdh.mall.market.contract;


import com.hdh.android.mail.base.bean.AccountFinanceInfo;
import com.hdh.android.mail.base.bean.AddressBean;
import com.hdh.android.mail.base.bean.OrderPayAppDTO;
import com.hdh.android.mail.base.bean.ShoppingCartBean;
import com.hdh.android.mail.base.inte.IActivityView;
import com.hdh.android.mail.base.inte.IModel;
import com.hdh.android.mail.base.inte.IPresenter;

import java.util.List;

public interface CreateOrderContract {

    interface View extends IActivityView {
        void refeshDefaultAddress(AddressBean addressBean);

        /**
         * 订单创建成功支付处理
         * @param data
         */
        void orderToShowPay(OrderPayAppDTO data);

        /**
         * 订单回调
         * @param success
         * @param msg
         */
        void creatOrderCallback(boolean success, String msg);

        void payByBalanceCallbak(boolean success, String msg);

        void setFinanceInfo(List<AccountFinanceInfo> financeInfo);

        void queryFinanceInfoCallBack(String msg);

    }

    interface Presenter extends IPresenter {
        void queryDefaultAddress();

        void createOrder(AddressBean addressBean, List<ShoppingCartBean> list);

        void payByBalance(int payType, String psw, OrderPayAppDTO data);

        void queryFinanceInfo();
    }

    interface Model extends IModel {

    }
}
