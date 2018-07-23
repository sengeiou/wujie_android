package com.txd.hzj.wjlp.mellOffLine;


import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ants.theantsgo.base.BaseActivity;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.tool.GlideImageLoader;
import com.txd.hzj.wjlp.view.BannerPointLayout;
import com.txd.hzj.wjlp.view.VpSwipeRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.Map;


/**
 * 创建者：Qyl
 * 创建时间：2018/7/23 0023 14:18
 * 功能描述：
 * 联系方式：无
 */
public class ShopMallDetailsAty extends BaseActivity {
    /**
     * 刷新加载控件
     */
    private VpSwipeRefreshLayout mallRefrsh;
    /**
     * 轮播图控件
     */
    private Banner bannerIma;
    /**
     * 轮播图指示器控件
     */
    private BannerPointLayout pointBanner;
    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;
    private ArrayList urls;


    @Override
    protected int getLayoutResId() {
        return R.layout.aty_down_shopmall_details;
    }

    @Override
    protected void initialized() {
        urls = new ArrayList();
        urls.add("http://seopic.699pic.com/photo/00005/5186.jpg_wh1200.jpg");
        urls.add("http://seopic.699pic.com/photo/50010/0719.jpg_wh1200.jpg");
        urls.add("http://seopic.699pic.com/photo/50009/9449.jpg_wh1200.jpg");
        urls.add("http://seopic.699pic.com/photo/50002/5923.jpg_wh1200.jpg");
        urls.add("http://seopic.699pic.com/photo/50001/9330.jpg_wh1200.jpg");
        urls.add("http://seopic.699pic.com/photo/50009/9191.jpg_wh1200.jpg");
        mallRefrsh = findViewById(R.id.shop_mall_refrsh);
        bannerIma = findViewById(R.id.banner);
        pointBanner = findViewById(R.id.bannerPointLayout);
        //设置图片加载器
        bannerIma.setImageLoader(new GlideImageLoader());
        //设置图片集合
        bannerIma.setImages(urls);
        //设置样式
        bannerIma.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置动画
        bannerIma.setBannerAnimation(Transformer.DepthPage);
        //设置轮播时间
        bannerIma.setDelayTime(2000);
        //设置指示器
       // bannerIma.setIndicatorGravity(BannerConfig.CENTER);
        //开始
        bannerIma.start();
        pointBanner.setPointNum(urls.size());
        bannerIma.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               // pointBanner.toPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //设置下拉刷新上拉加载
        mallRefrsh.setHeaderViewBackgroundColor(Color.WHITE);
        mallRefrsh.setHeaderView(createHeaderView());// add headerView
        mallRefrsh.setFooterView(createFooterView());
        mallRefrsh.setTargetScrollWithLayout(true); // 跟随手指滑动
        mallRefrsh.setOnPullRefreshListener(new VpSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                textView.setText("正在刷新");
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPullDistance(int i) {

            }

            @Override
            public void onPullEnable(boolean enable) {
                textView.setText(enable ? "松开刷新" : "下拉刷新");
                imageView.setVisibility(View.VISIBLE);
                imageView.setRotation(enable ? 180 : 0);
            }
        });
        mallRefrsh.setOnPushLoadMoreListener(new VpSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                footerTextView.setText("正在加载...");
                footerImageView.setVisibility(View.GONE);
                footerProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPushDistance(int i) {

            }

            @Override
            public void onPushEnable(boolean enable) {
                footerTextView.setText(enable ? "松开加载" : "上拉加载");
                footerImageView.setVisibility(View.VISIBLE);
                footerImageView.setRotation(enable ? 0 : 180);
            }
        });

    }

    @Override
    protected void requestData() {

    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
    }


    private View createFooterView() {
        View footerView = LayoutInflater.from(mallRefrsh.getContext()).inflate(R.layout.layout_footer, null);
        footerProgressBar = footerView.findViewById(R.id.footer_pb_view);
        footerImageView = footerView.findViewById(R.id.footer_image_view);
        footerTextView = footerView.findViewById(R.id.footer_text_view);
        footerProgressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.VISIBLE);
        footerImageView.setImageResource(R.drawable.down_arrow);
        footerTextView.setText("上拉加载更多...");
        return footerView;
    }

    private View createHeaderView() {
        View headerView = LayoutInflater.from(mallRefrsh.getContext()).inflate(R.layout.layout_head, null);
        progressBar = headerView.findViewById(R.id.pb_view);
        textView = headerView.findViewById(R.id.text_view);
        textView.setText("下拉刷新");
        imageView = headerView.findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.down_arrow);
        progressBar.setVisibility(View.GONE);
        return headerView;
    }

    @Override
    protected void onStart() {
        super.onStart();
        bannerIma.startAutoPlay();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (bannerIma != null) {
            bannerIma.stopAutoPlay();
        }
    }
}
