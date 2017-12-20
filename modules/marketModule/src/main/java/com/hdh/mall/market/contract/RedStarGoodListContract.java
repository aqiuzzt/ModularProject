package com.hdh.mall.market.contract;


import com.hdh.android.mail.base.bean.GoodsCategoryBean;
import com.hdh.android.mail.base.inte.IFragmentView;
import com.hdh.android.mail.base.inte.IModel;
import com.hdh.android.mail.base.inte.IPresenter;

import java.util.List;

import io.reactivex.ObservableEmitter;

/**
 * Created by albert on 2017/7/30.
 */

public interface RedStarGoodListContract {

    interface View extends IFragmentView<GoodsListContract.Presenter> {
        void refreshCategory(List<GoodsCategoryBean> list);

    }

    interface Presenter extends IPresenter {

        void preGoodsCategoryRequest(ObservableEmitter<Object> emitter);

    }

    interface Model extends IModel {

    }
}
