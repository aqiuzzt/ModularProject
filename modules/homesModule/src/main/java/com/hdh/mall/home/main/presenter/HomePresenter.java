package com.hdh.mall.home.main.presenter;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.hdh.android.mail.base.BaseApplication;
import com.hdh.android.mail.base.Constant;
import com.hdh.android.mail.base.bean.AccountFinanceInfo;
import com.hdh.android.mail.base.bean.BannerBean;
import com.hdh.android.mail.base.http.BaseRxCallback;
import com.hdh.android.mail.base.http.Result;
import com.hdh.android.mail.base.inte.PresenterImpl;
import com.hdh.android.mail.base.utils.ProfileStrorageUtil;
import com.hdh.common.http.util.APIException;
import com.hdh.common.util.HSON;
import com.hdh.common.util.LogUtil;
import com.hdh.mall.home.main.bean.AnnouncementBeanResult;
import com.hdh.mall.home.main.bean.StatisticsFinanceBean;
import com.hdh.mall.home.main.contract.HomeContract;
import com.hdh.mall.home.main.model.HomeModel;
import com.hdh.mall.home.utils.HttpRequestManager;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Desc: 首页
 * Author:Martin
 * Date:2017/3/9
 */

public class HomePresenter extends PresenterImpl<HomeContract.View, HomeContract.Model>
        implements HomeContract.Presenter {
    private static final String LOG_TAG = "HomePresenter";

    public HomePresenter(HomeContract.View view) {
        super(view);
    }


    @NonNull
    @Override
    protected HomeContract.Model createModel() {
        return new HomeModel();
    }

    @Override
    public void onAttach() {

    }


    @Override
    public void queryAccountFinanceConsum(String accountId) {
        LogUtil.i(LOG_TAG, "queryAccountFinanceConsum accountId:" + accountId);
        Observable<String> observable = getModel().queryAccountFinanceConsum(accountId);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseRxCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        LogUtil.i(LOG_TAG, "queryAccountFinanceConsum dddd s:" + result);
                        if (checkNull()) return;
                        if (HSON.checkCallbackllegal(result)) {
                            onFailed(new APIException(HSON.getCode(result), HSON.getMsg(result)));
                            return;
                        }
                        Result<String> ss = HSON.parse(result, new TypeToken<Result<String>>() {
                        });
                        getView().onQueryAccountFinanceConsum(ss.data, null);
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        if (checkNull()) return;
                        APIException apiException = (APIException) e;
                        if (!TextUtils.isEmpty(apiException.code) && apiException.code.equals("302")) {
                            getView().logout();
                        } else {
                            getView().onQueryAccountFinanceConsum("0", e.getMessage());
                        }
                    }
                });
    }

    @Override
    public void queryAccountFinance() {
        getModel().queryAccountFinance(ProfileStrorageUtil.getAccountId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseRxCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        if (checkNull()) return;
                        if (HSON.checkCallbackllegal(s)) {
                            onFailed(new APIException(HSON.getCode(s), HSON.getMsg(s)));
                            return;
                        }
                        Result<List<AccountFinanceInfo>> result = HSON.parse(s, new TypeToken<Result<List<AccountFinanceInfo>>>() {
                        });
                        if (result.isOk()) {
                            BaseApplication.getSecondLevelCacheKit().put(Constant.ACCOUNT_FINANCE_ALL_INFO, s);
                            getView().onQueryAccountFinance("");
                        }
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        if (checkNull()) return;
                        APIException apiException = (APIException) e;
                        if (!TextUtils.isEmpty(apiException.code) && apiException.code.equals("302")) {
                            getView().logout();
                        } else {
                            getView().onQueryAccountFinance(e.getMessage());
                        }

                    }
                });
    }

    @Override
    public void queryAccountFinancePerformance(String accountId) {
        Observable<String> observable = getModel().queryAccountFinancePerformance(accountId);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseRxCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtil.i(LOG_TAG, "queryAccountFinancePerformance dddd s:" + s);
                        if (checkNull()) return;
                        if (HSON.checkCallbackllegal(s)) {
                            onFailed(new APIException(HSON.getCode(s), HSON.getMsg(s)));
                            return;
                        }
                        Result<String> result = HSON.parse(s, new TypeToken<Result<String>>() {
                        });
                        if (result.isOk()) {
                            getView().queryAccountFinancePerformance(result.data, "");
                        } else {
                            if (result.code.equals("302")) {
                                getView().logout();
                            }
                        }
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        if (checkNull()) return;
                        APIException apiException = (APIException) e;
                        if (!TextUtils.isEmpty(apiException.code) && apiException.code.equals("302")) {
                            getView().logout();
                        } else {
                            getView().onQueryAccountFinance(e.getMessage());
                        }
                    }
                });


    }

    @Override
    public void queryGoldValue(String... dates) {

    }

    @Override
    public void queryBannerInfo() {
        //Todo 需要确认version 和type赋值
        Observable<String> observable = getModel().queryBannerInfo("1.0", 3);
        observable.subscribeOn(Schedulers.io()).flatMap(new Function<String, ObservableSource<Result<List<BannerBean>>>>() {
            @Override
            public ObservableSource<Result<List<BannerBean>>> apply(@io.reactivex.annotations.NonNull String s) throws Exception {
                LogUtil.i(LOG_TAG, "hot good:" + s);
                if (HSON.checkCallbackllegal(s)) {
                    return Observable.error(new APIException(HSON.getCode(s), HSON.getMsg(s)));
                }
                Result<List<BannerBean>> data = HSON.parse(s, new TypeToken<Result<List<BannerBean>>>() {
                });
                if (data.isOk())
                    return Observable.just(data);
                return Observable.error(new APIException(data.code, data.msg));
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new BaseRxCallback<Result<List<BannerBean>>>() {
            @Override
            public void onSuccess(Result<List<BannerBean>> listResult) {
                if (checkNull()) return;
                if (listResult.isOk()) {
                    getView().onQueryBannerCallback(listResult.data, "");
                } else {
                    getView().onQueryBannerCallback(null, listResult.msg);
                }
            }

            @Override
            public void onFailed(Throwable e) {
                if (checkNull()) return;
                APIException apiException = (APIException) e;
                if (!TextUtils.isEmpty(apiException.code) && apiException.code.equals("302")) {
                    getView().logout();
                } else {
                    getView().onQueryBannerCallback(null, e.getMessage());
                }
            }
        });
    }


    @Override
    public void queryAnnouncementList(String parentId, int pageIndex, int pageSize) {
        getModel().queryAnnouncementList(parentId, pageIndex, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseRxCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        if (checkNull()) return;
                        LogUtil.i(LOG_TAG, "queryAnnouncementList dddd s:" + result);
                        if (HSON.checkCallbackllegal(result)) {
                            onFailed(new APIException(HSON.getCode(result), HSON.getMsg(result)));
                            return;
                        }

                        Result<AnnouncementBeanResult> annouceList = HSON.parse(result, new TypeToken<Result<AnnouncementBeanResult>>() {
                        });
                        if (!annouceList.isOk()) {
                            getView().queryAnnouncementListCallback(null, annouceList.msg);
                        } else {
                            getView().queryAnnouncementListCallback(annouceList.data.getData(), "");
                        }

                    }

                    @Override
                    public void onFailed(Throwable e) {
                        if (checkNull()) return;
                        APIException apiException = (APIException) e;
                        if (!TextUtils.isEmpty(apiException.code) && apiException.code.equals("302")) {
                            getView().logout();
                        } else {
                            getView().queryAnnouncementListCallback(null, e.getMessage());
                        }
                    }
                });
    }

    @Override
    public void queryFinancePerformance(String accountId) {
        Observable<String> observable = getModel().queryFinancePerformance(accountId, "1");
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseRxCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtil.i(LOG_TAG, "queryFinancePerformance dddd s:" + s);
                        if (checkNull()) return;
                        if (HSON.checkCallbackllegal(s)) {
//                            onFailed(new APIException(HSON.getCode(s), HSON.getMsg(s)));
                            return;
                        }
                        Result<StatisticsFinanceBean> result = HSON.parse(s, new TypeToken<Result<StatisticsFinanceBean>>() {
                        });
                        if (result.isOk()) {
                            getView().queryFinancePerformanceCallback(result.data, "");
                        } else {
                            if (result.code.equals("302")) {
                                getView().logout();
                            }
                        }
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        if (checkNull()) return;
                        APIException apiException = (APIException) e;
                        if (!TextUtils.isEmpty(apiException.code) && apiException.code.equals("302")) {
                            getView().logout();
                        } else {
                            getView().onQueryAccountFinance(e.getMessage());
                        }
                    }
                });
    }

    @Override
    public void reportBannerStatistics(String infoId, int type) {
        HttpRequestManager.reportBannerStatistics(infoId, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseRxCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtil.i(LOG_TAG, "reportBannerStatistics dddd s:" + s);
                        if (checkNull()) return;
                        if (HSON.checkCallbackllegal(s)) {
                            onFailed(new APIException(HSON.getCode(s), HSON.getMsg(s)));
                            return;
                        }
                        Result<String> result = HSON.parse(s, new TypeToken<Result<String>>() {
                        });
                        if (result.isOk()) {
                            getView().reportBannerStatisticsCallback("");
                        } else {
                            if (result.code.equals("302")) {
                                getView().logout();
                            }
                        }
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        if (checkNull()) return;
                        getView().reportBannerStatisticsCallback(e.getMessage());
                    }
                });
    }


}
