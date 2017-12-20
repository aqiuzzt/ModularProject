package com.hdh.common.http.pojo;

import java.io.Serializable;

/**
 * Desc:
 * Author:Martin
 * Date:2017/3/21
 */

public class RequestEntity implements Serializable {
    public String sysParam;
    public String bizParam;

    public RequestEntity(String sysParam, String bizParam) {
        this.sysParam = sysParam;
        this.bizParam = bizParam;
    }

    @Override public String toString() {
        return "bizParam=" + bizParam + "&sysParam=" + sysParam;
    }
}
