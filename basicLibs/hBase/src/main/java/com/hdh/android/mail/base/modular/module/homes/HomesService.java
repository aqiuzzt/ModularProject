package com.hdh.android.mail.base.modular.module.homes;

import android.support.v4.app.Fragment;

import com.hdh.android.mail.base.modular.provider.IHomesProvider;
import com.hdh.android.mail.base.modular.router.ModuleManager;
import com.hdh.android.mail.base.modular.router.ServiceManager;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gzhjhc.com">Contact me.</a>
 * @since 2017/12/6 13:23
 */

public class HomesService {

    private static boolean hasHomeModule() {
        return ModuleManager.getInstance().hasModule(IHomesProvider.HOMES_MAIN_SERVICE);
    }


    public static Fragment getHomesFrgment(Object... args) {
        if(!hasHomeModule()) return null;
        return ServiceManager.getInstance().getHomesProvider().newInstance(args);
    }
}


