package com.hdh.mall.main.presenter;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.hdh.android.mail.base.BaseApplication;
import com.hdh.android.mail.base.http.BaseRxCallback;
import com.hdh.android.mail.base.http.Result;
import com.hdh.android.mail.base.inte.PresenterImpl;
import com.hdh.android.mail.base.utils.ProfileStrorageUtil;
import com.hdh.common.http.HTTP;
import com.hdh.common.http.util.APIException;
import com.hdh.common.util.HSON;
import com.hdh.common.util.LogUtil;
import com.hdh.common.util.app.AppUtil;
import com.hdh.mall.main.bean.UpdateInfoBean;
import com.hdh.mall.main.contract.MainContract;
import com.hdh.mall.main.model.MainModel;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter extends PresenterImpl<MainContract.View, MainContract.Model>
        implements MainContract.Presenter {
    private static final String LOG_TAG = "MainPresenter";

    public MainPresenter(MainContract.View view) {
        super(view);
    }

    @NonNull
    @Override
    protected MainContract.Model createModel() {
        return new MainModel();
    }

    @Override
    public void onAttach() {
        HTTP.path().path("timestamp").rxRequest().subscribeOn(Schedulers.io()).flatMap(new Function<String, ObservableSource<Long>>() {
            @Override
            public ObservableSource<Long> apply(@io.reactivex.annotations.NonNull String s) throws Exception {
                Result<Long> result = HSON.parse(s, new TypeToken<Result<Long>>() {
                });
                if (result.isOk()) {
                    return Observable.just(result.data);
                }
                return Observable.error(new APIException(result.code, result.msg));
            }
        }).doOnNext(new Consumer<Long>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull Long aLong) throws Exception {
                if (aLong != null) {
                    long timestamp = aLong;
                    long sys = System.currentTimeMillis();
                    long diff = timestamp - sys;
                    LogUtil.i("系统时间: " + timestamp + " 应用时间: " + sys + " 差异时间: " + diff);
                    ProfileStrorageUtil.putDiffTimestamp(diff);
                    BaseApplication.setDiffTimestamp(diff);
                }

            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe();
    }

    @Override
    public void init() {

    }

    @Override
    public void checkApkUpdate() {

        Observable<String> observable = getModel().checkApkUpdate(AppUtil.getAppVersionName(BaseApplication.get()));
        observable.subscribeOn(Schedulers.io()).flatMap(new Function<String, ObservableSource<UpdateInfoBean>>() {
            @Override
            public ObservableSource<UpdateInfoBean> apply(@io.reactivex.annotations.NonNull String s) throws Exception {
                LogUtil.i(LOG_TAG, " checkApkUpdate s:" + s);
                if (HSON.checkCallbackllegal(s)) {
                    return Observable.error(new APIException(HSON.getCode(s), HSON.getMsg(s)));
                }
                Result<UpdateInfoBean> result = HSON.parse(s, new TypeToken<Result<UpdateInfoBean>>() {
                });
                if (result.isOk()) {
                    return Observable.just(result.data);
                }
                return Observable.error(new APIException(result.code, result.msg));
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseRxCallback<UpdateInfoBean>() {
                    @Override
                    public void onSuccess(UpdateInfoBean result) {
                        if (checkNull()) return;
                        getView().checkApkUpdateCallback(result, "");
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        if (checkNull()) return;
                        getView().checkApkUpdateCallback(null, e.getMessage());

                    }
                });

    }
}

