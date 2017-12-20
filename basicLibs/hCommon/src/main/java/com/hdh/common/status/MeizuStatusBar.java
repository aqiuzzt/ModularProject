package com.hdh.common.status;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;


public class MeizuStatusBar implements StatusBarDelegate {
    @Override
    public boolean lightMode(Activity activity) {
        boolean flag = false;
        Window window = activity.getWindow();
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                value &= ~bit;
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                flag = true;
            } catch (Exception e) {
                flag = false;
            }
        }
        return flag;
    }

    @Override
    public boolean darkMode(Activity activity) {
        boolean flag = false;
        Window window = activity.getWindow();
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                value |= bit;
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                flag = true;
            } catch (Exception e) {
                flag = false;
            }
        }
        return flag;
    }
}
