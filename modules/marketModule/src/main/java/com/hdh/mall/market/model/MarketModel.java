package com.hdh.mall.market.model;


import com.hdh.mall.market.contract.MarketContract;
import com.hdh.mall.market.utils.HttpRequestManager;

import io.reactivex.Observable;


/**
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/12/4 11:01
 */
public class MarketModel implements MarketContract.Model {
    @Override
    public Observable<String> getMarketBanner(int type) {
        return HttpRequestManager.queryBannarByChannal(type);
    }

    @Override
    public Observable<String> reportBannerStatistics(String infoId, int type) {
        return HttpRequestManager.reportBannerStatistics(infoId, type);
    }

    @Override
    public Observable<String> requestGoodsList(String since, int pageSize, String categoryId) {
        return HttpRequestManager.requestGoodsListRx(since, pageSize, categoryId);
    }
}
