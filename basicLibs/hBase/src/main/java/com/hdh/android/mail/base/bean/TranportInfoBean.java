package com.hdh.android.mail.base.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gzhjhc.com">Contact me.</a>
 * @since 2017/12/4 17:55
 */

public class TranportInfoBean implements Parcelable {


    public String id;
    public int fullFree;
    public int price;
    public String name;
    public int increase;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeInt(this.fullFree);
        dest.writeInt(this.price);
        dest.writeString(this.name);
        dest.writeInt(this.increase);
    }

    public TranportInfoBean() {
    }

    protected TranportInfoBean(Parcel in) {
        this.id = in.readString();
        this.fullFree = in.readInt();
        this.price = in.readInt();
        this.name = in.readString();
        this.increase = in.readInt();
    }

    public static final Creator<TranportInfoBean> CREATOR = new Creator<TranportInfoBean>() {
        @Override
        public TranportInfoBean createFromParcel(Parcel source) {
            return new TranportInfoBean(source);
        }

        @Override
        public TranportInfoBean[] newArray(int size) {
            return new TranportInfoBean[size];
        }
    };
}
