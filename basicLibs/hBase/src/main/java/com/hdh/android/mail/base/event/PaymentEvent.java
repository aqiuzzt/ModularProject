package com.hdh.android.mail.base.event;

/**
 * 支付宝和微信 支付事件
 * Created by Administrator on 2016/11/18.
 */

public class PaymentEvent {
    public static final int PAY_WAY_ALIPAY = 1;
    public static final int PAY_WAY_WECHAT = 2;
    public static final int PAY_WAY_FUND = 0;
    public int payWay;
    public boolean isSuccess;

    public PaymentEvent(int payway, boolean isSuccess) {
        this.payWay = payway;
        this.isSuccess = isSuccess;
    }
}
