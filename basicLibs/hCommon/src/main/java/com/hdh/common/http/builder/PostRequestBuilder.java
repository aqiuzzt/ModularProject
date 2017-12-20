package com.hdh.common.http.builder;


import com.hdh.common.http.HTTP;
import com.hdh.common.http.annotation.Method;
import com.hdh.common.http.util.HttpConstants;
import com.hdh.common.util.LogUtil;

import java.net.URLEncoder;

import io.reactivex.Observable;
import okhttp3.FormBody;
import okhttp3.Request;

/**
 * 描述: post请求
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/21 20:22
 */
public class PostRequestBuilder extends RequestBuilder {
    private static final String LOG_TAG = "PostRequestBuilder";

    public PostRequestBuilder(@Method String method) {
        super(method);
    }

    protected Request getRequest() {
        if (tag == null) tag = api;
        Request.Builder builder = new Request.Builder();
        FormBody requestBody = new FormBody.Builder()
                .add(HttpConstants.SYS_PARAM, sysParams)
                .add(HttpConstants.BIZ_PARAM, duplicateEncode ? URLEncoder.encode(bizParams) : bizParams)
                .build();
        LogUtil.i(LOG_TAG, "duplicateEncode :" + duplicateEncode);
        return builder.post(requestBody).url(url()).tag(tag).build();
    }

    @Override
    protected Observable<String> buildRetrofitRequest() {
        LogUtil.i(LOG_TAG, "duplicateEncode :" + duplicateEncode);
        return HTTP.getService().post(sysParams, duplicateEncode ? URLEncoder.encode(bizParams) : bizParams);

    }
}
