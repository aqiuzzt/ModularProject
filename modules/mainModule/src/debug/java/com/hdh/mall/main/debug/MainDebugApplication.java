package com.hdh.mall.main.debug;


import com.alibaba.android.arouter.launcher.ARouter;
import com.hdh.android.mail.base.BaseApplication;
import com.hdh.android.mail.base.BuildConfig;
import com.hdh.android.mail.base.modular.config.ModuleOptions;
import com.hdh.android.mail.base.modular.provider.IMainProvider;
import com.hdh.android.mail.base.modular.router.ModuleManager;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gzhjhc.com">Contact me.</a>
 * @since 2017/11/29 18:20
 */

public class MainDebugApplication extends BaseApplication {
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
                .addModule(IMainProvider.MAIN_MAIN_SERVICE, IMainProvider.MAIN_MAIN_SERVICE);
        ModuleManager.getInstance().init(builder.build());
    }
}
