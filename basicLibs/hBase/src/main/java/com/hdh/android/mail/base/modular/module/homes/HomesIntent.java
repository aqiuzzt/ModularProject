package com.hdh.android.mail.base.modular.module.homes;

import com.hdh.android.mail.base.bean.OrderPayAppDTO;
import com.hdh.android.mail.base.modular.config.ParmaBundle;
import com.hdh.android.mail.base.modular.provider.IHomesProvider;
import com.hdh.android.mail.base.modular.router.MyARouter;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/20 14:34
 */
public class HomesIntent {



    public static void gotoAnnounvement(String parentId) {
        ParmaBundle parmaBundle = new ParmaBundle();
        parmaBundle.put("parentId", parentId);
        MyARouter.newInstance(IHomesProvider.HOMES_ACT_ANNOUNCEMENT)
                .withBundle(parmaBundle)
                .navigation();

    }

    public static void gotoConsumptionIntegral() {
        MyARouter.newInstance(IHomesProvider.HOMES_ACT_CONSUMPTION_INTEGRAL)
                .navigation();
    }

    public static void gotoConsumtionRecord(ParmaBundle parmaBundle) {
        MyARouter.newInstance(IHomesProvider.HOMES_ACT_CONSUMPTION_RECORD)
                .withBundle(parmaBundle)
                .navigation();
    }

    /**
     * "categoryId", category(IntegralCategoryBean)
     *
     * @param parmaBundle
     */
    public static void gotoIntegralRecord(ParmaBundle parmaBundle) {
        MyARouter.newInstance(IHomesProvider.Homes_ACT_INTEGRAL_RECORD)
                .withBundle(parmaBundle)
                .navigation();
    }


    public static void gotoBuyBack() {
        MyARouter.newInstance(IHomesProvider.HOMES_ACT_BUY_BACK)
                .navigation();
    }

    public static void gotoBuyBackRecord(long total) {
        ParmaBundle parmaBundle = new ParmaBundle();
        parmaBundle.put("total", total);

        MyARouter.newInstance(IHomesProvider.HOMES_ACT_BUY_BACK_RECORD)
                .withBundle(parmaBundle)
                .navigation();
    }

    /**
     * "withdraw", dto (BuyBackRecord)
     *
     * @param parmaBundle
     */
    public static void gotoBuyBackRecordDetail(ParmaBundle parmaBundle) {
        MyARouter.newInstance(IHomesProvider.HOMES_ACT_BUY_BACK_RECORD_DETAIL)
                .withBundle(parmaBundle)
                .navigation();
    }


    public static void gotoShoppingConsumed(long total) {
        ParmaBundle parmaBundle = new ParmaBundle();
        parmaBundle.put("total", total);
        MyARouter.newInstance(IHomesProvider.HOMES_ACT_SHOPPING_CONSUMED)
                .withBundle(parmaBundle)
                .navigation();
    }

    public static void gotoStimulateStress(long total) {
        ParmaBundle parmaBundle = new ParmaBundle();
        parmaBundle.put("total", total);
        MyARouter.newInstance(IHomesProvider.HOMES_ACT_STIMULATE_STRESS_CASH)
                .withBundle(parmaBundle)
                .navigation();
    }




    public static void gotoShoppingPea() {
        MyARouter.newInstance(IHomesProvider.HOMES_ACT_SHOPPING_PEA)
                .navigation();
    }


    public static void gotoRedstarDetail() {
        MyARouter.newInstance(IHomesProvider.HOMES_ACT_REDSTAR_DETAIL)
                .navigation();
    }


    public static void gotoRecommend() {
        MyARouter.newInstance(IHomesProvider.HOMES_ACT_RECOMMEND)
                .navigation();
    }

    public static void gotoRecommendRecord() {
        MyARouter.newInstance(IHomesProvider.HOMES_ACT_RECOMMEND_RECORD)
                .navigation();
    }





    public static void gotoBecomVip() {
        MyARouter.newInstance(IHomesProvider.HOMES_ACT_BECOME_VIP)
                .navigation();
    }


    public static void gotoVipPayment(OrderPayAppDTO orderPayAppDTO) {
        ParmaBundle parmaBundle = new ParmaBundle();
        parmaBundle.put("order", orderPayAppDTO);
        MyARouter.newInstance(IHomesProvider.HOMES_ACT_VIP_PAYMENT)
                .withBundle(parmaBundle)
                .navigation();
    }

    public static void gotoVipPayment(String payNo, long totalCost) {
        OrderPayAppDTO orderPayAppDTO = new OrderPayAppDTO();
        orderPayAppDTO.payNo = payNo;
        orderPayAppDTO.totalCost = totalCost + "";
        ParmaBundle parmaBundle = new ParmaBundle();
        parmaBundle.put("order", orderPayAppDTO);
        MyARouter.newInstance(IHomesProvider.HOMES_ACT_VIP_PAYMENT)
                .withBundle(parmaBundle)
                .navigation();
    }


}
