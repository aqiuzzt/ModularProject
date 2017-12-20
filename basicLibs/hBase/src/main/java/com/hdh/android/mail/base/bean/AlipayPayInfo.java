package com.hdh.android.mail.base.bean;

/**
 * 支付宝后台订单返回信息
 * 
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/12/4 10:50
 */

public class AlipayPayInfo {


    public TradeDTOBean tradeDTO;

    public CredentiaBean credentia;

    public static class TradeDTOBean {
        public String tradeType;
        public String body;
        public int tradeStatus;
        public String accountId;
        public String payWayDesc;
        public long payTime;
        public boolean inorout;
        public int amount;
        public String id;
        public String title;
        public int refundAmount;
        public String payNo;
        public String orderNo;
        public long createdAt;
        public int payWay;
        public String tradeStatusDesc;
    }

    public static class CredentiaBean {
        public String order_info;
    }
}
