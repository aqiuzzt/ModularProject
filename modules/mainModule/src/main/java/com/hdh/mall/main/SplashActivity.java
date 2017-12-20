package com.hdh.mall.main;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import com.hdh.android.mail.base.BaseApplication;
import com.hdh.android.mail.base.Constant;
import com.hdh.android.mail.base.activitys.BaseCommonActivity;
import com.hdh.android.mail.base.modular.module.main.MainIntent;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gzhjhc.com">Contact me.</a>
 * @since 2017/12/6 11:56
 */

public class SplashActivity extends BaseCommonActivity{
    private static final String LOG_TAG = "SplashActivity";
    private CompositeDisposable mCompositeDisposable;


    @Override
    protected void setContentViewBefore(Bundle savedInstanceState) {
        super.setContentViewBefore(savedInstanceState);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            //取消标题栏
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            //取消状态栏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    protected void bindView() {
        super.bindView();
        getToolBarX().setDisplayHomeAsUpEnabled(false).transparentizeToolbarBackground();
    }

    @Override
    protected void bindData() {
        mCompositeDisposable = new CompositeDisposable();
        Observable.timer(1, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Long aLong) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        MainIntent.gotoMianActivity();
                        BaseApplication.getSecondLevelCacheKit().put(Constant.LOGIN_FROME_TYPE, "main");
                    }
                });

        if (BaseApplication.getSecondLevelCacheKit() != null) {
            BaseApplication.getSecondLevelCacheKit().put(Constant.IS_CHECK_NORLMAL_UPDATE, false);
            if (!TextUtils.isEmpty(BaseApplication.getSecondLevelCacheKit().getAsString(Constant.LOGIN_FROME_TYPE))) {
                BaseApplication.getSecondLevelCacheKit().remove(Constant.LOGIN_FROME_TYPE);
            }
        }

    }

    @Override
    public int setLayoutId() {
        return R.layout.main_activity_splash;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }

}
