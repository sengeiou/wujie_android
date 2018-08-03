package com.txd.hzj.wjlp.minetoAty.coupon;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.utils.FragmentChangeManager;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.minetoAty.coupon.fgt.ClubCardFgt;
import com.txd.hzj.wjlp.minetoAty.coupon.fgt.DiscountCouponFgt;
import com.txd.hzj.wjlp.minetoAty.coupon.fgt.ShareOptionFgt;

import java.util.ArrayList;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/19 0019
 * 时间：上午 9:33
 * 描述：17-1我的卡券包
 */
public class CouponHzjAty extends BaseAty {


    @ViewInject(R.id.title_goods_tv)
    public TextView title_goods_tv;

    @ViewInject(R.id.title_goods_view)
    public View title_goods_view;

    @ViewInject(R.id.title_details_tv)
    public TextView title_details_tv;

    @ViewInject(R.id.title_details_view)
    public View title_details_view;

    @ViewInject(R.id.title_evaluate_tv)
    public TextView title_evaluate_tv;

    @ViewInject(R.id.title_evaluate_view)
    public View title_evaluate_view;

    private ArrayList<Fragment> mFragment;
    private FragmentChangeManager fcm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.card_title_layout);

        title_goods_tv.setText("会员卡");
        title_details_tv.setText("优惠券");
        title_evaluate_tv.setText("期权");
        fcm = new FragmentChangeManager(getSupportFragmentManager(), R.id.center_for_coupon_layout, mFragment);
        setTextViewAndViewColor(0);
    }

    @Override
    @OnClick({R.id.title_goods_layout, R.id.title_details_layout, R.id.title_evaluate_layout})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.title_goods_layout:// 会员卡
                setTextViewAndViewColor(0);
                break;
            case R.id.title_details_layout:// 优惠券
                setTextViewAndViewColor(1);
                break;
            case R.id.title_evaluate_layout://期权券
                setTextViewAndViewColor(2);
                break;
        }
    }

    /**
     * 改变标题栏的颜色
     *
     * @param next 下标
     */
    private void setTextViewAndViewColor(int next) {
        title_goods_tv.setTextColor(Color.BLACK);
        title_details_tv.setTextColor(Color.BLACK);
        title_evaluate_tv.setTextColor(Color.BLACK);

        title_goods_view.setBackgroundColor(Color.WHITE);
        title_details_view.setBackgroundColor(Color.WHITE);
        title_evaluate_view.setBackgroundColor(Color.WHITE);
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
        fcm.setFragments(next);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_coupon_hzj;
    }

    @Override
    protected void initialized() {
        mFragment = new ArrayList<>();
        mFragment.add(new ClubCardFgt());
        mFragment.add(new DiscountCouponFgt());
        mFragment.add(new ShareOptionFgt());
    }

    @Override
    protected void requestData() {

    }
}
