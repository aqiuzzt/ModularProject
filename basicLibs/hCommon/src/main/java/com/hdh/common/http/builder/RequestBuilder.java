package com.hdh.common.http.builder;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.hdh.common.http.HTTP;
import com.hdh.common.http.annotation.Method;
import com.hdh.common.http.handler.DataCallback;
import com.hdh.common.http.handler.HttpConfigController;
import com.hdh.common.http.handler.ResponseHandle;
import com.hdh.common.http.handler.SimpleRxObservableWrapper;
import com.hdh.common.util.app.AppNetworkUtil;
import com.hdh.common.util.LogUtil;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 描述:请求后台构造类 
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/21 20:23
 */
public abstract class RequestBuilder {
    private static final String LOG_TAG = "RequestBuilder";
    protected String api;
    protected Object tag;
    protected String mMethod;
    protected String sysParams;
    protected String bizParams;
    protected boolean duplicateEncode;
    protected File cacheFileDir;
    //请求的id 用来区分不同的请求,便于处理(可选)
    protected int id = 1024;
    private ParamsBuilder mParamsBuilder;
    private boolean hint;
    protected String path;
    protected String host;

    protected static final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
    protected static final MediaType TEXT= MediaType.parse("text/plain; charset=utf-8");
    protected static final MediaType ENCODE = MediaType.parse("application/x-www-form-urlencoded");

    public RequestBuilder(@Method String method) {
        this.mMethod = method;
    }

    public ParamsBuilder api(String api) {
        this.api = api;
        if (mParamsBuilder == null) mParamsBuilder = new ParamsBuilder(this, api);
        return mParamsBuilder;
    }

    public RequestBuilder host(String host) {
        this.host = host;
        return this;
    }

    public RequestBuilder path(String path) {
        this.path = path;
        return this;
    }

    public RequestBuilder tag(Object tag) {
        this.tag = tag;
        return this;
    }


    public RequestBuilder id(int id) {
        this.id = id;
        return this;
    }

    public RequestBuilder showHint() {
        hint = true;
        return this;
    }

    public Response execute() throws IOException {
        if (TextUtils.isEmpty(api)) {
            throw new IllegalArgumentException("api can not be null before this method execute");
        }

        Request request = getRequest();
        Call call = HTTP.getDefault().newCall(request);
        return call.execute();
    }

    /**
     * 网络异步请求
     * @param callback 回调
     * @return
     */
    public Call enqueue(Callback callback) {
        if (TextUtils.isEmpty(api)) {
            throw new IllegalArgumentException("api can not be null before this method execute");
        }

        if (callback == null) {
            throw new IllegalArgumentException("callback can not be null");
        }

        HttpConfigController mHandler = HTTP.getController();
        if (mHandler != null && !AppNetworkUtil.isNetworkAvailable(mHandler.context())) {
            if (hint) {
                mHandler.showFailedTip(404);
            }
        }

        if (callback instanceof BaseResponse) {
            BaseResponse handler = ((BaseResponse) callback);
            mHandler = HTTP.getController();
            if (mHandler != null && mHandler.networkCheck()) { //前置检查
                return null;
            }

            if (mHandler != null && !AppNetworkUtil.isNetworkAvailable(mHandler.context())) {
                id = 404;
                handler.onFailed(id, null, new IllegalStateException("network error"));
                return null;
            }

            handler.setId(id);
            handler.onStart(id);
            handler.showHint(hint);
        }

        Call call = HTTP.getDefault().newCall(getRequest());
        call.enqueue(callback);
        return call;
    }

    public void request(@NonNull DataCallback callback) {
        if (callback == null) return;
        ResponseHandle handle = new ResponseHandle(callback);
        enqueue(handle);
    }

    protected abstract Request getRequest();

    void setBizParams(String bizParams) {
        this.bizParams = bizParams;
    }

    void setSysParams(String sysParams) {
        this.sysParams = sysParams;
    }

    public RequestBuilder encode(Boolean encode) {
        duplicateEncode = encode;
        return this;
    }

    public Observable<String> rxRequest() {
        if (TextUtils.isEmpty(api)) {
            throw new IllegalArgumentException("api can not be null before this method execute");
        }
        return buildRetrofitRequest();
    }

    @Deprecated
    public <T> Observable<T> simpleRxRequest() {
        if (TextUtils.isEmpty(api)) {
            throw new IllegalArgumentException("api can not be null before this method execute");
        }
        Observable<String> observable = buildRetrofitRequest();
        return SimpleRxObservableWrapper.wrap(observable);
    }

    protected abstract Observable<String> buildRetrofitRequest();


    String url() {
        String baseHost = host == null ? HTTP.getHttpUrl() + "/router" : host;
        if (path == null) return baseHost;
        LogUtil.i(LOG_TAG, "url :" + String.format(Locale.getDefault(), "%s/%s", baseHost, path));
        return String.format(Locale.getDefault(), "%s/%s", baseHost, path);
    }

    /**
     * 必要情况重组参数,一般不会用到这个东西的把!
     */
    public RequestBuilder inertParam(Map<String, Object> map) {
        if (true)
            throw new IllegalStateException("这个方法有问题,暂时不要调用");
        ParamsBuilder builder = api(api);
//        int type = builder.getParamType();
        String key = builder.getSignKey();
        String value = builder.getSignValue();
        builder.addParams(map);
//        builder.paramType(type, key, value);
        return builder.sign();
    }
}
