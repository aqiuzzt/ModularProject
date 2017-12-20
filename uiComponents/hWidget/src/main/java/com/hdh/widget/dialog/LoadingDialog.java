package com.hdh.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdh.common.util.LogUtil;
import com.hdh.common.util.view.UIUtil;
import com.hdh.widget.R;


/**
 * 加载对话框
 */
public class LoadingDialog extends DialogFragment {

    private static final String LOG_TAG = "LoadingDialog";
    private Context mContext;
    private ImageView imageView;
    private TextView textView;
    private AnimationDrawable animationDrawable;
    private String text;
    private boolean cancel = false;
    private View mRootView;

    /**
     * 实例 对话框
     *
     * @param text   加载显示文本
     * @param cancel 是否可以点击空白可取消
     * @return
     */
    public static LoadingDialog newInstance(String text, boolean cancel) {
        Bundle args = new Bundle();
        args.putString("text", text);
        args.putBoolean("cancel", cancel);
        LoadingDialog fragment = new LoadingDialog();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = (ImageView) view.findViewById(R.id.loading_image);
        textView = (TextView) view.findViewById(R.id.loading_text);
        textView.setText(text);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            text = getArguments().getString("text");
            cancel = getArguments().getBoolean("cancel");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Material_Dialog);
        } else {
            setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Dialog);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Dialog dialog = getDialog();
        dialog.setOnDismissListener(mOnDismissListener);
        dialog.setCancelable(cancel);
        dialog.setCanceledOnTouchOutside(cancel);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN && mOnForceCloseListener != null) {
                    LogUtil.i(LOG_TAG, "keyCode:" + keyCode);
                    return mOnForceCloseListener.onForceClose(dialog);
                }
                return false;
            }
        });
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(R.drawable.dialog_loading_background);
            WindowManager.LayoutParams lm = window.getAttributes();
            lm.dimAmount = 0;
            int size = (int) (UIUtil.getScreenWidth(mContext) * 0.32f);
            lm.width = size;
            lm.height = size;
            window.setAttributes(lm);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mRootView == null) {
            mRootView = LayoutInflater.from(mContext).inflate(R.layout.dialog_common_loading, container, false);
        } else {
            if (container != null) container.removeView(mRootView);
        }
        return mRootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        imageView.setImageDrawable(null);
        imageView.setImageResource(R.drawable.loading_frame_animation);
        animationDrawable = (AnimationDrawable) imageView.getDrawable();
        if (animationDrawable != null && !animationDrawable.isRunning()) {
            animationDrawable.start();
        }
        if (!TextUtils.isEmpty(text)) textView.setText(text);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (animationDrawable != null && animationDrawable.isRunning()) {
            animationDrawable.stop();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (animationDrawable != null && animationDrawable.isRunning()) {
            animationDrawable.stop();
            imageView.setImageDrawable(null);
        }
    }

    public void setMessage(String message) {
        text = message;
    }

    private DialogInterface.OnDismissListener mOnDismissListener;

    public void setOnDismissListener(DialogInterface.OnDismissListener listener) {
        this.mOnDismissListener = listener;
    }

    public interface OnForceCloseListener {
        boolean onForceClose(DialogInterface dialog);
    }

    /**
     * 强制性退出回调
     */
    private OnForceCloseListener mOnForceCloseListener;

    public void setOnForceCloseListener(OnForceCloseListener onForceCloseListener) {
        mOnForceCloseListener = onForceCloseListener;
    }


}
