package com.hdh.common.util.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.hdh.common.bean.AppInfo;
import com.hdh.common.util.LogUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


/**
 * Created by Martin on 2017/3/26.
 */

public class AppUtil {

    public static final String LOG_TAG = "AppUtil";

    public static String[] mapPaks = new String[]{"com.autonavi.minimap", "com.baidu.BaiduMap", "com.google.android.apps.maps"};


    /**
     * 是否安装微信
     *
     * @param context
     * @return
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 返回当前设备上的地图应用集合
     *
     * @param context
     * @return
     */
    public static List<AppInfo> getMapApps(Context context) {
        LinkedList<AppInfo> apps = new LinkedList<AppInfo>();

        for (String pak : mapPaks) {
            AppInfo appinfo = getAppInfoByName(context, pak);
            if (appinfo != null) {
                apps.add(appinfo);
            }
        }
        return apps;
    }

    public static List<String> getMapInstalledName(Context context) {
        List<String> apps = new ArrayList<>();
        for (String pak : mapPaks) {
            boolean isInstalled = isInstalled(context, pak);
            if (isInstalled) {
                apps.add(pak);
            }
        }
        return apps;
    }

    /**
     * 通过包名获取应用信息
     *
     * @param context
     * @param packageName
     * @return
     */
    public static AppInfo getAppInfoByName(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        for (PackageInfo packageInfo : packageInfos) {
            if (packageName.equals(packageInfo.packageName)) {
                AppInfo tmpInfo = new AppInfo();
                tmpInfo.setAppName(packageInfo.applicationInfo.loadLabel(packageManager).toString());
                tmpInfo.setPackageName(packageInfo.packageName);
                tmpInfo.setVersionName(packageInfo.versionName);
                tmpInfo.setVersionCode(packageInfo.versionCode);
                tmpInfo.setAppIcon(packageInfo.applicationInfo.loadIcon(packageManager));
                return tmpInfo;
            }
        }
        return null;
    }

    /**
     * 是否安装这个app
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isInstalled(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        for (PackageInfo packageInfo : packageInfos) {
            if (packageName.equals(packageInfo.packageName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 帮助中心打电话
     *
     * @param mPhoneStr
     */
    public static void callHelp(Context context, String mPhoneStr) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + mPhoneStr);
        intent.setData(data);
        context.startActivity(intent);
    }


    /**
     * 获取ａｐｐ版本
     *
     * @param context
     * @return
     */
    public static int getAppVersionCode(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 获取APP版本名
     */
    public static String getAppVersionName(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 获取imei号
     *
     * @param mContext
     * @return
     */
    public static String getImei(Context mContext) {
        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();
        return imei;
    }

    /**
     * 获取电话号码
     *
     * @param mContext
     * @return
     */
    public static String getPhoneNumber(Context mContext) {
        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        String tel = tm.getLine1Number();
        return tel;
    }


    public static String getModel() {
        return Build.MODEL;
    }

    public static String getAndroidModel() {
        return Build.MODEL;
    }

    public static String getVersion() {
        return String.valueOf(Build.VERSION.SDK_INT);
    }


    /**
     * 获取外网IP
     *
     * @return
     */
    public static String getOutSideNetworkIp() {
        URL infoUrl = null;
        InputStream inStream = null;
        String ipLine = "";
        HttpURLConnection httpConnection = null;
        try {
            infoUrl = new URL("http://pv.sohu.com/cityjson?ie=utf-8");
            URLConnection connection = infoUrl.openConnection();
            httpConnection = (HttpURLConnection) connection;
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inStream = httpConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
                StringBuilder strber = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    strber.append(line + "\n");
                }
                Pattern pattern = Pattern
                        .compile("((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))");
                Matcher matcher = pattern.matcher(strber.toString());
                if (matcher.find()) {
                    ipLine = matcher.group();
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inStream.close();
                httpConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        LogUtil.e(LOG_TAG, ipLine);
        return ipLine;
    }


    /**
     * 获取IP地址
     *
     * @param context
     * @return
     */
    public static String getIP(Context context) {
        String ipv4 = null;
        //获取wifi服务
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            ipv4 = getLocalIpAddress();
            if (!TextUtils.isEmpty(ipv4)) {
                LogUtil.i(LOG_TAG, "ipv4:" + ipv4);
                return ipv4;
            }

            if (!TextUtils.isEmpty(getIpv6Address())) {
                LogUtil.i(LOG_TAG, "getIpv6Address:" + getIpv6Address());
                return getIpv6Address();
            }

        } else {
            String outIp = getOutSideNetworkIp();
            if (TextUtils.isEmpty(outIp)) {
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                int ipAddress = wifiInfo.getIpAddress();
                String ip = intToIp(ipAddress);
                LogUtil.i(LOG_TAG, "ip:" + ip);
                return ip;
            } else {
                return outIp;
            }
        }
        return "127.0.0.1";
    }

    /**
     * 获取ipv6
     *
     * @return
     */
    public static String getIpv6Address() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {

                        //fe80::94c5:b0ff:fef7:541b%dummy0
                        String ipv6 = inetAddress.getHostAddress();
                        LogUtil.e(LOG_TAG, "IP inetAddress：" + ipv6);
                        if (!TextUtils.isEmpty(ipv6)) {
                            if (ipv6.length() >= 25)
                                return ipv6.substring(0, 25);
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取ipv4
     *
     * @return
     */
    public static String getLocalIpAddress() {
        try {
            ArrayList<NetworkInterface> nilist = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface ni : nilist) {
                ArrayList<InetAddress> ialist = Collections.list(ni.getInetAddresses());
                for (InetAddress address : ialist) {
                    if (!address.isLoopbackAddress() && address instanceof Inet4Address) {
                        return address.getHostAddress();
                    }
                }
            }

        } catch (SocketException ex) {
            LogUtil.e(LOG_TAG, ex.toString());
        }
        return null;
    }


    private static String intToIp(int i) {

        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }


    /**
     * 隐藏软键盘
     *
     * @param view
     */
    public static void HideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }
    }

    /**
     * 隐藏软键盘
     */
    public static void isGoneKeyBoard(Activity activity) {
        if (activity.getCurrentFocus() != null) {
            // 隐藏软键盘
            ((InputMethodManager) activity
                    .getSystemService(activity.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(activity.getCurrentFocus()
                                    .getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    public static void hideSoftInput(Context context, IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 读取渠道号
     *
     * @param context
     * @param name    统计平台
     * @return
     */
    public static String getApplicationMetaValue(Context context, String name) {
        String value = "";
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            value = appInfo.metaData.getString(name);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return value;
    }


    /**
     * 从apk中获取版本信息
     *
     * @param context
     * @param channelPrefix 渠道前缀
     * @return
     */
    public static String getChannelFromApk(Context context, String channelPrefix) {
        //从apk包中获取
        ApplicationInfo appinfo = context.getApplicationInfo();
        String sourceDir = appinfo.sourceDir;
        //默认放在meta-inf/里， 所以需要再拼接一下
        String key = "META-INF/" + channelPrefix;
        String ret = "";
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(sourceDir);
            Enumeration<?> entries = zipfile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (entryName.startsWith(key)) {
                    ret = entryName;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zipfile != null) {
                try {
                    zipfile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String[] split = ret.split(channelPrefix);
        String channel = "";
        if (split != null && split.length >= 2) {
            channel = ret.substring(key.length());
        }
        return channel;
    }


    /**
     * 获取download目录，没有则创建之
     *
     * @param context
     * @return
     */
    public static File getDownloadDir(Context context) {
        boolean externalStorageAvailable = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        final String cachePath;
        if (externalStorageAvailable) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        File diskCacheDir = new File(cachePath + File.separator + "download");
        if (!diskCacheDir.exists()) {
            diskCacheDir.mkdirs();
        }
        return diskCacheDir;
    }


    public static String multipBigNumber(String s1, String s2) {
        int longArray[] = null;
        int shortArray[] = null;
        int s1Length = s1.length();
        int s2Length = s2.length();
        int longLength = s1Length > s2Length ? s1Length : s2Length;
        int shortLength = s1Length == longLength ? s2Length : s1Length;
        longArray = new int[longLength];
        shortArray = new int[shortLength];
        String longString = s1Length >= s2Length ? s1 : s2;
        String shortString = s1.equals(longString) ? s2 : s1;
        System.out.println("longString=" + longString + "; shortString=" + shortString);
        //低位在前，高位在后
        for (int i = longLength - 1; i >= 0; i--) {
            longArray[longLength - 1 - i] = longString.charAt(i) - 48;
        }
        for (int i = shortLength - 1; i >= 0; i--) {
            shortArray[shortLength - 1 - i] = shortString.charAt(i) - 48;
        }

        StringBuffer results[] = new StringBuffer[longLength];
        for (int i = 0; i < results.length; i++) {
            results[i] = new StringBuffer();
        }
        StringBuffer resultBuffer = new StringBuffer();

        /**
         * 把被乘数的每一位与乘数逐位相乘
         * 如：5607 * 2256,被乘数5607，乘数2256
         * 7 * 6 + 0 = 42 ：0表示低位向高位的进位
         7 * 5 + 4 = 39
         7 * 2 + 3 = 17
         7 * 2 + 1 = 15
         29751
         0 * 6 + 0 = 0
         0 * 5 + 0 = 0
         0 * 2 + 0 = 0
         0 * 2 + 0 = 0
         00000
         6 * 6 + 0 = 36
         6 * 5 + 3 = 33
         6 * 2 + 3 = 15
         6 * 2 + 1 = 13
         63531
         5 * 6 + 0 = 30
         5 * 5 + 3 = 28
         5 * 2 + 2 = 12
         5 * 2 + 1 = 11
         08211
         *  results：29751， 00000，63531，08211
         * */
        for (int i = 0; i < longLength; i++) {
            int temp = 0;
            int tempCarry = 0;//低位向高位的进位
            int currentValue = 0; //当前位乘积的值
            for (int j = 0; j < shortLength; j++) {

                temp = longArray[i] * shortArray[j] + tempCarry;
                //System.out.println("longArray[i]="+longArray[i]+" * "+"shortArray[j]="+shortArray[j]+" + "+tempCarry+" = "+temp);
                tempCarry = temp / 10;
                currentValue = temp % 10;
                results[i].append(currentValue);
                if (j == shortLength - 1) {
                    results[i].append(tempCarry);//最高位有进位则进位，无进位则补0
                }
            }
            LogUtil.i("dd", results[i].toString());
        }
        /**低位补0，results[i]低位补i个0(低位在前，高位在后)
         * 29751
         000000
         0063531
         00008211
         *  */
        for (int i = 0, length = results.length; i < length; i++) {
            String temp = "";
            for (int j = 0; j < i; j++) {
                temp += "0";
            }
            results[i].insert(0, temp);
            System.out.println(results[i].toString());
        }

        /**把每一行相加(低位在前，高位在后,从左往右 加)
         * 29751
         000000
         0063531
         00008211
         -------------
         29394621
         *
         * */
        int tempCarry = 0;
        int currentValue = 0;
        StringBuffer lastBuffer = results[results.length - 1];
        //需要循环的次数
        int times = lastBuffer.length();

        for (int i = 0; i < times; i++) {
            int temp = 0;
            for (int j = 0, length2 = results.length; j < length2; j++) {
                if (results[j].length() > i) {
                    temp += (results[j].charAt(i) - 48);//把第j行的第i位的字符转换成int
                }
            }
            temp += tempCarry;
            tempCarry = temp / 10;
            currentValue = temp % 10;
            resultBuffer.append(currentValue);
        }

        StringBuffer finalResult = new StringBuffer();

        boolean hasNumNotZero = false;
        //因为低位在前，高位在后，所以把29394621 转换成最终结果：12649392
        for (int i = resultBuffer.length() - 1; i >= 0; i--) {
            int temp = resultBuffer.charAt(i) - 48;
            if (temp != 0) hasNumNotZero = true;
            if (hasNumNotZero) finalResult.append(temp);
        }
        if (finalResult.length() == 0) finalResult.append(0);
        return finalResult.toString();

    }


}
