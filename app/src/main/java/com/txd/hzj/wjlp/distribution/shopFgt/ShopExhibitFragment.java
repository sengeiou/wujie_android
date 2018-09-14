package com.txd.hzj.wjlp.distribution.shopFgt;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.ants.theantsgo.util.PreferencesUtils;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.distribution.adapter.ShopExhibitAdapter;
import com.txd.hzj.wjlp.distribution.bean.ExhibitGoodsBean;
import com.txd.hzj.wjlp.distribution.presenter.ShopExhibitPst;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 创建者：Zyf
 * 功能描述：小店上货fragment
 * 联系方式：无
 */
public class ShopExhibitFragment extends BaseFgt {
    private static final String ARG_PARAM1 = "param1";

    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;

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


    private ShopExhibitPst mExhibitPst;
    private int p = 1; // 请求的分页
    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;



    private int mCate_id;
    //排序字段名
    private String name="red_return_integral";
    //
    private String flag="asc";

    private int pos=0;
    private String mShop_id;


    public static ShopExhibitFragment newInstance(int cate_id) {
        ShopExhibitFragment fragment = new ShopExhibitFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, cate_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCate_id = getArguments().getInt(ARG_PARAM1);
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
        mExhibitPst = new ShopExhibitPst(this);
        setChioceItem(0);
        mSuperSwipeRefreshLayout.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                textView.setText("正在刷新");
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                p = 1;
                mExhibitPst.goodsList(String.valueOf(p), String.valueOf(mCate_id),name,flag,0);
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
                mExhibitPst.goodsList(String.valueOf(p), String.valueOf(mCate_id),name,flag,0);

//                new Handler().postDelayed(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        //set false when finished
//                        showErrorTip("无更多数据了");
//                        mSuperSwipeRefreshLayout.setLoadMore(false);
//                        progressBar.setVisibility(View.GONE);
//                    }
//                }, 5000);
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
    protected void initialized() {
        selectId = getResources().getDrawable(R.drawable.shopjiantou);
        twoSelectId = getResources().getDrawable(R.drawable.shop_red_down);
        unSelectId = getResources().getDrawable(R.drawable.shopblgjiantou);
        selectId.setBounds(0, 0, selectId.getMinimumWidth(), selectId.getMinimumHeight());
        twoSelectId.setBounds(0, 0, selectId.getMinimumWidth(), selectId.getMinimumHeight());
        unSelectId.setBounds(0, 0, unSelectId.getMinimumWidth(), unSelectId.getMinimumHeight());
        datas = new ArrayList<>();
        if (PreferencesUtils.containKey(mContext, "shop_id")) {
            mShop_id = PreferencesUtils.getString(mContext, "shop_id");
        }
    }

    @Override
    protected void requestData() {
        mExhibitPst.goodsList(String.valueOf(p),String.valueOf(mCate_id),"red_return_integral","desc",0);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("goodsList")){
            ExhibitGoodsBean exhibitGoodsBean = JSONObject.parseObject(jsonStr, ExhibitGoodsBean.class);
            if (200 == exhibitGoodsBean.getCode()) {
                if (1==p){
                    datas = exhibitGoodsBean.getData().getList();
                    if (datas != null && datas.size()>0) {
                        shopExhibitAdapter = new ShopExhibitAdapter(datas);
                        exhibit_recyclerView.setAdapter(shopExhibitAdapter);
                        shopExhibitAdapter.setButtonClickListener(new ShopExhibitAdapter.OnButtonClickListener() {
                            @Override
                            public void buttonClick(int position) {
                                pos=position;
                                mExhibitPst.shopExhibitGoods(mShop_id,datas.get(position).getGoods_id(),"0","0","0");
                            }
                        });
                    }else {
                        mSuperSwipeRefreshLayout.setVisibility(View.GONE);
                        no_data_layout.setVisibility(View.VISIBLE);
                    }
                }else {
                    datas.addAll(exhibitGoodsBean.getData().getList());
                    shopExhibitAdapter.notifyDataSetChanged();
                }

            }
        }

        if (requestUrl.endsWith("goods")){
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            if (jsonObject.containsKey("code") && "200".equals(jsonObject.getString("code"))) {
                if (jsonObject.containsKey("message")) {
                    showToast(jsonObject.getString("message"));
                    shopExhibitAdapter.notifyData(pos);
                }
            }
        }

        refreshVisibleState();
    }


    private void refreshVisibleState() {
        if (progressBar.getVisibility()== View.VISIBLE){
            mSuperSwipeRefreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
        }
        if (footerProgressBar.getVisibility()==View.VISIBLE) {
            mSuperSwipeRefreshLayout.setLoadMore(false);
            footerProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        refreshVisibleState();
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
        int id = v.getId();
        if (id == R.id.internal_tv) {
            sort(0);
            setChioceItem(0);
        } else if (id == R.id.cash_coupon_tv) {
            sort(1);
            setChioceItem(1);
        } else if (id == R.id.sales_volume_tv) {
            sort(2);
            setChioceItem(2);
        } else if (id == R.id.price_tv) {
            sort(3);
            setChioceItem(3);
        }
    }

    private void sort(int index){
        if (index==0){
            name="red_return_integral";
            flag=internalNum%2==0?"asc":"desc";
            mExhibitPst.goodsList("1",String.valueOf(mCate_id),"red_return_integral",internalNum%2==0?"asc":"desc",0);
        }else  if (index==1){
            name="discount";
            flag=cashCouponNum%2==0?"asc":"desc";
            mExhibitPst.goodsList("1",String.valueOf(mCate_id),"discount",cashCouponNum%2==0?"asc":"desc",0);
        } else  if (index==2){
            name="new_sell_num";
            flag=salesVolumeNum%2==0?"asc":"desc";
            mExhibitPst.goodsList("1",String.valueOf(mCate_id),"new_sell_num",salesVolumeNum%2==0?"asc":"desc",0);
        }else  if (index==3){
            name="shop_price";
            flag=priceNum%2==0?"asc":"desc";
            mExhibitPst.goodsList("1",String.valueOf(mCate_id),"shop_price",priceNum%2==0?"asc":"desc",0);
        }
    }

    private void setChioceItem(int index) {
        clearChioce();
        if (index == 0) {
            internal_tv.setTextColor(Color.parseColor(redColor));
            internal_tv.setCompoundDrawables(null, null, internalNum % 2 == 0 ? selectId : twoSelectId, null);
            internalNum++;
            cashCouponNum = 0;
            salesVolumeNum = 0;
            priceNum = 0;
        } else if (index == 1) {
            cash_coupon_tv.setTextColor(Color.parseColor(redColor));
            cash_coupon_tv.setCompoundDrawables(null, null, cashCouponNum % 2 == 0 ? selectId : twoSelectId, null);

            internalNum = 0;
            cashCouponNum++;
            salesVolumeNum = 0;
            priceNum = 0;
        } else if (index == 2) {
            sales_volume_tv.setTextColor(Color.parseColor(redColor));
            sales_volume_tv.setCompoundDrawables(null, null, salesVolumeNum % 2 == 0 ? selectId : twoSelectId, null);
            internalNum = 0;
            cashCouponNum = 0;
            salesVolumeNum++;
            priceNum = 0;
        } else if (index == 3) {
            price_tv.setTextColor(Color.parseColor(redColor));
            price_tv.setCompoundDrawables(null, null, priceNum % 2 == 0 ? selectId : twoSelectId, null);
            internalNum = 0;
            cashCouponNum = 0;
            salesVolumeNum = 0;
            priceNum++;
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
