package com.txd.hzj.wjlp.distribution.shopFgt;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.distribution.adapter.ShopExhibitAdapter;
import com.txd.hzj.wjlp.distribution.bean.ExhibitGoodsBean;
import com.txd.hzj.wjlp.distribution.shopAty.ShopExhibit;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者：Zyf
 * 功能描述：小店上货fragment
 * 联系方式：无
 */
public class ShopExhibitFragment extends BaseFgt {
    private static final String ARG_PARAM1 = "param1";

    //积分
    private TextView internal_tv;
    //代金券
    private TextView cash_coupon_tv;
    //销量
    private TextView sales_volume_tv;
    //价格
    private TextView price_tv;

    private SuperSwipeRefreshLayout mSuperSwipeRefreshLayout;

    private RecyclerView exhibit_recyclerView;

    private Context mContext;

    private String redColor = "#ffe71f19";
    private String blgColor = "#ff333333";
    private Drawable selectId;
    private Drawable twoSelectId;
    private Drawable unSelectId;
    private int internalNum = 0;
    private int cashCouponNum = 0;
    private int salesVolumeNum = 0;
    private int priceNum = 0;
    private ShopExhibitAdapter shopExhibitAdapter;
    private List<ExhibitGoodsBean.DataBean.ListBean> datas;

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

    private ExhibitGoodsBean mGoodsBean;

    private ShopExhibit mShopExhibit;

    public static ShopExhibitFragment newInstance(ExhibitGoodsBean data) {
        ShopExhibitFragment fragment = new ShopExhibitFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mShopExhibit = (ShopExhibit) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGoodsBean = (ExhibitGoodsBean) getArguments().getSerializable(ARG_PARAM1);
        }
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_shop_exhibit;
    }

    @Override
    protected void getView(View view) {
        super.getView(view);
        internal_tv = view.findViewById(R.id.internal_tv);
        cash_coupon_tv = view.findViewById(R.id.cash_coupon_tv);
        sales_volume_tv = view.findViewById(R.id.sales_volume_tv);
        price_tv = view.findViewById(R.id.price_tv);
        mSuperSwipeRefreshLayout = view.findViewById(R.id.super_refreshLayout);
        mSuperSwipeRefreshLayout.setHeaderView(createHeaderView());// add headerView
        mSuperSwipeRefreshLayout.setFooterView(createFooterView());
        mSuperSwipeRefreshLayout.setHeaderViewBackgroundColor(Color.WHITE);
        mSuperSwipeRefreshLayout.setTargetScrollWithLayout(true);
        exhibit_recyclerView = view.findViewById(R.id.exhibit_recyclerView);
        exhibit_recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        exhibit_recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void initialized() {
        selectId = getResources().getDrawable(R.drawable.shopjiantou);
        twoSelectId = getResources().getDrawable(R.drawable.shop_red_down);
        unSelectId = getResources().getDrawable(R.drawable.shopblgjiantou);
        selectId.setBounds(0, 0, selectId.getMinimumWidth(), selectId.getMinimumHeight());
        twoSelectId.setBounds(0, 0, selectId.getMinimumWidth(), selectId.getMinimumHeight());
        unSelectId.setBounds(0, 0, unSelectId.getMinimumWidth(), unSelectId.getMinimumHeight());
    }

    @Override
    protected void requestData() {
        datas = new ArrayList<>();
        datas = mGoodsBean.getData().getList();
        if (datas!=null){
            shopExhibitAdapter = new ShopExhibitAdapter(datas);
            exhibit_recyclerView.setAdapter(shopExhibitAdapter);
        }
        mSuperSwipeRefreshLayout.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                textView.setText("正在刷新");
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                p = 1;
                mShopExhibit.refreshOrLoadMore(p,mSuperSwipeRefreshLayout,progressBar);

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
        mSuperSwipeRefreshLayout.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                footerTextView.setText("正在加载...");
                footerImageView.setVisibility(View.GONE);
                footerProgressBar.setVisibility(View.VISIBLE);
                p++;
                mShopExhibit.refreshOrLoadMore(p,mSuperSwipeRefreshLayout,progressBar);

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        //set false when finished
                        showErrorTip("无更多数据了");
                        mSuperSwipeRefreshLayout.setLoadMore(false);
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

    /**
     * 创建底部加载布局
     *
     * @return
     */
    private View createFooterView() {
        View footerView = LayoutInflater.from(mSuperSwipeRefreshLayout.getContext()).inflate(R.layout.layout_footer, null);
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
        View headerView = LayoutInflater.from(mSuperSwipeRefreshLayout.getContext()).inflate(R.layout.layout_head, null);
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

    @Override
    protected void immersionInit() {

    }

    @Override
    @OnClick({R.id.internal_tv, R.id.cash_coupon_tv, R.id.sales_volume_tv, R.id.price_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.internal_tv:
                setChioceItem(0);
                break;
            case R.id.cash_coupon_tv:
                setChioceItem(1);
                break;
            case R.id.sales_volume_tv:
                setChioceItem(2);
                break;
            case R.id.price_tv:
                setChioceItem(3);
                break;
        }
    }

    private void setChioceItem(int index) {
        clearChioce();
        switch (index) {
            case 0:
                internal_tv.setTextColor(Color.parseColor(redColor));
                internal_tv.setCompoundDrawables(null, null, internalNum % 2 == 0 ? selectId : twoSelectId, null);
                internalNum++;
                cashCouponNum = 0;
                salesVolumeNum = 0;
                priceNum = 0;
                break;
            case 1:
                cash_coupon_tv.setTextColor(Color.parseColor(redColor));
                cash_coupon_tv.setCompoundDrawables(null, null, cashCouponNum % 2 == 0 ? selectId : twoSelectId, null);
                internalNum = 0;
                cashCouponNum++;
                salesVolumeNum = 0;
                priceNum = 0;
                break;
            case 2:
                sales_volume_tv.setTextColor(Color.parseColor(redColor));
                sales_volume_tv.setCompoundDrawables(null, null, salesVolumeNum % 2 == 0 ? selectId : twoSelectId, null);
                internalNum = 0;
                cashCouponNum = 0;
                salesVolumeNum++;
                priceNum = 0;
                break;
            case 3:
                price_tv.setTextColor(Color.parseColor(redColor));
                price_tv.setCompoundDrawables(null, null, priceNum % 2 == 0 ? selectId : twoSelectId, null);
                internalNum = 0;
                cashCouponNum = 0;
                salesVolumeNum = 0;
                priceNum++;
                break;
        }
    }

    private void clearChioce() {
        internal_tv.setTextColor(Color.parseColor(blgColor));
        internal_tv.setCompoundDrawables(null, null, unSelectId, null);
        cash_coupon_tv.setTextColor(Color.parseColor(blgColor));
        cash_coupon_tv.setCompoundDrawables(null, null, unSelectId, null);
        sales_volume_tv.setTextColor(Color.parseColor(blgColor));
        sales_volume_tv.setCompoundDrawables(null, null, unSelectId, null);
        price_tv.setTextColor(Color.parseColor(blgColor));
        price_tv.setCompoundDrawables(null, null, unSelectId, null);
    }

}
