package com.txd.hzj.wjlp.mellonLine.gridClassify;

import android.content.DialogInterface;
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

import com.txd.hzj.wjlp.bean.commodity.*;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.tips.MikyouCommonDialog;
import com.ants.theantsgo.tool.ToolKit;
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
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.collect.UserCollectPst;
import com.txd.hzj.wjlp.http.country.CountryPst;
import com.txd.hzj.wjlp.mellonLine.adapter.GoodsCommentAttrAdapter;
import com.txd.hzj.wjlp.mellonLine.adapter.PostAdapter;
import com.txd.hzj.wjlp.mellonLine.adapter.PromotionAdapter;
import com.txd.hzj.wjlp.mellonLine.adapter.TheTrickAdapter;
import com.txd.hzj.wjlp.mellonLine.gridClassify.adapter.CommentPicAdapter;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;
import com.txd.hzj.wjlp.tool.TextUtils;
import com.txd.hzj.wjlp.view.ObservableScrollView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/11 0011
 * 时间：下午 1:02
 * 描述：进口馆商品详情(7-2)进口详情
 */
public class InputGoodsDetailsAty extends BaseAty implements ObservableScrollView.ScrollViewListener {
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
     * 进口税
     */
    @ViewInject(R.id.goods_tariff_tv)
    private TextView goods_tariff_tv;

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
     * 优惠券
     */
    @ViewInject(R.id.goods_trick_rv)
    private RecyclerView goods_trick_rv;
    private TheTrickAdapter theTrickAdapter;

    /**
     * 店铺活动列表
     */
    @ViewInject(R.id.promotion_lv)
    private ListView promotion_lv;

    private List<PromotionBean> promotionBeen;

    private PromotionAdapter promotionAdapter;

    private String mell_id = "";
    private String goods_id = "";
    private int bannerSize = 0;
    private CountryPst countryPst;
    /**
     * 是否收藏
     */
    private String is_collect = "0";
    /**
     * 购物车数量
     */
    @ViewInject(R.id.user_card_num_tv)
    private TextView user_card_num_tv;

    /*@ViewInject(R.id.goods_title_collect_iv)
    private ImageView goods_title_collect_iv;*/
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
    @ViewInject(R.id.goods_brief_tv)
    private WebView goods_desc_wv;


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

    /**
     * 产品规格
     */
    @ViewInject(R.id.goods_common_attr_lv)
    private ListViewForScrollView goods_common_attr_lv;
    private UserCollectPst collectPst;

    /**
     * 国家国旗
     */
    @ViewInject(R.id.goods_cou_logo_iv)
    private ImageView goods_cou_logo_iv;
    /**
     * 国家描述
     */
    @ViewInject(R.id.country_desc_tv)
    private TextView country_desc_tv;

    private int cou_size = 0;
    private int cou_size2 = 0;
    private String share_url = "";
    private String share_img = "";
    private String share_content = "";

    private String easemob_account = "";
    private String merchant_logo = "";
    private String merchant_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.goods_details_title);
        setTextViewAndViewColor(0);

        bannerSize = Settings.displayWidth;
        // 设置轮播图高度
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(bannerSize, bannerSize);
        online_carvouse_view.setLayoutParams(layoutParams);

        wujie_post_lv.setAdapter(postAdapter);
        // 判断是否显示回到顶部按钮
        getHeight();

        goods_trick_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        goods_trick_rv.setHasFixedSize(true);
        goods_trick_rv.setAdapter(theTrickAdapter);

        promotionAdapter = new PromotionAdapter(this, promotionBeen);
        promotion_lv.setAdapter(promotionAdapter);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_input_goods_details;
    }

    @Override
    protected void initialized() {
        goods_id = getIntent().getStringExtra("goods_id");
        image = new ArrayList<>();
        posts = new ArrayList<>();
        postAdapter = new PostAdapter(this, posts);
        promotionBeen = new ArrayList<>();
        countryPst = new CountryPst(this);

        collectPst = new UserCollectPst(this);

        // 商家等级
        imageViews = new ArrayList<>();

        imageViews.add(level_1_iv);
        imageViews.add(level_2_iv);
        imageViews.add(level_3_iv);
        imageViews.add(level_4_iv);
        imageViews.add(level_5_iv);

        head_size = ToolKit.dip2px(this, 60);
        logo_size = ToolKit.dip2px(this, 80);

        cou_size = ToolKit.dip2px(this, 36);
        cou_size2 = ToolKit.dip2px(this, 24);
    }

    @Override
    protected void requestData() {
        countryPst.goodsInfo(goods_id);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);

        if (requestUrl.contains("goodsInfo")) {// 票券区详情
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            String cart_num = data.get("cart_num");

            share_url = data.get("share_url");
            share_img = data.get("share_img");
            share_content = data.get("share_content");

            forBase(data, cart_num);
            // 轮播图
            if (ToolKit.isList(data, "goods_banner")) {
                image = JSONUtils.parseKeyAndValueToMapList(data.get("goods_banner"));
                forBanner();
            }
            // 商品基本信息
            Map<String, String> goodsInfo = JSONUtils.parseKeyAndValueToMap(data.get("goodsInfo"));
            forGoodsInfo(goodsInfo);

            // 商家信息
            Map<String, String> mInfo = JSONUtils.parseKeyAndValueToMap(data.get("mInfo"));
            forMellInfo(mInfo);

            // 促销活动
            if (ToolKit.isList(data, "promotion")) {
                forPromotion(data);
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
            }
            // 评论
            if (ToolKit.isList(data, "comment")) {
                try {
                    CommentBean comment = GsonUtil.GsonToBean(data.get("comment"), CommentBean.class);
                    all_comment_num_tv.setText("商品评价(" + comment.getTotal() + ")");
                    if (Integer.parseInt(comment.getTotal()) > 0) { // 如果商品有评价则显示评价模块
                        comment_layout.setVisibility(View.VISIBLE);
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
                            List<PicturesBean> pictures = bodyBean.getPictures();
                            if (!ListUtils.isEmpty(pictures)) {
                                CommentPicAdapter picadapter = new CommentPicAdapter(this, pictures);
                                estimate_pic.setAdapter(picadapter);
                            }
                        }
                    } else {
                        comment_layout.setVisibility(View.GONE); // 否则隐藏商品评价模块
                    }
                } catch (JsonSyntaxException e) {
                    all_comment_num_tv.setText("商品评价(0)");
                    comment_layout.setVisibility(View.GONE);
                }

            }
            // TODO==========产品属性==========
            if (ToolKit.isList(data, "goods_common_attr")) {
                List<GoodsCommonAttrBean> gca = GsonUtil.getObjectList(data.get("goods_common_attr"),
                        GoodsCommonAttrBean.class);
                GoodsCommentAttrAdapter gcaAdapter = new GoodsCommentAttrAdapter(this, gca);
                goods_common_attr_lv.setAdapter(gcaAdapter);
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

    /**
     * 商品信息
     *
     * @param goodsInfo 商品信息
     */
    private void forGoodsInfo(Map<String, String> goodsInfo) {
        for (Map.Entry entry : goodsInfo.entrySet()) {
            L.e(entry.getKey() + " : " + entry.getValue());
        }
        // 商品id
        goods_id = goodsInfo.get("goods_id");
        // 商品价格
        ChangeTextViewStyle.getInstance().forGoodsPrice(this, now_price_tv, "¥" + goodsInfo.get("shop_price"));
        // 商品原价
        old_price_tv.setText("¥" + goodsInfo.get("market_price"));
        old_price_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        // 积分
        ChangeTextViewStyle.getInstance().forTextColor(this, goods_profit_num_tv,
                "积分" + goodsInfo.get("integral"), 2, Color.parseColor("#FF0000"));
        // 运费(待定)
        ChangeTextViewStyle.getInstance().forTextColor(this, freight_tv,
                "运费10元", 2, Color.parseColor("#FF0000"));
        // 文字描述
        goods_brief_tv.loadDataWithBaseURL(null, goodsInfo.get("goods_brief"), "text/html", "utf-8", null);
        // 图文详情
        goods_desc_wv.loadDataWithBaseURL(null, goodsInfo.get("goods_desc"), "text/html", "utf-8", null);

        goods_details_name_tv.setText(goodsInfo.get("goods_name"));
        // 长按标题进行复制标题文字
        goods_details_name_tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                new MikyouCommonDialog(InputGoodsDetailsAty.this, "长按将复制文字：" + goods_details_name_tv.getText().toString(), "操作提示", "复制", "取消", true)
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

        Glide.with(this).load(goodsInfo.get("country_logo"))
                .override(cou_size, cou_size2)
                .placeholder(R.drawable.ic_default)
                .centerCrop()
                .dontAnimate()
                .error(R.drawable.ic_default)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(goods_cou_logo_iv);

        country_desc_tv.setText(goodsInfo.get("country_desc"));

    }

    /**
     * 商家信息
     *
     * @param mInfo 商家信息
     */
    private void forMellInfo(Map<String, String> mInfo) {
        mell_id = mInfo.get("merchant_id");

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
                "宝贝描述" + mInfo.get("goods_score"), 4, Color.parseColor("#FF0000"));
        ChangeTextViewStyle.getInstance().forTextColor(this, mell_serve_tv,
                "卖家服务" + mInfo.get("merchant_score"), 4, Color.parseColor("#FF0000"));
        ChangeTextViewStyle.getInstance().forTextColor(this, log_serve_tv,
                "物流服务" + mInfo.get("shipping_score"), 4, Color.parseColor("#FF0000"));
    }

    /**
     * 商品促销活动
     *
     * @param data 原始数据
     */
    private void forPromotion(Map<String, String> data) {
        List<PromotionBean> promotionBeen = GsonUtil.getObjectList(data.get("promotion"), PromotionBean.class);
        PromotionBean prom = promotionBeen.get(0);
        String type = prom.getType();
        int imageId = getResources().getIdentifier("icon_get_coupon_hzj_" + type, "drawable", getPackageName());
        onle_pro_type_iv.setImageResource(imageId);
        onle_pro_title_tv.setText(prom.getTitle());
        promotionBeen.remove(prom);
        if (!ListUtils.isEmpty(promotionBeen)) {// 判断移除掉一个活动之后是否为空
            goods_bottom_lin_layout.setVisibility(View.VISIBLE);
            promotionAdapter = new PromotionAdapter(this, promotionBeen);
            promotion_lv.setAdapter(promotionAdapter);
            show_or_hide_iv.setEnabled(true);
        } else {
            goods_bottom_lin_layout.setVisibility(View.GONE);
            // 此处可以设置。。不呢点击
            show_or_hide_iv.setEnabled(false);
        }
    }


    /**
     * 修改TextView的样式
     */
//    private void TextViewChange() {
//
//        ChangeTextViewStyle.getInstance().forGoodsPrice(this, now_price_tv, "¥14.8");
//        old_price_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//        ChangeTextViewStyle.getInstance().forTextColor(this, goods_profit_num_tv,
//                "积分10.23", 3, Color.parseColor("#FF0000"));
//
//        String tariff = "进口税 50元/件";
//        ChangeTextViewStyle.getInstance().forTextColor(this, goods_tariff_tv, tariff, 4, tariff.length() - 3,
//                ContextCompat.getColor(this, R.color.theme_color));
//        ChangeTextViewStyle.getInstance().forTextColor(this, freight_tv,
//                "运费10元", 2, Color.parseColor("#FF0000"));
//
//        ChangeTextViewStyle.getInstance().forGoodsLineFeed(this, all_prodect_tv, "339\n全部宝贝");
//        ChangeTextViewStyle.getInstance().forGoodsLineFeed(this, all_collect_tv, "359.9万\n人关注");
//
//
//        ChangeTextViewStyle.getInstance().forTextColor(this, goods_describe_tv,
//                "宝贝描述4.7", 2, Color.parseColor("#FF0000"));
//        ChangeTextViewStyle.getInstance().forTextColor(this, mell_serve_tv,
//                "卖家服务4.8", 2, Color.parseColor("#FF0000"));
//        ChangeTextViewStyle.getInstance().forTextColor(this, log_serve_tv,
//                "物流服务4.8", 2, Color.parseColor("#FF0000"));
//    }
    @Override
    @OnClick({R.id.title_goods_layout, R.id.title_details_layout, R.id.title_evaluate_layout,
            R.id.goods_title_collect_layout, R.id.goods_title_share_tv, R.id.show_or_hide_iv,
            R.id.show_or_hide_lv_iv, R.id.show_or_hide_explain_iv, R.id.be_back_top_iv,
            R.id.details_into_mell_tv, R.id.to_user_cart_layout, R.id.to_main_layout, R.id.to_chat_tv})
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
            case R.id.be_back_top_iv://回到顶部
                limit_goods_details_sc.smoothScrollTo(0, 0);
                setTextViewAndViewColor(0);
                break;
            case R.id.details_into_mell_tv:// 进店逛逛
                Bundle bundle = new Bundle();
                bundle.putString("mell_id", mell_id);
                startActivity(MellInfoAty.class, bundle);
                break;
            case R.id.to_user_cart_layout:// 购物车
                backMain(2);
                break;
            case R.id.to_main_layout:// 首页
                backMain(0);
                break;
            case R.id.to_chat_tv:// 首页
                toChat(easemob_account, merchant_logo, merchant_name);
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

            Glide.with(InputGoodsDetailsAty.this).load(image.get(position).get("path"))
                    .error(R.drawable.ic_default)
                    .placeholder(R.drawable.ic_default)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .centerCrop()
                    .override(bannerSize, bannerSize)
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
                bannerHeight = online_carvouse_view.getHeight();
                topHeighe = top_lin_layout.getHeight();
                secondHeight = second_lin_layout.getHeight();
                limit_goods_details_sc.setScrollViewListener(InputGoodsDetailsAty.this);
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
