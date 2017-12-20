package com.hdh.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdh.widget.R;


/**
 * Created by albert on 2017/10/9.
 */

public class BecomeVipFailedDailog {

    private Context context;
    private Dialog dialog;
    private LinearLayout lLayout_bg;
    private TextView mBecomeVipTv;
    private Display display;
    private boolean showTitle = false;
    private boolean showMsg = false;
    private boolean showPosBtn = false;
    private boolean showNegBtn = false;

    public BecomeVipFailedDailog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context
                .WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public BecomeVipFailedDailog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(R.layout.become_vip_failed_dialog_layout, null);

        // 获取自定义Dialog布局中的控件
        lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
        mBecomeVipTv = (TextView) view.findViewById(R.id.become_vip_failed_tv);

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);


        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display.getWidth() * 0.85)
                , LinearLayout.LayoutParams.WRAP_CONTENT));

        return this;
    }


    public BecomeVipFailedDailog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }


    public BecomeVipFailedDailog setNegativeButton(final View.OnClickListener listener) {
        showNegBtn = true;

        mBecomeVipTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public BecomeVipFailedDailog setCanceledOnTouchOutside(boolean value) {
        dialog.setCanceledOnTouchOutside(value);
        return this;
    }


    public void show() {
        dialog.show();
    }
}
