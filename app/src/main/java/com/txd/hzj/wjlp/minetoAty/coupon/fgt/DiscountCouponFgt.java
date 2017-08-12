package com.txd.hzj.wjlp.minetoAty.coupon.fgt;


import android.os.Bundle;

import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.minetoAty.adapter.TricketAdapter;
import com.txd.hzj.wjlp.minetoAty.tricket.MyCouponAty;
import com.txd.hzj.wjlp.popAty.adapter.CouponAdapter;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/19 0019
 * 时间：上午 9:55
 * 描述：优惠券
 * ===============Txunda===============
 */
public class DiscountCouponFgt extends BaseFgt {

    @ViewInject(R.id.valid_ticket_lv)
    private ListViewForScrollView valid_ticket_lv;
    @ViewInject(R.id.un_valid_ticket_lv)
    private ListViewForScrollView un_valid_ticket_lv;

    private CouponAdapter couponAdapter;
    private CouponAdapter couponAdapter1;

    public DiscountCouponFgt() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        valid_ticket_lv.setAdapter(couponAdapter);
        un_valid_ticket_lv.setAdapter(couponAdapter1);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_club_card;
    }

    @Override
    protected void initialized() {
        couponAdapter = new CouponAdapter(getActivity(), 0);
        couponAdapter.setFrom(0);
        couponAdapter1 = new CouponAdapter(getActivity(), 1);
        couponAdapter1.setFrom(0);
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void immersionInit() {

    }
}
