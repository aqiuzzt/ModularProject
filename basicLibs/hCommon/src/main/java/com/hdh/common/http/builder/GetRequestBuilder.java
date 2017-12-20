package com.hdh.common.http.builder;

import android.net.Uri;
import android.util.Log;

import com.hdh.common.http.HTTP;
import com.hdh.common.http.annotation.Method;
import com.hdh.common.http.util.HttpConstants;

import java.net.URLEncoder;

import io.reactivex.Observable;
import okhttp3.Request;

/**
 * 描述: GET请求
 * 
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/21 20:23
 */
public class GetRequestBuilder extends RequestBuilder {

    public GetRequestBuilder(@Method String method) {
        super(method);
    }

    @Override
    protected Request getRequest() {
        if (tag == null) this.tag = api;
        StringBuilder builder = new StringBuilder(url());
        if (sysParams != null || bizParams != null)
            builder.append("?");
        if (sysParams != null)
            builder.append(HttpConstants.SYS_PARAM).append("=").append(URLEncoder.encode(sysParams));
        if (sysParams != null && bizParams != null) {
            builder.append("&").append(HttpConstants.BIZ_PARAM).append("=");
            String encode = URLEncoder.encode(bizParams);
            if (duplicateEncode) { //2次编码
                encode = Uri.encode(encode);
            }
            builder.append(encode);
        }
        String url = builder.toString();
        if (HttpConstants.DEBUG) {
            Log.d(HttpConstants.TAG, url);
        }
        Request request = new Request.Builder()
                .get()
                .url(url)
                .tag(tag)
                .build();
        return request;
    }

    @Override protected Observable<String> buildRetrofitRequest() {
        String encodeBiz = URLEncoder.encode(bizParams);
        return HTTP.getService().get(URLEncoder.encode(sysParams), duplicateEncode ? URLEncoder.encode(encodeBiz) : encodeBiz);
    }
}
