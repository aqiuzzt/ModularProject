package com.hdh.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebView;


/**
 * webview滑动监听
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/24 14:27
 */

public class ScrollWebView extends WebView {
    private static final String LOG_TAG = "ScrollWebView";
    public OnScrollChangeListener listener;
    private Context mContext;
    private boolean longPress = false;
    private static double DOUBLE_CLICK_TIME = 0L;
    private float x, y;
    private long downTime;


    public ScrollWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ScrollWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollWebView(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {

        super.onScrollChanged(l, t, oldl, oldt);

        float webcontent = getContentHeight() * getScale();// webview的高度
        float webnow = getHeight() + getScrollY();// 当前webview的高度
        Log.i(LOG_TAG, "webcontent:" + webcontent + " webnow:" + webnow + " getScrollY：" + getScrollY());

        if (Math.abs(webcontent - webnow) < 3) {
            // 已经处于底端
            Log.i(LOG_TAG, "已经处于底端");
            listener.onPageEnd(l, t, oldl, oldt);
        } else if (getScrollY() < 100) {
            Log.i(LOG_TAG, "已经处于顶端");

            listener.onPageTop(l, t, oldl, oldt);

        } else {
            Log.i(LOG_TAG, "oldl-l:" + (oldl - l) + " oldt-t：" + (oldt - t));

            if (!longPress) {
                listener.onScrollChanged(l, t, oldl, oldt);
            }

        }

    }

    public void setOnScrollChangeListener(OnScrollChangeListener listener) {

        this.listener = listener;

    }

    public interface OnScrollChangeListener {
        void onPageEnd(int l, int t, int oldl, int oldt);

        void onPageTop(int l, int t, int oldl, int oldt);

        void onScrollChanged(int l, int t, int oldl, int oldt);

    }

}
