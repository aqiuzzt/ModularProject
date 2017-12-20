package com.hdh.android.mail.base.fragments;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hdh.android.mail.base.BaseApplication;
import com.hdh.android.mail.base.R;
import com.hdh.android.mail.base.R2;
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
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;

/**
 * SmartResfresh基类
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/8 18:55
 */

public abstract class BaseSmartRefreshFragment<E, AD extends BaseQuickAdapter<E>, P extends IPresenter> extends BaseLazyLoadFragment<P>
        implements DataCallback {

    @Nullable
    @BindView(R2.id.emptyView)
    EmptyView mEmptyView;
    @Nullable
    @BindView(R2.id.recyclerView)
    RecyclerView mRecyclerView;
    @Nullable
    @BindView(R2.id.swipeRefreshLayout)
    SmartRefreshLayout mSwipeRefreshLayout;
    private AD mAdapter;
    private boolean isViewPrepared;
    private boolean refreshing;
    private boolean loading;
    private boolean isDownUpddate;
    private boolean isAutoUseEmptyView = true;
    protected int pageIndex = 1;
    private static final String LOG_TAG = "BaseSmartRefreshFragment";

    @CallSuper
    @Override
    protected void bindView(View mView) {
        super.bindView(mView);
        mEmptyView = mView.findViewById(R.id.emptyView);
        mRecyclerView = mView.findViewById(R.id.recyclerView);
        mSwipeRefreshLayout = mView.findViewById(R.id.swipeRefreshLayout);
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

    /**
     * 自动显示空布局
     *
     * @return
     */
    public boolean autoUseEmptyView() {
        return false;
    }

    public boolean isRefreshing() {
        return refreshing;
    }


    public boolean isAutoUseEmptyView() {
        return isAutoUseEmptyView;
    }

    public void setAutoUseEmptyView(boolean autoUseEmptyView) {
        isAutoUseEmptyView = autoUseEmptyView;
    }

    public void setEnableRefreshLoadMore(boolean open) {
        mSwipeRefreshLayout.setEnableLoadmore(open);
    }

    public boolean isDownUpddate() {
        return isDownUpddate;
    }

    public void setDownUpddate(boolean downUpddate) {
        isDownUpddate = downUpddate;
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
    public SmartRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    protected abstract AD createAdapter();

    /**
     * 请求后台，获取数据
     *
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
        LogUtil.i(LOG_TAG, "bindData cccc" + " swipelayout is refreshCategoryList:" + mSwipeRefreshLayout.isRefreshing());
        if (!isNeedLazyLoadData() && isNeedAutoRefresh() && !mSwipeRefreshLayout.isRefreshing()) {
            refreshing = true;
            //进入页面默认先刷新一次
            refreshDataFromServer();
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
        return R.layout.base_fragment_smart_refresh_base;
    }


    @Override
    public void lazyLoad() {
        if (!isViewVisible || !isViewPrepared || !isNeedLazyLoadData()
                || (mAdapter != null && mAdapter.getData().size() > 0) ||
                mSwipeRefreshLayout.isRefreshing()) {
            return;
        }
        extraRefreshNetworkRequest();
        refresh();
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
            LogUtil.i(LOG_TAG, "refreshDataFromServer no network" + "  mAdapter.getItemCount()：" + mAdapter.getItemCount());
            if (isAutoUseEmptyView && mEmptyView != null && mAdapter.getItemCount() == 0) {
                mEmptyView.show(EmptyView.STATE_NONET);
            }
        }

    }

    /**
     * 请求接口，刷新数据
     */
    protected void refreshData() {
        pageIndex = 1;
        hideEmptyView();
        LogUtil.i(LOG_TAG, "refreshData ");
        if (AppNetworkUtil.isNetworkAvailable(getContext())) {
            RequestBuilder builder = getRequestBuild(true);
            if (builder != null) {
                handleRequestBuild(builder, getRxRequestEnable());
            }
            extraRefreshNetworkRequest();

        } else {
            stopLoading(false);
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
        LogUtil.i(LOG_TAG, "handleRequestBuild builder:" + builder.toString());
        if (rx)
            builder.rxRequest().
                    subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(baseRxCallback);
        else builder.request(this);
    }

    /**
     * 额外加载数据
     */
    public void extraRefreshNetworkRequest() {

    }

    @CallSuper
    @Override
    protected void bindEvent() {
        //下拉刷新
        mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                isDownUpddate = true;
                LogUtil.i(LOG_TAG, "isDownUpddate:" + isDownUpddate);
                refresh();
            }
        });
        //上拉刷新
        mSwipeRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                isDownUpddate = false;
                loadMore();
            }
        });


    }

    @CallSuper
    public void initRefreshLayout() {
        if (!allowPullToRefresh()) {
            LogUtil.i(LOG_TAG, "allowPullToRefresh:" + false);
            mSwipeRefreshLayout.setEnabled(false);
        }
        mSwipeRefreshLayout.setEnableLoadmore(true);
        mSwipeRefreshLayout.setLoadmoreFinished(false);
        mSwipeRefreshLayout.setEnableLoadmoreWhenContentNotFull(true);
        mSwipeRefreshLayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
        mSwipeRefreshLayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
    }

    @CallSuper
    public void initRecyclerView() {
        mAdapter = createAdapter();
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
     * 单页数据数
     *
     * @return
     */
    protected int getPageSize() {
        return 20;
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
    public SmartRefreshLayout getRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    @NonNull
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }


    /**
     * 请求刷新
     */

    protected void refresh() {
        if (!AppNetworkUtil.isNetworkAvailable(mContext)) {
            if (isAutoUseEmptyView && mEmptyView != null && mAdapter.getItemCount() == 0) {
                mEmptyView.show(EmptyView.STATE_NONET);
            } else {
                mSwipeRefreshLayout.finishRefresh(false);
                mSwipeRefreshLayout.setLoadmoreFinished(false);
                mSwipeRefreshLayout.setEnableLoadmore(true);
            }
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
                mSwipeRefreshLayout.finishLoadmore(false);
                mSwipeRefreshLayout.setLoadmoreFinished(false);
                mSwipeRefreshLayout.setEnableLoadmore(true);
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
        LogUtil.i(LOG_TAG, "loadMoreDataFromServer ");
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
     * 强制刷新,触发RefreshLayout自动刷新
     */
    public void forceRefresh() {
        LogUtil.i(LOG_TAG, "forceRefresh");
        if (mSwipeRefreshLayout != null && !mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setEnableRefresh(true);
            mSwipeRefreshLayout.autoRefresh();
        }
    }

    private BaseRxCallback<String> baseRxCallback = new BaseRxCallback<String>() {
        @Override
        public void onSuccess(String body) {
            handleOnSuccessCallback(body);
            LogUtil.d(LOG_TAG, "baseRxCallback onLoadDataSuccess body:" + body);
            stopLoading(true);
        }

        @Override
        public void onFailed(Throwable e) {
            stopLoading(false);
            showToast(e.getMessage());
            hideLoading();

            LogUtil.d(LOG_TAG, "baseRxCallback onFailed msg:" + e.getMessage());

        }

        @Override
        public void onError(Throwable e) {
            stopLoading(false);
            LogUtil.d(LOG_TAG, "baseRxCallback onError e:" + e.getCause());
            handleOnFailedCallback();

        }
    };


    /**
     * 设置SmartRefresh是否可监听上拉下拉
     *
     * @param enable
     */
    public void enableSmartRefresh(boolean enable) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setEnableRefresh(enable);
            mSwipeRefreshLayout.setEnableLoadmore(enable);
        }
    }

    /**
     * 停止上拉下拉刷新
     */
    public void stopLoading(boolean success) {
        if (mSwipeRefreshLayout != null) {
            if (mSwipeRefreshLayout.isRefreshing()) {
                LogUtil.i(LOG_TAG, "stopLoading isRefreshing");
                if (success) {
                    mSwipeRefreshLayout.finishRefresh(1000);
                } else {
                    mSwipeRefreshLayout.finishRefresh(false);
                }
                mSwipeRefreshLayout.setLoadmoreFinished(false);
                mSwipeRefreshLayout.setEnableLoadmore(true);

            }
            if (mSwipeRefreshLayout.isLoading()) {
                LogUtil.i(LOG_TAG, "stopLoading isLoading");
                if (success) {
                    mSwipeRefreshLayout.finishLoadmore(1000);
                } else {
                    mSwipeRefreshLayout.finishLoadmore(false);
                }
                mSwipeRefreshLayout.finishLoadmore(1000);
                mSwipeRefreshLayout.setLoadmoreFinished(false);
                mSwipeRefreshLayout.setEnableLoadmore(true);
            }
        }
    }

    /**
     * 请求后台数据返回成功处理
     *
     * @param body
     */
    void handleOnSuccessCallback(String body) {
        LogUtil.d(LOG_TAG, " handleOnSuccessCallback  body" + body);
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
                pageIndex++;
                mAdapter.setNewData(data); ///清空
            } else {
                if (data != null && data.size() > 0) {
                    pageIndex++;
                    mAdapter.addData(data);
                } else {
                    mAdapter.loadComplete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        if (!isAutoUseEmptyView) {
            handleEmptyViewDismiss(mAdapter.getItemCount());
        }
        if (isAutoUseEmptyView) {
            LogUtil.i(LOG_TAG, "handleOnSuccessCallback mAdapter.getItemCount:" + mAdapter.getItemCount() + " getdata size:" + mAdapter.getData().size());

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
     * 刷新加载数据完成重置初始值
     */
    public void loadAndRefreshComplete() {
        refreshing = false;
        loading = false;
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            if (mSwipeRefreshLayout != null) {
                enableSmartRefresh(false);
                mSwipeRefreshLayout.destroyDrawingCache();
                mSwipeRefreshLayout.clearAnimation();
            }
        } else {
            if (mSwipeRefreshLayout != null && refreshing) {
                enableSmartRefresh(true);
            }
        }
        super.onHiddenChanged(hidden);
    }

    /**
     * 显示空布局
     *
     * @param state
     */
    public void showEmptyView(@EmptyView.ViewState int state) {
        if (mEmptyView != null) {
            mEmptyView.show(state);
        }
    }

    /**
     * 隐藏空布局
     */
    public void hideEmptyView() {
        if (mEmptyView != null) {
            mEmptyView.dismiss();
        }
    }
}

