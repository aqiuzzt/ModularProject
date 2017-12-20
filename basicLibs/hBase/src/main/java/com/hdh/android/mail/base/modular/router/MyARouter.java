package com.hdh.android.mail.base.modular.router;

import android.app.Activity;
import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hdh.android.mail.base.modular.config.ParmaBundle;

/**
 * 对ARouter 二次封装
 *
 *<p>
 *     包含参数封装，路由跳转等
 *</p>
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/20 14:34
 */
public class MyARouter {

    private Postcard postcard;

    private MyARouter(Postcard postcard) {
        this.postcard = postcard;
    }

    public static MyARouter newInstance(String path) {
        return new MyARouter(ARouter.getInstance().build(path));
    }

    private boolean checkPostcard() {
        if (postcard == null)
            throw new IllegalArgumentException("MyARouter 的 postcard 为null");
        return true;
    }


    public MyARouter withBundle(ParmaBundle bundle) {
        if (bundle == null) return this;
        checkPostcard();
        postcard.with(bundle.build());
        return this;
    }

    public MyARouter addFlag(int flag) {
        checkPostcard();
        postcard.withFlags(flag);
        return this;
    }

    public Object navigation() {
        return navigation(null);
    }

    public Object navigation(Context context) {
        return navigation(context, null);
    }

    public void navigation(Activity activity, int requestCode) {
        navigation(activity, requestCode, null);
    }

    public Object navigation(Context context, NavigationCallback callback) {
        checkPostcard();
        return postcard.navigation(context, callback);
    }


    public void navigation(Activity activity, int requestCode, NavigationCallback callback) {
        postcard.navigation(activity, requestCode, callback);
    }

}
