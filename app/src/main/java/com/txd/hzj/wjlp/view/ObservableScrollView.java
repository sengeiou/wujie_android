package com.txd.hzj.wjlp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/4 0004
 * 时间：11:46
 * 描述：滚动监听的ScrollView
 */

public class ObservableScrollView extends ScrollView {
    public interface ScrollViewListener {
        void onScrollChanged(ObservableScrollView scrollView, int x, int y,
                             int oldx, int oldy);

    }

    private ScrollViewListener scrollViewListener = null;

    public interface onBottomListener {

        void onBottom();

    }

    /**
     * 加载更多
     */
    public onBottomListener onBottomListener = null;

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    public void setOnBottomListener(onBottomListener onBottomListener) {
        this.onBottomListener = onBottomListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
        if (getHeight() + getScrollY() >= getChildAt(0).getMeasuredHeight()) {
            if (onBottomListener != null) {
                onBottomListener.onBottom();
            }
        }

    }
}
