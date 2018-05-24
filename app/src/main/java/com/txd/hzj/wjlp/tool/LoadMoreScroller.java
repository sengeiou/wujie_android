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

//                作者：AndroidXing
//                链接：https://www.jianshu.com/p/ab597dabf35b
//                來源：简书
//                著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
    }
}
