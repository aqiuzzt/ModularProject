package com.hdh.android.mail.base.http;

import android.text.TextUtils;

import com.hdh.common.http.util.APIException;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/23 18:24
 */
public class OnErrorSubscribe implements Consumer<Throwable> {

    @Override
    public void accept(@NonNull Throwable throwable) throws Exception {
        int code = 200;
        String msg = null;
        if (throwable == null) {
            code = -1;
        } else {
            if (throwable instanceof APIException) {
                APIException exception = ((APIException) throwable);
                if (TextUtils.isEmpty(exception.code) || !TextUtils.isDigitsOnly(exception.code)) {
                    code = -1;
                } else {
                    code = Integer.parseInt(exception.code);
                }
                if (TextUtils.isEmpty(exception.msg) && code != 200) {
                    msg = "未知异常";
                } else {
                    msg = exception.msg;
                }

            } else {
                code = -1;
                msg = "未知异常";
            }
        }
        HttpErrorHandle.handleError(code, msg);
    }

    public static OnErrorSubscribe create() {
        return new OnErrorSubscribe();
    }
}
