package com.hdh.android.mail.base.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.reflect.TypeToken;
import com.hdh.common.util.HSON;

/**
 * Desc:
 * Author:Martin
 * Date:2016/10/24
 */

public class ProfileStorage {
    private final SharedPreferences mSharePreferences;

    public ProfileStorage(Context context, String name) {
        mSharePreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public SharedPreferences getPreferences() {
        return mSharePreferences;
    }

    public void clear() {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.clear();
        editor.apply();
    }

    @SuppressLint("CommitPrefEdits")
    public void commit(String key, String value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    @SuppressLint("CommitPrefEdits")
    public void commit(String key, int value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putInt(key, value);
        editor.commit();
    }

    @SuppressLint("CommitPrefEdits")
    public void commit(String key, boolean value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    @SuppressLint("CommitPrefEdits")
    public void commit(String key, long value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putLong(key, value);
        editor.commit();
    }

    @SuppressLint("CommitPrefEdits")
    public void commit(String key, float value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putFloat(key, value);
        editor.commit();
    }


    public void apply(String key, String value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void apply(String key, boolean value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void apply(String key, long value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public void apply(String key, float value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public void apply(String key, int value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void commitJson(@NonNull String key, @NonNull String value) {
        commit(key, value);
    }

    public void applyJson(@NonNull String key, @NonNull String value) {
        apply(key, value);
    }

    public int getInt(String key) {
        return getInt(key, -1);
    }

    public int getInt(String key, int defaultValue) {
        return getPreferences().getInt(key, defaultValue);
    }

    public String getString(String key, String defaultValue) {
        return getPreferences().getString(key, defaultValue);
    }

    @Nullable
    public String getString(String key) {
        return getPreferences().getString(key, null);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return getPreferences().getBoolean(key, defaultValue);
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public long getLong(String key, long defaultValue) {
        return getPreferences().getLong(key, defaultValue);
    }

    public long getLong(String key) {
        return getPreferences().getLong(key, 0L);
    }

    public float getFloat(String key, float defaultValue) {
        return getPreferences().getFloat(key, defaultValue);
    }

    public float getFloat(String key) {
        return getPreferences().getFloat(key, 0f);
    }

    @Nullable
    public <T> T getJsonObj(String key, Class<T> clazz) {
        String json = getPreferences().getString(key, null);
        if (json == null) return null;
        return HSON.parse(json, clazz);
    }

    public <T> T getJsonObj(String key, TypeToken<T> tTypeToken) {
        String json = getPreferences().getString(key, null);
        if (json == null) return null;
        return HSON.parse(json, tTypeToken);
    }


}
