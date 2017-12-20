package com.hdh.android.mail.base.db;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * 数据库操作类
 */

public class DBHelper {

    public static UserDao getUserDao() {
        return DatabaseFactory.create(DatabaseFactory.DAO_USER);
    }
    public static GoodsCategoryDao getCategoryDao() {
        return DatabaseFactory.create(DatabaseFactory.DAO_CATEGORY);
    }
    public static <T extends DataSupport> List<T> queryAll(Class<T> clazz) {
        return DataSupport.findAll(clazz);
    }

    public static <T extends DataSupport> void save(T bean) {
        bean.save();
    }
}
