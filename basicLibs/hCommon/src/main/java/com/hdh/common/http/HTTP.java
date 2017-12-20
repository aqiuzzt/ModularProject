package com.hdh.common.http;

import android.content.Context;
import android.util.Log;

import com.hdh.common.http.annotation.Method;
import com.hdh.common.http.builder.DeleteRequestBuilder;
import com.hdh.common.http.builder.GetRequestBuilder;
import com.hdh.common.http.builder.OSSUploadBuilder;
import com.hdh.common.http.builder.PathRequestBuilder;
import com.hdh.common.http.builder.PostRequestBuilder;
import com.hdh.common.http.builder.PutRequestBuilder;
import com.hdh.common.http.builder.RequestBuilder;
import com.hdh.common.http.handler.HttpConfigController;
import com.hdh.common.http.log.LoggerInterceptor;
import com.hdh.common.http.util.HttpConstants;
import com.hdh.common.http.util.HttpMethodFactory;
import com.hdh.common.http.util.Platform;
import com.hdh.common.http.util.StringConverterFactory;
import com.hdh.mall.hconfig.HttpUtil;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import okhttp3.Call;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/12/7 17:46
 */
public class HTTP {
    public static final long TIMEOUT = 90 * 1000L;
    public static final long READ_TIMEOUT = 90 * 1000L;
    public static final long WRITE_TIMEOUT = 90 * 1000L;
    private static HTTP sHTTP;
    private OkHttpClient sOkHttpClient;
    private Platform mPlatform;
    private HttpConfigController mController;
    private Retrofit mRetrofit;
    private APIService mAPIService;
    private static String urlType ="hcwx";

    private HTTP() {
        sOkHttpClient = get_p_OkHttpClient();
        mPlatform = Platform.get();
        mRetrofit = get_p_Retrofit();
        mAPIService = get_p_apiService();
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {

            }
        });
    }

    public static APIService getService() {
        return instance().get_p_apiService();
    }

    private APIService get_p_apiService() {
        return mAPIService;
    }

    private Retrofit get_p_Retrofit() {
        Log.d(HttpConstants.TAG, "get_p_Retrofit() called   " + getHttpUrl());
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(StringConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(getHttpUrl())
                .client(sOkHttpClient)
                .build();
        mAPIService = retrofit.create(APIService.class);
        return retrofit;
    }

    public static HTTP getsHTTP() {
        return sHTTP;
    }

    public static void setsHTTP(HTTP sHTTP) {
        HTTP.sHTTP = sHTTP;
    }

    /***********************************************
     * 外部访问方法
     ***********************************************/


    public static void setUrlType(String type) {
        urlType = type;
    }

    public static String getHttpUrl() {
        return HttpUtil.getUrl(urlType);
    }

    public static String getSecret() {
        return HttpUtil.getSecret(urlType);
    }

    public static String getAppKey() {
        return HttpUtil.getAppKey(urlType);
    }

    public static String getUploadPhotoAppKey() {
        return HttpUtil.getUploadPhotoAppKey(urlType);
    }

    public static String getRecommendUrl() {
        return HttpUtil.getRecommendUrl(urlType);
    }

    /**
     * 获取统一处理器
     */
    public static HttpConfigController getController() {
        return instance().get_p_Controller();
    }

    /**
     * 请求成功了之后可能要根据结果进行一些统一的处理
     */
    public static void setConfigController(HttpConfigController controller) {
        instance().p_setConfigController(controller);
    }

    /**
     * 获取一个处理异步的Handler
     *
     * @return
     */
    public static Executor getDelivery() {
        return instance().get_p_Delivery();
    }

    /**
     * 获取一个{@link #sOkHttpClient}
     *
     * @return
     */
    public static OkHttpClient getDefault() {
        return instance().get_p_Default();
    }

    /**
     * GET 请求构造
     *
     * @return
     */
    public static GetRequestBuilder get() {
        return new GetRequestBuilder(HttpConstants.GET);
    }

    /**
     * POST 请求构造
     *
     * @return
     */
    public static PostRequestBuilder post() {
        return new PostRequestBuilder(HttpConstants.POST);
    }

    /**
     * PUT 请求构造
     *
     * @return
     */
    public static PutRequestBuilder put() {
        return new PutRequestBuilder(HttpConstants.PUT);
    }

    /**
     * Delete 请求构造
     *
     * @return
     */
    public static DeleteRequestBuilder delete() {
        return new DeleteRequestBuilder(HttpConstants.DELETE);
    }

    public static RequestBuilder request(@Method String method) {
        return HttpMethodFactory.create(method);
    }

    /**
     * 上传图片到OSS 请求构造
     *
     * @return
     */
    public static OSSUploadBuilder upload(Context context) {
        return new OSSUploadBuilder(context);
    }


    public static PathRequestBuilder path() {
        return new PathRequestBuilder(HttpConstants.GET);
    }

    /**
     * 取消某一个或者某一类请求
     *
     * @param tag
     */
    public static void cancel(Object tag) {
        instance().p_cancel(tag);
    }

    /**
     * 取消所有请求
     */
    public static void cancelAll() {
        instance().p_cancelAll();
    }


    /***********************************************
     * 内部处理方法
     ***********************************************/
    private static HTTP instance() {
        if (sHTTP == null) {
            synchronized (HTTP.class) {
                if (sHTTP == null) {
                    sHTTP = new HTTP();
                }
            }
        }
        return sHTTP;
    }

    private Executor get_p_Delivery() {
        return mPlatform.defaultCallbackExecutor();
    }

    private OkHttpClient get_p_Default() {
        return sOkHttpClient;
    }

    private void p_setConfigController(HttpConfigController configController) {
        this.mController = configController;
    }

    private HttpConfigController get_p_Controller() {
        return mController;
    }

    private OkHttpClient get_p_OkHttpClient() {
        //LOG打印
        LoggerInterceptor loggerInterceptor = new LoggerInterceptor(HttpConstants.TAG, false);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.retryOnConnectionFailure(true);
        builder.addInterceptor(loggerInterceptor);//增加LOG打印
//        try {´
//            builder.sslSocketFactory(SSLHelper.getSSLSocketFactory());
//            builder.hostnameVerifier(SSLHelper.hostTrustFlag());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        builder.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS);

        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(Runtime.getRuntime().availableProcessors() * 2);
        builder.dispatcher(dispatcher);

        return builder.build();
    }

    private void p_cancel(Object tag) {
        if (tag == null) return;
        for (Call call : sOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                if (!call.isCanceled()) call.cancel();
            }
        }

        for (Call call : sOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                if (!call.isCanceled()) call.cancel();
            }
        }
    }

    private void p_cancelAll() {
        sOkHttpClient.dispatcher().cancelAll();
    }
}
