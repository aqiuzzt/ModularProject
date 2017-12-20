package com.hdh.mall.market.listener;

public interface OnShoppingCartChangeListener {

    void onDataChange(String selectCount, String selectMoney, String goodType);

    void onSelectItem(boolean isSelectedAll);
}
