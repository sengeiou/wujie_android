package com.txd.hzj.wjlp.mellOnLine.gridClassify.snatch;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.ListUtils;
import com.ants.theantsgo.view.banner.BannerAdapter;
import com.ants.theantsgo.view.banner.DotView;
import com.ants.theantsgo.view.banner.SliderBanner;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mainFgt.adapter.HorizontalAdapter;
import com.txd.hzj.wjlp.mellOnLine.adapter.GoodsForRvAdapter;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.fgt.HousDetailsHousesChenFgt;
import com.txd.hzj.wjlp.shoppingCart.adapter.GoodsByOrderAdapter;
import com.txd.hzj.wjlp.tool.GridDividerItemDecoration;
import com.txd.hzj.wjlp.view.UPMarqueeView;

import java.util.ArrayList;
import java.util.List;


/**
 * ===============Txunda===============
 * 作者：chen
 * 日期：2017/7/13 0007
 * 时间：下午17:10
 * 描述： 一元夺宝(11-1房产购)
 * ===============Txunda===============
 */
public class SnatchChenAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)//标题
    private TextView titlt_conter_tv;

    /**
     * 轮播图
     */
    @ViewInject(R.id.online_carvouse_view)
    private CarouselView online_carvouse_view;
    /**
     * 轮播图图片
     */
    private ArrayList<Integer> image;
    /**
     * 轮播图
     */
    private int allHeight = 0;

    // ==========头条==========
    /**
     * 今日头条
     */
    @ViewInject(R.id.upview1)
    private UPMarqueeView upview1;

    /**
     * 误解头条数据
     */
    private List<String> data;
    /**
     * 无界头条View
     */
    private List<View> views;

    //==========热门，最新，进度，人次================
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
     * 人次升降
     * 偶数-->降
     * 奇数-->升
     */
    private int times = 0;

    /**
     * 列表
     */
    @ViewInject(R.id.good_luck_rv)
    private RecyclerView good_luck_rv;

    private List<String> dataForRv;

    private GoodsForRvAdapter goodsByOrderAdapter;

    @ViewInject(R.id.snatch_be_back_top_iv)
    private ImageView snatch_be_back_top_iv;

    private int height = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("一元夺宝");
        // 轮播图高度
        allHeight = Settings.displayWidth / 2;
        // 设置轮播图高度
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Settings.displayWidth, allHeight);
        online_carvouse_view.setLayoutParams(layoutParams);
        forBanner();
        softStyle(soft_type);
        setView();
        upview1.setViews(views);

        GridLayoutManager manager = new GridLayoutManager(this, 2);
        good_luck_rv.setLayoutManager(manager);
        good_luck_rv.setItemAnimator(new DefaultItemAnimator());
        good_luck_rv.setHasFixedSize(true);
        good_luck_rv.addItemDecoration(new GridDividerItemDecoration(height, ContextCompat.getColor(this,
                R.color.bg_color)));
        good_luck_rv.setAdapter(goodsByOrderAdapter);

        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position <= 3) {
                    snatch_be_back_top_iv.setVisibility(View.GONE);
                } else {
                    snatch_be_back_top_iv.setVisibility(View.VISIBLE);
                }
                return 1;
            }
        });

        goodsByOrderAdapter.setListener(new HorizontalAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(SnatchGoodsDetailsAty.class, null);
            }
        });
    }


    @Override
    @OnClick({R.id.hot_goods_tv, R.id.lastest_goods_tv, R.id.plan_goods_layout, R.id.times_layout, R.id
            .snatch_be_back_top_iv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.hot_goods_tv:// 热门
                soft_type = 0;
                softStyle(soft_type);
                break;
            case R.id.lastest_goods_tv:// 最新
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
            case R.id.snatch_be_back_top_iv:// 回到顶部
                good_luck_rv.smoothScrollToPosition(0);
                break;
        }
    }

    /**
     * 轮播图
     */
    private void forBanner() {
        online_carvouse_view.setPageCount(image.size());
        online_carvouse_view.setImageListener(imageListener);
    }

    /**
     * 轮播图的点击事件
     */
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(final int position, ImageView imageView) {
            imageView.setImageResource(image.get(position));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    };

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_snatch_chen;
    }

    @Override
    protected void initialized() {
        image = new ArrayList<>();
        image.add(R.drawable.icon_temp_banner);
        image.add(R.drawable.icon_temp_banner);
        image.add(R.drawable.icon_temp_banner);
        data = new ArrayList<>();
        views = new ArrayList<>();
        data.add("家人给2岁孩子喝这个，孩子智力倒退10岁!!!");
        data.add("iPhone8最感人变化成真，必须买买买买!!!!");
        data.add("简直是白菜价！日本玩家33万甩卖15万张游戏王卡");
        data.add("iPhone7价格曝光了！看完感觉我的腰子有点疼...");
        data.add("主人内疚逃命时没带够，回废墟狂挖30小时！");
        dataForRv = new ArrayList<>();
        goodsByOrderAdapter = new GoodsForRvAdapter(this, dataForRv, 5);
        height = ToolKit.dip2px(this, 4);
    }

    @Override
    protected void requestData() {

    }

    /**
     * 初始化需要循环的View
     * 为了灵活的使用滚动的View，所以把滚动的内容让用户自定义
     * 假如滚动的是三条或者一条，或者是其他，只需要把对应的布局，和这个方法稍微改改就可以了，
     */
    private void setView() {
        for (int i = 0; i < data.size(); i = i + 2) {
            //设置滚动的单个布局
            LinearLayout moreView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_view, null);
            //初始化布局的控件
            TextView tv1 = moreView.findViewById(R.id.tv1);
            //进行对控件赋值
            tv1.setText(data.get(i).toString());
            //添加到循环滚动数组里面去
            views.add(moreView);
        }
    }

    private void softStyle(int type) {
        hot_goods_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        lastest_goods_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        plan_goods_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        times_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        times_iv.setImageResource(R.mipmap.icon_screen_default_chen);
        plan_goods_time_iv.setImageResource(R.mipmap.icon_screen_default_chen);
        if (0 == type) {
            hot_goods_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            times = 0;
            plan_goods_times = 0;
        } else if ((1 == type)) {
            lastest_goods_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            times = 0;
            plan_goods_times = 0;
        } else if (2 == type) {
            times = 0;
            plan_goods_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));

            if (plan_goods_times % 2 == 0) {
                plan_goods_time_iv.setImageResource(R.mipmap.icon_screen_down_chen);
            } else {
                plan_goods_time_iv.setImageResource(R.mipmap.icon_screen_top_chen);
            }

            plan_goods_times++;

        } else {
            plan_goods_times = 0;
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
