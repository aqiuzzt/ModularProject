package com.hdh.android.mail.base.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/12/4 10:50
 */

public class KeyAttributeBean implements Parcelable {
    /**
     * "id":"1709282020141745501",
     * "modelId":"1709252006474341482",
     * "attributeName":"颜 色",
     * "value":"白 色"
     */

    public String id;
    public String modelId;
    public String attributeName;
    public String value;

    protected KeyAttributeBean() {
    }

    protected KeyAttributeBean(Parcel in) {
        id = in.readString();
        modelId = in.readString();
        attributeName = in.readString();
        value = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(modelId);
        dest.writeString(attributeName);
        dest.writeString(value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<KeyAttributeBean> CREATOR = new Creator<KeyAttributeBean>() {
        @Override
        public KeyAttributeBean createFromParcel(Parcel in) {
            return new KeyAttributeBean(in);
        }

        @Override
        public KeyAttributeBean[] newArray(int size) {
            return new KeyAttributeBean[size];
        }
    };
}
