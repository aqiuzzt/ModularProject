package com.hdh.mall.main.model;



import com.hdh.mall.main.contract.MainContract;
import com.hdh.mall.main.utils.HttpRequestManager;

import io.reactivex.Observable;

public class MainModel implements MainContract.Model {

    @Override
    public Observable<String> checkApkUpdate(String oldVersion) {
        return null;
    }
}