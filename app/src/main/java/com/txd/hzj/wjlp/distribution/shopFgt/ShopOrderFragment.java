package com.txd.hzj.wjlp.distribution.shopFgt;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.distribution.adapter.ShopOrderManageAdapter;

import java.util.ArrayList;

/**
 * 创建者：Qyl
 * 创建时间：2018/6/12 0012 9:26
 * 功能描述：此类为分销订单管理fragment
 * 联系方式：无
 */
public class ShopOrderFragment extends BaseFgt {

    //订单列表
    @ViewInject(R.id.shop_order_re_list)
    private RecyclerView shop_order_re_list;
    //刷新
    @ViewInject(R.id.shop_order_refresh)
    private SuperSwipeRefreshLayout refreshLayout;
    private ShopOrderManageAdapter adapter;
    private ArrayList list;
    //刷新头
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;
    //加载
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;

    public static ShopOrderFragment newInstance(String string) {
        ShopOrderFragment fragment = new ShopOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString("String", string);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.shop_order_frg;
    }

    @Override
    protected void initialized() {
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void immersionInit() {
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        shop_order_re_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ShopOrderManageAdapter(list, getActivity());
        shop_order_re_list.setAdapter(adapter);

        refreshLayout.setHeaderView(createHeaderView());// add headerView
        refreshLayout.setFooterView(createFooterView());
        refreshLayout.setHeaderViewBackgroundColor(Color.WHITE);
        refreshLayout.setTargetScrollWithLayout(true);
        refreshLayout.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
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
        refreshLayout.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
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

    private View createHeaderView() {
        View headerView = LayoutInflater.from(refreshLayout.getContext()).inflate(R.layout.layout_head, null);
        progressBar = headerView.findViewById(R.id.pb_view);
        textView = headerView.findViewById(R.id.text_view);
        textView.setText("下拉刷新");
        imageView = headerView.findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.down_arrow);
        progressBar.setVisibility(View.GONE);
        return headerView;
    }

    private View createFooterView() {
        View footerView = LayoutInflater.from(refreshLayout.getContext()).inflate(R.layout.layout_footer, null);
        footerProgressBar = footerView.findViewById(R.id.footer_pb_view);
        footerImageView = footerView.findViewById(R.id.footer_image_view);
        footerTextView = footerView.findViewById(R.id.footer_text_view);
        footerProgressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.VISIBLE);
        footerImageView.setImageResource(R.drawable.down_arrow);
        footerTextView.setText("上拉加载更多...");
        return footerView;
    }
}
