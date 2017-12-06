package com.txd.hzj.wjlp.minetoAty.order.fgt;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.Order;
import com.txd.hzj.wjlp.mainFgt.adapter.IndianaRecordAdapter;
import com.txd.hzj.wjlp.mainFgt.adapter.MyOrderAdapter;
import com.txd.hzj.wjlp.minetoAty.order.GoodLuckOrderDetailsAty;
import com.txd.hzj.wjlp.minetoAty.order.OrderDetailsAty;
import com.txd.hzj.wjlp.popAty.LovingAdapter;
import com.txd.hzj.wjlp.txunda_lh.CarOrderInfo;
import com.txd.hzj.wjlp.txunda_lh.http.CarOrder;
import com.txd.hzj.wjlp.txunda_lh.http.HouseOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/19 0019
 * 时间：上午 11:46
 * 描述：线上商城订单碎片
 * ===============Txunda===============
 */
public class OrderOnLineFgt extends BaseFgt {
    private String from="";
    /**
     * 订单分类
     */
    private String title;
    /**
     * 订单类型
     */
    private String type;
    @ViewInject(R.id.super_sr_layout)
    private SuperSwipeRefreshLayout swipe_refresh;
    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;
    @ViewInject(R.id.layout)
    FrameLayout root;
    @ViewInject(R.id.order_on_line_lv)
    private ListView order_on_line_lv;
    private MyOrderAdapter adapter;

    private LovingAdapter lovingAdapter;
    private List<Order> list;
    private List<Order> more;
    private int p = 1;

    /**
     * 是不是第一次进入
     */
    private boolean frist = true;

    public OrderOnLineFgt() {

    }

    public static OrderOnLineFgt getFgt(String title, String type, String from) {
        OrderOnLineFgt fgt = new OrderOnLineFgt();
        fgt.type = type;
        fgt.from = from;
        fgt.title = title;
        return fgt;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        if (title.equals("抢宝记录")) {
//            // 0.全部，1.代付款，2.代发货，3.待收货
//            IndianaRecordAdapter adapter = new IndianaRecordAdapter(getActivity(), list, Integer.parseInt(type));
//            order_on_line_lv.setAdapter(adapter);//显示全部list
//        } else if (title.equals("爱心商店")) {
//        } else {
//            // 0.全部，1.代付款，2.代发货，3.待收货，4.待评价
//            adapter = new MyOrderAdapter(getActivity(), list, Integer.parseInt(type));
//            order_on_line_lv.setAdapter(adapter);//显示全部list
//        }

        order_on_line_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                L.e("=====title=====", title);
//                if (title.equals("抢宝记录")) {
//                    startActivity(GoodLuckOrderDetailsAty.class, null);
//                } else {
//                    startActivity(OrderDetailsAty.class, null);
//                }
                Bundle bundle = new Bundle();
                bundle.putString("id", list.get(i).getOrder_id());
                bundle.putString("type",type);
                startActivity(CarOrderInfo.class, bundle);
            }
        });

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_order_on_line_fgt;
    }

    @Override
    protected void initialized() {

    }


    @Override
    protected void requestData() {
        if (from.equals("1")) {
            CarOrder.orderList(type, p, OrderOnLineFgt.this);
        } else {
            HouseOrder.orderList(type, p, OrderOnLineFgt.this);
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
                        if (from.equals("1")) {
                            CarOrder.orderList(type, p, OrderOnLineFgt.this);
                        } else {
                            HouseOrder.orderList(type, p, OrderOnLineFgt.this);
                        }
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
                        if (from.equals("1")) {
                            CarOrder.orderList(type, p, OrderOnLineFgt.this);
                        } else {
                            HouseOrder.orderList(type, p, OrderOnLineFgt.this);
                        }
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

    private Map<String, String> data;

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        data = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("orderList")) {
            if (p == 1) {
                list = GsonUtil.getObjectList(data.get("data"), Order.class);
                adapter = new MyOrderAdapter(getActivity(), list,from);
                order_on_line_lv.setAdapter(adapter);
                if (!frist) {
                    swipe_refresh.setRefreshing(false);
                    progressBar.setVisibility(View.GONE);
                }

            } else {
                more = GsonUtil.getObjectList(data.get("data"), Order.class);
                list.addAll(more);
                adapter.notifyDataSetChanged();
                footerImageView.setVisibility(View.VISIBLE);
                footerProgressBar.setVisibility(View.GONE);
                swipe_refresh.setLoadMore(false);
            }
        }
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
