package com.hdh.mall.market.bean;

import com.hdh.android.mail.base.bean.ShoppingCartBean;

import java.util.List;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gzhjhc.com">Contact me.</a>
 * @since 2017/12/4 11:12
 */

public class CreateOrderRequestBean {

    public String goodsId;
    public long amount;
    public List<ShoppingCartBean.CartItemAttrsBean> saleAttributes;
}
