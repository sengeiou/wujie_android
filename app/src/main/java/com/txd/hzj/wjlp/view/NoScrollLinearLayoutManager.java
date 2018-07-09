package com.txd.hzj.wjlp.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/6/29 21:43
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class NoScrollLinearLayoutManager extends LinearLayoutManager{
    public NoScrollLinearLayoutManager(Context context) {
        super(context);
    }

    public NoScrollLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public NoScrollLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override

    public boolean canScrollVertically() {


        return false;

    }

    @Override
    public boolean canScrollHorizontally() {
        return false;
    }
};








