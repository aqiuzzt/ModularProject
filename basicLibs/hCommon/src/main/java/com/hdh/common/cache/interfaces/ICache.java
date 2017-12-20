package com.hdh.common.cache.interfaces;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Cache methods
 */
public interface ICache {



	/*
     * ========================================================================
	 * String 
	 * ========================================================================
	 */

    public void put(String key, String value);

    public void put(String key, String value, int cacheTime, int timeUnit);

    public String getAsString(String key);

	/*
	 * ========================================================================
	 * JSONObject 
	 * ========================================================================
	 */

    public void put(String key, JSONObject jsonObject);

    public void put(String key, JSONObject jsonObject, int cacheTime, int timeUnit);

    public JSONObject getAsJSONObject(String key);

	/*
	 * ========================================================================
	 * JSONArray 
	 * ========================================================================
	 */

    public void put(String key, JSONArray jsonArray);

    public void put(String key, JSONArray jsonArray, int cacheTime, int timeUnit);

    public JSONArray getAsJSONArray(String key);

	/*
	 * ========================================================================
	 * Byte 
	 * ========================================================================
	 */

    public void put(String key, byte[] value);

    public void put(String key, byte[] value, int cacheTime, int timeUnit);

    public byte[] getAsBytes(String key);

	/*
	 * ========================================================================
	 * Serializable
	 * ========================================================================
	 */

    public void put(String key, Serializable value);

    public void put(String key, Serializable value, int cacheTime, int timeUnit);

    public <T> T getAsSerializable(String key);

	/*
	 * ========================================================================
	 * Bitmap 
	 * ========================================================================
	 */

    public void put(String key, Bitmap bitmap);

    public void put(String key, Bitmap bitmap, int cacheTime, int timeUnit);

    public Bitmap getAsBitmap(String key);

	/*
	 * ========================================================================
	 * Drawable 
	 * ========================================================================
	 */

    public void put(String key, Drawable value);

    public void put(String key, Drawable value, int cacheTime, int timeUnit);

    public Drawable getAsDrawable(String key);

	/*
	 * ========================================================================
	 * Other methods
	 * ========================================================================
	 */

    public boolean remove(String key);

}
