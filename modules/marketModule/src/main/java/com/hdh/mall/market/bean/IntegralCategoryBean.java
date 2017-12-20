package com.hdh.mall.market.bean;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gzhjhc.com">Contact me.</a>
 * @since 2017/12/4 15:16
 */

public class IntegralCategoryBean {

    /**
     * 1：支付宝
     * 2：微信
     * 3：银积分(原优待金)
     * 7：购物豆
     * 8：金积分(原红星)
     * 10: 红积分
     * 11: 黄积分
     * 12: 蓝积分
     * 13: 绿积分
     * 14: 橙积分
     * 15: 紫积分
     */
    public static final int TYPE_GOLD = 8;
    public static final int TYPE_SIVILER = 3;
    public static final int TYPE_PEA = 7;
    public static final int TYPE_RED = 10;
    public static final int TYPE_YELLOW = 11;
    public static final int TYPE_BLUE = 12;
    public static final int TYPE_GREEN = 13;
    public static final int TYPE_ORANGE = 14;
    public static final int TYPE_PURPLE = 15;

    public static final int TYPE_GOLD_DISC = 1500;
    /**
     * 消费积分
     */
    public static final int TYPE_CONSUMPTION_INTEGRAL = 100;

    public String name;
    /**
     * 积分类型
     */
    public int intergralType;

    public IntegralCategoryBean(String name, int type) {
        this.name = name;
        this.intergralType = type;
    }
}
