package com.hdh.common.http.util;

import android.content.Context;

import com.hdh.common.http.builder.DeleteRequestBuilder;
import com.hdh.common.http.builder.GetRequestBuilder;
import com.hdh.common.http.builder.OSSUploadBuilder;
import com.hdh.common.http.builder.PostRequestBuilder;
import com.hdh.common.http.builder.PutRequestBuilder;
import com.hdh.common.http.builder.RequestBuilder;


/**
 * Desc:
 * Author:Martin
 * Date:2017/3/7
 */

public class HttpMethodFactory {
    public static RequestBuilder create(String method) {
        if (HttpConstants.GET.equals(method)) {
            return new GetRequestBuilder(method);
        } else if (HttpConstants.POST.equals(method)) {
            return new PostRequestBuilder(method);
        } else if (HttpConstants.PUT.equals(method)) {
            return new PutRequestBuilder(method);
        } else if (HttpConstants.DELETE.equals(method)) {
            return new DeleteRequestBuilder(method);
        }
        return null;
    }

    public static OSSUploadBuilder upload(Context context) {
        return new OSSUploadBuilder(context);
    }
}
