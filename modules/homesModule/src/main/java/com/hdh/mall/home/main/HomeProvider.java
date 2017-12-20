package com.hdh.mall.home.main;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hdh.android.mail.base.modular.provider.IHomesProvider;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gzhjhc.com">Contact me.</a>
 * @since 2017/12/19 14:04
 */
@Route(path = IHomesProvider.HOMES_MAIN_SERVICE)
public class HomeProvider implements IHomesProvider {
    @Override
    public Fragment newInstance(Object... args) {
        return HomePageFragment.getInstance();
    }

    @Override
    public void init(Context context) {

    }
}
