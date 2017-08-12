package com.txd.hzj.wjlp.popAty.fgt;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.popAty.adapter.CouponAdapter;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/10 0010
 * 时间：上午 11:41
 * 描述：优惠券列表
 * ===============Txunda===============
 */
public class CouponListFgt extends BaseFgt {

    private String type;

    @ViewInject(R.id.coupon_lv)
    private ListView coupon_lv;

    private CouponAdapter couponAdapter;

    public static CouponListFgt newInstance(String type) {
        CouponListFgt fragment = new CouponListFgt();
        fragment.type = type;
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        coupon_lv.setAdapter(couponAdapter);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_coupon_list_hzj;
    }

    @Override
    protected void initialized() {
        couponAdapter = new CouponAdapter(getActivity(), 0);
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void immersionInit() {

    }
}
