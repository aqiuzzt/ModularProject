package com.hdh.common.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


/**
 * Desc:Gson处理接送工具类
 * Author:Martin
 * Date:2016/7/7
 */
public class HSON {
    private static final String LOG_TAG = "----HSON----";
    private static final String DEFAULT_OBJ = "{}";
    private static Gson sGson;
    private static Gson sGsonBuidler;

    public static Gson getGson() {
        if (sGson == null) {
            sGson = new Gson();
        }
        return sGson;
    }

    public static Gson getGsonBuilder() {
        if (sGsonBuidler == null) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.disableHtmlEscaping();

            sGsonBuidler = gsonBuilder.create();
        }
        return sGsonBuidler;
    }

    private static Gson checkGson(Gson gson) {
        return gson == null ? getGson() : gson;
    }

    public static <T> T parse(String json, Class<T> clazz) {
        return parse(null, json, clazz);
    }

    public static <T> T parse(Gson gson, String json, Class<T> clazz) {

        if (json == null || clazz == null) return null;
        gson = checkGson(gson);
        return gson.fromJson(json, clazz);
    }

    public static <T> T parse(String json, TypeToken<T> token) {
        return parse(null, json, token);
    }

    public static <T> T parse(Gson gson, String json, TypeToken<T> token) {
        if (json == null || token == null) return null;
        gson = checkGson(gson);
        return gson.fromJson(json, token.getType());
    }

    public static <T> String toJson(Gson gson, T obj) {
        if (obj == null) return null;
        gson = checkGson(gson);
        return gson.toJson(obj);
    }

    public static <T> String toJson(T obj) {
        return toJson(null, obj);
    }

    public static String toJsonWithoutEscape(Map<String, Object> params) {
        if (params == null || params.size() == 0) return DEFAULT_OBJ;
        return getGsonBuilder().toJson(params);
    }

    public static String toJsonWithoutEscape(List<Object> params) {
        if (params == null || params.size() == 0) return DEFAULT_OBJ;
        return getGsonBuilder().toJson(params);
    }

    public static String toJsonWithoutEscape(Object param) {
        if (param == null) return DEFAULT_OBJ;
        return getGsonBuilder().toJson(param);
    }

    public static String toJsonWithoutEscapeMap(List<Map<String, Object>> params) {
        if (params == null || params.size() == 0) return DEFAULT_OBJ;
        return getGsonBuilder().toJson(params);
    }

    public static String toJson(Map<String, Object> params) {
        if (params == null) return null;
        return getGson().toJson(params);
    }

    /**
     * 根据json字符串返回Map对象
     *
     * @param json
     * @return
     */
    public static Map<String, Object> toMap(String json) {
        return HSON.toMap(HSON.parseJson(json));
    }

    /**
     * 将JSONObjec对象转换成Map-List集合
     *
     * @param json
     * @return
     */
    public static Map<String, Object> toMap(JsonObject json) {
        Map<String, Object> map = new HashMap<String, Object>();
        Set<Entry<String, JsonElement>> entrySet = json.entrySet();
        for (Iterator<Entry<String, JsonElement>> iter = entrySet.iterator(); iter.hasNext(); ) {
            Entry<String, JsonElement> entry = iter.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof JsonArray)
                map.put((String) key, toList((JsonArray) value));
            else if (value instanceof JsonObject)
                map.put((String) key, toMap((JsonObject) value));
            else
                map.put((String) key, value);
        }
        return map;
    }

    /**
     * 将JSONArray对象转换成List集合
     *
     * @param json
     * @return
     */
    public static List<Object> toList(JsonArray json) {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < json.size(); i++) {
            Object value = json.get(i);
            if (value instanceof JsonArray) {
                list.add(toList((JsonArray) value));
            } else if (value instanceof JsonObject) {
                list.add(toMap((JsonObject) value));
            } else {
                list.add(value);
            }
        }
        return list;
    }

    /**
     * 获取JsonObject
     *
     * @param json
     * @return
     */
    public static JsonObject parseJson(String json) {
        JsonParser parser = new JsonParser();
        JsonObject jsonObj = parser.parse(json).getAsJsonObject();
        return jsonObj;
    }

    /**
     * 后台数据返回形式比较乱，全部为空则报异常
     * <p></>排除code：200
     *
     * @param s
     * @return
     */
    public static boolean checkCallbackllegal(String s) {

        JsonObject json = HSON.parseJson(s);
        String dataStr = json.get("data").toString();
        String code = json.get("code").toString().replace("\"", "");
        if (TextUtils.isEmpty(dataStr) | "null".equals(dataStr))
            return true;
        if (!TextUtils.isEmpty(dataStr) && (dataStr.equals("[]") | dataStr.equals("{}"))) {
            if (!code.equals("200")) {
                LogUtil.i(LOG_TAG, "checkCallbackllegal s:" + s);
                return true;
            }
        }
        return false;
    }

    /**
     * 后台数据返回形式比较乱，全部为空则报异常
     *
     * @param s
     * @return
     */
    public static boolean checkCallbackllegalAll(String s) {
        JsonObject json = HSON.parseJson(s);
        String dataStr = json.get("data").toString();
        String code = json.get("code").toString().replace("\"", "");
        if (TextUtils.isEmpty(dataStr) | "null".equals(dataStr))
            return true;
        if (!TextUtils.isEmpty(dataStr) && (dataStr.equals("[]") | dataStr.equals("{}"))) {
            LogUtil.i(LOG_TAG, "checkCallbackllegal s:" + s);
            return true;
        }
        return false;
    }

    public static String getCode(String s) {
        JsonObject json = HSON.parseJson(s);
        String code = json.get("code").toString().replace("\"", "");
        return code;
    }

    public static String getMsg(String s) {
        JsonObject json = HSON.parseJson(s);
        String msg = json.get("msg").toString().replace("\"", "");
        return msg;
    }

}
