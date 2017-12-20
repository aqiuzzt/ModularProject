package com.hdh.android.mail.base.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/12/4 10:50
 */

public class GoodsImagesBean implements Parcelable {
    public String id;
    public String goodsId;
    public String imageUrl;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.goodsId);
        dest.writeString(this.imageUrl);
    }

    public GoodsImagesBean() {
    }

    protected GoodsImagesBean(Parcel in) {
        this.id = in.readString();
        this.goodsId = in.readString();
        this.imageUrl = in.readString();
    }

    public static final Creator<GoodsImagesBean> CREATOR = new Creator<GoodsImagesBean>() {
        @Override
        public GoodsImagesBean createFromParcel(Parcel source) {
            return new GoodsImagesBean(source);
        }

        @Override
        public GoodsImagesBean[] newArray(int size) {
            return new GoodsImagesBean[size];
        }
    };
}
