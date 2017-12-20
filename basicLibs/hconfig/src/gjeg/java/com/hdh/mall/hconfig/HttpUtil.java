package com.hdh.mall.hconfig;


/**
 * Created by Administrator on 2017/6/22.
 */

public class HttpUtil {
    private static final String LOG_TAG = "HttpUtil";
    private static final String tag = BuildConfig.BUILD_TYPE;

    public static String getUrl(String type) {
        String url = null;


        switch (BuildConfig.ENVIRONMENT) {
            case 1:
                url = "http://www.baidu.com";
                break;
            case 2:
                url = "http://www.baidu.com";
                break;
            case 3:
                url = "http://www.baidu.com";
                break;
        }

        return url;

    }

    public static String getSecret(String type) {
        String secret = null;

        switch (BuildConfig.ENVIRONMENT) {
            case 1:
                secret = "eeeeeeee";
                break;
            case 2:
                secret = "eeeeeeee=";
                break;
            case 3:
                secret = "eeeeeeee=";
                break;
        }

        return secret;

    }

    public static String getAppKey(String type) {
        String appkey = null;

        switch (BuildConfig.ENVIRONMENT) {
            case 1:
                appkey = "eeeeeeee";
                break;
            case 2:
                appkey = "eeeeeeee";
                break;
            case 3:
                appkey = "eeeeeeee";
                break;
        }

        return appkey;

    }

    public static String getUploadPhotoAppKey(String type) {
        String appkey = null;

        switch (BuildConfig.ENVIRONMENT) {
            case 1:
                appkey = "eeeeeeee";
                break;
            case 2:
                appkey = "eeeeeeee";
                break;
            case 3:
                appkey = "eeeeeeee";
                break;
        }

        return appkey;

    }


    /**
     * 获取我要推荐链接
     *
     * @return
     */
    public static String getRecommendUrl(String type) {
        String recUrl = null;

        switch (BuildConfig.ENVIRONMENT) {
            case 1:
                recUrl = "eeeeeeee";
                break;
            case 2:
                recUrl = "eeeeeeee";
                break;
            case 3:
                recUrl = "eeeeeeee";
                break;
        }

        return recUrl;

    }


}
