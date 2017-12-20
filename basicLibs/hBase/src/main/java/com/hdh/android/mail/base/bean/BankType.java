package com.hdh.android.mail.base.bean;


import com.hdh.android.mail.base.R;

public enum BankType {
    ICBC(R.drawable.icbc, "工商银行", 1),
    CBC(R.drawable.cbc, "建设银行", 2),
    ABC(R.drawable.abc, "农业银行", 3),
    BOC(R.drawable.boc, "中国银行", 4);

    public int icon;
    public int type;
    public String name;

    BankType(int icon, String name, int type) {
        this.icon = icon;
        this.name = name;
        this.type = type;
    }
}
