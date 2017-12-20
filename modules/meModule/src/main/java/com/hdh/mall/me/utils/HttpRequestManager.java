package com.hdh.mall.me.utils;

import android.support.annotation.WorkerThread;
import android.text.TextUtils;

import com.hdh.android.mail.base.BaseApplication;
import com.hdh.android.mail.base.bean.AddressBean;
import com.hdh.android.mail.base.bean.SmsRequest;
import com.hdh.android.mail.base.manager.DeviceManager;
import com.hdh.android.mail.base.utils.ProfileStrorageUtil;
import com.hdh.common.http.HTTP;
import com.hdh.common.http.builder.ParamsBuilder;
import com.hdh.common.http.builder.RequestBuilder;
import com.hdh.common.http.util.HttpConstants;
import com.hdh.common.http.util.UploadException;
import com.hdh.common.util.HSON;
import com.hdh.common.util.MD5;
import com.hdh.mall.hconfig.URLName;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gzhjhc.com">Contact me.</a>
 * @since 2017/12/4 16:17
 */

public class HttpRequestManager implements URLName {
    private static final String LOG_TAG = "HttpRequestManager";


    /**
     * 推出登录接口
     *
     * @return
     */
    public static Observable<String> logout() {
        return HTTP.post().api("logout")
                .addParam("accessToken", ProfileStrorageUtil.getAccessToken())
                .addParam("deviceUid", DeviceManager.getManager().getDeviceRequest(BaseApplication.get()).deviceUid)
                .setCommandKey("logout")
                .sign().rxRequest();

    }
}
