package com.hdh.common.status;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;


public class AndroidSdk23StatusBar implements StatusBarDelegate {
    /**
     * 状态栏透明/并且状态栏文字颜色为亮色
     *
     * @param activity
     * @return
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean lightMode(Activity activity) {
        boolean flag = false;
        try {
            View decorView = activity.getWindow().getDecorView();
            int option = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            }
            decorView.setSystemUiVisibility(option);
//            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            activity.getWindow().setStatusBarColor(Color.WHITE);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 状态栏透明/并且状态栏文字颜色为暗色
     *
     * @param activity
     * @return
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean darkMode(Activity activity) {
        boolean flag = false;
        try {
            View decorView = activity.getWindow().getDecorView();
            int option = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_VISIBLE;
            } else {
                option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            }
            decorView.setSystemUiVisibility(option);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
