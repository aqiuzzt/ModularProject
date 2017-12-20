package com.hdh.mall.market.bean;

import com.hdh.android.mail.base.bean.SaleAttributesBean;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/12/4 10:51
 */

public class SkuSaleAttributes implements Serializable {

    private List<SaleAttributesBean> saleAttributesBeanList;
    private String skuName;

    public List<SaleAttributesBean> getSaleAttributesBeanList() {
        return saleAttributesBeanList;
    }

    public void setSaleAttributesBeanList(List<SaleAttributesBean> saleAttributesBeanList) {
        this.saleAttributesBeanList = saleAttributesBeanList;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }
}
