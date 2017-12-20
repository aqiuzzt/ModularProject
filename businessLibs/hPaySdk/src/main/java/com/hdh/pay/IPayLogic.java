package com.hdh.pay;

import android.app.Activity;
import android.widget.Toast;

/**
 *
 * 使用demo类
 * @author albert  <a href="mailto:zhengting.zhou@gzhjhc.com">Contact me.</a>
 * @since 2017/11/28 10:56
 */

public class IPayLogic {

    private static IPayLogic mIPayLogic;
    private Activity mContext;

    private IPayLogic(Activity context) {
        mContext = context;
    }

    public static IPayLogic getIntance(Activity context) {
        if (mIPayLogic == null) {
            synchronized (IPayLogic.class) {
                if (mIPayLogic == null) {
                    mIPayLogic = new IPayLogic(context);
                }
            }
        }
        return mIPayLogic;
    }


    /**
     * 支付宝支付
     * @param orderInfo
     */
    public void startAliPay(final String orderInfo) {
        HPay.getIntance(mContext).toPay(HPay.PayMode.ALIPAY, orderInfo, new HPay.HPayListener() {
            @Override
            public void onPaySuccess() {
                Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPayError(int error_code, String message) {
                Toast.makeText(mContext, "支付失败>" + error_code + " " + message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPayCancel() {
                Toast.makeText(mContext, "取消了支付", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUUPay(String dataOrg, String sign, String mode) {

            }
        });
    }


    /**
     * 微信支付
     *
     * @param appId
     * @param partnerId
     * @param prepayId
     * @param nonceStr
     * @param timeStamp
     * @param sign
     */
    public void startWXPay(String appId, String partnerId, String prepayId,
                           String nonceStr, String timeStamp, String sign) {

        HPay.getIntance(mContext).toWxPay(appId, partnerId, prepayId, nonceStr, timeStamp, sign, new HPay.HPayListener() {
            @Override
            public void onPaySuccess() {
                Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPayError(int error_code, String message) {
                Toast.makeText(mContext, "支付失败>" + error_code + " " + message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPayCancel() {
                Toast.makeText(mContext, "取消了支付", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUUPay(String dataOrg, String sign, String mode) {

            }
        });
    }

    /**
     * 银联支付
     * @param tn
     */
    public void startUPPay(String tn) {

        HPay.getIntance(mContext).toUUPay("01", tn, new HPay.HPayListener() {
            @Override
            public void onPaySuccess() {
                Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPayError(int error_code, String message) {
                Toast.makeText(mContext, "支付失败>" + error_code + " " + message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPayCancel() {
                Toast.makeText(mContext, "取消了支付", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUUPay(String dataOrg, String sign, String mode) {
                Toast.makeText(mContext, "支付成功>需要后台查询订单确认>" + dataOrg + " " + mode, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
