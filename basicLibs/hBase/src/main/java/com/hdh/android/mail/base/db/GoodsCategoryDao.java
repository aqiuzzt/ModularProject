package com.hdh.android.mail.base.db;

import android.support.annotation.NonNull;

import com.hdh.common.util.LogUtil;
import com.hdh.android.mail.base.bean.GoodsCategoryBean;
import com.hdh.android.mail.base.bean.ImgUrlBean;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * 商品类别数据库存储
 * Created by Administrator on 2017/3/14.
 */

public class GoodsCategoryDao extends BaseDao<GoodsCategoryBean> {

    @Override
    public Class<GoodsCategoryBean> buildClass() {
        return GoodsCategoryBean.class;
    }

    @Override
    public boolean save(@NonNull GoodsCategoryBean bean) {
        LogUtil.d("jsy save id " + bean.categoryId);
        if (bean.imgUrl != null && bean.imgUrl.size() > 0) {
            for (ImgUrlBean imgBean :bean.imgUrl) {
                imgBean.bean = bean;
            }
            DataSupport.saveAll(bean.imgUrl);
        }

        if (bean.children == null) return true;
        for (GoodsCategoryBean childBean : bean.children) {
            if (bean.imgUrl != null && bean.imgUrl.size() > 0) {
                for (ImgUrlBean imgBean :bean.imgUrl) {
                    imgBean.bean = bean;
                }
                DataSupport.saveAll(bean.imgUrl);
            }
            childBean.save();
        }
        bean.save();
        return true;
    }


    public List<GoodsCategoryBean> queryAllCategory() {
        List<GoodsCategoryBean> firstClassCategoryList = queryFirstCategory();
        for (GoodsCategoryBean bean : firstClassCategoryList) {
            bean.children = queryCategoryByParentId(bean.categoryId);
        }
        return firstClassCategoryList;
    }

    public List<GoodsCategoryBean> queryFirstCategory() {
        return queryCategoryByParentId("0");
    }

    public List<GoodsCategoryBean> queryCategoryByParentId(String parentId) {


        return DataSupport.where("parentId = ?", parentId).find(GoodsCategoryBean.class, true);
    }
}
