package com.hdh.common.monitor;

import android.app.Application;


public class NetworkMonitorApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NetworkMonitor.getInstance().initialise(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        NetworkMonitor.getInstance().release();
    }
}
