package com.txd.hzj.wjlp.mellOnLine.fgt;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
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
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.CFGoodsList;
import com.txd.hzj.wjlp.http.goods.GoodsPst;
import com.txd.hzj.wjlp.mainFgt.adapter.HorizontalAdapter;
import com.txd.hzj.wjlp.mainFgt.adapter.RacycleAllAdapter;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.TicketGoodsDetialsAty;
import com.txd.hzj.wjlp.tool.GridDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/5 0005
 * 时间：下午 4:09
 * 描述：1-1-2二级分类
 * ===============Txunda===============
 */
public class SubClassifyListFgt extends BaseFgt {

    @ViewInject(R.id.su_classify_goods_rv)
    private RecyclerView su_classify_goods_rv;

    private RacycleAllAdapter racycleAllAdapter;

    private int height = 0;

    private String two = "";
    private String three = "";

    @ViewInject(R.id.sup_sub_layout)
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
     * 是不是第一次进入
     */
    private boolean frist = true;
    private int p = 1;
    private GoodsPst goodsPst;
    private List<CFGoodsList> goodsLists;
    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;

    public static SubClassifyListFgt getFgt(String two, String three) {
        SubClassifyListFgt subClassifyListFgt = new SubClassifyListFgt();
        subClassifyListFgt.two = two;
        subClassifyListFgt.three = three;
        return subClassifyListFgt;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        su_classify_goods_rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        su_classify_goods_rv.setItemAnimator(new DefaultItemAnimator());
        su_classify_goods_rv.setHasFixedSize(true);
        su_classify_goods_rv.addItemDecoration(new GridDividerItemDecoration(height, Color.parseColor("#F6F6F6")));


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
                        goodsPst.threeList(two, three, p, 0);
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
                        goodsPst.threeList(two, three, p, 0);
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

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        try {
//
//        } catch (NullPointerException e) {
//            L.e("Sub==========Error");
//        }
//    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("threeList")) {
            if (ToolKit.isList(map, "data")) {
                Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
                if (1 == p) {
                    if (ToolKit.isList(data, "list")) {
                        goodsLists = GsonUtil.getObjectList(data.get("list"), CFGoodsList.class);
                        racycleAllAdapter = new RacycleAllAdapter(getActivity(), goodsLists);
                        su_classify_goods_rv.setAdapter(racycleAllAdapter);
                        racycleAllAdapter.setListener(new HorizontalAdapter.OnItemClickLitener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Bundle bundle = new Bundle();
                                bundle.putString("ticket_buy_id", goodsLists.get(position).getGoods_id());
                                bundle.putInt("from", 1);
                                startActivity(TicketGoodsDetialsAty.class, bundle);
                            }
                        });
                    } else {
                        su_classify_goods_rv.setVisibility(View.GONE);
                        no_data_layout.setVisibility(View.VISIBLE);
                    }
                    if (!frist) {
                        swipe_refresh.setRefreshing(false);
                        progressBar.setVisibility(View.GONE);
                    }
                } else {

                    if (ToolKit.isList(data, "list")) {
                        goodsLists.addAll(GsonUtil.getObjectList(data.get("list"), CFGoodsList.class));
                        racycleAllAdapter.notifyDataSetChanged();
                    }

                    footerImageView.setVisibility(View.VISIBLE);
                    footerProgressBar.setVisibility(View.GONE);
                    swipe_refresh.setLoadMore(false);
                }
            } else {
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
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_sub_classify_list;
    }

    @Override
    protected void initialized() {
        height = ToolKit.dip2px(getActivity(), 4);
        goodsLists = new ArrayList<>();
        goodsPst = new GoodsPst(this);
    }

    @Override
    protected void requestData() {
        goodsPst.threeList(two, three, p, 1);
    }

    @Override
    protected void immersionInit() {

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
