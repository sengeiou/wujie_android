package com.txd.hzj.wjlp.distribution.shopAty;

import android.content.Context;
import android.graphics.Color;
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
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.distribution.adapter.ShopOrderManageAdapter;
import com.txd.hzj.wjlp.distribution.bean.ShopOrderBean;
import com.txd.hzj.wjlp.distribution.presenter.ShopExhibitPst;

import java.util.List;
import java.util.Map;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/9/11 15:34
 * 功能描述：
 */
public class ShopGiveYellowVoucher extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    private TextView titleName;
    private Context mContext;
    private ShopExhibitPst mExhibitPst;
    private String mShop_id;

    @ViewInject(R.id.empty_layout)
    private FrameLayout empty_layout;

    //订单列表
    @ViewInject(R.id.shop_order_re_list)
    private RecyclerView shop_order_re_list;
    //刷新
    @ViewInject(R.id.shop_order_refresh)
    private SuperSwipeRefreshLayout refreshLayout;
    private ShopOrderManageAdapter adapter;
    private int postion = 0;

    //刷新头
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;
    //加载
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;
    private int page = 1;
    private List<ShopOrderBean.DataBean> data;
    private double totalPrice=0;

    @ViewInject(R.id.total_tv)
    private TextView total_tv;

    @Override
    protected int getLayoutResId() {
        return R.layout.shop_give_blue_aty;
    }

    @Override
    protected void initialized() {
        mContext = this;
        titleName.setText("黄券审核");
        mExhibitPst = new ShopExhibitPst(this);
        if (PreferencesUtils.containKey(mContext, "shop_id")) {
            mShop_id = PreferencesUtils.getString(mContext, "shop_id");
        }

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
                page = 1;
                requestData();
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
                page+=1;
                requestData();
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

    @Override
    protected void requestData() {
        if (null != mExhibitPst && !TextUtils.isEmpty(mShop_id)) {
            mExhibitPst.shopYellowList(mShop_id, "4", String.valueOf(page));
        }
    }


    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("orders")) {
            ShopOrderBean shopOrderBean = JSONObject.parseObject(jsonStr, ShopOrderBean.class);
            if (200 == shopOrderBean.getCode()) {
                String nums = shopOrderBean.getNums();
                if (!TextUtils.isEmpty(nums)){
                    totalPrice=Double.parseDouble(nums);
                    total_tv.setText("剩余黄券额度"+nums);
                }
                if (page==1){
                    data = shopOrderBean.getData();
                    for (ShopOrderBean.DataBean dataBean : data) {
                        if (dataBean.getOrder_goods() == null || dataBean.getOrder_goods().size() == 0) {
                            data.remove(dataBean);
                        }
                    }
                    if (null != data && data.size() > 0) {
                        empty_layout.setVisibility(View.GONE);
                        refreshLayout.setVisibility(View.VISIBLE);
                        shop_order_re_list.setLayoutManager(new LinearLayoutManager(mContext));
                        adapter = new ShopOrderManageAdapter(data, mContext, "代金券");
                        shop_order_re_list.setAdapter(adapter);
                        adapter.setButtonClickListener(new ShopOrderManageAdapter.OnButtonClickListener() {
                            @Override
                            public void buttonClick(int num, String price, String ticket_status) {
                                postion = num;
                                if ("2".equals(ticket_status)){
                                    if (totalPrice>0){
                                        mExhibitPst.shopSetOrderTicket(data.get(num).getOrder_id(), ticket_status, price);
                                    }
                                }else {
                                    mExhibitPst.shopSetOrderTicket(data.get(num).getOrder_id(), ticket_status, price);
                                }



                            }
                        });
                    } else {
                        total_tv.setVisibility(View.GONE);
                        empty_layout.setVisibility(View.VISIBLE);
                        refreshLayout.setVisibility(View.GONE);
                    }
                }else {
                    List<ShopOrderBean.DataBean> data = shopOrderBean.getData();
                    for (ShopOrderBean.DataBean dataBean : data) {
                        if (dataBean.getOrder_goods() == null || dataBean.getOrder_goods().size() == 0) {
                            data.remove(dataBean);
                        }
                    }
                    this.data.addAll(data);
                   adapter.notifyDataSetChanged();
                }


            }
        }

        if (requestUrl.contains("setOrderTicket")) {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            if (jsonObject.containsKey("code") && "200".equals(jsonObject.getString("code"))) {
                if (jsonObject.containsKey("message")) {
                    showToast(jsonObject.getString("message"));
                    adapter.notifyData(postion);
                    if (adapter.getItemCount() == 0) {
                        total_tv.setVisibility(View.GONE);
                        empty_layout.setVisibility(View.VISIBLE);
                        refreshLayout.setVisibility(View.GONE);
                    }
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

    private void refreshComplete() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            refreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
        }
        if (footerProgressBar.getVisibility() == View.VISIBLE) {
            refreshLayout.setLoadMore(false);
            progressBar.setVisibility(View.GONE);
        }

    }
}
