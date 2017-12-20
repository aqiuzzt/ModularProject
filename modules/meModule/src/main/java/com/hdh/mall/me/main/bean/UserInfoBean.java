package com.hdh.mall.me.main.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/3/29.
 */

public class UserInfoBean implements Parcelable {
    public String invitationFrom;
    public String nickname ="";
    public String origin;
    public String avatar ="";
    public String id;
    public long updatedAt;
    public String parentId;
    public String invitationCode;
    public String email;
    public long createdAt;
    public String userId;
    public int role;
    public String account;
    public String mobile;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.invitationFrom);
        dest.writeString(this.nickname);
        dest.writeString(this.origin);
        dest.writeString(this.avatar);
        dest.writeString(this.id);
        dest.writeLong(this.updatedAt);
        dest.writeString(this.parentId);
        dest.writeString(this.invitationCode);
        dest.writeString(this.email);
        dest.writeLong(this.createdAt);
        dest.writeString(this.userId);
        dest.writeInt(this.role);
        dest.writeString(this.account);
        dest.writeString(this.mobile);
    }

    public UserInfoBean() {
    }

    protected UserInfoBean(Parcel in) {
        this.invitationFrom = in.readString();
        this.nickname = in.readString();
        this.origin = in.readString();
        this.avatar = in.readString();
        this.id = in.readString();
        this.updatedAt = in.readLong();
        this.parentId = in.readString();
        this.invitationCode = in.readString();
        this.email = in.readString();
        this.createdAt = in.readLong();
        this.userId = in.readString();
        this.role = in.readInt();
        this.account = in.readString();
        this.mobile = in.readString();
    }

    public static final Creator<UserInfoBean> CREATOR = new Creator<UserInfoBean>() {
        @Override
        public UserInfoBean createFromParcel(Parcel source) {
            return new UserInfoBean(source);
        }

        @Override
        public UserInfoBean[] newArray(int size) {
            return new UserInfoBean[size];
        }
    };
}
