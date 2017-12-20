package com.hdh.android.mail.base.modular.module.app;

import com.hdh.android.mail.base.modular.router.ModuleManager;
import com.hdh.android.mail.base.modular.provider.IAppProvider;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/20 14:34
 */
public class AppService {
    private static boolean hasModule() {
        return ModuleManager.getInstance().hasModule(IAppProvider.APP_MAIN_SERVICE);
    }

}
