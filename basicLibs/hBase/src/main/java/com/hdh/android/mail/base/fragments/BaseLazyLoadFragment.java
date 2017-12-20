package com.hdh.android.mail.base.fragments;


import com.hdh.android.mail.base.inte.IPresenter;

/**
 * 在ViewPager中进行数据懒加载
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/8 18:53
 */

public abstract class BaseLazyLoadFragment<P extends IPresenter> extends BaseMvpFragment<P> {
    /**
     * 控件是否初始化完成
     */
    protected boolean isViewVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isViewVisible = true;
            onVisible();
        } else {
            isViewVisible = false;
            onInvisible();
        }
    }

    /**
     * 懒加载相关
     **/
    protected void onInvisible() {

    }

    /**
     * 懒加载相关
     **/
    protected void onVisible() {
        lazyLoad();
    }

    /**
     * 懒加载相关
     **/
    public abstract void lazyLoad();
}
