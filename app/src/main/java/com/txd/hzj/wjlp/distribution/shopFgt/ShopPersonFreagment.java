package com.txd.hzj.wjlp.distribution.shopFgt;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.distribution.adapter.ShopPersonAdapter;
import com.txd.hzj.wjlp.distribution.bean.ShopPersonBean;
import com.txd.hzj.wjlp.distribution.presenter.ShopExhibitPst;
import com.txd.hzj.wjlp.view.SuperSwipeRefreshLayout;

import java.util.List;

/**
 * 创建者：Qyl
 * 创建时间：2018/6/13 0013 15:51
 * 功能描述：顾客管理，普通顾客页面
 * 联系方式：无
 */
public class ShopPersonFreagment extends BaseFgt {


    private ShopPersonAdapter adapter;
    @ViewInject(R.id.shop_person_relist)
    private RecyclerView reList;
    @ViewInject(R.id.shopPerson_super_ssrl)
    private SuperSwipeRefreshLayout shopPerson_super_ssrl;

    private int p = 1; // 请求的分页
    // Header View
    private RelativeLayout head_container;
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;

    private ShopExhibitPst mExhibitPst;
    private List<ShopPersonBean.DataBean.ConsumerBean> mConsumer;

    public static ShopPersonFreagment newInstance(int prage) {
        ShopPersonFreagment freagment = new ShopPersonFreagment();
        Bundle bundle = new Bundle();
        bundle.putInt("prage", prage);
        freagment.setArguments(bundle);
        return freagment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.shop_person_fragment;
    }

    @Override
    protected void initialized() {
        mExhibitPst = new ShopExhibitPst(this);
    }

    @Override
    protected void requestData() {
        mExhibitPst.getShopPerson("18", "1");


        shopPerson_super_ssrl.setHeaderView(createHeaderView());// add headerView
        shopPerson_super_ssrl.setFooterView(createFooterView());
        shopPerson_super_ssrl.setHeaderViewBackgroundColor(Color.WHITE);
        shopPerson_super_ssrl.setTargetScrollWithLayout(true);
        shopPerson_super_ssrl.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                textView.setText("正在刷新");
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                p = 1;
                // TODO 请求接口
                mExhibitPst.getShopPerson("18", "1");
                //                shopManageOrdinaryChild_sr_layout.setRefreshing(false);
                //                progressBar.setVisibility(View.GONE);
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
        shopPerson_super_ssrl.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                footerTextView.setText("正在加载...");
                footerImageView.setVisibility(View.GONE);
                footerProgressBar.setVisibility(View.VISIBLE);
                p++;
                // TODO 请求接口
                //                shopManageOrdinaryChild_sr_layout.setLoadMore(false);
                //                progressBar.setVisibility(View.GONE);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        //set false when finished
                        showErrorTip("无更多数据了");
                        shopPerson_super_ssrl.setLoadMore(false);
                        progressBar.setVisibility(View.GONE);
                    }
                }, 5000);
            }

            @Override
            public void onPushDistance(int distance) {
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
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        refreshVisibleState();
        ShopPersonBean shopPersonBean = JSON.parseObject(jsonStr, ShopPersonBean.class);
        if (200 == shopPersonBean.getCode()) {
            mConsumer = shopPersonBean.getData().getConsumer();
            adapter = new ShopPersonAdapter(shopPersonBean, getActivity(), 1);
            reList.setLayoutManager(new LinearLayoutManager(getActivity()));
            reList.setAdapter(adapter);
        }

    }

    private void refreshVisibleState() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            shopPerson_super_ssrl.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
        }
        if (footerProgressBar.getVisibility() == View.VISIBLE) {
            shopPerson_super_ssrl.setLoadMore(false);
            footerProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void immersionInit() {

    }

    /**
     * 创建底部加载布局
     *
     * @return
     */
    private View createFooterView() {
        View footerView = LayoutInflater.from(shopPerson_super_ssrl.getContext()).inflate(R.layout.layout_footer, null);
        footerProgressBar = footerView.findViewById(R.id.footer_pb_view);
        footerImageView = footerView.findViewById(R.id.footer_image_view);
        footerTextView = footerView.findViewById(R.id.footer_text_view);
        footerProgressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.VISIBLE);
        footerImageView.setImageResource(R.drawable.down_arrow);
        footerTextView.setText("上拉加载更多...");
        return footerView;
    }

    /**
     * 创建头部加载布局
     *
     * @return
     */
    private View createHeaderView() {
        View headerView = LayoutInflater.from(shopPerson_super_ssrl.getContext()).inflate(R.layout.layout_head, null);
        head_container = headerView.findViewById(R.id.head_container);
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
