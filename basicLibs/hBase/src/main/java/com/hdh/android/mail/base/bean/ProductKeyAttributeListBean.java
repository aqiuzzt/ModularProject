package com.hdh.android.mail.base.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 关键属性
 * 
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/12/4 10:50
 */

public class ProductKeyAttributeListBean implements Parcelable {


    /**
     * productKeyAttributeList	default value	N	关键属性
     └attributeName	default value	N	属性名
     └attrGroup	default value	N	属性组.比如,电脑的属性组有:主体,处理器,显示器,端口等属性组
     └productId	default value	N	产品id
     └value	default value	N	属性值
     */

    public String id;
    public String attributeName;
    public String value;
    public String attrGroup;
    public String productId;

    protected ProductKeyAttributeListBean(Parcel in) {
        id = in.readString();
        attributeName = in.readString();
        value = in.readString();
        attrGroup = in.readString();
        productId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(attributeName);
        dest.writeString(value);
        dest.writeString(attrGroup);
        dest.writeString(productId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProductKeyAttributeListBean> CREATOR = new Creator<ProductKeyAttributeListBean>() {
        @Override
        public ProductKeyAttributeListBean createFromParcel(Parcel in) {
            return new ProductKeyAttributeListBean(in);
        }

        @Override
        public ProductKeyAttributeListBean[] newArray(int size) {
            return new ProductKeyAttributeListBean[size];
        }
    };
}
