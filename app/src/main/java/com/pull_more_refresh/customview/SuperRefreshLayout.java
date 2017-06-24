package com.pull_more_refresh.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * 在下拉刷新的基础上增加上拉加载更多功能
 */
@SuppressWarnings("unused")
public class SuperRefreshLayout extends PullRefreshLayout implements AbsListView.OnScrollListener,
        PullRefreshLayout.OnRefreshListener {
    private ListView mListView;

    private int mTouchSlop;

    private SuperRefreshLayoutListener mListener;

    private boolean mIsOnLoading = false;

    private boolean mCanLoadMore = true;

    private int mYDown;

    private int mLastY;

    private int mTextColor;
    private int mFooterBackground;
    private boolean mIsMoving = false;

    public SuperRefreshLayout(Context context) {
        this(context, null);
    }

    public SuperRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setOnRefreshListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // 滚动到最底部
        if (canLoad() && (mListener != null)) {
            setIsOnLoading(true);
            mListener.onLoadMore();
        } else {

        }
    }

    /**
     * 下拉刷新回调函数
     */
    @Override
    public void onRefresh() {
        if (mListener != null && !mIsOnLoading) {
            mListener.onRefreshing();
        } else {
            onLoadComplete();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        // 初始化ListView对象
        if (mListView == null) {
            getListView();
        }
    }

    /**
     * 获取ListView并添加Footer
     */
    private void getListView() {
        int child = getChildCount();
        if (child > 0) {
            View childView = getChildAt(0);
            if (childView instanceof ListView) {
                mListView = (ListView) childView;
                // 设置滚动监听器给ListView,
                // 使得滚动的情况下也可以自动加载
                mListView.setOnScrollListener(this);
            }
        }
    }


    public void setCanLoadMore(boolean can) {
        this.mCanLoadMore = can;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mYDown = (int) event.getRawY();
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                mIsMoving = true;
                mLastY = (int) event.getRawY();
                break;
            }
            case MotionEvent.ACTION_UP: {
                mIsMoving = false;
                break;
            }
            default: {
                break;
            }
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 判断加载更多, 条件是到了最底部, ListView不在加载中, 且为上拉操作.
     *
     * @return 是否可以加载更多
     */
    private boolean canLoad() {
        return isInBottom() && !mIsOnLoading && isPullUp() && mCanLoadMore;
    }


    /**
     * 是否是上拉操作
     *
     * @return 是否是上拉操作
     */
    private boolean isPullUp() {
        return (mYDown - mLastY) >= mTouchSlop;
    }

    /**
     * 设置正在加载
     *
     * @param loading loading
     */
    public void setIsOnLoading(boolean loading) {
        mIsOnLoading = loading;
        if (!mIsOnLoading) {
            mYDown = 0;
            mLastY = 0;
        }
    }


    /**
     * 判断是否到了最底部
     */
    private boolean isInBottom() {
        return (mListView != null && mListView.getAdapter() != null)
                && mListView.getLastVisiblePosition() == (mListView.getAdapter().getCount() - 1);
    }


    public boolean isMoving() {
        return mIsMoving;
    }

    /**
     * 下拉加载结束记得调用
     */
    public void onLoadComplete() {
        setIsOnLoading(false);
        super.setRefreshing(false);
    }

    /**
     * set
     *
     * @param loadListener loadListener
     */
    public void setSuperRefreshLayoutListener(SuperRefreshLayoutListener loadListener) {
        mListener = loadListener;
    }

    /**
     * 数据加载回调监听
     */
    public interface SuperRefreshLayoutListener {
        /**
         * 下拉刷新
         */
        void onRefreshing();

        /**
         * 上拉加载更多
         */
        void onLoadMore();
    }
}
