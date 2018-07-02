package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.ListUtils;
import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.collect.UserCollectPst;
import com.txd.hzj.wjlp.http.groupbuy.GroupBuyPst;
import com.txd.hzj.wjlp.http.merchant.MerchantPst;
import com.txd.hzj.wjlp.mellOffLine.OffLineMellInfoAty;
import com.txd.hzj.wjlp.mellOffLine.dialog.MellCouponDialog;
import com.txd.hzj.wjlp.mellOffLine.dialog.NoticeDialog;
import com.txd.hzj.wjlp.mellOnLine.NoticeDetailsAty;
import com.txd.hzj.wjlp.mellOnLine.adapter.MellGoodsAndAdsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/14 0014
 * 时间：下午 2:30
 * 描述：线上店铺详情
 * ===============Txunda===============
 */
public class MellInfoAty extends BaseAty {

    // ========人气，价格，销量，全部
    /**
     * 人气
     */
    @ViewInject(R.id.popularity_tv)
    private TextView popularity_tv;
    /**
     * 全部商品
     */
    @ViewInject(R.id.mell_price_tv)
    private TextView mell_price_tv;

    /**
     * 热销商品
     */
    @ViewInject(R.id.sales_tv)
    private TextView sales_tv;
    /**
     * 活动商品
     */
    @ViewInject(R.id.all_classify_tv)
    private TextView all_classify_tv;
    /**
     * 最新上架
     */
    @ViewInject(R.id.at_laster_tv)
    private TextView at_laster_tv;

    /**
     * 排序方式
     */
    private int soft_type = 0;
    /**
     * 商家广告
     */
    @ViewInject(R.id.mell_ads_lv)
    private ListViewForScrollView mell_ads_lv;
    /**
     * 商品列表
     */
    @ViewInject(R.id.mell_goods_gv)
    private GridViewForScrollView mell_goods_gv;

    private PopupWindow mCurPopupWindow;

    /**
     * 店铺信息布局
     */
    @ViewInject(R.id.mell_info_head_layout)
    private LinearLayout mell_info_head_layout;

    @ViewInject(R.id.mell_info_app_bar_layout)
    private AppBarLayout mell_info_app_bar_layout;

    @ViewInject(R.id.mell_info_tool_layout)
    private CollapsingToolbarLayout mell_info_tool_layout;

    /**
     * TitleBar
     */
    @ViewInject(R.id.mell_tool_bar)
    private Toolbar mell_tool_bar;
    /**
     * 搜索输入框
     */
    @ViewInject(R.id.title_search_ev)
    private EditText title_search_ev;

    /**
     * 无界头条View
     */
    private List<View> views;
    private NoticeDialog noticeDialog;

    private TextView notice_content_tv;

    private List<String> aty_type;

    private int pop_select = 0;

    /**
     * 数据源，此参数用于判断ReclycleView的item样式
     * 0.店铺首页，全部是图片，LinearManager方式
     * 1.全部商品，热销商品，最新上架
     * 2.限量购
     * 3.拼团购
     * 4.无界预购
     * 5.竞拍汇
     * 6.一元夺宝
     */
    private int data_type = 0;

    private MellGoodsAndAdsAdapter mellGoodsAndAdsAdapter;

    private MerchantPst merchantPst;
    private GroupBuyPst groupBuyPst;
    private String mell_id = "";
    private int p = 1;
    /**
     * 是否收藏
     */
    private String is_collect = "";
    /**
     * 收藏
     */
    /*@ViewInject(R.id.goods_title_collect_iv)
    private ImageView goods_title_collect_iv;*/
    /**
     * 收藏
     */
    @ViewInject(R.id.goods_title_collect_tv)
    private TextView goods_title_collect_tv;
    /**
     * 收藏
     */
    private UserCollectPst collectPst;
    /**
     * 店铺名
     */
    private String merchant_name = "";
    /**
     * 店铺名称
     */
    @ViewInject(R.id.merchant_name_tv)
    private TextView merchant_name_tv;
    /**
     * 店铺描述
     */
    @ViewInject(R.id.merchant_slogan_tv)
    private TextView merchant_slogan_tv;

    /**
     * 店铺图片
     */
    @ViewInject(R.id.mell_logo_pic_iv)
    private ImageView mell_logo_pic_iv;

    private int size = 0;

    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;

    /**
     * 上拉刷新下拉加载
     */
    @ViewInject(R.id.mell_super_sr_layout)
    private SuperSwipeRefreshLayout mell_super_sr_layout;

    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;
    private boolean frist = true;
    private List<Map<String, String>> ads_list;

    @ViewInject(R.id.mell_ads_tv)
    private TextView mell_ads_tv;
    private String announce = "";

    /**
     * 优惠券布局
     */
    @ViewInject(R.id.check_all_coupon_tv)
    private LinearLayout check_all_coupon_tv;

    @ViewInject(R.id.ticket_tip_tv)
    private TextView ticket_tip_tv;
    private List<Map<String, String>> ticket_list;
    private MellCouponDialog mellCouponDialog;
    private String share_img;
    private String share_url;
    private String share_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(mell_tool_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mell_tool_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        showStatusBar(R.id.mell_tool_bar);

        mell_info_app_bar_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -mell_info_head_layout.getHeight() / 2) {
                    mell_info_tool_layout.setTitle(merchant_name);
                } else {
                    mell_info_tool_layout.setTitle(" ");
                }
            }
        });

        soft_type = 0;
        setStyle(soft_type);

        mell_super_sr_layout.setHeaderViewBackgroundColor(Color.WHITE);
        mell_super_sr_layout.setHeaderView(createHeaderView());// add headerView
        mell_super_sr_layout.setFooterView(createFooterView());
        mell_super_sr_layout.setTargetScrollWithLayout(true);

        mell_super_sr_layout
                .setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {

                    @Override
                    public void onRefresh() {
                        frist = false;
                        textView.setText("正在刷新");
                        imageView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        p = 1;
                        getData(soft_type);
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

        mell_super_sr_layout
                .setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {

                    @Override
                    public void onLoadMore() {
                        frist = false;
                        footerTextView.setText("正在加载...");
                        footerImageView.setVisibility(View.GONE);
                        footerProgressBar.setVisibility(View.VISIBLE);
                        p++;
                        getData(soft_type);
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

        mell_ads_lv.setEmptyView(no_data_layout);
        mell_goods_gv.setEmptyView(no_data_layout);

    }


    @Override
    @OnClick({R.id.popularity_tv, R.id.mell_price_tv, R.id.sales_tv, R.id.at_laster_tv,
            R.id.all_classify_tv, R.id.mell_info_by_off_line, R.id.off_line_mell_collect_layout, R.id.mell_ads_tv,
            R.id.check_all_coupon_tv, R.id.off_line_mell_share_tv, R.id.search_title_right_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.off_line_mell_share_tv:

                toShare("无界优品", share_img, share_url, share_content, mell_id, "1");

                break;
            case R.id.popularity_tv:// 店铺首页
                soft_type = 0;
                setStyle(soft_type);
                mCurPopupWindow = null;
                break;
            case R.id.mell_price_tv:// 全部商品
                soft_type = 1;
                setStyle(soft_type);
                mCurPopupWindow = null;
                break;
            case R.id.sales_tv:// 热销商品
                soft_type = 2;
                setStyle(soft_type);
                mCurPopupWindow = null;
                break;
            case R.id.at_laster_tv:// 最新上架
                soft_type = 3;
                setStyle(soft_type);
                mCurPopupWindow = null;
                break;
            case R.id.all_classify_tv:// 分类

                if (mCurPopupWindow == null) {
                    mCurPopupWindow = showPop(v);
                    mCurPopupWindow.update();
                } else {
                    mCurPopupWindow.dismiss();
                    mCurPopupWindow = null;
                }
                soft_type = 4;
                setStyle(soft_type);
                break;
            case R.id.mell_info_by_off_line:// 商城详情
                Bundle bundle = new Bundle();
                bundle.putInt("type", 1);
                bundle.putString("merchant_id", mell_id);
                bundle.putString("merchant_name", merchant_name);
                startActivity(OffLineMellInfoAty.class, bundle);
                break;
            case R.id.off_line_mell_collect_layout:// 收藏取消收藏
                if (!Config.isLogin()) {
                    toLogin();
                    break;
                }
                if (is_collect.equals("0")) {
                    collectPst.addCollect("2", mell_id);
                    break;
                }
                collectPst.delOneCollect("2", mell_id);
                break;
            case R.id.mell_ads_tv:// 公告
                noticeDialog = new NoticeDialog(MellInfoAty.this);
                noticeDialog.show();
                notice_content_tv = noticeDialog.findViewById(R.id.notice_content_tv);
                notice_content_tv.setText(announce);
                break;
            case R.id.check_all_coupon_tv:
                mellCouponDialog = new MellCouponDialog(this, ticket_list, new MellCouponDialog.ItemClick() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                });
                mellCouponDialog.show();
                break;
            case R.id.search_title_right_tv://搜索
                p = 1;
                if (soft_type == 0) {
                    setStyle(1);
                } else {
                    setStyle(soft_type);
                }
//                getData(soft_type);
                break;
        }
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.aty_mell_info_hzj;
    }

    @Override
    protected void initialized() {
        views = new ArrayList<>();
        merchantPst = new MerchantPst(this);
        collectPst = new UserCollectPst(this);
        groupBuyPst=new GroupBuyPst(this);
        aty_type = new ArrayList<>();
        ads_list = new ArrayList<>();
        aty_type.add("限量购");
        aty_type.add("拼单购");
        aty_type.add("无界预购");
        aty_type.add("比价购");
        aty_type.add("积分抽奖");
        mell_id = getIntent().getStringExtra("mell_id");
        size = ToolKit.dip2px(this, 80);
        ticket_list = new ArrayList<>();
    }

    @Override
    protected void requestData() {
        mell_goods_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("ticket_buy_id", ads_list.get(position).get("goods_id"));
                bundle.putInt("from", 1);
                startActivity(TicketGoodsDetialsAty.class, bundle);
            }
        });
        mell_ads_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!TextUtils.isEmpty(ads_list.get(position).get("merchant_id")) && !ads_list.get(position).get("merchant_id").equals("0")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("mell_id", ads_list.get(position).get("merchant_id"));
                    startActivity(MellInfoAty.class, bundle);
                } else if (!TextUtils.isEmpty(ads_list.get(position).get("goods_id")) && !ads_list.get(position).get("goods_id").equals("0")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("ticket_buy_id", ads_list.get(position).get("goods_id"));
                    bundle.putInt("from", 1);
                    startActivity(TicketGoodsDetialsAty.class, bundle);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("desc", ads_list.get(position).get("href"));
                    bundle.putString("href", ads_list.get(position).get("desc"));
                    bundle.putInt("from", 2);
                    startActivity(NoticeDetailsAty.class, bundle);
                }
            }
        });
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);

        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        String message = map.get("message");
        if (requestUrl.contains("merIndex") || requestUrl.contains("goodsList")) {
            L.e("goodsList" + map);
            if (ToolKit.isList(map, "data")) {
                Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
                share_img = data.get("share_img");
                share_url = data.get("share_url");
                share_content = data.get("share_content");
                if (1 == p) {
                    ads_list.clear();

                    // 顶部数据
                    forBaseTitle(data);

                    if (requestUrl.contains("merIndex"))// 首页(广告)
                    {
                        String ticket_num = data.get("ticket_num");
                        if (ticket_num.equals("0")) {
                            check_all_coupon_tv.setVisibility(View.GONE);
                        } else {
                            check_all_coupon_tv.setVisibility(View.VISIBLE);
                            ticket_tip_tv.setText("本店有" + ticket_num + "张优惠券可领取");
                        }
                        if (ToolKit.isList(data, "ticket_list")) {
                            ticket_list = JSONUtils.parseKeyAndValueToMapList(data.get("ticket_list"));
                        }
                        ads_list = JSONUtils.parseKeyAndValueToMapList(data.get("ads_list"));
                    } else if (requestUrl.contains("goodsList")) {
                        ads_list = JSONUtils.parseKeyAndValueToMapList(data.get("goods_list"));
                    }
                    if (!ListUtils.isEmpty(ads_list)) {
                        mellGoodsAndAdsAdapter = new MellGoodsAndAdsAdapter(this, data_type, ads_list);
                        if (requestUrl.contains("merIndex")) {// 首页
                            L.e("首页");
                            mell_goods_gv.setVisibility(View.GONE);
                            mell_ads_lv.setVisibility(View.VISIBLE);
                            mell_ads_lv.setAdapter(mellGoodsAndAdsAdapter);
                        } else if (requestUrl.contains("goodsList")) {//商品
                            mell_ads_lv.setVisibility(View.GONE);
                            mell_goods_gv.setVisibility(View.VISIBLE);
                            mell_goods_gv.setAdapter(mellGoodsAndAdsAdapter);
                        }
                    } else {
                        if (mellGoodsAndAdsAdapter != null)
                            mellGoodsAndAdsAdapter.notifyDataSetChanged();
                    }
                    if (!frist) {
                        // 加载完成
                        mell_super_sr_layout.setRefreshing(false);
                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    L.e("下一页");
                    if (requestUrl.contains("merIndex")) {// 首页
                        ads_list.addAll(JSONUtils.parseKeyAndValueToMapList(data.get("ads_list")));
                    } else if (requestUrl.contains("goodsList")) {//商品
                        ads_list.addAll(JSONUtils.parseKeyAndValueToMapList(data.get("goods_list")));
                    }
                    if (!ListUtils.isEmpty(ads_list)) {
                        mellGoodsAndAdsAdapter.notifyDataSetChanged();
                    }
                    // 加载完成
                    footerImageView.setVisibility(View.VISIBLE);
                    footerProgressBar.setVisibility(View.GONE);
                    mell_super_sr_layout.setLoadMore(false);
                }
            } else {
                if (1 == p) {
                    ads_list.clear();
                    if (!frist) {
                        // 加载完成
                        mell_super_sr_layout.setRefreshing(false);
                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    // 加载完成
                    footerImageView.setVisibility(View.VISIBLE);
                    footerProgressBar.setVisibility(View.GONE);
                    mell_super_sr_layout.setLoadMore(false);
                }
                if (mellGoodsAndAdsAdapter != null)
                    mellGoodsAndAdsAdapter.notifyDataSetChanged();
            }
            return;
        }
        if (requestUrl.contains("limitList") || requestUrl.contains("merchantGroupBuyList") || requestUrl.contains("preList")
                || requestUrl.contains("auctionList") || requestUrl.contains("oneBuyList")) {
            mell_ads_lv.setVisibility(View.GONE);
            mell_goods_gv.setVisibility(View.VISIBLE);

            if (ToolKit.isList(map, "data")) {
                Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));

                if (1 == p) {
                    ads_list.clear();

                    forBaseTitle(data);
                    if (ToolKit.isList(data, "goods_list")) {

                        ads_list = JSONUtils.parseKeyAndValueToMapList(data.get("goods_list"));
                        L.e("=====数据=====", ads_list.toString());
//                        if (mellGoodsAndAdsAdapter != null)
//                            mellGoodsAndAdsAdapter.notifyDataSetChanged();
//                        else {
//                        }
                        mellGoodsAndAdsAdapter = new MellGoodsAndAdsAdapter(this, data_type, ads_list);
                        mell_goods_gv.setAdapter(mellGoodsAndAdsAdapter);
                    } else {
                        //空数据
                        ads_list = JSONUtils.parseKeyAndValueToMapList(data.get("goods_list"));
                        mellGoodsAndAdsAdapter = new MellGoodsAndAdsAdapter(this, data_type, ads_list);
                        mell_goods_gv.setAdapter(mellGoodsAndAdsAdapter);
                    }
                    if (!frist) {
                        // 加载完成
                        mell_super_sr_layout.setRefreshing(false);
                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    if (ToolKit.isList(data, "goods_list")) {
                        ads_list.addAll(JSONUtils.parseKeyAndValueToMapList(data.get("goods_list")));
                        if (mellGoodsAndAdsAdapter != null)
                            mellGoodsAndAdsAdapter.notifyDataSetChanged();
                    }
                    // 加载完成
                    footerImageView.setVisibility(View.VISIBLE);
                    footerProgressBar.setVisibility(View.GONE);
                    mell_super_sr_layout.setLoadMore(false);
                }
            } else {
                if (1 == p) {
                    ads_list.clear();
                    if (!frist) {
                        // 加载完成
                        mell_super_sr_layout.setRefreshing(false);
                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    // 加载完成
                    footerImageView.setVisibility(View.VISIBLE);
                    footerProgressBar.setVisibility(View.GONE);
                    mell_super_sr_layout.setLoadMore(false);
                }
            }
            return;
        }
        if (requestUrl.contains("addCollect")) {// 添加收藏
            showRightTip("收藏成功");
            is_collect = "1";

            goods_title_collect_tv.setCompoundDrawables(null, com.txd.hzj.wjlp.tool.TextUtils.toDrawable(this, R.drawable.icon_collected), null, null);
            goods_title_collect_tv.setText("已收藏");
            return;
        }
        if (requestUrl.contains("delOneCollect")) {
            showRightTip("取消成功");
            is_collect = "0";
            goods_title_collect_tv.setCompoundDrawables(null, com.txd.hzj.wjlp.tool.TextUtils.toDrawable(this, R.drawable.icon_collect), null, null);
            goods_title_collect_tv.setText("收藏");
        }
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        L.e("eeeee" + error.get("message"));
        if (1 == p) {
            ads_list.clear();
            if (!frist) {
                // 加载完成
                mell_super_sr_layout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
            }
        } else {
            // 加载完成
            footerImageView.setVisibility(View.VISIBLE);
            footerProgressBar.setVisibility(View.GONE);
            mell_super_sr_layout.setLoadMore(false);
        }
    }

    /**
     * 顶部(头部公共部分)
     *
     * @param data 数据
     */
    private void forBaseTitle(Map<String, String> data) {
        // 收藏
        is_collect = data.get("is_collect");
        if ("0".equals(is_collect)) {
            goods_title_collect_tv.setCompoundDrawables(null, com.txd.hzj.wjlp.tool.TextUtils.toDrawable(this, R.drawable.icon_collect), null, null);
            goods_title_collect_tv.setText("收藏");
        } else {
            goods_title_collect_tv.setCompoundDrawables(null, com.txd.hzj.wjlp.tool.TextUtils.toDrawable(this, R.drawable.icon_collected), null, null);
            goods_title_collect_tv.setText("已收藏");
        }
        // 店铺名称
        merchant_name = data.get("merchant_name");
        merchant_name_tv.setText(merchant_name);

        // 店铺口号
        String merchant_slogan = data.get("merchant_slogan");
        merchant_slogan_tv.setText(merchant_slogan);
        // 商家图片
        Glide.with(this).load(data.get("logo"))
                .override(size, size)
                .error(R.drawable.ic_default)
                .placeholder(R.drawable.ic_default)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mell_logo_pic_iv);
        if (ToolKit.isList(data, "announce")) {
            announce = data.get("announce");
            mell_ads_tv.setText(announce);
        }
    }

    private void setStyle(int type) {
        pop_select = 0;
        // 店铺详情
        popularity_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        // 全部商品
        mell_price_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        // 热销商品
        sales_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        // 最新上架
        at_laster_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        // 活动商品
        all_classify_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        p = 1;
        if (0 == type) {
            popularity_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            data_type = 0;
            // 店铺首页
        } else if (1 == type) {
            mell_price_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            data_type = 1;
        } else if (2 == type) {
            sales_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            data_type = 1;
        } else if (3 == type) {
            data_type = 1;
            at_laster_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        } else {
            all_classify_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        }
        getData(type);
    }

    /**
     * 获取数据
     *
     * @param type 数据类型
     */
    private void getData(int type) {
        switch (type) {
            case 0:// 首页

                merchantPst.merIndex(mell_id, p);
                break;
            case 1:// 全部商品
                merchantPst.goodsList(mell_id, "", "", p, title_search_ev.getText().toString());
                break;
            case 2:// 热销商品
                merchantPst.goodsList(mell_id, "1", "", p, title_search_ev.getText().toString());
                break;
            case 3:// 分页商品
                merchantPst.goodsList(mell_id, "", "1", p, title_search_ev.getText().toString());
                break;
            case 4:// 活动商品
                switch (data_type) {
                    case 2://限量购
                        merchantPst.limitList(mell_id, p);
                        break;
                    case 3:// 拼团购
//                        merchantPst.groupList(mell_id, p);
                        groupBuyPst.merchantGroupBuyList(mell_id, p);
                        break;
                    case 4:// 无界预购
                        merchantPst.preList(mell_id, p);
                        break;
                    case 5:// 竞拍汇
                        merchantPst.auctionList(mell_id, p);
                        break;
                    case 6:// 积分夺宝
                        merchantPst.oneBuyList(mell_id, p);
                        break;
                }
                break;
        }
    }

    private PopupWindow showPop(View view) {
        final View contentView = LayoutInflater.from(view.getContext()).inflate(
                R.layout.pop_classify_layout, null);
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);

        GridView classify_gv = contentView.findViewById(R.id.classify_gv);
        final ClassIfyAdapter classIfyAdapter = new ClassIfyAdapter();
        classify_gv.setAdapter(classIfyAdapter);
        classIfyAdapter.setSelected(pop_select);
        classify_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                all_classify_tv.setText(aty_type.get(i));
                all_classify_tv.setTextColor(ContextCompat.getColor(MellInfoAty.this, R.color.theme_color));
                classIfyAdapter.setSelected(i);
                classIfyAdapter.notifyDataSetChanged();
                popupWindow.dismiss();
                pop_select = i;
                mCurPopupWindow = null;
                data_type = i + 2;
                getData(4);
            }
        });
        contentView.findViewById(R.id.be_dissmis_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                mCurPopupWindow = null;
            }
        });
        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        // setOutsideTouchable设置生效的前提是setTouchable(true)和setFocusable(false)
        popupWindow.setOutsideTouchable(true);

        // 设置为true之后，PopupWindow内容区域 才可以响应点击事件
        popupWindow.setTouchable(true);

        // true时，点击返回键先消失 PopupWindow
        // 但是设置为true时setOutsideTouchable，setTouchable方法就失效了（点击外部不消失，内容区域也不响应事件）
        // false时PopupWindow不处理返回键
        popupWindow.setFocusable(false);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;   // 这里面拦截不到返回键
            }
        });
        popupWindow.showAsDropDown(view);
        return popupWindow;
    }

    @Override
    public void onBackPressed() {
        if (mCurPopupWindow != null && mCurPopupWindow.isShowing()) {
            mCurPopupWindow.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    public void setAdapterType(int adapterType) {
        if (0 == adapterType) {
            mell_ads_lv.setVisibility(View.VISIBLE);
            mell_goods_gv.setVisibility(View.GONE);
        } else {
            mell_goods_gv.setVisibility(View.VISIBLE);
            mell_ads_lv.setVisibility(View.GONE);
            mell_goods_gv.setAdapter(mellGoodsAndAdsAdapter);
        }
    }

    /**
     * 全部筛选适配器
     */
    private class ClassIfyAdapter extends BaseAdapter {

        private CVH cvh;

        private int selected = 0;

        @Override
        public int getCount() {
            return aty_type.size();
        }

        @Override
        public String getItem(int i) {
            return aty_type.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            String type = getItem(i);
            if (view == null) {
                view = LayoutInflater.from(MellInfoAty.this).inflate(R.layout.item_classify_gv, viewGroup, false);
                cvh = new CVH();
                ViewUtils.inject(cvh, view);
                view.setTag(cvh);
            } else {
                cvh = (CVH) view.getTag();
            }

            if (selected == i) {
                cvh.classify_text_tv.setTextColor(ContextCompat.getColor(MellInfoAty.this, R.color.colorAccent));
            } else {
                cvh.classify_text_tv.setTextColor(ContextCompat.getColor(MellInfoAty.this, R.color.app_text_color));
            }
            cvh.classify_text_tv.setText(type);
            return view;
        }

        void setSelected(int selected) {
            this.selected = selected;
        }

        class CVH {

            @ViewInject(R.id.classify_text_tv)
            private TextView classify_text_tv;

        }
    }

    private View createFooterView() {
        View footerView = LayoutInflater.from(this).inflate(R.layout.layout_footer, null);
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
        View headerView = LayoutInflater.from(this).inflate(R.layout.layout_head, null);
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
