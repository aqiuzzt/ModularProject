package com.hdh.common.thread;

import android.support.annotation.CheckResult;
import android.support.annotation.UiThread;





/**
 * 结果返回线程
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/27 14:33
 * @param <T>
 * 
 */
public abstract class ReturnThread<T> implements ThreadDelegate {
    private final Runnable mThread;
    private long mKey;

    public ReturnThread() {
        mThread = new Runnable() {
            @Override
            public void run() {
                deliverEnd(doInBackground());
            }
        };
    }

    private void deliverEnd(final T result) {
        ThreadUtil.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                onPostExecute(result);
            }
        });
    }

    @Override
    public final long start() {
        mKey = ThreadUtil.execute(mThread);
        return mKey;
    }

    @Override
    public final boolean cancel(long key) {
        ThreadUtil.cancel(mKey);
        return false;
    }

    /**
     * 线程需要执行的内容
     */
    @CheckResult
    public abstract T doInBackground();

    /**
     * 这个方法执行在UI线程
     *
     * @param result
     */
    @UiThread
    public abstract void onPostExecute(T result);
}

/*
  new ReturnThread<String>() {
            @Override
            public String doInBackground() {
                PayTask alipay = new PayTask(OrderPaymentActivity.this);
                // 调用支付接口，获取支付结果
                return alipay.pay(result);
            }

            @Override
            public void onPostExecute(String result) {
                if (!TextUtils.isEmpty(result)) {
                    PayResult payResult = new PayResult(result);
                    if (payResult != null) {
                        if (TextUtils.equals(payResult.getResultStatus(), "9000")) {
                            showToast("支付成功");
                            EventBus.getDefault().post(new PaymentEvent(PAY_WAY_ALIPAY, true));
                            UIUtil.hideSoftInput(OrderPaymentActivity.this);
                        } else if (TextUtils.equals(payResult.getResultStatus(), "8000")) {
                            showToast("支付结果确认中");
                            EventBus.getDefault().post(new PaymentEvent(PAY_WAY_ALIPAY, false));
                        } else {
                            showToast("支付失败");
                            EventBus.getDefault().post(new PaymentEvent(PAY_WAY_ALIPAY, false));
                        }
                    } else {
                        showToast("支付失败");
                        EventBus.getDefault().post(new PaymentEvent(PAY_WAY_ALIPAY, false));
                    }
                } else {
                    showToast("支付失败");
                    EventBus.getDefault().post(new PaymentEvent(PAY_WAY_ALIPAY, false));
                }
            }
        }.start();
 */