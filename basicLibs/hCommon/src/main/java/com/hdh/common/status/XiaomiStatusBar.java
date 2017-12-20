package com.hdh.common.status;

import android.app.Activity;
import android.view.Window;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class XiaomiStatusBar implements StatusBarDelegate {

    @Override
    public boolean lightMode(Activity activity) {
        boolean flag = false;
        Window window = activity.getWindow();
        Class clazz = window.getClass();
        try {
            int tranceFlag = 0;
            int darkModeFlag = 0;
            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class); //只需要状态栏透明
            extraFlagField.invoke(window, 0, darkModeFlag);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }


    @Override
    public boolean darkMode(Activity activity) {
        boolean flag = false;
        Window window = activity.getWindow();
        Class clazz = window.getClass();
        try {
            int darkModeFlag = 0;
            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class); //只需要状态栏透明
            extraFlagField.invoke(window, darkModeFlag, darkModeFlag); //状态栏透明且黑色字体
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
