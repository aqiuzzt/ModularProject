package com.hdh.common.http.util;

/**
 * Desc: 上传出现任何异常都将抛出这个Exception
 * Author:Martin
 * Date:2016/7/23
 */
public class UploadException extends Exception {

    public UploadException(String msg) {
        super(msg);
    }
}
