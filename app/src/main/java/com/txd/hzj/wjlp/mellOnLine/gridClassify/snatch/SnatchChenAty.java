package com.txd.hzj.wjlp.mellOnLine.gridClassify.snatch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.ListUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ScrollDirectionListener;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.OneBuyBean;
import com.txd.hzj.wjlp.bean.OneBuyListBean;
import com.txd.hzj.wjlp.http.onebuy.OneBuyPst;
import com.txd.hzj.wjlp.mainFgt.adapter.HorizontalAdapter;
import com.txd.hzj.wjlp.mellOnLine.adapter.GoodsForRvAdapter;
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
    private List<OneBuyBean.Data.Banner> image;
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
    private List<OneBuyBean.Data.NewsBean> data;
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

    /**
     * 列表
     */
    @ViewInject(R.id.good_luck_rv)
    private RecyclerView good_luck_rv;

    private List<OneBuyListBean> dataForRv;
    private List<OneBuyListBean> dataForRv2;

    private GoodsForRvAdapter goodsByOrderAdapter;

    private int height = 0;

    /**
     * 空视图
     */
    @ViewInject(R.id.no_data_iv)
    private ImageView no_data_iv;

    @ViewInject(R.id.be_back_top_fab)
    private FloatingActionButton be_back_top_fab;

    @ViewInject(R.id.good_luck_sr)
    private SuperSwipeRefreshLayout good_luck_sr;
    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;

    /**
     * 分页
     */
    private int p = 1;

    /**
     * 是不是第一次进入
     */
    private boolean frist = true;

    private OneBuyPst oneBuyPst;
    /**
     * 热门
     */
    private String is_hot = "1";
    /**
     * 进度排序 1正序 2倒序
     */
    private String add_num = "";
    /**
     * 人气排序 1正序 2倒序
     */
    private String person_num = "";
    /**
     * 积分排序 1正序 2倒序
     */
    private String integral = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("积分夺宝");
        // 轮播图高度
        allHeight = Settings.displayWidth / 2;
        // 设置轮播图高度
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Settings.displayWidth, allHeight);
        online_carvouse_view.setLayoutParams(layoutParams);
        softStyle(soft_type);

        // 商品列表
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        good_luck_rv.setLayoutManager(manager);
        good_luck_rv.setItemAnimator(new DefaultItemAnimator());
        good_luck_rv.setHasFixedSize(true);
        good_luck_rv.addItemDecoration(new GridDividerItemDecoration(height, ContextCompat.getColor(this,
                R.color.bg_color)));


        be_back_top_fab.attachToRecyclerView(good_luck_rv, new ScrollDirectionListener() {
            @Override
            public void onScrollDown() {
                be_back_top_fab.hide();
            }

            @Override
            public void onScrollUp() {
                be_back_top_fab.show();
            }
        }, new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = good_luck_rv.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    int firstVisibleItemPosition = linearManager.findFirstVisibleItemPosition();
                    if (firstVisibleItemPosition > 5) {
                        be_back_top_fab.show();
                    } else {
                        be_back_top_fab.hide();
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });


        good_luck_sr.setHeaderViewBackgroundColor(0xff888888);
        good_luck_sr.setHeaderView(createHeaderView());// add headerView
        good_luck_sr.setFooterView(createFooterView());
        good_luck_sr.setTargetScrollWithLayout(true);

        good_luck_sr
                .setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {

                    @Override
                    public void onRefresh() {
                        frist = false;
                        textView.setText("正在刷新");
                        imageView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        p = 1;
                        oneBuyPst.oneBuyIndex(p, add_num, person_num, integral, is_hot);
                    }

                    @Override
                    public void onPullDistance(int distance) {
                    }

                    @Override
                    public void onPullEnable(boolean enable) {
                        textView.setText(enable ? "松开刷新" : "下拉刷新");
                        imageView.setVisibility(View.VISIBLE);
                        imageView.setRotation(enable ? 180 : 0);
                    }
                });

        good_luck_sr
                .setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {

                    @Override
                    public void onLoadMore() {
                        frist = false;
                        footerTextView.setText("正在加载...");
                        footerImageView.setVisibility(View.GONE);
                        footerProgressBar.setVisibility(View.VISIBLE);

                        p++;
                        oneBuyPst.oneBuyIndex(p, add_num, person_num, integral, is_hot);
                    }

                    @Override
                    public void onPushEnable(boolean enable) {
                        footerTextView.setText(enable ? "松开加载" : "上拉加载");
                        footerImageView.setVisibility(View.VISIBLE);
                        footerImageView.setRotation(enable ? 0 : 180);
                    }

                    @Override
                    public void onPushDistance(int distance) {

                    }

                });
    }


    @Override
    @OnClick({R.id.hot_goods_tv, R.id.lastest_goods_layout, R.id.plan_goods_layout, R.id.times_layout,
            R.id.be_back_top_fab})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
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
            case R.id.be_back_top_fab:// 回到顶部
                good_luck_rv.smoothScrollToPosition(0);
                break;
        }
    }

    /**
     * 轮播图
     */
    private void forBanner() {
        online_carvouse_view.setImageListener(imageListener);
        online_carvouse_view.setPageCount(image.size());
    }

    /**
     * 轮播图的点击事件
     */
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(final int position, ImageView imageView) {
            Glide.with(SnatchChenAty.this).load(image.get(position).getPicture())
                    .override(Settings.displayWidth, allHeight)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imageView);
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
        data = new ArrayList<>();
        views = new ArrayList<>();
        dataForRv = new ArrayList<>();
        dataForRv2 = new ArrayList<>();

        height = ToolKit.dip2px(this, 4);
        oneBuyPst = new OneBuyPst(this);
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
            tv1.setText(data.get(i).getTitle().toString());
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
        lastest_goods_time_iv.setImageResource(R.mipmap.icon_screen_default_chen);
        add_num = "";
        person_num = "";
        integral = "";
        is_hot = "";
        if (0 == type) {// 热门
            hot_goods_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            times = 0;
            plan_goods_times = 0;
            lastest_goods_times = 0;
            is_hot = "1";
        } else if ((1 == type)) {// 积分
            lastest_goods_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            times = 0;
            plan_goods_times = 0;
            if (lastest_goods_times % 2 == 0) {
                integral = "1";
                lastest_goods_time_iv.setImageResource(R.mipmap.icon_screen_down_chen);
            } else {
                integral = "2";
                lastest_goods_time_iv.setImageResource(R.mipmap.icon_screen_top_chen);
            }
            lastest_goods_times++;
        } else if (2 == type) {// 进度
            times = 0;
            lastest_goods_times = 0;
            plan_goods_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));

            if (plan_goods_times % 2 == 0) {
                add_num = "1";
                plan_goods_time_iv.setImageResource(R.mipmap.icon_screen_down_chen);
            } else {
                add_num = "2";
                plan_goods_time_iv.setImageResource(R.mipmap.icon_screen_top_chen);
            }

            plan_goods_times++;

        } else {// 人气
            plan_goods_times = 0;
            lastest_goods_times = 0;
            times_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            if (times % 2 == 0) {
                person_num = "1";
                times_iv.setImageResource(R.mipmap.icon_screen_down_chen);
            } else {
                person_num = "2";
                times_iv.setImageResource(R.mipmap.icon_screen_top_chen);
            }
            times++;
        }
        oneBuyPst.oneBuyIndex(p, add_num, person_num, integral, is_hot);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("oneBuyIndex")) {
            OneBuyBean oneBuyBean = GsonUtil.GsonToBean(jsonStr, OneBuyBean.class);
            L.e("=====原始数据=====", jsonStr);
            L.e("=====解析数据=====", oneBuyBean.toString());
            if (1 == p) {
                // 轮播图
                image = oneBuyBean.getData().getAds();
                if (!ListUtils.isEmpty(image)) {
                    forBanner();
                }
                // 公告
                data = oneBuyBean.getData().getNews();
                if (!ListUtils.isEmpty(data)) {
                    setView();
                    upview1.setViews(views);
                }
                // 商品
                dataForRv = oneBuyBean.getData().getOneBuyList();
                if (!ListUtils.isEmpty(dataForRv)) {
                    goodsByOrderAdapter = new GoodsForRvAdapter(this, dataForRv, 5);
                    good_luck_rv.setAdapter(goodsByOrderAdapter);
                } else {
                    good_luck_rv.setVisibility(View.GONE);
                    no_data_iv.setVisibility(View.VISIBLE);
                }
                if (!frist) {
                    good_luck_sr.setRefreshing(false);
                    progressBar.setVisibility(View.GONE);
                }
                goodsByOrderAdapter.setListener(new HorizontalAdapter.OnItemClickLitener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Bundle bundle = new Bundle();
                        bundle.putString("one_buy_id",dataForRv.get(position).getOne_buy_id());
                        startActivity(SnatchGoodsDetailsAty.class, bundle);
                    }
                });
            } else {
                // 商品
                dataForRv2 = oneBuyBean.getData().getOneBuyList();
                if (!ListUtils.isEmpty(dataForRv2)) {
                    dataForRv.removeAll(dataForRv2);
                    goodsByOrderAdapter.notifyDataSetChanged();
                }
                footerImageView.setVisibility(View.VISIBLE);
                footerProgressBar.setVisibility(View.GONE);
                good_luck_sr.setLoadMore(false);
            }
        }
    }

    private View createFooterView() {
        View footerView = LayoutInflater.from(this).inflate(R.layout.layout_footer, null);
        footerProgressBar = footerView
                .findViewById(R.id.footer_pb_view);
        footerImageView = footerView
                .findViewById(R.id.footer_image_view);
        footerTextView = footerView
                .findViewById(R.id.footer_text_view);
        footerProgressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.VISIBLE);
        footerImageView.setImageResource(R.drawable.down_arrow);
        footerTextView.setText("上拉加载更多...");
        return footerView;
    }

    private View createHeaderView() {
        View headerView = LayoutInflater.from(this).inflate(R.layout.layout_head, null);
        progressBar = headerView.findViewById(R.id.pb_view);
        textView = headerView.findViewById(R.id.text_view);
        textView.setText("下拉刷新");
        imageView = headerView.findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.down_arrow);
        progressBar.setVisibility(View.GONE);
        return headerView;
    }
}
