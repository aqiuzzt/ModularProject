package com.hdh.common.http.handler;

import android.support.annotation.UiThread;

import okhttp3.Call;

/**
 * Desc: 数据回调基类
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/21 20:24
 */
public interface DataCallback {

    /**
     * 请求成功,在UI线程
     */
    @UiThread
    void onSuccess(int id, String body);

    /**
     * 请求失败,在UI线程
     */
    @UiThread
    void onFailed(int id, Call call, Exception e);
}
