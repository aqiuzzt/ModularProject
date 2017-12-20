package com.hdh.common.status;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.CheckResult;
import android.support.annotation.DimenRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class StatusBarUtil {
    public static final String MIUI = "xiaomi";
    public static final String FLYME = "meizu";

    /**
     * if ({@link Build.VERSION#PREVIEW_SDK_INT} >= {@link Build.VERSION_CODES#M}) <br>
     * { <br>
     * 1.如果是miui并且>=V6 用小米的 <br>
     * 2.其它情况用安卓系统的 <br>
     * }else if({@link Build.VERSION#PREVIEW_SDK_INT} >= {@link Build.VERSION_CODES#KITKAT}) { <br>
     * 1.如果是MIUI <br>
     * 2.如果是Flyme <br>
     * 3.其它情况不处理 <br>
     * }
     *
     * @param activity
     */
    @CheckResult
    public static boolean darkMode(Activity activity) {
        int sdkInt = Build.VERSION.SDK_INT;
        String brand = Build.BRAND;
        if (brand == null)
            return false;
        brand = brand.toLowerCase();
        if (sdkInt >= Build.VERSION_CODES.M) {
            if (MIUI.equals(brand) && isMiuiV6()) {
                if (new StatusBarImpl(new XiaomiStatusBar()).darkMode(activity)) {
                    immersionModelForTarget19(activity);
                    return true;
                }
            } else {
                if (new StatusBarImpl(new AndroidSdk23StatusBar()).darkMode(activity)) {
                    return true;
                }
            }
        } else if (sdkInt >= Build.VERSION_CODES.KITKAT) {
            switch (brand) {
                case MIUI:
                    if (isMiuiV6()) {
                        if (new StatusBarImpl(new XiaomiStatusBar()).darkMode(activity)) {
                            immersionModelForTarget19(activity);
                            return true;
                        }
                    }
                    break;
                case FLYME:
                    if (new StatusBarImpl(new MeizuStatusBar()).darkMode(activity)) {
                        immersionModelForTarget19(activity);
                        return true;
                    }
                    break;
            }
        }
        return false;
    }

    public static boolean darkMode1(Activity activity) {
        int sdkInt = Build.VERSION.SDK_INT;
        String brand = Build.BRAND;
        if (brand == null)
            return false;
        brand = brand.toLowerCase();
        if (sdkInt >= Build.VERSION_CODES.M) {

            if (new StatusBarImpl(new AndroidSdk23StatusBar()).darkMode(activity)) {
                return true;
            }

        } else if (sdkInt >= Build.VERSION_CODES.KITKAT) {
            switch (brand) {
                case MIUI:
                    if (isMiuiV6()) {
                        if (new StatusBarImpl(new XiaomiStatusBar()).darkMode(activity)) {
                            immersionModelForTarget19(activity);
                            return true;
                        }
                    }
                    break;
                case FLYME:
                    if (new StatusBarImpl(new MeizuStatusBar()).darkMode(activity)) {
                        immersionModelForTarget19(activity);
                        return true;
                    }
                    break;
            }
        }
        return false;
    }


    /**
     * if ({@link Build.VERSION#PREVIEW_SDK_INT} >= {@link Build.VERSION_CODES#M}) <br>
     * { <br>
     * 1.如果是miui并且>=V6 用小米的 <br>
     * 2.其它情况用安卓系统的 <br>
     * }else if({@link Build.VERSION#PREVIEW_SDK_INT} >= {@link Build.VERSION_CODES#KITKAT}) { <br>
     * 1.如果是MIUI <br>
     * 2.如果是Flyme <br>
     * 3.其它情况不处理 <br>
     * }
     *
     * @param activity
     */
    @CheckResult
    public static boolean lightMode(Activity activity) {
        int sdkInt = Build.VERSION.SDK_INT;
        String brand = Build.BRAND;
        if (brand == null)
            return false;
        brand = brand.toLowerCase();
        if (sdkInt >= Build.VERSION_CODES.M) {
            if (MIUI.equals(brand) && isMiuiV6()) {
                if (new StatusBarImpl(new XiaomiStatusBar()).lightMode(activity)) {
                    immersionModelForTarget19(activity);
                    return true;
                }
            } else {
                if (new StatusBarImpl(new AndroidSdk23StatusBar()).lightMode(activity)) {
                    return true;
                }
            }
        } else if (sdkInt >= Build.VERSION_CODES.KITKAT) {
            switch (brand) {
                case MIUI:
                    if (isMiuiV6()) {
                        if (new StatusBarImpl(new XiaomiStatusBar()).lightMode(activity)) {
                            immersionModelForTarget19(activity);
                            return true;
                        }
                    }
                    break;
                case FLYME:
                    if (new StatusBarImpl(new MeizuStatusBar()).lightMode(activity)) {
                        immersionModelForTarget19(activity);
                        return true;
                    }
                    break;
            }
        }
        return false;
    }


    public static boolean lightMode1(Activity activity) {
        int sdkInt = Build.VERSION.SDK_INT;
        String brand = Build.BRAND;
        if (brand == null)
            return false;
        brand = brand.toLowerCase();
        if (sdkInt >= Build.VERSION_CODES.M) {

            if (new StatusBarImpl(new AndroidSdk23StatusBar()).lightMode(activity)) {
                return true;
            }

        } else if (sdkInt >= Build.VERSION_CODES.KITKAT) {
            switch (brand) {
                case MIUI:
                    if (isMiuiV6()) {
                        if (new StatusBarImpl(new XiaomiStatusBar()).lightMode(activity)) {
                            immersionModelForTarget19(activity);
                            return true;
                        }
                    }
                    break;
                case FLYME:
                    if (new StatusBarImpl(new MeizuStatusBar()).lightMode(activity)) {
                        immersionModelForTarget19(activity);
                        return true;
                    }
                    break;
            }
        }
        return false;
    }

    @CheckResult
    public static boolean show(Activity activity) {
        int sdkInt = Build.VERSION.SDK_INT;
        String brand = Build.BRAND;
        if (brand == null)
            return false;
        brand = brand.toLowerCase();
        if (sdkInt >= Build.VERSION_CODES.M) {
            if (MIUI.equals(brand) && isMiuiV6()) {
                if (new StatusBarImpl(new XiaomiStatusBar()).darkMode(activity)) {
                    immersionModelForTarget19(activity);
                    return true;
                }
            } else {
                if (new StatusBarImpl(new AndroidSdk23StatusBar()).lightMode(activity)) {
                    return true;
                }
            }
        } else if (sdkInt >= Build.VERSION_CODES.KITKAT) {
            switch (brand) {
                case MIUI:
                    if (isMiuiV6()) {
                        if (new StatusBarImpl(new XiaomiStatusBar()).darkMode(activity)) {
                            immersionModelForTarget19(activity);
                            return true;
                        }
                    }
                    break;
                case FLYME:
                    if (new StatusBarImpl(new MeizuStatusBar()).lightMode(activity)) {
                        immersionModelForTarget19(activity);
                        return true;
                    }
                    break;
            }
        }
        return false;
    }


    /**
     * 延伸顶部导航栏到状态栏
     *
     * @param view
     */
    public static void stretchHeadNavigation(@Nullable View view, @DimenRes int height) {
        if (view == null || Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        Context context = view.getContext();
        int actionBarHeight = context.getResources().getDimensionPixelOffset(height);
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.height = actionBarHeight + getStatusBarHeight(view.getContext());
        view.setLayoutParams(lp);
    }

    /**
     * 延伸顶部导航栏到状态栏
     *
     * @param view
     */
    public static void stretchHeadNavigationDelay(@Nullable final View view) {
        if (view == null || Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                ViewGroup.LayoutParams lp = view.getLayoutParams();
                lp.height = view.getHeight() + getStatusBarHeight(view.getContext());
                view.setLayoutParams(lp);
            }
        });

    }

    /**
     * 状态栏透明
     *
     * @param activity
     */
    public static void immersionModelForTarget19(Activity activity) {
        // SYSTEM_UI_FLAG_IMMERSIVE, and SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            activity.getWindow().setAttributes(localLayoutParams);
        }
    }

    /**
     * 判断miui是否为大于
     *
     * @return
     */
    private static boolean isMiuiV6() {
        boolean sIsMiuiV6 = false;
        try {
            Class<?> sysClass = Class.forName("android.os.SystemProperties");
            Method getStringMethod = sysClass.getDeclaredMethod("get", String.class);
            String rom = (String) getStringMethod.invoke(sysClass, "ro.miui.ui.version.name");
            Log.d("TAG", "Rom Version:" + rom);
            sIsMiuiV6 = "V6".equals(rom);
            if (rom != null) {
                String s = rom.substring(1);
                int v = Integer.parseInt(s);
                sIsMiuiV6 = v >= 6;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sIsMiuiV6;
    }

    public static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }



    /**
     * 修改状态栏为全透明
     *
     * @param activity
     */
    @TargetApi(19)
    public static void transparencyBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 修改状态栏颜色，支持4.4以上版本
     *
     * @param activity
     * @param colorId
     */
    public static void setStatusBarColor(Activity activity, int colorId) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
//      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(colorId));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //使用SystemBarTint库使4.4版本状态栏变色，需要先将状态栏设置为透明
            transparencyBar(activity);
            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(colorId);
        }
    }

    /**
     * 状态栏亮色模式，设置状态栏黑色文字、图标，
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @param activity
     * @return 1:MIUUI 2:Flyme 3:android6.0
     */
    public static int StatusBarLightMode(Activity activity) {
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (MIUISetStatusBarLightMode(activity, true)) {
                result = 1;
            } else if (FlymeSetStatusBarLightMode(activity.getWindow(), true)) {
                result = 2;
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                result = 3;
            }
        }
        return result;
    }

    /**
     * 已知系统类型时，设置状态栏黑色文字、图标。
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @param activity
     * @param type     1:MIUUI 2:Flyme 3:android6.0
     */
    public static void StatusBarLightMode(Activity activity, int type) {
        if (type == 1) {
            MIUISetStatusBarLightMode(activity, true);
        } else if (type == 2) {
            FlymeSetStatusBarLightMode(activity.getWindow(), true);
        } else if (type == 3) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

    }

    /**
     * 状态栏暗色模式，清除MIUI、flyme或6.0以上版本状态栏黑色文字、图标
     */
    public static void StatusBarDarkMode(Activity activity, int type) {
        if (type == 1) {
            MIUISetStatusBarLightMode(activity, false);
        } else if (type == 2) {
            FlymeSetStatusBarLightMode(activity.getWindow(), false);
        } else if (type == 3) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }

    }


    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏文字及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
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
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 需要MIUIV6以上
     *
     * @param activity
     * @param dark     是否把状态栏文字及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean MIUISetStatusBarLightMode(Activity activity, boolean dark) {
        boolean result = false;
        Window window = activity.getWindow();
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                    if (dark) {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    } else {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    }
                }
            } catch (Exception e) {

            }
        }
        return result;
    }

}
