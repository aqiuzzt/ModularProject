package com.hdh.android.mail.base.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.hdh.android.mail.base.Constant;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gzhjhc.com">Contact me.</a>
 * @since 2017/12/4 10:52
 */

public class AddressBean implements Parcelable {
    public String area;
    public String city;
    public long createdAt;
    public String detail;
    public String id;
    public String location;
    public String mobile;
    public String others;
    public String postCode = "888888";
    public String province;
    public String receiver;
    public int state;
    public String tel;
    public long updatedAt;
    public String userId;
    public String type = Constant.ADDRESS_TYPE;
    /**
     * 是否是默认地址。0：不是默认值；1：是默认值
     */
    public int isDefault;

    public String getAddress() {
        return new StringBuilder(province + "")
                .append(city)
                .append(area)
                .append(detail).toString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.area);
        dest.writeString(this.city);
        dest.writeLong(this.createdAt);
        dest.writeString(this.detail);
        dest.writeString(this.id);
        dest.writeString(this.location);
        dest.writeString(this.mobile);
        dest.writeString(this.others);
        dest.writeString(this.postCode);
        dest.writeString(this.province);
        dest.writeString(this.receiver);
        dest.writeInt(this.state);
        dest.writeString(this.tel);
        dest.writeLong(this.updatedAt);
        dest.writeString(this.userId);
        dest.writeString(this.type);
        dest.writeInt(this.isDefault);
    }

    public AddressBean() {
    }

    protected AddressBean(Parcel in) {
        this.area = in.readString();
        this.city = in.readString();
        this.createdAt = in.readLong();
        this.detail = in.readString();
        this.id = in.readString();
        this.location = in.readString();
        this.mobile = in.readString();
        this.others = in.readString();
        this.postCode = in.readString();
        this.province = in.readString();
        this.receiver = in.readString();
        this.state = in.readInt();
        this.tel = in.readString();
        this.updatedAt = in.readLong();
        this.userId = in.readString();
        this.type = in.readString();
        this.isDefault = in.readInt();
    }

    public static final Creator<AddressBean> CREATOR = new Creator<AddressBean>() {
        @Override
        public AddressBean createFromParcel(Parcel source) {
            return new AddressBean(source);
        }

        @Override
        public AddressBean[] newArray(int size) {
            return new AddressBean[size];
        }
    };
}
