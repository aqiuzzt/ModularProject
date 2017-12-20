package com.hdh.android.mail.base.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/12/4 10:51
 */

public class ShoppingCartBean implements Parcelable {

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


    public static final int PAY_TYPE_THIRD = 1;
    public static final int PAY_TYPE_COUPON = 2;
    public static final int PAY_TYPE_PEA = 3;
    public static final int PAY_TYPE_PEA_MONMEY = 4;
    public static final int PAY_TYPE_RED_STAR = 5;

    public String id;
    public String goodsId;
    public long price;
    public int supportPayType;
    public String accountId;
    public long count;
    public long discountPrice;
    public String goodsName;
    public String sku;
    public int chooseStatus;
    /**
     * 购物豆
     */
    public long secondPrice;
    public List<GoodsImagesBean> goodsImageDTOs;
    public List<CartItemAttrsBean> cartItemAttrs;
    public List<String> appCoverImages;
    public List<GoodsImagesBean> pcCoverImages;
    public List<GoodsImagesBean> mobileCoverImages;
    public List<TranportInfoBean> transportList;


    /**
     * 是否失效,0删除(失效),1正常
     */
    public String status;
    /**
     * 是否是编辑状态
     */
    public boolean isEditing;
    /**
     * 是否被选中
     */
    public boolean isChildSelected;

    /**
     * 是否是失效列表的子项
     */
    public boolean isInvalidItem;

    /**
     * 是否属于
     */
    public boolean isBelongInvalidList;

    /**
     * 临时解决方案，为了避免尾部重绘两次，增加一个虚ITEM，不显示，但是没有子项的组项，会有一条黑线，所以需要做特殊处理
     */
    public boolean isLastTempItem;


    protected ShoppingCartBean(Parcel in) {
        id = in.readString();
        goodsId = in.readString();
        price = in.readLong();
        supportPayType = in.readInt();
        accountId = in.readString();
        count = in.readLong();
        discountPrice = in.readLong();
        goodsName = in.readString();
        sku = in.readString();
        chooseStatus = in.readInt();
        secondPrice = in.readLong();
        goodsImageDTOs = in.createTypedArrayList(GoodsImagesBean.CREATOR);
        cartItemAttrs = in.createTypedArrayList(CartItemAttrsBean.CREATOR);
        appCoverImages = in.createStringArrayList();
        pcCoverImages = in.createTypedArrayList(GoodsImagesBean.CREATOR);
        mobileCoverImages = in.createTypedArrayList(GoodsImagesBean.CREATOR);
        transportList = in.createTypedArrayList(TranportInfoBean.CREATOR);
        status = in.readString();
        isEditing = in.readByte() != 0;
        isChildSelected = in.readByte() != 0;
        isInvalidItem = in.readByte() != 0;
        isBelongInvalidList = in.readByte() != 0;
        isLastTempItem = in.readByte() != 0;
    }

    public static final Creator<ShoppingCartBean> CREATOR = new Creator<ShoppingCartBean>() {
        @Override
        public ShoppingCartBean createFromParcel(Parcel in) {
            return new ShoppingCartBean(in);
        }

        @Override
        public ShoppingCartBean[] newArray(int size) {
            return new ShoppingCartBean[size];
        }
    };

    public String getSaleAttribute() {
        StringBuffer stringBuffer = new StringBuffer();
        if (cartItemAttrs != null) {
            for (ShoppingCartBean.CartItemAttrsBean bean : cartItemAttrs) {
                stringBuffer.append(" " + bean.value);
            }
        }
        return stringBuffer.toString();
    }

    public String getGoodsNameWithSaleAttribute() {
        StringBuffer stringBuffer = new StringBuffer("" + goodsName);
        if (cartItemAttrs != null) {
            for (ShoppingCartBean.CartItemAttrsBean bean : cartItemAttrs) {
                stringBuffer.append(" " + bean.value);
            }
        }
        return stringBuffer.toString();
    }

    public static class CartItemAttrsBean implements Parcelable {
        public String attributeName;
        public long cartItemId;
        public String value;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.attributeName);
            dest.writeLong(this.cartItemId);
            dest.writeString(this.value);
        }

        public CartItemAttrsBean() {
        }

        protected CartItemAttrsBean(Parcel in) {
            this.attributeName = in.readString();
            this.cartItemId = in.readLong();
            this.value = in.readString();
        }

        public static final Creator<CartItemAttrsBean> CREATOR = new Creator<CartItemAttrsBean>() {
            @Override
            public CartItemAttrsBean createFromParcel(Parcel source) {
                return new CartItemAttrsBean(source);
            }

            @Override
            public CartItemAttrsBean[] newArray(int size) {
                return new CartItemAttrsBean[size];
            }
        };
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShoppingCartBean that = (ShoppingCartBean) o;

        return id.equals(that.id);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(id);
        parcel.writeString(goodsId);
        parcel.writeLong(price);
        parcel.writeInt(supportPayType);
        parcel.writeString(accountId);
        parcel.writeLong(count);
        parcel.writeLong(discountPrice);
        parcel.writeString(goodsName);
        parcel.writeString(sku);
        parcel.writeInt(chooseStatus);
        parcel.writeLong(secondPrice);
        parcel.writeTypedList(goodsImageDTOs);
        parcel.writeTypedList(cartItemAttrs);
        parcel.writeStringList(appCoverImages);
        parcel.writeTypedList(pcCoverImages);
        parcel.writeTypedList(mobileCoverImages);
        parcel.writeTypedList(transportList);
        parcel.writeString(status);
        parcel.writeByte((byte) (isEditing ? 1 : 0));
        parcel.writeByte((byte) (isChildSelected ? 1 : 0));
        parcel.writeByte((byte) (isInvalidItem ? 1 : 0));
        parcel.writeByte((byte) (isBelongInvalidList ? 1 : 0));
        parcel.writeByte((byte) (isLastTempItem ? 1 : 0));
    }


    public ShoppingCartBean() {
    }



}