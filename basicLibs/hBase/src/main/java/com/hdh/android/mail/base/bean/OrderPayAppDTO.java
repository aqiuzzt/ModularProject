package com.hdh.android.mail.base.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 订单支付bean
 * 
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/12/4 10:50
 */

public class OrderPayAppDTO implements Parcelable {
    public String payNo;
    public String totalCost;
    public String orderId;
    public String orderState;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.payNo);
        dest.writeString(this.totalCost);
        dest.writeString(this.orderId);
        dest.writeString(this.orderState);
    }

    public OrderPayAppDTO() {
    }

    protected OrderPayAppDTO(Parcel in) {
        this.payNo = in.readString();
        this.totalCost = in.readString();
        this.orderId = in.readString();
        this.orderState = in.readString();
    }

    public static final Creator<OrderPayAppDTO> CREATOR = new Creator<OrderPayAppDTO>() {
        @Override
        public OrderPayAppDTO createFromParcel(Parcel source) {
            return new OrderPayAppDTO(source);
        }

        @Override
        public OrderPayAppDTO[] newArray(int size) {
            return new OrderPayAppDTO[size];
        }
    };
}
