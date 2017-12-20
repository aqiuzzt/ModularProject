package com.hdh.android.mail.base.manager;

import android.app.Activity;
import android.content.Context;

import com.hdh.common.util.LogUtil;

import java.util.Stack;

/**
 * activity管理类
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/24 14:14
 */
public class ActivityManager {

    private static final String LOG_TAG = "PFActivityManager";

    /**
     * activity管理单例
     */
    private static ActivityManager mActivityManger;
    /**
     * activity栈
     */
    private Stack<Activity> mActivityStack;


    /**
     * activity管理单例
     *
     * @return
     */
    public static synchronized ActivityManager getmActivityManger() {
        if (mActivityManger == null) {
            mActivityManger = new ActivityManager();
        }
        return mActivityManger;
    }


    /**
     * 把一个activity压入栈中
     *
     * @param actvity 压入的activity
     */
    public void pushOneActivity(Activity actvity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<Activity>();
        }
        mActivityStack.add(actvity);
        LogUtil.i(LOG_TAG, "PFActivityManager sizes = " + mActivityStack.size());
    }


    /**
     * 获取栈顶的activity，先进后出原则
     *
     * @return
     */
    public Activity getLastActivity() {
        return mActivityStack.lastElement();
    }

    /**
     * 移除一个activity
     *
     * @param activity
     */
    public void popOneActivity(Activity activity) {
        if (mActivityStack != null && mActivityStack.size() > 0) {
            if (activity != null) {
                activity.finish();
                mActivityStack.remove(activity);
                activity = null;

            }
        }
    }

    /**
     * 退出所有activity
     */
    public void popAllAllActivity() {
        if (mActivityStack != null) {
            while (mActivityStack.size() > 0) {
                Activity activity = getLastActivity();
                if (activity == null)
                    break;
                popOneActivity(activity);
            }
            mActivityStack.clear();
        }
    }


    /**
     * 获得当前栈顶Activity
     *
     * @return
     */
    public Activity currentActivity() {
        Activity activity = null;
        if (!mActivityStack.empty())
            activity = mActivityStack.lastElement();
        return activity;
    }


    /**
     * 退出栈中除了指定activity之外的所有Activity
     *
     * @param cls
     */
    public void popAllActivityExceptOne(Class cls) {
        while (mActivityStack.size() > 1) {
            Activity activity = mActivityStack.get(0);
            if (activity == null) {
                break;
            }
            if (!activity.getClass().equals(cls)) {
                popOneActivity(activity);
            }

        }

        LogUtil.i(LOG_TAG, "popAllActivityExceptOne PFActivityManager sizes = " + mActivityStack.size());
    }

    /**
     * app退出
     *
     * @param context
     */
    public void AppExit(Context context) {
        try {
            popAllAllActivity();
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getActivityStackLength() {
        return mActivityStack.size();
    }


}
