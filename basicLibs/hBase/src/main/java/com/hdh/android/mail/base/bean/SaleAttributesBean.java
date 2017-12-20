package com.hdh.android.mail.base.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/12/4 10:51
 */

public class SaleAttributesBean implements Cloneable, Parcelable {

    /**
     * id : 74
     * attributeName : 重量
     * goodsId : 2180011412364522001
     * value : 1斤，2斤
     */

    public String id;
    public String attributeName;
    public String goodsId;
    public String value;
    public SaleAttributesBean() {}


    public SaleAttributesBean(Parcel in) {
        id = in.readString();
        attributeName = in.readString();
        goodsId = in.readString();
        value = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(attributeName);
        dest.writeString(goodsId);
        dest.writeString(value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SaleAttributesBean> CREATOR = new Creator<SaleAttributesBean>() {
        @Override
        public SaleAttributesBean createFromParcel(Parcel in) {
            return new SaleAttributesBean(in);
        }

        @Override
        public SaleAttributesBean[] newArray(int size) {
            return new SaleAttributesBean[size];
        }
    };
}
