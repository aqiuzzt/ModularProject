package com.hdh.mall.home.debug;


import com.alibaba.android.arouter.launcher.ARouter;
import com.hdh.android.mail.base.BaseApplication;
import com.hdh.android.mail.base.BuildConfig;
import com.hdh.android.mail.base.modular.config.ModuleOptions;
import com.hdh.android.mail.base.modular.provider.IHomesProvider;
import com.hdh.android.mail.base.modular.router.ModuleManager;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gzhjhc.com">Contact me.</a>
 * @since 2017/11/29 18:20
 */

public class HomesDebugApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        initARouter();
    }

    private void initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
            ARouter.printStackTrace();
        }
        ARouter.init(this);
        ModuleOptions.ModuleBuilder builder = new ModuleOptions.ModuleBuilder(this)
                .addModule(IHomesProvider.HOMES_MAIN_SERVICE, IHomesProvider.HOMES_MAIN_SERVICE);
        ModuleManager.getInstance().init(builder.build());
    }
}
