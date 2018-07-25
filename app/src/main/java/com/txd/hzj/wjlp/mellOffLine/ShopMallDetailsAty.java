package com.txd.hzj.wjlp.mellOffLine;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.ShopOffLineBean;
import com.txd.hzj.wjlp.bean.offline.OffLineDataBean;
import com.txd.hzj.wjlp.http.offlineStoreInfo.StoreInfoPst;
import com.txd.hzj.wjlp.mainFgt.Constant;
import com.txd.hzj.wjlp.mainFgt.Pranster;
import com.txd.hzj.wjlp.mellOffLine.adapter.ShopEvaluateAdapter;
import com.txd.hzj.wjlp.tool.GlideImageLoader;
import com.txd.hzj.wjlp.tool.TextUtils;
import com.txd.hzj.wjlp.view.VpSwipeRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 创建者：Qyl
 * 创建时间：2018/7/23 0023 14:18
 * 功能描述：线下店铺详情页面
 * 联系方式：无
 */
public class ShopMallDetailsAty extends BaseAty implements View.OnClickListener, Constant.View {
    /**
     * 刷新加载控件
     */
    private VpSwipeRefreshLayout mallRefrsh;
    /**
     * 轮播图控件
     */
    private Banner bannerIma;

    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;
    //图片集合
    private ArrayList urls;
    //评价列表
    private RecyclerView shopEvaluate;
    //详情接口
    private StoreInfoPst pst;
    //商品数量
    private TextView shopGoddsNumber;
    //月销单量
    private TextView monthOrderNumber;
    //关注人数
    private TextView shopFollowPersons;
    //营业时间
    private TextView shopBusinessHours;
    //门店地址
    private TextView shopAddress;
    //门店电话
    private TextView shopTelephone;
    //查看商家资质
    private RelativeLayout shopBusinessAptitude;
    //举报商家
    private RelativeLayout shopReportBusiness;
    //我要结账
    private TextView mySettleAccounts;
    //title名字
    private TextView titleName;
    private TextView titleCollect;
    //title分享
    private TextView titleShare;
    //商家id
    private String s_id;
    //评价列表数据
    private List<ShopOffLineBean.DataBean.CommentBean.ListBean> listBeanList;
    //评价列表适配器
    private ShopEvaluateAdapter adapter;
    //评价数量
    private TextView evaluateNumbers;
    //评价评分
    private TextView evaluateBranch;
    private ListView nearbyBusinessList;
    private int page;
    private Pranster pranster;
    private OffLineDataBean mellInfo;


    @Override
    protected int getLayoutResId() {
        return R.layout.aty_down_shopmall_details;
    }

    @Override
    protected void initialized() {
        Intent intent = getIntent();
        mellInfo = (OffLineDataBean) intent.getSerializableExtra("mellInfo");
        s_id = mellInfo.getS_id();

        urls = new ArrayList();

        mallRefrsh = findViewById(R.id.shop_mall_refrsh);
        bannerIma = findViewById(R.id.banner);
        shopEvaluate = findViewById(R.id.shop_mall_evaluate_list);
        shopGoddsNumber = findViewById(R.id.shop_goods_number);
        monthOrderNumber = findViewById(R.id.shop_month_order_number);
        shopFollowPersons = findViewById(R.id.shop_follow_persons);
        shopBusinessHours = findViewById(R.id.shop_business_hours);
        shopAddress = findViewById(R.id.shop_address);
        shopTelephone = findViewById(R.id.shop_tele_phone);
        shopBusinessAptitude = findViewById(R.id.shop_business_aptitude);
        shopReportBusiness = findViewById(R.id.shop_Report_business);
        mySettleAccounts = findViewById(R.id.shop_my_settle_accounts);
        titleName = findViewById(R.id.aty_down_title_name);
        titleCollect = findViewById(R.id.goods_title_collect_tv);
        titleShare = findViewById(R.id.goods_title_share_tv);
        evaluateNumbers = findViewById(R.id.shop_evaluate_numbers);
        evaluateBranch = findViewById(R.id.shop_evaluate_branch);
        nearbyBusinessList = findViewById(R.id.shop_nearby_business_list);
        pranster = new Pranster();
        pranster.setView(this);
        page = 1;


        //设置图片加载器
        bannerIma.setImageLoader(new GlideImageLoader());
        //设置样式
        bannerIma.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置动画
        bannerIma.setBannerAnimation(Transformer.DepthPage);
        //设置轮播时间
        bannerIma.setDelayTime(2000);


        //设置下拉刷新上拉加载
        mallRefrsh.setHeaderViewBackgroundColor(Color.WHITE);
        mallRefrsh.setHeaderView(createHeaderView());// add headerView
        mallRefrsh.setFooterView(createFooterView());
        mallRefrsh.setTargetScrollWithLayout(true); // 跟随手指滑动
        mallRefrsh.setOnPullRefreshListener(new VpSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                textView.setText("正在刷新");
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                pst.offlineStoreInfo(s_id);
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
        mallRefrsh.setOnPushLoadMoreListener(new VpSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                footerTextView.setText("正在加载...");
                footerImageView.setVisibility(View.GONE);
                footerProgressBar.setVisibility(View.VISIBLE);
                pst.offlineStoreInfo(s_id);
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

        /**
         * 注册点击事件
         * */
        shopBusinessAptitude.setOnClickListener(this);
        shopReportBusiness.setOnClickListener(this);
        mySettleAccounts.setOnClickListener(this);
        titleCollect.setOnClickListener(this);
        titleShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            //查看商家资质
            case R.id.shop_business_aptitude:

                break;
            //举报商家
            case R.id.shop_Report_business:

                break;
            //我要结账
            case R.id.shop_my_settle_accounts:
                Intent intent = new Intent(ShopMallDetailsAty.this, PaymentAty.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("ShopOffLineBean", ShopOffLineBean.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            //收藏
            case R.id.goods_title_collect_tv:

                break;
            //分享
            case R.id.goods_title_share_tv:
                toShare("无界优品", "pic", "url", "context", "id", "shapetype");
                break;
        }
    }

    @Override
    protected void requestData() {
        pst = new StoreInfoPst(this);
        pst.offlineStoreInfo(s_id);
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        mallRefrsh.setRefreshing(false);
        mallRefrsh.setLoadMore(false);
        progressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.VISIBLE);
        footerProgressBar.setVisibility(View.GONE);
        if (requestUrl.contains("offlineStoreInfo")) {
            Log.i("线下店铺详情页数据", jsonStr.toString());
            Gson gson = new Gson();
            ShopOffLineBean offLineBean = gson.fromJson(jsonStr, ShopOffLineBean.class);
            //设置评价列表数据
            listBeanList = offLineBean.getData().getComment().getList();
            if (listBeanList != null) {
                adapter = new ShopEvaluateAdapter(this, listBeanList);
                shopEvaluate.setLayoutManager(new LinearLayoutManager(this));
                shopEvaluate.setAdapter(adapter);
            }
            //设置titleName
            titleName.setText(offLineBean.getData().getMerchant_name());
            //设置轮播图照片
            urls.clear();
            for (int i = 0; i < offLineBean.getData().getGallery().size(); i++) {
                urls.add(offLineBean.getData().getGallery().get(i).getPath());
            }
            bannerIma.setImages(urls);
            //开始
            bannerIma.start();
            //设置营业时间
            shopBusinessHours.setText(offLineBean.getData().getOpen_time());
            //设置门店电话
            shopTelephone.setText(offLineBean.getData().getMerchant_phone());
            //设置关注人数
            shopFollowPersons.setText(offLineBean.getData().getFocus_num());
            //设置商品数量
            shopGoddsNumber.setText(offLineBean.getData().getGoods_num());
            //设置月单量
            monthOrderNumber.setText(offLineBean.getData().getMonths_orders());
            //设置地址
            shopAddress.setText(offLineBean.getData().getFinal_address());
            //评价数量
            evaluateNumbers.setText("店铺评价(" + offLineBean.getData().getComment().getCount() + ")");
            //综合星级
            evaluateBranch.setText(offLineBean.getData().getComment().getStar_cate() + "分");
            //设置是否收藏
            if ("1".equals(offLineBean.getData().getIs_collect())) {
                titleCollect.setCompoundDrawables(null, TextUtils.toDrawable(this, R.drawable.icon_collect), null, null);
                titleCollect.setText("收藏");
            } else {
                titleCollect.setCompoundDrawables(null, TextUtils.toDrawable(this, R.drawable.icon_collected), null, null);
                titleCollect.setText("已收藏");
            }
            if (mellInfo.getLng() != null & mellInfo.getLat() != null) {
                pranster.requestStoreData(page, mellInfo.getLng(), mellInfo.getLat(), s_id, ShopMallDetailsAty.this, nearbyBusinessList);
            } else {
                pranster.requestStoreData(page, "-1", "-1", "-1", ShopMallDetailsAty.this, nearbyBusinessList);
            }
        }
    }


    private View createFooterView() {
        View footerView = LayoutInflater.from(mallRefrsh.getContext()).inflate(R.layout.layout_footer, null);
        footerProgressBar = footerView.findViewById(R.id.footer_pb_view);
        footerImageView = footerView.findViewById(R.id.footer_image_view);
        footerTextView = footerView.findViewById(R.id.footer_text_view);
        footerProgressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.VISIBLE);
        footerImageView.setImageResource(R.drawable.down_arrow);
        footerTextView.setText("上拉加载更多...");
        return footerView;
    }

    private View createHeaderView() {
        View headerView = LayoutInflater.from(mallRefrsh.getContext()).inflate(R.layout.layout_head, null);
        progressBar = headerView.findViewById(R.id.pb_view);
        textView = headerView.findViewById(R.id.text_view);
        textView.setText("下拉刷新");
        imageView = headerView.findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.down_arrow);
        progressBar.setVisibility(View.GONE);
        return headerView;
    }

    @Override
    protected void onStart() {
        super.onStart();
        bannerIma.startAutoPlay();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (bannerIma != null) {
            bannerIma.stopAutoPlay();
        }
    }


    @Override
    public void onItemClickListener(OffLineDataBean offLineDataBean, int position) {

    }

    @Override
    public void loadComplate() {
        mallRefrsh.setRefreshing(false); // 刷新成功
        footerImageView.setVisibility(View.VISIBLE);
        footerProgressBar.setVisibility(View.GONE);
        mallRefrsh.setLoadMore(false);
    }

    @Override
    public void loadMoreOver() {
        if (page > 1)
            page -= 1;
    }
}
