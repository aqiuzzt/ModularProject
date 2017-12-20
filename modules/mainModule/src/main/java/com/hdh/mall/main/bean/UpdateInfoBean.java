package com.hdh.mall.main.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by albert on 17/7/1.
 */

public class UpdateInfoBean implements Parcelable {

    public String url;//apk 下载链接
    public String versionName;//版本名称 ：2.2.1
    public String type;//更新类型：普通更新1，强制更新2
    public String title;//更新对话框 title，
    public String msg;//版本更新说明
    public String toggle; //0 关闭更新，1 可以更新


    public UpdateInfoBean() {
    }

    protected UpdateInfoBean(Parcel in) {
        url = in.readString();
        versionName = in.readString();
        type = in.readString();
        title = in.readString();
        msg = in.readString();
        toggle = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(versionName);
        dest.writeString(type);
        dest.writeString(title);
        dest.writeString(msg);
        dest.writeString(toggle);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UpdateInfoBean> CREATOR = new Creator<UpdateInfoBean>() {
        @Override
        public UpdateInfoBean createFromParcel(Parcel in) {
            return new UpdateInfoBean(in);
        }

        @Override
        public UpdateInfoBean[] newArray(int size) {
            return new UpdateInfoBean[size];
        }
    };
}
