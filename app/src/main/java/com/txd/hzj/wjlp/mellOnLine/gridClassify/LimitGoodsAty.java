package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.ListUtils;
import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.ants.theantsgo.view.taobaoprogressbar.CustomProgressBar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.AllGoodsBean;
import com.txd.hzj.wjlp.bean.GoodsAttrs;
import com.txd.hzj.wjlp.bean.GoodsCommonAttr;
import com.txd.hzj.wjlp.bean.Mell;
import com.txd.hzj.wjlp.bean.groupbuy.CommentBean;
import com.txd.hzj.wjlp.bean.groupbuy.PromotionBean;
import com.txd.hzj.wjlp.bean.groupbuy.TicketListBean;
import com.txd.hzj.wjlp.http.collect.UserCollectPst;
import com.txd.hzj.wjlp.http.integral.IntegralBuyPst;
import com.txd.hzj.wjlp.http.limit.LimitBuyPst;
import com.txd.hzj.wjlp.http.prebuy.PerBuyPst;
import com.txd.hzj.wjlp.huanxin.ui.ChatActivity;
import com.txd.hzj.wjlp.mainFgt.adapter.AllGvLvAdapter;
import com.txd.hzj.wjlp.mellOnLine.adapter.GoodsCommentAttrAdapter;
import com.txd.hzj.wjlp.mellOnLine.adapter.PostAdapter;
import com.txd.hzj.wjlp.mellOnLine.adapter.PromotionAdapter;
import com.txd.hzj.wjlp.mellOnLine.adapter.TheTrickAdapter;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.adapter.CommentPicAdapter;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;
import com.txd.hzj.wjlp.tool.CommonPopupWindow;
import com.txd.hzj.wjlp.view.ObservableScrollView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;
import cn.iwgang.countdownview.CountdownView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/7 0007
 * 时间：下午 5:07
 * 描述：限量详情(2-3)
 * ===============Txunda===============
 */
public class LimitGoodsAty extends BaseAty implements ObservableScrollView.ScrollViewListener, ObservableScrollView.onBottomListener {

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
    private List<Map<String, String>> image;

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
    @ViewInject(R.id.goods_title_collect_iv)
    private ImageView goods_title_collect_iv;

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
     * 无界商店
     */
    private IntegralBuyPst integralBuyPst;
    private String goods_id = "";
    private int type = 0;
    @ViewInject(R.id.tv_rmb)
    private TextView tv_rmb;
    @ViewInject(R.id.tv_kucun)
    private TextView tv_kucun;
    @ViewInject(R.id.count_down_layout)
    private LinearLayout count_down_layout;

    @ViewInject(R.id.goods_pro_layout)
    private FrameLayout goods_pro_layout;
    private String mell_id = "";
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


    ArrayList<Map<String, String>> ser_list;//服务的列表
    ArrayList<Map<String, String>> goods_price_desc;//价格的列表

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
    private ArrayList<Map<String, String>> dj_ticket;
    private List<PromotionBean> promotionBeen;
    private Map<String, String> goodsInfo;

    private List<GoodsAttrs> goodsAttrs;

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
        getHeight();
        rv_service.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        goods_trick_rv.setLayoutManager(new LinearLayoutManager(LimitGoodsAty.this, LinearLayoutManager.HORIZONTAL,
                false));
        goods_trick_rv.setHasFixedSize(true);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_limit_goods;
    }

    @Override
    protected void initialized() {
        limit_buy_id = getIntent().getStringExtra("limit_buy_id");
        type = getIntent().getIntExtra("type", 0);
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

    @Override
    protected void requestData() {
        switch (type) {
            case 0:// 限量购
                limitBuyPst.limitBuyInfo(limit_buy_id, page);
                break;
            case 2:// 无界预购
                perBuyPst.preBuyInfo(limit_buy_id, page);
                break;
            case 10:// 无界商店
                L.e("==========", String.valueOf(type));
                integralBuyPst.integralBuyInfo(limit_buy_id, page);
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
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("limitBuyInfo") ||
                requestUrl.contains("preBuyInfo") ||
                requestUrl.contains("integralBuyInfo")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            String cart_num = data.get("cart_num");
            if (!cart_num.equals("0")) {
                user_cart_num_tv.setText(cart_num);
                user_cart_num_tv.setVisibility(View.VISIBLE);
            } else {
                user_cart_num_tv.setVisibility(View.GONE);
            }
            goodsAttrs = GsonUtil.getObjectList(data.get("goods_attr"), GoodsAttrs.class);
            vouchers_desc = data.get("vouchers_desc");
            // 是否收藏
            is_collect = data.get("is_collect");

            if ("0".equals(is_collect)) {
                goods_title_collect_iv.setImageResource(R.drawable.icon_collect);
                goods_title_collect_tv.setText("收藏");
            } else {
                goods_title_collect_iv.setImageResource(R.drawable.icon_collected);
                goods_title_collect_tv.setText("已收藏");
            }
            share_url = data.get("share_url");
            share_img = data.get("share_img");
            share_content = data.get("share_content");
            // 轮播图
            if (ToolKit.isList(data, "goods_banner")) {
                image = JSONUtils.parseKeyAndValueToMapList(data.get("goods_banner"));
                forBanner();
            }
            // 商品基本信息
            goodsInfo = JSONUtils.parseKeyAndValueToMap(data.get("goodsInfo"));
            // 商品id
            goods_id = goodsInfo.get("goods_id");

            if (goodsInfo.get("is_new_goods").equals("0") && goodsInfo.get("is_end").equals("1")) {
                tv_expirationdate.setText(goodsInfo.get("is_new_goods_desc") + "\n" + goodsInfo.get("is_end_desc"));
            } else if (goodsInfo.get("is_new_goods").equals("0")) {
                tv_expirationdate.setText(goodsInfo.get("is_new_goods_desc"));
            } else if (goodsInfo.get("is_end").equals("1")) {
                tv_expirationdate.setText(goodsInfo.get("is_end_desc"));
            } else {
                tv_expirationdate.setVisibility(View.GONE);
            }

            tv_wy_price.setText("¥" + goodsInfo.get("wy_price"));
            tv_yx_price.setText("¥" + goodsInfo.get("yx_price"));
            Glide.with(this).load(goodsInfo.get("country_logo")).into(im_country_logo);
            tv_country_desc.setText(goodsInfo.get("country_desc"));
            tv_country_tax.setText(goodsInfo.get("country_tax") + "元");
            if (Double.parseDouble(goodsInfo.get("country_tax")) <= 0) {
                layou_jinkoushui.setVisibility(View.GONE);
            }

            tv_brief.setText(goodsInfo.get("goods_brief"));
            if (10 != type) {
                long now = System.currentTimeMillis() / 1000;//获取当前系统时间
                long end;
                if (0 == type) {
                    String stage_status = goodsInfo.get("stage_status");
                    if (stage_status.equals("即将开始")) {
                        end = Long.parseLong(goodsInfo.get("start_time"));
                    } else {// 正在进行中和已结束
                        end = Long.parseLong(goodsInfo.get("end_time"));
                    }
                } else {
                    end = Long.parseLong(goodsInfo.get("end_time"));
                }

                long difference = end - now;
                limit_status_tv.setText(goodsInfo.get("stage_status"));
                // 倒计时
                goods_count_down_view.setTag("limitGoods");
                difference = difference * 1000;
                goods_count_down_view.start(difference);
                // 限量购价格
                if (0 == type) {
//                    ChangeTextViewStyle.getInstance().forGoodsPrice(this, now_price_tv, "￥" + goodsInfo.get
//                            ("limit_price"));
                    now_price_tv.setText(goodsInfo.get("limit_price"));
                } else if (2 == type) {
                    now_price_tv.setText(goodsInfo.get("pre_price"));
//                    ChangeTextViewStyle.getInstance().forGoodsPrice(this, now_price_tv, "￥" + goodsInfo.get("deposit"));
                    tv_dingjin.setText("定金 " + goodsInfo.get("deposit"));
                    tv_dingjin.setVisibility(View.VISIBLE);
                    tv_manfa.setVisibility(View.VISIBLE);
                    tv_manfa.setText("满" + goodsInfo.get("success_max_num") + "件即可发货");
                    layout_wjsd.setVisibility(View.VISIBLE);
                    if (goodsInfo.get("is_integral").equals("1")) {
                        tv_jfzf.setText("可以使用" + goodsInfo.get("integral_price") + "积分支付");
                    } else {
                        tv_jfzf.setVisibility(View.GONE);
                    }
                    String date = new java.text.SimpleDateFormat("yyyy/MM/dd").format(new java.util.Date(goodsInfo.get("end_delivery_date")));
                    tv_fhsj.setText("最晚发货时间 " + date);
                    String desc = "活动说明：" + goodsInfo.get("desc");
                    SpannableString msp = new SpannableString(desc);
                    msp.setSpan(new ForegroundColorSpan(Color.parseColor("#666666")), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置色
                    tv_hdsm.setText(msp);
                    tv_jrgwc.setText(goodsInfo.get("pre_price") + "\n预约价格");
                } else {
                }

                // 市场价(原价)
                old_price_tv.setText("￥" + goodsInfo.get("market_price"));
                old_price_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                // 积分
                ChangeTextViewStyle.getInstance().forTextColor(this, goods_profit_num_tv,
                        "积分" + goodsInfo.get("integral"), 2, Color.parseColor("#FD8214"));

                try {
                    if (0 == type) {
                        limit_store = Integer.parseInt(goodsInfo.get("limit_store"));
                    } else if (2 == type) {
                        limit_store = Integer.parseInt(goodsInfo.get("success_max_num"));
                    } else {
                        //limit_store = Integer.parseInt(goodsInfo.get("success_max_num"));
                    }
                } catch (NumberFormatException e) {
                    limit_store = 100;
                }

                int sell_num;

                try {
                    sell_num = Integer.parseInt(goodsInfo.get("sell_num"));
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
                if (0 == type) {
                    only = "已抢" + sell_num + "件/剩余" + (limit_store - sell_num) + "件";
                    ChangeTextViewStyle.getInstance().forTextColor(this, goods_residue_tv, only,
                            6 + String.valueOf(sell_num).length(), only.length() - 1,
                            ContextCompat.getColor(this, R.color.theme_color));
                } else if (2 == type) {
                    only = "已预购" + sell_num + "件/剩余" + (limit_store - sell_num) + "件";
                    ChangeTextViewStyle.getInstance().forTextColor(this, goods_residue_tv, only,
                            7 + String.valueOf(sell_num).length(), only.length() - 1,
                            ContextCompat.getColor(this, R.color.theme_color));
                } else {
                    only = "已兑换" + sell_num + "件/剩余" + (limit_store - sell_num) + "件";
                    ChangeTextViewStyle.getInstance().forTextColor(this, goods_residue_tv, only,
                            7 + String.valueOf(sell_num).length(), only.length() - 1,
                            ContextCompat.getColor(this, R.color.theme_color));
                }
            } else {
                tv_kucun.setText("库存" + goodsInfo.get("goods_num"));
                layout_jgsm.setVisibility(View.GONE);
                tv_rmb.setVisibility(View.GONE);
                count_down_layout.setVisibility(View.GONE);
                goods_pro_layout.setVisibility(View.GONE);
                goods_profit_num_tv.setVisibility(View.GONE);
                old_price_tv.setText("￥" + goodsInfo.get("shop_price"));
                old_price_tv.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                now_price_tv.setText("此物品兑换，需要" + goodsInfo.get("use_integral") + "积分");
                now_price_tv.setTextSize(16f);
                //goods_residue_tv.setText("已兑换" + goodsInfo.get("sell_num") + "件");
                goods_residue_tv.setVisibility(View.GONE);
                tv_jrgwc.setVisibility(View.INVISIBLE);
                tv_ljgm.setText("立即兑换");
            }

            // 商品名称
            goods_details_name_tv.setText(goodsInfo.get("goods_name"));


//            String tariff = "进口税 " + goodsInfo.get("country_tax") + "元/件";
//            ChangeTextViewStyle.getInstance().forTextColor(this, goods_tariff_tv, tariff, 4, tariff.length() - 3,
//                    ContextCompat.getColor(this, R.color.theme_color));
//            // 运费
//            ChangeTextViewStyle.getInstance().forTextColor(this, freight_tv,
//                    "运费10元", 2, Color.parseColor("#FD8214"));

            goods_brief_tv.loadDataWithBaseURL(null, goodsInfo.get("goods_brief"), "text/html", "utf-8", null);
            goods_desc_wv.loadDataWithBaseURL(null, goodsInfo.get("goods_desc"), "text/html", "utf-8", null);

            // 商家信息
            Map<String, String> mInfo = JSONUtils.parseKeyAndValueToMap(data.get("mInfo"));
            mell_id = mInfo.get("merchant_id");

            easemob_account = mInfo.get("easemob_account");
            merchant_name = mInfo.get("merchant_name");
            merchant_logo = mInfo.get("logo");
            Glide.with(this).load(merchant_logo)
                    .override(logo_size, logo_size)
                    .placeholder(R.drawable.ic_default)
                    .centerCrop()
                    .error(R.drawable.ic_default)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(the_logo_by_mell_iv);
            title_by_mell_tv.setText(mInfo.get("merchant_name"));
            int level;
            try {
                level = Integer.parseInt(mInfo.get("level"));
            } catch (NumberFormatException e) {
                level = 0;
            }
            for (int i = 0; i < level; i++) {
                imageViews.get(i).setVisibility(View.VISIBLE);
            }

            ChangeTextViewStyle.getInstance().forGoodsLineFeed(this, all_prodect_tv, mInfo.get("all_goods") + "\n全部宝贝");
            ChangeTextViewStyle.getInstance().forGoodsLineFeed(this, all_collect_tv, mInfo.get("view_num") + "\n人关注");
            ChangeTextViewStyle.getInstance().forTextColor(this, goods_describe_tv,
                    "宝贝描述" + mInfo.get("goods_score"), 4, Color.parseColor("#FD8214"));
            ChangeTextViewStyle.getInstance().forTextColor(this, mell_serve_tv,
                    "卖家服务" + mInfo.get("merchant_score"), 4, Color.parseColor("#FD8214"));
            ChangeTextViewStyle.getInstance().forTextColor(this, log_serve_tv,
                    "物流服务" + mInfo.get("shipping_score"), 4, Color.parseColor("#FD8214"));

            tv_bzqd.setText(goodsInfo.get("package_list")); //包装清单
            tv_shfw.setText(goodsInfo.get("after_sale_service")); //售后服务
            tv_jgsm.setText(Html.fromHtml(data.get("price_desc"))); //价格说明
            // 促销活动
            if (ToolKit.isList(data, "promotion")) {
                promotionBeen = GsonUtil.getObjectList(data.get("promotion"), PromotionBean.class);
//                PromotionBean prom = promotionBeen.get(0);
//                String type = prom.getType();
//                int imageId = getResources().getIdentifier("icon_get_coupon_hzj_" + type, "drawable", getPackageName());
//                onle_pro_type_iv.setImageResource(imageId);
//                onle_pro_title_tv.setText(prom.getTitle());
//                promotionBeen.remove(prom);
                if (!ListUtils.isEmpty(promotionBeen)) {// 判断移除掉一个活动之后是否为空
//                    goods_bottom_lin_layout.setVisibility(View.VISIBLE);
                    promotionAdapter = new PromotionAdapter(this, promotionBeen);
                    promotion_lv.setAdapter(promotionAdapter);
//                    show_or_hide_iv.setEnabled(true);
                } else {
//                    goods_bottom_lin_layout.setVisibility(View.GONE);
                    // 此处可以设置。。不呢点击
//                    show_or_hide_iv.setEnabled(false);
                }
                promotion_layout.setVisibility(View.VISIBLE);
            } else {
                promotion_layout.setVisibility(View.GONE);
            }
            // 优惠券列表
            if (ToolKit.isList(data, "ticketList")) {
                List<TicketListBean> ticketListBeens = GsonUtil.getObjectList(data.get("ticketList"),
                        TicketListBean.class);
                theTrickAdapter = new TheTrickAdapter(this, ticketListBeens);
                goods_trick_rv.setAdapter(theTrickAdapter);
            } else {
                get_a_coupon_lin_layout.setVisibility(View.GONE);
            }
            // 评论
            if (ToolKit.isList(data, "comment")) {
                try {
                    CommentBean comment = GsonUtil.GsonToBean(data.get("comment"), CommentBean.class);
                    all_comment_num_tv.setText("商品评价(" + comment.getTotal() + ")");
                    Map<String, String> commentMap = JSONUtils.parseKeyAndValueToMap(data.get("comment"));
                    CommentBean.BodyBean bodyBean = comment.getBody();
                    if (bodyBean != null) {
                        Glide.with(this).load(bodyBean.getUser_head_pic())
                                .override(head_size, head_size)
                                .placeholder(R.drawable.ic_default)
                                .error(R.drawable.ic_default)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .into(comm_user_head_iv);
                        comm_user_name_tv.setText(bodyBean.getNickname());
                        comm_content_tv.setText(bodyBean.getContent());
                        List<CommentBean.BodyBean.PicturesBean> pictures = bodyBean.getPictures();
                        if (!ListUtils.isEmpty(pictures)) {
                            CommentPicAdapter picadapter = new CommentPicAdapter(this, pictures);
                            estimate_pic.setAdapter(picadapter);
                        }
                    }
                } catch (JsonSyntaxException e) {
                    all_comment_num_tv.setText("商品评价(0)");
                    comment_layout.setVisibility(View.GONE);
                }

            }

            if (ToolKit.isList(data, "goods_price_desc")) {
                goods_price_desc = JSONUtils.parseKeyAndValueToMapList(data.get("goods_price_desc"));
            }
            if (ToolKit.isList(data, "goods_server")) {
                ser_list = JSONUtils.parseKeyAndValueToMapList(data.get("goods_server"));
                rv_service.setAdapter(new service_adp(ser_list, 3));
            } else {
                layout_service.setVisibility(View.GONE);
            }


            if (ToolKit.isList(goodsInfo, "dj_ticket")) {
                dj_ticket = JSONUtils.parseKeyAndValueToMapList(goodsInfo.get("dj_ticket"));
                for (int i = 0; i < dj_ticket.size(); i++) {
                    if (i == 2) {
                        break;
                    }
                    switch (i) {
                        case 0: {
                            layout_djq0.setVisibility(View.VISIBLE);
                            tv_djq_desc0.setText(dj_ticket.get(i).get("discount_desc"));
                            break;
                        }
                        case 1: {
                            layout_djq1.setVisibility(View.VISIBLE);
                            tv_djq_desc1.setText(dj_ticket.get(i).get("discount_desc"));
                            break;
                        }
                        case 2: {
                            layout_djq2.setVisibility(View.VISIBLE);
                            tv_djq_desc2.setText(dj_ticket.get(i).get("discount_desc"));
                            break;
                        }
                    }

                    switch (dj_ticket.get(i).get("type")) {
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
            if (ToolKit.isList(data, "goods_common_attr")) {
                List<GoodsCommonAttr> gca = GsonUtil.getObjectList(data.get("goods_common_attr"),
                        GoodsCommonAttr.class);
                GoodsCommentAttrAdapter gcaAdapter = new GoodsCommentAttrAdapter(this, gca);
                goods_common_attr_lv.setAdapter(gcaAdapter);
            }

            if (ToolKit.isList(data, "guess_goods_list")) {
                if (page == 1) {
                    ticket = GsonUtil.getObjectList(data.get("guess_goods_list"), AllGoodsBean.class);
                    allGvLvAdapter1 = new AllGvLvAdapter(this, ticket, 1);
                    ticket_gv.setAdapter(allGvLvAdapter1);
                } else {
                    more = GsonUtil.getObjectList(data.get("guess_goods_list"), AllGoodsBean.class);
                    ticket.addAll(more);
                    allGvLvAdapter1.notifyDataSetChanged();
                }
            } else {
                is_f = false;
            }

            //搭配购
            Map<String, String> cheap_group = JSONUtils.parseKeyAndValueToMap(data.get("cheap_group"));
            if (cheap_group != null) {
                tv_ticket_buy_discount.setText("最多可用" + cheap_group.get("ticket_buy_discount") + "代金券");
                tv_group_price.setText("搭配价：¥" + cheap_group.get("group_price"));
                tv_group_integral.setText(cheap_group.get("integral"));
                double price = Double.parseDouble(cheap_group.get("goods_price")) - Double.parseDouble(cheap_group.get("group_price"));
                tv_goods_price.setText("立省¥" + price);
                ArrayList<Map<String, String>> maps = JSONUtils.parseKeyAndValueToMapList(cheap_group.get("goods"));
                rv_cheap_group.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                rv_cheap_group.setAdapter(new cg_adp(maps));
            } else {
                layout_cheap_group.setVisibility(View.GONE);

            }

            return;
        }
        if (requestUrl.contains("addCollect")) {// 添加收藏
            showRightTip("收藏成功");
            is_collect = "1";
            goods_title_collect_iv.setImageResource(R.drawable.icon_collected);
            goods_title_collect_tv.setText("已收藏");
            return;
        }
        if (requestUrl.contains("delOneCollect")) {
            showRightTip("取消成功");
            is_collect = "0";
            goods_title_collect_iv.setImageResource(R.drawable.icon_collect);
            goods_title_collect_tv.setText("收藏");
            return;
        }
    }

    @Override
    @OnClick({R.id.title_goods_layout, R.id.title_details_layout, R.id.title_evaluate_layout,
            R.id.goods_title_collect_layout, R.id.goods_title_share_tv, R.id.show_or_hide_iv,
            R.id.show_or_hide_lv_iv, R.id.show_or_hide_explain_iv, R.id.be_back_top_iv,
            R.id.go_to_cart_layout, R.id.to_main_layout, R.id.details_into_mell_tv,
            R.id.relation_mell_tv, R.id.tv_tab_1, R.id.tv_tab_2, R.id.tv_tab_3, R.id.tv_lingquan,
            R.id.btn_jgsm, R.id.im_service_more, R.id.layout_djq, R.id.tv_quxiao, R.id.im_toarrs})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
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
                toShare("无界优品", share_img, share_url, share_content, goods_id, "1");
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
            case R.id.btn_jgsm:
                if (ser_list != null) {
                    showPop(v, "价格说明", goods_price_desc, 0);
                }
                break;
            case R.id.im_service_more:
                if (ser_list != null) {
                    showPop(v, "服务说明", ser_list, 1);
                }
                break;
            case R.id.layout_djq:
                showDjqPop(v, dj_ticket);
                break;
            case R.id.tv_quxiao://促销弹框
                showCXPop(v);
                break;
            case R.id.tv_lingquan:
                showLQPop(v, "领券");
                break;
            case R.id.im_toarrs:
                toAttrs(v, goodsInfo.get("goods_img"), goodsInfo.get("shop_price"), (ArrayList) goodsAttrs);
                break;
        }
    }

    CommonPopupWindow commonPopupWindow;

    public void showPop(View view, final String title, final List<Map<String, String>> list, final int type) {//
        if (commonPopupWindow != null && commonPopupWindow.isShowing()) return;
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
                        recyclerview.setAdapter(new service_adp(list, type));
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


    /**
     * 领券
     */
    public void showLQPop(View view, final String title) {//
        if (commonPopupWindow != null && commonPopupWindow.isShowing()) return;
        commonPopupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_layout)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, Settings.displayHeight / 2)
                .setBackGroundLevel(0.7f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId, int position) {
                        TextView cancel = (TextView) view.findViewById(R.id.cancel);
                        RecyclerView recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
                        recyclerview.setLayoutManager(new GridLayoutManager(LimitGoodsAty.this, 2));
                        recyclerview.setAdapter(theTrickAdapter);
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

    /**
     * 促销
     *
     * @param view
     */
    public void showCXPop(View view) {
        if (commonPopupWindow != null && commonPopupWindow.isShowing()) return;
        commonPopupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.layou_popp_cuxiao)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, Settings.displayHeight / 2)
                .setBackGroundLevel(0.7f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId, int position) {
                        ListView promotion_lv = view.findViewById(R.id.promotion_lv);
                        PromotionAdapter promotionAdapter = new PromotionAdapter(LimitGoodsAty.this, promotionBeen);
                        promotion_lv.setAdapter(promotionAdapter);
                        TextView cancel = (TextView) view.findViewById(R.id.cancel);
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

    /**
     * 代金券的弹窗
     *
     * @param view
     */
    public void showDjqPop(final View view, final List<Map<String, String>> list) {
        if (commonPopupWindow != null && commonPopupWindow.isShowing()) return;
        commonPopupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.layout_popp_djq)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setBackGroundLevel(0.7f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId, int position) {
                        LinearLayout layout_djq0 = (LinearLayout) view.findViewById(R.id.layout_djq0);
                        LinearLayout layout_djq1 = (LinearLayout) view.findViewById(R.id.layout_djq1);
                        LinearLayout layout_djq2 = (LinearLayout) view.findViewById(R.id.layout_djq2);
                        TextView tv_djq_color0 = (TextView) view.findViewById(R.id.tv_djq_color0);
                        TextView tv_djq_color1 = (TextView) view.findViewById(R.id.tv_djq_color1);
                        TextView tv_djq_color2 = (TextView) view.findViewById(R.id.tv_djq_color2);
                        TextView tv_djq_desc0 = (TextView) view.findViewById(R.id.tv_djq_desc0);
                        TextView tv_djq_desc1 = (TextView) view.findViewById(R.id.tv_djq_desc1);
                        TextView tv_djq_desc2 = (TextView) view.findViewById(R.id.tv_djq_desc2);
                        TextView tv_desc = (TextView) view.findViewById(R.id.tv_desc);
                        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
                        tv_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                commonPopupWindow.dismiss();
                            }
                        });
                        tv_desc.setText(vouchers_desc);
                        for (int i = 0; i < list.size(); i++) {
                            switch (i) {
                                case 0: {
                                    layout_djq0.setVisibility(View.VISIBLE);
                                    tv_djq_desc0.setText(list.get(i).get("discount_desc"));
                                    break;
                                }
                                case 1: {
                                    layout_djq1.setVisibility(View.VISIBLE);
                                    tv_djq_desc1.setText(list.get(i).get("discount_desc"));
                                    break;
                                }
                                case 2: {
                                    layout_djq2.setVisibility(View.VISIBLE);
                                    tv_djq_desc2.setText(list.get(i).get("discount_desc"));
                                    break;
                                }
                            }
                            switch (list.get(i).get("type")) {
                                case "0": {
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
                    }
                }, 0)
                .setAnimationStyle(R.style.animbottom)
                .create();
        commonPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

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

            Glide.with(LimitGoodsAty.this).load(image.get(position).get("path"))
                    .centerCrop()
                    .override(bannerSize, bannerSize)
                    .error(R.drawable.ic_default)
                    .placeholder(R.drawable.ic_default)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imageView);

            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    };

    private void getHeight() {
        ViewTreeObserver vto = online_carvouse_view.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                online_carvouse_view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                // 轮播图高度
                bannerHeight = online_carvouse_view.getHeight();
                // 商品信息的高度
                topHeighe = top_lin_layout.getHeight();
                // 商品信息和评价的高度
                secondHeight = second_lin_layout.getHeight();
                limit_goods_details_sc.setScrollViewListener(LimitGoodsAty.this);
            }
        });
    }

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
                case 0:// 限量购
                    limitBuyPst.limitBuyInfo(limit_buy_id, page);
                    break;
                case 2:// 无界预购
                    perBuyPst.preBuyInfo(limit_buy_id, page);
                    break;
                case 10:// 无界商店
                    L.e("==========", String.valueOf(type));
                    integralBuyPst.integralBuyInfo(limit_buy_id, page);
                    break;
            }
        }
    }

    class service_adp extends RecyclerView.Adapter<service_adp.ViewHolder> {
        List<Map<String, String>> list;
        int tpye = 0;

        public service_adp(List<Map<String, String>> list, int type) {
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
            if (this.tpye == 0) {
                name = list.get(position).get("price_name") + "：";
                desc = list.get(position).get("desc");
                SpannableString msp = new SpannableString(name + desc);
                msp.setSpan(new ForegroundColorSpan(Color.parseColor("#F23030")), 0, name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置色
                holder.tv_text.setText(msp);
            } else if (this.tpye == 1) {
                name = list.get(position).get("server_name") + "：";
                desc = list.get(position).get("desc");
                SpannableString msp = new SpannableString(name + desc);
                msp.setSpan(new ForegroundColorSpan(Color.parseColor("#F23030")), 0, name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置色
                holder.tv_text.setText(msp);
            } else {
                holder.tv_text.setText(list.get(position).get("server_name"));
                holder.tv_text.setTextColor(Color.parseColor("#F23030"));
            }
            Glide.with(LimitGoodsAty.this).load(list.get(position).get("icon")).into(holder.im_logo);

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
        ArrayList<Map<String, String>> list;

        public cg_adp(ArrayList<Map<String, String>> list) {
            this.list = list;

        }

        @Override
        public cg_adp.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_dpg, null));
        }

        @Override
        public void onBindViewHolder(cg_adp.ViewHolder holder, int position) {
            Glide.with(LimitGoodsAty.this).load(list.get(position).get("goods_img")).into(holder.imageview);
            holder.tv_price.setText("¥" + list.get(position).get("shop_price"));
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
}
