package com.hdh.common.http.annotation;

import android.support.annotation.StringDef;

import com.hdh.common.http.util.HttpConstants;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/21 20:20
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({HttpConstants.GET, HttpConstants.POST, HttpConstants.PUT,HttpConstants.DELETE})
public @interface Method {
}
