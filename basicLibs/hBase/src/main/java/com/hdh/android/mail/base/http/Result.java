package com.hdh.android.mail.base.http;


/**
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/23 18:09
 * @param <T>
 */
public class Result<T> {
    public String code;
    public String msg;
    public boolean success;
    public T data;

    public boolean isOk() {
        return success && code.equals("200");
    }
}
