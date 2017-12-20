package com.hdh.common.http.util;


import com.hdh.common.http.HTTP;

/**
 * Desc:
 * Author:Martin
 * Date:2016/7/12
 */
public class HttpConstants {
    public static final String LOG_URL_ECHO_RESPONSE_PATH = "/re/";
    public static final String TAG = "HTTP_REQUEST";
    public static final boolean DEBUG = true;

    public static final String UTF8 = "UTF-8";

    public static final String SIGN_KEY = "command";
    public static final String COMMAND = SIGN_KEY;
    public static final String RAW = "rawRequest";
    public static final String SYS_PARAM = "sysParam";
    public static final String BIZ_PARAM = "bizParam";

    public static final int BusinessIdUser = 11001;

    public static final int FileTypeImage = 1;
    public static final int FileTypeVideo = 2;
    public static final int FileTypeOther = 3;

    public static final String AppId = HTTP.getUploadPhotoAppKey();
    /**
     * 上传图片
     **/
    public static final String aliyun_oos_upload_apiToken = "file.appToken";
    public static final String DATA = "data";

    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";
}
