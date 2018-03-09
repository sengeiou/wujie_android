package com.txd.hzj.wjlp.minetoAty.tricket;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.user.UserPst;
import com.txd.hzj.wjlp.minetoAty.adapter.TricketAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/17 0017
 * 时间：下午 2:21
 * 描述：购物券(15-3购物券)
 * ===============Txunda===============
 */
public class MyCouponAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.titlt_right_tv)
    public TextView titlt_right_tv;

    @ViewInject(R.id.valid_ticket_lv)
    private ListViewForScrollView valid_ticket_lv;
    @ViewInject(R.id.un_valid_ticket_lv)
    private ListViewForScrollView un_valid_ticket_lv;

    private int p = 1;
    /**
     * 分页数据
     */
    @ViewInject(R.id.pull_refresh_sc)
    private ScrollView pull_refresh_sc;

    private UserPst userPst;

    // 过期的
    private List<Map<String, String>> out;
    // 能正常使用
    private List<Map<String, String>> normal;

    public MyCouponAty() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("代金券");
        titlt_right_tv.setText("明细");
        titlt_right_tv.setVisibility(View.VISIBLE);
        titlt_right_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
    }

    @Override
    @OnClick({R.id.titlt_right_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titlt_right_tv:
                Bundle bundle = new Bundle();
                bundle.putInt("from", 1);
                startActivity(ParticularsUsedByTricketAty.class, bundle);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_my_coupon;
    }

    @Override
    protected void initialized() {
        userPst = new UserPst(this);
        out = new ArrayList<>();
        normal = new ArrayList<>();
    }

    @Override
    protected void requestData() {
        userPst.vouchersList(p);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("vouchersList")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            if (ToolKit.isList(data, "out")) {
                out = JSONUtils.parseKeyAndValueToMapList(data.get("out"));
                TricketAdapter tricketAdapter1 = new TricketAdapter(1, this, out);
                un_valid_ticket_lv.setAdapter(tricketAdapter1);
            }
            if (ToolKit.isList(data, "normal")) {
                normal = JSONUtils.parseKeyAndValueToMapList(data.get("normal"));
                TricketAdapter tricketAdapter = new TricketAdapter(0, this, normal);
                valid_ticket_lv.setAdapter(tricketAdapter);
            }
        }
    }
}
