package com.hdh.mall.me.main;

import com.hdh.android.mail.base.activitys.BaseMvpActivity;
import com.hdh.common.util.FragmentUtil;
import com.hdh.mall.me.R;
import com.hdh.mall.me.main.presenter.PersonPresenter;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gzhjhc.com">Contact me.</a>
 * @since 2017/12/5 19:39
 */

public class PersonActivity extends BaseMvpActivity<PersonPresenter> {
    private static final String tag = PersonActivity.class.getSimpleName();
    PersonFragment personFragment;

    @Override
    public PersonPresenter createPresenter() {
        return new PersonPresenter(personFragment);
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
        personFragment = PersonFragment.getInstance();
        FragmentUtil.addFragment(this, personFragment, R.id.content, tag);
    }

    @Override
    public int setLayoutId() {
        return R.layout.me_activity_person;
    }
}
