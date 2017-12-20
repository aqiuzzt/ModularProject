package com.hdh.mall.market.contract;


import com.hdh.android.mail.base.bean.ShoppingCartBean;
import com.hdh.android.mail.base.http.Result;
import com.hdh.android.mail.base.inte.IFragmentView;
import com.hdh.android.mail.base.inte.IModel;
import com.hdh.android.mail.base.inte.IPresenter;
import com.hdh.mall.market.bean.ShoppingCartClassifyResult;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by albert on 2017/10/12.
 */

public interface ShoppingCartContract {

    interface View extends IFragmentView<Presenter> {
        void onDeleteAllGoodsCallback(String message);

        void deleteShoppingCartItemCallback(String msg);

        void requestShopCartCallback(Result<ShoppingCartClassifyResult> result, String msg);


    }

    interface Presenter extends IPresenter {
        void deleteAllGoods(List<ShoppingCartBean> list);

        void deleteShoppingCartItem(String id);

        void requestShopCart(String cursor, int pageSize);


    }

    interface Model extends IModel {

        Observable<String> deleteAllGoods(String[] selectIds);

        Observable<String> deleteShoppingCartItem(String Id);

        Observable<String> requestShopCart(String cursor, int pageSize);

    }
}

