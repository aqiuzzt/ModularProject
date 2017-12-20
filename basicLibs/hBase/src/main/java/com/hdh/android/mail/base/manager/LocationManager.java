package com.hdh.android.mail.base.manager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;


/**
 * 高德地图定位操作
 * 默认启动定位后,如果位置信息没有发生任何变化,在超过定位时间时会将上一次的位置信息返回
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/30 12:06
 */

public class LocationManager {
    private static final int TIME_OUT = 20000;
    private AMapLocationClient mLocationClient;
    private LocationCallback mLocationCallback;

    private AMapLocationListener aMapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation == null) aMapLocation = mLocationClient.getLastKnownLocation();
            notifyCallbacks(aMapLocation);
            mLocationClient.stopLocation();
        }
    };

    void notifyCallbacks(AMapLocation aMapLocation) {
        if (mLocationCallback != null) {
            mLocationCallback.onLocationChanged(aMapLocation);
        }
    }

    /**
     * 启动定位
     */
    public LocationManager startLocation() {
        if (mLocationClient == null) throw new IllegalStateException("must register first!");
        if (!mLocationClient.isStarted()) {
            mLocationClient.startLocation();
        }
        return this;
    }

    /**
     * 关闭定位
     */
    public void stopLocation() {
        if (mLocationClient == null) throw new IllegalStateException("must register first!");
        if (mLocationClient.isStarted()) {
            mLocationClient.stopLocation();
        }
    }

    public AMapLocation getLastKnowLocation() {
        if (mLocationClient == null) throw new IllegalStateException("must register first!");
        return mLocationClient.getLastKnownLocation();
    }

    public LocationManager register(Context appContext) {
        mLocationClient = new AMapLocationClient(appContext.getApplicationContext());
        mLocationClient.setLocationListener(aMapLocationListener);
        setupLocationClientOption();
        return this;
    }

    private void setupLocationClientOption() {
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//高精度模式
        mLocationOption.setOnceLocation(false);//获取一次定位结果
        mLocationOption.setOnceLocationLatest(false); //动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        //设置是否强制刷新WIFI，默认为true，强制刷新。
//        mLocationOption.setWifiActiveScan(false);
        mLocationOption.setHttpTimeOut(TIME_OUT); ////单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        //定位结果缓存机制
        mLocationOption.setLocationCacheEnable(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
    }

    public LocationManager addLocationCallback(@NonNull LocationCallback callback) {
        this.mLocationCallback = callback;
        return this;
    }

    public LocationManager removeLocationCallback(@NonNull LocationCallback callback) {
        this.mLocationCallback = null;
        return this;
    }

    /**
     * 销毁所有的对象
     */
    public void destroy() {
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.unRegisterLocationListener(aMapLocationListener);
            mLocationClient.onDestroy();
        }
        removeLocationCallback(null);
    }

    public interface LocationCallback {
        /**
         * 如果定位失败,会获取上一次定位成功的值
         *
         * @param location
         */
        void onLocationChanged(@Nullable AMapLocation location);
    }
}
