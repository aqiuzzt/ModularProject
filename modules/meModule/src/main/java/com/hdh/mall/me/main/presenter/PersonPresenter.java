package com.hdh.mall.me.main.presenter;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.hdh.android.mail.base.http.BaseRxCallback;
import com.hdh.android.mail.base.http.Result;
import com.hdh.android.mail.base.inte.PresenterImpl;
import com.hdh.common.http.util.APIException;
import com.hdh.common.util.HSON;
import com.hdh.mall.me.main.contract.PersonContract;
import com.hdh.mall.me.main.model.PersonModel;
import com.hdh.mall.me.utils.HttpRequestManager;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Desc:
 * Author:Martin
 * Date:2017/3/9
 */

public class PersonPresenter extends PresenterImpl<PersonContract.View, PersonContract.Model>
        implements PersonContract.Presenter {
    private static final String LOG_TAG = "PersonPresenter";

    public PersonPresenter(PersonContract.View view) {
        super(view);
    }

    @NonNull
    @Override
    protected PersonContract.Model createModel() {
        return new PersonModel();
    }

    @Override
    public void onAttach() {

    }

    @Override
    public void requestPersonInfo() {

    }


    @Override
    public void requestLogout() {
        HttpRequestManager.logout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseRxCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        if (checkNull()) return;
                        if (HSON.checkCallbackllegal(s)) {
                            onFailed(new APIException(HSON.getCode(s),HSON.getMsg(s)));
                            return;
                        }
                        Result<Object> result = HSON.parse(s, new TypeToken<Result<Object>>() {
                        });
                        if (result.isOk()) {
                            getView().requestLogoutCallBack(null);
                        } else {
                            if (result.code.equals("302")) {
                                getView().logout();
                            } else {
                                getView().requestLogoutCallBack(result.msg);
                            }
                        }
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        if (checkNull()) return;
                        getView().requestLogoutCallBack(e.getMessage());
                    }
                });
    }

    @Override
    public void verifyLoginPwd(String password) {
        final Observable<String> verifyLoginObservable = getModel().verifyLoginPwd(password);
        verifyLoginObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseRxCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        if (checkNull()) return;
                        if (HSON.checkCallbackllegal(s)) {
                            onFailed(new APIException(HSON.getCode(s),HSON.getMsg(s)));
                            return;
                        }
                        Result<Object> result = HSON.parse(s, new TypeToken<Result<Object>>() {
                        });
                        if (result.isOk()) {
                            getView().verifyLoginPwdCallback(null);
                        } else {
                            if (result.code.equals("302")) {
                                getView().logout();
                            } else {
                                getView().verifyLoginPwdCallback(result.msg);
                            }
                        }

                    }

                    @Override
                    public void onFailed(Throwable e) {
                        if (checkNull()) return;
                        getView().verifyLoginPwdCallback(e.getMessage());
                    }
                });
    }
}
