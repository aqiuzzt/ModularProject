package com.hdh.common.status;

import android.app.Activity;


public interface StatusBarDelegate {


    boolean lightMode(Activity activity);

    boolean darkMode(Activity activity);

}
