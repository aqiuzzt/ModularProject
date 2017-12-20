package com.hdh.android.mail.base.modular.provider;

import android.support.v4.app.Fragment;

/**
 * fragment 相关页面provider
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/20 14:34
 */
public interface IFragmentProvider extends IBaseProvider {

    Fragment newInstance(Object... args);

}
