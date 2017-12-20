package com.hdh.common.http.log;


import android.text.TextUtils;

import com.hdh.common.http.util.HttpConstants;
import com.hdh.common.util.LogUtil;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class LoggerInterceptor implements Interceptor {
    public static final String TAG = HttpConstants.TAG;
    private String tag;
    private boolean showResponse;

    public LoggerInterceptor(String tag, boolean showResponse) {
        if (TextUtils.isEmpty(tag)) {
            tag = TAG;
        }
        this.showResponse = showResponse;
        this.tag = tag;
    }

    public LoggerInterceptor(String tag) {
        this(tag, false);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        logForRequest(request);
        Response response = chain.proceed(request);
        return logForResponse(response);
    }

    /**
     * 拦截，处理服务器响应
     * @param response
     * @return
     */
    private Response logForResponse(Response response) {
        try {
            //===>response log
            LogUtil.i(tag, "========response'log=======");
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            LogUtil.i(tag, "logForResponse url : " + clone.request().url());
            LogUtil.i(tag, "logForResponse code : " + clone.code());
            LogUtil.i(tag, "logForResponse protocol : " + clone.protocol());
            if (!TextUtils.isEmpty(clone.message()))
                LogUtil.i(tag, "logForResponse message : " + clone.message());

            if (showResponse) {
                ResponseBody body = clone.body();
                if (body != null) {
                    MediaType mediaType = body.contentType();
                    if (mediaType != null) {
                        LogUtil.i(tag, "responseBody's contentType : " + mediaType.toString());
                        if (isText(mediaType)) {
                            String resp = body.string();
                            LogUtil.i(tag, "responseBody's content : " + resp);

                            body = ResponseBody.create(mediaType, resp);
                            return response.newBuilder().body(body).build();
                        } else {
                            LogUtil.i(tag, "responseBody's content : " + " maybe [file part] , too large too print , ignored!");
                        }
                    }
                }
            }

            LogUtil.i(tag, "========response'log=======end");
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return response;
    }

    /**
     * 拦截 请求服务器处理
     * @param request
     */
    private void logForRequest(Request request) {
        try {
            String url = request.url().toString();
            Headers headers = request.headers();

            LogUtil.i(tag, "========request'log=======");
            LogUtil.i(tag, "logForRequest method : " + request.method());
            LogUtil.i(tag, "logForRequest url : " + url);
            if (headers != null && headers.size() > 0) {
                LogUtil.i(tag, "logForRequest headers : " + headers.toString());
            }
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    LogUtil.i(tag, "requestBody's contentType : " + mediaType.toString());
                    if (isText(mediaType)) {
                        LogUtil.i(tag, "requestBody's content : " + url + "?" + bodyToString(request));
                    } else {
                        LogUtil.i(tag, "requestBody's content : " + " maybe [file part] , too large too print , ignored!");
                    }
                }
            }
            LogUtil.i(tag, "========request'log=======end");
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    private boolean isText(MediaType mediaType) {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype().equals("json") ||
                    mediaType.subtype().equals("xml") ||
                    mediaType.subtype().equals("html") ||
                    mediaType.subtype().equals("webviewhtml") ||
                    mediaType.subtype().equals("x-www-form-urlencoded")
                    )
                return true;
        }
        return false;
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }
}