package com.hdh.android.mail.base;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;

import com.hdh.android.mail.base.db.city.CityDBManager;
import com.hdh.android.mail.base.manager.ActivityManager;
import com.hdh.android.mail.base.manager.UserManager;
import com.hdh.android.mail.base.utils.ProfileStrorageUtil;
import com.hdh.common.cache.SecondLevelCache;
import com.hdh.common.http.HTTP;
import com.hdh.common.http.handler.HttpConfigController;
import com.hdh.common.util.LogUtil;
import com.hdh.common.util.view.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import org.litepal.LitePal;

import java.util.Map;



/**
 * 基本 Application
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/20 14:34
 */
public class BaseApplication extends MultiDexApplication {
    private static long diffTimestamp = -1L;
    private static BaseApplication mBaseApplication;
    private static ActivityManager mActivityManager = null;
    private static UserManager userManager = null;
    private static SecondLevelCache secondLevelCacheKit;

    public static BaseApplication get() {
        return mBaseApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBaseApplication = this;
        mActivityManager = ActivityManager.getmActivityManger();
        userManager = UserManager.getInstance();
        secondLevelCacheKit = SecondLevelCache.getInstance(this);
        ProfileStrorageUtil.init(this);
        initHttp(this);
        LogUtil.debugEnabled("HDH_LOG", BuildConfig.DEBUG);
        ToastUtil.init(this);
        //初始化LitePal数据库
        LitePal.initialize(this);
        CityDBManager.getManager().initAsync(this);

    }

    public static ActivityManager getActivityManager() {
        return mActivityManager;
    }

    public static UserManager getUserManager() {
        return userManager;
    }

    /**
     * 服务器时间戳与系统时间的间隔
     *
     * @return
     */
    public static long getDiffTimestamp() {
        if (diffTimestamp == -1) {
            diffTimestamp = ProfileStrorageUtil.getDiffTimestamp();
        }
        return diffTimestamp;
    }

    public static void setDiffTimestamp(long diff) {
        diffTimestamp = diff;
    }

    public static long getServerTimestamp() {
        return System.currentTimeMillis() + getDiffTimestamp();
    }

    public static SecondLevelCache getSecondLevelCacheKit() {
        return secondLevelCacheKit;
    }


    /**
     * 初始化网络请求
     *
     * @param appContext
     */
    public static void initHttp(final Context appContext) {
        HttpConfigController controller = new HttpConfigController() {
            @Override
            public boolean unitHandle(String body) {
                return true;
            }

            @Override
            public boolean networkCheck() {
                return false;
            }

            @Override
            public Map<String, Object> unitParams() {
                return null;
            }

            @Override
            public String session() {
                return ProfileStrorageUtil.getAccessToken();
            }

            @Override
            public long diffTimestamp() {
                return getDiffTimestamp();
            }

            @Override
            public void showFailedTip(int code) {
            }

            @Override
            public Context context() {
                return appContext;
            }
        };

        HTTP.setConfigController(controller);
    }


    static {
        ClassicsHeader.REFRESH_HEADER_PULLDOWN = "下拉可以刷新";
        ClassicsHeader.REFRESH_HEADER_REFRESHING = "正在刷新...";
        ClassicsHeader.REFRESH_HEADER_LOADING = "正在加载...";
        ClassicsHeader.REFRESH_HEADER_RELEASE = "释放立即刷新";
        ClassicsHeader.REFRESH_HEADER_FINISH = "刷新完成";
        ClassicsHeader.REFRESH_HEADER_FAILED = "刷新失败";

        ClassicsFooter.REFRESH_FOOTER_PULLUP = "上拉加载更多";
        ClassicsFooter.REFRESH_FOOTER_RELEASE = "释放立即加载";
        ClassicsFooter.REFRESH_FOOTER_LOADING = "正在加载...";
        ClassicsFooter.REFRESH_FOOTER_FINISH = "加载完成";
        ClassicsFooter.REFRESH_FOOTER_FAILED = "加载失败";
        ClassicsFooter.REFRESH_FOOTER_ALLLOADED = "全部加载完成";
    }

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.color_bg_yellow, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate);//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
            }
        });
    }

}
