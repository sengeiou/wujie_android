package com.txd.hzj.wjlp.minetoAty.order.fgt;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ants.theantsgo.util.JSONUtils;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.mainFgt.adapter.AuctionRecordAdapter;
import com.txd.hzj.wjlp.new_wjyp.AuctionRecordAty;
import com.txd.hzj.wjlp.http.AuctionOrder;

import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/19 0019
 * 时间：13:34
 * 描述：竞拍碎片
 * ===============Txunda===============
 */

public class AuctionRecordFgt extends BaseFgt {

    private String type;
    private String title;

    @ViewInject(R.id.order_on_line_lv)
    private ListView order_on_line_lv;
    private AuctionRecordAdapter adapter;
    private int p = 1;
    @ViewInject(R.id.super_sr_layout)
    SuperSwipeRefreshLayout swipe_refresh;

    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;

    private boolean frist = true;

    public static AuctionRecordFgt getFgt(String title, String type) {
        AuctionRecordFgt fgt = new AuctionRecordFgt();
        fgt.type = type;
        fgt.title = title;
        return fgt;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 1.竞拍中，2.竞拍成功，3.竞拍结束
        //    order_on_line_lv.setAdapter(adapter);//显示竞拍中list
    }

    @Override
    protected void immersionInit() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_order_on_line_fgt;
    }

    @Override
    protected void initialized() {

        //   adapter = new AuctionRecordAdapter(getActivity(), list, Integer.parseInt(type) + 1);
    }

    @Override
    protected void requestData() {
        if (swipe_refresh == null){
            swipe_refresh = getActivity().findViewById(R.id.super_sr_layout);
        }
        swipe_refresh.setHeaderViewBackgroundColor(Color.WHITE);
        swipe_refresh.setHeaderView(createHeaderView());// add headerView
        swipe_refresh.setFooterView(createFooterView());
        swipe_refresh.setTargetScrollWithLayout(true);
        swipe_refresh.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {

                    @Override
                    public void onRefresh() {
                        frist = false;
                        textView.setText("正在刷新");
                        imageView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        p = 1;

                        AuctionOrder.OrderList(type, p, AuctionRecordFgt.this);

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
                        AuctionOrder.OrderList(type, p, AuctionRecordFgt.this);

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


        AuctionOrder.OrderList(type, p, this);
        order_on_line_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("id", list.get(position).get("order_id"));
                startActivity(AuctionRecordAty.class, bundle);
            }
        });
    }

    Map<String, String> data;
    List<Map<String, String>> list;
    List<Map<String, String>> more;

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        data = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (p == 1) {
            list = JSONUtils.parseKeyAndValueToMapList(data.get("data"));
            adapter = new AuctionRecordAdapter(getActivity(), list);
            order_on_line_lv.setAdapter(adapter);
            if (!frist) {
                swipe_refresh.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
            }
        } else {
            more = JSONUtils.parseKeyAndValueToMapList(data.get("data"));
            list.addAll(more);
            adapter.notifyDataSetChanged();
            footerImageView.setVisibility(View.VISIBLE);
            footerProgressBar.setVisibility(View.GONE);
            swipe_refresh.setLoadMore(false);
        }
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        removeProgressDialog();
        swipe_refresh.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.VISIBLE);
        footerProgressBar.setVisibility(View.GONE);
        swipe_refresh.setLoadMore(false);
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


