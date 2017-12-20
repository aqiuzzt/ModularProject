package com.hdh.common.monitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.hdh.common.util.app.AppNetworkUtil;


public class NetworkMonitor extends BroadcastReceiver {
    private boolean isFirst;
    private static NetworkMonitor sMonitor;

    private NetworkObservable observable;

    public void addObserver(NetworkObserver observer) {
        checkNotNull();
        this.observable.addObserver(observer);
    }

    private void checkNotNull() {
        if (this.observable == null) throw new IllegalStateException("必须先调用初始化方法(initialise)");
    }

    public void removeObserver(NetworkObserver observer) {
        checkNotNull();
        this.observable.deleteObserver(observer);
    }

    public void release() {
        checkNotNull();
        this.observable.deleteObservers();
    }

    public static synchronized NetworkMonitor getInstance() {
        if (sMonitor == null) {
            synchronized (NetworkMonitor.class) {
                if (sMonitor == null) {
                    sMonitor = new NetworkMonitor();
                }
            }
        }
        return sMonitor;
    }

    public void initialise(Context context) {
        Context appContext = context.getApplicationContext();
        this.observable = new NetworkObservable(appContext);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        isFirst = true;
        appContext.registerReceiver(this, filter);
    }

    private void notifyNetworkState(Context context) {
        checkNotNull();
        NetworkInfo networkInfo = AppNetworkUtil.getCurrentActiveNetwork(context);
        if (networkInfo != null) {
            if (!networkInfo.isAvailable()) {
                this.observable.notifyObservers(new NetworkObserver.Action(false, false, AppNetworkUtil.getSubType(context, networkInfo)));
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                this.observable.notifyObservers(new NetworkObserver.Action(true, true, AppNetworkUtil.getSubType(context, networkInfo)));
            } else if (networkInfo.getType() != ConnectivityManager.TYPE_BLUETOOTH) {
                this.observable.notifyObservers(new NetworkObserver.Action(true, false, AppNetworkUtil.getSubType(context, networkInfo)));
            } else {
                this.observable.notifyObservers(new NetworkObserver.Action(false, false, AppNetworkUtil.getSubType(context, networkInfo)));
            }
            return;
        }
        this.observable.notifyObservers(new NetworkObserver.Action(false, false, AppNetworkUtil.getSubType(context, networkInfo)));
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction()) && !isFirst) {
            this.notifyNetworkState(context);
        } else {
            isFirst = false;
        }
    }
}
