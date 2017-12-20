package com.hdh.android.mail.base.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gzhjhc.com">Contact me.</a>
 * @since 2017/11/30 16:23
 */

public class MerchantCategoryBean implements Parcelable {

    /**
     * {"others":"","state":1,"children":[],"activeState":1,"type":"merchant_type","id":"2140011621039979031",
     * "imgUrl":[{"name":"app_icon_Image","value":"http://beta.img.mypip.cn/images/2017/03/14/QQ_s%403x.png"
     */
    @SerializedName("others")
    public String others;
    @SerializedName("state")
    public int state;
    @SerializedName("activeState")
    public int activeState;
    @SerializedName("type")
    public String type;
    @SerializedName("version")
    public int version;
    @SerializedName("id")
    public String categoryId;
    @SerializedName("isOnline")
    public int isOnline;
    @SerializedName("parentId")
    public String parentId;
    @SerializedName("level")
    public int level;
    @SerializedName("description")
    public String description;
    @SerializedName("name")
    public String name;
    @SerializedName("displayOrder")
    public int displayOrder;
    @SerializedName("children")
    public List<GoodsCategoryBean> children;
    @SerializedName("imgUrl")
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
                ", goodsCategorychildrenBeanList=" + children +
                ", imgUrl=" + imgUrl +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.others);
        dest.writeInt(this.state);
        dest.writeInt(this.activeState);
        dest.writeString(this.type);
        dest.writeInt(this.version);
        dest.writeString(this.categoryId);
        dest.writeInt(this.isOnline);
        dest.writeString(this.parentId);
        dest.writeInt(this.level);
        dest.writeString(this.description);
        dest.writeString(this.name);
        dest.writeInt(this.displayOrder);
        dest.writeList(this.children);
        dest.writeList(this.imgUrl);
    }

    public MerchantCategoryBean() {
    }

    protected MerchantCategoryBean(Parcel in) {
        this.others = in.readString();
        this.state = in.readInt();
        this.activeState = in.readInt();
        this.type = in.readString();
        this.version = in.readInt();
        this.categoryId = in.readString();
        this.isOnline = in.readInt();
        this.parentId = in.readString();
        this.level = in.readInt();
        this.description = in.readString();
        this.name = in.readString();
        this.displayOrder = in.readInt();
        this.children = new ArrayList<GoodsCategoryBean>();
        in.readList(this.children, GoodsCategoryBean.class.getClassLoader());
        this.imgUrl = new ArrayList<ImgUrlBean>();
        in.readList(this.imgUrl, ImgUrlBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<MerchantCategoryBean> CREATOR = new Parcelable.Creator<MerchantCategoryBean>() {
        @Override
        public MerchantCategoryBean createFromParcel(Parcel source) {
            return new MerchantCategoryBean(source);
        }

        @Override
        public MerchantCategoryBean[] newArray(int size) {
            return new MerchantCategoryBean[size];
        }
    };
}
