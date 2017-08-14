package com.txd.hzj.wjlp.mellOnLine.gridClassify.hous;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
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


public class HousChenAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)//标题
    private TextView titlt_conter_tv;
    @ViewInject(R.id.rv_hous)//房子
    private RecyclerView mRvHous;

    @ViewInject(R.id.hose_be_back_top_iv)
    private ImageView hose_be_back_top_iv;


    /**
     * 热门
     */
    @ViewInject(R.id.hot_goods_tv)
    private TextView hot_goods_tv;
    /**
     * 最新
     */
    @ViewInject(R.id.lastest_goods_tv)
    private TextView lastest_goods_tv;

    @ViewInject(R.id.lastest_goods_time_iv)
    private ImageView lastest_goods_time_iv;

    /**
     * 进度
     */
    @ViewInject(R.id.plan_goods_tv)
    private TextView plan_goods_tv;

    /**
     * 进度升降
     */
    @ViewInject(R.id.plan_goods_time_iv)
    private ImageView plan_goods_time_iv;

    /**
     * 人次
     */
    @ViewInject(R.id.times_tv)
    private TextView times_tv;
    /**
     * 人次升降
     */
    @ViewInject(R.id.times_iv)
    private ImageView times_iv;
    /**
     * 排序种类
     * 0.热度
     * 1.最新
     * 2.进度
     * 3.人次
     */
    private int soft_type = 0;
    /**
     * 进度升降
     * 偶数-->降
     * 奇数-->升
     */
    private int plan_goods_times = 0;
    /**
     * 积分升降
     * 偶数-->降
     * 奇数-->升
     */
    private int lastest_goods_times = 0;
    /**
     * 人次升降
     * 偶数-->降
     * 奇数-->升
     */
    private int times = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 沉浸式解决顶部标题重叠
         */
        showStatusBar(R.id.title_re_layout);

        softStyle(soft_type);
    }

    @Override
    @OnClick({R.id.hose_be_back_top_iv, R.id.hot_goods_tv, R.id.lastest_goods_layout,
            R.id.plan_goods_layout, R.id.times_layout})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.hose_be_back_top_iv:
                mRvHous.scrollToPosition(0);
                break;
            case R.id.hot_goods_tv:// 热门
                soft_type = 0;
                softStyle(soft_type);
                break;
            case R.id.lastest_goods_layout:// 最新
                soft_type = 1;
                softStyle(soft_type);
                break;
            case R.id.plan_goods_layout:// 进度
                soft_type = 2;
                softStyle(soft_type);
                break;
            case R.id.times_layout:// 人次
                soft_type = 3;
                softStyle(soft_type);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_hous_chen;
    }

    @Override
    protected void initialized() {
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
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        mRvHous.setLayoutManager(manager);
        HousChenAdapter housAdapter = new HousChenAdapter(this);
        mRvHous.setAdapter(housAdapter);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position <= 3) {
                    hose_be_back_top_iv.setVisibility(View.GONE);
                } else {
                    hose_be_back_top_iv.setVisibility(View.VISIBLE);
                }
                return 1;
            }
        });
    }

    private void softStyle(int type) {
        hot_goods_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        lastest_goods_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        plan_goods_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        times_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        times_iv.setImageResource(R.mipmap.icon_screen_default_chen);
        plan_goods_time_iv.setImageResource(R.mipmap.icon_screen_default_chen);
        lastest_goods_time_iv.setImageResource(R.mipmap.icon_screen_default_chen);
        if (0 == type) {
            hot_goods_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            times = 0;
            plan_goods_times = 0;
            lastest_goods_times = 0;
        } else if ((1 == type)) {
            lastest_goods_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            times = 0;
            plan_goods_times = 0;
            if (lastest_goods_times % 2 == 0) {
                lastest_goods_time_iv.setImageResource(R.mipmap.icon_screen_down_chen);
            } else {
                lastest_goods_time_iv.setImageResource(R.mipmap.icon_screen_top_chen);
            }
            lastest_goods_times++;
        } else if (2 == type) {
            times = 0;
            lastest_goods_times = 0;
            plan_goods_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));

            if (plan_goods_times % 2 == 0) {
                plan_goods_time_iv.setImageResource(R.mipmap.icon_screen_down_chen);
            } else {
                plan_goods_time_iv.setImageResource(R.mipmap.icon_screen_top_chen);
            }

            plan_goods_times++;

        } else {
            plan_goods_times = 0;
            lastest_goods_times = 0;
            times_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            if (times % 2 == 0) {
                times_iv.setImageResource(R.mipmap.icon_screen_down_chen);
            } else {
                times_iv.setImageResource(R.mipmap.icon_screen_top_chen);
            }
            times++;
        }

    }

}
