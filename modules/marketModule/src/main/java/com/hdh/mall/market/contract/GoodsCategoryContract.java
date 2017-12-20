package com.hdh.mall.market.contract;

import com.hdh.android.mail.base.bean.GoodsCategoryBean;
import com.hdh.android.mail.base.inte.IActivityView;
import com.hdh.android.mail.base.inte.IModel;
import com.hdh.android.mail.base.inte.IPresenter;
import com.hdh.common.http.handler.DataCallback;

import java.util.List;

public interface GoodsCategoryContract {

    interface View extends IActivityView {
        void refresh(List<GoodsCategoryBean> data);
    }

    interface Presenter extends IPresenter {
        void  requestAllCategoryData();
    }

    interface Model extends IModel {
        void  requestAllCategoryData(DataCallback callback);
    }
}
