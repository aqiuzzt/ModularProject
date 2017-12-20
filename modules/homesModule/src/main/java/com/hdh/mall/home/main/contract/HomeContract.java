package com.hdh.mall.home.main.contract;


import com.hdh.android.mail.base.bean.BannerBean;
import com.hdh.android.mail.base.inte.IFragmentView;
import com.hdh.android.mail.base.inte.IModel;
import com.hdh.android.mail.base.inte.IPresenter;
import com.hdh.mall.home.main.bean.AnnouncementBean;
import com.hdh.mall.home.main.bean.StatisticsFinanceBean;

import java.util.List;

import io.reactivex.Observable;


/**
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/12/5 09:26
 */
public interface HomeContract {

    interface View extends IFragmentView<Presenter> {
        void onQueryBannerCallback(List<BannerBean> data, String msg);

        void onQueryAccountFinance(String message);

        void onQueryAccountFinanceConsum(String consumValue, String message);


        void queryAccountFinancePerformance(String performanceValue, String message);


        void queryAnnouncementListCallback(List<AnnouncementBean> announcementBeen, String msg);

        void queryFinancePerformanceCallback(StatisticsFinanceBean statisticsFinanceBean, String msg);

        void reportBannerStatisticsCallback(String message);
    }

    interface Presenter extends IPresenter {

        /**
         * 获取广告信息
         */
        void queryBannerInfo();

        /**
         * 消费
         *
         * @param accountId
         */
        void queryAccountFinanceConsum(String accountId);


        void queryAccountFinance();

        /**
         * 业绩
         *
         * @param accountId
         */
        void queryAccountFinancePerformance(String accountId);

        void queryGoldValue(String... dates);




        /**
         * 获取系统公告
         * @param parentId
         * @param pageIndex
         * @param pageSize
         */
        void queryAnnouncementList(String parentId, int pageIndex, int pageSize);

        /**
         * 查询业绩 不区分角色
         *
         * @param accountId
         */
        void queryFinancePerformance(String accountId);

        /**
         * 广告统计
         * @param infoId 需统计的业务id
         * @param type 数据类型,1: 普通URL跳转, 2:跳转到商品详情, 3:跳转到商家, 4:无
         */
        void reportBannerStatistics(String infoId, int type);


    }

    interface Model extends IModel {

        Observable<String> queryAccountFinance(String accountId);

        Observable<String> queryAccountFinanceConsum(String accountId);

        Observable<String> queryGlodIntegralValue(String... dates);

        Observable<String> queryAccountFinancePerformance(String accountId);

        Observable<String> queryAnnouncementList(String parentId, int pageIndex, int pageSize);


        Observable<String> queryBannerInfo(String version, int bannerType);

        Observable<String> queryFinancePerformance(String accountId, String type);


    }
}
