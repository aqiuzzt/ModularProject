package com.hdh.common.util;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/21 20:18
 */
public class LogUtil {

    private static String TAG = "LogUtil";
    private static boolean DEBUG = false;
    private static final int JSON_INDENT = 2;

    public static void debugEnabled(String tag, boolean enable) {
        TAG = tag;
        DEBUG = enable;
    }


    private static String printStackTrace() {
        StackTraceElement e = getStackTraceElement();
        StringBuilder buf = new StringBuilder(80);
        if (e.isNativeMethod()) {
            buf.append("(Native Method)");
        } else {
            String fName = e.getFileName();
            if (fName == null) {
                buf.append("(Unknown Source)");
            } else {
                int lineNum = e.getLineNumber();
                buf.append('(');
                buf.append(fName);
                if (lineNum >= 0) {
                    buf.append(':');
                    buf.append(lineNum);
                }
                buf.append(')');
            }
        }
        return buf.toString();
    }

    private static StackTraceElement getStackTraceElement() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        StackTraceElement target = null;
        boolean shouldTrace = false;
        for (StackTraceElement e :
                elements) {
            boolean isLogMethod = e.getClassName().equals(LogUtil.class.getName());
            if (shouldTrace && !isLogMethod) {
                target = e;
                break;
            }
            shouldTrace = isLogMethod;
        }
        return target;
    }

    public static void d(Object msg) {
        d(TAG, msg);
    }

    public static void d(String tag, Object msg) {
        if (DEBUG) {
            Log.d(tag, String.format("%s                  %s", msg, printStackTrace()));
        }
    }

    public static void e(Object msg) {
        d(TAG, msg);
    }

    public static void e(String tag, Object msg) {
        if (DEBUG) {
            Log.e(tag, String.format("%s                  %s", msg, printStackTrace()));
        }
    }

    public static void i(Object msg) {
        d(TAG, msg);
    }

    public static void i(String tag, Object msg) {
        if (DEBUG) {
            Log.i(tag, String.format("%s                  %s", msg, printStackTrace()));
        }
    }

    public static void w(Object msg) {
        d(TAG, msg);
    }

    public static void w(String tag, Object msg) {
        if (DEBUG) {
            Log.i(tag, String.format("%s                  %s", msg, printStackTrace()));
        }
    }

    public static void json(String json) {
        d(TAG, json);
    }

    public static void json(String tag, String json) {
        if (DEBUG) {
            if (TextUtils.isEmpty(json)) {
                d("Empty/Null json content");
                return;
            }
            try {
                json = json.trim();
                if (json.startsWith("{")) {
                    JSONObject jsonObject = new JSONObject(json);
                    String message = jsonObject.toString(JSON_INDENT);
                    d(tag, message);
                    return;
                }
                if (json.startsWith("[")) {
                    JSONArray jsonArray = new JSONArray(json);
                    String message = jsonArray.toString(JSON_INDENT);
                    d(tag, message);
                    return;
                }
                e(tag, "Invalid Json");
            } catch (JSONException e) {
                e(tag, "Invalid Json");
            }
        }
    }

}
