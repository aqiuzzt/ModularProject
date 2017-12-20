package com.hdh.android.mail.base.modular.provider;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/20 14:34
 */
public interface IHomesProvider extends IFragmentProvider {
    //服务
    String HOMES_MAIN_SERVICE = "/homepage/main/service";

    //作为Fragment被添加时候的key
    String HOMES_KEY_FRAGMENT = "homepage_key_fragment";

    String HOMES_ACT_ANNOUNCEMENT = "/homepage/act/announcement";

    String HOMES_ACT_CONSUMPTION_INTEGRAL = "/homepage/act/consumption_integral";
    String HOMES_ACT_CONSUMPTION_RECORD = "/homepage/act/consumption_record";
    String Homes_ACT_INTEGRAL_RECORD = "/homepage/act/integral_record";


    String HOMES_ACT_BUY_BACK = "/homepage/act/buy_back";
    String HOMES_ACT_BUY_BACK_RECORD = "/homepage/act/buy_back_record";
    String HOMES_ACT_BUY_BACK_RECORD_DETAIL = "/homepage/act/buy_back_record_detail";


    String HOMES_ACT_SHOPPING_CONSUMED = "/homepage/act/shopping_consumed";
    String HOMES_ACT_STIMULATE_STRESS_CASH = "/homepage/act/stimulate_stress_cash";



    String HOMES_ACT_SHOPPING_PEA = "/homepage/act/shopping_pea";


    String HOMES_ACT_REDSTAR_DETAIL = "/homepage/act/redstar_detail";


    String HOMES_ACT_RECOMMEND = "/homepage/act/recommend";
    String HOMES_ACT_RECOMMEND_RECORD = "/homepage/act/recommend_record";


    String HOMES_ACT_BECOME_VIP = "/homepage/act/become_vip";
    String HOMES_ACT_VIP_PAYMENT = "/homepage/act/vip_payment";

}
