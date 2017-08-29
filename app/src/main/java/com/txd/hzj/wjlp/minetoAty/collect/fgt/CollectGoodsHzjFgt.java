package com.txd.hzj.wjlp.minetoAty.collect.fgt;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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

import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.L;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.CFGoodsList;
import com.txd.hzj.wjlp.bean.CollectOrFootpointGoods;
import com.txd.hzj.wjlp.http.user.UserPst;
import com.txd.hzj.wjlp.mainFgt.adapter.RacycleAllAdapter;
import com.txd.hzj.wjlp.tool.GridDividerItemDecoration;
import com.txd.hzj.wjlp.view.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/24 0024
 * 时间：下午 5:07
 * 描述：收藏、足迹，商品碎片
 * ===============Txunda===============
 */
public class CollectGoodsHzjFgt extends BaseFgt {
    private static final String ARG_PARAM1 = "status";

    private boolean status;

    private int dataType = 0;

    @ViewInject(R.id.collect_goods_rv)
    private RecyclerView collect_goods_rv;

    private RacycleAllAdapter racycleAllAdapter;

    private List<CFGoodsList> data;
    private List<CFGoodsList> data2;
    private int height = 0;

    @ViewInject(R.id.collect_operation_layout)
    private LinearLayout collect_operation_layout;

    @ViewInject(R.id.swipe_refresh)
    private SuperSwipeRefreshLayout swipe_refresh;

    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;

    private UserPst userPst;
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

    public static CollectGoodsHzjFgt newInstance(boolean param1, int dataType) {
        CollectGoodsHzjFgt fragment = new CollectGoodsHzjFgt();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM1, param1);
        args.putInt("dataType", dataType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            status = getArguments().getBoolean(ARG_PARAM1);
        }

        swipe_refresh.setHeaderViewBackgroundColor(0xff888888);
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
                        userPst.myfooter(p, "1");
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
                        userPst.myfooter(p, "1");
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
    protected int getLayoutResId() {
        return R.layout.fgt_collect_goods_hzj;
    }

    @Override
    protected void initialized() {
        data = new ArrayList<>();
        data2 = new ArrayList<>();
        userPst = new UserPst(this);
        height = ToolKit.dip2px(getActivity(), 4);
    }

    @Override
    protected void requestData() {

        if (0 == dataType) {
            userPst.myfooter(p, "1");
        }

    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        L.e("=====商品=====",error.toString());
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("myfooter")) {
            CollectOrFootpointGoods goods = GsonUtil.GsonToBean(jsonStr, CollectOrFootpointGoods.class);
            allNum = goods.getNums();
            if (allNum > 0) {
                swipe_refresh.setVisibility(View.VISIBLE);
                no_data_layout.setVisibility(View.GONE);
            } else {
                swipe_refresh.setVisibility(View.GONE);
                no_data_layout.setVisibility(View.VISIBLE);
            }
            if (1 == p) {
                data = goods.getList();

                // 适配器初始化
                racycleAllAdapter = new RacycleAllAdapter(getActivity(), data);
                // 布局
                collect_goods_rv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                collect_goods_rv.setItemAnimator(new DefaultItemAnimator());
                collect_goods_rv.setHasFixedSize(true);
                collect_goods_rv.addItemDecoration(new GridDividerItemDecoration(height, Color.parseColor("#F6F6F6")));
                collect_goods_rv.setAdapter(racycleAllAdapter);

                if (!frist) {
                    swipe_refresh.setRefreshing(false);
                    progressBar.setVisibility(View.GONE);
                }
            } else {
                data2 = goods.getList();
                data.addAll(data2);
                racycleAllAdapter.notifyDataSetChanged();

                footerImageView.setVisibility(View.VISIBLE);
                footerProgressBar.setVisibility(View.GONE);
                swipe_refresh.setLoadMore(false);
            }
        }
    }

    @Override
    protected void immersionInit() {

    }

    public void setStatus(boolean show) {
        if (allNum <= 0) {
            return;
        }
        this.status = show;
        if (!show) {
            collect_operation_layout.setVisibility(View.GONE);
            if (racycleAllAdapter != null) {
                racycleAllAdapter.setShowSelect(false);
            }
        } else {
            collect_operation_layout.setVisibility(View.VISIBLE);
            if (racycleAllAdapter != null) {
                racycleAllAdapter.setShowSelect(true);
            }
        }
        assert racycleAllAdapter != null;
        racycleAllAdapter.notifyDataSetChanged();
    }

    private View createFooterView() {
        View footerView = LayoutInflater.from(swipe_refresh.getContext())
                .inflate(R.layout.layout_footer, null);
        footerProgressBar = (ProgressBar) footerView
                .findViewById(R.id.footer_pb_view);
        footerImageView = (ImageView) footerView
                .findViewById(R.id.footer_image_view);
        footerTextView = (TextView) footerView
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
        progressBar = (ProgressBar) headerView.findViewById(R.id.pb_view);
        textView = (TextView) headerView.findViewById(R.id.text_view);
        textView.setText("下拉刷新");
        imageView = (ImageView) headerView.findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.down_arrow);
        progressBar.setVisibility(View.GONE);
        return headerView;
    }
}
