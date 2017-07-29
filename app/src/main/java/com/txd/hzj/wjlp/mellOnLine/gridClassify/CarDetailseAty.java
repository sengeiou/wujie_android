package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mellOnLine.adapter.PostAdapter;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;
import com.txd.hzj.wjlp.view.ObservableScrollView;

import java.util.ArrayList;
import java.util.List;

public class CarDetailseAty extends BaseAty implements ObservableScrollView.ScrollViewListener {

    /**
     * 商品布局
     */
    @ViewInject(R.id.title_goods_layout)
    public LinearLayout title_goods_layout;
    /**
     * 商品TextView
     */
    @ViewInject(R.id.title_goods_tv)
    public TextView title_goods_tv;

    /**
     * 商品View
     */
    @ViewInject(R.id.title_goods_view)
    public View title_goods_view;
    /**
     * 详情布局
     */
    @ViewInject(R.id.title_details_layout)
    public LinearLayout title_details_layout;
    /**
     * 详情TextView
     */
    @ViewInject(R.id.title_details_tv)
    public TextView title_details_tv;

    /**
     * 详情View
     */
    @ViewInject(R.id.title_details_view)
    public View title_details_view;
    /**
     * 评价布局
     */
    @ViewInject(R.id.title_evaluate_layout)
    public LinearLayout title_evaluate_layout;
    /**
     * 评价TextView
     */
    @ViewInject(R.id.title_evaluate_tv)
    public TextView title_evaluate_tv;

    /**
     * 评价View
     */
    @ViewInject(R.id.title_evaluate_view)
    public View title_evaluate_view;

    /**
     * 商品轮播
     */
    @ViewInject(R.id.online_carvouse_view)
    private CarouselView online_carvouse_view;
    /**
     * 轮播图图片
     */
    private ArrayList<Integer> image;

    /**
     * 原价
     */
    @ViewInject(R.id.old_price_tv)
    private TextView old_price_tv;

    /**
     * 商品其他信息
     */
    @ViewInject(R.id.goods_other_info_layout)
    private LinearLayout goods_other_info_layout;

    /**
     * 滚动监听的ScrollView
     */
    @ViewInject(R.id.limit_goods_details_sc)
    private ObservableScrollView limit_goods_details_sc;

    /**
     * 回到顶部
     */
    @ViewInject(R.id.be_back_top_iv)
    private ImageView be_back_top_iv;

    /**
     * 轮播的高度
     */
    private int bannerHeight = 0;

    /**
     * 对应top_lin_layout
     */
    private int topHeighe = 0;
    /**
     * 对应 second_lin_layout
     */
    private int secondHeight = 0;

    /**
     * 除去图文详情和店铺信息，评论外的所有空间布局
     */
    @ViewInject(R.id.top_lin_layout)
    private LinearLayout top_lin_layout;

    /**
     * 除去图文详情外的所有控件布局
     */
    @ViewInject(R.id.second_lin_layout)
    private LinearLayout second_lin_layout;


    private int clickType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showStatusBar(R.id.goods_details_title);
        // 设置轮播图高度
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Settings.displayWidth,
                Settings.displayWidth);
        online_carvouse_view.setLayoutParams(layoutParams);
        forBanner();
        // 判断是否显示回到顶部按钮
        getHeight();

    }

    @Override
    @OnClick({R.id.title_goods_layout, R.id.title_details_layout, R.id.title_evaluate_layout,
            R.id.goods_title_collect_layout, R.id.goods_title_share_tv,
            R.id.be_back_top_iv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.title_goods_layout://商品
                clickType = 1;
                limit_goods_details_sc.smoothScrollTo(0, 0);
                break;
            case R.id.title_details_layout://详情
                clickType = 2;
                limit_goods_details_sc.smoothScrollTo(0, secondHeight);
                break;
            case R.id.title_evaluate_layout://评价
                clickType = 3;
                limit_goods_details_sc.smoothScrollTo(0, topHeighe);
                break;
            case R.id.goods_title_collect_layout://收藏
                break;
            case R.id.goods_title_share_tv://分享
                break;
            case R.id.be_back_top_iv:// 回到顶部
                limit_goods_details_sc.smoothScrollTo(0, 0);
                setTextViewAndViewColor(0);
                break;
        }
    }

    private void setTextViewAndViewColor(int next) {
        title_goods_tv.setTextColor(Color.BLACK);
        title_details_tv.setTextColor(Color.BLACK);
        title_evaluate_tv.setTextColor(Color.BLACK);

        title_goods_view.setBackgroundColor(Color.WHITE);
        title_details_view.setBackgroundColor(Color.WHITE);
        title_evaluate_view.setBackgroundColor(Color.WHITE);

        if (1 == clickType) {
            title_goods_tv.setTextColor(ContextCompat.getColor(this, R.color.theme_color));
            title_goods_view.setBackgroundColor(ContextCompat.getColor(this, R.color.theme_color));
            return;
        }
        if (2 == clickType) {
            title_details_tv.setTextColor(ContextCompat.getColor(this, R.color.theme_color));
            title_details_view.setBackgroundColor(ContextCompat.getColor(this, R.color.theme_color));
            return;
        }
        if (3 == clickType) {
            title_evaluate_tv.setTextColor(ContextCompat.getColor(this, R.color.theme_color));
            title_evaluate_view.setBackgroundColor(ContextCompat.getColor(this, R.color.theme_color));
            return;
        }

        if (0 == next) {
            title_goods_tv.setTextColor(ContextCompat.getColor(this, R.color.theme_color));
            title_goods_view.setBackgroundColor(ContextCompat.getColor(this, R.color.theme_color));
        } else if (1 == next) {
            title_details_tv.setTextColor(ContextCompat.getColor(this, R.color.theme_color));
            title_details_view.setBackgroundColor(ContextCompat.getColor(this, R.color.theme_color));
        } else {
            title_evaluate_tv.setTextColor(ContextCompat.getColor(this, R.color.theme_color));
            title_evaluate_view.setBackgroundColor(ContextCompat.getColor(this, R.color.theme_color));
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
        return R.layout.aty_car_detailse_hzj;
    }

    @Override
    protected void initialized() {
        image = new ArrayList<>();
        image.add(R.drawable.icon_temp_goods_banner);
        image.add(R.drawable.icon_temp_goods_banner);
        image.add(R.drawable.icon_temp_goods_banner);
        image.add(R.drawable.icon_temp_goods_banner);
        image.add(R.drawable.icon_temp_goods_banner);
    }

    @Override
    protected void requestData() {

    }

    private void getHeight() {
        ViewTreeObserver vto = online_carvouse_view.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                online_carvouse_view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                bannerHeight = online_carvouse_view.getHeight();
                topHeighe = top_lin_layout.getHeight();
                secondHeight = second_lin_layout.getHeight();
                limit_goods_details_sc.setScrollViewListener(CarDetailseAty.this);
            }
        });
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= 0) {
            be_back_top_iv.setVisibility(View.GONE);
            setTextViewAndViewColor(0);
        } else if (y >= bannerHeight) {
            be_back_top_iv.setVisibility(View.VISIBLE);
            if (y < topHeighe) {// 商品
                setTextViewAndViewColor(0);
            } else if (y >= topHeighe && y < secondHeight) {// 评价
                setTextViewAndViewColor(2);
            } else {// 详情
                setTextViewAndViewColor(1);
            }
        }
        if (oldy > y) {
            clickType = 0;
        }
    }
}
