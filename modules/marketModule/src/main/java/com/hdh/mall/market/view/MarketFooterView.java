package com.hdh.mall.market.view;

import android.content.Context;
import android.widget.LinearLayout;

import com.hdh.mall.market.R;

import butterknife.ButterKnife;

/**
 * Created by albert on 2017/10/31.
 */

public class MarketFooterView extends LinearLayout {

    public MarketFooterView(Context context) {
        super(context);
        inflate(context, R.layout.market_layout_market_footer, this);
        ButterKnife.bind(this);

    }
}
