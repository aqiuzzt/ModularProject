package com.hdh.common.status;

import android.app.Activity;
import android.support.annotation.NonNull;


public class StatusBarImpl {

    private StatusBarDelegate mDelegate;

    public StatusBarImpl(@NonNull StatusBarDelegate delegate) {
        mDelegate = delegate;
    }

    public boolean lightMode(Activity activity) {
        return mDelegate.lightMode(activity);
    }

    public boolean darkMode(Activity activity) {
        return mDelegate.darkMode(activity);
    }
}
