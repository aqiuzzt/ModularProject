package com.hdh.android.mail.base.bean;

import android.support.annotation.IntDef;


import com.hdh.android.mail.base.Constant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Desc:
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({Constant.ROLE_CONSUMER, Constant.ROLE_MERCHANT, Constant.ROLE_VIP, Constant.ROLE_PROVINCIAL_AGENT})
public @interface Role {
}
