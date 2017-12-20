package com.hdh.common.http.util;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Desc:
 * Author:Martin
 * Date:2016/12/20
 */

public class SSLHelper {
    public static SSLSocketFactory getSSLSocketFactory() {
        //创建一个不验证证书链的证书信任管理器。
        final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType) {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType) {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[0];
            }
        }};
        SSLSocketFactory sslSocketFactory = null;
        try {
            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts,
                    new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            sslSocketFactory = sslContext
                    .getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        return sslSocketFactory;
    }

    public static DefaultHostnameVerifier hostTrustFlag() {
        return DefaultHostnameVerifier.getInstance();
    }

    private static class DefaultHostnameVerifier implements HostnameVerifier {

        @Override public boolean verify(String hostname, SSLSession session) {
            return true;
        }

        public static DefaultHostnameVerifier getInstance() {
            return new DefaultHostnameVerifier();
        }
    }
}
