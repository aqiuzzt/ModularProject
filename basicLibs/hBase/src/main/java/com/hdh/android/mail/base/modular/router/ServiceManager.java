package com.hdh.android.mail.base.modular.router;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hdh.android.mail.base.modular.provider.IAccountInfoProvider;
import com.hdh.android.mail.base.modular.provider.IMainProvider;
import com.hdh.android.mail.base.modular.provider.IMeProvider;
import com.hdh.android.mail.base.modular.provider.IMarketProvider;
import com.hdh.android.mail.base.modular.provider.IHomesProvider;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/20 14:34
 */
public class ServiceManager {

    @Autowired
    IMeProvider meProvider;
    @Autowired
    IAccountInfoProvider accountInfoProvider;


    @Autowired
    IMainProvider mainProvider;


    @Autowired
    IMarketProvider marketProvider;

    @Autowired
    IHomesProvider homesProvider;


    public ServiceManager() {
        ARouter.getInstance().inject(this);
    }

    private static final class ServiceManagerHolder {
        private static final ServiceManager instance = new ServiceManager();
    }

    public static ServiceManager getInstance() {
        return ServiceManagerHolder.instance;
    }



    public IMeProvider getMeProvider() {
        return meProvider;
    }

    public IAccountInfoProvider getAccountInfoProvider() {
        return accountInfoProvider;
    }


    /**
     * 也可以使用自动注入，这里是手动发现并且调用
     *
     * @return
     */
//    public IFontProvider getFontProvider() {
////        return fontProvider != null ? fontProvider : (fontProvider = ((IFontProvider) MyARouter.newInstance(IFontProvider.FONT_MAIN_SERVICE).navigation()));
//
//        return fontProvider;
//    }

    public IMarketProvider getMarketProvider() {
        return marketProvider;
    }

    public IHomesProvider getHomesProvider() {
        return homesProvider;
//        homesProvider = ((IHomesProvider) MyARouter.newInstance(IHomesProvider.HOMES_MAIN_SERVICE).navigation());
//        return homesProvider != null ? homesProvider : (homesProvider = ((IHomesProvider) MyARouter.newInstance(IHomesProvider.HOMES_MAIN_SERVICE).navigation()));
    }

}
