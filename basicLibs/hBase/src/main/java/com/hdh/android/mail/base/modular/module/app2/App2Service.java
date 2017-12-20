package com.hdh.android.mail.base.modular.module.app2;

import com.hdh.android.mail.base.modular.provider.IAppProvider;
import com.hdh.android.mail.base.modular.router.ModuleManager;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/20 14:34
 */
public class App2Service {
    private static boolean hasModule() {
        return ModuleManager.getInstance().hasModule(IAppProvider.APP_MAIN_SERVICE);
    }

}
