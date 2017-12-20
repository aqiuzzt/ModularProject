package com.hdh.android.mail.base.manager;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.hdh.android.mail.base.BaseApplication;
import com.hdh.android.mail.base.BuildConfig;
import com.hdh.android.mail.base.bean.DeviceUUIDRequest;
import com.hdh.android.mail.base.utils.ProfileStrorageUtil;
import com.hdh.common.util.LogUtil;
import com.hdh.common.util.view.UIUtil;

import java.io.File;
import java.lang.reflect.Method;

/**
 * 手机设备基本信息
 */

public class DeviceManager {
    private static final String LOG_TAG = "DeviceManager";

    private DeviceUUIDRequest mDeviceRequest;

    private static DeviceManager sManager;

    private DeviceManager() {
    }

    public static DeviceManager getManager() {
        if (sManager == null) {
            synchronized (DeviceManager.class) {
                if (sManager == null) {
                    sManager = new DeviceManager();
                }
            }
        }
        return sManager;
    }

    public DeviceUUIDRequest getDeviceRequest(@NonNull Context context) {
        Context appContext = context.getApplicationContext();
        mDeviceRequest = new DeviceUUIDRequest();
        mDeviceRequest.deviceUid = getImei(appContext);
        mDeviceRequest.appVersion = BuildConfig.VERSION_NAME;
        mDeviceRequest.androidId = getAndroidId(appContext);
        mDeviceRequest.imei = getImei(appContext);
        mDeviceRequest.deviceModel = getModel();
        mDeviceRequest.deviceName = getDeviceName();
        mDeviceRequest.deviceType = String.valueOf(11);
        mDeviceRequest.displayScreenHeight = String.valueOf(UIUtil.getScreenHeight(appContext));
        mDeviceRequest.displayScreenWidth = String.valueOf(UIUtil.getScreenWidth(appContext));
        mDeviceRequest.mac = getMac(appContext);
        mDeviceRequest.displayDpi = String.valueOf(UIUtil.getDisplayDPI(appContext));
        mDeviceRequest.deviceSdkVersion = String.valueOf(Build.VERSION.SDK_INT);
        mDeviceRequest.deviceSn = Build.SERIAL;
        mDeviceRequest.appDistribution = "android";
        mDeviceRequest.appName = "JCGY";
        mDeviceRequest.appStage = BuildConfig.BUILD_TYPE;
        return mDeviceRequest;
    }

    public void setDeviceUUID(String s) {
        LogUtil.i(LOG_TAG, "setDeviceUUID deviceUid:" + s);
        if (mDeviceRequest != null) {
            mDeviceRequest.deviceUid = s;
            ProfileStrorageUtil.setDeviceUid(s);
        }
    }


    public void setAccountId(String accountId) {
        getDeviceRequest(BaseApplication.get());
        mDeviceRequest.accountId = accountId;
    }


    private String getDeviceUid(Context appContext) {
        try {
            ApplicationInfo info = appContext.getPackageManager().getApplicationInfo(appContext.getPackageName(), PackageManager.MATCH_UNINSTALLED_PACKAGES);
            return String.valueOf(info.uid);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getOS() {
        try {
            return "Android 1";
        } catch (Exception var1) {
            return "";
        }
    }

    private String getImei(Context context) {
        String id = "12345678";
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (tm.getDeviceId() != null) {
                id = tm.getDeviceId();
            } else {
                //android.provider.Settings;
                id = Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                if (!TextUtils.isEmpty(id)) {
                    return id;
                } else {
                    return "12345678";
                }
            }
        } catch (Exception var2) {
            return "12345678";
        }
        return id;
    }


    private String getImsi(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getSubscriberId();
        } catch (Exception var2) {
            return "";
        }
    }

    private String getMac(Context context) {
        try {
            WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (wm == null) {
                return "";
            } else {
                WifiInfo wi = wm.getConnectionInfo();
                return wi != null ? wi.getMacAddress() : "";
            }
        } catch (Exception var3) {
            return "";
        }
    }

    private String getIccid(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getSimSerialNumber();
        } catch (Exception var2) {
            return "";
        }
    }

    private String getSerialNo() {
        String var0 = "";
        try {
            Class var1 = Class.forName("android.os.SystemProperties");
            Method var2 = var1.getMethod("get", new Class[]{String.class, String.class});
            var0 = (String) var2.invoke(var1, new Object[]{"ro.serialno", "unknown"});
        } catch (Exception var3) {
            ;
        }

        return var0;
    }

    private String getAndroidId(Context var0) {
        try {
            String var1 = Settings.Secure.getString(var0.getContentResolver(), "android_id");
            return var1;
        } catch (Exception var2) {
            return "";
        }
    }

    private static String getCpu() {
        try {
            return Build.CPU_ABI;
        } catch (Exception var1) {
            return "";
        }
    }

    private String getModel() {
        try {
            return Build.MODEL;
        } catch (Exception var1) {
            return "";
        }
    }

    private String getSdSize() {
        try {
            File var0 = Environment.getExternalStorageDirectory();
            StatFs var1 = new StatFs(var0.getPath());
            long var2 = (long) var1.getBlockSize();
            long var4 = (long) var1.getBlockCount();
            return Long.toString(var4 * var2);
        } catch (Exception var6) {
            return "";
        }
    }

    private String getResolution(Context var0) {
        try {
            DisplayMetrics var1 = new DisplayMetrics();
            WindowManager var2 = (WindowManager) var0.getSystemService(Context.WINDOW_SERVICE);
            var2.getDefaultDisplay().getMetrics(var1);
            return var1.widthPixels + "*" + var1.heightPixels;
        } catch (Exception var3) {
            return "";
        }
    }

    private String getSsid(Context context) {
        try {
            WifiManager var1 = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo var2 = var1.getConnectionInfo();
            if (var2 != null) {
                return var2.getSSID();
            }
        } catch (Exception var3) {
        }
        return "";
    }

    private String getDeviceName() {
        try {
            return Build.BRAND;
        } catch (Exception var1) {
            return "";
        }
    }
}
