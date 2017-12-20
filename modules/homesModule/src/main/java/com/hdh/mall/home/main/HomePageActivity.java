package com.hdh.mall.home.main;

import com.hdh.android.mail.base.activitys.BaseMvpActivity;
import com.hdh.common.util.FragmentUtil;
import com.hdh.mall.home.R;
import com.hdh.mall.home.main.presenter.HomePresenter;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gzhjhc.com">Contact me.</a>
 * @since 2017/12/5 15:38
 */

public class HomePageActivity extends BaseMvpActivity<HomePresenter> {
    public static String tag = HomePageActivity.class.getSimpleName();
    HomePageFragment homePageFragment;

    @Override
    public HomePresenter createPresenter() {
        return new HomePresenter(homePageFragment);
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    protected void bindData() {

    }

    @Override
    protected void bindFragment() {
        super.bindFragment();
        homePageFragment = HomePageFragment.getInstance();
        FragmentUtil.addFragment(this, homePageFragment, R.id.content, tag);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_home_page;
    }
}
