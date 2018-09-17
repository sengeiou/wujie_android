package com.txd.hzj.wjlp.minetoaty.coupon.fgt;


import android.os.Bundle;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.http.user.UserPst;
import com.txd.hzj.wjlp.popAty.adapter.CouponAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/19 0019
 * 时间：上午 9:55
 * 描述：优惠券(卡券包)
 */
public class DiscountCouponFgt extends BaseFgt {

    @ViewInject(R.id.valid_ticket_lv)
    private ListViewForScrollView valid_ticket_lv;
    @ViewInject(R.id.un_valid_ticket_lv)
    private ListViewForScrollView un_valid_ticket_lv;

    private CouponAdapter couponAdapter;
    private CouponAdapter couponAdapter1;

    private UserPst userPst;

    private List<Map<String, String>> normal;
    private List<Map<String, String>> out;

    public DiscountCouponFgt() {
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_club_card;
    }

    @Override
    protected void initialized() {
        userPst = new UserPst(this);
        normal = new ArrayList<>();
        out = new ArrayList<>();
    }

    @Override
    protected void requestData() {
        userPst.myTicket();
    }

    @Override
    protected void immersionInit() {

    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("myTicket")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));

            if (ToolKit.isList(data, "normal")) {
                normal = JSONUtils.parseKeyAndValueToMapList(data.get("normal"));
                couponAdapter = new CouponAdapter(getActivity(), 0, 0, normal);
                valid_ticket_lv.setAdapter(couponAdapter);
            }

            if (ToolKit.isList(data, "out")) {
                out = JSONUtils.parseKeyAndValueToMapList(data.get("out"));
                couponAdapter1 = new CouponAdapter(getActivity(), 1, 0, out);
                un_valid_ticket_lv.setAdapter(couponAdapter1);
            }

        }
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        removeContent();
        removeDialog();
    }
}
