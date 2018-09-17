package com.txd.hzj.wjlp.mellonLine.gridClassify.prebuy;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.ListUtils;
import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.commodity.AllGoodsBean;
import com.txd.hzj.wjlp.http.country.CountryPst;
import com.txd.hzj.wjlp.http.integral.IntegralBuyPst;
import com.txd.hzj.wjlp.http.prebuy.PerBuyPst;
import com.txd.hzj.wjlp.http.ticketbuy.TicketBuyPst;
import com.txd.hzj.wjlp.mainfgt.adapter.AllGvLvAdapter;
import com.txd.hzj.wjlp.mellonLine.adapter.WjMellAdapter;
import com.txd.hzj.wjlp.mellonLine.gridClassify.LimitGoodsAty;
import com.txd.hzj.wjlp.mellonLine.gridClassify.TicketGoodsDetialsAty;
import com.txd.hzj.wjlp.view.VpSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/9/8 0008
 * 时间：13:56
 * 描述：无界预购三积分了商品列表碎片
 */

public class PreBuyThirdFgt extends BaseFgt {
    private String two = "";
    private String three = "";

    private int type = 0;
    // Header View
    private RelativeLayout head_container;
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;
    @ViewInject(R.id.pr_third_lv)
    private GridViewForScrollView pr_third_lv;
    @ViewInject(R.id.refresh_views)
    private VpSwipeRefreshLayout refresh_views;

    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;

    private List<AllGoodsBean> data;
    private List<AllGoodsBean> data2;
    /**
     * 无界预购
     */
    private PerBuyPst perBuyPst;
    /**
     * 票券区
     */
    private TicketBuyPst ticketBuyPst;
    /**
     * 积分商店
     */
    private IntegralBuyPst integralBuyPst;
    /**
     * 进口馆
     */
    private CountryPst countryPst;
    private int p = 1;
    private AllGvLvAdapter allGvLvAdapter1;
    private WjMellAdapter wjAdapter;

    private String country_id = "";

    public PreBuyThirdFgt() {
    }

    public static PreBuyThirdFgt getFgt(String two, String three, int type, String country_id) {
        PreBuyThirdFgt subClassifyListFgt = new PreBuyThirdFgt();
        subClassifyListFgt.two = two;
        subClassifyListFgt.three = three;
        subClassifyListFgt.type = type;
        subClassifyListFgt.country_id = country_id;
        return subClassifyListFgt;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        pr_third_lv.setEmptyView(no_data_layout);
        refresh_views.setHeaderView(createHeaderView());// add headerView
        refresh_views.setFooterView(createFooterView());
        refresh_views.setHeaderViewBackgroundColor(Color.WHITE);
        refresh_views.setTargetScrollWithLayout(true);
        refresh_views.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                textView.setText("正在刷新");
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                p = 1;
                forData();
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
        refresh_views.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                footerTextView.setText("正在加载...");
                footerImageView.setVisibility(View.GONE);
                footerProgressBar.setVisibility(View.VISIBLE);
                p++;
                forData();
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

        pr_third_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                switch (type) {
                    case 1:// 票券区
                        bundle.putString("ticket_buy_id", data.get(i).getTicket_buy_id());
                        startActivity(TicketGoodsDetialsAty.class, bundle);
                        break;
                    case 2:// 无界预购
                        bundle.putString("limit_buy_id", data.get(i).getPre_buy_id());
                        bundle.putInt("type", 2);
                        startActivity(LimitGoodsAty.class, bundle);
                        break;
                    case 10:// 积分商店
                        bundle.putString("limit_buy_id", data.get(i).getIntegral_buy_id());
                        bundle.putInt("type", 10);
                        startActivity(LimitGoodsAty.class, bundle);
                        break;
                    case 3:// 进口馆
                        bundle.putString("ticket_buy_id", data.get(i).getGoods_id());
                        bundle.putInt("from", 1);
                        startActivity(TicketGoodsDetialsAty.class, bundle);
                        break;
                }
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_pre_buy_third;
    }

    @Override
    protected void initialized() {
        data = new ArrayList<>();
        data2 = new ArrayList<>();
        perBuyPst = new PerBuyPst(this);
        ticketBuyPst = new TicketBuyPst(this);
        integralBuyPst = new IntegralBuyPst(this);
        countryPst = new CountryPst(this);
    }

    @Override
    protected void requestData() {
        forData();
    }

    private void forData() {
        switch (type) {
            case 1:// 票券区
                ticketBuyPst.threeList(two, three, p);
                break;
            case 2:// 无界预购
                perBuyPst.threeList(two, p, three);
                break;
            case 3:// 进口馆
                countryPst.threeList(two, country_id, p, three);
                break;
            case 10:// 无界预购
                integralBuyPst.threeList(two, three, p);
                break;
        }
        //progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void immersionInit() {

    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("threeList")) {
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            Map<String, String> datajson = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            L.e("=====数据=====", jsonStr);
            if (1 == p) {
                switch (type) {
                    case 1:// 票券区
                        data = GsonUtil.getObjectList(datajson.get("ticket_buy_list"), AllGoodsBean.class);
                        break;
                    case 2:// 无界预购
                        data = GsonUtil.getObjectList(datajson.get("pre_buy_list"), AllGoodsBean.class);
                        break;
                    case 3:// 进口馆
                        data = GsonUtil.getObjectList(datajson.get("list"), AllGoodsBean.class);
                        break;
                    case 10:// 积分商店
                        data = GsonUtil.getObjectList(datajson.get("integral_buy_list"), AllGoodsBean.class);
                        break;
                }
                if (!ListUtils.isEmpty(data)) {
                    L.e("Fgt=====type=====", String.valueOf(type));
                    if (10 == type) {
                        wjAdapter = new WjMellAdapter(getContext(), data);
                        pr_third_lv.setAdapter(wjAdapter);
                    } else {
                        allGvLvAdapter1 = new AllGvLvAdapter(getActivity(), data, type);
                        pr_third_lv.setAdapter(allGvLvAdapter1);
                    }
                }
            } else {
                switch (type) {
                    case 1:// 票券区
                        data2 = GsonUtil.getObjectList(datajson.get("ticket_buy_list"), AllGoodsBean.class);
                        break;
                    case 2:// 无界预购
                        data2 = GsonUtil.getObjectList(datajson.get("pre_buy_list"), AllGoodsBean.class);
                        break;
                    case 3:// 进口馆
                        data = GsonUtil.getObjectList(datajson.get("list"), AllGoodsBean.class);
                        break;
                    case 10:// 积分商店
                        data2 = GsonUtil.getObjectList(datajson.get("integral_buy_list"), AllGoodsBean.class);
                        break;
                }
                if (!ListUtils.isEmpty(data2)) {
                    data.addAll(data2);
                    if (10 == type) {
                        wjAdapter.notifyDataSetChanged();
                    } else {
                        allGvLvAdapter1.notifyDataSetChanged();
                    }
                }
            }
            progressBar.setVisibility(View.GONE);
            refresh_views.setLoadMore(false);
            footerImageView.setVisibility(View.GONE);
            footerProgressBar.setVisibility(View.VISIBLE);
            refresh_views.setRefreshing(false);
        }
    }

    private View createFooterView() {
        View footerView = LayoutInflater.from(refresh_views.getContext()).inflate(R.layout.layout_footer, null);
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
        View headerView = LayoutInflater.from(refresh_views.getContext()).inflate(R.layout.layout_head, null);
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
