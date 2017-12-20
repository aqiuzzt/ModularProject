package com.hdh.android.mail.base.bean;

/**
 * Created by Martin on 2017/3/29.
 */

public class DeviceUUIDRequest {
    /**
     * └deviceUid	default value	N	设备唯一id
     * └deviceName	default value	N	设备名称
     * └deviceType	default value	N	设备类型：11 Android 12 IOS
     * └deviceModel	default value	N	设备型号
     * └imei	default value	N	手机imei序列号
     * └deviceSdkVersion	default value	N	设备系统版本
     * └mac	default value	N	手机mac地址
     * └deviceSn	default value	N	设备序列号
     * └androidId	default value	N	Android设备的ANDROID_ID
     * └iosVendorId	default value	N	IOS设备的Vendor标识符
     * └iosUuid	default value	N	IOS设备的UUID(OPEN UUID)
     * └iosUdid	default value	N	IOS设备的UDID(OPEN UDID)
     * └iosAdId	default value	N	IOS设备的广告标识符
     * └displayScreenWidth	default value	N	手机屏幕宽度
     * └displayScreenHeight	default value	N	手机屏幕高度
     * └displayDpi	default value	N	手机屏幕密度/每英寸的像素数目
     * └iosDeviceToken	default value	N	IOS设备的DEVICE_TOKEN
     * └appName	default value	N	app名称：MQ、DAQ
     * └appVersion	default value	N	app版本
     * └appStage	default value	N	MuApplication 阶段状态, 可填: demo, alpha, beta, rc, release'
     * └appDistribution	default value	N	MuApplication 分发渠道简称
     * └accountId	default value	N	accountId
     */
    public String deviceUid;
    public String deviceName;
    public String deviceType;
    public String deviceModel;
    public String imei;
    public String deviceSdkVersion;
    public String mac;
    public String deviceSn;
    public String androidId;
    public String displayScreenWidth;
    public String displayScreenHeight;
    public String displayDpi;
    public String appName;
    public String appVersion;
    public String appStage;
    public String appDistribution;
    public String accountId;
}
