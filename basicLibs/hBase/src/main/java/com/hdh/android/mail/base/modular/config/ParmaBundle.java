package com.hdh.android.mail.base.modular.config;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;

import java.io.Serializable;

/**
 * 跳转封装参数类
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/20 14:34
 */
public class ParmaBundle {
    private Bundle bundle;

    public ParmaBundle() {
        bundle = new Bundle();
    }

    public ParmaBundle put(String key, int value) {
        bundle.putInt(key, value);
        return this;
    }

    public ParmaBundle put(String key, long value) {
        bundle.putLong(key, value);
        return this;
    }

    public ParmaBundle put(String key, String value) {
        if (TextUtils.isEmpty(value)) {
            return this;
        }
        bundle.putString(key, value);
        return this;
    }

    public ParmaBundle put(String key, Serializable value) {
        if (value == null) {
            return this;
        }
        bundle.putSerializable(key, value);
        return this;
    }

    public ParmaBundle put(String key, Parcelable value) {
        if (value == null) {
            return this;
        }
        bundle.putParcelable(key, value);
        return this;
    }

    public ParmaBundle put(String key, String[] arrays) {
        if (arrays == null) {
            return this;
        }
        bundle.putStringArray(key, arrays);
        return this;
    }

    public Bundle build() {
        return bundle;
    }
}
