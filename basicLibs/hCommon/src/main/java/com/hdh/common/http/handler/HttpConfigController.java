package com.hdh.common.http.handler;

import android.content.Context;

import java.util.Map;

/**
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/21 20:26
 */
public abstract class HttpConfigController {
    /**
     * 请求成功了之后可能要根据结果进行一些统一的处理
     *
     * @param result 请求结果
     * @return {@code true} 表示继续执行{@link com.hdh.common.http.builder.BaseResponse#onSuccess(int, String)} <br> {@code false} 表示不再继续往下执行
     */
    public abstract boolean unitHandle(String result);

    /**
     * 前置检查是否存在网络
     *
     * @return {@code false} 继续向下执行 {@code true} 阻断执行
     */
    public abstract boolean networkCheck();


    public abstract Map<String, Object> unitParams();

    public abstract String session();

    public abstract long diffTimestamp();

    public abstract void showFailedTip(int failedCode);

    public abstract Context context();
}
