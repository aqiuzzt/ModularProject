package com.hdh.common.thread;

import android.support.annotation.AnyThread;
import android.support.annotation.WorkerThread;


public abstract class VoidThread implements ThreadDelegate {
    private Runnable mThread;
    private long mKey;

    public VoidThread() {
        mThread = new Runnable() {
            @Override public void run() {
                doInBackground();
                deliverEnd();
            }
        };
    }

    private void deliverEnd() {
        ThreadUtil.getMainHandler().post(new Runnable() {
            @Override public void run() {
                onPostExecute();
            }
        });
    }

    /***
     * 这个方法执行在UI线程
     */
    @AnyThread
    protected void onPostExecute() {
    }

    /**
     * 执行线程返回对应的key
     *
     * @return
     */
    public final long start() {
        mKey = ThreadUtil.execute(mThread);
        return mKey;
    }

    @Override public final boolean cancel(long key) {
        return ThreadUtil.cancel(mKey);
    }

    /**
     * 线程需要执行的内容
     */
    @WorkerThread
    public abstract void doInBackground();
}
