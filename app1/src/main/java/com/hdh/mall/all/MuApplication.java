package com.hdh.mall.all;



import com.alibaba.android.arouter.launcher.ARouter;
import com.hdh.android.mail.base.*;
import com.hdh.android.mail.base.modular.config.ModuleOptions;
import com.hdh.android.mail.base.modular.provider.IHomesProvider;
import com.hdh.android.mail.base.modular.provider.IMainProvider;
import com.hdh.android.mail.base.modular.provider.IMarketProvider;
import com.hdh.android.mail.base.modular.provider.IMeProvider;
import com.hdh.android.mail.base.modular.router.ModuleManager;


/**
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/8 19:02
 */

public class MuApplication extends BaseApplication {





    @Override
    public void onCreate() {
        super.onCreate();
        initARouter();
    }



    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    private void initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
            ARouter.printStackTrace();
        }
        ARouter.init(this);
        ModuleOptions.ModuleBuilder builder = new ModuleOptions.ModuleBuilder(this)
                .addModule(IFontProvider.FONT_MAIN_SERVICE, IFontProvider.FONT_MAIN_SERVICE)
                .addModule(IHomesProvider.HOMES_MAIN_SERVICE,IHomesProvider.HOMES_MAIN_SERVICE)
                .addModule(IMainProvider.MAIN_MAIN_SERVICE,IMainProvider.MAIN_MAIN_SERVICE)
                .addModule(IMarketProvider.MARKET_MAIN_SERVICE,IMarketProvider.MARKET_MAIN_SERVICE)
                .addModule(IMeProvider.ME_MAIN_SERVICE,IMeProvider.ME_MAIN_SERVICE)
                .addModule(IMerchantProvider.MERCHANT_MAIN_SERVICE,IMerchantProvider.MERCHANT_MAIN_SERVICE);


        ModuleManager.getInstance().init(builder.build());
    }


}
