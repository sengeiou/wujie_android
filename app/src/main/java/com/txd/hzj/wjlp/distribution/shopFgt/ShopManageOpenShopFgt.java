package com.txd.hzj.wjlp.distribution.shopFgt;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.ants.theantsgo.util.PreferencesUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.distribution.adapter.ShopManageOpenAdapter;
import com.txd.hzj.wjlp.distribution.bean.DistributionGoodsBean;
import com.txd.hzj.wjlp.distribution.presenter.ShopExhibitPst;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.ToShareAty;
import com.txd.hzj.wjlp.view.SuperSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 创建者：wjj
 * 创建时间：2018-06-11 上午 11:02
 * 功能描述： 开店商品管理界面
 */
public class ShopManageOpenShopFgt extends BaseFgt {

    @ViewInject(R.id.shopManageOpen_data_gv)
    private GridView shopManageOpen_data_gv;
    @ViewInject(R.id.shopManageOpen_super_ssrl)
    private SuperSwipeRefreshLayout shopManageOpen_super_ssrl;

    List<DistributionGoodsBean.DataBean> list;
    ShopManageOpenAdapter adapter;

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
    private String mShop_id;
    private ShopExhibitPst mExhibitPst;

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
        shopManageOpen_super_ssrl.setRefreshing(true);
    }



    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_shopmanage_openshop;
    }

    @Override
    protected void initialized() {
        list = new ArrayList<>();
        mExhibitPst = new ShopExhibitPst(this);
        if (PreferencesUtils.containKey(getActivity(),"shop_id")){
            mShop_id = PreferencesUtils.getString(getActivity(), "shop_id");
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        shopManageOpen_super_ssrl.setHeaderView(createHeaderView());// add headerView
        shopManageOpen_super_ssrl.setFooterView(createFooterView());
        shopManageOpen_super_ssrl.setHeaderViewBackgroundColor(Color.WHITE);
        shopManageOpen_super_ssrl.setTargetScrollWithLayout(true);
        shopManageOpen_super_ssrl.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                textView.setText("正在刷新");
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                p = 1;
                requestData();

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
        shopManageOpen_super_ssrl.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                footerTextView.setText("正在加载...");
                footerImageView.setVisibility(View.GONE);
                footerProgressBar.setVisibility(View.VISIBLE);
                p++;
                requestData();
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
    protected void requestData() {
        if (null != mExhibitPst && !TextUtils.isEmpty(mShop_id)) {
            mExhibitPst.getGoodsList("", String.valueOf(p),mShop_id, "1");
        }
    }


    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("goods")){
            DistributionGoodsBean distributionGoodsBean = JSONObject.parseObject(jsonStr, DistributionGoodsBean.class);
            if (200==distributionGoodsBean.getCode()){
                List<DistributionGoodsBean.DataBean> data = distributionGoodsBean.getData();
                if (null!=data && data.size()>0){
                    if (p==1){
                        list.clear();
                    }
                    list.addAll(data);
                    adapter = new ShopManageOpenAdapter(getActivity(), list);
                    adapter.setOnImageClickListener(new ShopManageOpenAdapter.ImageClick() {
                        @Override
                        public void onImageClick(View view, int position) {
                            //分享功能，可以使用ToShareAty  toShare("无界优品", share_img, share_url, share_content, goods_id, "1");
                            Toast.makeText(getActivity(), ""+position, Toast.LENGTH_SHORT).show();
                            DistributionGoodsBean.DataBean goodsBean = list.get(position);
                            Bundle bundle = new Bundle();
                            bundle.putString("title", goodsBean.getGoods_name());
                            bundle.putString("pic", goodsBean.getGoods_img());
                            bundle.putString("url","1" );
                            bundle.putString("context", goodsBean.getGoods_name());
                            bundle.putString("id", "1");
                            bundle.putString("Shapetype", "1");
                            startActivity(ToShareAty.class, bundle);
                        }
                    });
                    shopManageOpen_data_gv.setAdapter(adapter);
                }


            }
        }
        refreshComplete();
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        removeProgressDialog();
        refreshComplete();
    }

    private void refreshComplete(){
        if (progressBar.getVisibility()==View.VISIBLE){
            shopManageOpen_super_ssrl.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
        }
        if (footerProgressBar.getVisibility()==View.VISIBLE){
            shopManageOpen_super_ssrl.setLoadMore(false);
            progressBar.setVisibility(View.GONE);
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
        View footerView = LayoutInflater.from(shopManageOpen_super_ssrl.getContext()).inflate(R.layout.layout_footer, null);
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
        View headerView = LayoutInflater.from(shopManageOpen_super_ssrl.getContext()).inflate(R.layout.layout_head, null);
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
