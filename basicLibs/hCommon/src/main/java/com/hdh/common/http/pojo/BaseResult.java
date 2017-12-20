package com.hdh.common.http.pojo;

/**
 * Desc:
 * Author:Martin
 * Date:2017/3/14
 */

public class BaseResult<T> {
    public String code;
    public boolean success;
    public String msg;
    public T data;
}
