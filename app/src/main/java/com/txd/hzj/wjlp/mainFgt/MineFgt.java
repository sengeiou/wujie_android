package com.txd.hzj.wjlp.mainFgt;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.L;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.DemoApplication;
import com.txd.hzj.wjlp.MainAty;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.Promoters;
import com.txd.hzj.wjlp.distribution.ApplyForShop;
import com.txd.hzj.wjlp.distribution.shopAty.ShopMain;
import com.txd.hzj.wjlp.http.index.IndexPst;
import com.txd.hzj.wjlp.http.user.UserPst;
import com.txd.hzj.wjlp.huanxin.ui.ChatActivity;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.GoodsEvaluateAty;
import com.txd.hzj.wjlp.minetoAty.AboutOursAty;
import com.txd.hzj.wjlp.minetoAty.FootprintAty;
import com.txd.hzj.wjlp.minetoAty.GiveCouponAty;
import com.txd.hzj.wjlp.minetoAty.GradeOfMemberAty;
import com.txd.hzj.wjlp.minetoAty.ShareToFriendsAty;
import com.txd.hzj.wjlp.minetoAty._GradeOfMemberAty;
import com.txd.hzj.wjlp.minetoAty.address.AddressListAty;
import com.txd.hzj.wjlp.minetoAty.balance.BalanceAty;
import com.txd.hzj.wjlp.minetoAty.books.BooksAty;
import com.txd.hzj.wjlp.minetoAty.collect.CollectHzjAty;
import com.txd.hzj.wjlp.minetoAty.coupon.CouponHzjAty;
import com.txd.hzj.wjlp.minetoAty.dialog.ApprenticeCodeAty;
import com.txd.hzj.wjlp.minetoAty.dialog.RegistrationCodeAty;
import com.txd.hzj.wjlp.minetoAty.feedback.FeedBackAty;
import com.txd.hzj.wjlp.minetoAty.help.HelpCenterAty;
import com.txd.hzj.wjlp.minetoAty.mell.MellGoodsListAty;
import com.txd.hzj.wjlp.minetoAty.mell.MellSettingAty;
import com.txd.hzj.wjlp.minetoAty.mell.StockRecordAty;
import com.txd.hzj.wjlp.minetoAty.myGrade.ShareGradeAty;
import com.txd.hzj.wjlp.minetoAty.order.OrderCenterAty;
import com.txd.hzj.wjlp.minetoAty.setting.SetAty;
import com.txd.hzj.wjlp.minetoAty.tricket.IntegralAty;
import com.txd.hzj.wjlp.minetoAty.tricket.MyCouponAty;
import com.txd.hzj.wjlp.new_wjyp.aty_mine1;
import com.txd.hzj.wjlp.new_wjyp.aty_mine2;
import com.txd.hzj.wjlp.view.ObservableScrollView;
import com.txd.hzj.wjlp.wjyp.LMSJAty;

import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/4 0004
 * 时间：上午 11:54
 * 描述：我的
 * ===============Txunda===============
 */
public class MineFgt extends BaseFgt implements ObservableScrollView.ScrollViewListener {

    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;
    private String lat;
    private String lng;
    private IndexPst indexPst;
    @ViewInject(R.id.super_mine_layout)
    private SuperSwipeRefreshLayout superSwipeRefreshLayout;

    /**
     * 标题栏
     */
    @ViewInject(R.id.mine_title_layout)
    public RelativeLayout mine_title_layout;
    private int allHeight = 0;
    /**
     * 我的背景
     */
    @ViewInject(R.id.rel_head_back)
    public RelativeLayout rel_head_back;

    /**
     * 用户头像，昵称
     */
    @ViewInject(R.id.user_head_name_layout)
    public LinearLayout user_head_name_layout;

    /**
     * 用户头像
     */
    @ViewInject(R.id.user_head_iv)
    private ShapedImageView user_head_iv;

    /**
     * 用户名称
     */
    @ViewInject(R.id.user_name_tv)
    private TextView user_name_tv;

    @ViewInject(R.id.user_nick_tv)
    private TextView user_nick_tv;

    @ViewInject(R.id.off_line_to_change_sc)
    private ObservableScrollView off_line_to_change_sc;


    /**
     * 用户视图
     */
    @ViewInject(R.id.member_lin_layout)
    private LinearLayout member_lin_layout;
    /**
     * 用户其他信息(头像下边)
     */
    @ViewInject(R.id.uset_other_info_layout)
    private LinearLayout uset_other_info_layout;
    /**
     * 商家
     */
    @ViewInject(R.id.mell_point_lin_layout)
    private LinearLayout mell_point_lin_layout;

    /**
     * 无界驿站
     */
    @ViewInject(R.id.mine_mell_point_tv)
    private TextView mine_mell_point_tv;

    /**
     * 普通用户信息
     */
    @ViewInject(R.id.member_type_layout)
    private LinearLayout member_type_layout;

    /**
     * 爱心
     * 积分
     */
    @ViewInject(R.id.center_tv)
    private TextView center_tv;

    /**
     * 用户头像
     */
    @ViewInject(R.id.img_head)
    private ShapedImageView img_headl;

    private Bundle bundle;

    /**
     * 0.商家
     * 1.用户
     */
    private int type = 1;

    private UserPst userPst;

    private int size = 0;

    private int tit_size = 0;

    private int icon_size = 0;

    /**
     * 普通用户
     */
    @ViewInject(R.id.mine_member_type_tv)
    private TextView mine_member_type_tv;

    @ViewInject(R.id.level_icon_iv)
    private ImageView level_icon_iv;

    /**
     * 金牌会员
     */
    @ViewInject(R.id.grade_of_member_tv)
    private TextView grade_of_member_tv;

    @ViewInject(R.id.rank_icon_iv)
    private ImageView rank_icon_iv;

    /**
     * 积分
     */
    @ViewInject(R.id.integral_tv2)
    private TextView integral_tv;

    /**
     * 余额
     */
    @ViewInject(R.id.balance_tv)
    private TextView balance_tv;
    /**
     * 购物券
     */
    @ViewInject(R.id.ticket_num_tv)
    private TextView ticket_num_tv;
    private String head_pic = "";

    @ViewInject(R.id.merchant_will_move_into_tv)
    private TextView merchant_will_move_into_tv;
    // 赠送蓝色代金券
    @ViewInject(R.id.give_coupon_tv)
    private TextView give_coupon_tv;

    /**
     * 赠送蓝色代金券ll
     */
    @ViewInject(R.id.give_coupon_tv_ll)
    private LinearLayout give_coupon_tv_ll;

    @ViewInject(R.id.message_num_tv)
    private TextView message_num_tv;
    private String invite_code = "";
    private String server_line = "";
    private String TYPE = "0";
    /**
     * 系统消息数量
     */
    private int new_msg = 0;

    /**
     * 金银铜钢铁
     */
    @ViewInject(R.id.im_jin)
    private ImageView im_jin;
    @ViewInject(R.id.im_yin)
    private ImageView im_yin;
    @ViewInject(R.id.im_tong)
    private ImageView im_tong;
    @ViewInject(R.id.im_gang)
    private ImageView im_gang;
    @ViewInject(R.id.im_tie)
    private ImageView im_tie;
    //拜师码
    @ViewInject(R.id.apprentice_code_tv)
    private View apprentice_code_tv;
    @ViewInject(R.id.apprentice_code_line)
    private View apprentice_code_line;
    private String is_member_trainer, is_merchant_trainer, code;
    private String service_easemob_account;
    private String service_head_pic;
    private String service_nickname;
    private String blue_voucher;
    private String imaUrl;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mine_title_layout.setBackgroundColor(Color.TRANSPARENT);
        allHeight = Settings.displayWidth * 2 / 3;
        //allHeight = allHeight + 20;//烦人的金银铜钢铁，加点高度
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Settings.displayWidth, allHeight);
        rel_head_back.setLayoutParams(layoutParams);

        img_headl.setVisibility(View.VISIBLE);

        off_line_to_change_sc.smoothScrollTo(0, 0);
        // 改变标题栏颜色
        off_line_to_change_sc.setScrollViewListener(MineFgt.this);
        if (0 == type) {
            mine_mell_point_tv.setVisibility(View.VISIBLE);
            mell_point_lin_layout.setVisibility(View.VISIBLE);
            member_type_layout.setVisibility(View.GONE);
            member_lin_layout.setVisibility(View.GONE);
            uset_other_info_layout.setVisibility(View.GONE);
            center_tv.setText("爱心");
        } else {
            mine_mell_point_tv.setVisibility(View.GONE);
            mell_point_lin_layout.setVisibility(View.GONE);
            member_type_layout.setVisibility(View.VISIBLE);
            member_lin_layout.setVisibility(View.VISIBLE);
            uset_other_info_layout.setVisibility(View.VISIBLE);
            center_tv.setText("积分");
        }


    }

    @Override
    @OnClick({R.id.tv_set, R.id.rel_mine_about, R.id.tv_help_center, R.id.tv_order_center, R.id.grade_of_member_layout,
            R.id.mine_member_type_layout, R.id.my_coupon_layout, R.id.integral_tv, R.id.registration_code_tv,
            R.id.my_balance_layout, R.id.coupon_tv, R.id.address_tv, R.id.feedBack_tv, R.id.shre_to_friends_tv, R.id.apply_for_shop,
            R.id.share_grade_tv, R.id.collect_tv, R.id.footprint_tv, R.id.evaluate_tv, R.id.call_service_tv,
            R.id.merchant_will_move_into_tv, R.id.books_tv, R.id.stock_record_tv, R.id.sales_record_tv, R.id.personalStores,
            R.id.mell_goods_list_tv, R.id.grade_for_app_tv, R.id.tv_dljm, R.id.tv_lmsj, R.id.give_coupon_tv_ll, R.id.apprentice_code_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_lmsj:
                startActivity(new Intent(getActivity(), LMSJAty.class));
                break;
            case R.id.tv_dljm:
                startActivity(aty_mine2.class, null);
                break;
            case R.id.tv_set:// 设置
                if (1 == type) {
                    startActivity(SetAty.class, null);
                } else {
                    startActivity(MellSettingAty.class, null);
                }
                break;
            case R.id.grade_of_member_layout:// 会员成长
                bundle = new Bundle();
                bundle.putInt("from", 0);
                startActivity(GradeOfMemberAty.class, null);
                break;
            case R.id.mine_member_type_layout:// 会员等级
//                bundle = new Bundle();
//                bundle.putInt("from", 1);
                startActivity(_GradeOfMemberAty.class, bundle);
                break;
            case R.id.rel_mine_about:// 关于
                startActivity(AboutOursAty.class, null);
                break;
            case R.id.tv_help_center:// 帮助中心
                startActivity(HelpCenterAty.class, null);
                break;
            case R.id.tv_order_center:// 订单中心
                startActivity(OrderCenterAty.class, null);
                break;
            case R.id.collect_tv:// 我的收藏
                startActivity(CollectHzjAty.class, null);
                break;
            case R.id.footprint_tv:// 我的足迹
                startActivity(FootprintAty.class, null);
                break;
            case R.id.merchant_will_move_into_tv:// 商家推荐
                startActivity(aty_mine1.class, null);
                break;
            case R.id.evaluate_tv:// 我的评价
                bundle = new Bundle();
                bundle.putInt("from", 1);
                startActivity(GoodsEvaluateAty.class, bundle);
                break;
            case R.id.my_coupon_layout:// 购物券
                startActivity(MyCouponAty.class, null);
                break;
            case R.id.integral_tv:// 积分
                String s = center_tv.getText().toString();
                if (s.equals("积分")) {
                    startActivity(IntegralAty.class, null);
                }
                break;
            case R.id.registration_code_tv:// 注册码
                bundle = new Bundle();
                bundle.putString("head_pic", head_pic);
                bundle.putString("invite_code", invite_code);
                startActivity(RegistrationCodeAty.class, bundle);
                break;
            case R.id.my_balance_layout:// 余额
                startActivity(BalanceAty.class, null);
                break;
            case R.id.coupon_tv:// 卡券包
                startActivity(CouponHzjAty.class, null);
                break;
            case R.id.address_tv:// 我的地址
                bundle = new Bundle();
                bundle.putInt("type", 1);
                startActivity(AddressListAty.class, bundle);
                break;
            case R.id.feedBack_tv:// 意见反馈
                startActivity(FeedBackAty.class, null);
                break;
            case R.id.shre_to_friends_tv:// 分享好友
                startActivity(ShareToFriendsAty.class, null);
                break;
            case R.id.share_grade_tv:// 分享成绩
                startActivity(ShareGradeAty.class, null);
                break;
            case R.id.books_tv:// 无界书院
                startActivity(BooksAty.class, null);
                break;
            case R.id.give_coupon_tv_ll:// 蓝色代金券赠送
                Intent intent1 = new Intent(getActivity(), GiveCouponAty.class);
                Bundle bundle = new Bundle();
                bundle.putString("TYPE", TYPE);
                bundle.putString("url", imaUrl);
                bundle.putString("blue_voucher", blue_voucher);
                intent1.putExtras(bundle);
                startActivity(intent1);
                break;
            case R.id.call_service_tv:// 客服

                if (TextUtils.isEmpty(service_easemob_account)) {
                    showToast("暂无客服账号！");
                    return;
                }
                toChat(service_easemob_account, service_head_pic, service_nickname);

                break;
            //申请开店
            case R.id.apply_for_shop:
                startActivity(ApplyForShop.class, null);
                break;
            //店铺管理
            case R.id.personalStores:
                startActivity(ShopMain.class, null);
                break;
            case R.id.stock_record_tv:// 进货记录
                this.bundle = new Bundle();
                this.bundle.putInt("from", 1);
                startActivity(StockRecordAty.class, this.bundle);
                break;
            case R.id.sales_record_tv:// 销货记录
                this.bundle = new Bundle();
                this.bundle.putInt("from", 2);
                startActivity(StockRecordAty.class, this.bundle);
                break;
            case R.id.mell_goods_list_tv:// 商品列表
                startActivity(MellGoodsListAty.class, null);
                break;
            case R.id.grade_for_app_tv:// 评分鼓励

                Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    L.e("MineFgt Exception:" + e.toString());
                    showErrorTip("跳转至应用市场失败，请到您自行到市场为我们评价哦ლ(⌒▽⌒ლ)");
                }
                break;
            case R.id.apprentice_code_tv: {
                bundle = new Bundle();
                bundle.putString("head_pic", head_pic);
                bundle.putString("code", code);
                bundle.putString("is_member_trainer", is_member_trainer);
                bundle.putString("is_merchant_trainer", is_merchant_trainer);
                startActivity(ApprenticeCodeAty.class, bundle);
            }
            break;
        }
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_mine;
    }

    @Override
    protected void initialized() {
        indexPst = new IndexPst(this);
        userPst = new UserPst(this);
        size = ToolKit.dip2px(getActivity(), 80);
        tit_size = ToolKit.dip2px(getActivity(), 40);
        icon_size = ToolKit.dip2px(getActivity(), 20);
    }

    @Override
    public void onResume() {
        super.onResume();
        userPst.userCenter();
    }


    @Override
    protected void requestData() {
        lat = DemoApplication.getInstance().getLocInfo().get("lat");
        lng = DemoApplication.getInstance().getLocInfo().get("lon");
        indexPst.index(lng, lat);
        superSwipeRefreshLayout.setHeaderViewBackgroundColor(Color.WHITE);
        superSwipeRefreshLayout.setHeaderView(createHeaderView());// add headerView
        superSwipeRefreshLayout.setTargetScrollWithLayout(true);

        superSwipeRefreshLayout.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {

            @Override
            public void onRefresh() {
                textView.setText("正在刷新");
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                indexPst.index(lng, lat);
                userPst.userCenter();
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
    }

    private View createHeaderView() {
        View headerView = LayoutInflater.from(superSwipeRefreshLayout.getContext()).inflate(R.layout.layout_head, null);
        progressBar = headerView.findViewById(R.id.pb_view);
        textView = headerView.findViewById(R.id.text_view);
        textView.setText("下拉刷新");
        imageView = headerView.findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.down_arrow);
        progressBar.setVisibility(View.GONE);
        return headerView;
    }

    @ViewInject(R.id.tv_dljm)
    private TextView tv_dljm;
    @ViewInject(R.id.tv_lmsj)
    private TextView tv_lmsj;

    private Promoters mData;

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        L.e("jsonStrALIANG" + jsonStr);
        if (requestUrl.contains("userCenter")) {

            Map<String, Object> map = GsonUtil.GsonToMaps(jsonStr);
            Map<String, String> data = (Map<String, String>) map.get("data");
            if (data.containsKey("complete_status")) { // 如果存在指定key
                if (data.get("complete_status").equals("1")) { // 如果可以转蓝色代金券
                    // 修改跳转过去的字段属性
                    blue_voucher = data.get("blue_voucher");
                    give_coupon_tv.setText("赠送蓝色代金券");
                    TYPE = "0";
                } else { // 否则不可以转的话
                    // 也设置其相应属性
                    TYPE = "1";
                    userPst.proMoters();
                }
            } else { // 如果没有该字段的情况下
                // 该if..else和上方的if..else逻辑相同，只是没有complete_status进行这一步的判断
                if (data.get("member_coding").equals("3")) { // 判断是否是优享会员
                    // 如果是优享会员则设置状态和传到下一界面的属性值
                    blue_voucher = data.get("blue_voucher");
                    give_coupon_tv.setText("赠送蓝色代金券");
                    TYPE = "0";
                } else { // 否则不是优享会员
                    // 设置其不可转的属性
                    TYPE = "1";
                    userPst.proMoters();
                }
            }
            /***********************拜师码*************************************/
            /************************************************************/
            // 昵称
            String nickname = data.get("nickname");
            // 头像
            head_pic = data.get("head_pic");
            // 邀请码
            invite_code = data.get("invite_code");

            //      "is_agent":"0",    //是否显示    代理加盟  0 不显示  1 显示
            String is_agent = data.get("is_agent");
            if (!TextUtils.isEmpty(is_agent) && "1".equals(is_agent)) {
                tv_dljm.setVisibility(View.VISIBLE);
            } else {
                tv_dljm.setVisibility(View.GONE);
            }
//            拜师码判断
            is_member_trainer = data.get("is_member_trainer");
            is_merchant_trainer = data.get("is_merchant_trainer");

            if (TextUtils.isEmpty(is_member_trainer) && TextUtils.isEmpty(is_merchant_trainer)) {
                apprentice_code_tv.setVisibility(View.GONE);
                apprentice_code_line.setVisibility(View.GONE);
            } else {
                code = data.get("code");
                apprentice_code_tv.setVisibility(View.VISIBLE);
                apprentice_code_line.setVisibility(View.VISIBLE);
            }

            service_easemob_account = data.get("service_easemob_account");
            service_head_pic = data.get("service_head_pic");
            service_nickname = data.get("service_nickname");
            user_name_tv.setText(nickname);
            user_nick_tv.setText(nickname);


            // 如果是无忧、优享会员则显示商家推荐
            if (data.get("user_card_type").equals("2") || data.get("user_card_type").equals("3")) {
                merchant_will_move_into_tv.setVisibility(View.VISIBLE);
            }

            /**
             *  "is_gold": "0"//金，0不点亮 1点亮,
             "is_silver": "0",//银，0不点亮 1点亮,
             "is_copper": "0",//铜，0不点亮 1点亮,
             "is_masonry": "0",//钢，0不点亮 1点亮,
             "is_iron": "0",//铁，0不点亮 1点亮,
             */

            L.e("wang", "data:" + data);

//            SOURCE：缓存原始数据，RESULT：缓存变换(如缩放、裁剪等)后的资源数据，
//            NONE：什么都不缓存，  ALL：缓存SOURC和RESULT。
//            默认采用RESULT策略，对于Download Only操作要使用SOURCE。
            Glide.with(getActivity()).load(data.get("is_gold_a"))
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(im_jin);

            Glide.with(getActivity()).load(data.get("is_silver_a"))
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(im_yin);

            Glide.with(getActivity()).load(data.get("is_copper_a"))
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(im_tong);

            Glide.with(getActivity()).load(data.get("is_masonry_a"))
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(im_gang);

            Glide.with(getActivity()).load(data.get("is_iron_a"))
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(im_tie);

            L.e("wang", "data.get(\"is_gold_a\") = " + data.get("is_gold_a")
                    + "\ndata.get(\"is_silver_a\") = " + data.get("is_silver_a")
                    + "\ndata.get(\"is_copper_a\") = " + data.get("is_copper_a")
                    + "\ndata.get(\"is_masonry_a\") = " + data.get("is_masonry_a")
                    + "\ndata.get(\"is_iron_a\") = " + data.get("is_iron_a"));

            Glide.with(getActivity()).load(head_pic)
                    .override(size, size)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default)
                    .centerCrop()
                    .into(img_headl);
            Glide.with(getActivity()).load(head_pic)
                    .override(tit_size, tit_size)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default)
                    .centerCrop()
                    .into(user_head_iv);

            integral_tv.setText(data.get("integral"));
            balance_tv.setText(data.get("balance"));
            ticket_num_tv.setText(data.get("ticket_num"));

            server_line = data.get("server_line");
            // 消息
            forMsg(data);


            mine_member_type_tv.setText(data.get("rank"));
            grade_of_member_tv.setText(data.get("level"));
            Glide.with(getActivity()).load(data.get("level_icon"))
                    .transform(new GlideRoundTransform(getActivity(), 20))
                    .error(R.drawable.ic_default)
                    .placeholder(R.drawable.ic_default)
//                    .fitCenter()

//                    .override(icon_size, icon_size)
                    .into(rank_icon_iv);
            Glide.with(getActivity()).load(data.get("rank_icon"))
                    .transform(new GlideRoundTransform(getActivity(), 20))
                    .error(R.drawable.ic_default)
                    .placeholder(R.drawable.ic_default)
//                    .fitCenter()

//                    .override(icon_size, icon_size)
                    .into(level_icon_iv);
        }
        if (requestUrl.contains("promoters")) {
            Promoters promoters = GsonUtil.GsonToBean(jsonStr, Promoters.class);
            Log.i("lanse", jsonStr);
            if (promoters.getCode().equals("1")) {
                imaUrl = promoters.getData().getAds().get(0).getPicture();
                give_coupon_tv.setText(promoters.getData().getAds().get(0).getDesc());
                Log.i("textviewtoStroing", imaUrl + "===================" + promoters.getData().getAds().get(0).getDesc());
            }
        }

        superSwipeRefreshLayout.setRefreshing(false);
        progressBar.setVisibility(View.GONE);

    }

    private void forMsg(Map<String, String> data) {
        // 消息数量
        try {
            new_msg = Integer.parseInt(data.get("new_msg"));

        } catch (NumberFormatException e) {
            new_msg = 0;
        }
        int hxNum = (new MainAty()).getUnreadMsgCountTotal();
        showOrHindMsg(hxNum);
    }

    public void showOrHindMsg(int hxNum) {
        int all = new_msg + hxNum;
        if (all <= 0) {
            message_num_tv.setVisibility(View.GONE);
        } else {
            message_num_tv.setVisibility(View.VISIBLE);
            message_num_tv.setText(String.valueOf(all));
        }
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        superSwipeRefreshLayout.setRefreshing(false);
        removeContent();
        removeDialog();
    }

    @Override
    protected void immersionInit() {
        showStatusBar(R.id.mine_title_layout);
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= 0) {
            mine_title_layout.setBackgroundColor(Color.TRANSPARENT);//AGB由相关工具获得，或者美工提供
            user_head_name_layout.setVisibility(View.GONE);
        } else if (y > 0 && y <= allHeight) {
            user_head_name_layout.setVisibility(View.VISIBLE);
            float scale = (float) y / allHeight;
            float alpha = (255 * scale);
            // 只是layout背景透明(仿知乎滑动效果)
            mine_title_layout.setBackgroundColor(Color.argb((int) alpha, 242, 48, 48));

            user_head_iv.setImageAlpha((int) alpha);
            user_name_tv.setTextColor(Color.argb((int) alpha, 255, 255, 255));

        } else {
            user_head_name_layout.setVisibility(View.VISIBLE);
            mine_title_layout.setBackgroundColor(Color.argb(255, 242, 48, 48));
            user_head_iv.setImageAlpha(255);
            user_name_tv.setTextColor(Color.WHITE);
        }
        immersionInit();
    }


    public void toChat(String easemob_account, String head_pic, String nickname) {
        if (!Config.isLogin()) {
            toLogin();
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("userId", easemob_account);// 对方环信账号
        bundle.putString("userHead", head_pic);// 对方头像
        bundle.putString("userName", nickname);// 对方昵称
        bundle.putString("myName", application.getUserInfo().get("nickname"));// 我的昵称
        bundle.putString("myHead", application.getUserInfo().get("head_pic"));// 我的头像
        startActivity(ChatActivity.class, bundle);
    }
}
