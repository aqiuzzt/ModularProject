package com.hdh.mall.market.utils;

import com.hdh.android.mail.base.bean.AddressBean;
import com.hdh.android.mail.base.bean.GoodsDetailBean;
import com.hdh.android.mail.base.bean.OrderPayAppDTO;
import com.hdh.android.mail.base.bean.SaleAttributesBean;
import com.hdh.android.mail.base.bean.ShoppingCartBean;
import com.hdh.android.mail.base.utils.ProfileStrorageUtil;
import com.hdh.common.http.HTTP;
import com.hdh.common.http.builder.RequestBuilder;
import com.hdh.common.util.HSON;
import com.hdh.common.util.MD5;
import com.hdh.mall.hconfig.URLName;
import com.hdh.mall.market.bean.CreateOrderRequestBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gzhjhc.com">Contact me.</a>
 * @since 2017/12/4 10:57
 */

public class HttpRequestManager implements URLName {
    private static final String LOG_TAG = "HttpRequestManager";

    public HttpRequestManager() {
        super();
    }




    public static Observable<String> queryBannarByChannal(int type) {
        return HTTP.get().api("")
                .addParam("bannerType", type)
                .addParam("version", "1.0")
                .setCommandKey("command")
                .sign().rxRequest();
    }


    /**
     * 上报banner统计信息
     *
     * @param infoId 需统计的业务id
     * @param type   数据类型,1: 普通URL跳转, 2:跳转到商品详情, 3:跳转到商家, 4:无
     * @return
     */
    public static Observable<String> reportBannerStatistics(String infoId, int type) {
        return HTTP.post().api("")
                .addParam("currentAccount", ProfileStrorageUtil.getAccountId())
                .addParam("infoId", infoId)
                .addParam("type", type)
                .setHierarchy(1)
                .setCommandKey("command")
                .sign()
                .rxRequest();

    }


    /**
     * 根据产品类型获取产品列表
     * <p>
     * 默认每次搜10个商品
     *
     * @param categoryId
     * @return
     */
    public static Observable<String> requestGoodsListRx(String since, int pageSize, String categoryId) {
        return HTTP.get().api("")
                .addParam("accountId", ProfileStrorageUtil.getAccountId())
                .addParam("since", since)
                .addParam("limit", pageSize)
                .addParam("categoryIds", new String[]{categoryId})
                .sign().rxRequest();
    }







}
