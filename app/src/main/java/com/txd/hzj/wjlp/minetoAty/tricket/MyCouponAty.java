package com.txd.hzj.wjlp.minetoAty.tricket;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshBase;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshScrollView;
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

    private TricketAdapter tricketAdapter;
    private TricketAdapter tricketAdapter1;

    private int p = 1;
    /**
     * 分页数据
     */
    @ViewInject(R.id.pull_refresh_sc)
    private PullToRefreshScrollView pull_refresh_sc;

    private UserPst userPst;

    // 过期的
    private List<Map<String, String>> out;
    private List<Map<String, String>> out2;
    // 能正常使用
    private List<Map<String, String>> normal;
    private List<Map<String, String>> normal2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("购物券");
        titlt_right_tv.setText("明细");
        titlt_right_tv.setVisibility(View.VISIBLE);
        titlt_right_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));

        pull_refresh_sc.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                p = 1;
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                p++;
            }
        });
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
        out2 = new ArrayList<>();
        normal = new ArrayList<>();
        normal2 = new ArrayList<>();

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
            if (1 == p) {
                if (ToolKit.isList(data, "out")) {
                    out = JSONUtils.parseKeyAndValueToMapList(data.get("out"));
                    tricketAdapter1 = new TricketAdapter(1, this, out);
                    un_valid_ticket_lv.setAdapter(tricketAdapter1);
                }
                if (ToolKit.isList(data, "normal")) {
                    normal = JSONUtils.parseKeyAndValueToMapList(data.get("normal"));
                    tricketAdapter = new TricketAdapter(0, this, normal);
                    valid_ticket_lv.setAdapter(tricketAdapter);
                }
            } else {
                if (ToolKit.isList(data, "out")) {
                    out2 = JSONUtils.parseKeyAndValueToMapList(data.get("out"));
                    out.addAll(out2);
                    tricketAdapter1.notifyDataSetChanged();
                }
                if (ToolKit.isList(data, "normal")) {
                    normal2 = JSONUtils.parseKeyAndValueToMapList(data.get("normal"));
                    normal.addAll(normal2);
                    tricketAdapter.notifyDataSetChanged();
                }
            }
            pull_refresh_sc.onRefreshComplete();
        }
    }
}
