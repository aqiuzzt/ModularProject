package com.hdh.mall.me.main.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.hdh.android.mail.base.bean.GoodsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jsy on 17/3/12.
 */

public class ProductResult implements Parcelable {

    public int pageCount;
    public int start;
    public int startOfPage;
    public int resultCount;
    public int pageSize;
    public int currentPage;
    public List<GoodsBean> data;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.pageCount);
        dest.writeInt(this.start);
        dest.writeInt(this.startOfPage);
        dest.writeInt(this.resultCount);
        dest.writeInt(this.pageSize);
        dest.writeInt(this.currentPage);
        dest.writeList(this.data);
    }

    public ProductResult() {
    }

    protected ProductResult(Parcel in) {
        this.pageCount = in.readInt();
        this.start = in.readInt();
        this.startOfPage = in.readInt();
        this.resultCount = in.readInt();
        this.pageSize = in.readInt();
        this.currentPage = in.readInt();
        this.data = new ArrayList<GoodsBean>();
        in.readList(this.data, GoodsBean.class.getClassLoader());
    }

    public static final Creator<ProductResult> CREATOR = new Creator<ProductResult>() {
        @Override
        public ProductResult createFromParcel(Parcel source) {
            return new ProductResult(source);
        }

        @Override
        public ProductResult[] newArray(int size) {
            return new ProductResult[size];
        }
    };
}
