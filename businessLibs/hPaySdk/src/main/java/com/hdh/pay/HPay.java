package com.hdh.pay;

import android.app.Activity;
import android.text.TextUtils;


import com.hdh.pay.alipay.Alipay;
import com.hdh.pay.unionpay.UPPay;
import com.hdh.pay.weixin.WeiXinPay;
import com.tencent.mm.opensdk.modelpay.PayReq;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 支付模块入口，支持微信、银联、支付宝三种支付方式
 */
public class HPay {
    private static HPay mHPay;
    private Activity mContext;

    private HPay(Activity context) {
        mContext = context;
    }

    public static HPay getIntance(Activity context) {
        if (mHPay == null) {
            synchronized (HPay.class) {
                if (mHPay == null) {
                    mHPay = new HPay(context);
                }
            }
        }
        return mHPay;
    }

    /**
     * 支付模块回调监听
     */
    public interface HPayListener {
        /**
         * 支付成功
         */
        void onPaySuccess();

        /**
         * 支付失败
         *
         * @param error_code
         * @param message
         */
        void onPayError(int error_code, String message);


        /**
         * 支付取消
         */
        void onPayCancel();

        /**
         * 银联支付结果回调
         */
        void onUUPay(String dataOrg, String sign, String mode);
    }

    /**
     * 支付模式：支付宝、微信、银联
     */
    public enum PayMode {
        WXPAY, ALIPAY, UUPAY
    }

    /**
     * 根据支付模式调用支付接口
     *
     * @param payMode       支付模式
     * @param payParameters 支付业务参数
     * @param listener      结果回调
     */
    public void toPay(PayMode payMode, String payParameters, HPayListener listener) {
        if (payMode.name().equalsIgnoreCase(PayMode.WXPAY.name())) {
            toWxPay(payParameters, listener);
        } else if (payMode.name().equalsIgnoreCase(PayMode.ALIPAY.name())) {
            toAliPay(payParameters, listener);
        } else if (payMode.name().equalsIgnoreCase(PayMode.UUPAY.name())) {
            toUUPay(payParameters, listener);
        }
    }


    /**
     * 微信支付
     *
     * @param payParameters 支付业务参数 json形式
     * @param listener      结果回调
     */
    public void toWxPay(String payParameters, HPayListener listener) {
        if (payParameters != null) {
            JSONObject param = null;
            try {
                param = new JSONObject(payParameters);
            } catch (JSONException e) {
                e.printStackTrace();
                if (listener != null) {
                    listener.onPayError(WeiXinPay.PAY_PARAMETERS_ERROE, "参数异常");
                }
                return;
            }
            if (TextUtils.isEmpty(param.optString("appId")) || TextUtils.isEmpty(param.optString("partnerId"))
                    || TextUtils.isEmpty(param.optString("prepayId")) || TextUtils.isEmpty(param.optString("nonceStr"))
                    || TextUtils.isEmpty(param.optString("timeStamp")) || TextUtils.isEmpty(param.optString("sign"))) {
                if (listener != null) {
                    listener.onPayError(WeiXinPay.PAY_PARAMETERS_ERROE, "参数异常");
                }
                return;
            }
            toWxPay(param.optString("appId"),
                    param.optString("partnerId"), param.optString("prepayId"),
                    param.optString("nonceStr"), param.optString("timeStamp"),
                    param.optString("sign"), listener);

        } else {
            if (listener != null) {
                listener.onPayError(WeiXinPay.PAY_PARAMETERS_ERROE, "参数异常");
            }
        }
    }

    /**
     * 微信支付
     * @param payReq  微信支付最终参数
     * @param listener 监听回调
     */
    public void toWxPay(PayReq payReq, HPayListener listener) {
        if (payReq != null) {
            if (TextUtils.isEmpty(payReq.appId)
                    || TextUtils.isEmpty(payReq.partnerId)
                    || TextUtils.isEmpty(payReq.prepayId)
                    || TextUtils.isEmpty(payReq.nonceStr)
                    || TextUtils.isEmpty(payReq.timeStamp)
                    || TextUtils.isEmpty(payReq.sign)) {
                if (listener != null) {
                    listener.onPayError(WeiXinPay.PAY_PARAMETERS_ERROE, "参数异常");
                }
                return;
            }
            WeiXinPay.getInstance(mContext).startWXPay(payReq, listener);

        } else {
            if (listener != null) {
                listener.onPayError(WeiXinPay.PAY_PARAMETERS_ERROE, "参数异常");
            }
        }
    }


    /**
     * 调用sdk 微信支付
     *
     * @param appId     微信支付appid
     * @param partnerId 商户号
     * @param prepayId  预支付交易会话ID
     * @param nonceStr  随机字符串
     * @param timeStamp 时间戳
     * @param sign      签名
     * @param listener  结果回调
     */
    public void toWxPay(String appId, String partnerId, String prepayId,
                        String nonceStr, String timeStamp, String sign, HPayListener listener) {
        if (TextUtils.isEmpty(appId) || TextUtils.isEmpty(partnerId)
                || TextUtils.isEmpty(prepayId) || TextUtils.isEmpty(nonceStr)
                || TextUtils.isEmpty(timeStamp) || TextUtils.isEmpty(sign)) {
            if (listener != null) {
                listener.onPayError(WeiXinPay.PAY_PARAMETERS_ERROE, "参数异常");
            }
            return;
        }
        WeiXinPay.getInstance(mContext).startWXPay(appId, partnerId, prepayId, nonceStr, timeStamp, sign, listener);
    }

    /**
     * 支付宝支付
     *
     * @param payParameters 支付业务蚕食
     * @param listener      结果回调
     */
    public void toAliPay(String payParameters, HPayListener listener) {
        if (payParameters != null) {
            if (listener != null) {
                Alipay.getInstance(mContext).startAliPay(payParameters, listener);
            }
        } else {
            if (listener != null) {
                listener.onPayError(Alipay.PAY_PARAMETERS_ERROE, "参数异常");
            }
        }
    }

    /**
     * 银联支付
     *
     * @param payParameters 支付业务蚕食
     * @param listener      结果回调
     */
    public void toUUPay(String payParameters, HPayListener listener) {
        if (payParameters != null) {
            JSONObject param = null;
            try {
                param = new JSONObject(payParameters);
            } catch (JSONException e) {
                e.printStackTrace();
                if (listener != null) {
                    listener.onPayError(UPPay.PAY_PARAMETERS_ERROE, "参数异常");
                }
                return;
            }
            if (TextUtils.isEmpty(param.optString("mode")) || TextUtils.isEmpty(param.optString("tn"))) {
                if (listener != null) {
                    listener.onPayError(UPPay.PAY_PARAMETERS_ERROE, "参数异常");
                }
                return;
            }
            toUUPay(param.optString("mode"),
                    param.optString("tn"), listener);
        } else {
            if (listener != null) {
                listener.onPayError(WeiXinPay.PAY_PARAMETERS_ERROE, "参数异常");
            }
        }
    }

    /**
     * 调用银联sdk支付
     *
     * @param mode
     * @param tn
     * @param listener
     */
    public void toUUPay(String mode, String tn, HPayListener listener) {
        if (listener == null) {
            listener.onPayError(UPPay.PAY_PARAMETERS_ERROE, "参数异常");
            return;
        }
        if (TextUtils.isEmpty(mode)) {
            mode = "00";
        }
        if (TextUtils.isEmpty(tn)) {
            listener.onPayError(UPPay.PAY_PARAMETERS_ERROE, "参数异常");
            return;
        }
        UPPay.getInstance(mContext).startUPPay(mode, tn, listener);
    }
}
