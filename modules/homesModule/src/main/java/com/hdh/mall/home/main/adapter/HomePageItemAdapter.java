package com.hdh.mall.home.main.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hdh.mall.home.R;
import com.hdh.mall.home.main.bean.HomeCategoryItem;

/**
 * 首页功能适配器
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/12/4 09:08
 */

public class HomePageItemAdapter extends BaseQuickAdapter<HomeCategoryItem> {

    public HomePageItemAdapter() {
        super(R.layout.item_home_category_type, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeCategoryItem item) {
        helper.setText(R.id.text, item.title);
        helper.setImageResource(R.id.merchant_iv, item.icon);
        helper.getConvertView().setBackgroundResource(item.background);
    }
}
