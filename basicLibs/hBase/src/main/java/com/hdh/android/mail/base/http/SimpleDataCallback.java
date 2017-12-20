package com.hdh.android.mail.base.http;

import com.google.gson.reflect.TypeToken;
import com.hdh.common.http.handler.DataCallbackEx;
import com.hdh.common.util.HSON;

import java.text.ParseException;

import okhttp3.Call;

/**
 * Desc:
 * Author:Martin
 * Date:2017/3/11
 */

public abstract class SimpleDataCallback<T> implements DataCallbackEx {

    private Result<T> mTResult;

    @Override public void onSuccess(int id, String body) {
        if (mTResult != null && mTResult.isOk()) {
            onResponse(id, mTResult, mTResult.data);
        } else if (mTResult == null) {
            onErrorHandle(id, new ParseException("数据解析失败,请重试", -1));
        } else {
            onErrorHandle(id, new ServiceResultException(mTResult.msg));
        }
    }

    @Override public void onStart(int id) {

    }

    @Override public void onFailed(int id, Call call, Exception e) {
        onErrorHandle(id, e);
    }

    @Override public void resultOnWorkThread(int id, String result) {
        mTResult = HSON.parse(result, new TypeToken<Result<T>>() {
        });

    }

    public abstract void onResponse(int id, Result<T> result, T data);

    public abstract void onErrorHandle(int id, Exception e);
}
