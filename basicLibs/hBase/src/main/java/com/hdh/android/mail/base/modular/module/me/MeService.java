package com.hdh.android.mail.base.modular.module.me;

import android.support.v4.app.Fragment;

import com.hdh.android.mail.base.modular.provider.IMeProvider;
import com.hdh.android.mail.base.modular.router.ModuleManager;
import com.hdh.android.mail.base.modular.router.ServiceManager;

/**
 * 管理调用Provider的某一个特定模块的Service
 * <p>
 * <br></> home
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/20 14:41
 */
public class MeService {

    private static boolean hasMeModule() {
        return ModuleManager.getInstance().hasModule(IMeProvider.ME_MAIN_SERVICE);
    }

    public static Fragment getMeFrgment(Object... args) {
        if(!hasMeModule()) return null;
        return ServiceManager.getInstance().getMeProvider().newInstance(args);
    }


}
