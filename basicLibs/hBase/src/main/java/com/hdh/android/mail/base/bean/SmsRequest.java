package com.hdh.android.mail.base.bean;

/**
 * Created by Martin on 2017/3/30.
 */

public class SmsRequest {
    /**
     * └deviceUid	default value	N	设备唯一标识
     * └mobile	default value	N	手机号码
     * └domain
     */
    public static final String DOMAIN_REG = "JC_REG";
    public static final String DOMAIN_FIND_PWD = "JC-FINDPWD";
    public String deviceUid;
    public String mobile;
    public String domain;
}
