package com.hdh.common.http.handler;

import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;

/**
 * 数据回调扩展
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/21 20:25
 */
public interface DataCallbackEx extends DataCallback {


    /**
     * 这个方法运行在UI线程
     * <br>请求开始执行前
     */
    @UiThread
    void onStart(int id);

    /**
     * 执行在异步线程,如果需要在返回结果之后进行其它操作可以在这里处理
     */
    @WorkerThread
    void resultOnWorkThread(int id, String result);

}
