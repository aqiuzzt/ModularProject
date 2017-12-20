package com.hdh.android.mail.base;


/**
 * Created by jsy on 17/3/26.
 */

public final class Constant {
    /**
     * 本地信息保存文件
     */
    public static final class Storage {
        public static final String PREFERENCE_NAME = "jc_app_storage";
        public static final String PREFERENCE_KEEP_NAME = "jc_app_storage_keep";
    }

    /**
     * 微信支付app id
     */
    public final static String WX_APP_ID = "wxe8d557b7ec0231bd";
    /**
     * 收货地址type
     */
    public static final String ADDRESS_TYPE = "Receiver_Address";
    /**
     * 0: Unknown
     * 10: 消费者
     * 1: VIP会员(原军人家属)
     * 2：一级商家(废弃)
     * 3：商家(原二级商家)
     * 4：三级商家(废弃)
     * 5：业务主管(业务员)
     * 6：经理(原运营中心)
     * 7：总监(原商务中心)
     * 8：总经理(区域代理)
     * 9：总裁(代理商)
     * 31: 区代理
     * 32: 市代理
     * 33: 省代理 Provincial agent
     */
    public static final int ROLE_CONSUMER = 10;
    public static final int ROLE_VIP = 1;
    public static final int ROLE_MERCHANT = 3;
    public static final int ROLE_PROVINCIAL_AGENT = 33;
    public static final int ROLE_CONSUMER_TOURISTS = 100;


    public static final int ROLE_MERCHANT_1 = 2;
    public static final int ROLE_MERCHANT_2 = 3;
    public static final int ROLE_MERCHANT_3 = 4;

    /**
     * 我的红星几个状态
     */
    public static final int INTEGRAL_TYPE_UNKNOWN = 0;
    public static final int INTEGRAL_TYPE_EXCITATION = 1;
    public static final int INTEGRAL_TYPE_FINISH_EXCITATION = 2;
    public static final int INTEGRAL_TYPE_FREEZE = 3;


    /**
     * 邮箱注册的
     */
    public static final String ACCOUNT_EMAIL_REGISTER = "0";
    /**
     * 手机号码注册
     */
    public static final String ACCOUNT_PHONE_REGISTER = "1";

    public static final String UPDATE_TIME = "update_time";





    //启动下载新apk服务action
    public static final String UPDATE_APK = "com.jcgy.mall.client.UPDATE_SERVICE";


    //更新常量
    public static final String IS_CHECK_NORLMAL_UPDATE = "is_check_update";
    public static final String APK_VERSION_NAME_OLD = "oldversionName";

    /**
     * 创建订单id
     */
    public static final String MERCHANT_ORDER_CREATE_ID = "merchant_order_create_id";

    /**
     * 创建订单信息
     */
    public static final String MERCAHNT_CREATE_ORDER_DETAIL_INFO = "merchant_create_order_detail_info";

    /**
     * 创建订单信息
     */
    public static final String MERCAHNT_CREATE_ORDER_DETAIL_INFO_NEW = "merchant_create_order_detail_info_new";

    /**
     * 平台账号信息
     */
    public static final String MERCAHNT_PLATFORM_ACCOUNT_DETAIL_INFO = "merchant_create_order_detail_info";

    public static final String MERCHANT_CER_PAYNOW_URL = "merchant_cer_paynow_url";

    public static final String MERCHANT_CHOOSE_ORDER_STATE = "merchant_choose_order_state";
    public static final String MERCHANT_CHOOSE_ORDER_STATE_DESC = "merchant_choose_order_state_desc";

    public static final String GOOD_DETAIL_INFO = "good_detail_info";

    public static final String REDSTAR_ACTIVITY_DETAIL_INFO = "redstar_activity_detail_info";

    public static final String REDSTAR_ACTIVITY_DETAIL_INFO_STATUS = "redstar_activity_detail_info_status";

    public static final String REDSTAR_ACTIVITY_ANNOUNCED_DETAIL_INFO = "redstar_activity_announced_detail_info";
    public static final String LOGIN_FROME_TYPE = "login_type";
    public static final String ACCOUNT_FINANCE_ALL_INFO = "account_finance_all_info";

}
