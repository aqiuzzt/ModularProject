package com.hdh.mall.me.main.model;



import com.hdh.mall.me.main.contract.PersonContract;
import com.hdh.mall.me.utils.HttpRequestManager;

import io.reactivex.Observable;


public class PersonModel implements PersonContract.Model {

    @Override
    public Observable<String> verifyLoginPwd(String password) {
        return null;
    }
}
