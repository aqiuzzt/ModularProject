package com.hdh.widget.banner;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hdh.common.util.LogUtil;
import com.hdh.widget.R;
import com.hdh.widget.banner.loader.ImageLoader;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gzhjhc.com">Contact me.</a>
 * @since 2017/12/1 11:09
 */

public class BannerImageLoader extends ImageLoader {
    private static final String LOG_TAG = "ImageLoaderImageLoader";
    public static final int DEFAULT_COLOR = 0xfff8f8f8; //淡白色

    public static void display(Context context, String url, ImageView imageView) {
        LogUtil.i(LOG_TAG, "url:" + url);
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate();
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    public static void display(Context context, String url, ImageView imageView, int defaultImg) {
        RequestOptions options = new RequestOptions();
        options.placeholder(defaultImg)
                .error(defaultImg)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate();
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    public static void displayCategory(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.account_avatar)
                .error(R.drawable.account_avatar)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate();
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    public static void displaySize(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions();
        options.override(300, 600)
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate();
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    public static void display(Context context, int id, ImageView imageView) {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate();
        Glide.with(context).load(id).apply(options).into(imageView);
    }


    /**
     * 显示头像 默认图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void displayAvatar(Context context, String url, ImageView imageView) {

        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.ic_account_avatar)
                .centerCrop()
                .error(R.drawable.ic_account_avatar)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate();
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    public static void displayAvatarRole(Context context, String url, ImageView imageView, int roleId) {

        RequestOptions options = new RequestOptions();
        options.placeholder(roleId)
                .centerCrop()
                .error(roleId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate();
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    public static void displayCrop(Context context, String url, ImageView imageView) {

        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate();
        Glide.with(context).load(url).apply(options).into(imageView);
    }


    //用于banner显示
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.default_image_banner)
                .error(R.drawable.default_image_banner)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate();
        Glide.with(context.getApplicationContext()).load(path).apply(options).into(imageView);
    }
}
