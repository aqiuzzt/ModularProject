package com.hdh.mall.home.main.bean;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

/**
 * Created by albert on 2017/9/25.
 */

public class HomeCategoryItem {

    public static final int TYPE_RECHARGE = 0;
    public static final int TYPE_BUYBACK = 1;
    public static final int TYPE_RECOMMEND = 2;
    public static final int TYPE_BECOME_VIP = 3;


    @StringRes
    public int title;
    @DrawableRes
    public int icon;
    /**
     * 类型
     */
    public int type;

    @DrawableRes
    public int background;

    public HomeCategoryItem(int title, int icon, int type, int bg) {
        this.title = title;
        this.icon = icon;
        this.type = type;
        this.background = bg;
    }
}
