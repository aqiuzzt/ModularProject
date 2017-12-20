package com.hdh.android.mail.base.modular.provider;

/**
 * 我的
 *
 * @author albert  <a href="mailto:zhengting.zhou@gzhjhc.com">Contact me.</a>
 * @since 2017/12/1 16:52
 */

public interface IMeProvider extends IFragmentProvider {

    /**
     * 我的模块服务
     */
    String ME_MAIN_SERVICE = "/me/main/service";

    //作为Fragment被添加时候的key
    String ME_KEY_FRAGMENT = "me_key_fragment";



    String ME_ACT_MY_ORDER_LIST = "/me/act/my_order_list";






    String ME_ACT_ABOUT_ME = "/me/act/about_me";
    String ME_ACT_CUSTOMER_SERVICE = "/me/act/customer_service";
    String ME_ACT_MARKET_ACCOUNCEMENT = "/me/act/market_announcement";


    String ME_ACT_DELIVER_PATH = "/me/act/deliver_path";
    String ME_ACT_ORDER_DETAIL = "/me/act/order_detail";


}
