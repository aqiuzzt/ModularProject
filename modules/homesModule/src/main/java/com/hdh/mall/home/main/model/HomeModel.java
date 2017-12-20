package com.hdh.mall.home.main.model;



import com.hdh.mall.home.main.contract.HomeContract;
import com.hdh.mall.home.utils.HttpRequestManager;

import io.reactivex.Observable;

/**
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/12/18 15:42
 */
public class HomeModel implements HomeContract.Model {


    @Override
    public Observable<String> queryAccountFinance(String accountId) {
        return HttpRequestManager.queryAccountFinance(accountId);
    }

    @Override
    public Observable<String> queryAccountFinanceConsum(String accountId) {
        return HttpRequestManager.queryAccountFinanceConsum(accountId);
    }

    @Override
    public Observable<String> queryGlodIntegralValue(String... dates) {
        return HttpRequestManager.queryRedStarValue(dates);
    }

    @Override
    public Observable<String> queryAccountFinancePerformance(String accountId) {
        return HttpRequestManager.queryAccountFinancePerformance(accountId);
    }

    @Override
    public Observable<String> queryAnnouncementList(String parentId, int pageIndex, int pageSize) {
        return HttpRequestManager.queryAnnouncementList(parentId, pageIndex, pageSize);
    }

    @Override
    public Observable<String> queryBannerInfo(String version, int bannerType) {
        return HttpRequestManager.queryBannerInfo(version, bannerType);
    }

    @Override
    public Observable<String> queryFinancePerformance(String accountId, String type) {
        return HttpRequestManager.queryFinancePerformance(accountId,type);
    }

}
