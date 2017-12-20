package com.hdh.android.mail.base.modular.module.market;

import android.support.v4.app.Fragment;

import com.hdh.android.mail.base.modular.provider.IMarketProvider;
import com.hdh.android.mail.base.modular.router.ModuleManager;
import com.hdh.android.mail.base.modular.router.ServiceManager;

/**
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/20 14:34
 */
public class MarketService {
    private static boolean hasMarketModule() {
        return ModuleManager.getInstance().hasModule(IMarketProvider.MARKET_MAIN_SERVICE);
    }

    public static Fragment getMarketFragment(Object... args) {
        if (!hasMarketModule()) return null;
        return ServiceManager.getInstance().getMarketProvider().newInstance(args);
    }
}
