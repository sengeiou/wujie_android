package com.txd.hzj.wjlp.mellOnLine.gridClassify.hous;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mellOnLine.adapter.HousChenAdapter;

/**
 * ===============Txunda===============
 * 作者：chen
 * 日期：2017/7/8 0007
 * 时间：上午 12:06
 * 描述：房产购(10-1房产购)
 * ===============Txunda===============
 */


public class HousChenAty extends BaseAty{

    @ViewInject(R.id.titlt_conter_tv)//标题
    private TextView titlt_conter_tv;
    @ViewInject(R.id.rv_hous)//房子
    private RecyclerView mRvHous;
    @ViewInject(R.id.nv_hous)//NestedScrollView
    private NestedScrollView mNvHous;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /**
         * 沉浸式解决顶部标题重叠
         */
        showStatusBar(R.id.title_re_layout);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_hous_chen;
    }

    @Override
    protected void initialized() {
        mNvHous.smoothScrollTo(0, 0);// 改变滚动条的位置

    }



    @Override
    protected void requestData() {
        titlt_conter_tv.setText("房产购");


        initRecycler();//RecyclerView初始化
    }

    /**
     * RecyclerView初始化
     */
    private void initRecycler() {
        GridLayoutManager manager=new GridLayoutManager(this,2, LinearLayoutManager.VERTICAL, true);
        mRvHous.setLayoutManager(manager);
        HousChenAdapter housAdapter=new HousChenAdapter(this);
        mRvHous.setAdapter(housAdapter);
        mRvHous.setNestedScrollingEnabled(false);//RecycleView固定大小会实现优化
        /**
         * 注：如果不加如下代码则滑动时“不顺畅”、“卡顿”、“粘滞”
         *
         * RecyclerVirew滑动时NesterScrollView不允许滑动
         */
        mRvHous.setHasFixedSize(true);


    }

}
