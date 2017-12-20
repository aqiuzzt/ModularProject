package com.hdh.android.mail.base.http;

import com.hdh.common.http.handler.DataCallback;

import okhttp3.Call;


/**
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/23 18:19
 */
public class DefaultDataCallback implements DataCallback {
    @Override public void onSuccess(int id, String body) {
        
    }

    @Override public void onFailed(int id, Call call, Exception e) {

    }
}
