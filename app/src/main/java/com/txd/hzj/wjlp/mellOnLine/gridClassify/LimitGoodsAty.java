package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ants.theantsgo.WeApplication;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.tips.CustomDialog;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.tools.ObserTool;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.ListUtils;
import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.ants.theantsgo.view.taobaoprogressbar.CustomProgressBar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
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
import com.txd.hzj.wjlp.bean.commodity.DjTicketBean;
import com.txd.hzj.wjlp.bean.commodity.FirstListBean;
import com.txd.hzj.wjlp.bean.commodity.FirstValBean;
import com.txd.hzj.wjlp.bean.commodity.GoodsAttrBean;
import com.txd.hzj.wjlp.bean.commodity.GoodsBannerBean;
import com.txd.hzj.wjlp.bean.commodity.GoodsBean;
import com.txd.hzj.wjlp.bean.commodity.GoodsCommonAttrBean;
import com.txd.hzj.wjlp.bean.commodity.GoodsPriceDescBean;
import com.txd.hzj.wjlp.bean.commodity.GoodsServerBean;
import com.txd.hzj.wjlp.bean.commodity.IntegralBuyInfoBean;
import com.txd.hzj.wjlp.bean.commodity.IntegralBuyInfoDataBean;
import com.txd.hzj.wjlp.bean.commodity.LimitGoodsInfo;
import com.txd.hzj.wjlp.bean.commodity.MInfoBean;
import com.txd.hzj.wjlp.bean.commodity.PicturesBean;
import com.txd.hzj.wjlp.bean.commodity.ProductBean;
import com.txd.hzj.wjlp.bean.commodity.PromotionBean;
import com.txd.hzj.wjlp.bean.commodity.TicketListBean;
import com.txd.hzj.wjlp.http.collect.UserCollectPst;
import com.txd.hzj.wjlp.http.integral.IntegralBuyPst;
import com.txd.hzj.wjlp.http.limit.LimitBuyPst;
import com.txd.hzj.wjlp.http.prebuy.PerBuyPst;
import com.txd.hzj.wjlp.mainFgt.adapter.AllGvLvAdapter;
import com.txd.hzj.wjlp.mellOnLine.adapter.GoodsCommentAttrAdapter;
import com.txd.hzj.wjlp.mellOnLine.adapter.PostAdapter;
import com.txd.hzj.wjlp.mellOnLine.adapter.PromotionAdapter;
import com.txd.hzj.wjlp.mellOnLine.adapter.TheTrickAdapter;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.adapter.CommentPicAdapter;
import com.txd.hzj.wjlp.new_wjyp.Collocations_aty;
import com.txd.hzj.wjlp.shoppingCart.BuildOrderAty;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;
import com.txd.hzj.wjlp.tool.CommonPopupWindow;
import com.txd.hzj.wjlp.tool.WJConfig;
import com.txd.hzj.wjlp.tool.proUrbArea.ProUrbAreaUtil;
import com.txd.hzj.wjlp.view.ObservableScrollView;
import com.txd.hzj.wjlp.view.ToastView;
import com.txd.hzj.wjlp.view.VpSwipeRefreshLayout;
import com.yanzhenjie.permission.AndPermission;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;
import cn.iwgang.countdownview.CountdownView;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/7 0007
 * 时间：下午 5:07
 * 描述：限量详情(2-3)
 */
public class LimitGoodsAty extends BaseAty implements ObservableScrollView.ScrollViewListener, ObservableScrollView.onBottomListener, CommodityDetailsInter.CommodityView, ProUrbAreaUtil.CallBack {

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
     * 商品轮播
     */
    @ViewInject(R.id.online_carvouse_view)
    private CarouselView online_carvouse_view;
    /**
     * 轮播图图片
     */
    private List<GoodsBannerBean> image;

    /**
     * 倒计时
     */
    @ViewInject(R.id.goods_count_down_view)
    private CountdownView goods_count_down_view;
    /**
     * 定金
     */
    @ViewInject(R.id.tv_dingjin)
    private TextView tv_dingjin;
    @ViewInject(R.id.tv_manfa)
    private TextView tv_manfa;//满多少发货
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
     * 价格说明
     */
    @ViewInject(R.id.layout_jgsm)
    private LinearLayout layout_jgsm;
    @ViewInject(R.id.tv_wy_price)
    private TextView tv_wy_price;//无忧价
    @ViewInject(R.id.tv_yx_price)
    private TextView tv_yx_price;//优享价

    @ViewInject(R.id.tv_bzqd)
    private TextView tv_bzqd;//包装清单
    @ViewInject(R.id.tv_shfw)
    private TextView tv_shfw;//售后服务
    @ViewInject(R.id.tv_jgsm)
    private TextView tv_jgsm;//价格说明

    @ViewInject(R.id.goods_custom_pb)
    private CustomProgressBar goods_custom_pb;

    /**
     * 已抢，剩余
     */
    @ViewInject(R.id.goods_residue_tv)
    private TextView goods_residue_tv;
    /**
     * 进口税
     */
    @ViewInject(R.id.goods_tariff_tv)
    private TextView goods_tariff_tv;

    /**
     * 满折布局
     */
    //    @ViewInject(R.id.goods_bottom_lin_layout)
    //    private LinearLayout goods_bottom_lin_layout;

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
    /**
     * 驿站适配器
     */
    private PostAdapter postAdapter;
    /**
     * 驿站列表
     */
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

    @ViewInject(R.id.toastView)
    private ToastView toastView;

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
    private int clickType = 0;

    /**
     * 购物优惠券
     */
    @ViewInject(R.id.goods_trick_rv)
    private RecyclerView goods_trick_rv;
    @ViewInject(R.id.get_a_coupon_lin_layout)
    private LinearLayout get_a_coupon_lin_layout;
    private TheTrickAdapter theTrickAdapter;

    /**
     * 店铺活动列表
     */
    @ViewInject(R.id.promotion_lv)
    private ListView promotion_lv;

    private PromotionAdapter promotionAdapter;
    /**
     * 限量购id
     */
    private String limit_buy_id = "";

    private LimitBuyPst limitBuyPst;
    /**
     * 轮播图宽高
     */
    private int bannerSize = Settings.displayWidth;

    /**
     * 限量购状态
     */
    @ViewInject(R.id.limit_status_tv)
    private TextView limit_status_tv;
    /**
     * 限量购进度
     */
    @ViewInject(R.id.limit_pro_tv)
    private TextView limit_pro_tv;

    /**
     * 促销标识
     */
    @ViewInject(R.id.onle_pro_type_iv)
    private ImageView onle_pro_type_iv;
    /**
     * 内容
     */
    @ViewInject(R.id.onle_pro_title_tv)
    private TextView onle_pro_title_tv;

    /**
     * 促销布局
     */
    @ViewInject(R.id.promotion_layout)
    private LinearLayout promotion_layout;
    /**
     * 促销布局上面的线
     */
    @ViewInject(R.id.promotion_layoutline)
    private View promotion_layoutline;

    /**
     * 商品条数
     */
    @ViewInject(R.id.all_comment_num_tv)
    private TextView all_comment_num_tv;
    /**
     * 评价商品的买家头像
     */
    @ViewInject(R.id.comm_user_head_iv)
    private ShapedImageView comm_user_head_iv;
    /**
     * 买家头像大小60dp
     */
    private int head_size = 0;
    /**
     * 买家昵称
     */
    @ViewInject(R.id.comm_user_name_tv)
    private TextView comm_user_name_tv;
    /**
     * 评论内容
     */
    @ViewInject(R.id.comm_content_tv)
    private TextView comm_content_tv;
    /**
     * 评论图片
     */
    @ViewInject(R.id.estimate_pic)
    private GridViewForScrollView estimate_pic;

    /**
     * 评价商品布局
     */
    @ViewInject(R.id.goods_for_my_evaluste_layout)
    private LinearLayout goods_for_my_evaluste_layout;

    // TODO==========店铺==========
    @ViewInject(R.id.the_logo_by_mell_iv)
    private ImageView the_logo_by_mell_iv;
    /**
     * 店铺logo大小 80dp
     */
    private int logo_size = 0;
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
    // TODO==========店铺==========
    /**
     * 产品规格
     */
    @ViewInject(R.id.goods_common_attr_lv)
    private ListViewForScrollView goods_common_attr_lv;
    /**
     * 文字描述
     */
    @ViewInject(R.id.goods_brief_tv)
    private WebView goods_brief_tv;
    /**
     * 图文详情
     */
    @ViewInject(R.id.goods_desc_wv)
    private WebView goods_desc_wv;

    /**
     * 名称
     */
    @ViewInject(R.id.goods_details_name_tv)
    private TextView goods_details_name_tv;

    @ViewInject(R.id.comment_layout)
    private LinearLayout comment_layout;

    /**
     * 购物车数量
     */
    @ViewInject(R.id.user_cart_num_tv)
    private TextView user_cart_num_tv;

    // TODO==========收藏==========
   /* @ViewInject(R.id.goods_title_collect_iv)
    private ImageView goods_title_collect_iv;*/

    @ViewInject(R.id.goods_title_collect_tv)
    private TextView goods_title_collect_tv;
    /**
     * 1.收藏，0.未收藏
     */
    private String is_collect = "0";
    // 收藏
    private UserCollectPst collectPst;
    /**
     * 无界预购
     */
    private PerBuyPst perBuyPst;
    /**
     * 积分商店
     */
    private IntegralBuyPst integralBuyPst;
    private String goods_id = "";
    private int type = 0; //0 限量 2无界预购 10积分商店
    @ViewInject(R.id.tv_rmb)
    private TextView tv_rmb;
    @ViewInject(R.id.tv_kucun)
    private TextView tv_kucun;
    @ViewInject(R.id.count_down_layout)
    private LinearLayout count_down_layout;

    @ViewInject(R.id.goods_pro_layout)
    private FrameLayout goods_pro_layout;
    private String mell_id = "";
    private String merchant_phone = "";
    private String share_content = "";
    private String share_img = "";
    private String share_url = "";
    private String merchant_logo = "";
    private String merchant_name = "";
    private String easemob_account = "";

    @ViewInject(R.id.tv_expirationdate)
    private TextView tv_expirationdate;//保质期提示
    @ViewInject(R.id.tv_tab_1)
    private TextView tv_tab_1;
    @ViewInject(R.id.tv_tab_2)
    private TextView tv_tab_2;
    @ViewInject(R.id.tv_tab_3)
    private TextView tv_tab_3;
    @ViewInject(R.id.layout_aftersale)
    private LinearLayout layout_aftersale;//包装售后


    @ViewInject(R.id.ticket_gv)//推荐商品列表
    private GridViewForScrollView ticket_gv;
    private AllGvLvAdapter allGvLvAdapter1;
    private boolean is_f = true;//判断刷新
    private int page = 1;
    private List<AllGoodsBean> ticket = new ArrayList<>();
    private List<AllGoodsBean> more = new ArrayList<>();

    /**
     * 搭配购
     *
     * @param
     */
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


    @ViewInject(R.id.tv_brief)
    private TextView tv_brief;//商品简介

    @ViewInject(R.id.layout_layout_settings)
    private LinearLayout layout_layout_settings;
    @ViewInject(R.id.layout_comment)
    private LinearLayout layout_comment;
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

    /**
     * 无界预购
     */
    @ViewInject(R.id.layout_wjsd)
    private LinearLayout layout_wjsd;
    @ViewInject(R.id.tv_jfzf)
    private TextView tv_jfzf;//可使用多少积分支付
    @ViewInject(R.id.tv_fhsj)
    private TextView tv_fhsj;//最晚发货时间
    @ViewInject(R.id.tv_hdsm)
    private TextView tv_hdsm;//活动说明文本

    @ViewInject(R.id.tv_jrgwc)
    private TextView tv_jrgwc;//加入购物车
    @ViewInject(R.id.tv_ljgm)
    private TextView tv_ljgm;//立即购买
    private int limit_store;

    private String vouchers_desc = "";//代金券弹窗下面的提示文字

    private List<DjTicketBean> dj_ticket;
    private List<PromotionBean> promotionBeen;
    private LimitGoodsInfo goodsInfo;

    private List<GoodsAttrBean> goodsAttrs;

    private List<ProductBean> goods_produc;
    private MInfoBean mInfo;

    private List<FirstListBean> goods_attr_first;
    private List<FirstValBean> first_val;
    private String is_attr = "";

    @ViewInject(R.id.remarks)
    private TextView remarks;

    private int goods_number = 0;
    private String product_id = "";
    private boolean is_C = false;

    @ViewInject(R.id.goods_select_attr_tv)
    private TextView goods_select_attr_tv;
    @ViewInject(R.id.title_goods_layout)
    private View title_goods_layout;

    @ViewInject(R.id.title_details_layout)
    private View title_details_layout;

    @ViewInject(R.id.title_evaluate_layout)
    private View title_evaluate_layout;
    @ViewInject(R.id.tv_date)
    private TextView tv_date;
    //刷新
    @ViewInject(R.id.limit_refresh)
    private VpSwipeRefreshLayout limit_refresh;
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    private CommodityDetailsInter.CommodityPranster commodityPranster;
    private String goods_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.goods_details_title);
        setTextViewAndViewColor(0);
        // 隐藏商品布局
        goods_for_my_evaluste_layout.setVisibility(View.GONE);
        // 设置轮播图高度
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(bannerSize, bannerSize);
        online_carvouse_view.setLayoutParams(layoutParams);
        wujie_post_lv.setAdapter(postAdapter);
        // 判断是否显示回到顶部按钮
        rv_service.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        goods_trick_rv.setLayoutManager(new LinearLayoutManager(LimitGoodsAty.this, LinearLayoutManager.HORIZONTAL,
                false));
        goods_trick_rv.setHasFixedSize(true);
        ProUrbAreaUtil.gainInstance().checkData((WeApplication) getApplication());
        commodityPranster = new CommodityDetailsPranster(this);
        limit_refresh.setHeaderViewBackgroundColor(Color.WHITE);
        limit_refresh.setHeaderView(createHeaderView());// add headerView
        limit_refresh.setTargetScrollWithLayout(true);
        limit_refresh.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                textView.setText("正在刷新");
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                getData();
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

        commodityPranster.goodsMsg(toastView);
    }

    private boolean init = false;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && !init) {
            commodityPranster.getHeight(online_carvouse_view, top_lin_layout, second_lin_layout, limit_goods_details_sc, be_back_top_iv);
            commodityPranster.setTabViews(title_goods_layout, title_details_layout, title_evaluate_layout);
            init = true;
        }
    }

    private void getHeight() {
        commodityPranster.getHeight(online_carvouse_view, top_lin_layout, second_lin_layout, limit_goods_details_sc, be_back_top_iv);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_limit_goods;
    }

    @Override
    protected void initialized() {
        limit_buy_id = getIntent().getStringExtra("limit_buy_id");
        type = getIntent().getIntExtra("type", WJConfig.XLG);
        limitBuyPst = new LimitBuyPst(this);
        collectPst = new UserCollectPst(this);
        perBuyPst = new PerBuyPst(this);
        integralBuyPst = new IntegralBuyPst(this);
        image = new ArrayList<>();
        posts = new ArrayList<>();
        postAdapter = new PostAdapter(this, posts);
        head_size = ToolKit.dip2px(this, 60);
        logo_size = ToolKit.dip2px(this, 80);
        // 商家等级
        imageViews = new ArrayList<>();

        imageViews.add(level_1_iv);
        imageViews.add(level_2_iv);
        imageViews.add(level_3_iv);
        imageViews.add(level_4_iv);
        imageViews.add(level_5_iv);
    }

    private void getData() {
        switch (type) {
            case WJConfig.XLG:// 限量购
                limitBuyPst.limitBuyInfo(limit_buy_id, page);
                tv_jrgwc.setVisibility(View.GONE);
                break;
            case WJConfig.WJYG:// 无界预购
                perBuyPst.preBuyInfo(limit_buy_id, page);
                tv_ljgm.setText("交付定金");
                break;
            case WJConfig.WJSD:// 积分商店
                integralBuyPst.integralBuyInfo(limit_buy_id, page);
                tv_jrgwc.setVisibility(View.GONE);
                break;
        }

        ticket_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("ticket_buy_id", ticket.get(position).getGoods_id());
                bundle.putInt("from", 1);
                startActivity(TicketGoodsDetialsAty.class, bundle);
            }
        });
        tv_ljgm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (WJConfig.XLG == type) {//  (ArrayList) goodsAttrs,  (ArrayList) goods_produc
                    if (is_C) {//限量购物
                        Intent intent = new Intent();
                        intent.putExtra("mid", mell_id);
                        intent.putExtra("type", WJConfig.TYPE_XLG);
                        intent.putExtra("goods_id", goods_id);
                        intent.putExtra("group_buy_id", limit_buy_id);
                        intent.putExtra("num", String.valueOf(goods_number));
                        intent.putExtra("product_id", product_id);
                        intent.setClass(LimitGoodsAty.this, BuildOrderAty.class);
                        startActivity(intent);
                    } else {
                        toAttrs(v, 0, WJConfig.TYPE_XLG, goods_id + "-" + mell_id, goodsInfo.getGoods_img(), goodsInfo.getLimit_price(), limit_buy_id, goods_attr_first, first_val, is_attr);
                    }
                } else if (WJConfig.WJYG == type) {//      (ArrayList) goodsAttrs,    (ArrayList) goods_produc
                    if (is_C) {    //无界预购
                        Intent intent = new Intent();
                        intent.putExtra("mid", mell_id);
                        intent.putExtra("type", WJConfig.TYPE_WJYG);
                        intent.putExtra("goods_id", goods_id);
                        intent.putExtra("group_buy_id", limit_buy_id);
                        intent.putExtra("num", String.valueOf(goods_number));
                        intent.putExtra("product_id", product_id);
                        intent.setClass(LimitGoodsAty.this, BuildOrderAty.class);
                        startActivity(intent);
                    } else {
                        toAttrs(v, 0, WJConfig.TYPE_WJYG, goods_id + "-" + mell_id, goodsInfo.getGoods_img(), goodsInfo.getShop_price(), limit_buy_id, goods_attr_first, first_val, is_attr);
                    }
                } else {///   (ArrayList) goodsAttrs,                            (ArrayList) goods_produc,//积分商店
                    if (is_C) {
                        Intent intent = new Intent();
                        intent.putExtra("mid", mell_id);
                        intent.putExtra("type", WJConfig.TYPE_WJSD);
                        intent.putExtra("goods_id", goods_id);
                        intent.putExtra("group_buy_id", limit_buy_id);
                        intent.putExtra("num", String.valueOf(goods_number));
                        intent.putExtra("product_id", product_id);
                        intent.setClass(LimitGoodsAty.this, BuildOrderAty.class);
                        startActivity(intent);
                    } else {
                        toAttrs(v, 0, WJConfig.TYPE_WJSD, goods_id + "-" + mell_id, goodsInfo.getGoods_img(), goodsInfo.getUse_integral(), limit_buy_id, goods_attr_first, first_val, is_attr);
                    }
                }
            }
        });
        commodityPranster.goodsMsg(toastView);
    }

    @Override
    protected void requestData() {
        switch (type) {
            case WJConfig.XLG:// 限量购
                limitBuyPst.limitBuyInfo(limit_buy_id, page);
                tv_jrgwc.setVisibility(View.GONE);
                break;
            case WJConfig.WJYG:// 无界预购
                perBuyPst.preBuyInfo(limit_buy_id, page);
                tv_ljgm.setText("交付定金");
                break;
            case WJConfig.WJSD:// 积分商店
                integralBuyPst.integralBuyInfo(limit_buy_id, page);
                tv_jrgwc.setVisibility(View.GONE);
                break;
        }

        ticket_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("ticket_buy_id", ticket.get(position).getGoods_id());
                bundle.putInt("from", 1);
                startActivity(TicketGoodsDetialsAty.class, bundle);
            }
        });
        tv_ljgm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (WJConfig.XLG == type) {//  (ArrayList) goodsAttrs,  (ArrayList) goods_produc
                    if (is_C) {//限量购物
                        Intent intent = new Intent();
                        intent.putExtra("mid", mell_id);
                        intent.putExtra("type", WJConfig.TYPE_XLG);
                        intent.putExtra("goods_id", goods_id);
                        intent.putExtra("group_buy_id", limit_buy_id);
                        intent.putExtra("num", String.valueOf(goods_number));
                        intent.putExtra("product_id", product_id);
                        intent.setClass(LimitGoodsAty.this, BuildOrderAty.class);
                        startActivity(intent);
                    } else {
                        toAttrs(v, 0, WJConfig.TYPE_XLG, goods_id + "-" + mell_id, goodsInfo.getGoods_img(), goodsInfo.getLimit_price(), limit_buy_id, goods_attr_first, first_val, is_attr);
                    }
                } else if (WJConfig.WJYG == type) {//      (ArrayList) goodsAttrs,    (ArrayList) goods_produc
                    if (is_C) {    //无界预购
                        Intent intent = new Intent();
                        intent.putExtra("mid", mell_id);
                        intent.putExtra("type", WJConfig.TYPE_WJYG);
                        intent.putExtra("goods_id", goods_id);
                        intent.putExtra("group_buy_id", limit_buy_id);
                        intent.putExtra("num", String.valueOf(goods_number));
                        intent.putExtra("product_id", product_id);
                        intent.setClass(LimitGoodsAty.this, BuildOrderAty.class);
                        startActivity(intent);
                    } else {
                        toAttrs(v, 0, WJConfig.TYPE_WJYG, goods_id + "-" + mell_id, goodsInfo.getGoods_img(), goodsInfo.getShop_price(), limit_buy_id, goods_attr_first, first_val, is_attr);
                    }
                } else {///   (ArrayList) goodsAttrs,                            (ArrayList) goods_produc,//积分商店
                    if (is_C) {
                        Intent intent = new Intent();
                        intent.putExtra("mid", mell_id);
                        intent.putExtra("type", WJConfig.TYPE_WJSD);
                        intent.putExtra("goods_id", goods_id);
                        intent.putExtra("group_buy_id", limit_buy_id);
                        intent.putExtra("num", String.valueOf(goods_number));
                        intent.putExtra("product_id", product_id);
                        intent.setClass(LimitGoodsAty.this, BuildOrderAty.class);
                        startActivity(intent);
                    } else {
                        toAttrs(v, 0, WJConfig.TYPE_WJSD, goods_id + "-" + mell_id, goodsInfo.getGoods_img(), goodsInfo.getUse_integral(), limit_buy_id, goods_attr_first, first_val, is_attr);
                    }
                }
            }
        });
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        progressBar.setVisibility(View.GONE);
        limit_refresh.setRefreshing(false);
        removeDialog();
        if (requestUrl.contains("limitBuyInfo") ||
                requestUrl.contains("preBuyInfo") ||
                requestUrl.contains("integralBuyInfo")) {
            ObserTool.gainInstance().jsonToBean(jsonStr, IntegralBuyInfoBean.class, new ObserTool.BeanListener() {
                @Override
                public void returnObj(Object t) {
                    IntegralBuyInfoBean integralBuyInfoBean = (IntegralBuyInfoBean) t;
                    IntegralBuyInfoDataBean data = integralBuyInfoBean.getData();
                    remarks.setText(data.getRemarks());
                    String cart_num = data.getCart_num();
                    goods_attr_first = data.getFirst_list();
                    first_val = data.getFirst_val();

                    if (!cart_num.equals("0")) {
                        user_cart_num_tv.setText(cart_num);
                        user_cart_num_tv.setVisibility(View.VISIBLE);
                    } else {
                        user_cart_num_tv.setVisibility(View.GONE);
                    }
                    goodsAttrs = data.getGoods_attr();
                    goods_produc = data.getProduct();

                    vouchers_desc = data.getVouchers_desc();
                    // 是否收藏
                    is_collect = data.getIs_collect();

                    if ("0".equals(is_collect)) {
                        commodityPranster.isCollect(is_collect, "收藏", goods_title_collect_tv, LimitGoodsAty.this);
                    } else {
                        commodityPranster.isCollect(is_collect, "已收藏", goods_title_collect_tv, LimitGoodsAty.this);
                    }
                    share_url = Config.OFFICIAL_WEB + "Wap/IntegralBuy/integralBuyInfo/integral_buy_id/" + limit_buy_id + ".html";
                    share_img = data.getShare_img();
                    share_content = data.getShare_content();
                    List<GoodsBannerBean> banners = data.getGoods_banner();
                    // 轮播图
                    if (null != banners && banners.size() > 0) {
                        image = banners;
                        forBanner();
                    }
                    // 商品基本信息
                    goodsInfo = data.getGoodsInfo();

                    /**
                     *以下表示如果buy_status==0，表示当前商品已经下架
                     * */
                    String buyStatusStr = goodsInfo.getBuy_status();
                    if (!TextUtils.isEmpty(buyStatusStr) && buyStatusStr.equals("0")) {
                        CustomDialog.Builder dialog = new CustomDialog.Builder(LimitGoodsAty.this);
                        dialog.setCancelable(false);
                        dialog.setMessage("当前商品已下架");
                        dialog.setTitle("下架提示");
                        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LimitGoodsAty.this.finish();
                            }
                        });
                        dialog.create().show();
                    }


                    is_attr = data.getIs_attr();
                    is_attr = is_attr + "-" + goodsInfo.getGoods_num();
                    // 商品id
                    goods_id = goodsInfo.getGoods_id();
                    goods_name = goodsInfo.getGoods_name();

                    String tx = DemoApplication.getInstance().getLocInfo().get("province")
                            + "," + DemoApplication.getInstance().getLocInfo().get("city") + "," + DemoApplication.getInstance().getLocInfo().get("district");
                    tv_chose_ads.setText(tx);
                    commodityPranster.freight(goods_id, tx, String.valueOf(goods_number), product_id);

                    if (goodsInfo.getIs_new_goods().equals("0") && goodsInfo.getIs_end().equals("1")) {
                        tv_expirationdate.setText(goodsInfo.getIs_new_goods_desc() + "\n" + goodsInfo.getIs_end_desc());
                    } else if (goodsInfo.getIs_new_goods().equals("0")) {
                        tv_expirationdate.setText(goodsInfo.getIs_new_goods_desc());
                    } else if (goodsInfo.getIs_end().equals("1")) {
                        tv_expirationdate.setText(goodsInfo.getIs_end_desc());
                    } else {
                        tv_expirationdate.setVisibility(View.GONE);
                    }

                    tv_wy_price.setText("¥" + goodsInfo.getWy_price());
                    tv_yx_price.setText("¥" + goodsInfo.getYx_price());
                    commodityPranster.setBitmap(LimitGoodsAty.this, goodsInfo.getCountry_logo(), im_country_logo);
                    commodityPranster.setTextContent(goodsInfo.getCountry_desc(), tv_country_desc);
                    commodityPranster.setTextContent(goodsInfo.getCountry_tax() + "积分", tv_country_tax);
                    if (Double.parseDouble(goodsInfo.getCountry_tax()) <= 0) {
                        layou_jinkoushui.setVisibility(View.GONE);
                    }

                    tv_brief.setText(goodsInfo.getGoods_brief());
                    if (10 != type) {
                        long now = System.currentTimeMillis() / 1000;//获取当前系统时间
                        long end;
                        if (0 == type) {
                            String stage_status = goodsInfo.getStage_status();
                            if (stage_status.equals("即将开始")) {
                                end = Long.parseLong(goodsInfo.getStart_time());
                            } else {// 正在进行中和已结束
                                end = Long.parseLong(goodsInfo.getEnd_time());
                            }
                        } else {
                            end = Long.parseLong(goodsInfo.getEnd_time());
                        }

                        long difference = end - now;
                        limit_status_tv.setText(goodsInfo.getStage_status());
                        if (goodsInfo.getStage_status().equals("已结束")) {
                            goods_count_down_view.setVisibility(View.GONE);
                            tv_ljgm.setEnabled(false);
                            tv_ljgm.setBackgroundColor(Color.GRAY);
                            tv_ljgm.setText("已结束");
                        }
                        // 倒计时
                        goods_count_down_view.setTag("limitGoods");
                        difference = difference * 1000;
                        goods_count_down_view.start(difference);
                        // 限量购价格
                        if (WJConfig.XLG == type) {
                            //                    ChangeTextViewStyle.getInstance().forGoodsPrice(this, now_price_tv, "￥" + goodsInfo.get
                            //                            ("limit_price"));
                            now_price_tv.setText(goodsInfo.getLimit_price());
                        } else if (WJConfig.WJYG == type) {
                            now_price_tv.setText(goodsInfo.getPre_price());
                            //                    ChangeTextViewStyle.getInstance().forGoodsPrice(this, now_price_tv, "￥" + goodsInfo.get("deposit"));
                            tv_dingjin.setText("定金 " + goodsInfo.getDeposit());
                            tv_dingjin.setVisibility(View.VISIBLE);
                            tv_manfa.setVisibility(View.VISIBLE);
                            tv_manfa.setText("满" + goodsInfo.getSuccess_max_num() + "件即可发货");
                            layout_wjsd.setVisibility(View.VISIBLE);
                            if (goodsInfo.getIntegral().equals("1")) {
                                tv_jfzf.setText("可以使用" + goodsInfo.getIntegral_price() + "积分支付");
                            } else {
                                tv_jfzf.setVisibility(View.GONE);
                            }
                            String date = new java.text.SimpleDateFormat("yyyy/MM/dd").format(new java.util.Date(goodsInfo.getEnd_delivery_date()));
                            tv_fhsj.setText("最晚发货时间 " + date);
                            String desc = "活动说明：" + goodsInfo.getDesc();
                            SpannableString msp = new SpannableString(desc);
                            msp.setSpan(new ForegroundColorSpan(Color.parseColor("#666666")), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置色
                            tv_hdsm.setText(msp);
                            tv_jrgwc.setText(goodsInfo.getPre_price() + "\n预约价格");
                        } else {
                        }

                        // 市场价(原价)
                        old_price_tv.setText("￥" + goodsInfo.getMarket_price());
                        old_price_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        // 积分
                        ChangeTextViewStyle.getInstance().forTextColor(LimitGoodsAty.this, goods_profit_num_tv,
                                "积分" + goodsInfo.getIntegral(), 2, Color.parseColor("#FF0000"));

                        try {
                            if (WJConfig.XLG == type) {
                                limit_store = Integer.parseInt(goodsInfo.getLimit_store());
                            } else if (WJConfig.WJYG == type) {
                                limit_store = Integer.parseInt(goodsInfo.getSuccess_max_num());
                            } else {
                                //limit_store = Integer.parseInt(goodsInfo.getSuccess_max_num());
                            }
                        } catch (NumberFormatException e) {
                            limit_store = 100;
                        }

                        int sell_num;

                        try {
                            sell_num = Integer.parseInt(goodsInfo.getSell_num());
                        } catch (NumberFormatException e) {
                            sell_num = 0;
                        }

                        // 进度
                        goods_custom_pb.setMaxProgress(limit_store);
                        goods_custom_pb.setCurProgress(sell_num);
                        double d = sell_num * 100.0f / limit_store;
                        if (sell_num >= limit_store) {
                            d = 100f;
                        }
                        // 计算百分比
                        String str = new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
                        limit_pro_tv.setText(str + "%");
                        String only;
                        if (WJConfig.XLG == type) {
                            only = "已抢" + sell_num + "件/剩余" + (limit_store - sell_num) + "件";
                            ChangeTextViewStyle.getInstance().forTextColor(LimitGoodsAty.this, goods_residue_tv, only,
                                    6 + String.valueOf(sell_num).length(), only.length() - 1,
                                    ContextCompat.getColor(LimitGoodsAty.this, R.color.theme_color));
                        } else if (WJConfig.WJYG == type) {
                            only = "已预购" + sell_num + "件/剩余" + (limit_store - sell_num) + "件";
                            ChangeTextViewStyle.getInstance().forTextColor(LimitGoodsAty.this, goods_residue_tv, only,
                                    7 + String.valueOf(sell_num).length(), only.length() - 1,
                                    ContextCompat.getColor(LimitGoodsAty.this, R.color.theme_color));
                        } else {
                            only = "已兑换" + sell_num + "件/剩余" + (limit_store - sell_num) + "件";
                            ChangeTextViewStyle.getInstance().forTextColor(LimitGoodsAty.this, goods_residue_tv, only,
                                    7 + String.valueOf(sell_num).length(), only.length() - 1,
                                    ContextCompat.getColor(LimitGoodsAty.this, R.color.theme_color));
                        }
                    } else {
                        //                        layout_service.setVisibility(View.GONE);
                        //                        layout_layout_settings.setVisibility(View.GONE);
                        layout_djq.setVisibility(View.GONE);
                        tv_kucun.setText("库存" + goodsInfo.getGoods_num());
                        layout_jgsm.setVisibility(View.GONE);
                        tv_rmb.setVisibility(View.GONE);
                        count_down_layout.setVisibility(View.GONE);
                        goods_pro_layout.setVisibility(View.GONE);
                        goods_profit_num_tv.setVisibility(View.GONE);
                        old_price_tv.setText("￥" + goodsInfo.getMarket_price());
                        old_price_tv.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        now_price_tv.setText("此物品兑换，需要" + goodsInfo.getUse_integral() + "积分");
                        now_price_tv.setTextSize(16f);
                        //goods_residue_tv.setText("已兑换" + goodsInfo.getSell_num() + "件");
                        goods_residue_tv.setVisibility(View.GONE);
                        tv_ljgm.setText("立即兑换");
                    }

                    // 商品名称
                    goods_details_name_tv.setText(goodsInfo.getGoods_name());


                    //            String tariff = "进口税 " + goodsInfo.getCountry_tax() + "元/件";
                    //            ChangeTextViewStyle.getInstance().forTextColor(this, goods_tariff_tv, tariff, 4, tariff.length() - 3,
                    //                    ContextCompat.getColor(this, R.color.theme_color));
                    //            // 运费
                    //            ChangeTextViewStyle.getInstance().forTextColor(this, freight_tv,
                    //                    "运费10元", 2, Color.parseColor("#FF0000"));

                    goods_brief_tv.loadDataWithBaseURL(null, goodsInfo.getGoods_brief(), "text/html", "utf-8", null);

                    goods_desc_wv.loadDataWithBaseURL(null, goodsInfo.getGoods_desc(), "text/html", "utf-8", null);

                    // 商家信息
                    mInfo = data.getmInfo();
                    mell_id = mInfo.getMerchant_id();
                    merchant_phone = mInfo.getMerchant_phone();
                    easemob_account = mInfo.getEasemob_account();
                    merchant_name = mInfo.getMerchant_name();
                    merchant_logo = mInfo.getLogo();
                    Glide.with(LimitGoodsAty.this).load(merchant_logo)
                            .override(logo_size, logo_size)
                            .placeholder(R.drawable.ic_default)
                            .centerCrop()
                            .error(R.drawable.ic_default)
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(the_logo_by_mell_iv);
                    title_by_mell_tv.setText(mInfo.getMerchant_name());
                    int level;
                    try {
                        level = Integer.parseInt(mInfo.getLevel());
                    } catch (NumberFormatException e) {
                        level = 0;
                    }
                    for (int i = 0; i < level; i++) {
                        imageViews.get(i).setVisibility(View.VISIBLE);
                    }

                    ChangeTextViewStyle.getInstance().forGoodsLineFeed(LimitGoodsAty.this, all_prodect_tv, mInfo.getAll_goods() + "\n全部宝贝");
                    ChangeTextViewStyle.getInstance().forGoodsLineFeed(LimitGoodsAty.this, all_collect_tv, mInfo.getView_num() + "\n人关注");
                    ChangeTextViewStyle.getInstance().forTextColor(LimitGoodsAty.this, goods_describe_tv,
                            "宝贝描述" + mInfo.getGoods_score(), 4, Color.parseColor("#FF0000"));
                    ChangeTextViewStyle.getInstance().forTextColor(LimitGoodsAty.this, mell_serve_tv,
                            "卖家服务" + mInfo.getMerchant_score(), 4, Color.parseColor("#FF0000"));
                    ChangeTextViewStyle.getInstance().forTextColor(LimitGoodsAty.this, log_serve_tv,
                            "物流服务" + mInfo.getShipping_score(), 4, Color.parseColor("#FF0000"));

                    tv_bzqd.setText(goodsInfo.getPackage_list()); //包装清单
                    tv_shfw.setText(goodsInfo.getAfter_sale_service()); //售后服务
                    //                    tv_jgsm.setText(Html.fromHtml(data.getPrice_desc())); //价格说明
                    tv_jgsm.setText(data.getPrice_desc()); //价格说明
                    // 促销活动
                    if (null != data.getPromotion() && data.getPromotion().size() > 0) {
                        promotionBeen = data.getPromotion();
                        //                PromotionBean prom = promotionBeen.get(0);
                        //                String type = prom.getType();
                        //                int imageId = getResources().getIdentifier("icon_get_coupon_hzj_" + type, "drawable", getPackageName());
                        //                onle_pro_type_iv.setImageResource(imageId);
                        //                onle_pro_title_tv.setText(prom.getTitle());
                        //                promotionBeen.remove(prom);
                        if (!ListUtils.isEmpty(promotionBeen)) {// 判断移除掉一个活动之后是否为空
                            //                    goods_bottom_lin_layout.setVisibility(View.VISIBLE);
                            promotionAdapter = new PromotionAdapter(LimitGoodsAty.this, promotionBeen);
                            promotion_lv.setAdapter(promotionAdapter);
                            //                    show_or_hide_iv.setEnabled(true);
                        } else {
                            //                    goods_bottom_lin_layout.setVisibility(View.GONE);
                            // 此处可以设置。。不呢点击
                            //                    show_or_hide_iv.setEnabled(false);
                        }
                        promotion_layout.setVisibility(View.VISIBLE);
                        promotion_layoutline.setVisibility(View.VISIBLE);
                    } else {
                        promotion_layout.setVisibility(View.GONE);
                        promotion_layoutline.setVisibility(View.GONE);
                    }
                    // 优惠券列表
                    List<TicketListBean> ticketListBeens = data.getTicketList();
                    if (null != ticketListBeens && ticketListBeens.size() > 0) {
                        theTrickAdapter = new TheTrickAdapter(LimitGoodsAty.this, ticketListBeens);
                        goods_trick_rv.setAdapter(theTrickAdapter);
                    } else {
                        get_a_coupon_lin_layout.setVisibility(View.GONE);
                    }
                    // 评论
                    CommentBean comment = data.getComment();
                    if (null != comment) {
                        try {
                            all_comment_num_tv.setText("商品评价(" + comment.getTotal() + ")");
                            BodyBean bodyBean = comment.getBody();
                            if (bodyBean != null) {
                                Glide.with(LimitGoodsAty.this).load(bodyBean.getUser_head_pic())
                                        .override(head_size, head_size)
                                        .placeholder(R.drawable.ic_default)
                                        .error(R.drawable.ic_default)
                                        .centerCrop()
                                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                        .into(comm_user_head_iv);
                                comm_user_name_tv.setText(bodyBean.getNickname());
                                comm_content_tv.setText(bodyBean.getContent());
                                if (!android.text.TextUtils.isEmpty(bodyBean.getCreate_time())) {
                                    tv_date.setText(bodyBean.getCreate_time());
                                }
                                List<PicturesBean> pictures = bodyBean.getPictures();
                                if (!ListUtils.isEmpty(pictures)) {
                                    CommentPicAdapter picadapter = new CommentPicAdapter(LimitGoodsAty.this, pictures);
                                    estimate_pic.setAdapter(picadapter);
                                }
                            } else {
                                layout_comment.setVisibility(View.GONE);
                            }
                        } catch (JsonSyntaxException e) {
                            layout_comment.setVisibility(View.GONE);
                        }

                    } else {
                        layout_comment.setVisibility(View.GONE);
                    }
                    List<GoodsPriceDescBean> goodsPriceDescBeans = data.getGoods_price_desc();
                    if (null != goodsPriceDescBeans && goodsPriceDescBeans.size() > 0) {
                        goods_price_desc = (ArrayList<GoodsPriceDescBean>) goodsPriceDescBeans;
                    }
                    ArrayList<GoodsServerBean> goodsServerBeans = (ArrayList<GoodsServerBean>) data.getGoods_server();
                    if (null != goodsServerBeans && goodsServerBeans.size() > 0) {
                        ser_list = goodsServerBeans;
                        rv_service.setAdapter(new service_adp(ser_list, 3));
                    } else {
                        layout_service.setVisibility(View.GONE);
                    }

                    List<DjTicketBean> dj_ticketList = goodsInfo.getDj_ticket();
                    if (null != dj_ticketList && dj_ticketList.size() > 0) {
                        dj_ticket = dj_ticketList;
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
                                    layout_djq2.setVisibility(View.VISIBLE);
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
                    // TODO==========产品属性==========
                    List<GoodsCommonAttrBean> gca = data.getGoods_common_attr();
                    if (null != gca && gca.size() > 0) {
                        GoodsCommentAttrAdapter gcaAdapter = new GoodsCommentAttrAdapter(LimitGoodsAty.this, gca);
                        goods_common_attr_lv.setAdapter(gcaAdapter);
                    }
                    List<AllGoodsBean> gusssGoodLists = data.getGuess_goods_list();
                    if (null != gusssGoodLists && gusssGoodLists.size() > 0) {
                        if (page == 1) {
                            ticket = gusssGoodLists;
                            allGvLvAdapter1 = new AllGvLvAdapter(LimitGoodsAty.this, ticket, 1);
                            ticket_gv.setAdapter(allGvLvAdapter1);
                        } else {
                            more = gusssGoodLists;
                            ticket.addAll(more);
                            allGvLvAdapter1.notifyDataSetChanged();
                        }
                    } else {
                        is_f = false;
                    }
                    //搭配购
                    CheapGroupBean cheap_group = data.getCheap_group();
                    if (cheap_group != null) {
                        tv_ticket_buy_discount.setText("最多可用" + cheap_group.getTicket_buy_discount() + "%代金券");
                        tv_group_price.setText("搭配价：¥" + cheap_group.getGroup_price());
                        tv_group_integral.setText(cheap_group.getIntegral());
                        double price = Double.parseDouble(cheap_group.getGroup_price()) - Double.parseDouble(cheap_group.getGroup_price());
                        DecimalFormat df = new DecimalFormat("#.00");
                        tv_goods_price.setText("立省¥" + df.format(price));
                        List<GoodsBean> maps = cheap_group.getGoods();
                        rv_cheap_group.setLayoutManager(new LinearLayoutManager(LimitGoodsAty.this, LinearLayoutManager.HORIZONTAL, false));
                        rv_cheap_group.setAdapter(new cg_adp(maps));
                    } else {
                        layout_cheap_group.setVisibility(View.GONE);

                    }
                }
            });
            return;
        }
        if (requestUrl.contains("addCollect")) {// 添加收藏
            showRightTip("收藏成功");
            is_collect = "1";
            commodityPranster.isCollect(is_collect, "已收藏", goods_title_collect_tv, LimitGoodsAty.this);
            return;
        }
        if (requestUrl.contains("delOneCollect")) {
            showRightTip("取消成功");
            is_collect = "0";
            commodityPranster.isCollect(is_collect, "收藏", goods_title_collect_tv, LimitGoodsAty.this);
            return;
        }
    }

    @Override
    @OnClick({R.id.title_goods_layout, R.id.title_details_layout, R.id.title_evaluate_layout,
            R.id.goods_title_collect_layout, R.id.goods_title_share_tv, R.id.show_or_hide_iv,
            R.id.show_or_hide_lv_iv, R.id.show_or_hide_explain_iv, R.id.be_back_top_iv, R.id.tv_showClassify,
            R.id.go_to_cart_layout, R.id.to_main_layout, R.id.details_into_mell_tv, R.id.tv_dpg,
            R.id.tv_chose_ads, R.id.all_evaluate_tv,
            R.id.relation_mell_tv, R.id.tv_tab_1, R.id.tv_tab_2, R.id.tv_tab_3, R.id.tv_lingquan,
            R.id.btn_jgsm, R.id.im_service_more, R.id.layout_djq, R.id.tv_quxiao, R.id.layout_layout_settings})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.all_evaluate_tv: {
                Bundle bundle = new Bundle();
                //                bundle.putInt("from", 2);
                bundle.putInt("from", 0);//0商品
                bundle.putString("mid", mell_id);
                bundle.putString("goods_id", goods_id);
                startActivity(GoodsEvaluateAty.class, bundle);
                break;
            }
            case R.id.tv_chose_ads:
                //                if (isLoaded) {
                //                    ShowPickerView();
                //                }
                ProUrbAreaUtil.gainInstance().showPickerView(tv_chose_ads, goods_id, String.valueOf(goods_number), product_id, LimitGoodsAty.this, LimitGoodsAty.this);
                break;
            case R.id.tv_showClassify:
                //                Intent intent = new Intent();
                //                intent.putExtra("appBarTitle", goodsInfo.getTwo_cate_name());
                //                intent.putExtra("two_cate_id", goodsInfo.getCate_id());
                //                intent.setClass(this, SubclassificationAty.class);
                //                startActivity(intent);
                toClassify(v, goodsInfo.getTop_cate_id());
                break;
            case R.id.title_goods_layout://商品
                clickType = 1;
                limit_goods_details_sc.smoothScrollTo(0, 0);
                break;
            case R.id.title_details_layout://详情
                clickType = 2;
                setTextViewAndViewColor(1);
                limit_goods_details_sc.smoothScrollTo(0, secondHeight);
                break;
            case R.id.title_evaluate_layout://评价
                clickType = 3;
                setTextViewAndViewColor(2);
                limit_goods_details_sc.smoothScrollTo(0, topHeighe);
                break;
            case R.id.goods_title_collect_layout://收藏,取消收藏
                if (!Config.isLogin()) {
                    toLogin();
                    break;
                }
                if (is_collect.equals("0")) {
                    collectPst.addCollect("1", goods_id);
                    break;
                }
                collectPst.delOneCollect("1", goods_id);
                break;
            case R.id.goods_title_share_tv://分享
                toShare(goods_name, share_img, share_url, share_content, goods_id, "1");
                break;
            case R.id.show_or_hide_iv://展开,隐藏(满折布局)
                //                getHeight();// 重新计算高度
                //                if (goods_bottom_lin_layout.getVisibility() == View.GONE) {
                //                    goods_bottom_lin_layout.setVisibility(View.VISIBLE);
                //                    show_or_hide_iv.setImageResource(R.drawable.icon_show_other_layout);
                //                } else {
                //                    goods_bottom_lin_layout.setVisibility(View.GONE);
                //                    show_or_hide_iv.setImageResource(R.drawable.icon_hide_other_layout);
                //                }
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
            case R.id.be_back_top_iv://回到顶部
                limit_goods_details_sc.smoothScrollTo(0, 0);
                setTextViewAndViewColor(0);
                break;
            case R.id.go_to_cart_layout:// 购物车
                backMain(2);
                break;
            case R.id.to_main_layout:// 首页
                backMain(0);
                break;
            case R.id.details_into_mell_tv:// 进店逛逛
                Bundle bundle = new Bundle();
                bundle.putString("mell_id", mell_id);
                L.e("=====商家id======", mell_id);
                startActivity(MellInfoAty.class, bundle);
                break;
            case R.id.relation_mell_tv:// 客服
                commodityPranster.chat_merchant(mell_id, LimitGoodsAty.this, merchant_phone);
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
            case R.id.btn_jgsm:
                if (goods_price_desc != null) {
                    showPricePop(v, "价格说明", goods_price_desc);
                }
                break;
            case R.id.im_service_more:
                if (ser_list != null) {
                    showServerPop(v, "服务说明", ser_list);
                }
                break;
            case R.id.layout_djq:
                commodityPranster.showDjqPop(v, dj_ticket, LimitGoodsAty.this, vouchers_desc);
                break;
            case R.id.tv_quxiao://促销弹框
                commodityPranster.showCXPop(v, LimitGoodsAty.this, promotionBeen);
                //                showCXPop(v);
                break;
            case R.id.tv_lingquan:
                commodityPranster.showLQPop(v, "领券", LimitGoodsAty.this, theTrickAdapter);
                //                showLQPop(v, "领券");
                break;
            case R.id.layout_layout_settings:
                if (WJConfig.XLG == type) {
                    toAttrs(v, 4, WJConfig.TYPE_XLG, goods_id + "-" + mell_id, goodsInfo.getGoods_img(), goodsInfo.getLimit_price(), limit_buy_id, goods_attr_first, first_val, is_attr);
                } else if (WJConfig.WJYG == type) {
                    toAttrs(v, 4, WJConfig.TYPE_WJYG, goods_id + "-" + mell_id, goodsInfo.getGoods_img(), goodsInfo.getShop_price(), limit_buy_id, goods_attr_first, first_val, is_attr);
                } else if (WJConfig.WJSD == type) {
                    toAttrs(v, 4, WJConfig.TYPE_WJSD, goods_id + "-" + mell_id, goodsInfo.getGoods_img(), goodsInfo.getUse_integral(), limit_buy_id, goods_attr_first, first_val, is_attr);
                }
                //                toAttrs(v, 4, "5", goods_id + "-" + mell_id, goodsInfo.getGoods_img(), goodsInfo.getLimit_price(), limit_buy_id, goods_attr_first, first_val, is_attr);
                break;
            case R.id.tv_dpg:
                Bundle bundle1 = new Bundle();
                bundle1.putString("goods_id", goods_id);
                startActivity(Collocations_aty.class, bundle1);
                break;
        }
    }

    CommonPopupWindow commonPopupWindow;

    public void showServerPop(View view, final String title, final ArrayList<GoodsServerBean> list) {//
        if (commonPopupWindow != null && commonPopupWindow.isShowing()) {
            return;
        }
        commonPopupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_layout)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, Settings.displayHeight / 2)
                .setBackGroundLevel(0.7f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId, int position) {
                        TextView cancel = (TextView) view.findViewById(R.id.cancel);
                        RecyclerView recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
                        recyclerview.setLayoutManager(new LinearLayoutManager(LimitGoodsAty.this, 1, false));
                        recyclerview.setAdapter(new service_adp(list, 1));
                        TextView tv_title = (TextView) view.findViewById(R.id.popp_title);
                        tv_title.setText(title);
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                commonPopupWindow.dismiss();
                            }
                        });
                    }
                }, 0)
                .setAnimationStyle(R.style.animbottom)
                .create();
        commonPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    public void showPricePop(View view, final String title, final ArrayList<GoodsPriceDescBean> list) {//
        if (commonPopupWindow != null && commonPopupWindow.isShowing()) {
            return;
        }
        commonPopupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_layout)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, Settings.displayHeight / 2)
                .setBackGroundLevel(0.7f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId, int position) {
                        TextView cancel = (TextView) view.findViewById(R.id.cancel);
                        RecyclerView recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
                        recyclerview.setLayoutManager(new LinearLayoutManager(LimitGoodsAty.this, 1, false));
                        recyclerview.setAdapter(new Price_adp(list));
                        TextView tv_title = (TextView) view.findViewById(R.id.popp_title);
                        tv_title.setText(title);
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                commonPopupWindow.dismiss();
                            }
                        });
                    }
                }, 0)
                .setAnimationStyle(R.style.animbottom)
                .create();
        commonPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }


    //    /**
    //     * 领券
    //     */
    //    public void showLQPop(View view, final String title) {//
    //        if (commonPopupWindow != null && commonPopupWindow.isShowing()) return;
    //        commonPopupWindow = new CommonPopupWindow.Builder(this)
    //                .setView(R.layout.popup_layout)
    //                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, Settings.displayHeight / 2)
    //                .setBackGroundLevel(0.7f)
    //                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
    //                    @Override
    //                    public void getChildView(View view, int layoutResId, int position) {
    //                        TextView cancel = (TextView) view.findViewById(R.id.cancel);
    //                        RecyclerView recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
    //                        recyclerview.setLayoutManager(new GridLayoutManager(LimitGoodsAty.this, 2));
    //                        recyclerview.setAdapter(theTrickAdapter);
    //                        TextView tv_title = (TextView) view.findViewById(R.id.popp_title);
    //                        tv_title.setText(title);
    //                        cancel.setOnClickListener(new View.OnClickListener() {
    //                            @Override
    //                            public void onClick(View view) {
    //                                commonPopupWindow.dismiss();
    //                            }
    //                        });
    //
    //                    }
    //                }, 0)
    //                .setAnimationStyle(R.style.animbottom)
    //                .create();
    //        commonPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    //    }

    //    /**
    //     * 促销
    //     *
    //     * @param view
    //     */
    //    public void showCXPop(View view) {
    //        if (commonPopupWindow != null && commonPopupWindow.isShowing()) return;
    //        commonPopupWindow = new CommonPopupWindow.Builder(this)
    //                .setView(R.layout.layou_popp_cuxiao)
    //                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, Settings.displayHeight / 2)
    //                .setBackGroundLevel(0.7f)
    //                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
    //                    @Override
    //                    public void getChildView(View view, int layoutResId, int position) {
    //                        ListView promotion_lv = view.findViewById(R.id.promotion_lv);
    //                        PromotionAdapter promotionAdapter = new PromotionAdapter(LimitGoodsAty.this, promotionBeen);
    //                        promotion_lv.setAdapter(promotionAdapter);
    //                        TextView cancel = (TextView) view.findViewById(R.id.cancel);
    //                        cancel.setOnClickListener(new View.OnClickListener() {
    //                            @Override
    //                            public void onClick(View view) {
    //                                commonPopupWindow.dismiss();
    //                            }
    //                        });
    //
    //                    }
    //                }, 0)
    //                .setAnimationStyle(R.style.animbottom)
    //                .create();
    //        commonPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    //    }


    private void setTextViewAndViewColor(int next) {
        title_goods_tv.setTextColor(Color.BLACK);
        title_details_tv.setTextColor(Color.BLACK);
        title_evaluate_tv.setTextColor(Color.BLACK);

        title_goods_view.setBackgroundColor(Color.WHITE);
        title_details_view.setBackgroundColor(Color.WHITE);
        title_evaluate_view.setBackgroundColor(Color.WHITE);

        if (1 == clickType) {
            title_goods_tv.setTextColor(ContextCompat.getColor(this, R.color.theme_color));
            title_goods_view.setBackgroundColor(ContextCompat.getColor(this, R.color.theme_color));
            return;
        }
        if (2 == clickType) {
            title_details_tv.setTextColor(ContextCompat.getColor(this, R.color.theme_color));
            title_details_view.setBackgroundColor(ContextCompat.getColor(this, R.color.theme_color));
            return;
        }
        if (3 == clickType) {
            title_evaluate_tv.setTextColor(ContextCompat.getColor(this, R.color.theme_color));
            title_evaluate_view.setBackgroundColor(ContextCompat.getColor(this, R.color.theme_color));
            return;
        }
        if (0 == next) {
            title_goods_tv.setTextColor(ContextCompat.getColor(this, R.color.theme_color));
            title_goods_view.setBackgroundColor(ContextCompat.getColor(this, R.color.theme_color));
        } else if (1 == next) {
            title_details_tv.setTextColor(ContextCompat.getColor(this, R.color.theme_color));
            title_details_view.setBackgroundColor(ContextCompat.getColor(this, R.color.theme_color));
        } else {
            title_evaluate_tv.setTextColor(ContextCompat.getColor(this, R.color.theme_color));
            title_evaluate_view.setBackgroundColor(ContextCompat.getColor(this, R.color.theme_color));
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

            Glide.with(LimitGoodsAty.this).load(image.get(position).getPath())
                    .centerCrop()
                    .override(bannerSize, bannerSize)
                    .error(R.drawable.ic_default)
                    .placeholder(R.drawable.ic_default)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imageView);

            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    };


    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= 0) {
            be_back_top_iv.setVisibility(View.GONE);
            setTextViewAndViewColor(0);
        } else if (y > bannerHeight) {
            be_back_top_iv.setVisibility(View.VISIBLE);
            if (y < topHeighe) {
                setTextViewAndViewColor(0);
            } else if (y >= topHeighe && y < secondHeight) {
                setTextViewAndViewColor(2);
            } else {
                setTextViewAndViewColor(1);
            }
        }
        if (oldy > y) {
            clickType = 0;
        }
    }

    @Override
    public void onBottom() {
        if (is_f) {
            page++;
            switch (type) {
                case WJConfig.XLG:// 限量购
                    limitBuyPst.limitBuyInfo(limit_buy_id, page);
                    break;
                case WJConfig.WJYG:// 无界预购
                    perBuyPst.preBuyInfo(limit_buy_id, page);
                    break;
                case WJConfig.WJSD:// 积分商店
                    L.e("==========", String.valueOf(type));
                    integralBuyPst.integralBuyInfo(limit_buy_id, page);
                    break;
            }
        }
    }

    @Override
    public void getFreightPay(String payStr) {
        removeDialog();
        ChangeTextViewStyle.getInstance().forTextColor(this, freight_tv,
                payStr, 2, Color.parseColor("#FF0000"));
    }

    @Override
    public void showErrorTip(String msg) {
        super.showErrorTip(msg);
    }

    @Override
    public void freightGetEd(Map<String, String> map) {
        removeDialog();
        ChangeTextViewStyle.getInstance().forTextColor(this, freight_tv,
                map.get("pay"), 2, Color.parseColor("#FF0000"));
    }

    class service_adp extends RecyclerView.Adapter<service_adp.ViewHolder> {
        ArrayList<GoodsServerBean> list;
        private int tpye;

        public service_adp(ArrayList<GoodsServerBean> list, int type) {
            this.list = list;
            this.tpye = type;
        }

        @Override
        public service_adp.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new service_adp.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lh_service, null));
        }

        @Override
        public void onBindViewHolder(service_adp.ViewHolder holder, int position) {
            String name = "";
            String desc = "";
            if (this.tpye == 1) {
                name = list.get(position).getServer_name() + "：";
                desc = list.get(position).getDesc();
                SpannableString msp = new SpannableString(name + desc);
                msp.setSpan(new ForegroundColorSpan(Color.parseColor("#F23030")), 0, name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置色
                holder.tv_text.setText(msp);
            } else {
                holder.tv_text.setText(list.get(position).getServer_name());
                holder.tv_text.setTextColor(Color.parseColor("#F23030"));
            }
            Glide.with(LimitGoodsAty.this).load(list.get(position).getIcon()).into(holder.im_logo);

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView im_logo;
            TextView tv_text;

            public ViewHolder(View itemView) {
                super(itemView);
                im_logo = (ImageView) itemView.findViewById(R.id.im_logo);
                tv_text = (TextView) itemView.findViewById(R.id.tv_text);
            }
        }
    }


    class Price_adp extends RecyclerView.Adapter<Price_adp.ViewHolder> {
        ArrayList<GoodsPriceDescBean> list;

        public Price_adp(ArrayList<GoodsPriceDescBean> list) {
            this.list = list;
        }

        @Override
        public Price_adp.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Price_adp.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lh_service, null));
        }

        @Override
        public void onBindViewHolder(Price_adp.ViewHolder holder, int position) {
            String name = list.get(position).getPrice_name() + "：";
            String desc = list.get(position).getDesc();
            SpannableString msp = new SpannableString(name + desc);
            msp.setSpan(new ForegroundColorSpan(Color.parseColor("#F23030")), 0, name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置色
            holder.tv_text.setText(msp);
            Glide.with(LimitGoodsAty.this).load(list.get(position).getIcon()).into(holder.im_logo);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView im_logo;
            TextView tv_text;

            public ViewHolder(View itemView) {
                super(itemView);
                im_logo = (ImageView) itemView.findViewById(R.id.im_logo);
                tv_text = (TextView) itemView.findViewById(R.id.tv_text);
            }
        }
    }


    class cg_adp extends RecyclerView.Adapter<cg_adp.ViewHolder> {
        List<GoodsBean> list;

        public cg_adp(List<GoodsBean> list) {
            this.list = list;

        }

        @Override
        public cg_adp.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_dpg, null));
        }

        @Override
        public void onBindViewHolder(cg_adp.ViewHolder holder, int position) {
            Glide.with(LimitGoodsAty.this).load(list.get(position).getGoods_img()).into(holder.imageview);
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

    @ViewInject(R.id.tv_chose_ads)
    private TextView tv_chose_ads;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0x0002 && data != null && requestCode == 1000) {
            is_C = true;
            goods_number = data.getIntExtra("num", 0);
            product_id = data.getStringExtra("product_id");
            if (WJConfig.TYPE_WJSD.equals(data.getStringExtra("type"))) {//积分商店
                limit_buy_id = data.getStringExtra("integral_buy_id");
                String use_integral = data.getStringExtra("use_integral");
                old_price_tv.setText("￥" + data.getStringExtra("shop_price"));
                now_price_tv.setText("此物品兑换，需要" + use_integral + "积分");
                tv_kucun.setText("库存" + data.getStringExtra("goods_num"));
            } else {
                ChangeTextViewStyle.getInstance().forTextColor(this, goods_profit_num_tv,
                        "积分" + data.getStringExtra("red_return_integral"), 2, Color.parseColor("#FF0000"));
                now_price_tv.setText(data.getStringExtra("shop_price"));
                tv_wy_price.setText("¥" + data.getStringExtra("wy_price"));
                tv_yx_price.setText("¥" + data.getStringExtra("wy_price"));
            }
            String pro_valStr = data.getStringExtra("pro_value");
            if (pro_valStr.lastIndexOf("+") == pro_valStr.length() - 1) {//多规格属性后面带+时候需要屏蔽+
                pro_valStr = pro_valStr.substring(0, pro_valStr.lastIndexOf("+"));
            }
            goods_select_attr_tv.setText("已选商品配置(" + pro_valStr + ")x" + goods_number);
            //                old_price_tv.setText("￥" + data.getStringExtra("market_price"));
            //                old_price_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

            dj_ticket = (List<DjTicketBean>) data.getSerializableExtra("data");
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
                            layout_djq2.setVisibility(View.VISIBLE);
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

            //            L.e("product_id=" + data.getStringExtra("product_id") + "\n" +
            //                    "pro_value=" + data.getStringExtra("pro_value") + "\n" +
            //                    "num=" + data.getIntExtra("num", 0) + "\n" +
            //                    "shop_price=" + data.getStringExtra("shop_price") + "\n" +
            //                    "market_price=" + data.getStringExtra("market_price") + "\n" +
            //                    "red_return_integral=" + data.getStringExtra("red_return_integral") + "\n" +
            //                    "discount=" + data.getStringExtra("discount") + "\n" +
            //                    "yellow_discount=" + data.getStringExtra("yellow_discount") + "\n" +
            //                    "blue_discount=" + data.getStringExtra("blue_discount") + "\n" +
            //                    "wy_price=" + data.getStringExtra("wy_price") + "\n" +
            //                    "wy_price=" + data.getStringExtra("wy_price") + "\n");

        } else if (requestCode == 1000 && resultCode == 0x0001 && null != data) {
            L.e("返回商品详情");
            Bundle bundle = new Bundle();
            bundle.putString("mid", data.getStringExtra("mid"));
            bundle.putString("type", data.getStringExtra("type"));
            bundle.putString("goods_id", data.getStringExtra("goods_id"));
            if (WJConfig.WJSD == type) {//积分商店
                bundle.putString("group_buy_id", data.getStringExtra("integral_buy_id"));
            } else {
                bundle.putString("group_buy_id", data.getStringExtra("group_buy_id"));
            }
            String order_id = data.getStringExtra("order_id");
            if (!android.text.TextUtils.isEmpty(order_id)) {
                bundle.putString("order_id", order_id);
            }
            bundle.putString("num", data.getStringExtra("num"));
            bundle.putString("product_id", data.getStringExtra("product_id"));
            startActivity(BuildOrderAty.class, bundle);
        }
    }

    private View createHeaderView() {
        View headerView = LayoutInflater.from(limit_refresh.getContext()).inflate(R.layout.layout_head, null);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // 只需要调用这一句，其它的交给AndPermission吧，最后一个参数是PermissionListener。
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, commodityPranster.requestPhoneListener(merchant_phone, LimitGoodsAty.this));
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (null != toastView) {
            toastView.cancle();
        }
    }
}
