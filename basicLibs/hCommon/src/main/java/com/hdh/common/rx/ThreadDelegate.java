package com.hdh.common.rx;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;


/**
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/28 11:28
 * @param <T>
 */
public interface ThreadDelegate<T> {
    /**
     * 在子线程中执行
     *
     * @return
     */
    @CheckResult @NonNull
    T doInBackground();

    @UiThread
    void handleResult(T result);
}
