package com.txd.hzj.wjlp.mellonLine.fgt;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.PreferencesUtils;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.CFGoodsList;
import com.txd.hzj.wjlp.http.goods.GoodsPst;
import com.txd.hzj.wjlp.mainfgt.adapter.HorizontalAdapter;
import com.txd.hzj.wjlp.mainfgt.adapter.RacycleAllAdapter;
import com.txd.hzj.wjlp.mellonLine.gridClassify.TicketGoodsDetialsAty;
import com.txd.hzj.wjlp.tool.GridDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/5 0005
 * 时间：下午 4:09
 * 描述：1-1-2二级分类
 */
public class SubClassifyListFgt extends BaseFgt {

    //积分
    @ViewInject(R.id.internal_tv)
    private TextView internal_tv;
    //代金券
    @ViewInject(R.id.cash_coupon_tv)
    private TextView cash_coupon_tv;
    //销量
    @ViewInject(R.id.sales_volume_tv)
    private TextView sales_volume_tv;
    //价格
    @ViewInject(R.id.price_tv)
    private TextView price_tv;

    @ViewInject(R.id.pop_search_layout)
    private LinearLayout pop_search_layout;

    @ViewInject(R.id.lower_et)
    private EditText lower_et;

    @ViewInject(R.id.higher_et)
    private EditText higher_et;

    @ViewInject(R.id.lower_tv)
    private TextView lower_tv;

    @ViewInject(R.id.higher_tv)
    private TextView higher_tv;

    @ViewInject(R.id.cancel_tv)
    private TextView cancel_tv;

    @ViewInject(R.id.sure_tv)
    private TextView sure_tv;


    @ViewInject(R.id.bg_view)
    private View bg_view;


    @ViewInject(R.id.su_classify_goods_rv)
    private RecyclerView su_classify_goods_rv;

    private RacycleAllAdapter racycleAllAdapter;

    private int height = 0;

    private String two = "";
    private String three = "";

    @ViewInject(R.id.sup_sub_layout)
    private SuperSwipeRefreshLayout swipe_refresh;

    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;

    /**
     * 是不是第一次进入
     */
    private boolean frist = true;
    private int p = 1;
    private GoodsPst goodsPst;
    private List<CFGoodsList> goodsLists;
    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;

    private String redColor = "#ffe71f19";
    private String blgColor = "#ff333333";
    private Drawable selectId;
    private Drawable twoSelectId;
    private Drawable unSelectId;
    private int priceNum = 0;
    //按销量从高到底排序
    private String sell = "";
    //按用券比例从高到底排序
    private String tsort = "";
    //按可返积分从高到底排序
    private String integral = "";
    //psort=1按价格从底到高，psort=2按价格从高到底
    private String psort = "";
    //价格区间内检索商品，格式是 开始金额_结束金额 ，两个价格中间用下划线链接
    private String price = "";

    public static SubClassifyListFgt getFgt(String two, String three) {
        SubClassifyListFgt subClassifyListFgt = new SubClassifyListFgt();
        subClassifyListFgt.two = two;
        subClassifyListFgt.three = three;
        return subClassifyListFgt;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        selectId = getResources().getDrawable(R.drawable.shopjiantou);
        twoSelectId = getResources().getDrawable(R.drawable.shop_red_down);
        unSelectId = getResources().getDrawable(R.drawable.shopblgjiantou);
        selectId.setBounds(0, 0, selectId.getMinimumWidth(), selectId.getMinimumHeight());
        twoSelectId.setBounds(0, 0, selectId.getMinimumWidth(), selectId.getMinimumHeight());
        unSelectId.setBounds(0, 0, unSelectId.getMinimumWidth(), unSelectId.getMinimumHeight());
        if (PreferencesUtils.containKey(getActivity(),"sub_lower")) {
            lower_et.setText(PreferencesUtils.getInt(getActivity(), "sub_lower",0)+"");
        }
        if (PreferencesUtils.containKey(getActivity(),"sub_higher")) {
            higher_et.setText(PreferencesUtils.getInt(getActivity(), "sub_higher",0)+"");
        }
        lower_et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                lower_et.setCursorVisible(true);
                return false;
            }
        });
        higher_et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                higher_et.setCursorVisible(true);
                return false;
            }
        });

        su_classify_goods_rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        su_classify_goods_rv.setItemAnimator(new DefaultItemAnimator());
        su_classify_goods_rv.setHasFixedSize(true);
        su_classify_goods_rv.addItemDecoration(new GridDividerItemDecoration(height, Color.parseColor("#F6F6F6")));
        su_classify_goods_rv.setNestedScrollingEnabled(false);


        swipe_refresh.setHeaderViewBackgroundColor(Color.WHITE);
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
                        goodsPst.threeList(two, three, p, sell, tsort, integral, psort, price,0);
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
                        goodsPst.threeList(two, three, p, sell, tsort, integral, psort, price,0);
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


    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("threeList")) {
            if (ToolKit.isList(map, "data")) {
                Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
                if (1 == p) {
                    if (ToolKit.isList(data, "list")) {
                        goodsLists = GsonUtil.getObjectList(data.get("list"), CFGoodsList.class);
                        racycleAllAdapter = new RacycleAllAdapter(getActivity(), goodsLists);
                        su_classify_goods_rv.setAdapter(racycleAllAdapter);
                        racycleAllAdapter.setListener(new HorizontalAdapter.OnItemClickLitener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Bundle bundle = new Bundle();
                                bundle.putString("ticket_buy_id", goodsLists.get(position).getGoods_id());
                                bundle.putInt("from", 1);
                                startActivity(TicketGoodsDetialsAty.class, bundle);
                            }
                        });
                    } else {
                        su_classify_goods_rv.setVisibility(View.GONE);
                        no_data_layout.setVisibility(View.VISIBLE);
                    }
                    if (!frist) {
                        swipe_refresh.setRefreshing(false);
                        progressBar.setVisibility(View.GONE);
                    }
                } else {

                    if (ToolKit.isList(data, "list")) {
                        goodsLists.addAll(GsonUtil.getObjectList(data.get("list"), CFGoodsList.class));
                        racycleAllAdapter.notifyDataSetChanged();
                    }

                    footerImageView.setVisibility(View.VISIBLE);
                    footerProgressBar.setVisibility(View.GONE);
                    swipe_refresh.setLoadMore(false);
                }
            } else {
                if (1 == p) {
                    if (!frist) {
                        swipe_refresh.setRefreshing(false);
                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    footerImageView.setVisibility(View.VISIBLE);
                    footerProgressBar.setVisibility(View.GONE);
                    swipe_refresh.setLoadMore(false);
                }
            }
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_sub_classify_list;
    }

    @Override
    protected void initialized() {
        height = ToolKit.dip2px(getActivity(), 4);
        goodsLists = new ArrayList<>();
        goodsPst = new GoodsPst(this);
    }

    @Override
    protected void requestData() {
        goodsPst.threeList(two, three, p, sell, tsort, integral, psort, price,1);
    }

    @Override
    protected void immersionInit() {

    }


    @SuppressLint("ResourceAsColor")
    @Override
    @OnClick({ R.id.internal_tv, R.id.cash_coupon_tv, R.id.sales_volume_tv, R.id.price_tv, R.id.lower_tv, R.id.higher_tv, R.id.cancel_tv, R.id.sure_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.internal_tv:
                p=1;
                setChioceItem(0);
                if (pop_search_layout.getVisibility() == View.VISIBLE) {
                    bg_view.setVisibility(View.GONE);
                    pop_search_layout.setVisibility(View.GONE);
                    pop_search_layout.setFocusable(false);
                }
                break;
            case R.id.cash_coupon_tv:
                p=1;
                setChioceItem(1);
                if (pop_search_layout.getVisibility() == View.VISIBLE) {
                    bg_view.setVisibility(View.GONE);
                    pop_search_layout.setVisibility(View.GONE);
                    pop_search_layout.setFocusable(false);
                }
                break;
            case R.id.sales_volume_tv:
                p=1;
                setChioceItem(2);
                if (pop_search_layout.getVisibility() == View.VISIBLE) {
                    bg_view.setVisibility(View.GONE);
                    pop_search_layout.setVisibility(View.GONE);
                    pop_search_layout.setFocusable(false);
                }
                break;
            case R.id.price_tv:
                setChioceItem(3);
                if (pop_search_layout.getVisibility() == View.GONE) {
                    bg_view.setVisibility(View.VISIBLE);
                    bg_view.setClickable(true);
                    bg_view.setFocusableInTouchMode(true);
                    pop_search_layout.setVisibility(View.VISIBLE);
                    pop_search_layout.setFocusable(true);
                    TranslateAnimation animation  = new TranslateAnimation(0, 0, 0, 0.5f);
                    animation.setDuration(200);
                    pop_search_layout.startAnimation(animation);
                } else if (pop_search_layout.getVisibility() == View.VISIBLE) {
                    bg_view.setVisibility(View.GONE);
                    pop_search_layout.setVisibility(View.GONE);
                    pop_search_layout.setFocusable(false);
                }
                break;
            case R.id.lower_tv:
                p=1;
                priceNum=1;
                price_tv.setCompoundDrawables(null, null,selectId, null);
                clearTv();
                lower_tv.setTextColor(Color.parseColor("#FE666A"));
                lower_tv.setBackgroundResource(R.drawable.shape_red_pop);
                sell = "";
                tsort = "";
                integral = "";
                psort = "1";
                price = "";
                requestData();
                if (pop_search_layout.getVisibility() == View.VISIBLE) {
                    bg_view.setVisibility(View.GONE);
                    pop_search_layout.setVisibility(View.GONE);
                    pop_search_layout.setFocusable(false);
                }
                break;
            case R.id.higher_tv:
                p=1;
                priceNum=2;
                price_tv.setCompoundDrawables(null, null,twoSelectId, null);
                clearTv();
                higher_tv.setTextColor(Color.parseColor("#FE666A"));
                higher_tv.setBackgroundResource(R.drawable.shape_red_pop);
                sell = "";
                tsort = "";
                integral = "";
                psort = "2";
                price = "";
                requestData();
                if (pop_search_layout.getVisibility() == View.VISIBLE) {
                    bg_view.setVisibility(View.GONE);
                    pop_search_layout.setVisibility(View.GONE);
                    pop_search_layout.setFocusable(false);
                }
                break;
            case R.id.cancel_tv:
                if (pop_search_layout.getVisibility() == View.VISIBLE) {
                    bg_view.setVisibility(View.GONE);
                    pop_search_layout.setVisibility(View.GONE);
                    pop_search_layout.setFocusable(false);
                }
                break;
            case R.id.sure_tv:
                p=1;
                priceNum=0;
                clearTv();
                price_tv.setCompoundDrawables(null, null, unSelectId, null);
                String lower = lower_et.getText().toString();
                String higher=higher_et.getText().toString();
                if (TextUtils.isEmpty(lower)){
                    lower="0";
                }
                if (TextUtils.isEmpty(higher)){
                    higher="0";
                }
                int low = Integer.parseInt(lower);
                int high = Integer.parseInt(higher);
                if (low>high){
                    showToast("输入有误，最低价不能高于最高价");
                    return;
                }

                PreferencesUtils.putInt(getActivity(),"sub_lower",low);
                PreferencesUtils.putInt(getActivity(),"sub_higher",high);
                sell = "";
                tsort = "";
                integral = "";
                psort = "";
                price = low+"_"+high;
                requestData();
                if (pop_search_layout.getVisibility() == View.VISIBLE) {
                    bg_view.setVisibility(View.GONE);
                    pop_search_layout.setVisibility(View.GONE);
                    pop_search_layout.setFocusable(false);
                }
                break;
        }
    }

    private void clearTv() {
        lower_tv.setTextColor(getResources().getColor(R.color.hint_text_color));
        lower_tv.setBackgroundResource(R.drawable.gr_item_back);
        higher_tv.setTextColor(getResources().getColor(R.color.hint_text_color));
        higher_tv.setBackgroundResource(R.drawable.gr_item_back);
    }

    private void setChioceItem(int index) {
        clearChioce();
        if (index == 0) {
            internal_tv.setTextColor(Color.parseColor(redColor));
            sell = "";
            tsort = "";
            integral = "1";
            psort = "";
            price = "";
            requestData();
        } else if (index == 1) {
            cash_coupon_tv.setTextColor(Color.parseColor(redColor));
            sell = "";
            tsort = "1";
            integral = "";
            psort = "";
            price = "";
            requestData();
        } else if (index == 2) {
            sales_volume_tv.setTextColor(Color.parseColor(redColor));
            sell = "1";
            tsort = "";
            integral = "";
            psort = "";
            price = "";
            requestData();
        } else if (index == 3) {
            price_tv.setTextColor(Color.parseColor(redColor));
            if (priceNum==1){
                price_tv.setCompoundDrawables(null, null,selectId, null);
            }else if (priceNum==2){
                price_tv.setCompoundDrawables(null, null,twoSelectId, null);
            }

        }
    }

    private void clearChioce() {
        internal_tv.setTextColor(Color.parseColor(blgColor));
        cash_coupon_tv.setTextColor(Color.parseColor(blgColor));
        sales_volume_tv.setTextColor(Color.parseColor(blgColor));
        price_tv.setTextColor(Color.parseColor(blgColor));
        price_tv.setCompoundDrawables(null, null, unSelectId, null);
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
