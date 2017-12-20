package com.hdh.android.mail.base.http;

import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.hdh.common.http.util.APIException;
import com.hdh.common.util.LogUtil;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;

import io.reactivex.exceptions.OnErrorNotImplementedException;


/**
 * RxJava 出现的异常处理
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/23 18:09
 */
public class RxExceptionMessageFactory {

    private static final String LOG_TAG = "RxExceptionMessageFactory";

    private RxExceptionMessageFactory() {
    }

    public static boolean isApiException(Throwable e) {

        if (e instanceof APIException) {
            LogUtil.i(LOG_TAG, "RxExceptionMessageFactory isApiException APIException");
            return false;
        }

        return true;
    }

    public static String createApiExceptionStr(Throwable e) {

        try {
            LogUtil.i(LOG_TAG, "createApiExceptionStr msg:" + e.getMessage());
            throw e;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        if (e instanceof SocketException
                || e instanceof UnknownHostException
                || e instanceof OnErrorNotImplementedException
                || e instanceof ConnectException) {
            return "网络连接异常";
        } else if (e instanceof JsonIOException
                || e instanceof JsonSyntaxException
                || e instanceof JsonParseException) {
            return "服务器数据异常";
        } else if (e instanceof APIException) {
            return e.getMessage();
        }
        return "系统异常";
    }
}
