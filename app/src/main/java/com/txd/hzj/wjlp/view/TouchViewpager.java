package com.txd.hzj.wjlp.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * 创建者：Qyl
 * 创建时间：2018/5/8 0008 9:56
 * 功能描述：
 * 联系方式：无
 */
public class TouchViewpager extends ViewPager {

    private float startX;
    private float startY;

    public TouchViewpager(Context context) {
        super(context);
    }

    public TouchViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //      //用getParent去请求，希望父控件不拦截这个OnToch事件
//        getParent().requestDisallowInterceptTouchEvent(true);

        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                //用getParent去请求，希望父控件不拦截这个OnToch事件
                //这样为了保证ACTION_MOVE调用
                getParent().requestDisallowInterceptTouchEvent(true);

                startX = (int) ev.getRawX();
                startY = (int) ev.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                int endX = (int) ev.getRawX();
                int endY = (int) ev.getRawY();

                if (Math.abs(endX - startX) > Math.abs(endY - startY)) {//左右滑动

                    if (endX > startX) {//右滑

                        if (getCurrentItem() == 0) {//第一个页面，需要父控件拦截
                            getParent().requestDisallowInterceptTouchEvent(true);
                        }

                    } else {//左滑

                        if (getCurrentItem() == getAdapter().getCount() - 1) {//最后一个页面，需要拦截
                            getParent().requestDisallowInterceptTouchEvent(true);
                        }

                    }

                } else {//上下滑动，需要父控件拦截
                    getParent().requestDisallowInterceptTouchEvent(false);
                }

                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
