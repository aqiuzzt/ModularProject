package com.hdh.common.http.handler;

import com.google.gson.reflect.TypeToken;
import com.hdh.common.http.pojo.BaseResult;
import com.hdh.common.http.util.APIException;
import com.hdh.common.util.HSON;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/21 20:27
 */
public class SimpleRxObservableWrapper {

    public static <T> Observable<T> wrap(Observable<String> observable) {
        return observable.flatMap(new Function<String, ObservableSource<BaseResult<T>>>() {
            @Override public ObservableSource<BaseResult<T>> apply(String s) throws Exception {
                BaseResult<T> result = null;
                try {
                    result = HSON.parse(s, new TypeToken<BaseResult<T>>() {
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new APIException("96060", "数据解析异常");
                }
                return Observable.just(result);
            }
        }).map(new SimpleHttpResultFunc<T>());
    }

}
