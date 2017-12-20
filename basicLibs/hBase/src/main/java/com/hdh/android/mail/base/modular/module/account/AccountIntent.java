package com.hdh.android.mail.base.modular.module.account;

import android.app.Activity;

import com.hdh.android.mail.base.modular.config.ParmaBundle;
import com.hdh.android.mail.base.modular.provider.IAccountInfoProvider;
import com.hdh.android.mail.base.modular.router.ModuleManager;
import com.hdh.android.mail.base.modular.router.MyARouter;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gzhjhc.com">Contact me.</a>
 * @since 2017/12/14 11:48
 */

public class AccountIntent {
    public static final int CHANGE_PWD_TYPE_PAY = 1;
    public static final int CHANGE_PWD_TYPE_LOGIN = 2;
    private static ParmaBundle parmaBundle = new ParmaBundle();

    private static boolean hasModule() {
        return ModuleManager.getInstance().hasModule(IAccountInfoProvider.ACCOUNT_MAIN_SERVICE);
    }

    public static void gotChangPhone() {
        MyARouter.newInstance(IAccountInfoProvider.ACCOUNT_ACT_CHANGE_PHONE)
                .navigation();
    }

    public static void gotoChangeLoginPwdForPayPwd() {
        parmaBundle.put("type", CHANGE_PWD_TYPE_PAY);
        MyARouter.newInstance(IAccountInfoProvider.ACCOUNT_ACT_CHANGE_LOGIN_PWD)
                .withBundle(parmaBundle)
                .navigation();
    }

    public static void gotoChangeLoginPwdForLoginPwd() {
        parmaBundle.put("type", CHANGE_PWD_TYPE_LOGIN);
        MyARouter.newInstance(IAccountInfoProvider.ACCOUNT_ACT_CHANGE_LOGIN_PWD)
                .withBundle(parmaBundle)
                .navigation();
    }


    public static void gotoAccountSetting(Activity activity, int requestCode) {
        MyARouter.newInstance(IAccountInfoProvider.ACCOUNT_ACT_ACCOUNT_SETTING)
                .navigation(activity, requestCode);

    }


    public static void gotoSetPayPwd() {
        MyARouter.newInstance(IAccountInfoProvider.ACCOUNT_ACT_SET_PAY_PWD)
                .navigation();
    }

    public static void gotoSetPayPwd(Activity activity, int requestCode) {
        MyARouter.newInstance(IAccountInfoProvider.ACCOUNT_ACT_SET_PAY_PWD)
                .navigation(activity, requestCode);
    }

}
