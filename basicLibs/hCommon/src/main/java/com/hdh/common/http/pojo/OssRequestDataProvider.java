package com.hdh.common.http.pojo;

import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;

/**
 * Created by Administrator on 2016/3/3.
 * 获取stsToken
 */
public class OssRequestDataProvider extends OSSFederationCredentialProvider {
    private String tempAccessKeyId;
    private String tempAccessKeySecret;
    private String securityToken;
    private String expiration;

    public OssRequestDataProvider(String tempAccessKeyId, String tempAccessKeySecret, String securityToken, String expiration) {
        this.tempAccessKeyId = tempAccessKeyId;
        this.tempAccessKeySecret = tempAccessKeySecret;
        this.securityToken = securityToken;
        this.expiration = expiration;
    }

    @Override
    public OSSFederationToken getFederationToken() {
        return new OSSFederationToken(tempAccessKeyId, tempAccessKeySecret, securityToken, expiration);
    }
}