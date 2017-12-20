package com.hdh.android.mail.base.modular.module.main;

import com.hdh.android.mail.base.modular.provider.IMainProvider;
import com.hdh.android.mail.base.modular.router.ModuleManager;
import com.hdh.android.mail.base.modular.router.MyARouter;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gzhjhc.com">Contact me.</a>
 * @since 2017/12/6 11:52
 */

public class MainIntent {

    private static boolean hasModule3() {
        return ModuleManager.getInstance().hasModule(IMainProvider.MAIN_MAIN_SERVICE);
    }

    public static void gotoMianActivity(){
        MyARouter.newInstance(IMainProvider.MIAN_ACT_MAIN_HOME)
                .navigation();
    }
}
