package com.hdh.android.mail.base.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.ArrayList;
import java.util.List;


/**
 * 城市
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/30 10:12
 */
public class City implements Parcelable, IPickerViewData {
    public String id;
    public String parentId;
    public int level;
    public String name;
    public List<City> children;
    public String type;
    public String letter; //firstLetter
    public String firstLetters; //firstLetters
    public String pinyin; //pinyin

    public City(String name) {
        this.name = name;
    }

    public String getFirstLetters() {
        return firstLetters;
    }

    public void setFirstLetters(String firstLetters) {
        this.firstLetters = firstLetters;
    }

    public void setInitialLetter(String s) {
        this.letter = s;
    }

    public String getInitialLetter() {
        return this.letter;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getPinyin() {
        return pinyin;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.parentId);
        dest.writeInt(this.level);
        dest.writeString(this.name);
        dest.writeList(this.children);
        dest.writeString(this.type);
        dest.writeString(this.letter);
        dest.writeString(this.firstLetters);
        dest.writeString(this.pinyin);
    }

    public City() {
    }

    protected City(Parcel in) {
        this.id = in.readString();
        this.parentId = in.readString();
        this.level = in.readInt();
        this.name = in.readString();
        this.children = new ArrayList<City>();
        in.readList(this.children, City.class.getClassLoader());
        this.type = in.readString();
        this.letter = in.readString();
        this.firstLetters = in.readString();
        this.pinyin = in.readString();
    }

    public static final Parcelable.Creator<City> CREATOR = new Parcelable.Creator<City>() {
        @Override
        public City createFromParcel(Parcel source) {
            return new City(source);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

    @Override
    public String getPickerViewText() {
        return name;
    }
}
