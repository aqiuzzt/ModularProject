package com.hdh.common.http.builder;

import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hdh.common.http.HTTP;
import com.hdh.common.http.crypto.SignUtils;
import com.hdh.common.http.handler.HttpConfigController;
import com.hdh.common.http.util.HttpConstants;
import com.hdh.common.util.HSON;
import com.hdh.common.util.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 只有一级参数需要进行签名
 * 描述: 请求参数构建
 * 
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/21 20:23
 */
public class ParamsBuilder {
    private static final String LOG_TAG="ParamsBuilder";
    private final RequestBuilder requestBuilder;
    private Map<String, Object> mParams;
    private String apiName;
    private String signKey;
    private String signValue;
    private String rawKey = HttpConstants.RAW;
    private String commandKey = HttpConstants.COMMAND;
    private Map<String, Object> signKeyValueMap;
    private String mSession;
    @IntRange(from = 0, to = 2)
    private int hierarchy = 1;  //请求参数层级
    private String bizParams;  //请求的业务参数
    private String sysParams;  //请求的系统参数


    public ParamsBuilder(RequestBuilder requestBuilder, String api) {
        if (mParams == null) mParams = new HashMap<>();
        if (signKeyValueMap == null) signKeyValueMap = new HashMap<>();
        this.requestBuilder = requestBuilder;
        this.apiName = api;

        // 看下有没有公共参数
        HttpConfigController mPreHandler = HTTP.getController();
        if (mPreHandler != null && mPreHandler.unitParams() != null && mPreHandler.unitParams().size() > 0) {
            mParams.putAll(mPreHandler.unitParams());
        }
        if (mPreHandler != null) {  //设置统一的session,如果与统一相背可以是用setSession先至空
            mSession = mPreHandler.session();
            LogUtil.i(LOG_TAG,"ParamsBuilder mSession "+mSession);
        }
    }

    public ParamsBuilder addParam(String key, Object value) {
        mParams.put(key, value);
        return this;
    }

    public ParamsBuilder addParams(Map<String, Object> params) {
        if (params != null)
            mParams.putAll(params);
        return this;
    }

    /**
     * 设置第一层的key
     * 默认为 {@link HttpConstants#COMMAND}
     * 一般为request/command...
     *
     * @param command
     * @return
     */
    public ParamsBuilder setCommandKey(String command) {
        this.commandKey = command;
        return this;
    }

    /**
     * 设置第一层的二层的key
     * 默认为 {@link HttpConstants#RAW}
     * 一般为rawRequest/raw...
     *
     * @param raw
     * @return
     */
    public ParamsBuilder setRawKey(String raw) {
        this.rawKey = raw;
        return this;
    }

    /**
     * 设置请求参数的层级数
     * 默认一层
     *
     * @param hierarchy
     * @return
     */
    public ParamsBuilder setHierarchy(@IntRange(from = 0, to = 2) int hierarchy) {
        this.hierarchy = hierarchy;
        return this;
    }

    /**
     * 签名的key ,一般为第一级的key
     * 默认会自动处理,如果有特殊则自己写
     *
     * @param signKey
     * @return
     */
    public ParamsBuilder setSignKey(String signKey) {
        this.signKey = signKey;
        return this;
    }

    /**
     * 签名的value ,一般为第一级参数转化的json
     * 默认会自动处理,如果有特殊则自己写
     *
     * @param signValue
     * @return
     */
    public ParamsBuilder setSignValue(String signValue) {
        this.signValue = signValue;
        return this;
    }

    String getSignKey() {
        return this.signKey;
    }

    String getSignValue() {
        return this.signValue;
    }

    public RequestBuilder sign() {
        try {
            //组装业务参数
            if (mParams != null && mParams.size() > 0 && this.bizParams == null) {
                HashMap<String, Object> commandJson = null;
                switch (hierarchy) {
                    case 0: //0层的情况,直接把所有的参数搞进去就行了
                        bizParams = HSON.toJsonWithoutEscape(mParams);
                        break;
                    case 1: //一层的情况,直接把value转json作为签名
                        commandJson = new HashMap<>();
                        commandJson.put(commandKey, mParams);
                        bizParams = HSON.toJsonWithoutEscape(commandJson);

                        if (signKey == null) signKey = commandKey;
                        if (signValue == null) signValue = HSON.toJsonWithoutEscape(mParams);
                        break;
                    case 2: //两层的情况,把第二层
                        HashMap<String, Object> rawJson = new HashMap<>();
                        rawJson.put(rawKey, mParams);
                        commandJson = new HashMap<>();
                        commandJson.put(commandKey, rawJson);
                        bizParams = HSON.toJsonWithoutEscape(commandJson);
                        if (signKey == null) signKey = commandKey;
                        if (signValue == null) signValue = HSON.toJsonWithoutEscape(rawJson);
                        break;
                }
            }
            if (sysParams == null)
                sysParams = assembleSysParam();
            LogUtil.d(HttpConstants.TAG, String.format("sysParams:%s bizParams:%s", sysParams, bizParams));

            requestBuilder.setSysParams(sysParams);
            requestBuilder.setBizParams(bizParams);
        } catch (Exception e) {
            e.printStackTrace();
            if (HttpConstants.DEBUG) {
                Log.d(HttpConstants.TAG, "请求参数组装失败 :" + e.getMessage());
            }
        }
        LogUtil.i(LOG_TAG,"requestBuilder bizParams :"+requestBuilder.bizParams);
        return requestBuilder;
    }

    /**
     * 添加签名时的key-value
     *
     * @param key
     * @param value
     * @return
     */
    public ParamsBuilder addSignMap(String key, Object value) {
        signKeyValueMap.put(key, value);
        return this;
    }

    /**
     * 单个请求设置session
     * 已经统一处理,可以不理会
     *
     * @param session
     * @return
     */
    public ParamsBuilder setSession(String session) {
        this.mSession = session;
        return this;
    }

    /**
     * 组装公共参数
     *
     * @return
     */
    @Nullable
    private String assembleSysParam() {
        TreeMap<String, Object> treeMap = new TreeMap<>();
        HashMap<String, Object> jsonObject = new HashMap<>();
        try {
            long sysTime = System.currentTimeMillis();
            long appTime = sysTime;
            if (HTTP.getController() != null) {
                appTime = sysTime + HTTP.getController().diffTimestamp();
            }

            String timestamp = String.valueOf(appTime);
            jsonObject.put("apiName", this.apiName);
            jsonObject.put("appKey", HTTP.getAppKey());
            jsonObject.put("timestamp", timestamp);
            jsonObject.put("format", "json");
            jsonObject.put("v", "1.0.0");//TODO
            jsonObject.put("session", mSession == null ? "1" : mSession);
//            jsonObject.put("session", "JC_BETA_TEST");

            if (hierarchy == 0 && signKey == null && mParams.size() > 0) { //如果层级为0那么就直接把参数丢进去
                treeMap.putAll(mParams);
            } else if (signKey != null && signValue != null) { //否则把第一级作为签名参数丢进去
                treeMap.put(signKey, signValue);
            }

            if (signKeyValueMap.size() > 0)
                treeMap.putAll(signKeyValueMap); //额外的签名参数,与第一级同级,如果需要可以直接覆盖掉已有的签名
//            treeMap.put("session", "JC_BETA_TEST");

            treeMap.put("session", mSession == null ? "1" : mSession);
            treeMap.put("apiName", this.apiName);
            treeMap.put("appKey", HTTP.getAppKey());
            treeMap.put("timestamp", timestamp);
            treeMap.put("format", "json");
            treeMap.put("v", "1.0.0");
            List<String> ignoreParams = new ArrayList<>();
            ignoreParams.add("sign");
            LogUtil.i(LOG_TAG,"treeMap :"+ HSON.toJson(treeMap));
            String sign = SignUtils.signUseMD5(treeMap, ignoreParams, HTTP.getSecret(), true);
            jsonObject.put("sign", sign);
            return HSON.toJsonWithoutEscape(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
            if (HttpConstants.DEBUG) {
                Log.d(HttpConstants.TAG, "签名数据组装失败 :" + e.getMessage());
            }
        }
        return null;
    }


}
