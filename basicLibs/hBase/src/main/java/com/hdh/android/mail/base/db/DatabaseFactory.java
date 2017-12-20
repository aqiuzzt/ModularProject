package com.hdh.android.mail.base.db;

import android.support.annotation.NonNull;

import java.util.HashMap;

/**
 * 数据库工厂类
 */
@SuppressWarnings("unchecked")
public class DatabaseFactory {
    public static final String DAO_USER = "USER_DAO";
    public static final String DAO_CATEGORY = "USER_CATEGORY";

    private static final HashMap<String, BaseDao> mDelayMap = new HashMap<>();

    @NonNull
    public static synchronized <T extends BaseDao> T create(@Factory String type) {
        switch (type) {
            case DAO_USER:
                return generateClassT(type, UserDao.class);
            case DAO_CATEGORY:
                return generateClassT(type, GoodsCategoryDao.class);
            default:
                return (T) new DefaultDao();
        }
    }

    private static <T extends BaseDao> T generateClassT(@Factory String type, @NonNull Class<?> clazz) {
        if (mDelayMap.containsKey(type)) {
            return (T) mDelayMap.get(type);
        } else {
            T obj = null;
            try {
                obj = (T) clazz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mDelayMap.put(type, obj);
            return obj;
        }
    }

    private static class DefaultDao extends BaseDao {
        @Override public Class buildClass() {
            return DefaultDao.class;
        }
    }
}
