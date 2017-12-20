package com.hdh.android.mail.base.inte;

import android.support.annotation.NonNull;

/**
 * 
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/23 10:56
 * @param <T>
 */
public interface IFragmentView<T extends IPresenter> extends IActivityView {

    void setPresenter(@NonNull T presenter);




}
