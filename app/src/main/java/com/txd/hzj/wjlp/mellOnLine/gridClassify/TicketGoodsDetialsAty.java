package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.WeApplication;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.ants.theantsgo.tips.CustomDialog;
import com.ants.theantsgo.tips.MikyouCommonDialog;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.ListUtils;
import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.txd.hzj.wjlp.DemoApplication;
import com.txd.hzj.wjlp.MainAty;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.EasemobBean;
import com.txd.hzj.wjlp.bean.GoodsAttrs;
import com.txd.hzj.wjlp.bean.commodity.AllGoodsBean;
import com.txd.hzj.wjlp.bean.commodity.BodyBean;
import com.txd.hzj.wjlp.bean.commodity.CommentBean;
import com.txd.hzj.wjlp.bean.commodity.DjTicketBean;
import com.txd.hzj.wjlp.bean.commodity.GoodsCommonAttrBean;
import com.txd.hzj.wjlp.bean.commodity.PicturesBean;
import com.txd.hzj.wjlp.bean.commodity.PromotionBean;
import com.txd.hzj.wjlp.bean.commodity.TicketListBean;
import com.txd.hzj.wjlp.http.address.AddressPst;
import com.txd.hzj.wjlp.http.category.GoodsCategory;
import com.txd.hzj.wjlp.http.collect.UserCollectPst;
import com.txd.hzj.wjlp.http.goods.GoodsPst;
import com.txd.hzj.wjlp.http.ticketbuy.TicketBuyPst;
import com.txd.hzj.wjlp.mainFgt.adapter.AllGvLvAdapter;
import com.txd.hzj.wjlp.mellOnLine.SubclassificationAty;
import com.txd.hzj.wjlp.mellOnLine.adapter.GoodsCommentAttrAdapter;
import com.txd.hzj.wjlp.mellOnLine.adapter.PostAdapter;
import com.txd.hzj.wjlp.mellOnLine.adapter.PromotionAdapter;
import com.txd.hzj.wjlp.mellOnLine.adapter.TheTrickAdapter;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.adapter.CommentPicAdapter;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.snatch.SnatchGoodsDetailsAty;
import com.txd.hzj.wjlp.new_wjyp.aty_collocations;
import com.txd.hzj.wjlp.http.Easemob;
import com.txd.hzj.wjlp.http.Freight;
import com.txd.hzj.wjlp.shoppingCart.BuildOrderAty;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;
import com.txd.hzj.wjlp.tool.CommonPopupWindow;
import com.txd.hzj.wjlp.tool.TextUtils;
import com.txd.hzj.wjlp.tool.proUrbArea.ProUrbAreaUtil;
import com.txd.hzj.wjlp.view.ObservableScrollView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/10 0010
 * 时间：上午 10:13
 * 描述：票券区商品详情(3-2票券)
 * ===============Txunda===============
 */
public class TicketGoodsDetialsAty extends BaseAty implements ObservableScrollView.ScrollViewListener, ObservableScrollView.onBottomListener {
    private String is_attr = "";

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
    private ArrayList<Map<String, String>> image;

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
    private int clickType = 0;

    /**
     * 代金券
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
     * 轮播图宽高
     */
    private int bannerSize = 0;

    private TicketBuyPst ticketBuyPst;
    /**
     * 商品id
     */
    private String goods_id = "";
    /**
     * 票券区id
     */
    private String ticket_buy_id = "";

    /**
     * 购物车数量
     */
    @ViewInject(R.id.user_card_num_tv)
    private TextView user_card_num_tv;
    /**
     * 是否收藏
     */
    private String is_collect = "";

    @ViewInject(R.id.goods_title_collect_iv)
    private ImageView goods_title_collect_iv;
    @ViewInject(R.id.goods_title_collect_tv)
    private TextView goods_title_collect_tv;
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
     * 买家头像60dp
     */
    private int head_size = 0;
    @ViewInject(R.id.goods_brief_tv)
    private WebView goods_brief_tv;
    @ViewInject(R.id.goods_desc_wv)
    private WebView goods_desc_wv;

    @ViewInject(R.id.ticket_goods_tv)
    private TextView ticket_goods_tv;

    @ViewInject(R.id.goods_details_name_tv)
    private TextView goods_details_name_tv;

    /**
     * 促销布局
     */
    @ViewInject(R.id.promotion_layout)
    private LinearLayout promotion_layout;
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
     * 评价商品布局
     */
    @ViewInject(R.id.goods_for_my_evaluste_layout)
    private LinearLayout goods_for_my_evaluste_layout;

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

    @ViewInject(R.id.comment_layout)
    private LinearLayout comment_layout;
    @ViewInject(R.id.layout_comment)
    private LinearLayout layout_comment;
    /**
     * 产品规格
     */
    @ViewInject(R.id.goods_common_attr_lv)
    private ListViewForScrollView goods_common_attr_lv;
    private UserCollectPst collectPst;
    private String mell_id = ""; // 店铺id

    private int from = 0;
    private boolean is_f = true;//判断刷新
    private GoodsPst goodsPst;
    private String share_url = "";
    private String share_img = "";
    private String share_content = "";
    private String easemob_account = "";
    private String merchant_logo = "";
    private String merchant_name = "";
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
    private String vouchers_desc = "";//代金券弹窗下面的提示文字
    private List<PromotionBean> promotionBeen;
    private List<GoodsAttrs> goodsAttrs;
    private List<GoodsAttrs.product> goods_product;
    private String goods_attr_first;
    private String first_val;
    @ViewInject(R.id.tv_date)
    private TextView tv_date;
    @ViewInject(R.id.goods_select_attr_tv)
    private TextView goods_select_attr_tv;
    private int goods_number = 0;
    private String product_id = "";
    private boolean is_C = false;
    private String goodsName = "";
    private String cate_id;
    private String pcate_id;
    private AddressPst addressPst;

    private String discount_desc0; // 红券描述
    private String discount_desc1; // 黄券描述
    private String discount_desc2; // 蓝券描述
    private View easemobView;
    private EasemobBean easemobBean; // 获取的客服环信账号对象
    private String messageStr = "当前商品已下架";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.goods_details_title);
        // 设置轮播图高度
        bannerSize = Settings.displayWidth;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(bannerSize, bannerSize);
        online_carvouse_view.setLayoutParams(layoutParams);
        goods_for_my_evaluste_layout.setVisibility(View.GONE);
        wujie_post_lv.setAdapter(postAdapter);
        // 判断是否显示回到顶部按钮
        getHeight();
        rv_service.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        goods_trick_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        goods_trick_rv.setHasFixedSize(true);
        goods_trick_rv.setAdapter(theTrickAdapter);
        ProUrbAreaUtil.gainInstance().checkData((WeApplication) getApplication());
    }

    @Override
    @OnClick({R.id.title_goods_layout, R.id.title_details_layout, R.id.title_evaluate_layout,
            R.id.goods_title_collect_layout, R.id.goods_title_share_tv, R.id.show_or_hide_iv,
            R.id.show_or_hide_lv_iv, R.id.show_or_hide_explain_iv, R.id.be_back_top_iv,
            R.id.go_to_cart_layout, R.id.be_back_main_tv, R.id.details_into_mell_tv,
            R.id.tv_chose_ads, R.id.all_evaluate_tv,
            R.id.to_chat_tv, R.id.tv_tab_1, R.id.tv_tab_2, R.id.tv_tab_3, R.id.layout_layout_settings,
            R.id.tv_gwc, R.id.tv_ljgm, R.id.btn_jgsm, R.id.im_service_more, R.id.tv_lingquan,
            R.id.layout_djq, R.id.tv_showClassify, R.id.tv_quxiao, R.id.tv_wjsd, R.id.tv_dpg})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.all_evaluate_tv: {
                Bundle bundle = new Bundle();
                bundle.putInt("from", 0);
                bundle.putString("goods_id", goods_id);
                bundle.putString("mid", mell_id);
                startActivity(GoodsEvaluateAty.class, bundle);
                break;
            }
            case R.id.tv_chose_ads: // 弹出地址选择器
                ProUrbAreaUtil.gainInstance().showPickerView(tv_chose_ads, goods_id, this);
                break;
            case R.id.title_goods_layout:// 商品
                clickType = 1;
                limit_goods_details_sc.smoothScrollTo(0, 0);
                break;
            case R.id.title_details_layout:// 详情
                clickType = 2;
                limit_goods_details_sc.smoothScrollTo(0, secondHeight);
                break;
            case R.id.title_evaluate_layout:// 评价
                clickType = 3;
                limit_goods_details_sc.smoothScrollTo(0, topHeighe);
                break;
            case R.id.goods_title_collect_layout:// 收藏
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
            case R.id.goods_title_share_tv: // 分享
                toShare(goodsName, share_img, share_url, share_url, goods_id, "1");
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
//                    wujie_post_lv.setVisibility(View.VISIBLE);
//                    show_or_hide_lv_iv.setImageResource(R.drawable.icon_show_other_layout);
                } else {// 显示状态
//                    wujie_post_lv.setVisibility(View.GONE);
//                    show_or_hide_lv_iv.setImageResource(R.drawable.icon_hide_other_layout);
                }
                break;
            case R.id.show_or_hide_explain_iv: // 展开,隐藏(无界驿站)
                getHeight(); // 重新计算高度
                if (goods_other_info_layout.getVisibility() == View.GONE) { // 隐藏状态
                    goods_other_info_layout.setVisibility(View.VISIBLE);
                    show_or_hide_explain_iv.setImageResource(R.drawable.icon_show_other_layout);
                } else { // 显示状态
                    goods_other_info_layout.setVisibility(View.GONE);
                    show_or_hide_explain_iv.setImageResource(R.drawable.icon_hide_other_layout);
                }
                break;
            case R.id.be_back_top_iv: // 回到顶部
                limit_goods_details_sc.smoothScrollTo(0, 0);
                setTextViewAndViewColor(0);
                break;
            case R.id.go_to_cart_layout: // 购物车
                backMain(2);
                break;
            case R.id.be_back_main_tv: // 首页
                backMain(0);
                break;
            case R.id.details_into_mell_tv: // 进店逛逛
                Bundle bundle = new Bundle();
                bundle.putString("mell_id", mell_id);
                startActivity(MellInfoAty.class, bundle);
                break;
            case R.id.to_chat_tv: // 客服

                Easemob.bind(mell_id, this); // 获取商铺的环信账号
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
            case R.id.tv_gwc:
                if (is_C) {
                    RequestParams params = new RequestParams();
                    ApiTool2 apiTool2 = new ApiTool2();
                    params.addBodyParameter("goods_id", goods_id);
                    params.addBodyParameter("product_id", product_id);
                    params.addBodyParameter("num", String.valueOf(goods_number));
                    apiTool2.postApi(Config.BASE_URL + "Cart/addCart", params, this);
                } else {
                    // 购物车, (ArrayList) goodsAttrs, (ArrayList) goods_product
                    try {
                        String goods_img = goodsInfo.get("goods_img");
                        String shop_price = goodsInfo.get("shop_price");
                        toAttrs(v, 1, "1", goods_id, goods_img, shop_price, "", goods_attr_first, first_val, is_attr);
                    } catch (Exception e) {
                        L.e("TicketGoodsDetialsAty is Exception:" + e.toString());
                        showErrorTip("获取字段异常");
                    }
                }
                break;
            case R.id.tv_ljgm:
                if (is_C) {
                    Intent intent = new Intent();
                    intent.putExtra("mid", mell_id);
                    intent.putExtra("type", "1");
                    intent.putExtra("goods_id", goods_id);
                    intent.putExtra("group_buy_id", "");
                    intent.putExtra("num", String.valueOf(goods_number));
                    intent.putExtra("product_id", product_id);
                    intent.setClass(this, BuildOrderAty.class);
                    startActivity(intent);
                } else {
                    //直接购买, (ArrayList) goodsAttrs, (ArrayList) goods_product
                    try {
                        String goods_img = goodsInfo.get("goods_img");
                        String shop_price = goodsInfo.get("shop_price");
                        toAttrs(v, 0, "1", goods_id + "-" + mell_id, goods_img, shop_price, "", goods_attr_first, first_val, is_attr);
                    } catch (Exception e) {
                        L.e("tv_ljgm throw Exception :" + e.toString());
                        showErrorTip("获取字段异常");
                    }
                }
                break;
            case R.id.btn_jgsm:
                if (goods_price_desc != null) {
                    showPop(v, "价格说明", goods_price_desc, 0);
                }
                break;
            case R.id.im_service_more:
                if (ser_list != null) {
                    showPop(v, "服务说明", ser_list, 1);
                }
                break;
            case R.id.layout_layout_settings://(ArrayList) goodsAttrs, (ArrayList) goods_product

                try {
                    String goods_img = goodsInfo.get("goods_img");
                    String shop_price = goodsInfo.get("shop_price");
                    toAttrs(v, 4, "1", goods_id + "-" + mell_id, goods_img, shop_price, "", goods_attr_first, first_val, is_attr);
                } catch (Exception e) {
                    L.e("layout_layout_settings throw Exception :" + e.toString());
                    showErrorTip("获取字段异常");
                }

                break;
            case R.id.layout_djq:
                showDjqPop(v, dj_ticket);
                break;
            case R.id.tv_showClassify:
                cate_id = goodsInfo.get("cate_id");
                pcate_id = goodsInfo.get("pcate_id");
                GoodsCategory.cateIndexs(cate_id, this);
                showProgressDialog();

                break;
            case R.id.tv_quxiao://促销弹框
                showCXPop(v);
                break;
            case R.id.tv_lingquan:
                showLQPop(v, "领券");
                break;
            case R.id.tv_wjsd:
                Bundle mBundle = new Bundle();
                mBundle.putInt("type", 10);
                mBundle.putString("title", "无界商店");
                startActivity(TicketZoonAty.class, mBundle);
                break;
            case R.id.tv_dpg:
                Bundle bundle1 = new Bundle();
                bundle1.putString("goods_id", goods_id);
                startActivity(aty_collocations.class, bundle1);
                break;
        }
    }

    @ViewInject(R.id.tv_chose_ads)
    private TextView tv_chose_ads;

    /**
     * 省
     */
    private String province = "";
    /**
     * 市
     */
    private String city = "";
    /**
     * 区
     */
    private String area = "";

    private ArrayList<Map<String, String>> ser_list;//服务的列表
    private ArrayList<Map<String, String>> goods_price_desc;//价格的列表
    //    ArrayList<Map<String, String>> dj_ticket;//代金券的说明
    private List<DjTicketBean> dj_ticket;

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
            Glide.with(TicketGoodsDetialsAty.this).load(image.get(position).get("path"))
                    .centerCrop()
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .override(bannerSize, bannerSize)
                    .into(imageView);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    };

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_ticket_goods_detials;
    }

    @Override
    protected void initialized() {
        ticket_buy_id = getIntent().getStringExtra("ticket_buy_id");
        ticketBuyPst = new TicketBuyPst(this);
        collectPst = new UserCollectPst(this);
        goodsPst = new GoodsPst(this);
        from = getIntent().getIntExtra("from", 0);
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

    private int page = 1;

    @Override
    protected void requestData() {
        if (0 == from) {
            ticketBuyPst.ticketBuyInfo(ticket_buy_id, page);
        } else {
            goodsPst.goodsInfo(ticket_buy_id, page);
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

    private List<AllGoodsBean> ticket = new ArrayList<>();
    private List<AllGoodsBean> more = new ArrayList<>();
    Map<String, String> goodsInfo;


    @ViewInject(R.id.remarks)
    private TextView remarks;

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        L.e("=========wang==========", requestUrl + "  jsonstr:" + jsonStr);
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("addCart")) {
            showToast("添加成功！");
            if (0 == from) {
                ticketBuyPst.ticketBuyInfo(ticket_buy_id, page);
            } else {
                goodsPst.goodsInfo(ticket_buy_id, page);
            }
        }
        if (requestUrl.contains("freight")) {
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            map = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            freight_tv.setText(map.get("pay"));
            freight_tv.setTextColor(Color.parseColor("#FD8214"));
            tv_freight.setText(map.get("pay"));
        }
        if (requestUrl.contains("ticketBuyInfo") || requestUrl.contains("goodsInfo")) {
            L.e("cccc" + jsonStr);
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            String cart_num = data.get("cart_num");
            L.e("aaaaa" + data.get("cart_num"));
            is_attr = data.get("is_attr");
            remarks.setText(data.get("remarks"));
            forBase(data, cart_num);
            share_url = data.get("share_url");
            share_img = data.get("share_img");

            share_content = data.get("share_content"); // TODO 此处为null
            // 轮播图
            if (ToolKit.isList(data, "goods_banner")) {
                image = JSONUtils.parseKeyAndValueToMapList(data.get("goods_banner"));
                forBanner();
            }
//            goodsAttrs = GsonUtil.getObjectList(data.get("goods_attr"), GoodsAttrs.class);
//            goods_product = GsonUtil.getObjectList(data.get("product"), GoodsAttrs.product.class);
            goods_attr_first = data.get("first_list");
            first_val = data.get("first_val");
            vouchers_desc = data.get("vouchers_desc");
            // 商品基本信息
            goodsInfo = JSONUtils.parseKeyAndValueToMap(data.get("goodsInfo"));
            /**
             *以下表示如果buy_status==0，表示当前商品已经下架
             * */
            if (goodsInfo.containsKey("buy_status") && goodsInfo.get("buy_status").equals("0")) {
                CustomDialog.Builder dialog = new CustomDialog.Builder(this);
                dialog.setMessage("当前商品已下架");
                dialog.setTitle("下架提示");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TicketGoodsDetialsAty.this.finish();
                    }
                });
                dialog.setCancelable(false);
                dialog.create().show();
            }
            goodsName = goodsInfo.get("goods_name");
            forGoodsInfo(goodsInfo);

            tv_jgsm.setText(data.get("price_desc")); // 价格说明
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
            // 商家信息
            Map<String, String> mInfo = JSONUtils.parseKeyAndValueToMap(data.get("mInfo"));
            forMellInfo(mInfo);

            if (ToolKit.isList(data, "promotion")) {
                forPromotion(data);
                promotion_layout.setVisibility(View.VISIBLE);
            } else {
                promotion_layout.setVisibility(View.GONE);
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

            // 代金券列表
            if (ToolKit.isList(data, "ticketList")) {
                List<TicketListBean> ticketListBeens = GsonUtil.getObjectList(data.get("ticketList"), TicketListBean.class);
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
                    if (Integer.parseInt(comment.getTotal()) > 0) { // 如果评论数量大于0（有评论）则显示该模块内容
                        layout_comment.setVisibility(View.VISIBLE);
                        Map<String, String> commentMap = JSONUtils.parseKeyAndValueToMap(data.get("comment"));
                        BodyBean bodyBean = comment.getBody();
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
                            tv_date.setText(bodyBean.getCreate_time());
                            List<PicturesBean> pictures = bodyBean.getPictures();
                            if (!ListUtils.isEmpty(pictures)) {
                                CommentPicAdapter picadapter = new CommentPicAdapter(this, pictures);
                                estimate_pic.setAdapter(picadapter);
                            }
                        }
                    } else { // 否则没有评论，则隐藏该模块的整体内容
                        layout_comment.setVisibility(View.GONE);
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
            // TODO==========产品属性==========
            if (ToolKit.isList(data, "goods_common_attr")) {
                List<GoodsCommonAttrBean> gca = GsonUtil.getObjectList(data.get("goods_common_attr"), GoodsCommonAttrBean.class);
                GoodsCommentAttrAdapter gcaAdapter = new GoodsCommentAttrAdapter(TicketGoodsDetialsAty.this, gca);
                goods_common_attr_lv.setAdapter(gcaAdapter);
            }
            L.e("aaaaa" + data.get("cheap_group"));
            if (!data.get("cheap_group").equals("[]")) {
                //搭配购
                Map<String, String> cheap_group = JSONUtils.parseKeyAndValueToMap(data.get("cheap_group"));
                if (cheap_group != null) {
                    tv_ticket_buy_discount.setText("最多可用" + cheap_group.get("ticket_buy_discount") + "%代金券");
                    tv_group_price.setText("搭配价：¥" + cheap_group.get("group_price"));
                    tv_group_integral.setText(cheap_group.get("integral"));
                    double price = Double.parseDouble(cheap_group.get("goods_price")) - Double.parseDouble(cheap_group.get("group_price"));
                    DecimalFormat df = new DecimalFormat("#.00");
                    tv_goods_price.setText("立省¥" + df.format(price));
                    ArrayList<Map<String, String>> maps = JSONUtils.parseKeyAndValueToMapList(cheap_group.get("goods"));
                    rv_cheap_group.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                    rv_cheap_group.setAdapter(new cg_adp(maps));
                } else {
                    layout_cheap_group.setVisibility(View.GONE);

                }
            }

            return;

        }
        if (requestUrl.contains("addCollect")) {// 添加收藏
            showRightTip("收藏成功");
            is_collect = "1";
            goods_title_collect_tv.setCompoundDrawables(null, TextUtils.toDrawable(this, R.drawable.icon_collected), null, null);
            goods_title_collect_tv.setText("已收藏");
            return;
        }
        if (requestUrl.contains("delOneCollect")) {
            showRightTip("取消成功");
            is_collect = "0";
            goods_title_collect_tv.setCompoundDrawables(null, TextUtils.toDrawable(this, R.drawable.icon_collect), null, null);
            goods_title_collect_tv.setText("收藏");
        }
        if (requestUrl.contains("cateIndex")) {
            L.e("json" + jsonStr);
            Map<String, String> datas = JSONUtils.parseKeyAndValueToMap(jsonStr);
            if (datas.get("code").equals("1")) {
                datas = JSONUtils.parseKeyAndValueToMap(datas.get("data"));
                List<Map<String, String>> mapList = JSONUtils.parseKeyAndValueToMapList(datas.get("two_cate"));
                int mm = 0;
                for (int m = 0; m < mapList.size(); m++) {
                    L.e("cate_id" + mapList.get(m).get("cate_id"));
                    if (mapList.get(m).get("cate_id").equals(pcate_id)) {
                        mm = m + 1;
                        break;
                    }
                }
                //查看分类
                Intent intent = new Intent();
                intent.putExtra("appBarTitle", goodsInfo.get("two_cate_name"));
                intent.putExtra("two_cate_id", goodsInfo.get("cate_id"));
                intent.putExtra("page", mm);
                intent.setClass(this, SubclassificationAty.class);
                startActivity(intent);
            }
        }

        /**
         * 获取商家环信账号
         */
        if (requestUrl.contains("Easemob/bind")) {
            L.e("Easemob/bind：" + jsonStr);
            if (jsonStr == null || jsonStr.equals("")) {
                showErrorTip("获取数据为空，请联系我们");
                return;
            }

            Gson gson = new Gson();
            easemobBean = gson.fromJson(jsonStr, EasemobBean.class); // 如果Json有值 bean 对象必定有值

            // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓创建Dialog弹窗显示列表项↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
            AlertDialog.Builder builder = new AlertDialog.Builder(this); // 创建对话框构建器
            View view2 = View.inflate(TicketGoodsDetialsAty.this, R.layout.popup_sel_chat, null); // 获取布局
            builder.setView(view2); // 设置参数主要是设置获取的布局View
            // 获取布局中的控件
            ListView dataLv = (ListView) view2.findViewById(R.id.popSelChat_data_lv);
            LinearLayout nodataLayout = (LinearLayout) view2.findViewById(R.id.popSelChat_nodata_layout);

            // 以上判断Bean有值，但是以防万一还是先判空
            if (easemobBean == null || easemobBean.getData().getEasemob_account_num() < 1) {
                // 如果Bean为空或者获取的在线客服账号数小于1，也就是没有在线客服
                dataLv.setVisibility(View.GONE); // 隐藏List列表
                nodataLayout.setVisibility(View.VISIBLE); // 显示空数据提示
            } else {
                // 否则就是有在线客服
                dataLv.setVisibility(View.VISIBLE); // 显示List列表
                nodataLayout.setVisibility(View.GONE); // 隐藏空数据提示
            }

            final AlertDialog alertDialog = builder.create();// 创建对话框
            // 设置相应的控件操作，赋值、点击事件等等

            dataLv.setAdapter(new DialogAdapter(easemobBean.getData().getEasemob_account()));

            dataLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    EasemobBean.DataBean.EasemobAccountBean easemobAccountBean = easemobBean.getData().getEasemob_account().get(position);
                    // 参数说明：账号、头像、昵称
                    toChat(easemobAccountBean.getHx(), easemobAccountBean.getHead_pic(), easemobAccountBean.getNickname());
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();

            // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑创建Dialog弹窗显示列表项↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        }

    }

    /**
     * 显示客服弹窗的List适配器
     */
    class DialogAdapter extends BaseAdapter {

        List<EasemobBean.DataBean.EasemobAccountBean> list;

        public DialogAdapter(List<EasemobBean.DataBean.EasemobAccountBean> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyHolder holder;
            if (convertView == null) {
                holder = new MyHolder();
                convertView = LayoutInflater.from(TicketGoodsDetialsAty.this).inflate(R.layout.item_popup_sel_chat, null);
                convertView.setTag(holder);
                com.lidroid.xutils.ViewUtils.inject(holder, convertView);
            } else {
                holder = (MyHolder) convertView.getTag();
            }

            EasemobBean.DataBean.EasemobAccountBean easemobAccountBean = list.get(position);

            Glide.with(TicketGoodsDetialsAty.this).load(list.get(position).getHead_pic())
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(holder.itemPopSelChat_headPic_imgv);
            holder.itemPopSelChat_nickname_tv.setText(easemobAccountBean.getNickname());
            holder.itemPopSelChat_position_tv.setText(easemobAccountBean.getPosition());

            return convertView;
        }

        class MyHolder {
            @ViewInject(R.id.itemPopSelChat_headPic_imgv)
            ImageView itemPopSelChat_headPic_imgv; // 客服头像
            @ViewInject(R.id.itemPopSelChat_nickname_tv)
            TextView itemPopSelChat_nickname_tv; // 客服名称
            @ViewInject(R.id.itemPopSelChat_position_tv)
            TextView itemPopSelChat_position_tv; // 在线状态
        }

    }

    /**
     * 是否被收藏，购物车数量
     *
     * @param data     原始数据
     * @param cart_num 购物车数量
     */
    private void forBase(Map<String, String> data, String cart_num) {
        if (!cart_num.equals("0")) {
            user_card_num_tv.setText(cart_num);
            user_card_num_tv.setVisibility(View.VISIBLE);
        } else {
            user_card_num_tv.setVisibility(View.GONE);
        }
        // 是否收藏
        is_collect = data.get("is_collect");

        if ("0".equals(is_collect)) {
            goods_title_collect_tv.setCompoundDrawables(null, TextUtils.toDrawable(this, R.drawable.icon_collect), null, null);
            goods_title_collect_tv.setText("收藏");
        } else {
            goods_title_collect_tv.setCompoundDrawables(null, TextUtils.toDrawable(this, R.drawable.icon_collected), null, null);
            goods_title_collect_tv.setText("已收藏");
        }
    }

    @ViewInject(R.id.tv_salesvolume)
    private TextView tv_salesvolume;//销量
    @ViewInject(R.id.tv_inventory)
    private TextView tv_inventory;//库存
    @ViewInject(R.id.tv_freight)
    private TextView tv_freight;//运费
    @ViewInject(R.id.tv_expirationdate)
    private TextView tv_expirationdate;//保质期提示
    @ViewInject(R.id.tv_wy_price)
    private TextView tv_wy_price;//无忧价
    @ViewInject(R.id.tv_yx_price)
    private TextView tv_yx_price;//优享价
    @ViewInject(R.id.layout_jifen)
    private LinearLayout layout_jifen;//积分布局
    @ViewInject(R.id.tv_integral)
    private TextView tv_integral;//积分
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
    @ViewInject(R.id.lv_qkk)
    private ListViewForScrollView lv_qkk;//去看看列表
    @ViewInject(R.id.im_country_logo)
    private ImageView im_country_logo;//国旗
    @ViewInject(R.id.tv_country_desc)
    private TextView tv_country_desc;//国家
    @ViewInject(R.id.tv_country_tax)
    private TextView tv_country_tax;//进口税
    @ViewInject(R.id.layou_jinkoushui)//进口税的布局 0的话就隐藏
    private LinearLayout layou_jinkoushui;
    @ViewInject(R.id.layout_service)
    private LinearLayout layout_service;//服务布局
    @ViewInject(R.id.rv_service)
    private RecyclerView rv_service;
    CommonPopupWindow commonPopupWindow;

    /**
     * 商品信息
     *
     * @param goodsInfo 商品信息
     */
    private void forGoodsInfo(Map<String, String> goodsInfo) {
        // 商品id
        goods_id = goodsInfo.get("goods_id");

        L.e("=============goodsInfo===============", goodsInfo.toString());

        // 直接获取首界面定位的信息
        String tx = "";
        if (MainAty.GDLOC_MAP != null) { // 如果定位信息为空，就设置原始的提示消息
            tx = MainAty.GDLOC_MAP.get("province") + "," + MainAty.GDLOC_MAP.get("city") + "," + MainAty.GDLOC_MAP.get("district");
        } else {
            tx = DemoApplication.getInstance().getLocInfo().get("province") + "," + DemoApplication.getInstance().getLocInfo().get("city") + "," + DemoApplication.getInstance().getLocInfo().get("district");
        }
        tv_chose_ads.setText(tx);
        L.e("==========商品详情获取的定位信息===========" + tx);
        // 定位好之后获取运费信息
        Freight.freight(goods_id, tx, TicketGoodsDetialsAty.this);
        showProgressDialog();
        // 商品价格
        // ChangeTextViewStyle.getInstance().forGoodsPrice(this, now_price_tv, "￥" + goodsInfo.get("shop_price"));
        now_price_tv.setText(goodsInfo.get("shop_price"));
        // 商品原价
        old_price_tv.setText("￥" + goodsInfo.get("market_price"));
        old_price_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        // 积分
        ChangeTextViewStyle.getInstance().forTextColor(this, goods_profit_num_tv,
                "积分" + goodsInfo.get("integral"), 2, Color.parseColor("#FD8214"));
        // 运费(待定)
        ChangeTextViewStyle.getInstance().forTextColor(this, freight_tv,
                "运费10元", 2, Color.parseColor("#FD8214"));
        // 文字描述
        // goods_brief_tv.loadDataWithBaseURL(null, goodsInfo.get("goods_brief"), "text/html", "utf-8", null);
        // 图文详情
        goods_desc_wv.loadDataWithBaseURL(null, goodsInfo.get("goods_desc"), "text/html", "utf-8", null);
        goods_details_name_tv.setText(goodsInfo.get("goods_name"));
        // 长按标题进行复制标题文字
        goods_details_name_tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                new MikyouCommonDialog(TicketGoodsDetialsAty.this, "长按将复制文字：" + goods_details_name_tv.getText().toString(), "操作提示", "复制", "取消", true)
                        .setOnDiaLogListener(new MikyouCommonDialog.OnDialogListener() {

                            @Override
                            public void dialogListener(int btnType, View customView, DialogInterface dialogInterface, int which) {
                                switch (btnType) {
                                    case MikyouCommonDialog.OK: { // 复制文字
                                        copyFromTextView(goods_details_name_tv);
                                        showToast("商品名称已复制到剪切板，快去粘贴吧~");
                                    }
                                    break;
                                    case MikyouCommonDialog.NO: { // 取消操作
                                    }
                                    break;
                                }
                            }
                        }).showDialog();
                return false;
            }
        });
        ticket_goods_tv.setText("可使用" + goodsInfo.get("ticket_buy_discount") + "%代金券");
        /**判断这块儿显示和隐藏
         * "is_new_goods": "1",//是否是新品  0不是 1是
         "is_new_goods_desc": "此件商品是旧货八五成新",//新品描述
         "is_end": "0",//是否临期 0未临期 1临期
         "is_end_desc": "此商品属于临期商品，商品保质期到期日为2017-20-30",// 临期描述
         */
        if (goodsInfo.get("is_new_goods").equals("0") && goodsInfo.get("is_end").equals("1")) {
            tv_expirationdate.setText(goodsInfo.get("is_new_goods_desc") + "\n" + goodsInfo.get("is_end_desc"));
        } else if (goodsInfo.get("is_new_goods").equals("0")) {
            tv_expirationdate.setText(goodsInfo.get("is_new_goods_desc"));
        } else if (goodsInfo.get("is_end").equals("1")) {
            tv_expirationdate.setText(goodsInfo.get("is_end_desc"));
        } else {
            tv_expirationdate.setVisibility(View.GONE);
        }
        tv_salesvolume.setText("销量\t" + goodsInfo.get("sell_num"));
        tv_inventory.setText("库存\t" + goodsInfo.get("goods_num"));
        is_attr = is_attr + "-" + goodsInfo.get("goods_num");
        //tv_freight.setText(goodsInfo.get(""));
        tv_wy_price.setText("¥" + goodsInfo.get("wy_price"));
        tv_yx_price.setText("¥" + goodsInfo.get("yx_price"));
        if (goodsInfo.get("integral_buy_id").equals("0")) {
            layout_jifen.setVisibility(View.GONE);
        } else {
            //此商品可以使用xxx积分兑换，如想使用积分兑换请到无界商店进行兑换
            tv_integral.setText("此商品可以使用" + goodsInfo.get("use_integral") + "积分兑换，如想使用积分兑换请到无界商店进行兑换");
        }
        tv_brief.setText(goodsInfo.get("goods_brief"));

        if (ToolKit.isList(goodsInfo, "dj_ticket")) {
            dj_ticket = JSONUtils.parseKeyAndValueToMapList(DjTicketBean.class, goodsInfo.get("dj_ticket"));

            for (int i = 0; i < dj_ticket.size(); i++) {
                if (i == 2) {
                    break;
                }
                switch (i) {
                    case 0: {
                        layout_djq0.setVisibility(View.VISIBLE);
                        discount_desc0 = dj_ticket.get(i).getDiscount_desc();
                        tv_djq_desc0.setText(discount_desc0);
                        break;
                    }
                    case 1: {
                        layout_djq1.setVisibility(View.VISIBLE);
                        discount_desc1 = dj_ticket.get(i).getDiscount_desc();
                        tv_djq_desc1.setText(discount_desc1);
                        break;
                    }
                    case 2: {
                        layout_djq2.setVisibility(View.GONE);
                        discount_desc2 = dj_ticket.get(i).getDiscount_desc();
                        tv_djq_desc2.setText(discount_desc2);
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
        if (ToolKit.isList(goodsInfo, "goods_active")) {
            final ArrayList<Map<String, String>> qkk_list = JSONUtils.parseKeyAndValueToMapList(goodsInfo.get("goods_active"));
            lv_qkk.setAdapter(new qkk_adapter(qkk_list));
            lv_qkk.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    switch (qkk_list.get(i).get("act_type")) {
                        case "1": {
                            Bundle bundle = new Bundle();
                            bundle.putString("one_buy_id", qkk_list.get(i).get("act_id"));
                            startActivity(SnatchGoodsDetailsAty.class, bundle);
                        }
                        break;
                        case "2": {
                            Bundle bundle = new Bundle();
                            bundle.putString("limit_buy_id", qkk_list.get(i).get("act_id"));
                            bundle.putInt("type", 2);
                            startActivity(LimitGoodsAty.class, bundle);

                        }
                        break;
                        case "3": {
                            Bundle bundle = new Bundle();
                            bundle.putString("auction_id", qkk_list.get(i).get("act_id"));
                            startActivity(AuctionGoodsDetailsAty.class, bundle);
                        }
                        break;
                        case "4": {
                            Bundle bundle = new Bundle();
                            bundle.putString("limit_buy_id", qkk_list.get(i).get("act_id"));
                            bundle.putInt("type", 0);
                            startActivity(LimitGoodsAty.class, bundle);
                        }

                        break;
                        case "5": {
                            Bundle bundle = new Bundle();
                            bundle.putString("group_buy_id", qkk_list.get(i).get("act_id"));
                            startActivity(GoodLuckDetailsAty.class, bundle);
                        }
                        break;
                    }
                }
            });
        }
        Glide.with(this).load(goodsInfo.get("country_logo")).into(im_country_logo);
        tv_country_desc.setText(goodsInfo.get("country_desc"));
//        tv_country_tax.setText(goodsInfo.get("country_tax") + "元");
        tv_country_tax.setText(goodsInfo.get("country_tax"));
        if (Double.parseDouble(goodsInfo.get("country_tax")) <= 0) {
            layou_jinkoushui.setVisibility(View.GONE);
        }

        tv_bzqd.setText(goodsInfo.get("package_list")); //包装清单
        tv_shfw.setText(goodsInfo.get("after_sale_service")); //售后服务


    }

    /**
     * 商家信息
     *
     * @param mInfo 商家信息
     */
    private void forMellInfo(Map<String, String> mInfo) {

        L.e("=========mInfo===============", mInfo.toString());

        mell_id = mInfo.get("merchant_id");
//        Easemob.bind(mell_id, this);
        showProgressDialog();
        easemob_account = mInfo.get("merchant_easemob_account");
        merchant_logo = mInfo.get("logo");
        merchant_name = mInfo.get("merchant_name");

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
    }

    /**
     * 商品促销活动
     *
     * @param data 原始数据
     */
    private void forPromotion(Map<String, String> data) {
        promotionBeen = GsonUtil.getObjectList(data.get("promotion"), PromotionBean.class);
//        PromotionBean prom = promotionBeen.get(0);
//        String type = prom.getType();
//        int imageId = getResources().getIdentifier("icon_get_coupon_hzj_" + type, "drawable", getPackageName());
//        onle_pro_type_iv.setImageResource(imageId);
//        onle_pro_title_tv.setText(prom.getTitle());
//        promotionBeen.remove(prom);
        if (!ListUtils.isEmpty(promotionBeen)) {// 判断移除掉一个活动之后是否为空
//            goods_bottom_lin_layout.setVisibility(View.VISIBLE);
            promotionAdapter = new PromotionAdapter(this, promotionBeen);
            promotion_lv.setAdapter(promotionAdapter);
//            show_or_hide_iv.setEnabled(true);
        } else {
//            goods_bottom_lin_layout.setVisibility(View.GONE);
            // 此处可以设置。。不呢点击
//            show_or_hide_iv.setEnabled(false);
        }
    }

    private void getHeight() {
        ViewTreeObserver vto = online_carvouse_view.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                online_carvouse_view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                bannerHeight = online_carvouse_view.getHeight();
                topHeighe = top_lin_layout.getHeight();
                secondHeight = second_lin_layout.getHeight();
                limit_goods_details_sc.setScrollViewListener(TicketGoodsDetialsAty.this);

            }
        });
        limit_goods_details_sc.setOnBottomListener(TicketGoodsDetialsAty.this);
    }

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
                        recyclerview.setLayoutManager(new LinearLayoutManager(TicketGoodsDetialsAty.this, 1, false));
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
     * 代金券的弹窗
     *
     * @param view
     */
    public void showDjqPop(final View view, final List<DjTicketBean> list) {
        if (commonPopupWindow != null && commonPopupWindow.isShowing()) {
            return;
        }
        if (list == null || list.size() == 0) {
            return;
        }
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
                                    tv_djq_desc0.setText(list.get(i).getDiscount_desc());
                                    break;
                                }
                                case 1: {
                                    layout_djq1.setVisibility(View.VISIBLE);
                                    tv_djq_desc1.setText(list.get(i).getDiscount_desc());
                                    break;
                                }
                                case 2: {
                                    layout_djq2.setVisibility(View.VISIBLE);
                                    tv_djq_desc2.setText(list.get(i).getDiscount_desc());
                                    break;
                                }
                            }

                            switch (list.get(i).getType()) {
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
                        PromotionAdapter promotionAdapter = new PromotionAdapter(TicketGoodsDetialsAty.this, promotionBeen);
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
                        recyclerview.setLayoutManager(new GridLayoutManager(TicketGoodsDetialsAty.this, 2));
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
            if (0 == from) {
                ticketBuyPst.ticketBuyInfo(ticket_buy_id, page);
            } else {
                goodsPst.goodsInfo(ticket_buy_id, page);
            }
        }
    }

    class qkk_adapter extends BaseAdapter {
        ArrayList<Map<String, String>> list;

        public qkk_adapter(ArrayList<Map<String, String>> list) {
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
            view = View.inflate(TicketGoodsDetialsAty.this, R.layout.item_lh_qkk, null);
            TextView act_desc = (TextView) view.findViewById(R.id.tv_qkk);
            act_desc.setText(list.get(i).get("act_desc"));
            return view;
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
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lh_service, null));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
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
            Glide.with(TicketGoodsDetialsAty.this).load(list.get(position).get("icon")).into(holder.im_logo);

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
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_dpg, null));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Glide.with(TicketGoodsDetialsAty.this).load(list.get(position).get("goods_img")).into(holder.imageview);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        L.e("返回商品详情" + requestCode + resultCode);
        if (requestCode == 1000) {
            L.e("返回商品详情");
            if (resultCode == 0x0001) {
                Bundle bundle = new Bundle();
                bundle.putString("mid", data.getStringExtra("mid"));
                bundle.putString("type", "1");
                bundle.putString("goods_id", data.getStringExtra("goods_id"));
                bundle.putString("group_buy_id", "");
                bundle.putString("num", data.getStringExtra("num"));
                bundle.putString("product_id", data.getStringExtra("product_id"));
                startActivity(BuildOrderAty.class, bundle);
                return;
            } else if (resultCode == 0x0002) {
                is_C = true;

                // 去除字符串末位的加号
                String proValueStr = data.getStringExtra("pro_value"); // 获取回传的字符串
                int addLastIndexOf = proValueStr.lastIndexOf("+"); // 获取字符串中最后一个加号位置
                if (addLastIndexOf == proValueStr.length() - 1) {
                    // 如果最后一个加号在字符串末位，则截取最后一个加号之前的数据赋值给proValueStr
                    proValueStr = proValueStr.substring(0, addLastIndexOf);
                }

                goods_select_attr_tv.setText(proValueStr + " x" + data.getIntExtra("num", 0));
                tv_wy_price.setText("¥" + data.getStringExtra("wy_price"));
                tv_yx_price.setText("¥" + data.getStringExtra("yx_price"));
                now_price_tv.setText(data.getStringExtra("shop_price"));
                tv_inventory.setText("库存\t" + data.getStringExtra("goods_num"));
                old_price_tv.setText("￥" + data.getStringExtra("market_price"));
                old_price_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

                ChangeTextViewStyle.getInstance().forTextColor(this, goods_profit_num_tv,
                        "积分" + data.getStringExtra("red_return_integral"), 2, Color.parseColor("#FD8214"));
//                ArrayList<Map<String, String>> dj_list = JSONUtils.parseKeyAndValueToMapList(data.getStringExtra("data"));
                dj_ticket = (List<DjTicketBean>) data.getSerializableExtra("data");
                if (null != dj_ticket) {
                    for (int i = 0; i < dj_ticket.size(); i++) {
                        if (i == 2) {
                            break;
                        }
                        switch (i) {
                            case 0: {
//                                layout_djq0.setVisibility(View.VISIBLE);
//                                tv_djq_desc0.setText(dj_ticket.get(i).get("discount_desc"));
                                break;
                            }
                            case 1: {
//                                layout_djq1.setVisibility(View.VISIBLE);
//                                tv_djq_desc1.setText(dj_ticket.get(i).get("discount_desc"));
                                break;
                            }
                            case 2: {
//                                layout_djq2.setVisibility(View.VISIBLE);
//                                tv_djq_desc2.setText(dj_ticket.get(i).get("discount_desc"));
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
                return;
            }

            if (0 == from) {
                ticketBuyPst.ticketBuyInfo(ticket_buy_id, page);
            } else {
                goodsPst.goodsInfo(ticket_buy_id, page);
            }
            is_C = false;

            layout_djq0.setVisibility((discount_desc0 != null && discount_desc0.isEmpty()) ? View.GONE : View.VISIBLE);
            tv_djq_desc0.setText(discount_desc0);
            layout_djq1.setVisibility((discount_desc1 != null && discount_desc1.isEmpty()) ? View.GONE : View.VISIBLE);
            tv_djq_desc1.setText(discount_desc1);
            layout_djq2.setVisibility((discount_desc2 != null && discount_desc2.isEmpty()) ? View.GONE : View.GONE);
            tv_djq_desc2.setText(discount_desc2);

        }
    }

    public void toAttrs(View v, int from, String type, String goods_id, String imageurl, String price,
                        String group_buy_id, String goods_attr, String goods_val, String is_attr) {
        Bundle bundle = new Bundle();
        bundle.putInt("from", from);
        bundle.putString("type", type);
        bundle.putString("goods_id", goods_id);
        bundle.putString("group_buy_id", group_buy_id);
        bundle.putString("imageurl", imageurl);
        bundle.putString("price", price);
        bundle.putString("goods_attr", goods_attr);
        bundle.putString("goods_val", goods_val);
        bundle.putString("is_attr", is_attr);
        startActivityForResult(GoodsAttributeAty.class, bundle, 1000);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        goodsPst.goodsInfo(ticket_buy_id, page);
    }
}
