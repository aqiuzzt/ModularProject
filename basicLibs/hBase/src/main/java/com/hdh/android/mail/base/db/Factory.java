package com.hdh.android.mail.base.db;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.hdh.android.mail.base.db.DatabaseFactory.DAO_CATEGORY;
import static com.hdh.android.mail.base.db.DatabaseFactory.DAO_USER;

@Retention(RetentionPolicy.SOURCE)
@StringDef(value = {DAO_USER,DAO_CATEGORY})
public @interface Factory {
}