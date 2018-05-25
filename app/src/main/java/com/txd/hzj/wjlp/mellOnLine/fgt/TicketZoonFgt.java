package com.txd.hzj.wjlp.mellOnLine.fgt;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.tools.ObserTool;
import com.ants.theantsgo.util.ListUtils;
import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.GroupBuyBean;
import com.txd.hzj.wjlp.bean.TwoCateListBean;
import com.txd.hzj.wjlp.bean.commodity.AllGoodsBean;
import com.txd.hzj.wjlp.http.country.CountryPst;
import com.txd.hzj.wjlp.http.groupbuy.GroupBuyPst;
import com.txd.hzj.wjlp.http.integral.IntegralBuyPst;
import com.txd.hzj.wjlp.http.prebuy.PerBuyPst;
import com.txd.hzj.wjlp.http.ticketbuy.TicketBuyPst;
import com.txd.hzj.wjlp.mainFgt.adapter.AllGvLvAdapter;
import com.txd.hzj.wjlp.mainFgt.adapter.TicketZoonAdapter;
import com.txd.hzj.wjlp.mainFgt.adapter.ViewPagerAdapter;
import com.txd.hzj.wjlp.mellOnLine.NoticeDetailsAty;
import com.txd.hzj.wjlp.mellOnLine.adapter.WjMellAdapter;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.GoodLuckDetailsAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.LimitGoodsAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.MellInfoAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.TicketGoodsDetialsAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.groupbuy.GroupBuyThirdAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.prebuy.PreBuyThirdAty;
import com.txd.hzj.wjlp.tool.WJConfig;
import com.txd.hzj.wjlp.view.TouchViewpager;
import com.txd.hzj.wjlp.view.VpSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/10 0010
 * 时间：上午 9:42
 * 描述：票券区 无界预购 进口馆 拼团购 无界商店
 * ===============Txunda===============
 */
public class TicketZoonFgt extends BaseFgt implements NestedScrollView.OnScrollChangeListener {
    /**
     * 商品
     */
    @ViewInject(R.id.ticket_zoon_goods_gv)
    private GridViewForScrollView ticket_zoon_goods_gv;
    /**
     * 商品
     */
    @ViewInject(R.id.ticket_zoon_goods_lv)
    private ListViewForScrollView ticket_zoon_goods_lv;

    /**
     * GridView数据列表
     */
    private List<TwoCateListBean> gv_classify;

    private String title = "";
    /**
     * 哪种类型，无界预购 等
     */
    private int type = 0;
    private AllGvLvAdapter allGvLvAdapter1;

    private WjMellAdapter wjMellAdapter;

    private List<AllGoodsBean> data;
    private List<AllGoodsBean> data2;

    /**
     * 回到顶部
     */
    @ViewInject(R.id.zoom_be_back_top_iv)
    private ImageView zoom_be_back_top_iv;

    @ViewInject(R.id.zooom_sc)
    private NestedScrollView zooom_sc;

    @ViewInject(R.id.goods_menu_vp)
    private TouchViewpager goods_menu_vp;
    private int pageSize = 10;
    private int curIndex = 0;

    // 拼团购
    private GroupBuyPst groupBuyPst;
    // 无界预购
    private PerBuyPst perBuyPst;
    // 票券区
    private TicketBuyPst ticketBuyPst;
    // 无界书院
    private IntegralBuyPst integralBuyPst;
    // 进口馆
    private CountryPst countryPst;

    private int p = 1;//页数

    @ViewInject(R.id.refresh_view)
    private VpSwipeRefreshLayout refresh_view;

    private boolean isLoding = true;
    private int numall = 0;
    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;
    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;
    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;

    @ViewInject(R.id.group_ad_pic_iv)
    private ImageView group_ad_pic_iv;

    private LinearLayout.LayoutParams params;
    private String desc = "";
    private String href = "";
    private String country_id = "";

    public static TicketZoonFgt getFgt(String title, int type, String country_id) {
        TicketZoonFgt tzf = new TicketZoonFgt();
        tzf.title = title;
        tzf.type = type;
        tzf.country_id = country_id;
        return tzf;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        if (type != 8) {
        allGvLvAdapter1 = new AllGvLvAdapter(getActivity(), data, type);
//        }

//        if (8 == type) {
//            ticket_zoon_goods_lv.setVisibility(View.VISIBLE);
//            ticket_zoon_goods_gv.setVisibility(View.GONE);
//            ticket_zoon_goods_lv.setEmptyView(no_data_layout);
//        } else {
        ticket_zoon_goods_lv.setVisibility(View.GONE);
        ticket_zoon_goods_gv.setVisibility(View.VISIBLE);
        ticket_zoon_goods_gv.setEmptyView(no_data_layout);

        ticket_zoon_goods_gv.setOnScrollListener(new AbsListView.OnScrollListener() {
            boolean sIsScrolling = false;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING || scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    sIsScrolling = true;
                    Glide.with(getContext()).pauseRequests();
                } else if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (sIsScrolling == true) {
                        Glide.with(getContext()).resumeRequests();
                    }
                    sIsScrolling = false;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Settings.displayWidth, Settings.displayWidth * 400 / 1242);
        group_ad_pic_iv.setLayoutParams(params);
//        }
        ticket_zoon_goods_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                switch (type) {
                    case WJConfig.PQQ:// 票券区
                        bundle.putString("ticket_buy_id", data.get(i).getTicket_buy_id());
                        startActivity(TicketGoodsDetialsAty.class, bundle);
                        break;
                    case WJConfig.WJYG:// 无界预购
                        bundle.putString("limit_buy_id", data.get(i).getPre_buy_id());
                        bundle.putInt("type", WJConfig.WJYG);
                        startActivity(LimitGoodsAty.class, bundle);
                        break;
                    case WJConfig.WJSD:// 无界商家
                        bundle.putString("limit_buy_id", data.get(i).getIntegral_buy_id());
                        bundle.putInt("type", WJConfig.WJSD);
                        startActivity(LimitGoodsAty.class, bundle);
                        break;
                    case WJConfig.JKG:// 进口馆
                        bundle.putString("ticket_buy_id", data.get(i).getGoods_id());
                        bundle.putInt("from", 1);
                        startActivity(TicketGoodsDetialsAty.class, bundle);
                        break;
                    case WJConfig.PTG:
                        bundle.putString("group_buy_id", data.get(i).getGroup_buy_id());
                        startActivity(GoodLuckDetailsAty.class, bundle);
                        break;
                }
            }
        });
        ticket_zoon_goods_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("group_buy_id", data.get(i).getGroup_buy_id());
                startActivity(GoodLuckDetailsAty.class, bundle);
            }
        });
        zooom_sc.smoothScrollTo(0, 0);
        zooom_sc.setOnScrollChangeListener(this);

        forUpdata();
    }

    /**
     * 更新数据
     */
    private void forUpdata() {
        refresh_view.setHeaderViewBackgroundColor(Color.WHITE);
        refresh_view.setHeaderView(createHeaderView());// add headerView
        refresh_view.setTargetScrollWithLayout(true);
        refresh_view.setFooterView(createFooterView());
        refresh_view.setOnPullRefreshListener(new VpSwipeRefreshLayout.OnPullRefreshListener() {

            @Override
            public void onRefresh() {
                textView.setText("正在刷新");
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                p = 1;
                forData();
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
        refresh_view.setOnPushLoadMoreListener(new VpSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                footerTextView.setText("正在加载...");
                footerImageView.setVisibility(View.GONE);
                footerProgressBar.setVisibility(View.VISIBLE);
                if (numall <= data.size()) {
                    refresh_view.setLoadMore(false); // 刷新成功
                    return;
                }
                // 加载操作
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
       /* refresh_view.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                p = 1;
                forData();
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                if (numall <= data.size()) {
                    refresh_view.loadmoreFinish(PullToRefreshLayout.SUCCEED); // 刷新成功
                    return;
                }
                // 加载操作
                p++;
                forData();
            }
        });*/
    }

    @Override
    @OnClick({R.id.zoom_be_back_top_iv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            //R.id.group_ad_pic_iv,
//            case R.id.group_ad_pic_iv:
//                Bundle bundle = new Bundle();
//                bundle.putInt("from", 2);
//                bundle.putString("desc", desc);
//                bundle.putString("href", href);
//                startActivity(NoticeDetailsAty.class, bundle);
//                break;
            case R.id.zoom_be_back_top_iv:
                zoom_be_back_top_iv.setVisibility(View.GONE);
                zooom_sc.smoothScrollTo(0, 0);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_ticket_zoon;
    }

    @Override
    protected void initialized() {
        groupBuyPst = new GroupBuyPst(this);
        perBuyPst = new PerBuyPst(this);
        ticketBuyPst = new TicketBuyPst(this);
        integralBuyPst = new IntegralBuyPst(this);
        countryPst = new CountryPst(this);
        gv_classify = new ArrayList<>();
        data = new ArrayList<>();
        data2 = new ArrayList<>();
    }

    @Override
    protected void requestData() {
        forData();
    }

    private void forData() {
        switch (type) {
            case WJConfig.PQQ:// 票券区
                ticketBuyPst.ticketBuyIndex(p, title);
                break;
            case WJConfig.WJYG:// 无界预购
                perBuyPst.preBuyIndex(p, title);
                break;
            case WJConfig.JKG:// 进口馆
                countryPst.countryGoods(p, country_id, title);
                break;
            case WJConfig.PTG:// 拼团购
                groupBuyPst.groupBuyIndex(p, title);
                break;
            case WJConfig.WJSD:// 无界商店
                integralBuyPst.integralBuyIndex(p, title);
                break;
        }
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("groupBuyIndex")) {  //拼团
            GroupBuyBean groupBuyBean = GsonUtil.GsonToBean(jsonStr, GroupBuyBean.class);
            numall = groupBuyBean.getNums();
            if (1 == p) {
                //第一页数据
                if (isLoding) {
                    gv_classify = groupBuyBean.getData().getTwo_cate_list();
                    if (!ListUtils.isEmpty(gv_classify)) {
                        if (gv_classify.size() > 5) {
                            params = new LinearLayout.LayoutParams(Settings.displayWidth,
                                    ToolKit.dip2px(getActivity(), 160));
                        } else {
                            params = new LinearLayout.LayoutParams(Settings.displayWidth,
                                    ToolKit.dip2px(getActivity(), 160));
                        }
                        goods_menu_vp.setLayoutParams(params);
                        forMenu();
                    }
                }

                data = groupBuyBean.getData().getGroup_buy_list();

                if (!ListUtils.isEmpty(data)) {
                    allGvLvAdapter1 = new AllGvLvAdapter(getActivity(), data, type);
                    // ticket_zoon_goods_lv.setAdapter(allGvLvAdapter1);
                    ticket_zoon_goods_gv.setAdapter(allGvLvAdapter1);
                }

                final GroupBuyBean.Data.AdsBean adsBean = groupBuyBean.getData().getAds();
                if (adsBean != null) {
                    Glide.with(getActivity()).load(adsBean.getPicture())
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .error(R.drawable.ic_default)
                            .placeholder(R.drawable.ic_default)
                            .into(group_ad_pic_iv);
                    group_ad_pic_iv.setOnClickListener(new View.OnClickListener() {
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
                                bundle.putInt("from", 2);
                                bundle.putString("desc", desc);
                                bundle.putString("href", href);
                                startActivity(NoticeDetailsAty.class, bundle);
                            }

                        }
                    });
                    desc = adsBean.getDesc();
                    href = adsBean.getHref();

                }

                refresh_view.setRefreshing(false); // 刷新成功
            } else {
                data2 = groupBuyBean.getData().getGroup_buy_list();
                if (!ListUtils.isEmpty(data2)) {
                    data.addAll(data2);
                    allGvLvAdapter1.notifyDataSetChanged();
                }
                refresh_view.setLoadMore(false); // 刷新成功
            }
            return;
        }
        if (requestUrl.contains("preBuyIndex")) {// 无界预购
            forOtherData(jsonStr);
            return;
        }
        if (requestUrl.contains("ticketBuyIndex")) {// 票券区
            forOtherData(jsonStr);
            return;
        }
        if (requestUrl.contains("integralBuyIndex")) {// 无界商店
            forOtherData(jsonStr);
            return;
        }
        if (requestUrl.contains("countryGoods")) {// 进口馆
            forOtherData(jsonStr);
        }
    }

    /**
     * 非 拼团购数据
     *
     * @param jsonStr 原始数据
     */
    private void forOtherData(String jsonStr) {

        ObserTool.gainInstance().jsonToBean(jsonStr, GroupBuyBean.class, new ObserTool.BeanListener() {
            @Override
            public void returnObj(Object t) {
                GroupBuyBean groupBuyBean = (GroupBuyBean) t;
                numall = groupBuyBean.getNums();
                if (1 == p) {
                    if (isLoding) {
                        gv_classify = groupBuyBean.getData().getTwo_cate_list();
                        if (!ListUtils.isEmpty(gv_classify)) {
                            if (gv_classify.size() > 5) {
                                params = new LinearLayout.LayoutParams(Settings.displayWidth,
                                        ToolKit.dip2px(getActivity(), 160));
                            } else {
                                params = new LinearLayout.LayoutParams(Settings.displayWidth,
                                        ToolKit.dip2px(getActivity(), 160));
                            }
                            goods_menu_vp.setLayoutParams(params);
                            forMenu();
                        }
                    }
                    switch (type) {
                        case WJConfig.PQQ:// 票券区
                            data = groupBuyBean.getData().getTicket_buy_list();
                            break;
                        case WJConfig.WJYG:// 无界预购
                            data = groupBuyBean.getData().getPre_buy_list();
                            break;
                        case WJConfig.JKG:// 进口馆
                            data = groupBuyBean.getData().getList();
                            break;
                        case WJConfig.WJSD:// 无界商店
                            data = groupBuyBean.getData().getIntegral_buy_list();
                            break;
                    }

                    if (!ListUtils.isEmpty(data)) {
                        if (10 == type) {
                            wjMellAdapter = new WjMellAdapter(getActivity(), data);
                            ticket_zoon_goods_gv.setAdapter(wjMellAdapter);
                        } else {
                            allGvLvAdapter1 = new AllGvLvAdapter(getActivity(), data, type);
                            ticket_zoon_goods_gv.setAdapter(allGvLvAdapter1);
                        }
                    }

                    final GroupBuyBean.Data.AdsBean adsBean = groupBuyBean.getData().getAds();
                    if (adsBean != null) {
                        Glide.with(getActivity()).load(adsBean.getPicture())
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                        .centerCrop()
//                        .override(Settings.displayWidth, Settings.displayWidth / 2)
                                .error(R.drawable.ic_default)
                                .placeholder(R.drawable.ic_default)
                                .into(group_ad_pic_iv);
                        group_ad_pic_iv.setOnClickListener(new View.OnClickListener() {
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
                                    bundle.putInt("from", 2);
                                    bundle.putString("desc", desc);
                                    bundle.putString("href", href);
                                    startActivity(NoticeDetailsAty.class, bundle);
                                }

                            }
                        });
                        desc = adsBean.getDesc();
                        href = adsBean.getHref();
                    }
                    refresh_view.setRefreshing(false); // 刷新成功
                } else {
                    switch (type) {

                        case WJConfig.PQQ:// 票券区

                            data2 = groupBuyBean.getData().getTicket_buy_list();
                            break;
                        case WJConfig.WJYG:// 无界预购
                            data2 = groupBuyBean.getData().getPre_buy_list();
                            break;
                        case WJConfig.JKG:// 进口馆
                            data = groupBuyBean.getData().getList();
                            break;
                        case WJConfig.WJSD:// 无界商店
                            data = groupBuyBean.getData().getIntegral_buy_list();
                            break;
                    }
                    if (!ListUtils.isEmpty(data2)) {
                        data.addAll(data2);
                        if (WJConfig.WJSD == type) {
                            wjMellAdapter.notifyDataSetChanged();
                        } else {
                            allGvLvAdapter1.notifyDataSetChanged();
                        }
                    }
                    refresh_view.setLoadMore(false); // 刷新成功
                }

            }
        });
    }

    @Override
    protected void immersionInit() {

    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY < Settings.displayWidth / 2) {
            zoom_be_back_top_iv.setVisibility(View.GONE);
        } else {
            zoom_be_back_top_iv.setVisibility(View.VISIBLE);
        }
    }

    private void forMenu() {
        // 获取总页数
        int pageCount = (int) Math.ceil(gv_classify.size() * 1.0 / pageSize);
        // 初始化View列表
        ArrayList<View> mPagerList = new ArrayList<>();
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        for (int i = 0; i < pageCount; i++) {
            GridViewForScrollView gridView = (GridViewForScrollView) inflater.inflate(R.layout.on_line_gv_layout,
                    goods_menu_vp, false);
            gridView.setAdapter(new TicketZoonAdapter(getActivity(), gv_classify, i));
            mPagerList.add(gridView);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    int itemPos = i + curIndex * pageSize;

                    Bundle bundle = new Bundle();
                    bundle.putString("appBarTitle", gv_classify.get(itemPos).getName());
                    bundle.putString("two_cate_id", gv_classify.get(itemPos).getTwo_cate_id());
                    bundle.putInt("type", type);
                    if (WJConfig.JKG == type)
                        bundle.putString("country_id", country_id);
                    switch (type) {
                        case WJConfig.PQQ:// 票券区
                        case WJConfig.WJYG:// 无界预购
                        case WJConfig.JKG:// 进口馆
                        case WJConfig.WJSD:// 无界商店
                            startActivity(PreBuyThirdAty.class, bundle);
                            break;
                        case WJConfig.PTG:// 拼团购
                            startActivity(GroupBuyThirdAty.class, bundle);
                            break;

                    }
                }
            });
            // 给ViewPager设置适配器
            goods_menu_vp.setAdapter(new ViewPagerAdapter(mPagerList));
            // 添加页面改变监听事件
            goods_menu_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    curIndex = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

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

    private View createFooterView() {
        View footerView = LayoutInflater.from(refresh_view.getContext()).inflate(R.layout.layout_footer, null);
        footerProgressBar = footerView.findViewById(R.id.footer_pb_view);
        footerImageView = footerView.findViewById(R.id.footer_image_view);
        footerTextView = footerView.findViewById(R.id.footer_text_view);
        footerProgressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.VISIBLE);
        footerImageView.setImageResource(R.drawable.down_arrow);
        footerTextView.setText("上拉加载更多...");
        return footerView;
    }


}
