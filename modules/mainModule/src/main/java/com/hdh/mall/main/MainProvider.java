package com.hdh.mall.main;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hdh.android.mail.base.modular.provider.IMainProvider;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gzhjhc.com">Contact me.</a>
 * @since 2017/12/6 14:40
 */
@Route(path = IMainProvider.MAIN_MAIN_SERVICE)
public class MainProvider implements IMainProvider {
    @Override
    public void init(Context context) {

    }
}
