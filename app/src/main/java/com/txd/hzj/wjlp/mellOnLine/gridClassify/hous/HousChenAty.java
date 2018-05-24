package com.txd.hzj.wjlp.mellOnLine.gridClassify.hous;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ScrollDirectionListener;
import com.txd.hzj.wjlp.DemoApplication;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.house.HouseBuyPst;
import com.txd.hzj.wjlp.mellOnLine.NoticeDetailsAty;
import com.txd.hzj.wjlp.mellOnLine.adapter.HousChenAdapter;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.MellInfoAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.TicketGoodsDetialsAty;
import com.txd.hzj.wjlp.tool.GridDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    private HouseBuyPst houseBuyPst;
    private String lng = "";
    private String lat = "";
    private String integral_sort = "";
    private String distance_sort = "";
    private String price_sort = "";
    private String sort = "1";

    @ViewInject(R.id.huose_ss_re_layout)
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

    @ViewInject(R.id.house_ada_iv)
    private ImageView house_ada_iv;
    private int ads_h;
    private int ads_w;
    private String href = "";
    private String desc = "";
    private List<Map<String, String>> houseList;
    private HousChenAdapter housAdapter;

    @ViewInject(R.id.be_back_top_fab)
    private FloatingActionButton be_back_top_fab;
    /**
     * 空视图
     */
    @ViewInject(R.id.no_data_iv)
    private ImageView no_data_iv;
    private int height = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 沉浸式解决顶部标题重叠
         */
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("房产购");
        ads_h = Settings.displayWidth * 400 / 1242;
        ads_w = Settings.displayWidth;
        // 设置广告图的高度
        CollapsingToolbarLayout.LayoutParams params = new CollapsingToolbarLayout.LayoutParams(ads_w, ads_h);
        house_ada_iv.setLayoutParams(params);

        initRecycler();//RecyclerView初始化
        softStyle(soft_type);

        be_back_top_fab.attachToRecyclerView(mRvHous, new ScrollDirectionListener() {
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
                RecyclerView.LayoutManager layoutManager = mRvHous.getLayoutManager();
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


        good_luck_sr.setHeaderViewBackgroundColor(Color.WHITE);
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
                        getData();
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
                        getData();
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
    @OnClick({R.id.hot_goods_tv, R.id.lastest_goods_layout,
            R.id.plan_goods_layout, R.id.times_layout,
//            R.id.house_ada_iv,
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
//            case R.id.house_ada_iv:// 广告
//                Bundle bundle = new Bundle();
//                bundle.putString("desc", desc);
//                bundle.putString("href", href);
//                bundle.putInt("from", 2);
//                startActivity(NoticeDetailsAty.class, bundle);
//                break;
            case R.id.be_back_top_fab:// 回到顶部
                mRvHous.smoothScrollToPosition(0);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_hous_chen;
    }

    @Override
    protected void initialized() {
        houseList = new ArrayList<>();
        lat = DemoApplication.getInstance().getLocInfo().get("lat");
        lng = DemoApplication.getInstance().getLocInfo().get("lon");
        houseBuyPst = new HouseBuyPst(this);
        height = ToolKit.dip2px(this, 4);
    }


    @Override
    protected void requestData() {
    }

    private void getData() {
        houseBuyPst.houseList(p, lng, lat, integral_sort, distance_sort, price_sort, sort);
        good_luck_sr.setLoadMore(false);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("houseList")) {
            if (ToolKit.isList(map, "data")) {// 能拿到data
                Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
                if (1 == p) {
                    final Map<String, String> ads = JSONUtils.parseKeyAndValueToMap(data.get("ads"));
                    Glide.with(this).load(ads.get("picture"))
                            .fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .placeholder(R.drawable.ic_default)
                            .error(R.drawable.ic_default)
                            .into(house_ada_iv);
                    house_ada_iv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (!TextUtils.isEmpty(ads.get("merchant_id")) && !ads.get("merchant_id").equals("0")) {
                                Bundle bundle = new Bundle();
                                bundle.putString("mell_id", ads.get("merchant_id"));
                                startActivity(MellInfoAty.class, bundle);
                            } else if (!TextUtils.isEmpty(ads.get("goods_id")) && !ads.get("goods_id").equals("0")) {
                                Bundle bundle = new Bundle();
                                bundle.putString("ticket_buy_id", ads.get("goods_id"));
                                bundle.putInt("from", 1);
                                startActivity(TicketGoodsDetialsAty.class, bundle);
                            } else {
                                Bundle bundle = new Bundle();
                                bundle.putString("desc", desc);
                                bundle.putString("href", href);
                                bundle.putInt("from", 2);
                                startActivity(NoticeDetailsAty.class, bundle);
                            }


                        }
                    });
                    desc = ads.get("desc");
                    href = ads.get("href");
                    if (ToolKit.isList(data, "car_list")) {
                        houseList = JSONUtils.parseKeyAndValueToMapList(data.get("car_list"));
                        housAdapter = new HousChenAdapter(this, houseList);
                        mRvHous.setAdapter(housAdapter);
                    } else {
                        mRvHous.setVisibility(View.GONE);
                        no_data_iv.setVisibility(View.VISIBLE);
                    }
                    if (!frist) {
                        good_luck_sr.setRefreshing(false);
                        good_luck_sr.setLoadMore(false);
                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    if (ToolKit.isList(data, "car_list")) {
                        houseList.addAll(JSONUtils.parseKeyAndValueToMapList(data.get("car_list")));
                        housAdapter.notifyDataSetChanged();
                    }
                }
            } else {// 不存在data
                if (1 == p) {
                    if (!frist) {
                        good_luck_sr.setRefreshing(false);
                        good_luck_sr.setLoadMore(false);
                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    footerImageView.setVisibility(View.VISIBLE);
                    footerProgressBar.setVisibility(View.GONE);
                    good_luck_sr.setRefreshing(false);
                    good_luck_sr.setLoadMore(false);
                }
            }
        }
    }

    /**
     * RecyclerView初始化
     */
    private void initRecycler() {
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        mRvHous.setLayoutManager(manager);
        mRvHous.setHasFixedSize(true);
        mRvHous.addItemDecoration(new GridDividerItemDecoration(height, ContextCompat.getColor(this,
                R.color.bg_color)));
    }

    private void softStyle(int type) {
        hot_goods_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        lastest_goods_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        plan_goods_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        times_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        times_iv.setImageResource(R.mipmap.icon_screen_default_chen);
        plan_goods_time_iv.setImageResource(R.mipmap.icon_screen_default_chen);
        lastest_goods_time_iv.setImageResource(R.mipmap.icon_screen_default_chen);

        p = 1;
        integral_sort = "";
        distance_sort = "";
        price_sort = "";
        sort = "";

        if (0 == type) {// 综合
            hot_goods_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            times = 0;
            plan_goods_times = 0;
            lastest_goods_times = 0;
            sort = "1";
        } else if ((1 == type)) {// 积分
            lastest_goods_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            times = 0;
            plan_goods_times = 0;
            if (lastest_goods_times % 2 == 0) {
                integral_sort = "1";
                lastest_goods_time_iv.setImageResource(R.mipmap.icon_screen_down_chen);
            } else {
                integral_sort = "2";
                lastest_goods_time_iv.setImageResource(R.mipmap.icon_screen_top_chen);
            }
            lastest_goods_times++;
        } else if (2 == type) {// 距离
            times = 0;
            lastest_goods_times = 0;
            plan_goods_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));

            if (plan_goods_times % 2 == 0) {
                distance_sort = "1";
                plan_goods_time_iv.setImageResource(R.mipmap.icon_screen_down_chen);
            } else {
                distance_sort = "2";
                plan_goods_time_iv.setImageResource(R.mipmap.icon_screen_top_chen);
            }

            plan_goods_times++;

        } else {// 价格
            plan_goods_times = 0;
            lastest_goods_times = 0;
            times_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            if (times % 2 == 0) {
                price_sort = "1";
                times_iv.setImageResource(R.mipmap.icon_screen_down_chen);
            } else {
                price_sort = "2";
                times_iv.setImageResource(R.mipmap.icon_screen_top_chen);
            }
            times++;
        }
        getData();
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
