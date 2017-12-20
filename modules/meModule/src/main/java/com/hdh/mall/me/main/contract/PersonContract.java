package com.hdh.mall.me.main.contract;



import com.hdh.android.mail.base.inte.IFragmentView;
import com.hdh.android.mail.base.inte.IModel;
import com.hdh.android.mail.base.inte.IPresenter;

import io.reactivex.Observable;

/**
 * Desc:
 * Author:Martin
 * Date:2017/3/9
 */

public interface PersonContract {

    interface View extends IFragmentView<Presenter> {
        void refresh();

        void requestCallback(String msg);

        void requestLogoutCallBack(String msg);

        void verifyLoginPwdCallback(String msg);
    }

    interface Presenter extends IPresenter {
        void requestPersonInfo();

        void requestLogout();

        /**
         * 一级商家入单验证登录密码
         * @param password
         */
        void verifyLoginPwd(String password);

    }

    interface Model extends IModel {

        Observable<String> verifyLoginPwd(String password);

    }
}
