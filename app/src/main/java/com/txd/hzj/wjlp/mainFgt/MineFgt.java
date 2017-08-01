package com.txd.hzj.wjlp.mainFgt;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.GoodsEvaluateAty;
import com.txd.hzj.wjlp.minetoAty.AboutOursAty;
import com.txd.hzj.wjlp.minetoAty.FeedBackAty;
import com.txd.hzj.wjlp.minetoAty.FootprintAty;
import com.txd.hzj.wjlp.minetoAty.GradeOfMemberAty;
import com.txd.hzj.wjlp.minetoAty.HelpCenterAty;
import com.txd.hzj.wjlp.minetoAty.ShareToFriendsAty;
import com.txd.hzj.wjlp.minetoAty.address.AddressListAty;
import com.txd.hzj.wjlp.minetoAty.balance.BalanceAty;
import com.txd.hzj.wjlp.minetoAty.books.BooksAty;
import com.txd.hzj.wjlp.minetoAty.collect.CollectHzjAty;
import com.txd.hzj.wjlp.minetoAty.coupon.CouponHzjAty;
import com.txd.hzj.wjlp.minetoAty.dialog.RegistrationCodeAty;
import com.txd.hzj.wjlp.minetoAty.mell.MellGoodsListAty;
import com.txd.hzj.wjlp.minetoAty.mell.MellSettingAty;
import com.txd.hzj.wjlp.minetoAty.mell.StockRecordAty;
import com.txd.hzj.wjlp.minetoAty.mellInto.MerchantWillMoveIntoAty;
import com.txd.hzj.wjlp.minetoAty.myGrade.ShareGradeAty;
import com.txd.hzj.wjlp.minetoAty.tricket.IntegralAty;
import com.txd.hzj.wjlp.minetoAty.tricket.MyCouponAty;
import com.txd.hzj.wjlp.minetoAty.order.OrderCenterAty;
import com.txd.hzj.wjlp.minetoAty.SetAty;
import com.txd.hzj.wjlp.view.ObservableScrollView;
import com.txd.hzj.wjlp.view.flowlayout.WaveView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/4 0004
 * 时间：上午 11:54
 * 描述：我的
 * ===============Txunda===============
 */
public class MineFgt extends BaseFgt implements ObservableScrollView.ScrollViewListener {
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
    private ImageView user_head_iv;

    /**
     * 用户名称
     */
    @ViewInject(R.id.user_name_tv)
    private TextView user_name_tv;

    /**
     * 设置
     */
    @ViewInject(R.id.tv_set)
    TextView tv_set;
    @ViewInject(R.id.off_line_to_change_sc)
    private ObservableScrollView off_line_to_change_sc;
    /**
     * 波浪视图
     */
    @ViewInject(R.id.wave)
    private WaveView wave;

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

    private Bundle bundle;

    /**
     * 0.商家
     * 1.用户
     */
    private int type = 1;

    public MineFgt() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mine_title_layout.setBackgroundColor(Color.TRANSPARENT);
        allHeight = Settings.displayWidth * 2 / 3;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Settings.displayWidth, allHeight);
        rel_head_back.setLayoutParams(layoutParams);

        off_line_to_change_sc.smoothScrollTo(0, 0);
        // 改变标题栏颜色
        off_line_to_change_sc.setScrollViewListener(MineFgt.this);
        wave.startAnim();

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
    @OnClick({R.id.tv_set, R.id.rel_mine_about, R.id.tv_help_center, R.id.tv_order_center, R.id.grade_of_member_tv,
            R.id.mine_member_type_tv, R.id.my_coupon_layout, R.id.integral_tv, R.id.registration_code_tv,
            R.id.my_balance_layout, R.id.coupon_tv, R.id.address_tv, R.id.feedBack_tv, R.id.shre_to_friends_tv,
            R.id.share_grade_tv, R.id.collect_tv, R.id.footprint_tv, R.id.evaluate_tv,
            R.id.merchant_will_move_into_tv, R.id.books_tv, R.id.stock_record_tv, R.id.sales_record_tv,
            R.id.mell_goods_list_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_set:// 设置
                if (1 == type) {
                    startActivity(SetAty.class, null);
                } else {
                    startActivity(MellSettingAty.class, null);
                }
                break;
            case R.id.grade_of_member_tv:// 会员等级
                bundle = new Bundle();
                bundle.putInt("from", 0);
                startActivity(GradeOfMemberAty.class, bundle);
                break;
            case R.id.mine_member_type_tv:// 会员等级
                bundle = new Bundle();
                bundle.putInt("from", 1);
                startActivity(GradeOfMemberAty.class, bundle);
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
            case R.id.merchant_will_move_into_tv:// 商家入驻
                startActivity(MerchantWillMoveIntoAty.class, null);
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
                } else {

                }
                break;
            case R.id.registration_code_tv:// 注册码
                startActivity(RegistrationCodeAty.class, null);
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
            case R.id.stock_record_tv:// 进货记录
                bundle = new Bundle();
                bundle.putInt("from", 1);
                startActivity(StockRecordAty.class, bundle);
                break;
            case R.id.sales_record_tv:// 销货记录
                bundle = new Bundle();
                bundle.putInt("from", 2);
                startActivity(StockRecordAty.class, bundle);
                break;
            case R.id.mell_goods_list_tv:// 商品列表
                startActivity(MellGoodsListAty.class, null);
                break;
        }
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_mine;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

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

            user_head_iv.setAlpha(alpha);
            user_name_tv.setTextColor(Color.argb((int) alpha, 255, 255, 255));

        } else {
            user_head_name_layout.setVisibility(View.VISIBLE);
            mine_title_layout.setBackgroundColor(Color.argb(255, 242, 48, 48));
            user_head_iv.setAlpha(255);
            user_name_tv.setTextColor(Color.WHITE);
        }
        immersionInit();
    }
}
