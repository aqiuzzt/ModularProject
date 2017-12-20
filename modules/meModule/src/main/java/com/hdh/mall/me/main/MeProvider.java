package com.hdh.mall.me.main;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hdh.android.mail.base.modular.provider.IMeProvider;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gzhjhc.com">Contact me.</a>
 * @since 2017/12/6 14:42
 */
@Route(path = IMeProvider.ME_MAIN_SERVICE)
public class MeProvider implements IMeProvider {
    @Override
    public Fragment newInstance(Object... args) {

        return PersonFragment.getInstance();
    }

    @Override
    public void init(Context context) {

    }
}
