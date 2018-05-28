package com.txd.hzj.wjlp.tool;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import com.ants.theantsgo.util.L;
import com.bumptech.glide.Glide;
import com.txd.hzj.wjlp.mainFgt.adapter.RacycleAllAdapter;

/**
 * 创建者：TJDragon（Liugang）
 * 创建时间：2018/5/27 0:55
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class TouchStopListener4ScrollView implements View.OnTouchListener {
    Context context;
    private LoadListener listener;

    public interface LoadListener {
        public void setCanLoadImg(boolean flag);
    }

    public TouchStopListener4ScrollView(Context context, LoadListener listener) {
        this.context = context;
        this.listener = listener;
    }

    private int lastY = 0;
    private int touchEventId = -9983761;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            View scroller = (View) msg.obj;
            if (msg.what == touchEventId) {
                if (lastY == scroller.getScrollY()) {
                    handleStop(scroller);
                } else {
                    handler.sendMessageDelayed(handler.obtainMessage(touchEventId, scroller), 5);
                    lastY = scroller.getScrollY();
                }
            }
        }
    };


    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            handler.sendMessageDelayed(handler.obtainMessage(touchEventId, v), 5);
        } else {
            if (null != listener) {
                listener.setCanLoadImg(false);
            }
            Glide.with(context).pauseRequests();
        }
        return false;
    }


    private void handleStop(Object view) {
//        NestedScrollView scroller = (NestedScrollView) view;
        if (L.isDebug)
            Toast.makeText(context, "手指停止滑动", Toast.LENGTH_SHORT).show();
        Glide.with(context).resumeRequests();
        if (null != listener)
            listener.setCanLoadImg(true);
//            scrollY = scroller.getScrollY();
    }
}
