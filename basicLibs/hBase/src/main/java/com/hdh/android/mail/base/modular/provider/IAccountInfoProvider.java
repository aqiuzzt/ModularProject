package com.hdh.android.mail.base.modular.provider;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gzhjhc.com">Contact me.</a>
 * @since 2017/12/14 11:27
 */
public interface IAccountInfoProvider extends IBaseProvider {
    /**
     * 账号信息模块服务
     */
    String ACCOUNT_MAIN_SERVICE = "/account/main/service";

    //作为Fragment被添加时候的key
    String ACCOUNT_KEY_FRAGMENT = "account_key_fragment";
    String ACCOUNT_ACT_ACCOUNT_SETTING = "/account/act/account_setting";
    String ACCOUNT_ACT_BIND_PHONE = "/account/act/bind_phone";

    String ACCOUNT_ACT_CHANGE_LOGIN_PWD = "/account/act/change_login_pwd";

    String ACCOUNT_ACT_CHANGE_PHONE = "/account/act/change_phone";

    String ACCOUNT_ACT_SET_PAY_PWD = "/account/act/set_pay_pwd";
}
