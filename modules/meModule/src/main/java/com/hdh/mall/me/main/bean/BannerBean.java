package com.hdh.mall.me.main.bean;

/**
 * Created by jsy on 17/3/12.
 */

public class BannerBean  {

    public static final int BANNER_TYPE_URL=1;
    public static final int BANNER_TYPE_GOOD=2;
    public static final int BANNER_TYPE_MERCHANT=3;
    public static final int BANNER_TYPE_NOMAL=4;

    /**
     *  "id":"1505304809226",
     "updatedAccount":"1705311116584345281",
     "bannerId":"1505304809226",
     "bannerType":1,
     "displayOrder":1,
     "image":"http://cdn.beta.mgcheng.com/goods/2017/10/10/528e1680701b4307be24618d6ce9762c.png",
     "createdAccount":"1705311103339963150",
     "startAt":1505318400000,
     "endAt":1509897600000,
     "type":4,
     "url":"",
     "isShow":1
     */
    public String createdAccount;
    public String image;
    public int readyCount;
    public long endAt;
    /**
     * 数据类型,1: 普通URL跳转, 2:跳转到商品详情, 3:跳转到商家, 4:无
     */
    public int type;
    public String url;
    public String id;
    public int isDisplay;
    public String infoId;
    public String updatedAccount;
    public String bannerId;
    public int bannerType;
    public int displayOrder;
    public long startAt;
    public int isShow;


}
