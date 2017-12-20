package com.hdh.android.mail.base.db.city;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;




/**
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/30 10:14
 * 
 */
public class CityOpenDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "city";
    private static final int DATABASE_VERSION = 1;
    public CityOpenDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override public void onCreate(SQLiteDatabase db) {

    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
