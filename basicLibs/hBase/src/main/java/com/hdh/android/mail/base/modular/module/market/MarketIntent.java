package com.hdh.android.mail.base.modular.module.market;

import com.hdh.android.mail.base.bean.GoodsBean;
import com.hdh.android.mail.base.bean.GoodsCategoryBean;
import com.hdh.android.mail.base.bean.GoodsDetailBean;
import com.hdh.android.mail.base.bean.OrderPayAppDTO;
import com.hdh.android.mail.base.bean.SaleAttributesBean;
import com.hdh.android.mail.base.bean.ShoppingCartBean;
import com.hdh.android.mail.base.modular.config.ParmaBundle;
import com.hdh.android.mail.base.modular.provider.IMarketProvider;
import com.hdh.android.mail.base.modular.router.ModuleManager;
import com.hdh.android.mail.base.modular.router.MyARouter;

import java.util.ArrayList;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/20 14:34
 */
public class MarketIntent {


    private static boolean hasModule() {
        return ModuleManager.getInstance().hasModule(IMarketProvider.MARKET_MAIN_SERVICE);
    }


    public static void gotoCreateOrderWithShoppingCartItems(ArrayList<ShoppingCartBean> list) {
        ParmaBundle parmaBundle = new ParmaBundle();
        parmaBundle.put("shopping_cart_list", list);
        MyARouter.newInstance(IMarketProvider.MARKET_ACT_CREATE_ORDER)
                .withBundle(parmaBundle)
                .navigation();

    }

    public static void gotoCreateOrderWithGoodsDetailBean(GoodsDetailBean bean) {
        ParmaBundle parmaBundle = new ParmaBundle();
        parmaBundle.put("goodsDetailBean", bean);
        MyARouter.newInstance(IMarketProvider.MARKET_ACT_CREATE_ORDER)
                .withBundle(parmaBundle)
                .navigation();

    }

    public static void gotoCreateOrderWithSelectSku(GoodsDetailBean bean, SaleAttributesBean saleAttributesBean, long count) {
        ParmaBundle parmaBundle = new ParmaBundle();
        parmaBundle.put("goodsDetailBean", bean);
        parmaBundle.put("saleAttributesBean", saleAttributesBean);
        parmaBundle.put("count", count);
        MyARouter.newInstance(IMarketProvider.MARKET_ACT_CREATE_ORDER)
                .withBundle(parmaBundle)
                .navigation();

    }


    public static void gotoGoodCategory() {
        MyARouter.newInstance(IMarketProvider.MARKET_ACT_GOOG_CATEGORY)
                .navigation();
    }

    public static void gotoGoodDetail(String id) {
        ParmaBundle parmaBundle = new ParmaBundle();
        GoodsBean bean = new GoodsBean();
        bean.id = id;
        parmaBundle.put("goodsBean", bean);
        MyARouter.newInstance(IMarketProvider.MARKET_ACT_GOOD_DETAIL)
                .withBundle(parmaBundle)
                .navigation();
    }

    public static void gotoGoodsList(GoodsCategoryBean category) {
        ParmaBundle parmaBundle = new ParmaBundle();
        parmaBundle.put("categoryId", category);
        MyARouter.newInstance(IMarketProvider.MARKET_ACT_GOOD_LIST)
                .withBundle(parmaBundle)
                .navigation();
    }


    public static void gotoMarketSearch() {
        MyARouter.newInstance(IMarketProvider.MARKET_ACT_MARKET_SEARCH)
                .navigation();
    }

    public static void gotoMarketType(GoodsCategoryBean category) {
        ParmaBundle parmaBundle = new ParmaBundle();
        parmaBundle.put("categoryId", category);
        MyARouter.newInstance(IMarketProvider.MARKET_ACT_MARKET_TYPE)
                .withBundle(parmaBundle)
                .navigation();
    }

    public static void gotoOrderPayment(OrderPayAppDTO orderPayAppDTO) {
        ParmaBundle parmaBundle = new ParmaBundle();
        parmaBundle.put("order", orderPayAppDTO);
        MyARouter.newInstance(IMarketProvider.MARKET_ACT_ORDER_PAYMENT)
                .withBundle(parmaBundle)
                .navigation();
    }

    public static void gotoOrderPayment(String payNo, long totalCost) {
        OrderPayAppDTO orderPayAppDTO = new OrderPayAppDTO();
        orderPayAppDTO.payNo = payNo;
        orderPayAppDTO.totalCost = totalCost + "";
        ParmaBundle parmaBundle = new ParmaBundle();
        parmaBundle.put("order", orderPayAppDTO);
        MyARouter.newInstance(IMarketProvider.MARKET_ACT_ORDER_PAYMENT)
                .withBundle(parmaBundle)
                .navigation();
    }

    public static void gotoShoppingCart(){
        MyARouter.newInstance(IMarketProvider.MARKET_ACT_SHOPPING_CART)
                .navigation();
    }


}
