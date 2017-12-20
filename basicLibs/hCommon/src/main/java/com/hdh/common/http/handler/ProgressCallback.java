package com.hdh.common.http.handler;


/**
 * 进度回调
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/21 20:26
 */
public interface ProgressCallback {

    void onProgress(long currentSize, long totalSize);
}
