package com.hdh.common.http.pojo;

/**
 * Desc:
 * Author:Martin
 * Date:2016/7/14
 */
public class OSSResult<T> {
    public String code;
    public T data;
    public String msg;

    public boolean isSuccess() {
        return String.valueOf(200).endsWith(code);
    }
}
