package com.hdh.mall.market.presenter;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.hdh.android.mail.base.bean.BannerBean;
import com.hdh.android.mail.base.bean.GoodsBean;
import com.hdh.android.mail.base.bean.GoodsCategoryBean;
import com.hdh.android.mail.base.http.BaseRxCallback;
import com.hdh.android.mail.base.http.Result;
import com.hdh.android.mail.base.inte.PresenterImpl;
import com.hdh.common.http.util.APIException;
import com.hdh.common.util.HSON;
import com.hdh.common.util.LogUtil;
import com.hdh.mall.market.contract.MarketContract;
import com.hdh.mall.market.model.MarketModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Desc:商城presenter
 * Author:Martin
 * Date:2017/3/9
 */

public class MarketPresenter extends PresenterImpl<MarketContract.View, MarketContract.Model>
        implements MarketContract.Presenter {
    private static final String LOG_TAG = "MarketPresenter";

    public MarketPresenter(MarketContract.View view) {
        super(view);
    }

    @NonNull
    @Override
    protected MarketContract.Model createModel() {
        return new MarketModel();
    }

    @Override
    public void onAttach() {

    }

    @Override
    public void onQueryBannerAndCategoryData() {

        getModel().getMarketBanner(2).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseRxCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        LogUtil.d(LOG_TAG, " getMarketBanner result " + result);
                        if (checkNull()) return;
                        if (HSON.checkCallbackllegalAll(result)) {
                            onFailed(new APIException(HSON.getCode(result), HSON.getMsg(result)));
                            return;
                        }
                        Result<List<BannerBean>> listResult = HSON.parse(result, new TypeToken<Result<List<BannerBean>>>() {
                        });
                        if (listResult.isOk()) {
                            getView().refreshBannerView(2,listResult.data);
                        }
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        if (checkNull()) return;
                        APIException apiException = (APIException) e;
                        if (!TextUtils.isEmpty(apiException.code) && apiException.code.equals("302")) {
                            getView().logout();
                        } else {
                            getView().onQueryBannerAndCategoryDataCallback(e.getMessage());
                        }
                    }
                });

        getModel().getMarketBanner(4).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseRxCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        LogUtil.d(LOG_TAG, " getMarketBanner result " + result);
                        if (checkNull()) return;
                        if (HSON.checkCallbackllegalAll(result)) {
                            onFailed(new APIException(HSON.getCode(result), HSON.getMsg(result)));
                            return;
                        }
                        Result<List<BannerBean>> listResult = HSON.parse(result, new TypeToken<Result<List<BannerBean>>>() {
                        });
                        if (listResult.isOk()) {
                            getView().refreshBannerView(4,listResult.data);
                        }
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        if (checkNull()) return;
                        APIException apiException = (APIException) e;
                        if (!TextUtils.isEmpty(apiException.code) && apiException.code.equals("302")) {
                            getView().logout();
                        } else {
                            getView().onQueryBannerAndCategoryDataCallback(e.getMessage());
                        }
                    }
                });



    }

    @Override
    public void reportBannerStatistics(String infoId, int type) {
        getModel().reportBannerStatistics(infoId, type).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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

    @Override
    public void requestGoodsList(String since, int pageSize, String categoryId) {

        getModel().requestGoodsList(since, pageSize, categoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseRxCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtil.i(LOG_TAG, "requestGoodsList dddd s:" + s);
                        if (checkNull()) return;
                        if (HSON.checkCallbackllegalAll(s)) {
                            onFailed(new APIException(HSON.getCode(s), HSON.getMsg(s)));
                            return;
                        }
                        Result<List<GoodsBean>> result = HSON.parse(s, new TypeToken<Result<List<GoodsBean>>>() {
                        });
                        if (result.isOk()) {
                            getView().requestGoodsListCallback(result, "");
                        } else {
                            if (result.code.equals("302")) {
                                getView().logout();
                            }
                        }
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        if (checkNull()) return;
                        getView().requestGoodsListCallback(null, e.getMessage());
                    }
                });

    }
}
