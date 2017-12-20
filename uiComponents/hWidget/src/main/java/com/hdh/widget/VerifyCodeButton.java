package com.hdh.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;


import java.lang.ref.WeakReference;

/**
 * 验证码按钮
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/24 14:27
 */

public class VerifyCodeButton extends AppCompatButton implements View.OnClickListener {
    private String normalText;
    private String sendingFormat;
    private String reSendText;
    private int countDownTime;
    private VerifyCodeCountDownTimer countDownTimer;

    public VerifyCodeButton(Context context) {
        this(context, null);
    }

    public VerifyCodeButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerifyCodeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = null;
        try {
            a = context.obtainStyledAttributes(attrs, R.styleable.VerifyCodeButton, defStyleAttr, 0);
            normalText = a.getString(R.styleable.VerifyCodeButton_normalText);
            sendingFormat = a.getString(R.styleable.VerifyCodeButton_sendingFormat);
            reSendText = a.getString(R.styleable.VerifyCodeButton_reSendText);
            countDownTime = a.getInt(R.styleable.VerifyCodeButton_countDownTime, 300);
        } finally {
            if (a != null) {
                a.recycle();
            }
        }
        if (normalText == null) normalText = "获取验证码";
        if (sendingFormat == null) sendingFormat = "%ds";
        if (reSendText == null) reSendText = "重新获取";
        setOnClickListener(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (countDownTimer != null) {
            countDownTimer.start();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (countDownTimer != null) countDownTimer.cancel();
    }

    @Override
    public void onClick(View v) {
        if (countDownTimer == null) {
            countDownTimer = new VerifyCodeCountDownTimer(countDownTime, this, sendingFormat, reSendText);
        }
        if (listener != null && listener.onClick(v)) {
            setEnabled(false);
            countDownTimer.start();
        }
    }

    public interface OnVerifyButtonClickListener {
        boolean onClick(View view);
    }

    private OnVerifyButtonClickListener listener;

    public void setOnVerifyButtonClickListener(OnVerifyButtonClickListener listener) {
        this.listener = listener;
    }

    private static class VerifyCodeCountDownTimer extends CountDownTimer {
        private WeakReference<Button> btn;
        private String format;
        private String resendText;

        public VerifyCodeCountDownTimer(long millisInFuture, Button button, String format, String resendText) {
            super(millisInFuture * 1000, 1000);
            btn = new WeakReference<>(button);
            this.format = format;
            this.resendText = resendText;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (btn.get() != null) {
                btn.get().setText(String.format(format, (int) (millisUntilFinished * 0.001f)));
            }
        }

        @Override
        public void onFinish() {
            if (btn.get() != null) {
                btn.get().setEnabled(true);
                btn.get().setText(resendText);
            }
        }
    }
}
