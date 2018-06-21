package com.txd.hzj.wjlp.distribution.shopFgt;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.distribution.adapter.ShopManageOpenAdapter;
import com.txd.hzj.wjlp.distribution.bean.DistributionGoodsBean;
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

    List<DistributionGoodsBean> list;
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

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
        shopManageOpen_super_ssrl.setRefreshing(true);
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        removeProgressDialog();
        shopManageOpen_super_ssrl.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.VISIBLE);
        footerProgressBar.setVisibility(View.GONE);
        shopManageOpen_super_ssrl.setLoadMore(false);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_shopmanage_openshop;
    }

    @Override
    protected void initialized() {
    }

    @Override
    protected void requestData() {
    }

    @Override
    public void onResume() {
        super.onResume();
        getdata();
    }

    private void getdata() {
        list = new ArrayList<>();
        DistributionGoodsBean distributionGoodsBean;
        for (int i = 0; i < 10; i++) {
            distributionGoodsBean = new DistributionGoodsBean();
            distributionGoodsBean.set_id(i);
            distributionGoodsBean.setImageUrl("https://gd1.alicdn.com/imgextra/i1/646527539/TB2goIfbiMnBKNjSZFCXXX0KFXa_!!646527539.jpg_400x400.jpg");
            distributionGoodsBean.setGoodsName("测试商品测试商品测试商品测试商品测试商品测试商品测试商品测试商品" + i);
            distributionGoodsBean.setDaijinquan("最多可用50%代金券");
            distributionGoodsBean.setMeny("1380.00");
            distributionGoodsBean.setJifen("10.00");
            distributionGoodsBean.setChecked(false);
            list.add(distributionGoodsBean);
        }
        adapter = new ShopManageOpenAdapter(getActivity(), list);
        adapter.setOnImageClickListener(new ShopManageOpenAdapter.ImageClick() {
            @Override
            public void onImageClick(View view, int position) {
                //分享功能，可以使用ToShareAty  toShare("无界优品", share_img, share_url, share_content, goods_id, "1");
                Toast.makeText(getActivity(), ""+position, Toast.LENGTH_SHORT).show();
                DistributionGoodsBean goodsBean = list.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("title", goodsBean.getGoodsName());
                bundle.putString("pic", goodsBean.getImageUrl());
                bundle.putString("url","1" );
                bundle.putString("context", goodsBean.getGoodsName());
                bundle.putString("id", "1");
                bundle.putString("Shapetype", "1");
                startActivity(ToShareAty.class, bundle);
            }
        });
        shopManageOpen_data_gv.setAdapter(adapter);

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
                // TODO 请求接口
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
        shopManageOpen_super_ssrl.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                footerTextView.setText("正在加载...");
                footerImageView.setVisibility(View.GONE);
                footerProgressBar.setVisibility(View.VISIBLE);
                p++;
                // TODO 请求接口
//                shopManageOrdinaryChild_sr_layout.setLoadMore(false);
//                progressBar.setVisibility(View.GONE);
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
