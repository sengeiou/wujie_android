package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
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
import com.txd.hzj.wjlp.http.carbuy.CarBuyPst;
import com.txd.hzj.wjlp.http.collect.UserCollectPst;
import com.txd.hzj.wjlp.mellOnLine.adapter.GoodsCommentAttrAdapter;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.adapter.CommentPicAdapter;
import com.txd.hzj.wjlp.txunda_lh.BeanComment;
import com.txd.hzj.wjlp.txunda_lh.aty_submit_order;
import com.txd.hzj.wjlp.view.ObservableScrollView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

public class CarDetailseAty extends BaseAty implements ObservableScrollView.ScrollViewListener {

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
     * 原价
     */
    @ViewInject(R.id.old_price_tv)
    private TextView old_price_tv;

    /**
     * 商品其他信息
     */
    @ViewInject(R.id.goods_other_info_layout)
    private LinearLayout goods_other_info_layout;

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

    private int bannerSize = 0;

    @ViewInject(R.id.goods_for_my_evaluste_layout)
    private LinearLayout goods_for_my_evaluste_layout;
    /**
     * 汽车id
     */
    private String car_id = "";

    private CarBuyPst carBuyPst;

    @ViewInject(R.id.user_cart_num_tv)
    private TextView user_cart_num_tv;

    private String is_collect = "";

    @ViewInject(R.id.goods_title_collect_iv)
    private ImageView goods_title_collect_iv;
    @ViewInject(R.id.goods_title_collect_tv)
    private TextView goods_title_collect_tv;
    private UserCollectPst collectPst;

    @ViewInject(R.id.car_details_name_tv)
    private TextView car_details_name_tv;

    @ViewInject(R.id.car_d_pre_money_tv)
    private TextView car_d_pre_money_tv;

    @ViewInject(R.id.car_d_integral_tv)
    private TextView car_d_integral_tv;

    @ViewInject(R.id.car_d_other_info_tv)
    private TextView car_d_other_info_tv;

    /**
     * 文字描述
     */
    @ViewInject(R.id.goods_brief_tv)
    private WebView goods_brief_tv;
    @ViewInject(R.id.tv_desc)
    private TextView tv_desc;
    /**
     * 图文详情
     */
    @ViewInject(R.id.goods_desc_wv)
    private WebView goods_desc_wv;
    @ViewInject(R.id.layout_comment)
    private LinearLayout layout_comment;
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

    @ViewInject(R.id.comment_layout)
    private LinearLayout comment_layout;

    /**
     * 产品规格
     */
    @ViewInject(R.id.goods_common_attr_lv)
    private ListViewForScrollView goods_common_attr_lv;
    @ViewInject(R.id.goods_title_collect_layout)
    private LinearLayout goods_title_collect_layout;

    private String share_url;
    private String share_img;
    private String share_content;

    private String easemob_account = "";
    private String merchant_logo = "";
    private String merchant_name = "";
    private Map<String, String> car_info;
    private String car_num = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showStatusBar(R.id.goods_details_title);
        // 设置轮播图高度
        bannerSize = Settings.displayWidth;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(bannerSize, bannerSize);
        online_carvouse_view.setLayoutParams(layoutParams);

        goods_for_my_evaluste_layout.setVisibility(View.GONE);

        // 判断是否显示回到顶部按钮
        getHeight();

    }

    @Override
    @OnClick({R.id.title_goods_layout, R.id.title_details_layout, R.id.title_evaluate_layout,
            R.id.goods_title_collect_layout, R.id.goods_title_share_tv, R.id.creat_group_tv, R.id.all_evaluate_tv,
            R.id.be_back_top_iv, R.id.to_user_cart_layout, R.id.be_back_main_tv, R.id.to_chat_tv})
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
                    collectPst.addCollect("1", car_id);
                    break;
                }
                collectPst.delOneCollect("1", car_id);
                break;
            case R.id.goods_title_share_tv://分享
                toShare("无界优品", share_img, share_url, share_content, car_id, "1");
                break;
            case R.id.be_back_top_iv:// 回到顶部
                limit_goods_details_sc.smoothScrollTo(0, 0);
                setTextViewAndViewColor(0);
                break;
            case R.id.to_user_cart_layout:// d购物车
                backMain(2);
                break;
            case R.id.be_back_main_tv:// 首页
                backMain(0);
                break;
            case R.id.to_chat_tv:// 首页
                toChat(easemob_account, merchant_logo, merchant_name);
                break;
            case R.id.creat_group_tv: {
                if (!Config.isLogin()) {
                    toLogin();
                    break;
                }
                Bundle bundle = new Bundle();
                bundle.putString("id", car_info.get("car_id"));
                bundle.putString("image", car_info.get("car_img"));
                bundle.putString("title", car_info.get("car_name"));
                bundle.putString("price", car_info.get("pre_money"));
                bundle.putString("money", car_d_other_info_tv.getText().toString());
                bundle.putString("type", "1");
                startActivity(aty_submit_order.class, bundle);
            }
            break;
            case R.id.all_evaluate_tv: {
                Bundle bundle = new Bundle();
                bundle.putInt("from", 0);
                bundle.putString("id", car_id);
                bundle.putString("num", car_num);
                startActivity(GoodsEvaluateAty.class, bundle);
            }
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
            Glide.with(CarDetailseAty.this).load(image.get(position).get("path"))
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
    protected int getLayoutResId() {
        return R.layout.aty_car_detailse_hzj;
    }

    @Override
    protected void initialized() {
        goods_title_collect_layout.setVisibility(View.GONE);
        image = new ArrayList<>();
        car_id = getIntent().getStringExtra("car_id");
        carBuyPst = new CarBuyPst(this);
        head_size = ToolKit.dip2px(this, 60);
        collectPst = new UserCollectPst(this);
    }

    @Override
    protected void requestData() {
        carBuyPst.carInfo(car_id);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("carInfo")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));

            easemob_account = data.get("easemob_account");
            merchant_logo = data.get("server_head_pic");
            merchant_name = data.get("server_name");

            String cart_num = data.get("cart_num");
            if (!cart_num.equals("0")) {
                user_cart_num_tv.setText(cart_num);
                user_cart_num_tv.setVisibility(View.VISIBLE);
            } else {
                user_cart_num_tv.setVisibility(View.GONE);
            }
            // 是否收藏
            is_collect = data.get("is_collect");

//            if ("0".equals(is_collect)) {
//                goods_title_collect_iv.setImageResource(R.drawable.icon_collect);
//                goods_title_collect_tv.setText("收藏");
//            } else {
//                goods_title_collect_iv.setImageResource(R.drawable.icon_collected);
//                goods_title_collect_tv.setText("已收藏");
//            }

            // "share_url": "http://wjyp.txunda.com",//分享链接
            // "share_img": "分享图片",
            // "share_content": "分享内容"

            share_url = data.get("share_url");
            share_img = data.get("share_img");
            share_content = data.get("share_content");

            car_info = JSONUtils.parseKeyAndValueToMap(data.get("car_info"));

            // 轮播图
            if (ToolKit.isList(car_info, "banner")) {
                image = JSONUtils.parseKeyAndValueToMapList(car_info.get("banner"));
                forBanner();
            }
            car_details_name_tv.setText(car_info.get("car_name"));
            car_d_pre_money_tv.setText(car_info.get("pre_money"));
            car_d_integral_tv.setText(car_info.get("integral"));
            car_d_other_info_tv.setText("车全款：￥" + car_info.get
                    ("all_price") + "\n可    抵：￥" + car_info.get("true_pre_money") + "车款");

            tv_desc.setText(car_info.get("car_desc"));
            goods_brief_tv.setVisibility(View.GONE);
            goods_brief_tv.loadDataWithBaseURL(null, car_info.get("car_desc"), "text/html", "utf-8", null);
            goods_desc_wv.loadDataWithBaseURL(null, car_info.get("content"), "text/html", "utf-8", null);

            if (!data.get("comment_num").equals("0")) {
                car_num = data.get("comment_num");
                all_comment_num_tv.setText("商品评价(" + data.get("comment_num") + ")");
                List<BeanComment> comment = GsonUtil.getObjectList(data.get("comment_new"), BeanComment.class);
                Glide.with(this).load(comment.get(0).getHead_pic()).into(comm_user_head_iv);
                comm_user_name_tv.setText(comment.get(0).getNickname());
                comm_content_tv.setText(comment.get(0).getContent());
                List<CommentBean.BodyBean.PicturesBean> pic = comment.get(0).getPictures_arr();
                CommentPicAdapter picadapter = new CommentPicAdapter(this, pic);
                estimate_pic.setAdapter(picadapter);
            } else {
                layout_comment.setVisibility(View.GONE);
            }

//            // 评论
//            if (ToolKit.isList(data, "comment")) {
//                try {
//                    CommentBean comment = GsonUtil.GsonToBean(data.get("comment"), CommentBean.class);
//                    all_comment_num_tv.setText("商品评价(" + comment.getTotal() + ")");
//                    Map<String, String> commentMap = JSONUtils.parseKeyAndValueToMap(data.get("comment"));
//                    CommentBean.BodyBean bodyBean = comment.getBody();
//                    if (bodyBean != null) {
//                        Glide.with(this).load(bodyBean.getHead_pic())
//                                .override(head_size, head_size)
//                                .placeholder(R.drawable.ic_default)
//                                .error(R.drawable.ic_default)
//                                .centerCrop()
//                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                                .into(comm_user_head_iv);
//                        comm_user_name_tv.setText(bodyBean.getNickname());
//                        comm_content_tv.setText(bodyBean.getContent());
//                        List<CommentBean.BodyBean.PicturesBean> pictures = bodyBean.getPictures();
//                        if (!ListUtils.isEmpty(pictures)) {
//                            CommentPicAdapter picadapter = new CommentPicAdapter(this, pictures);
//                            estimate_pic.setAdapter(picadapter);
//                        }
//                    }
//                } catch (JsonSyntaxException e) {
//                    all_comment_num_tv.setText("商品评价(0)");
//                    comment_layout.setVisibility(View.GONE);
//                }
//            }
            // TODO==========产品属性==========
            if (ToolKit.isList(data, "attr")) {
                List<GoodsCommonAttr> gca = GsonUtil.getObjectList(data.get("attr"),
                        GoodsCommonAttr.class);
                GoodsCommentAttrAdapter gcaAdapter = new GoodsCommentAttrAdapter(this, gca);
                goods_common_attr_lv.setAdapter(gcaAdapter);
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
                limit_goods_details_sc.setScrollViewListener(CarDetailseAty.this);
            }
        });
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= 0) {
            be_back_top_iv.setVisibility(View.GONE);
            setTextViewAndViewColor(0);
        } else if (y >= bannerHeight) {
            be_back_top_iv.setVisibility(View.VISIBLE);
            if (y < topHeighe) {// 商品
                setTextViewAndViewColor(0);
            } else if (y >= topHeighe && y < secondHeight) {// 评价
                setTextViewAndViewColor(2);
            } else {// 详情
                setTextViewAndViewColor(1);
            }
        }
        if (oldy > y) {
            clickType = 0;
        }
    }
}
