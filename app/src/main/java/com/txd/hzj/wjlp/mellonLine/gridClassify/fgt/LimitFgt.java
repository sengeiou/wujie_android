package com.txd.hzj.wjlp.mellonLine.gridClassify.fgt;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.listenerForAdapter.AdapterTextViewClickListener;
import com.ants.theantsgo.tool.DateTool;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.LimitBuyBean;
import com.txd.hzj.wjlp.bean.auction.AuctionList;
import com.txd.hzj.wjlp.http.limit.LimitBuyPst;
import com.txd.hzj.wjlp.mellonLine.NoticeDetailsAty;
import com.txd.hzj.wjlp.mellonLine.adapter.LimitAdapter;
import com.txd.hzj.wjlp.mellonLine.gridClassify.LimitGoodsAty;
import com.txd.hzj.wjlp.mellonLine.gridClassify.MellInfoAty;
import com.txd.hzj.wjlp.mellonLine.gridClassify.TicketGoodsDetialsAty;
import com.txd.hzj.wjlp.webviewH5.WebViewAty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.iwgang.countdownview.CountdownView;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/7 0007
 * 时间：下午 2:33
 * 描述：抢购碎片
 */
public class LimitFgt extends BaseFgt implements NestedScrollView.OnScrollChangeListener {

    private int type;
    private String stage_id = "";
    private String str = "";
    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;
    /**
     * 倒计时
     */
    @ViewInject(R.id.limit_count_down_view)
    private CountdownView limit_count_down_view;
    @ViewInject(R.id.limit_gv)
    private GridViewForScrollView limit_gv;

    private LimitAdapter limiAdapter;
    private List<AuctionList> list;
    private List<AuctionList> list2;

    /**
     * 可以监听滚动距离的ScrollView
     */
    @ViewInject(R.id.fgt_limit_sc)
    private NestedScrollView fgt_limit_sc;

    @ViewInject(R.id.top_ad_iv)
    private ImageView top_ad_iv;
    /**
     * 回到顶部
     */
    @ViewInject(R.id.to_be_back_iv)
    private ImageView to_be_back_iv;
    private int height = 0;

    private LimitBuyPst limitBuyPst;
    private int p = 1;

    /**
     * 空视图
     */
    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;

    /**
     * 刷新控件
     */
    @ViewInject(R.id.ptr_layout)
    private SuperSwipeRefreshLayout refresh_view;
    private int numall = -1;

    @ViewInject(R.id.limit_status_tv)
    private TextView limit_status_tv;
    private int operation_select = -1;
    private String href = "";
    private String desc = "";
    private LimitBuyBean limitBuyBean;

    public LimitFgt() {
    }

    public static LimitFgt getFgt(String stage_id, int type) {
        LimitFgt limitFgt = new LimitFgt();
        limitFgt.stage_id = stage_id;
        limitFgt.type = type;
        return limitFgt;
    }

    public static LimitFgt getFgt(String stage_id, String s) {
        LimitFgt limitFgt = new LimitFgt();
        limitFgt.stage_id = stage_id;
        limitFgt.str = s;
        return limitFgt;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        limit_gv.setEmptyView(no_data_layout);
        limit_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("limit_buy_id", list.get(i).getLimit_buy_id());
                bundle.putInt("type", 0);
                startActivity(LimitGoodsAty.class, bundle);
            }
        });
        height = Settings.displayWidth * 400 / 1242;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Settings.displayWidth, height);
        top_ad_iv.setLayoutParams(params);
        fgt_limit_sc.setOnScrollChangeListener(this);
        fgt_limit_sc.smoothScrollTo(0, 0);
        forUpdata();
        limit_status_tv.setText(str);
        if (-1 == type) {
            limit_status_tv.setText("本场已结束");
        } else if (0 == type) {
            limit_status_tv.setText("距离本场结束");
        } else {
            limit_status_tv.setText("距离本场开始");
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            try {
                fgt_limit_sc.smoothScrollTo(0, 0);
            } catch (NullPointerException e) {
                L.e("限量购，视图空指针异常无法滚动到页面顶部");
            }
        }
    }

    @Override
    @OnClick({R.id.to_be_back_iv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            //R.id.top_ad_iv,
//            case R.id.top_ad_iv:
//                Bundle bundle = new Bundle();
//                bundle.putInt("from", 2);
//                bundle.putString("desc", desc);
//                bundle.putString("href", href);
//                startActivity(NoticeDetailsAty.class, bundle);
//                break;
            case R.id.to_be_back_iv:
                fgt_limit_sc.smoothScrollTo(0, 0);
                to_be_back_iv.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_limit;
    }

    @Override
    protected void initialized() {
        list = new ArrayList<>();
        list2 = new ArrayList<>();
        limitBuyPst = new LimitBuyPst(this);
    }

    @Override
    protected void requestData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        limitBuyPst.limitBuyIndex(p, stage_id);
    }

    @Override
    protected void immersionInit() {
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        L.e("限量购=======》:", requestUrl + " " + jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        numall = Integer.parseInt(map.get("nums"));
        if (requestUrl.contains("limitBuyIndex")) {
            limitBuyBean = GsonUtil.GsonToBean(jsonStr, LimitBuyBean.class);
            LimitBuyBean.DataBean dataBean = limitBuyBean.getData();

//            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            // 当前时间
            String now = DateTool.getUserDate("HH:mm");
            // 结束时间
            String end;
            if (1 == type) {
                end = dataBean.getStart_time();
            } else {
                end = dataBean.getEnd_time();
            }
            // 两个时间的差值
            String interval = DateTool.getTwoHour(end, now);
            // 将差值转成秒
            double temp = Double.parseDouble(interval) * 60 * 60;
            // 将秒转成毫秒
            long difference = (long) (temp * 1000);
            // 设置倒计时的Tag
            limit_count_down_view.setTag("limit" + stage_id);
            // 设置倒计时的时长
            limit_count_down_view.start(difference);

            if (1 == p) {
                final LimitBuyBean.DataBean.AdsBean adsBean = dataBean.getAds();
//                final Map<String, String> ads = JSONUtils.parseKeyAndValueToMap(data.get("ads"));
                Glide.with(getActivity()).load(adsBean.getPicture())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .error(R.drawable.ic_default)
                        .placeholder(R.drawable.ic_default)
//                        .centerCrop()
//                        .override(Settings.displayWidth, height)
                        .into(top_ad_iv);
                top_ad_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!TextUtils.isEmpty(adsBean.getMerchant_id()) && !adsBean.getMerchant_id().equals("0")) {
                            Bundle bundle = new Bundle();
                            bundle.putString("mell_id", adsBean.getMerchant_id());
                            startActivity(MellInfoAty.class, bundle);
                        } else if (!TextUtils.isEmpty(adsBean.getGoods_id()) && !adsBean.getGoods_id().equals("0")) {
                            Bundle bundle = new Bundle();
                            bundle.putString("ticket_buy_id", adsBean.getGoods_id());
                            bundle.putInt("from", 1);
                            startActivity(TicketGoodsDetialsAty.class, bundle);
                        } else {
                            Bundle bundle = new Bundle();
//                            bundle.putInt("from", 2);
//                            bundle.putString("desc", desc);
                            bundle.putString("url", href);
                            startActivity(WebViewAty.class, bundle);
                        }
                    }
                });
                desc = adsBean.getDesc();
                href = adsBean.getHref();
                if (dataBean.getLimitBuyList().size() > 0) {
//                    list = GsonUtil.getObjectList(data.get("limitBuyList"), AuctionList.class);
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    AuctionList auctionList;
                    for (LimitBuyBean.DataBean.LimitBuyListBean limitBuyListBean : dataBean.getLimitBuyList()) {
                        auctionList = new AuctionList();
                        auctionList.setAuction_id(limitBuyListBean.getId());
                        auctionList.setStart_time(limitBuyListBean.getStart_time());
                        auctionList.setEnd_time(limitBuyListBean.getEnd_time());
                        auctionList.setIntegral(limitBuyListBean.getIntegral());
                        auctionList.setGoods_name(limitBuyListBean.getGoods_name());
                        auctionList.setGoods_img(limitBuyListBean.getGoods_img());
                        auctionList.setCountry_id(limitBuyListBean.getCountry_id());
                        auctionList.setCountry_logo(limitBuyListBean.getCountry_logo());
                        auctionList.setTicket_buy_id(limitBuyListBean.getTicket_buy_id());
                        auctionList.setTicket_buy_discount(limitBuyListBean.getTicket_buy_discount());
                        auctionList.setStart_price(limitBuyListBean.getSell_num()); // TODO 起拍价这边设置的不正确，回传的好像没有这个字段
                        auctionList.setMarket_price(limitBuyListBean.getMarket_price());//原价
                        auctionList.setIs_remind(limitBuyListBean.getIs_remind());//是否已经设置提醒
                        auctionList.setLimit_buy_id(limitBuyListBean.getLimit_buy_id());//限量购id
                        auctionList.setLimit_price(limitBuyListBean.getLimit_price());//限量购价格
                        auctionList.setLimit_store(limitBuyListBean.getLimit_store());//库存
                        auctionList.setLimit_num(limitBuyListBean.getLimit_num());//每人限购数量
                        auctionList.setSell_num(limitBuyListBean.getSell_num());//积分
                        list.add(auctionList);
                    }
                    limiAdapter = new LimitAdapter(list, getActivity(), type, 0);
                    limit_gv.setAdapter(limiAdapter);
                    limiAdapter.setTvClick(new AdapterTextViewClickListener() {
                        @Override
                        public void onTextViewClick(View v, int position) {
                            if (!Config.isLogin()) {
                                toLogin();
                                return;
                            }
                            if (list.get(position).getIs_remind().equals("1")) {
                                showRightTip("您已设置过提醒");
                                return;
                            }
                            operation_select = position;
                            limitBuyPst.remindMe(list.get(position).getLimit_buy_id());
                        }
                    });
                }
                progressBar.setVisibility(View.GONE);
                refresh_view.setRefreshing(false); // 刷新成功
            } else {
                if (dataBean.getLimitBuyList().size() > 0) {
                    if (list2 == null) {
                        list2 = new ArrayList<>();
                    }
                    AuctionList auctionList;
                    for (LimitBuyBean.DataBean.LimitBuyListBean limitBuyListBean : dataBean.getLimitBuyList()) {
                        auctionList = new AuctionList();
                        auctionList.setAuction_id(limitBuyListBean.getId());
                        auctionList.setStart_time(limitBuyListBean.getStart_time());
                        auctionList.setEnd_time(limitBuyListBean.getEnd_time());
                        auctionList.setIntegral(limitBuyListBean.getIntegral());
                        auctionList.setGoods_name(limitBuyListBean.getGoods_name());
                        auctionList.setGoods_img(limitBuyListBean.getGoods_img());
                        auctionList.setCountry_id(limitBuyListBean.getCountry_id());
                        auctionList.setCountry_logo(limitBuyListBean.getCountry_logo());
                        auctionList.setTicket_buy_id(limitBuyListBean.getTicket_buy_id());
                        auctionList.setTicket_buy_discount(limitBuyListBean.getTicket_buy_discount());
                        auctionList.setStart_price(limitBuyListBean.getSell_num()); // TODO 起拍价这边设置的不正确，回传的好像没有这个字段
                        auctionList.setMarket_price(limitBuyListBean.getMarket_price());//原价
                        auctionList.setIs_remind(limitBuyListBean.getIs_remind());//是否已经设置提醒
                        auctionList.setLimit_buy_id(limitBuyListBean.getLimit_buy_id());//限量购id
                        auctionList.setLimit_price(limitBuyListBean.getLimit_price());//限量购价格
                        auctionList.setLimit_store(limitBuyListBean.getLimit_store());//库存
                        auctionList.setLimit_num(limitBuyListBean.getLimit_num());//每人限购数量
                        auctionList.setSell_num(limitBuyListBean.getSell_num());//积分
                        list2.add(auctionList);
                    }
//                    list2 = GsonUtil.getObjectList(dataBean.getLimitBuyList(), AuctionList.class);
                    list.addAll(list2);
                    limiAdapter.notifyDataSetChanged();
                }
                footerImageView.setVisibility(View.VISIBLE);
                footerProgressBar.setVisibility(View.GONE);
                refresh_view.setLoadMore(false); // 刷新成功
            }
            return;
        }
        if (requestUrl.contains("remindMe")) {
            showRightTip("设置成功");
            if (operation_select >= 0) {
                list.get(operation_select).setIs_remind("1");
                limiAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 更新数据
     */
    private void forUpdata() {
        refresh_view.setHeaderViewBackgroundColor(Color.WHITE);
        refresh_view.setHeaderView(createHeaderView());// add headerView
        refresh_view.setFooterView(createFooterView());
        refresh_view.setTargetScrollWithLayout(true);
        refresh_view.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                textView.setText("正在刷新");
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                p = 1;
                limitBuyPst.limitBuyIndex(p, stage_id);
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
        refresh_view.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                footerTextView.setText("正在加载...");
                footerImageView.setVisibility(View.GONE);
                footerProgressBar.setVisibility(View.VISIBLE);
                if (numall <= list.size()) {
                    refresh_view.setLoadMore(false); // 加载成功
                    return;
                }
                // 加载操作
                p++;
                limitBuyPst.limitBuyIndex(p, stage_id);
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
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY < height) {
            to_be_back_iv.setVisibility(View.GONE);
        } else {
            to_be_back_iv.setVisibility(View.VISIBLE);
        }
    }

    private View createFooterView() {
        View footerView = LayoutInflater.from(refresh_view.getContext())
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
        View headerView = LayoutInflater.from(refresh_view.getContext()).inflate(R.layout.layout_head, null);
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
