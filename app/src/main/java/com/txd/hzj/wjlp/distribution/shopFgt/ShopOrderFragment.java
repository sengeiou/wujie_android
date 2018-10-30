package com.txd.hzj.wjlp.distribution.shopFgt;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.ants.theantsgo.util.PreferencesUtils;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.distribution.adapter.ShopOrderManageAdapter;
import com.txd.hzj.wjlp.distribution.bean.ShopOrderBean;
import com.txd.hzj.wjlp.distribution.presenter.ShopExhibitPst;

import java.util.List;
import java.util.Map;

/**
 * 创建者：Qyl
 * 创建时间：2018/6/12 0012 9:26
 * 功能描述：此类为分销订单管理fragment
 * 联系方式：无
 */
public class ShopOrderFragment extends BaseFgt {

    @ViewInject(R.id.empty_layout)
    private FrameLayout empty_layout;
    //订单列表
    @ViewInject(R.id.shop_order_re_list)
    private RecyclerView shop_order_re_list;
    //刷新
    @ViewInject(R.id.shop_order_refresh)
    private SuperSwipeRefreshLayout refreshLayout;
    private ShopOrderManageAdapter adapter;
    //刷新头
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;
    //加载
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;


    private ShopExhibitPst mExhibitPst;
    private String mTitle;
    private String mStatus="";
    private String mShop_id;

    public static ShopOrderFragment newInstance(String string) {
        ShopOrderFragment fragment = new ShopOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString("ShopOrderFragment", string);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.shop_order_frg;
    }

    @Override
    protected void initialized() {
        mExhibitPst = new ShopExhibitPst(this);
        if (PreferencesUtils.containKey(getActivity(),"shop_id")){
            mShop_id = PreferencesUtils.getString(getActivity(), "shop_id");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()){
            getData();
        }
    }

    private void getData(){
        if (getArguments() != null) {
            mTitle = getArguments().getString("ShopOrderFragment");
            if ("全部".equals(mTitle)) {
                mStatus="";
            } else if ("待付款".equals(mTitle)) {
                mStatus="0";
            } else if ("待发货".equals(mTitle)) {
                mStatus="1";
            } else if ("待收货".equals(mTitle)) {
                mStatus="2";
            } else if ("待评价".equals(mTitle)) {
                mStatus="3";
            }else if ("已完成".equals(mTitle)) {
                mStatus="4";
            }
        }
        if (null != mExhibitPst && !TextUtils.isEmpty(mShop_id)) {
            mExhibitPst.shopOrderList(mShop_id, "2", mStatus);
        }
    }

    @Override
    protected void requestData() {
    }


    @Override
    protected void immersionInit() {
        refreshLayout.setHeaderView(createHeaderView());// add headerView
//        refreshLayout.setFooterView(createFooterView());
        refreshLayout.setHeaderViewBackgroundColor(Color.WHITE);
        refreshLayout.setTargetScrollWithLayout(true);
        refreshLayout.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                textView.setText("正在刷新");
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                getData();
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
//        refreshLayout.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                footerTextView.setText("正在加载...");
//                footerImageView.setVisibility(View.GONE);
//                footerProgressBar.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onPushDistance(int i) {
//
//            }
//
//            @Override
//            public void onPushEnable(boolean enable) {
//                footerTextView.setText(enable ? "松开加载" : "上拉加载");
//                footerImageView.setVisibility(View.VISIBLE);
//                footerImageView.setRotation(enable ? 0 : 180);
//            }
//        });
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


    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("orders")){
            ShopOrderBean shopOrderBean = JSONObject.parseObject(jsonStr, ShopOrderBean.class);
            if (200==shopOrderBean.getCode()){
                List<ShopOrderBean.DataBean> data = shopOrderBean.getData();
                for (ShopOrderBean.DataBean dataBean:data){
                    if (dataBean.getOrder_goods()==null || dataBean.getOrder_goods().size()==0){
                        data.remove(dataBean);
                    }
                }
                if (null != data && data.size()>0){
                    empty_layout.setVisibility(View.GONE);
                    refreshLayout.setVisibility(View.VISIBLE);
                    shop_order_re_list.setLayoutManager(new LinearLayoutManager(getActivity()));
                    adapter = new ShopOrderManageAdapter(data, getActivity(), mTitle);
                    shop_order_re_list.setAdapter(adapter);
                }else {
                    empty_layout.setVisibility(View.VISIBLE);
                    refreshLayout.setVisibility(View.GONE);
                }

            }
        }
        refreshComplete();
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        empty_layout.setVisibility(View.VISIBLE);
        refreshLayout.setVisibility(View.GONE);
        removeDialog();
        refreshComplete();
    }

    private void refreshComplete(){
        if (progressBar.getVisibility()==View.VISIBLE){
            refreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
        }
//        if (footerProgressBar.getVisibility()==View.VISIBLE){
//            refreshLayout.setLoadMore(false);
//            progressBar.setVisibility(View.GONE);
//        }

    }
}
