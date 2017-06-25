package com.pull_more_refresh.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.ListView;

import static com.pull_more_refresh.customview.PullListView.TYPE_MORE.TYPE_NO_MORE;

/**
 * 在下拉刷新的基础上增加上拉加载更多功能
 */
@SuppressWarnings("unused")
public class SuperRefreshLayout extends PullRefreshLayout implements AbsListView.OnScrollListener,
        PullRefreshLayout.OnRefreshListener {
    private PullListView mPullListView;

    private int mTouchSlop;

    private SuperRefreshLayoutListener mListener;


    private boolean mCanLoadMore = true;


    private int mTextColor;
    private int mFooterBackground;

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
        if (isInBottom()/*滚动到最底部*/) {
            if (canLoad() && (mListener != null)) {
                mListener.onLoadMore();
            } else {
                mPullListView.setFooterType(TYPE_NO_MORE);
            }
        }
    }

    /**
     * 下拉刷新回调函数
     */
    @Override
    public void onRefresh() {
        if (mListener != null) {
            mListener.onRefreshing();
        } else {
            super.setRefreshing(false);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        // 初始化ListView对象
        if (mPullListView == null) {
            getListView();
        }
    }

    /**
     * 获取ListView并添加Footer
     */
    private void getListView() {
        int child = getChildCount();
        for (int i = 0; i < child; i++) {
            View childView = getChildAt(i);
            if (childView instanceof ListView) {
                mPullListView = (PullListView) childView;
                // 设置滚动监听器给ListView,
                // 使得滚动的情况下也可以自动加载
                mPullListView.setOnScrollListener(this);
                break;
            }
        }
    }


    public void setCanLoadMore(boolean can) {
        this.mCanLoadMore = can;
    }

    /**
     * 事件分发函数
     * MotionEvent进入SuperRefreshLayout布局中从这里开发分开
     *
     * @param event 分发的事件对象
     * @return true 事件需要向下分发; false 事件不需要分发返回上一次;
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                break;
            }
            case MotionEvent.ACTION_UP: {
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
        return mCanLoadMore;
    }


    /**
     * 判断滑到ListView最底
     */
    private boolean isInBottom() {
        return (mPullListView != null && mPullListView.getAdapter() != null)
                && mPullListView.getLastVisiblePosition() == (mPullListView.getAdapter().getCount() - 1);
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
