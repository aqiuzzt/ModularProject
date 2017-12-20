package com.hdh.common.http.builder;

import android.util.Log;

import com.hdh.common.http.HTTP;
import com.hdh.common.http.annotation.Method;
import com.hdh.common.http.util.HttpConstants;

import java.util.Locale;

import io.reactivex.Observable;
import okhttp3.Request;


/**
 * 请求路径封装
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/21 20:23
 */
public class PathRequestBuilder extends RequestBuilder {
    public PathRequestBuilder(@Method String method) {
        super(method);
    }

    @Override
    public RequestBuilder path(String path) {
        api = path;
        return super.path(path);
    }

    @Override
    protected Request getRequest() {
        if (tag == null) tag = path;
        String url = url();
        if (HttpConstants.DEBUG) {
            Log.d(HttpConstants.TAG, url);
        }
        return new Request.Builder()
                .get()
                .url(url)
                .tag(tag)
                .build();
    }

    @Override
    protected Observable<String> buildRetrofitRequest() {
        if (this.path != null) {
            return HTTP.getService().path(path);
        } else {
            return Observable.error(new IllegalArgumentException("the path arguments is null"));
        }
    }

    @Override
    String url() {
        return String.format(Locale.getDefault(), "%s/%s", HTTP.getHttpUrl(), path);
    }
}
