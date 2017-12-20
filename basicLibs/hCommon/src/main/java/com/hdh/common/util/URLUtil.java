package com.hdh.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


/**
 * URL工具类
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/22 10:01
 */
public class URLUtil {

    /**
     * url 解码
     *
     * @param schemeUrl url
     * @return 解码url
     */
    public static String decodeURL(String schemeUrl) {
        try {
            return URLDecoder.decode(schemeUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return schemeUrl;
    }

    /**
     * url 编码
     *
     * @param schemeUrl url
     * @return 编码url
     */
    public static String encodeURL(String schemeUrl) {
        try {
            return URLEncoder.encode(schemeUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ;
        return schemeUrl;
    }

    /**
     * 返回带参数的get请求url地址
     *
     * @param url    url
     * @param params 参数
     * @return 带参数的get请求url地址
     */
    public static String getURLWithParams(String url, Map<String, String> params) {
        return url + "?" + joinParam(params);
    }

    /**
     * 连接参数
     *
     * @param params 参数
     * @return 连接结果
     */
    private static StringBuffer joinParam(Map<String, String> params) {
        StringBuffer result = new StringBuffer();
        Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> param = iterator.next();
            String key = param.getKey();
            String value = param.getValue();
            result.append(key).append('=').append(value);
            if (iterator.hasNext()) {
                result.append('&');
            }
        }
        return result;
    }
}
