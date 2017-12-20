package com.hdh.mall.market;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hdh.android.mail.base.modular.provider.IMarketProvider;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gzhjhc.com">Contact me.</a>
 * @since 2017/12/6 14:41
 */
@Route(path = IMarketProvider.MARKET_MAIN_SERVICE)
public class MarketProvider implements IMarketProvider {
    @Override
    public void init(Context context) {

    }

    @Override
    public Fragment newInstance(Object... args) {

        return MarketsFragment.getInstance();
    }
}
