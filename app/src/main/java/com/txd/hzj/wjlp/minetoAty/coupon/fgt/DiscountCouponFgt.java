package com.txd.hzj.wjlp.minetoAty.coupon.fgt;


import android.os.Bundle;

import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.minetoAty.adapter.TricketAdapter;
import com.txd.hzj.wjlp.minetoAty.tricket.MyCouponAty;

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
    private TricketAdapter tricketAdapter;
    private TricketAdapter tricketAdapter1;

    public DiscountCouponFgt() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        valid_ticket_lv.setAdapter(tricketAdapter);
        un_valid_ticket_lv.setAdapter(tricketAdapter1);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_club_card;
    }

    @Override
    protected void initialized() {
        tricketAdapter = new TricketAdapter(0, getActivity());
        tricketAdapter1 = new TricketAdapter(1, getActivity());
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void immersionInit() {

    }
}
