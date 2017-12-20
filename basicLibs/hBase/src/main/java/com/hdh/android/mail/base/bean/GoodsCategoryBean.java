package com.hdh.android.mail.base.bean;

import com.google.gson.annotations.SerializedName;
import com.hdh.android.mail.base.db.LitePalSupport;

import org.litepal.annotation.Column;

import java.io.Serializable;
import java.util.List;

/**
 * 商品分类实体
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/30 10:04
 */

public class GoodsCategoryBean extends LitePalSupport implements Serializable {

    public String others;
    public int state;
    public int activeState;
    public String type;
    public int version;

    @SerializedName("id")
    public String categoryId;
    public int isOnline;
    public String parentId;
    public int level;
    public String description;
    public String name;
    public int displayOrder;
    @Column(ignore = true)
    public List<GoodsCategoryBean> children;
    public List<ImgUrlBean> imgUrl;

    @Override
    public String toString() {
        return "GoodsCategoryBean{" +
                "others='" + others + '\'' +
                ", state=" + state +
                ", activeState=" + activeState +
                ", type='" + type + '\'' +
                ", version=" + version +
                ", categoryId='" + categoryId + '\'' +
                ", isOnline=" + isOnline +
                ", parentId='" + parentId + '\'' +
                ", level=" + level +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", displayOrder=" + displayOrder +
                ", children=" + children +
                ", imgUrl=" + imgUrl +
                '}';
    }


}
