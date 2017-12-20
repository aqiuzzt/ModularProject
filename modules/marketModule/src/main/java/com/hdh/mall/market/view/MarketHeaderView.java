package com.hdh.mall.market.view;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;

import com.hdh.android.mail.base.bean.BannerBean;
import com.hdh.mall.market.R;
import com.hdh.mall.market.R2;
import com.hdh.widget.banner.Banner;
import com.hdh.widget.banner.BannerImageLoader;
import com.hdh.widget.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商城 :banner 和 商品分类
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/12/5 17:55
 */

public class MarketHeaderView extends LinearLayout {
    private static final String LOG_TAG = "MarketHeaderView";
    @BindView(R2.id.banner)
    Banner mBanner;

    private Context mContext;
    private OnCategoryClickListener onCategoryClickListener;

    public MarketHeaderView(Context context) {
        super(context);
        mContext = context;
        inflate(context, R.layout.market_layout_market_header, this);
        ButterKnife.bind(this);

        mBanner.post(new Runnable() {
            @Override
            public void run() {
                Log.d(LOG_TAG, "banner height " + mBanner.getHeight() + "  width " + mBanner.getWidth());
            }
        });

    }

    /**
     * 更新商家banner
     *
     * @param list
     */
    public void refreshBanner(final List<BannerBean> list) {

        ArrayList<String> urls = new ArrayList<>();
        for (BannerBean bean : list) {
            urls.add(bean.image);
        }
        mBanner.setImages(urls).setImageLoader(new BannerImageLoader()).isAutoPlay(true).start();
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (list != null) {
                    BannerBean bean = list.get(position);

                    if (onCategoryClickListener != null) {
                        onCategoryClickListener.onClickBanner(bean);
                    }
                }
            }
        });
    }

    public interface OnCategoryClickListener {


        void onClickBanner(BannerBean bannerBean);
    }

    public void setOnCategoryClickListener(OnCategoryClickListener l) {
        onCategoryClickListener = l;
    }


}
