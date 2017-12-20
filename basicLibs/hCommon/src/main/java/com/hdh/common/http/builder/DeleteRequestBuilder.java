package com.hdh.common.http.builder;

import com.hdh.common.http.HTTP;
import com.hdh.common.http.annotation.Method;
import com.hdh.common.http.pojo.RequestEntity;
import com.hdh.common.http.util.HttpConstants;

import java.net.URLEncoder;

import io.reactivex.Observable;
import okhttp3.FormBody;
import okhttp3.Request;

/**
 * 删除请求
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/21 20:20
 */
public class DeleteRequestBuilder extends RequestBuilder {

    public DeleteRequestBuilder(@Method String method) {
        super(method);
    }

    protected Request getRequest() {
        if (tag == null) tag = api;
        Request.Builder builder = new Request.Builder();
        FormBody requestBody = new FormBody.Builder()
                .add(HttpConstants.SYS_PARAM, sysParams)
                .add(HttpConstants.BIZ_PARAM, duplicateEncode ? URLEncoder.encode(bizParams) : bizParams)
                .build();
        return builder.delete(requestBody).url(url()).tag(tag).build();
    }

    @Override
    protected Observable<String> buildRetrofitRequest() {
        RequestEntity entity = new RequestEntity(sysParams, duplicateEncode ? URLEncoder.encode(bizParams) : bizParams);
        return HTTP.getService().deleteInvoke(entity);
    }
}
