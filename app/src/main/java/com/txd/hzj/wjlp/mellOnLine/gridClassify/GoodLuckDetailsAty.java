package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.listenerForAdapter.AdapterTextViewClickListener;
import com.ants.theantsgo.tool.ToolKit;
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
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.GoodsCommonAttr;
import com.txd.hzj.wjlp.bean.groupbuy.CommentBean;
import com.txd.hzj.wjlp.bean.groupbuy.GoodsBannerBean;
import com.txd.hzj.wjlp.bean.groupbuy.GoodsInfoBean;
import com.txd.hzj.wjlp.bean.groupbuy.GroupBean;
import com.txd.hzj.wjlp.bean.groupbuy.GroupBuyInfo;
import com.txd.hzj.wjlp.bean.groupbuy.MellInfoBean;
import com.txd.hzj.wjlp.bean.groupbuy.PromotionBean;
import com.txd.hzj.wjlp.bean.groupbuy.TicketListBean;
import com.txd.hzj.wjlp.http.collect.UserCollectPst;
import com.txd.hzj.wjlp.http.groupbuy.GroupBuyPst;
import com.txd.hzj.wjlp.mellOnLine.adapter.GoodLuckAdapter;
import com.txd.hzj.wjlp.mellOnLine.adapter.GoodsCommentAttrAdapter;
import com.txd.hzj.wjlp.mellOnLine.adapter.PostAdapter;
import com.txd.hzj.wjlp.mellOnLine.adapter.PromotionAdapter;
import com.txd.hzj.wjlp.mellOnLine.adapter.TheTrickAdapter;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.adapter.CommentPicAdapter;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;
import com.txd.hzj.wjlp.view.ObservableScrollView;

import java.util.ArrayList;
import java.util.List;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/10 0010
 * 时间：上午 11:00
 * 描述：拼团详情
 * ===============Txunda===============
 */
public class GoodLuckDetailsAty extends BaseAty implements ObservableScrollView.ScrollViewListener {
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
    @ViewInject(R.id.goods_title_collect_iv)
    private ImageView goods_title_collect_iv;
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
    private int clickType = 0;

    /**
     * 优惠券
     */
    @ViewInject(R.id.goods_trick_rv)
    private RecyclerView goods_trick_rv;

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
    @ViewInject(R.id.one_price_tv)
    private TextView one_price_tv;
    /**
     * 团购价，一键开团
     */
    @ViewInject(R.id.creat_group_tv)
    private TextView creat_group_tv;
    /**
     * 购物车数量
     */
    @ViewInject(R.id.user_cart_num_tv)
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
    private String is_collect = "";
    private UserCollectPst collectPst;
    private String goods_id = "";

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
        getHeight();

        // 优惠券
        goods_trick_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        goods_trick_rv.setHasFixedSize(true);
    }

    @Override
    @OnClick({R.id.title_goods_layout, R.id.title_details_layout, R.id.title_evaluate_layout,
            R.id.goods_title_collect_layout, R.id.goods_title_share_tv, R.id.show_or_hide_iv,
            R.id.show_or_hide_lv_iv, R.id.show_or_hide_explain_iv, R.id.be_back_top_iv, R.id.to_cart_layout,
            R.id.creat_group_tv, R.id.go_to_main_layout})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.title_goods_layout://商品
                clickType = 1;
                limit_goods_details_sc.smoothScrollTo(0, 0);
                break;
            case R.id.title_details_layout://详情
                clickType = 2;
                limit_goods_details_sc.smoothScrollTo(0, secondHeight);
                break;
            case R.id.title_evaluate_layout://评价
                clickType = 3;
                limit_goods_details_sc.smoothScrollTo(0, topHeighe);
                break;
            case R.id.goods_title_collect_layout://收藏

                if(!Config.isLogin()){
                    toLogin();
                }
                if("0".equals(is_collect)){
                    collectPst.addCollect("1",goods_id);
                    break;
                }
                collectPst.delOneCollect("1",goods_id);

                break;
            case R.id.goods_title_share_tv://分享
                toShare();
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
            case R.id.be_back_top_iv:// 回到顶部
                limit_goods_details_sc.smoothScrollTo(0, 0);
                setTextViewAndViewColor(0);
                break;
            case R.id.go_to_main_layout:// 回到首页
                backMain(0);
                break;
            case R.id.to_cart_layout:// 购物车
                backMain(2);
                break;
            case R.id.creat_group_tv:// 一键开团(付款生成订单)
//                bundle = new Bundle();
//                bundle.putInt("status", 2);
//                startActivity(CreateGroupAty.class, bundle);
                break;
        }
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
        groupBuyPst.groupBuyInfo(group_buy_id);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("groupBuyInfo")) {
            GroupBuyInfo groupBuyInfo = GsonUtil.GsonToBean(jsonStr, GroupBuyInfo.class);
            image = groupBuyInfo.getData().getGoods_banner();
            // 团购商品轮播图
            if (!ListUtils.isEmpty(image)) {
                forBanner();
            }
            GoodsInfoBean goodsInfo = groupBuyInfo.getData().getGoodsInfo();
            goods_id = goodsInfo.getGoods_id();

            // 售价
            ChangeTextViewStyle.getInstance().forGoodsPrice(this, now_price_tv, "￥" + goodsInfo.getShop_price());
            // 市场价
            old_price_tv.setText("￥" + goodsInfo.getMarket_price());
            old_price_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

            // 积分
            ChangeTextViewStyle.getInstance().forTextColor(this, goods_profit_num_tv,
                    "积分" + goodsInfo.getIntegral(), 2, Color.parseColor("#FD8214"));

            // 名称
            goods_details_name_tv.setText(goodsInfo.getGoods_name());

            // 运费
            ChangeTextViewStyle.getInstance().forTextColor(this, freight_tv,
                    "运费10元", 2, Color.parseColor("#FD8214"));


            goods_brief_tv.loadDataWithBaseURL(null, goodsInfo.getGoods_brief(), "text/html", "utf-8", null);
            goods_desc_wv.loadDataWithBaseURL(null, goodsInfo.getGoods_desc(), "text/html", "utf-8", null);

            // 店铺信息
            MellInfoBean mellInfoBean = groupBuyInfo.getData().getMInfo();
            Glide.with(this).load(mellInfoBean.getLogo())
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

            ChangeTextViewStyle.getInstance().forGoodsLineFeed(this, all_prodect_tv,
                    mellInfoBean.getAll_goods() + "\n全部宝贝");
            ChangeTextViewStyle.getInstance().forGoodsLineFeed(this, all_collect_tv,
                    mellInfoBean.getView_num() + "\n人关注");
            ChangeTextViewStyle.getInstance().forTextColor(this, goods_describe_tv,
                    "宝贝描述" + mellInfoBean.getGoods_score(), 4, Color.parseColor("#FD8214"));
            ChangeTextViewStyle.getInstance().forTextColor(this, mell_serve_tv,
                    "卖家服务" + mellInfoBean.getMerchant_score(), 4, Color.parseColor("#FD8214"));
            ChangeTextViewStyle.getInstance().forTextColor(this, log_serve_tv,
                    "物流服务" + mellInfoBean.getShipping_score(), 4, Color.parseColor("#FD8214"));

            // 店铺活动
            promotion = groupBuyInfo.getData().getPromotion();
            if (!ListUtils.isEmpty(promotion)) {

                promotion_layout.setVisibility(View.VISIBLE);

                PromotionBean promotionBean = promotion.get(0);
                // =====单个活动设置
                String type = promotionBean.getType();
                int imageId = getResources().getIdentifier("icon_get_coupon_hzj_" + type, "drawable", getPackageName());
                onle_pro_type_iv.setImageResource(imageId);
                onle_pro_title_tv.setText(promotionBean.getTitle());

                promotion.remove(promotionBean);
                if (!ListUtils.isEmpty(promotion)) {
                    PromotionAdapter promotionAdapter = new PromotionAdapter(this,promotion);
                    promotion_lv.setAdapter(promotionAdapter);
                    goods_bottom_lin_layout.setVisibility(View.VISIBLE);
                    show_or_hide_iv.setEnabled(true);
                } else {
                    goods_bottom_lin_layout.setVisibility(View.GONE);
                    show_or_hide_iv.setEnabled(false);
                }
            } else {
                promotion_layout.setVisibility(View.GONE);
            }

            // 优惠券列表
            ticketList = groupBuyInfo.getData().getTicketList();
            if (!ListUtils.isEmpty(ticketList)) {
                TheTrickAdapter theTrickAdapter = new TheTrickAdapter(this, ticketList);
                goods_trick_rv.setAdapter(theTrickAdapter);
            }
            // 评论
            try {
                CommentBean comment = groupBuyInfo.getData().getComment();
                if (comment != null) {
                    all_comment_num_tv.setText("商品评价(" + comment.getTotal() + ")");
                    CommentBean.BodyBean bodyBean = comment.getBody();
                    if (bodyBean != null) {
                        Glide.with(this).load(bodyBean.getUser_head_pic())
                                .override(mSize, mSize)
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
                }
            } catch (JsonSyntaxException e){
                all_comment_num_tv.setText("商品评价(0)");
                comment_layout.setVisibility(View.GONE);
            }
            // 一键开团
            creat_group_tv.setText("￥" + groupBuyInfo.getData().getOne_price() + "\n一键开团");
            // 单独购买
            one_price_tv.setText("￥" + groupBuyInfo.getData().getGroup_price() + "\n单独购买");

            int num;
            try {
                num = Integer.parseInt(groupBuyInfo.getData().getCart_num());
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
            is_collect = groupBuyInfo.getData().getIs_collect();
            if ("0".equals(is_collect)) {
                goods_title_collect_iv.setImageResource(R.drawable.icon_collect);
                goods_title_collect_tv.setText("收藏");
            } else {
                goods_title_collect_iv.setImageResource(R.drawable.icon_collected);
                goods_title_collect_tv.setText("已收藏");
            }


            // 参团列表
            groupList = groupBuyInfo.getData().getGroup();
            if (!ListUtils.isEmpty(groupList)) {
                // 拼团列表
                GoodLuckAdapter goodLuckAdapter = new GoodLuckAdapter(this, groupList);
                good_luck_lv.setAdapter(goodLuckAdapter);
                // 去参团
                goodLuckAdapter.setAdapterTextViewClickListener(new AdapterTextViewClickListener() {
                    @Override
                    public void onTextViewClick(View v, int position) {
                        if(!Config.isLogin()){
                            toLogin();
                        }
                        bundle = new Bundle();
                        bundle.putInt("status", 0);
                        bundle.putString("log_id", groupList.get(position).getId());
                        startActivity(CreateGroupAty.class, bundle);
                    }
                });
            }
            // 产品属性
            List<GoodsCommonAttr> gca = groupBuyInfo.getData().getGoods_common_attr();
            if (!ListUtils.isEmpty(gca)) {
                GoodsCommentAttrAdapter gcaAdapter = new GoodsCommentAttrAdapter(this, gca);
                goods_common_attr_lv.setAdapter(gcaAdapter);
            }
            // ==========团购详情End===========
            return;
        }
        if(requestUrl.contains("addCollect")){
            showRightTip("收藏成功");
            is_collect = "1";
            goods_title_collect_iv.setImageResource(R.drawable.icon_collected);
            goods_title_collect_tv.setText("已收藏");
            return;
        }
        if(requestUrl.contains("delOneCollect")){
            showRightTip("取消成功");
            is_collect = "0";
            goods_title_collect_iv.setImageResource(R.drawable.icon_collect);
            goods_title_collect_tv.setText("收藏");
            return;
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
                limit_goods_details_sc.setScrollViewListener(GoodLuckDetailsAty.this);
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
}
