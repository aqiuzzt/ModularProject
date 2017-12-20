package com.hdh.android.mail.base.http;

import com.hdh.android.mail.base.BaseApplication;
import com.hdh.common.util.view.ToastUtil;

import java.util.Locale;


/**
 *
 */
public class HttpErrorHandle {
    public static void handleError(int code, String msg) {
        String tip = String.format(Locale.getDefault(), "%s %s%d", msg, "错误码:", code);
        ToastUtil.show(BaseApplication.get(), tip);
    }
}
