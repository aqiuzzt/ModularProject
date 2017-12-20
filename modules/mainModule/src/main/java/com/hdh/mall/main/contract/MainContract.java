package com.hdh.mall.main.contract;


import com.hdh.android.mail.base.inte.IActivityView;
import com.hdh.android.mail.base.inte.IModel;
import com.hdh.android.mail.base.inte.IPresenter;
import com.hdh.mall.main.bean.UpdateInfoBean;

import io.reactivex.Observable;

public interface MainContract {

    interface View extends IActivityView {

        void checkApkUpdateCallback(UpdateInfoBean updateInfoBean, String msg);

    }

    interface Presenter extends IPresenter {
        void init();

        void checkApkUpdate();
    }

    interface Model extends IModel {
        Observable<String> checkApkUpdate(String oldVersion);

    }
}
