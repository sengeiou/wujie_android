package com.txd.hzj.wjlp.mellOnLine.gridClassify.fgt;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.http.house.HouseBuyPst;
import com.txd.hzj.wjlp.mellOnLine.adapter.DetilsTypeChenAdapter;
import com.txd.hzj.wjlp.tool.GridDividerItemDecoration;

import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：chen
 * 日期：2017/7/8 0007
 * 时间：上午 12:06
 * 描述：房产购详情页_户型(10-2房产购)
 * ===============Txunda===============
 */

public class HousDetailsTypeChenFgt extends BaseFgt {

    @ViewInject(R.id.rv_details_type)//RecyclerView
    private RecyclerView rv_details_type;

    @ViewInject(R.id.hx_be_back_top_iv)
    private ImageView hx_be_back_top_iv;
    private int height = 0;

    private String house_id = "";

    private HouseBuyPst houseBuyPst;
    private int p = 1;

    @ViewInject(R.id.house_ss_re_layout)
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
     * 是不是第一次进入
     */
    private boolean frist = true;

    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;

    private List<Map<String, String>> liat;
    private DetilsTypeChenAdapter adapter;

    public static HousDetailsTypeChenFgt getFgt(String house_id) {
        HousDetailsTypeChenFgt housDetailsHousesChenFgt = new HousDetailsTypeChenFgt();
        housDetailsHousesChenFgt.house_id = house_id;
        return housDetailsHousesChenFgt;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerView();//RecyclerView初始化
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
                        houseBuyPst.houseStyleList(house_id, p);
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
                        houseBuyPst.houseStyleList(house_id, p);
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
    protected void immersionInit() {

    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        //super.onError(requestUrl, error);
        removeProgressDialog();
    }

    @Override
    @OnClick({R.id.hx_be_back_top_iv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.hx_be_back_top_iv:
                rv_details_type.smoothScrollToPosition(0);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_hous_details_type_chen;
    }

    @Override
    protected void initialized() {
        houseBuyPst = new HouseBuyPst(this);
        height = ToolKit.dip2px(getActivity(), 4);

    }

    @Override
    protected void requestData() {
        houseBuyPst.houseStyleList(house_id, p);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("houseStyleList")) {
            if (ToolKit.isList(map, "data")) {
                if (1 == p) {
                    liat = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
                    adapter = new DetilsTypeChenAdapter(getActivity(), liat);
                    rv_details_type.setAdapter(adapter);
                    if (!frist) {
                        good_luck_sr.setRefreshing(false);
                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    liat.addAll(JSONUtils.parseKeyAndValueToMapList(map.get("data")));
                    adapter.notifyDataSetChanged();
                    footerImageView.setVisibility(View.VISIBLE);
                    footerProgressBar.setVisibility(View.GONE);
                    good_luck_sr.setLoadMore(false);
                }
            } else {
                no_data_layout.setVisibility(View.VISIBLE);
                good_luck_sr.setVisibility(View.GONE);
                if (1 == p) {
                    if (!frist) {
                        good_luck_sr.setRefreshing(false);
                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    footerImageView.setVisibility(View.VISIBLE);
                    footerProgressBar.setVisibility(View.GONE);
                    good_luck_sr.setLoadMore(false);
                }
            }

        }

    }

    /**
     * RecyclerView初始化
     */
    private void initRecyclerView() {

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        rv_details_type.setLayoutManager(manager);
        rv_details_type.addItemDecoration(new GridDividerItemDecoration(height, Color.parseColor("#F6F6F6")));
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position <= 3) {
                    hx_be_back_top_iv.setVisibility(View.GONE);
                } else {
                    hx_be_back_top_iv.setVisibility(View.VISIBLE);
                }
                return 1;
            }
        });
    }

    private View createFooterView() {
        View footerView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_footer, null);
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
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_head, null);
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
