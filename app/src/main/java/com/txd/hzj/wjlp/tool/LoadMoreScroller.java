package com.txd.hzj.wjlp.tool;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/24 14:26
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class LoadMoreScroller extends RecyclerView.OnScrollListener {
    private Context context;

    public LoadMoreScroller(Context context) {
        this.context = context;
    }

    boolean sIsScrolling = false;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        if (newState == RecyclerView.SCROLL_STATE_DRAGGING || newState == RecyclerView.SCROLL_STATE_SETTLING) {
            sIsScrolling = true;
            Glide.with(context).pauseRequests();
        } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            if (sIsScrolling == true) {
                Glide.with(context).resumeRequests();
            }
            sIsScrolling = false;
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
    }
}
