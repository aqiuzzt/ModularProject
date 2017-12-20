package com.hdh.android.mail.base.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;


import com.hdh.android.mail.base.R;
import com.hdh.common.util.CheckUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * 自定义Toolbar
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/23 11:37
 */
public class ToolBarX implements View.OnClickListener {
    private Toolbar mToolbar;
    private AppCompatActivity mActivity;
    private ActionBar mActionBar;

    public static final int TITLE_OPTS_NORMAL = 0;
    /**
     * 标题在中间
     */
    public static final int TITLE_OPTS_CENTER = 1;
    public static final int TITLE_OPTS_NONE = 2;
    private TextView mTitleView;
    private TextView mMenuTilteView;
    private int titleOptions;
    /**
     * 返回按钮图标id
     */
    private int backResId = R.drawable.ic_back;

    public ToolBarX inflateMenu(@MenuRes int menuId) {
        if (mToolbar == null) return this;
        mToolbar.inflateMenu(menuId);
        return this;
    }

    public Menu getMenu() {
        if (mToolbar == null) {
            return null;
        }
        return mToolbar.getMenu();
    }

    /**
     * 设置菜单监听
     *
     * @param listener
     * @return
     */
    public ToolBarX setOnMenuItemClickListener(@NonNull Toolbar.OnMenuItemClickListener listener) {
        if (mToolbar == null) return this;
        mToolbar.setOnMenuItemClickListener(listener);
        return this;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TITLE_OPTS_CENTER, TITLE_OPTS_NONE, TITLE_OPTS_NORMAL})
    public @interface TitleOption {
    }

    public ToolBarX(@Nullable Toolbar toolbar, @NonNull AppCompatActivity activity) {
        if (toolbar == null) return;
        this.mToolbar = toolbar;
        this.mActivity = activity;
        mActivity.setSupportActionBar(mToolbar);
        mActionBar = mActivity.getSupportActionBar();
        setDisplayHomeAsUpEnabled(true); // 返回按钮
        setNavigationIcon(backResId);
        setTitleOptions(TITLE_OPTS_CENTER);
        mToolbar.setNavigationOnClickListener(this);
    }

    /**
     * Fragment中使用Toolbar的情况
     *
     * @param toolbar
     */
    public ToolBarX(@Nullable Toolbar toolbar) {
        if (toolbar == null) return;
        this.mToolbar = toolbar;
        setDisplayHomeAsUpEnabled(true); // 返回按钮
        setNavigationIcon(backResId);
        setTitleOptions(TITLE_OPTS_CENTER);
        mToolbar.setNavigationOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mActivity != null) {
            mActivity.finish();
        }
    }

    public ToolBarX onBackPressed(View.OnClickListener listener) {
        if (mToolbar == null) return this;
        mToolbar.setNavigationOnClickListener(listener);
        return this;
    }

    /**
     * 设置Toolbar的背景色
     *
     * @param color
     * @return
     */
    public ToolBarX setBackgroundColor(@ColorRes int color) {
        if (mToolbar == null) return this;
        Context context = mToolbar.getContext();
        mToolbar.setBackgroundColor(ContextCompat.getColor(context, color));
        return this;
    }

    /**
     * 使得状态栏透明
     *
     * @return
     */
    public ToolBarX transparentizeToolbarBackground() {
        if (mToolbar == null) return this;
        mToolbar.setBackgroundColor(Color.TRANSPARENT);
        return this;
    }

    /**
     * 设置标题
     *
     * @param title
     * @return
     */
    public ToolBarX setTitle(CharSequence title) {
        if (mToolbar == null) return this;
        findTitleView();
        if (mTitleView != null) {
            mTitleView.setText(title);
        } else if (mActionBar != null) {
            mActionBar.setTitle(title);
        }
        return this;
    }

    /**
     * 设置右边菜单标题
     *
     * @param title
     * @return
     */
    public ToolBarX setMenuTitle(CharSequence title) {
        if (mToolbar == null) return this;
        findMenuTitleView();
        if (mMenuTilteView != null) {
            mMenuTilteView.setText(title);
        } else if (mActionBar != null) {
            mActionBar.setTitle(title);
        }
        return this;
    }

    /**
     * 设置右边菜单标题 颜色
     *
     * @param color
     * @return
     */
    public ToolBarX setMenuTitleColor(CharSequence title, int color) {
        if (mToolbar == null) return this;
        findMenuTitleView();
        if (mMenuTilteView != null) {
            mMenuTilteView.setText(title);
            mMenuTilteView.setTextColor(color);
        } else if (mActionBar != null) {
            mActionBar.setTitle(title);
        }
        return this;
    }


    /**
     * 设置右边菜单标题 颜色
     *
     * @return
     */
    public ToolBarX setMenuTitleBg(CharSequence title, Drawable bg) {
        if (mToolbar == null) return this;
        findMenuTitleView();
        if (mMenuTilteView != null) {
            mMenuTilteView.setText(title);
            mMenuTilteView.setBackgroundDrawable(bg);
        } else if (mActionBar != null) {
            mActionBar.setTitle(title);
        }
        return this;
    }

    public void setOnMenuTitleClick(View.OnClickListener listener) {
        if (mMenuTilteView == null) {
            findTitleView();
        }
        mMenuTilteView.setOnClickListener(listener);
    }

    public void setOnTitleClick(View.OnClickListener listener) {
        if (mTitleView == null) {
            findTitleView();
        }
        mTitleView.setOnClickListener(listener);
    }

    /**
     * 设置标题
     *
     * @param title
     * @return
     */
    public ToolBarX setTitle(@StringRes int title) {
        if (CheckUtil.checkNull(mToolbar)) return this;
        return setTitle(mToolbar.getContext().getString(title));
    }

    private void findTitleView() {
        if (mTitleView == null && titleOptions == TITLE_OPTS_CENTER) {
            mTitleView = (TextView) mToolbar.findViewById(R.id.toolbar_title_view);
            if (mTitleView == null)
                throw new IllegalStateException("请使用R.toolbar_simple_layout,或者标题设置为R.id.toolbar_title_view");
        }
    }

    private void findMenuTitleView() {
        if (mMenuTilteView == null) {
            mMenuTilteView = (TextView) mToolbar.findViewById(R.id.toolbar_menu_title_view);
            if (mMenuTilteView == null)
                throw new IllegalStateException("请使用R.toolbar_simple_layout,或者标题设置为R.id.toolbar_menu_title_view");
        }
    }

    /**
     * 控制Toolbar显示
     *
     * @return
     */
    public ToolBarX show() {
        if (mToolbar == null || mActionBar == null) return this;
        mActionBar.show();
        return this;
    }

    /**
     * 控制Toolbar隐藏
     *
     * @return
     */
    public ToolBarX hide() {
        if (mToolbar == null || mActionBar == null) return this;
        mActionBar.hide();
        return this;
    }

    /**
     * 隐藏返回按钮
     *
     * @return
     */
    public ToolBarX setDisplayHomeAsUpEnabled(boolean enable) {
        if (!enable) {
            setNavigationIcon(-1);
        } else {
            setNavigationIcon(backResId);
        }
        return this;
    }

    /**
     * 设置标题的显示方式</br>
     * <li>{@link #TITLE_OPTS_CENTER} 居中显示并且Toolbar必须包含一个TextView且id为#toolbar_title_view 可以选择使用#toolbar_simple_layout</li>
     * <li>{@link #TITLE_OPTS_NORMAL} 使用Toolbar的标题,显示在左边</li>
     * <li>{@link #TITLE_OPTS_NONE} 没有标题</li>
     *
     * @param opts
     * @return
     */
    public ToolBarX setTitleOptions(@TitleOption int opts) {
        if (mToolbar == null) return this;
        this.titleOptions = opts;
        //获取TextView
        switch (opts) {
            case TITLE_OPTS_NORMAL:
                if (mTitleView != null) mTitleView.setVisibility(View.GONE);
                if (mActionBar != null)
                    mActionBar.setDisplayShowTitleEnabled(true);
                break;
            case TITLE_OPTS_NONE:
                if (mTitleView != null) mTitleView.setVisibility(View.GONE);
                if (mActionBar != null)
                    mActionBar.setDisplayShowTitleEnabled(false);
                break;
            case TITLE_OPTS_CENTER:
                if (mTitleView != null) mTitleView.setVisibility(View.VISIBLE);
                if (mActionBar != null)
                    mActionBar.setDisplayShowTitleEnabled(false);
                break;
        }
        return this;
    }

    /**
     * 设置返回按钮的图标
     *
     * @param resId
     * @return
     */
    public ToolBarX setNavigationIcon(@DrawableRes int resId) {
        if (mToolbar == null) return this;
        this.backResId = resId;
        Drawable drawable = null;
        if (resId != -1) drawable = AppCompatResources.getDrawable(mToolbar.getContext(), resId);
        mToolbar.setNavigationIcon(drawable);
        return this;
    }

    /**
     * 设置返回按钮的点击事件,默认为关闭本活动
     *
     * @param listener
     * @return
     */
    public ToolBarX setNavigationOnClickListener(View.OnClickListener listener) {
        if (mToolbar == null) return this;
        mToolbar.setNavigationOnClickListener(listener);
        return this;
    }

    /**
     * 设置右边小三点的图标
     *
     * @param resId
     * @return
     */
    public ToolBarX setOverflowIcon(@DrawableRes int resId) {
        if (mToolbar == null) return this;
        Drawable drawable = ContextCompat.getDrawable(mActivity, resId);
        mToolbar.setOverflowIcon(drawable);
        return this;
    }

    /**
     * 设置自定义视图
     *
     * @param view
     * @param lp
     * @return
     */
    public ToolBarX setCustomView(View view, ActionBar.LayoutParams lp) {
        if (mToolbar == null || mActionBar == null) return this;
        if (lp == null) {
            mActionBar.setCustomView(view);
        } else {
            mActionBar.setCustomView(view, lp);
        }
        return this;
    }

    /**
     * 返回一个自定义视图
     *
     * @return
     */
    public View getCustomView() {
        if (mToolbar == null || mActionBar == null) return null;
        return mActionBar.getCustomView();
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public TextView getmTitleView() {
        return mTitleView;
    }

    public TextView getmMenuTilteView() {
        return mMenuTilteView;
    }
}
