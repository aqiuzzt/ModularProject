package com.hdh.android.mail.base.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 属性库存信息
 * 
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/12/4 10:51
 */

public class SaleAttrInventoryBean implements Parcelable {
    /**
     * "id":"1",
     * "realSale":0,
     * "goodsId":"2180012006474201120",
     * "attributeNames":"白色|4G|5寸",
     * "inventory":100,
     * "cost":4999.0
     */

    public String id;
    public String realSale;
    public String goodsId;
    public String attributeNames;
    public long inventory;
    public long price;
    public long discountPrice;

    public SaleAttrInventoryBean() {
    }


    protected SaleAttrInventoryBean(Parcel in) {
        id = in.readString();
        realSale = in.readString();
        goodsId = in.readString();
        attributeNames = in.readString();
        inventory = in.readLong();
        price = in.readLong();
        discountPrice = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(realSale);
        dest.writeString(goodsId);
        dest.writeString(attributeNames);
        dest.writeLong(inventory);
        dest.writeLong(price);
        dest.writeLong(discountPrice);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SaleAttrInventoryBean> CREATOR = new Creator<SaleAttrInventoryBean>() {
        @Override
        public SaleAttrInventoryBean createFromParcel(Parcel in) {
            return new SaleAttrInventoryBean(in);
        }

        @Override
        public SaleAttrInventoryBean[] newArray(int size) {
            return new SaleAttrInventoryBean[size];
        }
    };
}
