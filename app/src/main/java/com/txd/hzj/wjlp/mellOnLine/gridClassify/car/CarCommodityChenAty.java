package com.txd.hzj.wjlp.mellOnLine.gridClassify.car;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.DemoApplication;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.carbuy.CarBuyPst;
import com.txd.hzj.wjlp.http.collect.UserCollectPst;
import com.txd.hzj.wjlp.http.user.UserPst;
import com.txd.hzj.wjlp.mellOnLine.adapter.CarCommodityChenAdapter;
import com.txd.hzj.wjlp.mellOnLine.adapter.CarTypeChenAdapter;
import com.txd.hzj.wjlp.tool.GridDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：chen
 * 日期：2017/7/8 0007
 * 时间：上午 17.27
 * 描述：汽车购(9-2汽车购)
 * ===============Txunda===============
 */
public class CarCommodityChenAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)//标题
    private TextView titlt_conter_tv;
    @ViewInject(R.id.rv_car_commodity)//RecyclerView
    private RecyclerView rv_car_commodity;
    private int height = 0;

    @ViewInject(R.id.car_be_back_top_iv)
    private ImageView car_be_back_top_iv;
    private GridLayoutManager barndManager;

    private String min_price = "0.0001";
    private String max_price = "1000";
    private String style_id = "0";
    private String brand_id = "0";
    private CarBuyPst carBuyPst;
    /**
     * 分页
     */
    @ViewInject(R.id.car_s_s_r_layout)
    private SuperSwipeRefreshLayout swipe_refresh;

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

    /**
     * 空视图
     */
    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;
    private int allNum = 0;

    private List<Map<String, String>> list;
    private CarCommodityChenAdapter gridAdapter;


    private String lat;
    private String lng;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 沉浸式解决顶部标题重叠
         */
        showStatusBar(R.id.title_re_layout);
        initRecycler();//RecyclerView初始化
        barndManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position <= 3) {
                    car_be_back_top_iv.setVisibility(View.GONE);
                } else {
                    car_be_back_top_iv.setVisibility(View.VISIBLE);
                }
                return 1;
            }
        });
        swipe_refresh.setHeaderViewBackgroundColor(Color.WHITE);
        swipe_refresh.setHeaderView(createHeaderView());// add headerView
        swipe_refresh.setFooterView(createFooterView());
        swipe_refresh.setTargetScrollWithLayout(true);

        swipe_refresh
                .setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {

                    @Override
                    public void onRefresh() {
                        frist = false;
                        textView.setText("正在刷新");
                        imageView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        p = 1;
                        carBuyPst.carList(min_price, max_price, p, style_id, brand_id, lng, lat);
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

        swipe_refresh
                .setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {

                    @Override
                    public void onLoadMore() {
                        frist = false;
                        footerTextView.setText("正在加载...");
                        footerImageView.setVisibility(View.GONE);
                        footerProgressBar.setVisibility(View.VISIBLE);

                        p++;
                        carBuyPst.carList(min_price, max_price, p, style_id, brand_id, lng, lat);
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
    @OnClick({R.id.car_be_back_top_iv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.car_be_back_top_iv:
                car_be_back_top_iv.setVisibility(View.GONE);
                rv_car_commodity.scrollToPosition(0);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_car_commodity;
    }


    @Override
    protected void initialized() {
        lat = DemoApplication.getInstance().getLocInfo().get("lat");
        lng = DemoApplication.getInstance().getLocInfo().get("lon");
        titlt_conter_tv.setText("汽车购");
        carBuyPst = new CarBuyPst(this);
        height = ToolKit.dip2px(this, 4);
        list = new ArrayList<>();
        min_price = getIntent().getStringExtra("min_price");
        max_price = getIntent().getStringExtra("max_price");
        style_id = getIntent().getStringExtra("style_id");
        brand_id = getIntent().getStringExtra("brand_id");
    }


    @Override
    protected void requestData() {
        carBuyPst.carList(min_price, max_price, p, style_id, brand_id, lng, lat);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("carList")) {
            allNum = Integer.parseInt(map.get("nums"));
            if (allNum > 0) {
                swipe_refresh.setVisibility(View.VISIBLE);
                no_data_layout.setVisibility(View.GONE);
            } else {
                swipe_refresh.setVisibility(View.GONE);
                no_data_layout.setVisibility(View.VISIBLE);
            }
            if (ToolKit.isList(map, "data")) {
                if (1 == p) {
                    list = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
                    gridAdapter = new CarCommodityChenAdapter(this, list);
                    rv_car_commodity.setAdapter(gridAdapter);
                } else {
                    list.addAll(JSONUtils.parseKeyAndValueToMapList(map.get("data")));
                    gridAdapter.notifyDataSetChanged();
                }
            } else {// 没有数据
                if (1 == p) {
                    if (!frist) {
                        swipe_refresh.setRefreshing(false);
                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    footerImageView.setVisibility(View.VISIBLE);
                    footerProgressBar.setVisibility(View.GONE);
                    swipe_refresh.setLoadMore(false);
                }
            }
            swipe_refresh.setLoadMore(false);
            swipe_refresh.setRefreshing(false);
        }
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);

        if (1 == p) {
            if (!frist) {
                swipe_refresh.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
            }
        } else {
            footerImageView.setVisibility(View.VISIBLE);
            footerProgressBar.setVisibility(View.GONE);
            swipe_refresh.setLoadMore(false);
        }
    }

    /**
     * RecyclerView初始化
     */
    private void initRecycler() {
        barndManager = new GridLayoutManager(this, 2);
        rv_car_commodity.setLayoutManager(barndManager);
        rv_car_commodity.setHasFixedSize(true);
        rv_car_commodity.addItemDecoration(new GridDividerItemDecoration(height, Color.parseColor("#F6F6F6")));
    }

    private View createFooterView() {
        View footerView = LayoutInflater.from(swipe_refresh.getContext())
                .inflate(R.layout.layout_footer, null);
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
        View headerView = LayoutInflater.from(swipe_refresh.getContext())
                .inflate(R.layout.layout_head, null);
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
