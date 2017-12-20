package com.hdh.android.mail.base.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/12/4 10:50
 */

public class GoodsDetailBean implements Parcelable {
    /**
     * 1：支付宝
     * 2：微信
     * 3：银积分(原优待金)
     * 7：购物豆
     * 8：金积分(原红星,由score系统处理,与finance系统无关)
     * 10: 红积分
     * 11: 黄积分
     * 12: 蓝积分
     * 13: 绿积分
     * 14: 橙积分
     * 15: 紫积分
     */


    public static final int PAY_TYPE_ALIPAY = 1;
    public static final int PAY_TYPE_WECHAT = 2;
    public static final int PAY_TYPE_GOLD = 8;
    public static final int PAY_TYPE_SIVILER = 3;
    public static final int PAY_TYPE_PEA = 7;
    public static final int PAY_TYPE_RED = 10;
    public static final int PAY_TYPE_YELLOW = 11;
    public static final int PAY_TYPE_BLUE = 12;
    public static final int PAY_TYPE_GREEN = 13;
    public static final int PAY_TYPE_ORANGE = 14;
    public static final int PAY_TYPE_PURPLE = 15;
    public String id;
    public String summary;//图文详情 html
    /**
     * 支付属性
     */
    public int supportPayType;
    /**
     * 套餐详情
     */
    public String detail;
    public long price;
    public long discountPrice;
    public String commonQa;
    public String goodsCode;
    public int salesCount;
    public String goodsName;
    /**
     * 购物豆
     */
    public long secondPrice;
    /**
     * 库存
     */
    public long inventory;
    /**
     * 关键属性
     */
    public List<ProductKeyAttributeListBean> productKeyAttributeList;
    /**
     * 销售属性 购买的时候选择
     */
    public List<SaleAttributesBean> saleAttributes;
    /**
     * 对应属性规则的库存信息
     */
    public List<SaleAttrInventoryBean> saleAttributesInventories;
    public List<String> appDetailImages;
    public List<TranportInfoBean> transportList;
    /**
     * 详情中的商品参数
     */
    public List<KeyAttributeBean> keyAttributes;
    /**
     * 商品状态：1,.审核中，2.显示中，3.审核不通过，4.已停用
     */
    public String goodsState;

    public GoodsDetailBean() {
    }

    protected GoodsDetailBean(Parcel in) {
        id = in.readString();
        summary = in.readString();
        supportPayType = in.readInt();
        detail = in.readString();
        price = in.readLong();
        discountPrice = in.readLong();
        commonQa = in.readString();
        goodsCode = in.readString();
        salesCount = in.readInt();
        goodsName = in.readString();
        secondPrice = in.readLong();
        inventory = in.readLong();
        productKeyAttributeList = in.createTypedArrayList(ProductKeyAttributeListBean.CREATOR);
        saleAttributes = in.createTypedArrayList(SaleAttributesBean.CREATOR);
        saleAttributesInventories = in.createTypedArrayList(SaleAttrInventoryBean.CREATOR);
        appDetailImages = in.createStringArrayList();
        transportList = in.createTypedArrayList(TranportInfoBean.CREATOR);
        keyAttributes = in.createTypedArrayList(KeyAttributeBean.CREATOR);
        goodsState = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(summary);
        dest.writeInt(supportPayType);
        dest.writeString(detail);
        dest.writeLong(price);
        dest.writeLong(discountPrice);
        dest.writeString(commonQa);
        dest.writeString(goodsCode);
        dest.writeInt(salesCount);
        dest.writeString(goodsName);
        dest.writeLong(secondPrice);
        dest.writeLong(inventory);
        dest.writeTypedList(productKeyAttributeList);
        dest.writeTypedList(saleAttributes);
        dest.writeTypedList(saleAttributesInventories);
        dest.writeStringList(appDetailImages);
        dest.writeTypedList(transportList);
        dest.writeTypedList(keyAttributes);
        dest.writeString(goodsState);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GoodsDetailBean> CREATOR = new Creator<GoodsDetailBean>() {
        @Override
        public GoodsDetailBean createFromParcel(Parcel in) {
            return new GoodsDetailBean(in);
        }

        @Override
        public GoodsDetailBean[] newArray(int size) {
            return new GoodsDetailBean[size];
        }
    };
}
