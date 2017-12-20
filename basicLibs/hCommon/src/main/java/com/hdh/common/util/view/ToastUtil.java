package com.hdh.common.util.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import java.lang.ref.WeakReference;



public class ToastUtil {
    private static Toast sToast;
    private static Context sContext;
    private static ToastUtil sInstance;

    private ToastUtil() {

    }

    public void _init(Context appContext) {
        sContext = appContext.getApplicationContext();
        initToast();
    }

    private Toast getToast() {
        if (sToast == null) {
            initToast();
        }
        return sToast;
    }

    public static void init(Context context) {
        getInstance()._init(context);
    }

    private static ToastUtil getInstance() {
        if (sInstance == null) {
            sInstance = new ToastUtil();
        }
        return sInstance;
    }


    public static void show(Context context, String msg) {
        Toast toast = getInstance().getToast();
        toast.setText(msg);
        toast.show();
    }


    @SuppressLint("ShowToast")
    private void initToast() {
        if (sContext != null && sToast == null) {
            sToast = Toast.makeText(sContext,"",Toast.LENGTH_SHORT);
        }
    }

    public static void cancel(Context context) {
        getInstance()._cancel();
    }

    public static void destroy(Context context) {
        getInstance()._destroy();
    }


    @SuppressLint("ShowToast")
    public void _cancel() {
        if (sToast != null) {
            sToast.cancel();
        }
    }

    public void _destroy() {
        _cancel();
        sToast = null;
        new WeakReference<Context>(sContext);
    }
}
