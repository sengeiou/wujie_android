package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ants.theantsgo.WeApplication;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.listenerForAdapter.AdapterTextViewClickListener;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.tools.ObserTool;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.ListUtils;
import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.JsonSyntaxException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.txd.hzj.wjlp.DemoApplication;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.commodity.AllGoodsBean;
import com.txd.hzj.wjlp.bean.commodity.BodyBean;
import com.txd.hzj.wjlp.bean.commodity.CheapGroupBean;
import com.txd.hzj.wjlp.bean.commodity.CommentBean;
import com.txd.hzj.wjlp.bean.commodity.DataBean;
import com.txd.hzj.wjlp.bean.commodity.DjTicketBean;
import com.txd.hzj.wjlp.bean.commodity.FirstListBean;
import com.txd.hzj.wjlp.bean.commodity.FirstValBean;
import com.txd.hzj.wjlp.bean.commodity.GoodLuckBean;
import com.txd.hzj.wjlp.bean.commodity.GoodsActiveBean;
import com.txd.hzj.wjlp.bean.commodity.GoodsBannerBean;
import com.txd.hzj.wjlp.bean.commodity.GoodsBean;
import com.txd.hzj.wjlp.bean.commodity.GoodsCommonAttrBean;
import com.txd.hzj.wjlp.bean.commodity.GoodsInfoBean;
import com.txd.hzj.wjlp.bean.commodity.GoodsPriceDescBean;
import com.txd.hzj.wjlp.bean.commodity.GoodsServerBean;
import com.txd.hzj.wjlp.bean.commodity.GroupBean;
import com.txd.hzj.wjlp.bean.commodity.MInfoBean;
import com.txd.hzj.wjlp.bean.commodity.PicturesBean;
import com.txd.hzj.wjlp.bean.commodity.PromotionBean;
import com.txd.hzj.wjlp.bean.commodity.TicketListBean;
import com.txd.hzj.wjlp.http.Freight;
import com.txd.hzj.wjlp.http.collect.UserCollectPst;
import com.txd.hzj.wjlp.http.groupbuy.GroupBuyPst;
import com.txd.hzj.wjlp.mainFgt.adapter.AllGvLvAdapter;
import com.txd.hzj.wjlp.mellOnLine.SubclassificationAty;
import com.txd.hzj.wjlp.mellOnLine.adapter.GoodLuckAdapter;
import com.txd.hzj.wjlp.mellOnLine.adapter.GoodsCommentAttrAdapter;
import com.txd.hzj.wjlp.mellOnLine.adapter.PostAdapter;
import com.txd.hzj.wjlp.mellOnLine.adapter.PromotionAdapter;
import com.txd.hzj.wjlp.mellOnLine.adapter.TheTrickAdapter;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.adapter.CommentPicAdapter;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.snatch.SnatchGoodsDetailsAty;
import com.txd.hzj.wjlp.new_wjyp.aty_collocations;
import com.txd.hzj.wjlp.shoppingCart.BuildOrderAty;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;
import com.txd.hzj.wjlp.tool.proUrbArea.ProUrbAreaUtil;
import com.txd.hzj.wjlp.view.ObservableScrollView;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;
import cn.iwgang.countdownview.CountdownView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/10 0010
 * 时间：上午 11:00
 * 描述：拼团详情
 * ===============Txunda===============
 */
public class GoodLuckDetailsAty extends BaseAty implements ObservableScrollView.onBottomListener, CommodityDetailsInter.GoodLuckView{
    private String order_id;
    /**
     * 商品TextView
     */
    @ViewInject(R.id.title_goods_tv)
    public TextView title_goods_tv;

    /**
     * 商品View
     */
    @ViewInject(R.id.title_goods_view)
    public View title_goods_view;
    /**
     * 详情TextView
     */
    @ViewInject(R.id.title_details_tv)
    public TextView title_details_tv;

    /**
     * 详情View
     */
    @ViewInject(R.id.title_details_view)
    public View title_details_view;
    /**
     * 评价TextView
     */
    @ViewInject(R.id.title_evaluate_tv)
    public TextView title_evaluate_tv;

    /**
     * 评价View
     */
    @ViewInject(R.id.title_evaluate_view)
    public View title_evaluate_view;

    /**
     * 收藏iv
     */
  /*  @ViewInject(R.id.goods_title_collect_iv)
    private ImageView goods_title_collect_iv;*/
    /**
     * 收藏TV
     */
    @ViewInject(R.id.goods_title_collect_tv)
    private TextView goods_title_collect_tv;

    /**
     * 商品轮播
     */
    @ViewInject(R.id.online_carvouse_view)
    private CarouselView online_carvouse_view;
    /**
     * 轮播图图片
     */
    private List<GoodsBannerBean> image;

    /**
     * 现价
     */
    @ViewInject(R.id.now_price_tv)
    private TextView now_price_tv;
    /**
     * 原价
     */
    @ViewInject(R.id.old_price_tv)
    private TextView old_price_tv;

    /**
     * 分红权
     */
    @ViewInject(R.id.goods_profit_num_tv)
    private TextView goods_profit_num_tv;


    /**
     * 满折布局
     */
    @ViewInject(R.id.goods_bottom_lin_layout)
    private LinearLayout goods_bottom_lin_layout;

    /**
     * 展开，隐藏满折布局
     */
    @ViewInject(R.id.show_or_hide_iv)
    private ImageView show_or_hide_iv;

    /**
     * 无界驿站
     */
    @ViewInject(R.id.wujie_post_lv)
    private ListViewForScrollView wujie_post_lv;

    private PostAdapter postAdapter;

    private List<String> posts;

    /**
     * 显示或隐藏列表
     */
    @ViewInject(R.id.show_or_hide_lv_iv)
    private ImageView show_or_hide_lv_iv;

    /**
     * 运费
     */
    @ViewInject(R.id.freight_tv)
    private TextView freight_tv;

    /**
     * 显示和隐藏品质信息
     */
    @ViewInject(R.id.show_or_hide_explain_iv)
    private ImageView show_or_hide_explain_iv;
    /**
     * 商品其他信息
     */
    @ViewInject(R.id.goods_other_info_layout)
    private LinearLayout goods_other_info_layout;

    /**
     * 全部宝贝
     */
    @ViewInject(R.id.all_prodect_tv)
    private TextView all_prodect_tv;
    /**
     * 关注人数
     */
    @ViewInject(R.id.all_collect_tv)
    private TextView all_collect_tv;
    /**
     * 商品描述
     */
    @ViewInject(R.id.goods_describe_tv)
    private TextView goods_describe_tv;
    /**
     * 买家服务
     */
    @ViewInject(R.id.mell_serve_tv)
    private TextView mell_serve_tv;
    /**
     * 物流服务
     */
    @ViewInject(R.id.log_serve_tv)
    private TextView log_serve_tv;

    /**
     * 滚动监听的ScrollView
     */
    @ViewInject(R.id.limit_goods_details_sc)
    private ObservableScrollView limit_goods_details_sc;

    /**
     * 回到顶部
     */
    @ViewInject(R.id.be_back_top_iv)
    private ImageView be_back_top_iv;

    /**
     * 轮播的高度
     */
    private int bannerHeight = 0;

    /**
     * 对应top_lin_layout
     */
    private int topHeighe = 0;
    /**
     * 对应 second_lin_layout
     */
    private int secondHeight = 0;

    /**
     * 除去图文详情和店铺信息，评论外的所有空间布局
     */
    @ViewInject(R.id.top_lin_layout)
    private LinearLayout top_lin_layout;

    /**
     * 除去图文详情外的所有控件布局
     */
    @ViewInject(R.id.second_lin_layout)
    private LinearLayout second_lin_layout;

    /**
     * 别人在开团
     */
    @ViewInject(R.id.good_luck_lv)
    private ListViewForScrollView good_luck_lv;
    private Bundle bundle;


    /**
     * 店铺活动列表
     */
    @ViewInject(R.id.promotion_lv)
    private ListView promotion_lv;

    private GroupBuyPst groupBuyPst;
    private String group_buy_id = "";

    /**
     * 商品名称
     */
    @ViewInject(R.id.goods_details_name_tv)
    private TextView goods_details_name_tv;

    /**
     * 店铺logo
     */
    @ViewInject(R.id.the_logo_by_mell_iv)
    private ImageView the_logo_by_mell_iv;
    /**
     * 店铺logo大小
     */
    private int size = 0;
    /**
     * 店铺名称
     */
    @ViewInject(R.id.title_by_mell_tv)
    private TextView title_by_mell_tv;
    /**
     * 等级1
     */
    @ViewInject(R.id.level_1_iv)
    private ImageView level_1_iv;
    /**
     * 等级2
     */
    @ViewInject(R.id.level_2_iv)
    private ImageView level_2_iv;
    /**
     * 等级3
     */
    @ViewInject(R.id.level_3_iv)
    private ImageView level_3_iv;
    /**
     * 等级4
     */
    @ViewInject(R.id.level_4_iv)
    private ImageView level_4_iv;
    /**
     * 等级5
     */
    @ViewInject(R.id.level_5_iv)
    private ImageView level_5_iv;
    private List<ImageView> imageViews;
    /**
     * 优惠券列表
     */
    private List<TicketListBean> ticketList;
    /**
     * 店铺活动
     */
    private List<PromotionBean> promotion;
    /**
     * 活动类型
     */
    @ViewInject(R.id.onle_pro_type_iv)
    private ImageView onle_pro_type_iv;

    /**
     * 活动标题
     */
    @ViewInject(R.id.onle_pro_title_tv)
    private TextView onle_pro_title_tv;

    /**
     * 活动布局
     */
    @ViewInject(R.id.promotion_layout)
    private LinearLayout promotion_layout;

    /**
     * 评价的商品
     */
    @ViewInject(R.id.goods_for_my_evaluste_layout)
    private LinearLayout goods_for_my_evaluste_layout;

    /**
     * 评价--买家头像
     */
    @ViewInject(R.id.comm_user_head_iv)
    private ShapedImageView comm_user_head_iv;
    /**
     * 买家头像大小，60dp
     */
    private int mSize = 0;

    /**
     * 买家昵称
     */
    @ViewInject(R.id.comm_user_name_tv)
    private TextView comm_user_name_tv;
    /**
     * 买家评论内容
     */
    @ViewInject(R.id.comm_content_tv)
    private TextView comm_content_tv;
    /**
     * 买家评论图片
     */
    @ViewInject(R.id.estimate_pic)
    private GridViewForScrollView estimate_pic;
    /**
     * 商品评价数量
     */
    @ViewInject(R.id.all_comment_num_tv)
    private TextView all_comment_num_tv;

    /**
     * 单独购买
     */
    @ViewInject(R.id.one_price_tvs)
    private TextView one_price_tv;
    /**
     * 团购价，一键开团
     */
    @ViewInject(R.id.creat_group_tvs)
    private TextView creat_group_tv;
    /**
     * 购物车数量
     */
    @ViewInject(R.id.user_cart_num_tvs)
    private TextView user_cart_num_tv;
    /**
     * 别人在开团列表
     */
    private List<GroupBean> groupList;

    /**
     * 产品规格列表
     */
    @ViewInject(R.id.goods_common_attr_lv)
    private ListViewForScrollView goods_common_attr_lv;

    /**
     * 文字描述
     */
    @ViewInject(R.id.goods_brief_tv)
    private WebView goods_brief_tv;
    /**
     * 图文描述
     */
    @ViewInject(R.id.goods_desc_wv)
    private WebView goods_desc_wv;

    @ViewInject(R.id.comment_layout)
    private LinearLayout comment_layout;
    @ViewInject(R.id.layout_comment)
    private LinearLayout layout_comment;
    private String is_collect = "";
    private UserCollectPst collectPst;
    private String goods_id = "";
    private String mell_id = "";
    private String share_url = "";
    private String share_content = "";
    private String share_img = "";

    private String easemob_account = "";
    private String merchant_logo = "";
    private String merchant_name = "";


    private String vouchers_desc = "";//代金券弹窗下面的提示文字
    @ViewInject(R.id.tv_expirationdate)
    private TextView tv_expirationdate;//保质期提示

    @ViewInject(R.id.layout_djq)
    private LinearLayout layout_djq;//代金券布局
    @ViewInject(R.id.layout_djq0)
    private LinearLayout layout_djq0;
    @ViewInject(R.id.layout_djq1)
    private LinearLayout layout_djq1;
    @ViewInject(R.id.layout_djq2)
    private LinearLayout layout_djq2;
    @ViewInject(R.id.tv_djq_color0)
    private TextView tv_djq_color0;
    @ViewInject(R.id.tv_djq_color1)
    private TextView tv_djq_color1;
    @ViewInject(R.id.tv_djq_color2)
    private TextView tv_djq_color2;
    @ViewInject(R.id.tv_djq_desc0)
    private TextView tv_djq_desc0;
    @ViewInject(R.id.tv_djq_desc1)
    private TextView tv_djq_desc1;
    @ViewInject(R.id.tv_djq_desc2)
    private TextView tv_djq_desc2;
    @ViewInject(R.id.tyLayout)
    private RelativeLayout tyLayout;
    @ViewInject(R.id.good_count_down_view)
    private CountdownView good_count_down_view;
    @ViewInject(R.id.lv_qkk)
    private ListViewForScrollView lv_qkk;//去看看列表

    /**
     * 代金券
     */
    @ViewInject(R.id.goods_trick_rv)
    private RecyclerView goods_trick_rv;
    @ViewInject(R.id.get_a_coupon_lin_layout)
    private LinearLayout get_a_coupon_lin_layout;
    private TheTrickAdapter theTrickAdapter;

    @ViewInject(R.id.layout_service)
    private LinearLayout layout_service;//服务布局
    @ViewInject(R.id.rv_service)
    private RecyclerView rv_service;

    ArrayList<GoodsServerBean> ser_list;//服务的列表
    ArrayList<GoodsPriceDescBean> goods_price_desc;//价格的列表


    @ViewInject(R.id.im_country_logo)
    private ImageView im_country_logo;//国旗
    @ViewInject(R.id.tv_country_desc)
    private TextView tv_country_desc;//国家
    @ViewInject(R.id.tv_country_tax)
    private TextView tv_country_tax;//进口税
    @ViewInject(R.id.layou_jinkoushui)//进口税的布局 0的话就隐藏
    private LinearLayout layou_jinkoushui;


    @ViewInject(R.id.tv_tab_1)
    private TextView tv_tab_1;
    @ViewInject(R.id.tv_tab_2)
    private TextView tv_tab_2;
    @ViewInject(R.id.tv_tab_3)
    private TextView tv_tab_3;
    @ViewInject(R.id.layout_aftersale)
    private LinearLayout layout_aftersale;//包装售后
    @ViewInject(R.id.tv_bzqd)
    private TextView tv_bzqd;//包装清单
    @ViewInject(R.id.tv_shfw)
    private TextView tv_shfw;//售后服务
    @ViewInject(R.id.tv_jgsm)
    private TextView tv_jgsm;//价格说明
    @ViewInject(R.id.ticket_gv)
    private GridViewForScrollView ticket_gv;
    private AllGvLvAdapter allGvLvAdapter1;
    @ViewInject(R.id.layout_cheap_group)
    private LinearLayout layout_cheap_group;//搭配购
    @ViewInject(R.id.tv_ticket_buy_discount)
    private TextView tv_ticket_buy_discount;//最多可使用多少代金券
    @ViewInject(R.id.tv_group_price)
    private TextView tv_group_price;//最多可使用多少代金券
    @ViewInject(R.id.tv_group_integral)
    private TextView tv_group_integral;//送多少积分
    @ViewInject(R.id.tv_goods_price)
    private TextView tv_goods_price;//省多少
    @ViewInject(R.id.rv_cheap_group)
    private RecyclerView rv_cheap_group;
    @ViewInject(R.id.tv_expirationdateLayout)
    private LinearLayout tv_expirationdateLayout;
    @ViewInject(R.id.layout_pt)
    private LinearLayout layout_pt;//活动倒计时|别人在开团
    @ViewInject(R.id.remarks)
    private TextView remarks;
    private List<AllGoodsBean> ticket = new ArrayList<>();
    private List<AllGoodsBean> more = new ArrayList<>();

    private boolean is_f = true;//判断刷新
    private int page = 1;


    private ArrayList<DjTicketBean> dj_ticket;
    private MInfoBean mellInfoBean;


    private List<FirstListBean> goods_attr_first;
    private List<FirstValBean> first_val;
    private String is_attr = "";

    private int goods_number = 0;
    private String product_id = "";
    private boolean is_C = false;
    private GoodsInfoBean goodsInfo;

    @ViewInject(R.id.goods_select_attr_tv)
    private TextView goods_select_attr_tv;
    private String groupPrice;
    private String goods_name;
    private GoodLuckDetailsPranster goodLuckPranster;

    @ViewInject(R.id.title_goods_layout)
    private View title_goods_layout;

    @ViewInject(R.id.title_details_layout)
    private View title_details_layout;

    @ViewInject(R.id.title_evaluate_layout)
    private View title_evaluate_layout;
    private StringBuffer ex_stringBuffer;
    private CommodityDetailsPranster commodityDetailsPranster;
    @ViewInject(R.id.rb)
    private RatingBar rb;
    @ViewInject(R.id.tv_date)
    private TextView tv_date;

    @ViewInject(R.id.pdMoreLLayout)
    private LinearLayout pdMoreLLayout;
    @ViewInject(R.id.pdnumTv)
    private TextView pdnumTv;
    @ViewInject(R.id.seeMoreTv)
    private TextView seeMoreTv;
    //开团总数
    private String group_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.goods_details_title);
        // 设置轮播图高度
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Settings.displayWidth,
                Settings.displayWidth);
        online_carvouse_view.setLayoutParams(layoutParams);

        limit_goods_details_sc.scrollTo(0, 0);

        //评价的商品布局(隐藏)
        goods_for_my_evaluste_layout.setVisibility(View.GONE);

        wujie_post_lv.setAdapter(postAdapter);
        // 判断是否显示回到顶部按钮
//        getHeight();
        rv_service.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        // 优惠券
        goods_trick_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        goods_trick_rv.setHasFixedSize(true);
//        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        goodLuckPranster = new GoodLuckDetailsPranster();
        goodLuckPranster.setGoodLuckView(GoodLuckDetailsAty.this);
        commodityDetailsPranster = new CommodityDetailsPranster();
//        List<WebView> webViewList=new ArrayList<>();
//        webViewList.add(goods_desc_wv);
//        webViewList.add(goods_brief_tv);
//        configWebView(webViewList);
    }

    private boolean init = false;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && !init) {
            goodLuckPranster.getHeight(online_carvouse_view, top_lin_layout, second_lin_layout, limit_goods_details_sc, be_back_top_iv);
            goodLuckPranster.setTabViews(title_goods_layout, title_details_layout, title_evaluate_layout);
            init = true;
        }
    }

    @Override
    @OnClick({
            R.id.goods_title_collect_layout, R.id.goods_title_share_tv, R.id.show_or_hide_iv, R.id.tv_dpg,
            R.id.show_or_hide_lv_iv, R.id.show_or_hide_explain_iv, R.id.to_cart_layouts,
            R.id.creat_group_tv, R.id.go_to_main_layouts, R.id.details_into_mell_tv, R.id.to_chat_tv,
            R.id.tv_chose_ads, R.id.all_evaluate_tv,
            R.id.im_service_more, R.id.tv_tab_1, R.id.tv_tab_2, R.id.tv_tab_3, R.id.tv_gwc, R.id.tv_ljgm, R.id.btn_jgsm,
            R.id.tv_quxiao, R.id.tv_lingquan, R.id.tv_showClassify, R.id.layout_layout_settings, R.id.layout_djq, R.id.tv_expirationdateLayout, R.id.pdMoreLLayout
    })
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.all_evaluate_tv: {
                Bundle bundle = new Bundle();
                bundle.putInt("from", 0);
                bundle.putString("mid", mell_id);
                bundle.putString("goods_id", goods_id);
                startActivity(GoodsEvaluateAty.class, bundle);
                break;
            }
            case R.id.tv_chose_ads://运送至选择
//                if (isLoaded) {
//                    ShowPickerView();
//                }
                ProUrbAreaUtil.gainInstance().showPickerView((TextView) v, goods_id, GoodLuckDetailsAty.this);

                break;
            case R.id.tv_showClassify://查看分类
                Intent intent = new Intent();
                intent.putExtra("appBarTitle", goodsInfo.getTwo_cate_name());
                intent.putExtra("two_cate_id", goodsInfo.getCate_id());
                intent.setClass(this, SubclassificationAty.class);
                startActivity(intent);
                break;
            case R.id.goods_title_collect_layout://收藏

                if (!Config.isLogin()) {
                    toLogin();
                }
                if ("0".equals(is_collect)) {
                    collectPst.addCollect("1", goods_id);
                    break;
                }
                collectPst.delOneCollect("1", goods_id);

                break;
            case R.id.goods_title_share_tv://分享
//                toShare("无界优品", share_img, share_url, share_content, goods_id, "1");
                toShare(goods_name, share_img, "1", share_content, group_buy_id, "1");
                break;
            case R.id.show_or_hide_iv://展开,隐藏(满折布局)
                getHeight();// 重新计算高度
                if (goods_bottom_lin_layout.getVisibility() == View.GONE) {
                    goods_bottom_lin_layout.setVisibility(View.VISIBLE);
                    show_or_hide_iv.setImageResource(R.drawable.icon_show_other_layout);
                } else {
                    goods_bottom_lin_layout.setVisibility(View.GONE);
                    show_or_hide_iv.setImageResource(R.drawable.icon_hide_other_layout);
                }
                break;
            case R.id.show_or_hide_lv_iv://展开,隐藏(无界驿站)
                getHeight();// 重新计算高度
                if (wujie_post_lv.getVisibility() == View.GONE) {// 隐藏状态
                    wujie_post_lv.setVisibility(View.VISIBLE);
                    show_or_hide_lv_iv.setImageResource(R.drawable.icon_show_other_layout);
                } else {// 显示状态
                    wujie_post_lv.setVisibility(View.GONE);
                    show_or_hide_lv_iv.setImageResource(R.drawable.icon_hide_other_layout);
                }
                break;
            case R.id.show_or_hide_explain_iv://展开,隐藏(无界驿站)
                getHeight();// 重新计算高度
                if (goods_other_info_layout.getVisibility() == View.GONE) {// 隐藏状态
                    goods_other_info_layout.setVisibility(View.VISIBLE);
                    show_or_hide_explain_iv.setImageResource(R.drawable.icon_show_other_layout);
                } else {// 显示状态
                    goods_other_info_layout.setVisibility(View.GONE);
                    show_or_hide_explain_iv.setImageResource(R.drawable.icon_hide_other_layout);
                }
                break;

            case R.id.go_to_main_layouts:// 回到首页
                backMain(0);
                break;
            case R.id.to_cart_layouts:// 购物车
                backMain(2);
                break;
            case R.id.creat_group_tv:// 一键开团(付款生成订单)
//                bundle = new Bundle();
//                bundle.putInt("status", 2);
//                startActivity(CreateGroupAty.class, bundle);
                break;
            case R.id.details_into_mell_tv:// 进店逛逛
                Bundle bundle = new Bundle();
                bundle.putString("mell_id", mell_id);
                startActivity(MellInfoAty.class, bundle);
                break;
            case R.id.to_chat_tv:// 进店逛逛
                toChat(easemob_account, merchant_logo, merchant_name);
                break;
            case R.id.tv_tab_1:
                goods_desc_wv.setVisibility(View.VISIBLE);
                goods_common_attr_lv.setVisibility(View.GONE);
                layout_aftersale.setVisibility(View.GONE);
                tv_tab_1.setTextColor(Color.parseColor("#F23030"));
                tv_tab_2.setTextColor(Color.parseColor("#666666"));
                tv_tab_3.setTextColor(Color.parseColor("#666666"));
                break;
            case R.id.tv_tab_2:
                layout_aftersale.setVisibility(View.GONE);
                goods_desc_wv.setVisibility(View.GONE);
                goods_common_attr_lv.setVisibility(View.VISIBLE);
                tv_tab_2.setTextColor(Color.parseColor("#F23030"));
                tv_tab_1.setTextColor(Color.parseColor("#666666"));
                tv_tab_3.setTextColor(Color.parseColor("#666666"));
                break;
            case R.id.tv_tab_3:
                tv_tab_3.setTextColor(Color.parseColor("#F23030"));
                tv_tab_1.setTextColor(Color.parseColor("#666666"));
                tv_tab_2.setTextColor(Color.parseColor("#666666"));
                goods_desc_wv.setVisibility(View.GONE);
                goods_common_attr_lv.setVisibility(View.GONE);
                layout_aftersale.setVisibility(View.VISIBLE);
                break;
            case R.id.im_service_more:
                if (ser_list != null) {
                    commodityDetailsPranster.showPop(v, "服务说明", ser_list, 1, GoodLuckDetailsAty.this);
                }
                break;
            case R.id.tv_quxiao://促销弹框
                commodityDetailsPranster.showCXPop(v, GoodLuckDetailsAty.this, promotion);
                break;
            case R.id.tv_lingquan:
                commodityDetailsPranster.showLQPop(v, "领券", GoodLuckDetailsAty.this, theTrickAdapter);
                break;
            case R.id.layout_layout_settings:// 已选商品配置,
                if ("1".equals(groupType)) {
                    toExAttars(v, 4, "3", goods_id + "-" + mellInfoBean.getMerchant_id(), goodsInfo.getGoods_img(),
                            goodsInfo.getShop_price(), group_buy_id, goods_attr_first, first_val, is_attr, groupType);
                } else {
                    toAttrs(v, 4, "3", goods_id + "-" + mellInfoBean.getMerchant_id(), goodsInfo.getGoods_img(),
                            goodsInfo.getShop_price(), group_buy_id, goods_attr_first, first_val, is_attr);
                }
                break;
            case R.id.layout_djq:
                commodityDetailsPranster.showDjqPop(v, dj_ticket, GoodLuckDetailsAty.this, vouchers_desc);
                break;
            case R.id.tv_dpg:
                Bundle bundle1 = new Bundle();
                bundle1.putString("goods_id", goods_id);
                startActivity(aty_collocations.class, bundle1);
                break;
            case R.id.tv_expirationdateLayout: {
                goodLuckPranster.showExperiencePopWindow(GoodLuckDetailsAty.this, v, ex_stringBuffer);
            }
            break;
            case R.id.pdMoreLLayout: {
                //参团查看更多(参团弹窗)
                goodLuckPranster.showCollagePop(v,"正在拼单",groupList,groupType,GoodLuckDetailsAty.this,group_count);
            }
            break;
        }
    }


    /**
     * 轮播图
     */
    private void forBanner() {
        online_carvouse_view.setImageListener(imageListener);
        online_carvouse_view.setPageCount(image.size());
    }

    /**
     * 轮播图的点击事件
     */
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(final int position, ImageView imageView) {
            Glide.with(GoodLuckDetailsAty.this).load(image.get(position).getPath())
                    .override(Settings.displayWidth, Settings.displayWidth)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .centerCrop()
                    .into(imageView);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    };

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_good_luck_details;
    }

    @Override
    protected void initialized() {
        groupBuyPst = new GroupBuyPst(this);
        collectPst = new UserCollectPst(this);
        group_buy_id = getIntent().getStringExtra("group_buy_id");
        image = new ArrayList<>();
        posts = new ArrayList<>();
        // 优惠券列表
        ticketList = new ArrayList<>();
        // 商家活动列表
        promotion = new ArrayList<>();
        // 商家等级
        imageViews = new ArrayList<>();
        // 别人在开团列表
        groupList = new ArrayList<>();

        imageViews.add(level_1_iv);
        imageViews.add(level_2_iv);
        imageViews.add(level_3_iv);
        imageViews.add(level_4_iv);
        imageViews.add(level_5_iv);

        postAdapter = new PostAdapter(this, posts);
        // 商家logo大小
        size = ToolKit.dip2px(this, 80);
        // 买家头像(评论)
        mSize = ToolKit.dip2px(this, 60);
    }

    @Override
    protected void requestData() {
        groupBuyPst.groupBuyInfo(group_buy_id, page);
        ticket_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("ticket_buy_id", ticket.get(position).getGoods_id());
                bundle.putInt("from", 1);
                startActivity(TicketGoodsDetialsAty.class, bundle);
            }
        });
        ProUrbAreaUtil.gainInstance().checkData((WeApplication) getApplication());
        showProgressDialog();
    }


    private String groupType;

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        removeProgressDialog();
        if (requestUrl.contains("freight")) {
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            map = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            ChangeTextViewStyle.getInstance().forTextColor(this, freight_tv,
                     map.get("pay"), 0, Color.parseColor("#FD8214"));
        }
        if (requestUrl.contains("groupBuyInfo")) {
            ObserTool.gainInstance().jsonToBean(jsonStr, GoodLuckBean.class, new ObserTool.BeanListener() {
                @Override
                public void returnObj(Object t) {
                    GoodLuckBean goodLuckBean = (GoodLuckBean) t;
                    final DataBean dataBean = goodLuckBean.getData();
                    remarks.setText(dataBean.getRemarks());
                    goodsInfo = dataBean.getGoodsInfo();
                    image = dataBean.getGoods_banner();
                    share_url = dataBean.getShare_url();
                    share_img = dataBean.getShare_img();
                    share_content = dataBean.getShare_content();
                    goods_name = dataBean.getGoodsInfo().getGoods_name();
                    // 团购商品轮播图
                    if (!ListUtils.isEmpty(image)) {
                        forBanner();
                    }
                    goods_attr_first = dataBean.getFirst_list();
                    first_val = dataBean.getFirst_val();
                    is_attr = dataBean.getIs_attr();
                    is_attr = is_attr + "-999";
                    vouchers_desc = dataBean.getVouchers_desc();
//                    List<GoodsAttrBean> goodsAttrs = dataBean.getGoods_attr();
                    groupPrice = dataBean.getGroup_price();//todo  返回参数有误
//                    List<ProductBean> goods_produc =dataBean.getProduct();
                    goods_id = goodsInfo.getGoods_id();
                    String tx = DemoApplication.getInstance().getLocInfo().get("province")
                            + "," + DemoApplication.getInstance().getLocInfo().get("city") + "," + DemoApplication.getInstance().getLocInfo().get("district");
                    tv_chose_ads.setText(tx);
                    Freight.freight(goods_id, tx, GoodLuckDetailsAty.this);
                    showProgressDialog();
                    /**判断这块儿显示和隐藏
                     * "is_new_goods": "1",//是否是新品  0不是 1是
                     "is_new_goods_desc": "此件商品是旧货八五成新",//新品描述
                     "is_end": "0",//是否临期 0未临期 1临期
                     "is_end_desc": "此商品属于临期商品，商品保质期到期日为2017-20-30",//临期描述
                     1试用品拼单 2常规拼单",
                     */
                    groupType = dataBean.getGroup_type();
                    if ("1".equals(groupType)) {  //体验拼单
                        layout_pt.setVisibility(View.GONE); // 隐藏开团列表

                        online_carvouse_view.setPageColor(0);//设置banner圆点非可见
                        online_carvouse_view.setFillColor(0);
                        online_carvouse_view.setStrokeColor(0);

                        groupList = (List<GroupBean>) dataBean.getGroup();//设置上面的倒计时
                        if (null != groupList && groupList.size() == 1) {
                            GroupBean groupBean = groupList.get(0);

                            tyLayout.setVisibility(View.VISIBLE);
                            tyLayout.getBackground().setAlpha(125);

                            Calendar calendar = Calendar.getInstance();
                            long endTrueTime = Long.parseLong(groupBean.getEnd_true_time());
                            long sysTime = Long.parseLong(groupBean.getSys_time());
                            long endTime = Long.parseLong(groupBean.getEnd_time());
                            if (!android.text.TextUtils.isEmpty(groupBean.getSys_time()))
                                calendar.setTimeInMillis(sysTime);

                            // 当前时间
                            long now_time = calendar.getTimeInMillis();
                            // 剩余时间
                            long last_time = endTime - now_time;
                            long last_endTime = endTrueTime - now_time;
                            // 开始倒计时
                            good_count_down_view.setConvertDaysToHours(true);
                            if (last_time < 0) {
                                last_time = 0;
                            }
                            good_count_down_view.start(last_time * 1000);
//                            good_count_down_view.start(24*60*60*2 * 1000);

                            //设置提示信息
                            List<String> memoList = groupBean.getMemo();//设置提示信息
                            ex_stringBuffer = new StringBuffer();
                            for (String str : memoList
                                    ) {
                                ex_stringBuffer.append(str);
                                ex_stringBuffer.append("\n");
                            }
                            tv_expirationdate.setText(ex_stringBuffer);
                        }
                    } else if (goodsInfo.getIs_new_goods().equals("0") && goodsInfo.getIs_end().equals("1")) {
                        tv_expirationdate.setText(goodsInfo.getIs_new_goods_desc() + "\n" + goodsInfo.getIs_end_desc());
                    } else if (goodsInfo.getIs_new_goods_desc().equals("0")) {
                        tv_expirationdate.setText(goodsInfo.getIs_new_goods_desc());
                    } else if (goodsInfo.getIs_end().equals("1")) {
                        tv_expirationdate.setText(goodsInfo.getIs_end_desc());
                    } else {
                        tv_expirationdateLayout.setVisibility(View.GONE);
                    }
                    // 售价
                    //   ChangeTextViewStyle.getInstance().forGoodsPrice(this, now_price_tv, "￥" + goodsInfo.getShop_price());
                    now_price_tv.setText(goodsInfo.getShop_price());
                    old_price_tv.setText("已团" + dataBean.getTotal() + "件");
//            // 市场价
//            old_price_tv.setText("￥" + goodsInfo.getMarket_price());
//            old_price_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

                    // 积分
                    ChangeTextViewStyle.getInstance().forTextColor(GoodLuckDetailsAty.this, goods_profit_num_tv,
                            "积分" + goodsInfo.getIntegral(), 2, Color.parseColor("#FD8214"));

                    // 名称
                    goods_details_name_tv.setText(goodsInfo.getGoods_name());

                    // 运费
                    ChangeTextViewStyle.getInstance().forTextColor(GoodLuckDetailsAty.this, freight_tv,
                            "运费10元", 2, Color.parseColor("#FD8214"));

                    goods_brief_tv.loadDataWithBaseURL(null, goodsInfo.getGoods_brief(), "text/html", "utf-8", null);
                    goods_desc_wv.loadDataWithBaseURL(null, goodsInfo.getGoods_desc(), "text/html", "utf-8", null);
                    commodityDetailsPranster.setBitmap(GoodLuckDetailsAty.this, goodsInfo.getCountry_logo(), im_country_logo);
                    commodityDetailsPranster.setTextContent(goodsInfo.getCountry_desc(), tv_country_desc);
                    commodityDetailsPranster.setTextContent(goodsInfo.getCountry_tax() + "元", tv_country_tax);
                    tv_country_desc.setText(goodsInfo.getCountry_desc());
                    tv_country_tax.setText(goodsInfo.getCountry_tax() + "元");
                    if (Double.parseDouble(goodsInfo.getCountry_tax()) <= 0) {
                        layou_jinkoushui.setVisibility(View.GONE);
                    }
                    if (null != goodsInfo.getDj_ticket() && goodsInfo.getDj_ticket().size() > 0) {
                        dj_ticket = (ArrayList<DjTicketBean>) goodsInfo.getDj_ticket();
                        for (int i = 0; i < dj_ticket.size(); i++) {
                            if (i == 2) {
                                break;
                            }
                            switch (i) {
                                case 0: {
                                    layout_djq0.setVisibility(View.VISIBLE);
                                    tv_djq_desc0.setText(dj_ticket.get(i).getDiscount_desc());
                                    break;
                                }
                                case 1: {
                                    layout_djq1.setVisibility(View.VISIBLE);
                                    tv_djq_desc1.setText(dj_ticket.get(i).getDiscount_desc());
                                    break;
                                }
                                case 2: {
                                    layout_djq2.setVisibility(View.GONE);
                                    tv_djq_desc2.setText(dj_ticket.get(i).getDiscount_desc());
                                    break;
                                }
                            }

                            switch (dj_ticket.get(i).getType()) {
                                case "0": {
                                    //  tv_djq_color0.setBackgroundColor(Color.parseColor("#FF534C"));
                                    tv_djq_color0.setBackgroundResource(R.drawable.shape_red_bg);
                                }
                                break;
                                case "1": {
                                    tv_djq_color1.setBackgroundResource(R.drawable.shape_yellow_bg);
                                }
                                break;
                                case "2": {
                                    tv_djq_color2.setBackgroundResource(R.drawable.shape_blue_bg);
                                }

                                break;
                            }

                        }
                    } else {
                        layout_djq.setVisibility(View.GONE);
                    }

                    if (null != goodsInfo.getGoods_active() && goodsInfo.getGoods_active().size() > 0) {
                        final ArrayList<GoodsActiveBean> qkk_list = (ArrayList<GoodsActiveBean>) goodsInfo.getGoods_active();
                        lv_qkk.setAdapter(new qkk_adapter(qkk_list));
                        lv_qkk.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                switch (qkk_list.get(i).getAct_type()) {
                                    case "1": {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("one_buy_id", qkk_list.get(i).getAct_id());
                                        startActivity(SnatchGoodsDetailsAty.class, bundle);
                                    }
                                    break;
                                    case "2": {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("limit_buy_id", qkk_list.get(i).getAct_id());
                                        bundle.putInt("type", 2);//无界预购
                                        startActivity(LimitGoodsAty.class, bundle);
                                    }
                                    break;
                                    case "3": {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("auction_id", qkk_list.get(i).getAct_id());
                                        startActivity(AuctionGoodsDetailsAty.class, bundle);
                                    }
                                    break;
                                    case "4": {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("limit_buy_id", qkk_list.get(i).getAct_id());
                                        bundle.putInt("type", 0);//限量购
                                        startActivity(LimitGoodsAty.class, bundle);
                                    }

                                    break;
                                    case "5": {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("group_buy_id", qkk_list.get(i).getAct_id());
                                        startActivity(GoodLuckDetailsAty.class, bundle);
                                    }

                                    break;


                                }
                            }
                        });
                    }

                    // 代金券列表
                    if (null != dataBean.getTicketList() && dataBean.getTicketList().size() > 0) {
                        List<TicketListBean> ticketListBeens = dataBean.getTicketList();
                        theTrickAdapter = new TheTrickAdapter(GoodLuckDetailsAty.this, ticketListBeens);
                        goods_trick_rv.setAdapter(theTrickAdapter);
                    } else {
                        get_a_coupon_lin_layout.setVisibility(View.GONE);
                    }

                    if (null != dataBean.getGoods_price_desc() && dataBean.getGoods_price_desc().size() > 0) {
                        goods_price_desc = (ArrayList<GoodsPriceDescBean>) dataBean.getGoods_price_desc();
                    }
                    if (null != dataBean.getGoods_server() && dataBean.getGoods_server().size() > 0) {
                        ser_list = (ArrayList<GoodsServerBean>) dataBean.getGoods_server();
                        rv_service.setAdapter(new
                                Service_adp(ser_list, 3, GoodLuckDetailsAty.this));
                    } else {
                        layout_service.setVisibility(View.GONE);
                    }


                    // 店铺信息
                    mellInfoBean = dataBean.getmInfo();
                    easemob_account = mellInfoBean.getEasemob_account();
                    merchant_logo = mellInfoBean.getLogo();
                    merchant_name = mellInfoBean.getMerchant_name();
                    mell_id = mellInfoBean.getMerchant_id();
                    Glide.with(GoodLuckDetailsAty.this).load(mellInfoBean.getLogo())
                            .override(size, size)
                            .placeholder(R.drawable.ic_default)
                            .centerCrop()
                            .error(R.drawable.ic_default)
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(the_logo_by_mell_iv);
                    title_by_mell_tv.setText(mellInfoBean.getMerchant_name());
                    int level;
                    try {
                        level = Integer.parseInt(mellInfoBean.getLevel());
                    } catch (NumberFormatException e) {
                        level = 0;
                    }
                    for (int i = 0; i < level; i++) {
                        imageViews.get(i).setVisibility(View.VISIBLE);
                    }

                    ChangeTextViewStyle.getInstance().forGoodsLineFeed(GoodLuckDetailsAty.this, all_prodect_tv,
                            mellInfoBean.getAll_goods() + "\n全部宝贝");
                    ChangeTextViewStyle.getInstance().forGoodsLineFeed(GoodLuckDetailsAty.this, all_collect_tv,
                            mellInfoBean.getView_num() + "\n人关注");
                    ChangeTextViewStyle.getInstance().forTextColor(GoodLuckDetailsAty.this, goods_describe_tv,
                            "宝贝描述" + mellInfoBean.getGoods_score(), 4, Color.parseColor("#FF0000"));
                    ChangeTextViewStyle.getInstance().forTextColor(GoodLuckDetailsAty.this, mell_serve_tv,
                            "卖家服务" + mellInfoBean.getMerchant_score(), 4, Color.parseColor("#FF0000"));
                    ChangeTextViewStyle.getInstance().forTextColor(GoodLuckDetailsAty.this, log_serve_tv,
                            "物流服务" + mellInfoBean.getShipping_score(), 4, Color.parseColor("#FF0000"));
//                    FD8214
                    // 店铺活动
                    promotion = dataBean.getPromotion();
                    if (!ListUtils.isEmpty(promotion)) {
                        promotion_layout.setVisibility(View.VISIBLE);
                        //PromotionBean promotionBean = promotion.get(0);
                        // =====单个活动设置
//                String type = promotionBean.getType();
//                int imageId = getResources().getIdentifier("icon_get_coupon_hzj_" + type, "drawable", getPackageName());
//                onle_pro_type_iv.setImageResource(imageId);
//                onle_pro_title_tv.setText(promotionBean.getTitle());
                        //  promotion.remove(promotionBean);
                        if (!ListUtils.isEmpty(promotion)) {
                            PromotionAdapter promotionAdapter = new PromotionAdapter(GoodLuckDetailsAty.this, promotion);
                            promotion_lv.setAdapter(promotionAdapter);
                            //  goods_bottom_lin_layout.setVisibility(View.VISIBLE);
                            ///  show_or_hide_iv.setEnabled(true);
                        } else {
                            //  goods_bottom_lin_layout.setVisibility(View.GONE);
                            //show_or_hide_iv.setEnabled(false);
                        }
                    } else {
                        promotion_layout.setVisibility(View.GONE);
                    }

                    // 优惠券列表
                    ticketList = dataBean.getTicketList();
//            if (!ListUtils.isEmpty(ticketList)) {
//                TheTrickAdapter theTrickAdapter = new TheTrickAdapter(this, ticketList);
//                goods_trick_rv.setAdapter(theTrickAdapter);
//            }
                    // 评论
                    if (null != dataBean.getComment()) {
                        try {
                            CommentBean comment = dataBean.getComment();
                            if (Integer.parseInt(comment.getTotal()) > 0) { // 如果有商品评价
                                layout_comment.setVisibility(View.VISIBLE); // 显示评价模块
                                all_comment_num_tv.setText("商品评价(" + comment.getTotal() + ")");
                                CommentBean commentMap = dataBean.getComment();
                                BodyBean bodyBean = comment.getBody();
                                if (bodyBean != null) {
                                    Glide.with(GoodLuckDetailsAty.this).load(bodyBean.getUser_head_pic())
                                            .override(mSize, mSize)
                                            .placeholder(R.drawable.ic_default)
                                            .error(R.drawable.ic_default)
                                            .centerCrop()
                                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                            .into(comm_user_head_iv);
                                    comm_user_name_tv.setText(bodyBean.getNickname());
                                    comm_content_tv.setText(bodyBean.getContent());

                                    if (!android.text.TextUtils.isEmpty(bodyBean.getAll_star())) {
                                        int allStar = Integer.parseInt(bodyBean.getAll_star());
                                        rb.setRating(allStar);
                                    }
                                    if (!android.text.TextUtils.isEmpty(bodyBean.getCreate_time())) {
                                        tv_date.setText(bodyBean.getCreate_time());
                                    }
                                    List<PicturesBean> pictures = bodyBean.getPictures();
                                    if (!ListUtils.isEmpty(pictures)) {
                                        CommentPicAdapter picadapter = new CommentPicAdapter(GoodLuckDetailsAty.this, pictures);
                                        estimate_pic.setAdapter(picadapter);
                                    }
                                }
                            } else { // 否则没有商品评价
                                layout_comment.setVisibility(View.GONE); // 隐藏评价模块
                            }
                        } catch (JsonSyntaxException e) {
                            all_comment_num_tv.setText("商品评价(0)");
                            comment_layout.setVisibility(View.GONE);
                            layout_comment.setVisibility(View.GONE);
                        }
                    } else {
                        comment_layout.setVisibility(View.GONE);
                        layout_comment.setVisibility(View.GONE);
                    }

                    tv_bzqd.setText(goodsInfo.getPackage_list()); //包装清单
                    tv_shfw.setText(goodsInfo.getAfter_sale_service()); //售后服务
                    tv_jgsm.setText(Html.fromHtml(dataBean.getPrice_desc())); //价格说明

                    //搭配购
                    final CheapGroupBean cheap_group = dataBean.getCheap_group();
                    if (cheap_group != null) {
                        tv_ticket_buy_discount.setText("最多可用" + cheap_group.getTicket_buy_discount() + "%代金券");
                        tv_group_price.setText("搭配价：¥" + cheap_group.getGroup_price());
                        tv_group_integral.setText(cheap_group.getIntegral());
                        double price = Double.parseDouble(cheap_group.getGoods_price()) - Double.parseDouble(cheap_group.getGroup_price());
                        DecimalFormat df = new DecimalFormat("#.00");
                        tv_goods_price.setText("立省¥" + df.format(price));
                        ArrayList<GoodsBean> maps = (ArrayList<GoodsBean>) cheap_group.getGoods();
                        rv_cheap_group.setLayoutManager(new LinearLayoutManager(GoodLuckDetailsAty.this, LinearLayoutManager.HORIZONTAL, false));
                        rv_cheap_group.setAdapter(new cg_adp(maps));
                    } else {
                        layout_cheap_group.setVisibility(View.GONE);

                    }
                    if (null != dataBean.getGuessGoodsList() && dataBean.getGuessGoodsList().size() > 0) {
                        if (page == 1) {
                            ticket = dataBean.getGuessGoodsList();
                            allGvLvAdapter1 = new AllGvLvAdapter(GoodLuckDetailsAty.this, ticket, 1);
                            ticket_gv.setAdapter(allGvLvAdapter1);
                        } else {
                            more = dataBean.getGuessGoodsList();
                            ticket.addAll(more);
                            allGvLvAdapter1.notifyDataSetChanged();
                        }
                    } else {
                        is_f = false;
                    }

                    if ("2".equals(groupType)) {
                        // 一键开团
                        creat_group_tv.setText("￥" + groupPrice + "\n发起拼单");
                        creat_group_tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {//, (ArrayList) goodsAttrs, (ArrayList) goods_produc
                                if (is_C) {
                                    Intent intent = new Intent();
                                    intent.putExtra("mid", mellInfoBean.getMerchant_id());
                                    intent.putExtra("type", "3");
                                    intent.putExtra("goods_id", goods_id);
                                    intent.putExtra("group_buy_id", group_buy_id);
                                    intent.putExtra("num", String.valueOf(goods_number));
                                    intent.putExtra("product_id", product_id);
                                    intent.putExtra("group_type", groupType);
                                    if (!android.text.TextUtils.isEmpty(order_id)) {
                                        bundle.putString("order_id", order_id);
                                    }
                                    intent.setClass(GoodLuckDetailsAty.this, BuildOrderAty.class);
                                    startActivity(intent);
                                } else {
                                    //直接购买, (ArrayList) goodsAttrs, (ArrayList) goods_product
                                    toAttrs(v, 0, "3", goods_id + "-" + mellInfoBean.getMerchant_id(), goodsInfo.getGoods_img(),
                                            goodsInfo.getShop_price(), group_buy_id, goods_attr_first, first_val, is_attr);
                                }

                            }
                        });
                    } else if ("1".equals(groupType)) {//1试用品拼单
                        creat_group_tv.setText("体验拼单");
                        creat_group_tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {//, (ArrayList) goodsAttrs, (ArrayList) goods_produc
                                GroupBean groupBean = dataBean.getGroup().get(0);
                                if (groupBean.getDiff_num().equals("0")) {
                                    showErrorTip("团已满");
                                } else {
                                    if (groupBean.getIs_member().equals("0")) {
                                        if (is_C) {
                                            Intent intent = new Intent();
                                            intent.putExtra("mid", mellInfoBean.getMerchant_id());
                                            intent.putExtra("type", "2");
                                            intent.putExtra("goods_id", goods_id);
                                            intent.putExtra("group_buy_id", group_buy_id);
                                            intent.putExtra("num", String.valueOf(goods_number));
                                            intent.putExtra("product_id", product_id);
                                            intent.putExtra("group_type", groupType);
                                            if (!android.text.TextUtils.isEmpty(order_id)) {
                                                bundle.putString("order_id", order_id);
                                            }
                                            intent.setClass(GoodLuckDetailsAty.this, BuildOrderAty.class);
                                            startActivity(intent);
                                        } else {
                                            toExAttars(v, 0, "2", goods_id + "-" + mellInfoBean.getMerchant_id(), goodsInfo.getGoods_img(),
                                                    goodsInfo.getShop_price(), group_buy_id, goods_attr_first, first_val, is_attr, groupType);
                                        }
                                    } else {
                                        showErrorTip("您已参加活动");
                                    }
                                }
                            }
                        });
                    }

                    //creat_group_tv.setText("￥" + groupBuyInfo.getData().getOne_price() + "\n一键开团");
                    // 单独购买
                    one_price_tv.setText("￥" + dataBean.getOne_price() + "\n独立购买");
                    one_price_tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {//, (ArrayList) goodsAttrs, (ArrayList) goods_produc

//                            if (is_C) {
//                                Intent intent = new Intent();
//                                intent.putExtra("mid", mellInfoBean.getMerchant_id());
//                                intent.putExtra("type", "2");
//                                intent.putExtra("goods_id", goods_id);
//                                intent.putExtra("group_buy_id", group_buy_id);
//                                intent.putExtra("num", String.valueOf(goods_number));
//                                intent.putExtra("product_id", product_id);
//                                intent.setClass(GoodLuckDetailsAty.this, BuildOrderAty.class);
//                                startActivity(intent);
//                            } else {
//                                //直接购买, (ArrayList) goodsAttrs, (ArrayList) goods_product
//                                toAttrs(v, 0, "2", goods_id + "-" + mellInfoBean.getMerchant_id(), goodsInfo.getGoods_img(),
//                                        goodsInfo.getShop_price(), group_buy_id, goods_attr_first, first_val, is_attr);
//                            }
                            //直接跳转普通商品界面 暂定
                            Bundle bundle = new Bundle();
                            bundle.putString("ticket_buy_id", goods_id);
                            bundle.putInt("from", 1);
                            startActivity(TicketGoodsDetialsAty.class, bundle);
                        }
                    });
                    int num;
                    try {
                        num = Integer.parseInt(dataBean.getCart_num());
                    } catch (NumberFormatException e) {
                        num = 0;
                    }
                    if (0 == num) {
                        user_cart_num_tv.setVisibility(View.GONE);
                    } else {
                        user_cart_num_tv.setText(String.valueOf(num));
                        user_cart_num_tv.setVisibility(View.VISIBLE);
                    }
                    // 是否收藏
                    is_collect = dataBean.getIs_collect();
                    if ("0".equals(is_collect)) {

                        commodityDetailsPranster.isCollect(is_collect, "收藏", goods_title_collect_tv, GoodLuckDetailsAty.this);
                    } else {
                        commodityDetailsPranster.isCollect(is_collect, "已收藏", goods_title_collect_tv, GoodLuckDetailsAty.this);
                    }

                    // 参团列表
                    groupList = (List<GroupBean>) dataBean.getGroup();
                    if (!ListUtils.isEmpty(groupList)) {//最多十个团
                        List<GroupBean> twoGroupBean=new ArrayList<>();
                        if(groupList.size()>2){
                            twoGroupBean.add(groupList.get(0));
                            twoGroupBean.add(groupList.get(1));
                        }else{
                            twoGroupBean.addAll(groupList);
                        }
                        group_count = dataBean.getGroup_count();
                        if (!TextUtils.isEmpty(group_count))
                            pdnumTv.setText(group_count + "人在开团");

                        // 拼团列表
                        GoodLuckAdapter goodLuckAdapter = new GoodLuckAdapter(GoodLuckDetailsAty.this, twoGroupBean, groupType);
                        good_luck_lv.setAdapter(goodLuckAdapter);
                        //Item高度80，分割线1. 我也不知道怎么获取setAdapter之后的高度。。。
                        int list_h = groupList.size() * ToolKit.dip2px(GoodLuckDetailsAty.this, 80) + groupList.size();
                        secondHeight = secondHeight + list_h;
                        topHeighe = topHeighe + list_h;
                        if ("2".equals(groupType)) {
                            // 去参团
                            goodLuckAdapter.setAdapterTextViewClickListener(new AdapterTextViewClickListener() {
                                @Override
                                public void onTextViewClick(View v, int position) {
                                    itmeClick(v,position);
                                }
                            });
                        }
                    } else {
                        layout_pt.setVisibility(View.GONE);
                    }
//            // 产品属性GoodsCommonAttr
                    if (null != dataBean.getGoods_common_attr() && dataBean.getGoods_common_attr().size() > 0) {
                        List<GoodsCommonAttrBean> gca = dataBean.getGoods_common_attr();

                        GoodsCommentAttrAdapter gcaAdapter = new GoodsCommentAttrAdapter(GoodLuckDetailsAty.this, gca);
                        goods_common_attr_lv.setAdapter(gcaAdapter);
                    }
                    // ==========团购详情End===========
                    return;
                }
            });

        }
        if (requestUrl.contains("addCollect")) {
            showRightTip("收藏成功");
            is_collect = "1";

            commodityDetailsPranster.isCollect(is_collect, "已收藏", goods_title_collect_tv, GoodLuckDetailsAty.this);
            return;
        }
        if (requestUrl.contains("delOneCollect")) {
            showRightTip("取消成功");
            is_collect = "0";

            commodityDetailsPranster.isCollect(is_collect, "收藏", goods_title_collect_tv, GoodLuckDetailsAty.this);
            return;
        }
    }

    private void getHeight() {
        goodLuckPranster.getHeight(online_carvouse_view, top_lin_layout, second_lin_layout, limit_goods_details_sc, be_back_top_iv);
    }


    @Override
    public void onBottom() {
        if (is_f) {
            page++;
            groupBuyPst.groupBuyInfo(group_buy_id, page);
        }
    }

    @Override
    public void itmeClick(View v, int position) {
        if (!Config.isLogin()) {
            toLogin();
            return;
        }
        bundle = new Bundle();
        bundle.putInt("status", 0);
        bundle.putString("goods_id", goods_id + "-" + mellInfoBean.getMerchant_id());
        //                        bundle.putParcelableArrayList("list", (ArrayList) goodsAttrs);
        //                        bundle.putParcelableArrayList("list_p", (ArrayList) goods_produc);
        bundle.putSerializable("goods_attr_first", (Serializable) goods_attr_first);
        bundle.putSerializable("first_val", (Serializable) first_val);
        bundle.putString("is_attr", is_attr);
        if (null != goodsInfo.getIntegral())
            bundle.putString("integral", goodsInfo.getIntegral());
        bundle.putString("group_buy_id", group_buy_id);
        bundle.putString("id", groupList.get(position).getId());
        startActivity(CreateGroupAty.class, bundle);
    }


    class cg_adp extends RecyclerView.Adapter<cg_adp.ViewHolder> {
        ArrayList<GoodsBean> list;

        public cg_adp(ArrayList<GoodsBean> list) {
            this.list = list;

        }

        @Override
        public cg_adp.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new cg_adp.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_dpg, null));
        }

        @Override
        public void onBindViewHolder(cg_adp.ViewHolder holder, int position) {
            Glide.with(GoodLuckDetailsAty.this).load(list.get(position).getGoods_img()).into(holder.imageview);
            holder.tv_price.setText("¥" + list.get(position).getShop_price());
            if (position == list.size() - 1) {
                holder.im_jiahao.setVisibility(View.GONE);
            } else {
                holder.im_jiahao.setVisibility(View.VISIBLE);

            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageview;
            TextView tv_price;
            ImageView im_jiahao;

            public ViewHolder(View itemView) {
                super(itemView);
                imageview = (ImageView) itemView.findViewById(R.id.imageview);
                im_jiahao = (ImageView) itemView.findViewById(R.id.imageView3);
                tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            }
        }
    }

    class qkk_adapter extends BaseAdapter {
        ArrayList<GoodsActiveBean> list;

        public qkk_adapter(ArrayList<GoodsActiveBean> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = View.inflate(GoodLuckDetailsAty.this, R.layout.item_lh_qkk, null);
            TextView act_desc = (TextView) view.findViewById(R.id.tv_qkk);
            act_desc.setText(list.get(i).getAct_desc());
            return view;
        }
    }


    @ViewInject(R.id.tv_chose_ads)
    private TextView tv_chose_ads;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0x0002 && data != null) {
            if (requestCode == 1000) {
                is_C = true;
                mellInfoBean.setMerchant_id(data.getStringExtra("mid"));

                goods_id = data.getStringExtra("goods_id");
                group_buy_id = data.getStringExtra("group_buy_id");
                order_id = data.getStringExtra("order_id");

                product_id = data.getStringExtra("product_id");
                if (data.hasExtra("group_type")) {
                    groupType = data.getStringExtra("group_type");
                }
                if (!android.text.TextUtils.isEmpty(data.getStringExtra("p_shop_price"))) {
                    one_price_tv.setText("￥" + data.getStringExtra("p_shop_price") + "\n独立购买");
                }
                goods_select_attr_tv.setText(data.getStringExtra("pro_value") + "x" + data.getIntExtra("num", 0));
                now_price_tv.setText(data.getStringExtra("shop_price"));
                old_price_tv.setText("￥" + data.getStringExtra("market_price"));
                old_price_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                creat_group_tv.setText("￥" + data.getStringExtra("shop_price") + "\n发起拼单");
                ChangeTextViewStyle.getInstance().forTextColor(this, goods_profit_num_tv,
                        "积分" + data.getStringExtra("red_return_integral"), 2, Color.parseColor("#FD8214"));
//                ArrayList<Map<String, String>> dj_list = JSONUtils.parseKeyAndValueToMapList(data.getStringExtra("data"));
                dj_ticket = (ArrayList<DjTicketBean>) data.getSerializableExtra("data");
                if (dj_ticket != null) {
                    for (int i = 0; i < dj_ticket.size(); i++) {
                        if (i == 2) {
                            break;
                        }
                        switch (i) {
                            case 0: {
                                layout_djq0.setVisibility(View.VISIBLE);
                                tv_djq_desc0.setText(dj_ticket.get(i).getDiscount_desc());
                                break;
                            }
                            case 1: {
                                layout_djq1.setVisibility(View.VISIBLE);
                                tv_djq_desc1.setText(dj_ticket.get(i).getDiscount_desc());
                                break;
                            }
                            case 2: {
                                layout_djq2.setVisibility(View.GONE);
                                tv_djq_desc2.setText(dj_ticket.get(i).getDiscount_desc());
                                break;
                            }
                        }
                        switch (dj_ticket.get(i).getType()) {
                            case "0": {
                                //  tv_djq_color0.setBackgroundColor(Color.parseColor("#FF534C"));
                                tv_djq_color0.setBackgroundResource(R.drawable.shape_red_bg);
                            }
                            break;
                            case "1": {
                                tv_djq_color1.setBackgroundResource(R.drawable.shape_yellow_bg);
                            }
                            break;
                            case "2": {
                                tv_djq_color2.setBackgroundResource(R.drawable.shape_blue_bg);
                            }

                            break;
                        }

                    }
                } else {
                    layout_djq.setVisibility(View.GONE);
                }
                goods_number = data.getIntExtra("num", 0);
                product_id = data.getStringExtra("product_id");
                L.e("product_id=" + data.getStringExtra("product_id") + "\n" +
                        "pro_value=" + data.getStringExtra("pro_value") + "\n" +
                        "num=" + data.getIntExtra("num", 0) + "\n" +
                        "shop_price=" + data.getStringExtra("shop_price") + "\n" +
                        "market_price=" + data.getStringExtra("market_price") + "\n" +
                        "red_return_integral=" + data.getStringExtra("red_return_integral") + "\n" +
                        "discount=" + data.getStringExtra("discount") + "\n" +
                        "yellow_discount=" + data.getStringExtra("yellow_discount") + "\n" +
                        "blue_discount=" + data.getStringExtra("blue_discount") + "\n" +
                        "wy_price=" + data.getStringExtra("wy_price") + "\n" +
                        "wy_price=" + data.getStringExtra("wy_price") + "\n");
            }

        } else if (requestCode == 1000) {
            L.e("返回商品详情");
            if (resultCode == 0x0001) {
                Bundle bundle = new Bundle();
                bundle.putString("mid", data.getStringExtra("mid"));
                bundle.putString("type", data.getStringExtra("type"));
                bundle.putString("goods_id", data.getStringExtra("goods_id"));
                bundle.putString("group_buy_id", data.getStringExtra("group_buy_id"));
                String order_id = data.getStringExtra("order_id");
                if (!android.text.TextUtils.isEmpty(order_id)) {
                    bundle.putString("order_id", order_id);
                }
                bundle.putString("num", data.getStringExtra("num"));
                bundle.putString("product_id", data.getStringExtra("product_id"));
                if (data.hasExtra("group_type"))
                    bundle.putString("group_type", data.getStringExtra("group_type"));
                startActivity(BuildOrderAty.class, bundle);
            }
        }

    }

    @Override
    protected void onDestroy() {
        List<WebView> webViewList = new ArrayList<>();
        webViewList.add(goods_desc_wv);
        webViewList.add(goods_brief_tv);
        destoryWebViews(webViewList);
        super.onDestroy();
    }
}
