package com.txd.hzj.wjlp.mellonLine.gridClassify;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ants.theantsgo.WeApplication;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.listenerForAdapter.AdapterTextViewClickListener;
import com.ants.theantsgo.tips.CustomDialog;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.tools.ObserTool;
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
import com.txd.hzj.wjlp.bean.commodity.GoodsBannerBean;
import com.txd.hzj.wjlp.bean.commodity.GoodsBean;
import com.txd.hzj.wjlp.bean.commodity.GoodsCommonAttrBean;
import com.txd.hzj.wjlp.bean.commodity.GoodsInfoBean;
import com.txd.hzj.wjlp.bean.commodity.GoodsPriceDescBean;
import com.txd.hzj.wjlp.bean.commodity.GoodsServerBean;
import com.txd.hzj.wjlp.bean.commodity.GroupBean;
import com.txd.hzj.wjlp.bean.commodity.GroupRankBean;
import com.txd.hzj.wjlp.bean.commodity.MInfoBean;
import com.txd.hzj.wjlp.bean.commodity.PicturesBean;
import com.txd.hzj.wjlp.bean.commodity.PromotionBean;
import com.txd.hzj.wjlp.bean.commodity.TicketListBean;
import com.txd.hzj.wjlp.http.collect.UserCollectPst;
import com.txd.hzj.wjlp.http.groupbuy.GroupBuyPst;
import com.txd.hzj.wjlp.mainfgt.adapter.AllGvLvAdapter;
import com.txd.hzj.wjlp.mellonLine.adapter.GoodLuckAdapter;
import com.txd.hzj.wjlp.mellonLine.adapter.GoodsCommentAttrAdapter;
import com.txd.hzj.wjlp.mellonLine.adapter.LuckAdapter;
import com.txd.hzj.wjlp.mellonLine.adapter.PostAdapter;
import com.txd.hzj.wjlp.mellonLine.adapter.PromotionAdapter;
import com.txd.hzj.wjlp.mellonLine.adapter.TheTrickAdapter;
import com.txd.hzj.wjlp.mellonLine.gridClassify.adapter.CommentPicAdapter;
import com.txd.hzj.wjlp.minetoaty.order.OnlineShopAty;
import com.txd.hzj.wjlp.new_wjyp.Collocations_aty;
import com.txd.hzj.wjlp.shoppingCart.BuildOrderAty;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;
import com.txd.hzj.wjlp.tool.proUrbArea.ProUrbAreaUtil;
import com.txd.hzj.wjlp.view.ObservableScrollView;
import com.txd.hzj.wjlp.view.ToastView;
import com.txd.hzj.wjlp.view.VpSwipeRefreshLayout;
import com.yanzhenjie.permission.AndPermission;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;
import cn.iwgang.countdownview.CountdownView;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/10 0010
 * 时间：上午 11:00
 * 描述：拼团详情
 */
public class GoodLuckDetailsAty extends BaseAty implements ObservableScrollView.onBottomListener, CommodityDetailsInter.GoodLuckView, ProUrbAreaUtil.CallBack {
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

    private ArrayList<String> mImageUrls;

    /**
     * 现价
     */
    @ViewInject(R.id.now_price_tv)
    private TextView now_price_tv;
    /**
     * 原价 （已修改为已参与多少人）
     */
    @ViewInject(R.id.old_price_tv)
    private TextView old_price_tv;

    /**
     * 单买价格 （价格下面画线的那个字段）
     */
    @ViewInject(R.id.old_money_tv)
    private TextView old_money_tv;

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
    //    体验拼单进度条
    @ViewInject(R.id.experiencePb)
    private ProgressBar experiencePb;
    @ViewInject(R.id.progress_tv)
    private TextView progress_tv;

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
    //    @ViewInject(R.id.goods_for_my_evaluste_layout)
    //    private LinearLayout goods_for_my_evaluste_layout;

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
    private String merchant_phone = "";
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

    @ViewInject(R.id.luckLayout) //手气排行榜
    private LinearLayout luckLayout;
    @ViewInject(R.id.luckMoreLayout)
    private RelativeLayout luckMoreLayout;
    @ViewInject(R.id.lv_ranking)
    private ListViewForScrollView lv_ranking;

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
    @ViewInject(R.id.tv_brief)
    private TextView tv_brief;

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
    @ViewInject(R.id.seeMore)
    private View seeMore;
    @ViewInject(R.id.layout_pt)
    private LinearLayout layout_pt;//活动倒计时|别人在开团
    @ViewInject(R.id.remarks)
    private TextView remarks;
    @ViewInject(R.id.max_num_tv)
    private TextView max_num_tv;//成单所需x人
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
    //    private String groupPrice;
    private String goods_name;
    private GoodLuckDetailsPranster goodLuckPranster;

    @ViewInject(R.id.title_goods_layout)
    private View title_goods_layout;

    @ViewInject(R.id.title_details_layout)
    private View title_details_layout;

    @ViewInject(R.id.title_evaluate_layout)
    private View title_evaluate_layout;
    private List<String> expStrList;
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

    @ViewInject(R.id.limitGoodsDetials_superRefesh_ssrl)
    private VpSwipeRefreshLayout limitGoodsDetials_superRefesh_ssrl;
    @ViewInject(R.id.toastView)
    private ToastView toastView;
    // 刷新头部
    private RelativeLayout head_container;
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;
    private String a_id; // 此变量虽然不知道什么意思，但是在请求接口groupBuyInfo的时候会用到
    private String onePrice;//单买价
    private Dialog dialog;
    private String title;//体验拼单规则
    //是否参与了拼手气活动
    private boolean isPartPinShouQi = false;
    private String mGroupBeanId;

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
        //        goods_for_my_evaluste_layout.setVisibility(View.GONE);

        wujie_post_lv.setAdapter(postAdapter);
        // 判断是否显示回到顶部按钮
        //        getHeight();
        rv_service.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        // 优惠券
        goods_trick_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        goods_trick_rv.setHasFixedSize(true);
        //        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        goodLuckPranster = new GoodLuckDetailsPranster(this);
        goodLuckPranster.setGoodLuckView(GoodLuckDetailsAty.this);
        commodityDetailsPranster = new CommodityDetailsPranster(this);
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
        if (hasFocus && progress_tv.getVisibility() == View.VISIBLE) {
            ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(progress_tv.getLayoutParams());
            margin.leftMargin = (int) (experiencePb.getWidth() * current / max) + experiencePb.getLeft() - progress_tv.getWidth() / 2;
            if (margin.leftMargin < 0) {
                margin.leftMargin = experiencePb.getLeft();
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(margin);
            progress_tv.setLayoutParams(layoutParams);
        }

    }

    @Override
    @OnClick({
            R.id.goods_title_collect_layout, R.id.goods_title_share_tv, R.id.show_or_hide_iv, R.id.tv_dpg,
            R.id.show_or_hide_lv_iv, R.id.show_or_hide_explain_iv, R.id.to_cart_layouts,
            R.id.creat_group_tv, R.id.go_to_main_layouts, R.id.details_into_mell_tv, R.id.to_chat_tv,
            R.id.tv_chose_ads, R.id.all_evaluate_tv,
            R.id.im_service_more, R.id.tv_tab_1, R.id.tv_tab_2, R.id.tv_tab_3, R.id.tv_gwc, R.id.tv_ljgm, R.id.btn_jgsm,
            R.id.tv_quxiao, R.id.tv_lingquan, R.id.tv_showClassify, R.id.layout_layout_settings, R.id.layout_djq,R.id.luckMoreLayout, R.id.tv_expirationdateLayout, R.id.pdMoreLLayout
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
                ProUrbAreaUtil.gainInstance().showPickerView((TextView) v, goods_id, String.valueOf(goods_number), product_id, GoodLuckDetailsAty.this, GoodLuckDetailsAty.this);

                break;
            case R.id.tv_showClassify://查看分类
                //                Intent intent = new Intent();
                //                intent.putExtra("appBarTitle", goodsInfo.getTwo_cate_name());
                //                intent.putExtra("two_cate_id", goodsInfo.getCate_id());
                //                intent.setClass(this, SubclassificationAty.class);
                //                startActivity(intent);
                toClassify(v, goodsInfo.getTop_cate_id());
                break;
            case R.id.goods_title_collect_layout://收藏

                if (!Config.isLogin()) {
                    toLogin();
                    return;
                }
                if ("0".equals(is_collect)) {
                    collectPst.addCollect("1", goods_id);
                    break;
                }
                collectPst.delOneCollect("1", goods_id);

                break;
            case R.id.goods_title_share_tv://分享
                //                toShare("无界优品", share_img, share_url, share_content, goods_id, "1");
                if("1".equals(groupType)){
                    toShare(goods_name, share_img, "1", share_content, group_buy_id, "10:"+goods_id);
                }else if ("2".equals(groupType)){
                    toShare(goods_name, share_img, "1", share_content, group_buy_id, "9:"+goods_id);
                }

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
                goodLuckPranster.chat_merchant(mell_id, GoodLuckDetailsAty.this, merchant_phone);
                //                toChat(easemob_account, merchant_logo, merchant_name);
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
            case R.id.luckMoreLayout:
                Bundle bundle2 = new Bundle();
                bundle2.putString("a_id", mGroupBeanId);
                startActivity(LuckRakingListAty.class, bundle2);
                break;
            case R.id.tv_dpg:
                Bundle bundle1 = new Bundle();
                bundle1.putString("goods_id", goods_id);
                startActivity(Collocations_aty.class, bundle1);
                break;
            case R.id.tv_expirationdateLayout: {
                goodLuckPranster.showExperiencePopWindow(GoodLuckDetailsAty.this, v, expStrList, title);
            }
            break;
            case R.id.pdMoreLLayout: {
                //参团查看更多(参团弹窗)
                goodLuckPranster.showCollagePop(v, "正在拼单", groupList, groupType, GoodLuckDetailsAty.this, group_count);
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
        a_id = getIntent().getStringExtra("a_id");
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
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(group_buy_id)) {
            showProgressDialog();
            groupBuyPst.groupBuyInfo(group_buy_id, 1, a_id);
        }
    }

    @Override
    protected void requestData() {
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
        limitGoodsDetials_superRefesh_ssrl.setHeaderView(createHeaderView());// add headerView
        limitGoodsDetials_superRefesh_ssrl.setHeaderViewBackgroundColor(Color.WHITE);
        limitGoodsDetials_superRefesh_ssrl.setTargetScrollWithLayout(true);
        limitGoodsDetials_superRefesh_ssrl.setOnPullRefreshListener(new VpSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                textView.setText("正在刷新");
                imageView.setVisibility(View.GONE);
                textView.setText("正在刷新");
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                groupBuyPst.groupBuyInfo(group_buy_id, 1, a_id);
                goodLuckPranster.goodsMsg(toastView);
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
        if (null == goodLuckPranster) {
            goodLuckPranster = new GoodLuckDetailsPranster(this);
        }
        goodLuckPranster.goodsMsg(toastView);
    }

    private View createHeaderView() {
        View headerView = LayoutInflater.from(limitGoodsDetials_superRefesh_ssrl.getContext()).inflate(R.layout.layout_head, null);
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


    private String groupType;

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        limitGoodsDetials_superRefesh_ssrl.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
    }

    private int current, max;

    @Override
    public void onComplete(String requestUrl, final String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        removeProgressDialog();

        limitGoodsDetials_superRefesh_ssrl.setRefreshing(false);
        progressBar.setVisibility(View.GONE);

        if (requestUrl.contains("groupBuyInfo")) {
            L.e("groupBuyInfo:" + jsonStr);
            ObserTool.gainInstance().jsonToBean(jsonStr, GoodLuckBean.class, new ObserTool.BeanListener() {
                @Override
                public void returnObj(Object t) {
                    GoodLuckBean goodLuckBean = (GoodLuckBean) t;


                    final DataBean dataBean = goodLuckBean.getData();
                    remarks.setText(dataBean.getRemarks());
                    goodsInfo = dataBean.getGoodsInfo();
                    if (TextUtils.isEmpty(goodsInfo.getGoods_brief())) {
                        tv_brief.setVisibility(View.GONE);
                    } else {
                        tv_brief.setVisibility(View.VISIBLE);
                        tv_brief.setText(goodsInfo.getGoods_brief());
                    }
                    /**
                     *以下表示如果buy_status==0，表示当前商品已经下架
                     * */
                    String buyStatusStr = goodsInfo.getBuy_status();
                    if (!TextUtils.isEmpty(buyStatusStr) && buyStatusStr.equals("0")) {
                        CustomDialog.Builder dialog = new CustomDialog.Builder(GoodLuckDetailsAty.this);
                        dialog.setCancelable(false);
                        dialog.setMessage("当前商品已下架");
                        dialog.setTitle("下架提示");
                        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                GoodLuckDetailsAty.this.finish();
                            }
                        });
                        dialog.create().show();
                    }
                    image = dataBean.getGoods_banner();
                    share_url = dataBean.getShare_url();
                    share_img = dataBean.getShare_img();
                    share_content = dataBean.getGoodsInfo().getGoods_brief();
                    goods_name = dataBean.getGoodsInfo().getGoods_name();
                    // 团购商品轮播图
                    if (!ListUtils.isEmpty(image)) {
                        mImageUrls=new ArrayList<>();
                        for (int i = 0; i < image.size(); i++) {
                            mImageUrls.add(image.get(i).getPath());
                        }
                        forBanner();
                    }
                    goods_attr_first = dataBean.getFirst_list();
                    first_val = dataBean.getFirst_val();
                    is_attr = dataBean.getIs_attr();
                    is_attr = is_attr + "-999";
                    vouchers_desc = dataBean.getVouchers_desc();
                    goods_id = goodsInfo.getGoods_id();
                    max_num_tv.setVisibility(View.VISIBLE);
                    max_num_tv.setText("成单所需" + goodsInfo.getGroup_num() + "人");
                    showProgressDialog();
                    /**判断这块儿显示和隐藏
                     * "is_new_goods": "1",//是否是新品  0不是 1是
                     "is_new_goods_desc": "此件商品是旧货八五成新",//新品描述
                     "is_end": "0",//是否临期 0未临期 1临期
                     "is_end_desc": "此商品属于临期商品，商品保质期到期日为2017-20-30",//临期描述
                     1试用品拼单 2常规拼单",
                     */
                    groupType = dataBean.getGroup_type();
                    tv_expirationdateLayout.setEnabled(false);
                    seeMore.setVisibility(View.GONE);
                    if ("1".equals(groupType)) {  //体验拼单
                        experiencePb.setVisibility(View.VISIBLE);
                        max = Integer.parseInt(goodsInfo.getGroup_num());
                        current = Integer.parseInt(dataBean.getTotal());
                        experiencePb.setMax(max);
                        experiencePb.setProgress(current);
                        progress_tv.setVisibility(View.VISIBLE);
                        progress_tv.setText((int) ((100.0f * current) / max) + "%");
                        layout_pt.setVisibility(View.GONE); // 隐藏开团列表
                        online_carvouse_view.setPageColor(0);//设置banner圆点非可见
                        online_carvouse_view.setFillColor(0);
                        online_carvouse_view.setStrokeColor(0);
                        groupList = (List<GroupBean>) dataBean.getGroup();//设置上面的倒计时
                        if (null != groupList && groupList.size() == 1) {
                            GroupBean groupBean = groupList.get(0);
                            mGroupBeanId = groupBean.getId();
                            if (groupBean.getDiff_num().equals("0") || !groupBean.getIs_member().equals("0")) {
                                creat_group_tv.setBackgroundColor(Color.parseColor("#9D9D9D"));
                                isPartPinShouQi = true;
                            } else {
                                creat_group_tv.setBackgroundColor(getResources().getColor(R.color.red_tv_back));
                                isPartPinShouQi = false;
                            }
                            tyLayout.setVisibility(View.VISIBLE);
                            tyLayout.getBackground().setAlpha(180);

                            Calendar calendar = Calendar.getInstance();
                            long endTrueTime = Long.parseLong(groupBean.getEnd_true_time());
                            long sysTime = Long.parseLong(groupBean.getSys_time());
                            long endTime = Long.parseLong(groupBean.getEnd_time());
                            if (!TextUtils.isEmpty(groupBean.getSys_time())) {
                                calendar.setTimeInMillis(sysTime);
                            }

                            // 当前时间
                            long now_time = calendar.getTimeInMillis();
                            // 剩余时间
                            long last_time = endTime - now_time;
                            long last_endTime = endTrueTime - now_time;
                            // 开始倒计时
                            //                            good_count_down_view.setConvertDaysToHours(true);
                            if (last_time < 0) {
                                last_time = 0;
                            }
                            good_count_down_view.start(last_time * 1000);

                            //设置提示信息
                            List<String> memoList = groupBean.getMemo();//设置提示信息

                            expStrList = new ArrayList<>();
                            StringBuffer ex_stringBuffer = new StringBuffer();
                            title = memoList.get(0);
                            for (int i = 0; i < memoList.size(); i++) {
                                String str = memoList.get(i);
                                ex_stringBuffer.append(str);
                                ex_stringBuffer.append("<br/>");
                                if (i > 0) {
                                    expStrList.add(str);
                                }
                            }
                            String source = ex_stringBuffer.toString();
                            tv_expirationdate.setText(Html.fromHtml(source));
                        }
                        tv_expirationdateLayout.setEnabled(true);
                        seeMore.setVisibility(View.VISIBLE);
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
                    now_price_tv.setText(goodsInfo.getShop_price());
                    if ("1".equals(groupType)) {//体验拼单
                        goods_profit_num_tv.setVisibility(View.GONE);
                        old_price_tv.setText("已参与" + dataBean.getTotal() + "人");
                    } else {
                        old_price_tv.setText("已拼" + dataBean.getTotal() + "件");
                    }
                    // TODO =============================================设置值===============================================================
                    try {
                        JSONObject jsonObject = new JSONObject(jsonStr);
                        JSONObject dataObject = jsonObject.getJSONObject("data");
                        String oldMoneyStr = "";
                        if (dataObject.has("goodsInfo")) {
                            JSONObject object = dataObject.getJSONObject("goodsInfo");
                            oldMoneyStr = object.has("market_price") ? object.getString("market_price") : "";
                        }
                        //                        String oldMoneyStr = dataObject.has("one_price") ? dataObject.getString("one_price") : "";
                        old_money_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        old_money_tv.setText(oldMoneyStr.equals("") ? "" : "¥" + oldMoneyStr);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //            // 市场价
                    //            old_price_tv.setText("¥" + goodsInfo.getMarket_price());
                    //            old_price_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

                    // 积分
                    ChangeTextViewStyle.getInstance().forTextColor(GoodLuckDetailsAty.this, goods_profit_num_tv,
                            "积分" + goodsInfo.getIntegral(), 2, getResources().getColor(R.color.red_tv_back));
                    //                    FD8214

                    // 名称
                    goods_details_name_tv.setText(goodsInfo.getGoods_name());

                    String tx = DemoApplication.getInstance().getLocInfo().get("province")
                            + "," + DemoApplication.getInstance().getLocInfo().get("city") + "," + DemoApplication.getInstance().getLocInfo().get("district");
                    tv_chose_ads.setText(tx);
                    // 运费
                    tv_chose_ads.setText(tx);
                    goodLuckPranster.freight(goods_id, tx, String.valueOf(goods_number), product_id);
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
                    if ("1".equals(groupType)){
                        layout_djq.setVisibility(View.GONE);
                        luckLayout.setVisibility(View.VISIBLE);
                        GroupRankBean groupRankBean = dataBean.getGroup_rank();
                        List<GroupRankBean.RankBean> rank = groupRankBean.getRank_list();
                        if (rank != null) {
                            lv_ranking.setAdapter(new LuckAdapter(rank, GoodLuckDetailsAty.this));
                        }
                    }else {
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
                    }
                    lv_qkk.setVisibility(View.GONE);

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
                    merchant_phone = mellInfoBean.getMerchant_phone();
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

                                    if (!TextUtils.isEmpty(bodyBean.getAll_star())) {
                                        int allStar = Integer.parseInt(bodyBean.getAll_star());
                                        rb.setRating(allStar);
                                    }
                                    if (!TextUtils.isEmpty(bodyBean.getCreate_time())) {
                                        tv_date.setText(bodyBean.getCreate_time());
                                    }
                                    List<PicturesBean> pictures = bodyBean.getPictures();
                                    if (!ListUtils.isEmpty(pictures)) {
                                        CommentPicAdapter picadapter = new CommentPicAdapter(GoodLuckDetailsAty.this, pictures);
                                        estimate_pic.setVisibility(View.VISIBLE);
                                        estimate_pic.setAdapter(picadapter);
                                    } else {
                                        estimate_pic.setVisibility(View.GONE);
                                    }
                                }
                            } else { // 否则没有商品评价
                                layout_comment.setVisibility(View.GONE); // 隐藏评价模块
                            }
                        } catch (JsonSyntaxException e) {
                            layout_comment.setVisibility(View.GONE);
                        }
                    } else {
                        layout_comment.setVisibility(View.GONE);
                    }

                    tv_bzqd.setText(goodsInfo.getPackage_list()); //包装清单
                    tv_shfw.setText(goodsInfo.getAfter_sale_service()); //售后服务
                    //                    tv_jgsm.setText(Html.fromHtml(dataBean.getPrice_desc())); //价格说明
                    tv_jgsm.setText(dataBean.getPrice_desc()); //价格说明

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
                        String integral = goodsInfo.getIntegral();
                        if (Double.parseDouble(integral) == 0){
                            creat_group_tv.setText("发起拼单");
                        }else {
                            creat_group_tv.setText("送" +integral +"积分\n发起拼单");
                        }

                        creat_group_tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //, (ArrayList) goodsAttrs, (ArrayList) goods_produc
                                if (!Config.isLogin()) {
                                    toLogin();
                                    return;
                                }
                                if (null != groupList && groupList.size() > 0) {//如果已经有拼单
                                    showDialogs(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                            //参加别人的团
                                            goodLuckPranster.showCollagePop(v, "正在拼单", groupList, groupType, GoodLuckDetailsAty.this, group_count);
                                        }
                                    }, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            //发起拼单
                                            buy(v);
                                            dialog.dismiss();

                                        }
                                    });
                                } else {
                                    buy(v);
                                }
                            }
                        });
                    } else if ("1".equals(groupType)) {//1试用品拼单
                        if (Double.parseDouble(goodsInfo.getIntegral()) == 0){
                            creat_group_tv.setText("手气" + goodsInfo.getShop_price() + "元");
                        }else {
                            creat_group_tv.setText("送" + goodsInfo.getIntegral() + "积分\n手气" + goodsInfo.getShop_price() + "元");
                        }

                        creat_group_tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!Config.isLogin()) {
                                    toLogin();
                                    return;
                                }

                                if (!isPartPinShouQi) {
                                    if (null != dataBean && dataBean.getGroup().size() > 0) {
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
                                                    if (!TextUtils.isEmpty(order_id)) {
                                                        bundle.putString("order_id", order_id);
                                                    }
                                                    intent.setClass(GoodLuckDetailsAty.this, BuildOrderAty.class);
                                                    startActivity(intent);
                                                } else {
                                                    toExAttars(v, 0, "2", goods_id + "-" + mellInfoBean.getMerchant_id(), goodsInfo.getGoods_img(),
                                                            goodsInfo.getShop_price(), group_buy_id, goods_attr_first, first_val, is_attr, groupType);
                                                }
                                            } else {
                                                showToast("您已参与");
                                            }
                                        }
                                    }
                                } else {
                                    showPingShouQiDialog(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    }, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Bundle mBundle = new Bundle();
                                            mBundle.putString("title", "拼单购");
                                            mBundle.putString("type", "3");
                                            startActivity(OnlineShopAty.class, mBundle);
                                            dialog.dismiss();
                                        }
                                    });
                                }
                            }
                        });
                    }

                    // 单独购买

                    if (null != goodsInfo.getP_integral()) {
                        String p_integral = goodsInfo.getP_integral();
                        if ("1".equals(groupType)) {
                            onePrice = dataBean.getOne_price();
                            if (Double.parseDouble(p_integral) == 0){
                                one_price_tv.setText("单买" + onePrice + "元");
                            }else {
                                one_price_tv.setText("送" + goodsInfo.getP_integral() + "积分\n单买" + onePrice + "元");
                            }

                        } else {
                            if (Double.parseDouble(p_integral) == 0){
                                one_price_tv.setText("独立购买");
                            }else {
                                one_price_tv.setText("送" + p_integral + "积分\n独立购买");
                            }

                        }
                    }
                    one_price_tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {//, (ArrayList) goodsAttrs, (ArrayList) goods_produc
                            //直接跳转普通商品界面 暂定
                            if (!Config.isLogin()) {
                                toLogin();
                                return;
                            }
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
                        List<GroupBean> twoGroupBean = new ArrayList<>();
                        if (groupList.size() > 2) {
                            twoGroupBean.add(groupList.get(0));
                            twoGroupBean.add(groupList.get(1));
                        } else {
                            twoGroupBean.addAll(groupList);
                        }
                        group_count = dataBean.getGroup_count();
                        if (!TextUtils.isEmpty(group_count)) {
                            pdnumTv.setText(group_count + "人在拼单");
                        }

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
                                    itmeClick(v, position);
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
                    if (groupType != null && groupType.equals("1")){
                        goodLuckPranster.setData(GoodLuckDetailsAty.this,"21",group_buy_id,mImageUrls,goods_name,goodsInfo.getIntegral(),goodsInfo.getTicket_buy_discount(),goodsInfo.getShop_price(),goodsInfo.getMarket_price(),"",goodsInfo.getGoods_brief(),goodsInfo.getSell_num());
                    }else if (groupType != null && groupType.equals("2")){
                        goodLuckPranster.setData(GoodLuckDetailsAty.this,"22",group_buy_id,mImageUrls,goods_name,goodsInfo.getIntegral(),goodsInfo.getTicket_buy_discount(),goodsInfo.getShop_price(),goodsInfo.getMarket_price(),"",goodsInfo.getGoods_brief(),goodsInfo.getSell_num());
                    }

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
            groupBuyPst.groupBuyInfo(group_buy_id, page, null);
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
        if (null != goodsInfo.getIntegral()) {
            bundle.putString("integral", goodsInfo.getIntegral());
        }
        bundle.putString("group_buy_id", group_buy_id);
        bundle.putString("id", groupList.get(position).getId());
        startActivity(CreateGroupAty.class, bundle);
    }

    @Override
    public void getFreightPay(String payStr) {
        removeDialog();
        ChangeTextViewStyle.getInstance().forTextColor(this, freight_tv,
                payStr, 0, getResources().getColor(R.color.red_tv_back));
    }

    @Override
    public void showErrorTip(String msg) {
        super.showErrorTip(msg);
    }


    class cg_adp extends RecyclerView.Adapter<cg_adp.ViewHolder> {
        ArrayList<GoodsBean> list;

        public cg_adp(ArrayList<GoodsBean> list) {
            this.list = list;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_dpg, null));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
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


    /**
     * 拼单购发起拼单
     *
     * @param v
     */
    private void buy(View v) {
        if (is_C) {
            Intent intent = new Intent();
            intent.putExtra("mid", mellInfoBean.getMerchant_id());
            intent.putExtra("type", "3");
            intent.putExtra("goods_id", goods_id);
            intent.putExtra("group_buy_id", group_buy_id);
            intent.putExtra("num", String.valueOf(goods_number));
            intent.putExtra("product_id", product_id);
            intent.putExtra("group_type", groupType);
            if (!TextUtils.isEmpty(order_id)) {
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

    /**
     * 点击发起拼单按钮弹出的弹窗
     *
     * @param yesListener
     * @param noListener
     */
    private void showDialogs(View.OnClickListener yesListener, View.OnClickListener noListener) {
        dialog = new Dialog(GoodLuckDetailsAty.this, R.style.Ticket_Dialog);
        View view = View.inflate(this, R.layout.goodluck_collage_dialog_view, null);
        dialog.setContentView(view);
        view.findViewById(R.id.goodluck_yes).setOnClickListener(yesListener);
        view.findViewById(R.id.goodluck_no).setOnClickListener(noListener);

        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        dialog.setCancelable(false);
        dialog.show();

    }


    /**
     * 参与拼手气完成后的弹窗
     */
    private void showPingShouQiDialog(View.OnClickListener yesListener, View.OnClickListener noListener) {
        dialog = new Dialog(GoodLuckDetailsAty.this, R.style.Ticket_Dialog);
        View view = View.inflate(this, R.layout.goodluck_collage_dialog_view, null);
        dialog.setContentView(view);
        ((TextView) view.findViewById(R.id.title_tv)).setText("主人，您已参与此商品拼手气活动，不可重复参与哦！");
        TextView left_tv = view.findViewById(R.id.goodluck_yes);
        TextView right_tv = view.findViewById(R.id.goodluck_no);
        left_tv.setText("继续逛逛");
        right_tv.setText("看看进度");
        left_tv.setOnClickListener(yesListener);
        right_tv.setOnClickListener(noListener);

        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        dialog.setCancelable(false);
        dialog.show();
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
                if (!TextUtils.isEmpty(data.getStringExtra("p_integral"))) {
                    if ("1".equals(groupType)) {
                        String p_integral = goodsInfo.getP_integral();
                        if (Double.parseDouble(p_integral) == 0){
                            one_price_tv.setText("单买" + onePrice + "元");
                        }else {
                            one_price_tv.setText("送" + p_integral + "积分\n单买" + onePrice + "元");
                        }
                    } else {
                        String p_integral = data.getStringExtra("p_integral");
                        if (Double.parseDouble(p_integral) == 0){
                            one_price_tv.setText("独立购买");
                        }else {
                            one_price_tv.setText("送" + p_integral + "积分\n独立购买");
                        }
                    }
                }

                String pro_valStr = data.getStringExtra("pro_value");
                if (pro_valStr.lastIndexOf("+") == pro_valStr.length() - 1) {//多规格属性后面带+时候需要屏蔽+
                    pro_valStr = pro_valStr.substring(0, pro_valStr.lastIndexOf("+"));
                }
                goods_select_attr_tv.setText(pro_valStr + "x" + data.getIntExtra("num", 0));
                now_price_tv.setText(data.getStringExtra("shop_price"));
                //                old_price_tv.setText("¥" + data.getStringExtra("market_price"));
                //                old_price_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                if ("1".equals(groupType)) {
                    String red_return_integral = data.getStringExtra("red_return_integral");
                    if (Double.parseDouble(red_return_integral) == 0){
                        creat_group_tv.setText("手气" + goodsInfo.getShop_price() + "元");
                    }else {
                        creat_group_tv.setText("送" + red_return_integral + "积分\n手气" + goodsInfo.getShop_price() + "元");
                    }

                } else {
                    if (Double.parseDouble(data.getStringExtra("red_return_integral"))==0){
                        creat_group_tv.setText("发起拼单");
                    }else {
                        creat_group_tv.setText("送" + data.getStringExtra("red_return_integral") + "积分\n发起拼单");
                    }

                }
                ChangeTextViewStyle.getInstance().forTextColor(this, goods_profit_num_tv,
                        "积分" + data.getStringExtra("red_return_integral"), 2, getResources().getColor(R.color.red_tv_back));
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
                if (!TextUtils.isEmpty(order_id)) {
                    bundle.putString("order_id", order_id);
                }
                bundle.putString("num", data.getStringExtra("num"));
                bundle.putString("product_id", data.getStringExtra("product_id"));
                if (data.hasExtra("group_type")) {
                    bundle.putString("group_type", data.getStringExtra("group_type"));
                }
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

    @Override
    public void freightGetEd(Map<String, String> map) {
        ChangeTextViewStyle.getInstance().forTextColor(this, freight_tv,
                map.get("pay"), 0, getResources().getColor(R.color.red_tv_back));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // 只需要调用这一句，其它的交给AndPermission吧，最后一个参数是PermissionListener。
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, goodLuckPranster.requestPhoneListener(merchant_phone, GoodLuckDetailsAty.this));
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (null != toastView) {
            toastView.cancle();
        }
    }
}
