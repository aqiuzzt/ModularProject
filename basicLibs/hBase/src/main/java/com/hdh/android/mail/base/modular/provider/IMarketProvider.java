package com.hdh.android.mail.base.modular.provider;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/20 14:34
 */
public interface IMarketProvider extends IFragmentProvider {
    //服务
    String MARKET_MAIN_SERVICE = "/market/main/service";
    //作为Fragment被添加时候的key
    String MARKET_KEY_FRAGMENT = "market_key_fragment";


    String MARKET_ACT_CREATE_ORDER = "/market/act/create_order";
    String MARKET_ACT_GOOG_CATEGORY = "/market/act/good_category";
    String MARKET_ACT_GOOD_DETAIL = "/market/act/good_detail";
    String MARKET_ACT_GOOD_LIST = "/market/act/good_list";
    String MARKET_ACT_MARKET_SEARCH = "/market/act/market_search";
    String MARKET_ACT_MARKET_TYPE = "/market/act/market_type";
    String MARKET_ACT_ORDER_PAYMENT = "/market/act/order_payment";
    String MARKET_ACT_SHOPPING_CART = "/market/act/shopping_cart";

}
