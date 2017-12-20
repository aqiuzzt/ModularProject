package com.hdh.common.http.util;

/**
 * Desc:
 * Author:Martin
 * Date:2017/3/14
 */

public class APIException extends Exception {

    public String code;
    public String msg;

    public APIException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "APIException{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
