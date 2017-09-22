package com.txd.hzj.wjlp.popAty.fgt;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ants.theantsgo.listenerForAdapter.AdapterTextViewClickListener;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshBase;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.http.welfare.WelfarePst;
import com.txd.hzj.wjlp.popAty.adapter.CouponAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private PullToRefreshListView coupon_lv;

    private CouponAdapter couponAdapter;

    private List<Map<String, String>> ticket_list;

    private WelfarePst welfarePst;
    private int p = 1;

    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;
    private String ticket_id = "";

    public static CouponListFgt newInstance(String type) {
        CouponListFgt fragment = new CouponListFgt();
        fragment.type = type;
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        coupon_lv.setEmptyView(no_data_layout);
        coupon_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                p = 1;
                welfarePst.ticketList(p, type, 0);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                p++;
                welfarePst.ticketList(p, type, 0);
            }
        });
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (getUserVisibleHint()) {
//            try {
//                welfarePst = new WelfarePst(this);
//                welfarePst.ticketList(p, type, 1);
//            } catch (NullPointerException e) {
//                L.e("Coupon======Error");
//            }
//        }
//    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("ticketList")) {// 优惠券列表
            if (ToolKit.isList(map, "data")) {
                Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
                if (ToolKit.isList(data, "ticket_list")) {
                    if (1 == p) {
                        ticket_list = JSONUtils.parseKeyAndValueToMapList(data.get("ticket_list"));
                        couponAdapter = new CouponAdapter(getActivity(), 0, 1, ticket_list);
                        coupon_lv.setAdapter(couponAdapter);
                        couponAdapter.setListener(new AdapterTextViewClickListener() {
                            @Override
                            public void onTextViewClick(View v, int position) {
                                switch (v.getId()) {
                                    case R.id.can_get_coupon_tv:// 领取优惠券
                                        if (ticket_list.get(position).get("is_get").equals("1")) {
                                            showRightTip("您已领取该优惠券");
                                            break;
                                        }
                                        ticket_id = ticket_list.get(position).get("ticket_id");
                                        welfarePst.getTicket(ticket_id);
                                        break;
                                }
                            }
                        });
                    } else {
                        ticket_list.addAll(JSONUtils.parseKeyAndValueToMapList(data.get("ticket_list")));
                        couponAdapter.notifyDataSetChanged();
                    }
                }
            }
            coupon_lv.onRefreshComplete();
            return;
        }
        if (requestUrl.contains("getTicket")) {// 领取优惠券
            showRightTip("领取成功");
            welfarePst.ticketList(p, type, 0);
        }
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        removeContent();
        removeDialog();

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_coupon_list_hzj;
    }

    @Override
    protected void initialized() {
        ticket_list = new ArrayList<>();
        welfarePst = new WelfarePst(this);
    }

    @Override
    protected void requestData() {
        welfarePst.ticketList(p, type, 1);
    }

    @Override
    protected void immersionInit() {

    }
}
