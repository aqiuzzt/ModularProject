package com.hdh.android.mail.base.utils;

import com.hdh.android.mail.base.BaseApplication;
import com.hdh.android.mail.base.Constant;


/**
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/12/4 18:05
 */
public class ClearCache {
    public static void clearAll() {

        BaseApplication.getSecondLevelCacheKit().remove(Constant.ACCOUNT_FINANCE_ALL_INFO);
        BaseApplication.getSecondLevelCacheKit().remove(Constant.REDSTAR_ACTIVITY_ANNOUNCED_DETAIL_INFO);
        BaseApplication.getSecondLevelCacheKit().remove(Constant.REDSTAR_ACTIVITY_DETAIL_INFO_STATUS);
        BaseApplication.getSecondLevelCacheKit().remove(Constant.REDSTAR_ACTIVITY_DETAIL_INFO);
        BaseApplication.getSecondLevelCacheKit().remove(Constant.GOOD_DETAIL_INFO);
        BaseApplication.getSecondLevelCacheKit().remove(Constant.MERCHANT_CHOOSE_ORDER_STATE_DESC);

        BaseApplication.getSecondLevelCacheKit().remove(Constant.MERCHANT_CHOOSE_ORDER_STATE);
        BaseApplication.getSecondLevelCacheKit().remove(Constant.MERCHANT_CER_PAYNOW_URL);
        BaseApplication.getSecondLevelCacheKit().remove(Constant.MERCAHNT_PLATFORM_ACCOUNT_DETAIL_INFO);
        BaseApplication.getSecondLevelCacheKit().remove(Constant.MERCAHNT_CREATE_ORDER_DETAIL_INFO_NEW);

        BaseApplication.getSecondLevelCacheKit().remove(Constant.MERCHANT_ORDER_CREATE_ID);
        BaseApplication.getSecondLevelCacheKit().remove(Constant.MERCAHNT_CREATE_ORDER_DETAIL_INFO);

    }
}
