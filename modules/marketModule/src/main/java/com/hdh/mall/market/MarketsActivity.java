package com.hdh.mall.market;

import com.hdh.android.mail.base.activitys.BaseMvpActivity;
import com.hdh.common.util.FragmentUtil;
import com.hdh.mall.market.presenter.MarketPresenter;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gzhjhc.com">Contact me.</a>
 * @since 2017/12/4 15:04
 */

public class MarketsActivity extends BaseMvpActivity<MarketPresenter> {
    public static String tag = MarketsActivity.class.getSimpleName();
    private MarketsFragment marketsFragment;

    @Override
    public MarketPresenter createPresenter() {
        return new MarketPresenter(marketsFragment);
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    protected void bindFragment() {
        super.bindFragment();
        marketsFragment = MarketsFragment.getInstance();
        FragmentUtil.addFragment(this, marketsFragment, R.id.content, tag);
    }

    @Override
    protected void bindData() {


    }

    @Override
    public int setLayoutId() {
        return R.layout.market_activity_markets;
    }
}
