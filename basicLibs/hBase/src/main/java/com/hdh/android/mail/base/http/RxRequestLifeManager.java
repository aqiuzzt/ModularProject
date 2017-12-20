package com.hdh.android.mail.base.http;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Rx请求生命周期管理
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/23 18:28
 */
public class RxRequestLifeManager {

    private static RxRequestLifeManager sLifeManager;
    private static final Object mLock = new Object();

    public static RxRequestLifeManager instance() {
        if (sLifeManager == null) {
            synchronized (mLock) {
                if (sLifeManager == null) {
                    sLifeManager = new RxRequestLifeManager();
                }
            }
        }
        return sLifeManager;
    }

    private Map<String, CompositeDisposable> mStack;

    private RxRequestLifeManager() {
    }

    private boolean _add(String tag, Disposable d) {
        CompositeDisposable mCompositeDisposable = checkDisposableContainer(tag);
        mCompositeDisposable.add(d);
        return true;
    }

    private CompositeDisposable checkDisposableContainer(String tag) {
        CompositeDisposable mCompositeDisposable;
        synchronized (mLock) {
            if (mStack == null) mStack = new HashMap<>();
            if (!mStack.containsKey(tag)) {
                mCompositeDisposable = new CompositeDisposable();
                mStack.put(tag, mCompositeDisposable);
            } else {
                mCompositeDisposable = mStack.get(tag);
            }
        }
        return mCompositeDisposable;
    }

    private boolean _remove(String tag, Disposable d) {
        synchronized (mLock) {
            if (mStack == null) return false;
            if (mStack.containsKey(tag)) {
                CompositeDisposable cd = mStack.get(tag);
                cd.remove(d);
                if (cd.size() == 0) {
                    cd.clear();
                    mStack.remove(tag);
                    return true;
                }
            }
            return false;
        }
    }

    private boolean _remove(String tag) {
        synchronized (mLock) {
            if (mStack == null) return false;
            if (mStack.containsKey(tag)) {
                CompositeDisposable cd = mStack.get(tag);
                cd.clear();
                mStack.remove(tag);
                return true;
            }
            return false;
        }
    }

    private void _clear() {
        synchronized (mLock) {
            if (mStack == null) return;
            Collection<CompositeDisposable> listIterator = mStack.values();
            for (CompositeDisposable d : listIterator) {
                d.clear();
            }
            mStack.clear();
        }
    }


    public static boolean add(String tag, Disposable d) {
        return instance()._add(tag, d);
    }


    public static boolean remove(String tag, Disposable d) {
        return instance()._remove(tag, d);
    }

    public static boolean remove(String tag) {
        return instance()._remove(tag);
    }

    public static void clear() {
        instance()._clear();
    }
}
