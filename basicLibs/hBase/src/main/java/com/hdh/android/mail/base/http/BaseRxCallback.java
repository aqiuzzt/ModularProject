package com.hdh.android.mail.base.http;

import android.support.annotation.NonNull;

import com.hdh.common.http.util.APIException;
import com.hdh.common.util.LogUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * @param <T>
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/23 18:06
 */
public abstract class BaseRxCallback<T> implements Observer<T> {

    private static final String LOG_TAG = "BaseRxCallback";
    private String tag;
    private Disposable d;

    public BaseRxCallback(@NonNull Object activityOrFragment) {
        if (activityOrFragment != null) {
            tag = activityOrFragment.getClass().getSimpleName();
        } else {
            throw new IllegalArgumentException("object can not be null");
        }
    }

    public BaseRxCallback() {

    }

    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
        if (tag != null)
            RxRequestLifeManager.add(tag, d);
    }

    @Override
    public void onNext(T t) {
        LogUtil.i(LOG_TAG, "BaseRxCallback onNext:" + t.getClass());
        onSuccess(t);
    }

    public abstract void onSuccess(T t);

    public abstract void onFailed(Throwable e);

    @Override
    public void onError(Throwable e) {
//        需要判断是否是组装exception 是则直接返回。不是则再次组装，只赋予新msg
        if (e instanceof APIException) {
            APIException apiException = (APIException) e;
            LogUtil.d(LOG_TAG, "onError:" + apiException.getMessage() + " code:" + apiException.getCode());
            onFailed(apiException);
        } else {
            String errorStr = RxExceptionMessageFactory.createApiExceptionStr(e);
            onFailed(new APIException(null, errorStr));
        }

    }

    @Override
    public void onComplete() {
        if (tag != null && d != null)
            RxRequestLifeManager.remove(tag, d);
    }
}
