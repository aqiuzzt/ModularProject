package com.hdh.mall.market.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.hdh.android.mail.base.bean.ShoppingCartBean;

import java.util.List;

/**
 * 
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/12/4 10:51
 */

public class ShoppingCartClassifyBean implements Parcelable {

    private int supportPayType;

    private List<ShoppingCartBean> items;

    /**
     * 是否处于编辑状态
     */
    private boolean isEditing;
    /**
     * 组是否被选中
     */
    private boolean isGroupSelected;

    /**
     * 是否失效列表
     */
    private boolean isInvalidList;

    private boolean isAllGoodsInvalid;

    public boolean isEditing() {
        return isEditing;
    }

    public void setEditing(boolean editing) {
        isEditing = editing;
    }

    public boolean isGroupSelected() {
        return isGroupSelected;
    }

    public void setGroupSelected(boolean groupSelected) {
        isGroupSelected = groupSelected;
    }

    public boolean isInvalidList() {
        return isInvalidList;
    }

    public void setInvalidList(boolean invalidList) {
        isInvalidList = invalidList;
    }

    public boolean isAllGoodsInvalid() {
        return isAllGoodsInvalid;
    }

    public void setAllGoodsInvalid(boolean allGoodsInvalid) {
        isAllGoodsInvalid = allGoodsInvalid;
    }

    protected ShoppingCartClassifyBean(Parcel in) {
        supportPayType = in.readInt();
        items = in.createTypedArrayList(ShoppingCartBean.CREATOR);
        isEditing = in.readByte() != 0;
        isGroupSelected = in.readByte() != 0;
        isInvalidList = in.readByte() != 0;
        isAllGoodsInvalid = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(supportPayType);
        dest.writeTypedList(items);
        dest.writeByte((byte) (isEditing ? 1 : 0));
        dest.writeByte((byte) (isGroupSelected ? 1 : 0));
        dest.writeByte((byte) (isInvalidList ? 1 : 0));
        dest.writeByte((byte) (isAllGoodsInvalid ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShoppingCartClassifyBean> CREATOR = new Creator<ShoppingCartClassifyBean>() {
        @Override
        public ShoppingCartClassifyBean createFromParcel(Parcel in) {
            return new ShoppingCartClassifyBean(in);
        }

        @Override
        public ShoppingCartClassifyBean[] newArray(int size) {
            return new ShoppingCartClassifyBean[size];
        }
    };

    public int getSupportPayType() {
        return supportPayType;
    }

    public void setSupportPayType(int supportPayType) {
        this.supportPayType = supportPayType;
    }

    public List<ShoppingCartBean> getItems() {
        return items;
    }

    public void setItems(List<ShoppingCartBean> items) {
        this.items = items;
    }

    public ShoppingCartClassifyBean() {

    }


}
