package com.hdh.android.mail.base.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hdh.android.mail.base.BaseApplication;
import com.hdh.android.mail.base.R;
import com.hdh.android.mail.base.http.BaseRxCallback;
import com.hdh.android.mail.base.http.CursorResult;
import com.hdh.android.mail.base.http.PageResult;
import com.hdh.android.mail.base.http.Result;
import com.hdh.android.mail.base.inte.IPresenter;
import com.hdh.common.http.builder.RequestBuilder;
import com.hdh.common.http.handler.DataCallback;
import com.hdh.common.util.app.AppNetworkUtil;
import com.hdh.common.util.LogUtil;
import com.hdh.widget.DefaultDecoration;
import com.hdh.widget.EmptyView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;


/**
 * 下拉刷新基类
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/8 18:56
 */

public abstract class BaseRefreshFragment<E, AD extends BaseQuickAdapter<E>, P extends IPresenter> extends BaseLazyLoadFragment<P>
        implements DataCallback {

    EmptyView mEmptyView;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private final RefreshEventDelegate mRefreshEventDelegate = new RefreshEventDelegate();
    private final AutoLoadEventDelegate mAutoLoadEventDelegate = new AutoLoadEventDelegate();
    private AD mAdapter;
    private boolean isViewPrepared;
    private boolean mUseCacheData;
    private boolean refreshing;
    private boolean loading;
    private boolean loadmore;
    private boolean isDownUpddate;
    private boolean isAutoUseEmptyView = true;
    private static final String LOG_LAG = "BaseRefreshFragment";

    @CallSuper
    @Override
    protected void bindView(View mView) {
        super.bindView(mView);
        mEmptyView=mView.findViewById(R.id.emptyView);
        mRecyclerView=mView.findViewById(R.id.recyclerView);
        mSwipeRefreshLayout=mView.findViewById(R.id.swipeRefreshLayout);
        initRefreshLayout();
        initRecyclerView();
    }


    /**
     * 只有在ViewPager时才有用
     *
     * @return
     */
    public boolean isNeedLazyLoadData() {
        return false;
    }

    /**
     * 进来自动刷新一下
     */
    public boolean isNeedAutoRefresh() {
        return true;
    }

    public boolean isLoadMore() {
        return loadmore;
    }

    public void setIsLoadmore(boolean values) {
        loadmore = values;
    }

    public boolean isDownUpddate() {
        return isDownUpddate;
    }

    public void setIsDownUpdate(boolean values) {
        isDownUpddate = values;
    }

    public boolean isAutoUseEmptyView() {
        return isAutoUseEmptyView;
    }

    public void setAutoUseEmptyView(boolean autoUseEmptyView) {
        this.isAutoUseEmptyView = autoUseEmptyView;
    }


    public boolean isRefreshing() {
        return refreshing;
    }

    @NonNull
    public AD getAdapter() {
        return mAdapter;
    }


    /***
     * 创建布局管理器
     *
     * @return
     */
    @NonNull
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Nullable
    protected RecyclerView.ItemDecoration createDefaultItemDecoration() {
        return new DefaultDecoration(getActivity());
    }

    @Nullable
    public EmptyView getEmptyView() {
        return mEmptyView;
    }

    @NonNull
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    protected abstract AD createAdapter();


    /**
     * 获取数据请求
     *
     * @param refresh 是否刷新
     * @return
     */
    public abstract RequestBuilder getRequestBuild(boolean refresh);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        isViewPrepared = true;
        lazyLoad();
        return view;
    }

    @CallSuper
    @Override
    protected void bindData() {
        if (mSwipeRefreshLayout != null) {
            LogUtil.i(LOG_LAG, "bindData cccc" + " swipelayout is refreshCategoryList:" + mSwipeRefreshLayout.isRefreshing());
            if (!isNeedLazyLoadData() && isNeedAutoRefresh() && !mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (mSwipeRefreshLayout != null) {
                            LogUtil.i(LOG_LAG, "bindData load data first.");
                            mSwipeRefreshLayout.setRefreshing(true);
                            refreshing = true;
                            //进入页面默认先刷新一次
                            refreshDataFromServer();
                        }

                    }
                }, 300);
            }
        }
    }

    /**
     * 必须在拿到请求结果后调emitter.next("");
     *
     * @param emitter
     * @return
     */
    public boolean preposeNetworkRequest(ObservableEmitter<Object> emitter) {
        return false;
    }


    @Override
    public int setLayoutId() {
        return R.layout.base_fragment_refresh_base;
    }


    @Override
    public void lazyLoad() {
        if (!isViewVisible || !isViewPrepared ||
                !isNeedLazyLoadData() ||
                (!mUseCacheData && mAdapter != null && mAdapter.getData().size() > 0) ||
                mSwipeRefreshLayout.isRefreshing()) {
            return;
        }

        extraRefreshNetworkRequest();

        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);

                refresh();
            }
        }, 300);
    }


    /**
     * 通过网络获取刷新数据
     */
    protected void refreshDataFromServer() {
        if (AppNetworkUtil.isNetworkAvailable(getContext())) {
            Observable.create(new ObservableOnSubscribe<Object>() {
                @Override
                public void subscribe(final ObservableEmitter<Object> emitter) throws Exception {
                    preposeNetworkRequest(emitter);
                    emitter.onNext("");
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .doOnNext(new Consumer<Object>() {
                        @Override
                        public void accept(@io.reactivex.annotations.NonNull Object o) throws Exception {
                            refreshData();
                        }
                    }).subscribe();
        } else {
            LogUtil.i(LOG_LAG, "refreshDataFromServer no network" + "  mAdapter.getItemCount()：" + mAdapter.getItemCount());
            mSwipeRefreshLayout.setRefreshing(false);
            if (isAutoUseEmptyView && mEmptyView != null && mAdapter.getItemCount() == 0) {
                mEmptyView.show(EmptyView.STATE_NONET);
            }
        }

    }

    /**
     * 刷新数据
     */
    protected void refreshData() {
        LogUtil.i(LOG_LAG, "refreshData ");
        if (AppNetworkUtil.isNetworkAvailable(getContext())) {
            RequestBuilder builder = getRequestBuild(true);
            if (builder != null) {
                handleRequestBuild(builder, getRxRequestEnable());
            }
            extraRefreshNetworkRequest();

        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            if (isAutoUseEmptyView && mEmptyView != null && mAdapter.getItemCount() == 0)
                mEmptyView.show(EmptyView.STATE_NONET);
        }
    }

    /**
     * rx请求获取数据
     * <br></>如果不想使用Rx那么自己实现这个方法
     *
     * @param builder
     */
    private void handleRequestBuild(RequestBuilder builder, boolean rx) {
        LogUtil.i(LOG_LAG, "handleRequestBuild builder:" + builder.toString());
        if (rx)
            builder.rxRequest()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(baseRxCallback);
        else builder.request(this);
    }


    public void extraRefreshNetworkRequest() {

    }

    @CallSuper
    @Override
    protected void bindEvent() {
        //下拉刷新
        mSwipeRefreshLayout.setOnRefreshListener(mRefreshEventDelegate);
        //上拉刷新
        mAdapter.setOnLoadMoreListener(mAutoLoadEventDelegate);


    }

    /**
     * 初始化
     */
    @CallSuper
    public void initRefreshLayout() {
        if (!allowPullToRefresh())
            mSwipeRefreshLayout.setEnabled(false);
        int end = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, mContext.getResources().getDisplayMetrics());
        mSwipeRefreshLayout.setProgressViewOffset(true, 0, end);
        //设置刷新加载颜色
        mSwipeRefreshLayout.setColorSchemeResources(R.color.color_bg_yellow);
    }

    @CallSuper
    public void initRecyclerView() {
        mAdapter = createAdapter();
        mAdapter.openLoadMore(getPageSize());
        mRecyclerView.setLayoutManager(createLayoutManager());
        initLayoutManger(mRecyclerView.getLayoutManager(), mAdapter);
        RecyclerView.ItemDecoration itemDecoration = createDefaultItemDecoration();
        if (itemDecoration != null)
            mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setAdapter(mAdapter);
    }


    protected void initLayoutManger(RecyclerView.LayoutManager layoutManager, AD adapter) {
    }

    /**
     * 单页数据数 (这个跟LoadMore开闭有关)
     *
     * @return
     */
    protected int getPageSize() {
        return 10;
    }

    /**
     * 是否允許刷新
     *
     * @return
     */
    protected boolean allowPullToRefresh() {
        return true;
    }

    @NonNull
    public SwipeRefreshLayout getRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    @NonNull
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    /**
     * 下拉刷新监听
     */
    private class RefreshEventDelegate implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            isDownUpddate = true;
            LogUtil.i(LOG_LAG, "isDownUpddate:" + isDownUpddate);
            refresh();
        }
    }

    /**
     * 自动刷新
     */
    private class AutoLoadEventDelegate implements BaseQuickAdapter.RequestLoadMoreListener {
        @Override
        public void onLoadMoreRequested() {
            loadmore = true;
            loadMore();
        }
    }

    /**
     * 请求刷新
     */

    public void refresh() {
        if (!AppNetworkUtil.isNetworkAvailable(mContext)) {
            mSwipeRefreshLayout.setRefreshing(false);
            if (isAutoUseEmptyView && mEmptyView != null && mAdapter.getItemCount() == 0)
                LogUtil.i(LOG_LAG, "refresh mAdapter.getItemCount():" + mAdapter.getItemCount());
            mEmptyView.show(EmptyView.STATE_NONET);
            return;
        }
        if (!loading && !refreshing) {
            refreshData();
            refreshing = true;
        }
    }

    /**
     * 请求加载更多
     */
    private void loadMore() {
        if (!AppNetworkUtil.isNetworkAvailable(BaseApplication.get())) {
            try {
                mAdapter.showLoadMoreFailedView();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (!loading && !refreshing) {
                loadMoreDataFromServer();
                loading = true;
            }

        }
    }

    /**
     * 从服务器上 加载更多
     */
    protected void loadMoreDataFromServer() {
        LogUtil.i(LOG_LAG, "loadMoreDataFromServer ");
        RequestBuilder builder = getRequestBuild(false);
        if (builder != null) {
            handleRequestBuild(builder, getRxRequestEnable());

        }
    }

    /**
     * 请求是否使用Rx
     *
     * @return
     */
    protected boolean getRxRequestEnable() {
        return true;
    }

    /**
     * 滑动到顶部
     */
    public void seekToTop() {
        RecyclerView.LayoutManager lm = mRecyclerView.getLayoutManager();
        if (lm instanceof LinearLayoutManager) {
            ((LinearLayoutManager) lm).scrollToPositionWithOffset(0,
                    0);
        } else if (lm instanceof GridLayoutManager) {
            ((GridLayoutManager) lm).scrollToPositionWithOffset(0,
                    0);
        } else if (lm instanceof StaggeredGridLayoutManager) {
            ((StaggeredGridLayoutManager) lm).scrollToPositionWithOffset(
                    0, 0);
        }
    }


    /**
     * 强制刷新,带下拉动画
     */
    public void forceRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mSwipeRefreshLayout != null && !mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(true);
                    refresh();
                }
            }
        }, 300);
    }

    /**
     * 请求接口回调基类
     */
    private BaseRxCallback<String> baseRxCallback = new BaseRxCallback<String>() {
        @Override
        public void onSuccess(String body) {
            handleOnSuccessCallback(body);
            LogUtil.d(LOG_LAG, "baseRxCallback onLoadDataSuccess body:" + body);
        }

        @Override
        public void onFailed(Throwable e) {
            showToast(e.getMessage());
            hideLoading();
            LogUtil.d(LOG_LAG, "baseRxCallback onFailed msg:" + e.getMessage());

        }

        @Override
        public void onError(Throwable e) {
            LogUtil.d(LOG_LAG, "baseRxCallback onError e:" + e.getCause());
            handleOnFailedCallback();
        }
    };

    /**
     * onSuccess 请求后台数据返回成功处理
     *
     * @param body
     */
    void handleOnSuccessCallback(String body) {
        LogUtil.d(LOG_LAG, " handleOnSuccessCallback  body" + body);
        Result<List<E>> listResult = null;
        Result<CursorResult<List<E>>> cursorResultResult = null;
        Result<PageResult<List<E>>> pageResultResult = null;
        List<E> data = null;
        try {
            listResult = handleListDataResult(body);
            if (listResult == null) {
                cursorResultResult = handleListCursorDataResult(body);
                if (cursorResultResult == null) {
                    pageResultResult = handleListPageDataResult(body);
                }
            }
            if (listResult != null) data = listResult.data;
            if (cursorResultResult != null) data = cursorResultResult.data.data;
            if (pageResultResult != null) data = pageResultResult.data.data;
            if (refreshing) {
                mAdapter.setNewData(data); ///清空
            } else {
                if (data != null && data.size() > 0)
                    mAdapter.addData(data);
                else {
                    mAdapter.loadComplete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (mAdapter != null) {
                try {
                    mAdapter.showLoadMoreFailedView();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

            loadAndRefreshComplete();
        }
        if (!isAutoUseEmptyView) {
            handleEmptyViewDismiss(mAdapter.getItemCount());
        }
        if (isAutoUseEmptyView) {
            LogUtil.i(LOG_LAG, "handleOnSuccessCallback mAdapter.getItemCount:"
                    + mAdapter.getItemCount() + " getdata size:" + mAdapter.getData().size());

            if (mAdapter.getItemCount() != 0) {
                if (mEmptyView != null) {
                    mEmptyView.dismiss();
                } else {
                    hideEmptyView();
                }
            } else {
                if (mEmptyView != null) {
                    mEmptyView.show(EmptyView.STATE_NODATA);
                } else {
                    showEmptyView(EmptyView.STATE_NODATA);
                }
            }
        }
        loadAndRefreshComplete();
    }

    @Override
    public void onSuccess(int id, String body) {
        handleOnSuccessCallback(body);
    }

    protected Result<PageResult<List<E>>> handleListPageDataResult(String body) {
        return null;
    }

    protected Result<CursorResult<List<E>>> handleListCursorDataResult(String body) {
        return null;
    }


    public void handleEmptyViewDismiss(int count) {

    }


    protected Result<List<E>> handleListDataResult(String data) {
        return null;
    }


    @Override
    public void onFailed(int id, Call call, Exception e) {
        handleOnFailedCallback();
    }

    /**
     * 处理失败回调
     */
    void handleOnFailedCallback() {
        try {
            mAdapter.showLoadMoreFailedView();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        if (isAutoUseEmptyView) {
            if (mEmptyView != null) {
                mEmptyView.show(EmptyView.STATE_ERROR);
            } else {
                showEmptyView(EmptyView.STATE_ERROR);
            }
        }
        loadAndRefreshComplete();
    }

    /**
     * 刷新加载数据完成
     */
    public void loadAndRefreshComplete() {
        refreshing = false;
        loading = false;
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            if (mSwipeRefreshLayout != null) {
                mSwipeRefreshLayout.setRefreshing(false);
                mSwipeRefreshLayout.destroyDrawingCache();
                mSwipeRefreshLayout.clearAnimation();
            }
        } else {
            if (mSwipeRefreshLayout != null && refreshing) {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        }
        super.onHiddenChanged(hidden);
    }

    public void showEmptyView(@EmptyView.ViewState int state) {
        if (mEmptyView != null) {
            mEmptyView.show(state);
        }
    }

    public void hideEmptyView() {
        if (mEmptyView != null) {
            mEmptyView.dismiss();
        }
    }
}
