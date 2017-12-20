package com.hdh.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hdh.common.util.view.UIUtil;
import com.hdh.widget.PasswordInputView;
import com.hdh.widget.R;

/**
 * 对话框 工厂类
 * Created by jsy on 2016/10/26.
 */

public class DialogFactory {

    /**
     * 显示在屏幕下方的列表对话框
     */
    public static AlertDialog getBottomListDialog(Context context, String[] data, final AdapterView.OnItemClickListener listener, boolean isCancel) {

        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(R.layout.dialog_bottom_list_layout);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.windowAnimations = R.style.BottomSheetAnimation;
        window.setAttributes(lp);
        window.setGravity(Gravity.BOTTOM);
        setupAlertDialog(context, dialog, isCancel);

        TextView cancelBtn = (TextView) window.findViewById(R.id.cancel);
        ListView listview = (ListView) window.findViewById(R.id.listview);
        listview.setAdapter(new ArrayAdapter<String>(context, R.layout.dialog_list_item_layout, data));
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
                if (listener != null) {
                    listener.onItemClick(parent, view, position, id);
                }
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        return dialog;
    }


    public static AlertDialog ShowVertifyPayPasswordDialog(final Activity context,
                                                           final PasswordInputView.InputCompleteListener listener,
                                                           boolean isCancel) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(R.layout.dialog_pay_password_layout);
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.BottomDialogAnim);
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        setupFullAlertDialog(context, dialog, isCancel);

        View dismissBtn = window.findViewById(R.id.btn_dismiss);
        dismissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        final PasswordInputView passwordInputView = (PasswordInputView) window.findViewById(R.id.password_view);
        passwordInputView.post(new Runnable() {
            @Override
            public void run() {
                //设置可获得焦点
                passwordInputView.setFocusable(true);
                passwordInputView.setFocusableInTouchMode(true);
                //请求获得焦点
                passwordInputView.requestFocus();
                UIUtil.showSoftInput(context, passwordInputView);
            }
        });
        passwordInputView.setCompleteListener(new PasswordInputView.InputCompleteListener() {
            @Override
            public void onComplete(String str) {
                passwordInputView.clear();
                dialog.dismiss();
                if (listener != null) {
                    listener.onComplete(str);
                }
            }
        });
        return dialog;
    }

    public static AlertDialog ShowVertifyPayPasswordDialog(final Context context,
                                                           final PasswordInputView.InputCompleteListener listener,
                                                           boolean isCancel) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(R.layout.dialog_pay_password_layout);
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.BottomDialogAnim);
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        setupFullAlertDialog(context, dialog, isCancel);

        View dismissBtn = window.findViewById(R.id.btn_dismiss);
        dismissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        final PasswordInputView passwordInputView = (PasswordInputView) window.findViewById(R.id.password_view);
        passwordInputView.post(new Runnable() {
            @Override
            public void run() {
                //设置可获得焦点
                passwordInputView.setFocusable(true);
                passwordInputView.setFocusableInTouchMode(true);
                //请求获得焦点
                passwordInputView.requestFocus();
                UIUtil.showSoftInput(context, passwordInputView);
            }
        });
        passwordInputView.setCompleteListener(new PasswordInputView.InputCompleteListener() {
            @Override
            public void onComplete(String str) {
                passwordInputView.clear();
                dialog.dismiss();
                if (listener != null) {
                    listener.onComplete(str);
                }
            }
        });
        return dialog;
    }


    /**
     * 有确定、取消按钮的对话框
     */
    public static AlertDialog getOkCancelDialog(Context context, String info, final View.OnClickListener listener, boolean isCancel) {
        return getOkCancelDialog(context, info, "确定", R.color.colorTextYellow, listener, isCancel);
    }

    /**
     * 有确定、取消按钮的对话框监听回调
     */
    public static AlertDialog getOkCancelListenerDialog(Context context, String info,
                                                        final View.OnClickListener okListener,
                                                        final View.OnClickListener canelListener,
                                                        boolean isCancel) {
        return getOkCancelDialog(context, info, "确定", R.color.colorTextYellow, okListener, canelListener, isCancel);
    }


    /**
     * 有确定、取消按钮的对话框
     */
    public static AlertDialog getOkCancelDialog(Context context, String info,
                                                String okBtnText,
                                                @ColorRes int okBtnColor,
                                                final View.OnClickListener listener,
                                                boolean isCancel) {
        final AlertDialog dialog = new AlertDialog.Builder(context).setCancelable(isCancel).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(R.layout.dialog_ok_cancel_layout);
        window.setGravity(Gravity.CENTER);
        setupAlertDialog(context, dialog, isCancel);

        TextView textView = (TextView) window.findViewById(R.id.text);
        TextView okBtn = (TextView) window.findViewById(R.id.ok);
        TextView cancelBtn = (TextView) window.findViewById(R.id.cancel);

        okBtn.setTextColor(ContextCompat.getColor(context, okBtnColor));
        okBtn.setText(okBtnText);

        textView.setText(info);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(view);
                }
                dialog.dismiss();
            }
        });
        return dialog;
    }

    /**
     * 有确定、取消按钮的对话框
     */
    public static AlertDialog getOkCancelDialog(Context context, String info, String okBtnText,
                                                @ColorRes int okBtnColor, final View.OnClickListener okListener,
                                                final View.OnClickListener canelListener, boolean isCancael) {
        final AlertDialog dialog = new AlertDialog.Builder(context).setCancelable(isCancael).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(R.layout.dialog_ok_cancel_layout);
        window.setGravity(Gravity.CENTER);
        setupAlertDialog(context, dialog, isCancael);

        TextView textView = (TextView) window.findViewById(R.id.text);
        TextView okBtn = (TextView) window.findViewById(R.id.ok);
        TextView cancelBtn = (TextView) window.findViewById(R.id.cancel);

//        okBtn.setTextColor(okBtnColor);
        okBtn.setTextColor(ContextCompat.getColor(context, okBtnColor));
        okBtn.setText(okBtnText);

        textView.setText(info);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canelListener != null) {
                    canelListener.onClick(v);
                }
                dialog.dismiss();
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (okListener != null) {
                    okListener.onClick(view);
                }
                dialog.dismiss();
            }
        });
        return dialog;
    }


    private static void setupAlertDialog(Context context, Dialog dialog, boolean isSucced) {
        setupAlertDialog(context, dialog, -1, isSucced);
    }

    /**
     * 设置支付密码样式
     * @param context
     * @param dialog
     * @param width
     * @param isSucced
     */
    private static void setupAlertDialog(Context context, Dialog dialog, int width, boolean isSucced) {
        dialog.setCancelable(isSucced);
        Window window = dialog.getWindow();
        if (window == null) return;
        WindowManager.LayoutParams lp = window.getAttributes();
        if (width < 0) {
            lp.width = UIUtil.getScreenWidth(context); //设置宽度
        } else {
            lp.width = width; //设置宽度
        }
        window.setAttributes(lp);
        window.setDimAmount(0.45f);//设置背景黑暗度
        window.setBackgroundDrawableResource(android.R.color.transparent);//窗体透明度
    }

    private static void setupFullAlertDialog(Context context, Dialog dialog, boolean isSucced) {
        dialog.setCancelable(isSucced);
        Window window = dialog.getWindow();
        if (window == null) return;
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        window.setDimAmount(0.45f);//设置背景黑暗度
        window.setBackgroundDrawableResource(android.R.color.transparent);//窗体透明度
    }

    public interface PayPasswordVertifyListener {
        void onVertifyPayPasswordSuccess(String psw);
    }
}
