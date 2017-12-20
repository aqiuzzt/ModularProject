package com.hdh.android.mail.base.bean;

import com.bigkoo.pickerview.model.IPickerViewData;



public class SimpleOption implements IPickerViewData {

    public String text;
    public String id;

    public SimpleOption(String text, String id) {
        this.text = text;
        this.id = id;
    }

    @Override public String getPickerViewText() {
        return text;
    }
}
