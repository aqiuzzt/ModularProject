package com.hdh.android.mail.base.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/3/27.
 */

public class BankCardBean implements Parcelable {

    public String id;
    public String accountName;
    public String accountId;
    public long createdAt;
    public int accountType;
    public String account;
    public String bankName;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.accountName);
        dest.writeString(this.accountId);
        dest.writeLong(this.createdAt);
        dest.writeInt(this.accountType);
        dest.writeString(this.account);
        dest.writeString(this.bankName);
    }

    public BankCardBean() {
    }

    protected BankCardBean(Parcel in) {
        this.id = in.readString();
        this.accountName = in.readString();
        this.accountId = in.readString();
        this.createdAt = in.readLong();
        this.accountType = in.readInt();
        this.account = in.readString();
        this.bankName = in.readString();
    }

    public static final Parcelable.Creator<BankCardBean> CREATOR = new Parcelable.Creator<BankCardBean>() {
        @Override
        public BankCardBean createFromParcel(Parcel source) {
            return new BankCardBean(source);
        }

        @Override
        public BankCardBean[] newArray(int size) {
            return new BankCardBean[size];
        }
    };
}
