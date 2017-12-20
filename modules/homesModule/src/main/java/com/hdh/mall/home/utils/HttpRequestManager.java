package com.hdh.mall.home.utils;

import android.support.annotation.WorkerThread;
import android.text.TextUtils;

import com.hdh.android.mail.base.BaseApplication;
import com.hdh.android.mail.base.utils.ProfileStrorageUtil;
import com.hdh.common.http.HTTP;
import com.hdh.common.http.builder.RequestBuilder;
import com.hdh.common.http.util.HttpConstants;
import com.hdh.common.http.util.UploadException;
import com.hdh.common.util.HSON;
import com.hdh.common.util.MD5;
import com.hdh.mall.hconfig.URLName;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gzhjhc.com">Contact me.</a>
 * @since 2017/12/5 08:40
 */

public class HttpRequestManager implements URLName {
    private static final String LOG_TAG = "HttpRequestManager";



    public static Observable<String> queryAccountFinance(String accountId) {
        return HTTP.get().api(URLName.HOME_ANNOUNCEMENT_DETAIL)
                .addParam("accountId", accountId)
                .setHierarchy(0)
                .sign().rxRequest();
    }



    public static Observable<String> queryAccountFinanceConsum(String accountId) {
        return HTTP.get().api(URLName.HOME_ANNOUNCEMENT_DETAIL)
                .addParam("accountId", accountId)
                .setHierarchy(0)
                .sign().rxRequest();
    }

    public static Observable<String> queryRedStarValue(String[] dates) {
        return HTTP.get().api(URLName.HOME_ANNOUNCEMENT_DETAIL)
                .addParam("enableDates", dates)
                .addSignMap("enableDates", HSON.toJson(dates))
                .setHierarchy(0).sign().rxRequest();
    }


    public static Observable<String> queryAccountFinancePerformance(String accountId) {
        return HTTP.get().api(URLName.HOME_ANNOUNCEMENT_DETAIL)
                .addParam("accountId", accountId)
                .setHierarchy(0)
                .sign().rxRequest();
    }

    public static Observable<String> queryFinancePerformance(String accountId, String type) {
        return HTTP.get().api(URLName.HOME_ANNOUNCEMENT_DETAIL)
                .addParam("accountId", accountId)
                .addParam("type", type)
                .setHierarchy(1)
                .sign().rxRequest();
    }

    public static Observable<String> queryAnnouncementList(String parentId, int pageIndex, int pageSize) {
        return HTTP.get().api("")
                .addParam("parentId", parentId)
                .addParam("pageIndex", pageIndex)
                .addParam("pageSize", pageSize)
                .setHierarchy(0)
                .sign()
                .rxRequest();
    }

    /**
     * 广告信息查询
     *
     * @param version
     * @param bannerType
     * @return
     */
    public static Observable<String> queryBannerInfo(String version, int bannerType) {
        return HTTP.get().api("").
                addParam("version", version)
                .addParam("bannerType", bannerType)
                .setCommandKey("command").sign().rxRequest();
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



}
