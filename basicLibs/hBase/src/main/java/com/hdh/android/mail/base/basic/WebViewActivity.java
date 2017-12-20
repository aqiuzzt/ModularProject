package com.hdh.android.mail.base.basic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.hdh.android.mail.base.R;
import com.hdh.android.mail.base.R2;
import com.hdh.android.mail.base.activitys.BaseCommonActivity;
import com.hdh.common.util.LogUtil;
import com.hdh.common.util.StringUtil;

import java.lang.ref.WeakReference;

import butterknife.BindView;

import static android.view.View.VISIBLE;


/**
 * 商品网页跳转
 */
public class WebViewActivity extends BaseCommonActivity {
    public static String TAG = WebViewActivity.class.getSimpleName();
    @BindView(R2.id.progressbar)
    ProgressBar mProgressbar;
    @BindView(R2.id.content)
    FrameLayout mContent;
    private WebView mWebView;
    private String title;
    private String url;

    @Override
    public int setLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void bindView() {
        super.bindView();
        if (mWebView != null) {
            mWebView.destroy();
        }
        mWebView = new WebView(this);
        configWebView();
        mContent.addView(mWebView);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void configWebView() {
        WebSettings mWebSetting = mWebView.getSettings();  //获取WebSetting
        mWebSetting.setJavaScriptEnabled(true);//让WebView支持JavaScript
        mWebSetting.setDomStorageEnabled(true);//启用H5 DOM API （默认false）

        mWebSetting.setUseWideViewPort(true);  //将图片调整到适合webview的大小
        mWebSetting.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        mWebSetting.setDisplayZoomControls(false); //隐藏原生的缩放控件

        mWebSetting.setLoadsImagesAutomatically(true);  //自动加载图片
        mWebSetting.setDefaultTextEncodingName("utf-8");//设置编码格式
        mWebSetting.setBuiltInZoomControls(true);
        mWebSetting.setSupportZoom(true);//设定支持缩放
        mWebSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

    }

    @Override
    public void onPause() {
        super.onPause();
        mWebView.pauseTimers();
        mWebView.onPause();
    }

    public static class WebChromeClient extends android.webkit.WebChromeClient {
        private WeakReference<WebViewActivity> mReference;

        public WebChromeClient(WebViewActivity activity) {
            mReference = new WeakReference<>(activity);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (mReference.get() != null) {
                if (newProgress == 100) {
                    mReference.get().mProgressbar.setVisibility(View.GONE);
                } else {
                    if (mReference.get().mProgressbar.getVisibility() == View.GONE)
                        mReference.get().mProgressbar.setVisibility(VISIBLE);
                    mReference.get().mProgressbar.setProgress(newProgress);
                }
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            if (StringUtil.isEmpty(mReference.get().title)) {
                LogUtil.i(TAG, "title:" + title);
                if (title.contains(".")) {
                    title = title.replace(".html", "");
                }
                mReference.get().getToolBarX().setTitle(title);
            } else {
                LogUtil.i(TAG, "mReference title:" + mReference.get().title);
                String titleStr;
                if (mReference.get().title.contains(".")) {
                    titleStr = mReference.get().title.replace(".html", "");
                    mReference.get().getToolBarX().setTitle(titleStr);
                } else {
                    mReference.get().getToolBarX().setTitle(mReference.get().title);
                }
            }
        }
    }

    @Override
    public void onResume() {
        mWebView.resumeTimers();
        mWebView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if (mWebView != null) {
            mWebView.destroy();
            mWebView = null;
        }
        mContent.removeAllViews();
        super.onDestroy();
    }

    @Override
    protected void bindData() {
        mWebView.loadUrl(url);
    }

    @Override
    protected void handleIntent(Intent intent, Bundle savedInstanceState) {
        super.handleIntent(intent, savedInstanceState);
        if (intent != null) {
            title = getIntent().getStringExtra("title");
            url = getIntent().getStringExtra("url");
        }
    }

    @Override
    protected void bindEvent() {
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient(this));
    }

    public static void start(Context context, String title, String url) {
        Intent starter = new Intent(context, WebViewActivity.class);
        starter.putExtra("title", title);
        starter.putExtra("url", url);
        context.startActivity(starter);
    }

    @Override
    public void onBackPressed() {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}

