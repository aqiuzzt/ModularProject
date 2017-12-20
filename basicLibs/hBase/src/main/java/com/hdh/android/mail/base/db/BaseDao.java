package com.hdh.android.mail.base.db;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import org.litepal.crud.DataSupport;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Desc:实体类必须集成自{@link LitePalSupport} 其中包含一个LitePal需要保持的id(自增长),不要与服务器的id混淆
 * 如果服务器中返回了id那么应该用{@link com.google.gson.annotations.SerializedName}进行转换,否则会覆盖服务器中的id,导致数据错误
 *
 * @see DataSupport
 */

public abstract class BaseDao<T extends LitePalSupport> {

    private Class<T> clazz;

    public BaseDao() {
        clazz = buildClass();
    }

    /**
     * 普通的保存數據,不需要设置{@link T} 的 id
     *
     * @param bean
     * @return
     */
    public boolean save(@NonNull T bean) {
        return bean.save();
    }

    /**
     * 保存所有的对象,不能保证不会重复数据
     *
     * @param list
     */
    public void saveAll(Collection<T> list) {
        DataSupport.saveAll(list);
    }

    /**
     * 保存或更新实体(如果是更新需要设置实体的id)
     *
     * @param bean
     * @return
     */
    public boolean saveOrUpdate(T bean) {
        return bean.saveOrUpdate();
    }

    /**
     * 通过id删除实体
     *
     * @param id
     * @return
     */
    public boolean delete(@NonNull String id) {
        if (!TextUtils.isDigitsOnly(id)) {
            throw new IllegalArgumentException("the id only must be a number");
        }
        int result = DataSupport.delete(clazz, Long.parseLong(id));
        return result > 0;
    }

    /**
     * 删除表中的所有数据
     *
     * @return
     */
    public int deleteAll() {
        return DataSupport.deleteAll(clazz);
    }

    /**
     * @param id      表中的id
     * @param newBean 新的实体对象
     * @return
     */
    public boolean update(long id, @NonNull T newBean) {
        int result = newBean.update(id);
        return result > 0;
    }

    /**
     * 通过id查询实体
     *
     * @param id
     * @return
     */
    @CheckResult @Nullable
    public T query(@NonNull String id) {
        if (!TextUtils.isDigitsOnly(id)) {
            throw new IllegalArgumentException("the id only must be a number");
        }
        return DataSupport.find(clazz, Long.parseLong(id));
    }

    @CheckResult @NonNull
    public List<T> queryAll() {
        List<T> list = DataSupport.findAll(clazz);
        if (list == null) return Collections.EMPTY_LIST;
        return list;
    }


    public abstract Class<T> buildClass();
}
