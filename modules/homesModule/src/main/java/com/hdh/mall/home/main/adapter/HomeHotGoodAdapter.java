package com.hdh.mall.home.main.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hdh.android.mail.base.bean.BannerBean;
import com.hdh.mall.home.R;
import com.hdh.widget.banner.BannerImageLoader;


/**
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/12/4 08:59
 */
public class HomeHotGoodAdapter extends BaseQuickAdapter<BannerBean> {
    private Context mContext;
    private OnBannerClickListener onBannerClickListener;

    public HomeHotGoodAdapter(Context context) {
        super(R.layout.item_home_hot_recommand, null);
        mContext = context;
    }

    public void setOnBannerClickListener(OnBannerClickListener l) {
        onBannerClickListener = l;
    }

    @Override
    protected void convert(BaseViewHolder helper, final BannerBean item) {
        ImageView goodIv = helper.getView(R.id.item_hot_recommand_iv);
        if (!TextUtils.isEmpty(item.image)) {
            BannerImageLoader.display(mContext, item.image, goodIv);
        } else {
            BannerImageLoader.display(mContext, "", goodIv);
        }

        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBannerClickListener != null) {
                    onBannerClickListener.onClickBanner(item);
                }
            }
        });
    }


    public interface OnBannerClickListener {

        void onClickBanner(BannerBean bannerBean);
    }
}
