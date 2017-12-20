package com.hdh.common.http.handler;

import com.hdh.common.http.pojo.BaseResult;
import com.hdh.common.http.util.APIException;

import io.reactivex.functions.Function;

/**
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/21 20:27
 * @param <T>
 */
public class SimpleHttpResultFunc<T> implements Function<BaseResult<T>, T> {
    @Override public T apply(BaseResult<T> tBaseResult) throws Exception {
        if (!"200".equals(tBaseResult.code))
            throw new APIException(tBaseResult.code, tBaseResult.msg);
        return tBaseResult.data;
    }
}
