package com.hdh.mall.market.contract;


import com.hdh.android.mail.base.bean.ShoppingCartBean;
import com.hdh.android.mail.base.inte.IActivityView;
import com.hdh.android.mail.base.inte.IModel;
import com.hdh.android.mail.base.inte.IPresenter;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * Created by albert on 2017/8/9.
 */

public interface EditShoppingCartContract {

    interface View extends IActivityView {

        void onDeleteAllGoodsCallback(String message);

        void deleteShoppingCartItemCallback(String msg);

    }

    interface Presenter extends IPresenter {
        void deleteAllGoods(ArrayList<ShoppingCartBean> list);

        void deleteShoppingCartItem(String id);

    }

    interface Model extends IModel {

        Observable<String> deleteAllGoods(String[] selectIds);

        Observable<String> deleteShoppingCartItem(String Id);

    }
}
