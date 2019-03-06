package com.fivefivelike.mybaselibrary.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.R;
import com.fivefivelike.mybaselibrary.utils.paginate.LoadingListItemCreator;
import com.fivefivelike.mybaselibrary.utils.paginate.LoadingListItemSpanLookup;
import com.fivefivelike.mybaselibrary.utils.paginate.Paginate;
import com.fivefivelike.mybaselibrary.utils.paginate.ViewHolder;
import com.fivefivelike.mybaselibrary.view.LoadMoreListView;
import com.fivefivelike.mybaselibrary.view.ProgressView;

import java.util.List;

/**
 * Created by liugongce on 2017/7/10.
 */

public abstract class BasePullDelegate extends BaseDelegate {
    // 分页页数
    public int page;
    //分页长度
    public int pagesize = 20;
    private LoadMode mMode = LoadMode.REFRESH;
    private boolean mIsLoadMore = true;
    /**
     * 下拉刷新控件
     */
    private SwipeRefreshLayout mWwipeRefreshLayout;
    private LoadMoreListView mPullListView;
    private RecyclerView mPullRecyclerView;
    /**
     * 用链式达到一句话设置{@link #mPullRecyclerView}的加载操作
     */
    private Paginate mPaginate;
    /**
     * 当{@link #mPullRecyclerView}设置 GridLayoutManager用到
     */
    private int SPAN_SIZE = 0;
    /**
     * {@link #mPullRecyclerView}的上拉显示布局
     */
    private View mFootView;
    /**
     * 用来判断{@link #mPullRecyclerView}加载状态 true是加载中,fasle是不在加载中
     */
    private boolean isLoading;
    /**
     * 用来判断{@link #mPullRecyclerView}中的是不是已经加载了所有的数据
     */
    private boolean isFinish;

    /**
     * 初始化使用LoadMoreListView的上拉页面
     *
     * @param adapter listView的adapter
     */
    public void initListViewPull(BaseAdapter adapter, LoadMoreListView.Callback callback, SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        mWwipeRefreshLayout = getViewById(R.id.swipeRefreshLayout);
        mPullListView = getViewById(R.id.pull_listView);
        mPullListView.setPagesize(pagesize);
        mPullListView.setAdapter(adapter);
        if (mIsLoadMore) {
            mPullListView.setCallback(callback);
        }
        mWwipeRefreshLayout.setOnRefreshListener(onRefreshListener);
    }

    public void setColorSchemeResources(int... colorResIds) {
        if (mWwipeRefreshLayout != null) {
            mWwipeRefreshLayout.setColorSchemeResources(colorResIds);
        }
    }

    boolean isShowNoData = true;

    public boolean isShowNoData() {
        return isShowNoData;
    }

    public void setShowNoData(boolean showNoData) {
        isShowNoData = showNoData;
    }

    /**
     * 初始化使用RecyclerView的上拉页面
     *
     * @param adapter RecyclerView 的adapter
     * @param manager RecyclerView的显示方式
     */
    public void initRecycleviewPull(RecyclerView.Adapter adapter, RecyclerView.LayoutManager manager, final LoadMoreListView.Callback callback, SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        mWwipeRefreshLayout = getViewById(R.id.swipeRefreshLayout);
        mPullRecyclerView = getViewById(R.id.pull_recycleview);
        mPullRecyclerView.setLayoutManager(manager);
        mPullRecyclerView.setAdapter(adapter);
        if (mIsLoadMore) {
            mPaginate = Paginate.with(mPullRecyclerView, new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    isLoading = true;
                    callback.loadData();
                }

                @Override
                public boolean isLoading() {
                    return isLoading;
                }

                @Override
                public boolean hasLoadedAllItems() {
                    return isFinish;
                }
            }).setLoadingTriggerThreshold(2)
                    .addLoadingListItem(true)
                    .setLoadingListItemCreator(new LoadingListItemCreator() {
                        @Override
                        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                            mFootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_layout, parent, false);//初始化尾布局
                            ProgressView loadingView = new ProgressView(parent.getContext());//尾部加载中状态
                            loadingView.setIndicatorId(ProgressView.BallPulse);
                            loadingView.setIndicatorColor(0xff69b3e0);
                            TextView endView = new TextView(parent.getContext());//所有数据加载完布局
                            endView.setGravity(Gravity.CENTER);
                            endView.setText("已经到底啦~");
                            LinearLayout loadLayout = (LinearLayout) mFootView.findViewById(R.id.loading_view_layout);
                            LinearLayout endLayout = (LinearLayout) mFootView.findViewById(R.id.end_layout);
                            loadLayout.addView(loadingView);
                            endLayout.addView(endView);
                            return new ViewHolder(mFootView);
                        }

                        @Override
                        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                        }
                    })
                    .setLoadingListItemSpanSizeLookup(new LoadingListItemSpanLookup() {
                        @Override
                        public int getSpanSize() {
                            return SPAN_SIZE;
                        }
                    })
                    .setPagesize(pagesize)
                    .build();
        }
        mWwipeRefreshLayout.setOnRefreshListener(onRefreshListener);
    }


    /**
     * 数据请求回来调用
     *
     * @param list
     */
    protected void requestBack(List list) {
        mWwipeRefreshLayout.setRefreshing(false);
        hideNoData();
        switch (mMode) {
            case REFRESH://下拉
                if (list != null) {
                    list.clear();
                }
                break;
            case DOWN://上拉
                break;
        }
        if (mPullListView != null) {
            mPullListView.loadMoreComplete();
        } else if (mPullRecyclerView != null) {
            isFinish = false;
            isLoading = false;
            if (mFootView != null) {
                mFootView.findViewById(R.id.loading_view_layout).setVisibility(View.VISIBLE);
                mFootView.findViewById(R.id.end_layout).setVisibility(View.GONE);
            }
        }
    }

    public void loadFinish() {
        switch (mMode) {
            case REFRESH://下拉
                showNoData();
                break;
            case DOWN://上拉
                if (mPullListView != null) {
                    mPullListView.loadMoreEnd();
                } else if (mPullRecyclerView != null) {
                    isFinish = true;
                    isLoading = false;
                    if (mFootView != null) {
                        mFootView.findViewById(R.id.loading_view_layout).setVisibility(View.GONE);
                        mFootView.findViewById(R.id.end_layout).setVisibility(View.VISIBLE);
                    }
                }
                break;
        }
    }

    public void hideNoData() {
        getViewById(R.id.no_data).setVisibility(View.GONE);
    }

    public void showNoData() {
        if (isShowNoData) {
            getViewById(R.id.no_data).setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置是否上拉加载
     *
     * @param isLoadMore
     */
    public void setIsLoadMore(boolean isLoadMore) {
        this.mIsLoadMore = isLoadMore;
    }

    /**
     * 设置是否下拉刷新
     *
     * @param isPullDown
     */
    public void setIsPullDown(boolean isPullDown) {
        if (mWwipeRefreshLayout != null) {
            mWwipeRefreshLayout.setEnabled(isPullDown);
        }
    }

    /**
     * 请求数据
     *
     * @param loadMode 类型
     */
    public void requestData(LoadMode loadMode) {
        switch (loadMode) {
            case REFRESH://下拉刷新
                mMode = LoadMode.REFRESH;
                page = 1;
                break;
            case DOWN://上拉加载
                mMode = LoadMode.DOWN;
                page++;
                break;
        }
    }

    public enum LoadMode {
        /**
         * 下拉刷新
         */
        REFRESH,
        /**
         * 上拉加载
         */
        DOWN
    }

    public void stopRefresh() {
        if (mWwipeRefreshLayout != null) {
            mWwipeRefreshLayout.setRefreshing(false);
        }
    }

    public SwipeRefreshLayout getWwipeRefreshLayout() {
        return mWwipeRefreshLayout;
    }

    public LoadMoreListView getPullListView() {
        return mPullListView;
    }

    public RecyclerView getPullRecyclerView() {
        return mPullRecyclerView;
    }

}
