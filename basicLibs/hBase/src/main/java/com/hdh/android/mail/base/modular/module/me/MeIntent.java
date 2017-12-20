package com.hdh.android.mail.base.modular.module.me;

import com.hdh.android.mail.base.modular.config.ParmaBundle;
import com.hdh.android.mail.base.modular.provider.IMeProvider;
import com.hdh.android.mail.base.modular.router.ModuleManager;
import com.hdh.android.mail.base.modular.router.MyARouter;

/**
 * 管理该我的 module的Activity跳转
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/20 14:34
 */
public class MeIntent {
    private static ParmaBundle parmaBundle = new ParmaBundle();


    private static boolean hasModule() {
        return ModuleManager.getInstance().hasModule(IMeProvider.ME_MAIN_SERVICE);
    }




    public static void gotoMyOrderList(int status) {
        parmaBundle.put("status", status);
        MyARouter.newInstance(IMeProvider.ME_ACT_MY_ORDER_LIST)
                .withBundle(parmaBundle)
                .navigation();
    }

    public static void gotoMyOrderList() {
        MyARouter.newInstance(IMeProvider.ME_ACT_MY_ORDER_LIST)
                .navigation();
    }




    public static void gotoAboutMe() {
        MyARouter.newInstance(IMeProvider.ME_ACT_ABOUT_ME)
                .navigation();
    }

    public static void gotoCustomerServices() {
        MyARouter.newInstance(IMeProvider.ME_ACT_CUSTOMER_SERVICE)
                .navigation();
    }

    public static void gotoMarketAnnouncement() {
        MyARouter.newInstance(IMeProvider.ME_ACT_MARKET_ACCOUNCEMENT)
                .navigation();
    }





    public static void gotoDeliveryPath(String deliveryId) {
        parmaBundle.put("deliveryId", deliveryId);
        MyARouter.newInstance(IMeProvider.ME_ACT_DELIVER_PATH)
                .withBundle(parmaBundle)
                .navigation();
    }


    /**
     * 传入"orderBean", bean
     *
     * @param bundle
     */
    public static void gotoOrderDetail(ParmaBundle bundle) {
        MyARouter.newInstance(IMeProvider.ME_ACT_ORDER_DETAIL)
                .withBundle(bundle)
                .navigation();
    }




}
