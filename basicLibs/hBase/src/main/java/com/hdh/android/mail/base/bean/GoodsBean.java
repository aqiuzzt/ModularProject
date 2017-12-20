package com.hdh.android.mail.base.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gzhjhc.com">Contact me.</a>
 * @since 2017/12/4 17:54
 */

public class GoodsBean implements Parcelable {


    public static final int GOOD_TYPE_ALIPAY = 1;
    public static final int GOOD_TYPE_WECHAT = 2;
    public static final int GOOD_TYPE_GLOD=8;
    public static final int GOOD_TYPE_SLIVER=3;
    public static final int GOOD_TYPE_PEA=7;
    public static final int GOOD_TYPE_RED=10;
    public static final int GOOD_TYPE_YELLOW=11;
    public static final int GOOD_TYPE_BLUE=12;
    public static final int GOOD_TYPE_GREEN=13;
    public static final int GOOD_TYPE_ORANGE=14;
    public static final int GOOD_TYPE_PURPLE=15;



    public String id;
    public long price;
    public int goodsState;
    public long secondPrice;
    public long discountPrice;
    public String goodsCode;
    public String goodsName;
    /**
     * 支付属性
     */
    public int supportPayType;

    public List<String> appCoverImages;
    public List<String> appDetailImages;
    /**
     * 运费选择信息
     */
    public List<TranportInfoBean> transportList;


    public GoodsBean() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeLong(this.price);
        dest.writeLong(this.discountPrice);
        dest.writeString(this.goodsCode);
        dest.writeString(this.goodsName);
        dest.writeInt(this.supportPayType);
        dest.writeStringList(this.appCoverImages);
        dest.writeStringList(this.appDetailImages);
        dest.writeTypedList(this.transportList);
        dest.writeInt(this.goodsState);
        dest.writeLong(this.secondPrice);
    }

    protected GoodsBean(Parcel in) {
        this.id = in.readString();
        this.price = in.readLong();
        this.discountPrice = in.readLong();
        this.goodsState = in.readInt();
        this.secondPrice = in.readLong();
        this.goodsCode = in.readString();
        this.goodsName = in.readString();
        this.supportPayType = in.readInt();
        this.appCoverImages = in.createStringArrayList();
        this.appDetailImages = in.createStringArrayList();
        this.transportList = in.createTypedArrayList(TranportInfoBean.CREATOR);
    }

    public static final Parcelable.Creator<GoodsBean> CREATOR = new Parcelable.Creator<GoodsBean>() {
        @Override
        public GoodsBean createFromParcel(Parcel source) {
            return new GoodsBean(source);
        }

        @Override
        public GoodsBean[] newArray(int size) {
            return new GoodsBean[size];
        }
    };
}
