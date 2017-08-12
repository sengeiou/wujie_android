package com.txd.hzj.wjlp.minetoAty.tricket;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.minetoAty.adapter.TricketAdapter;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("购物券");
        titlt_right_tv.setText("明细");
        titlt_right_tv.setVisibility(View.VISIBLE);
        titlt_right_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        valid_ticket_lv.setAdapter(tricketAdapter);
        un_valid_ticket_lv.setAdapter(tricketAdapter1);
    }

    @Override
    @OnClick({R.id.titlt_right_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titlt_right_tv:
                Bundle bundle = new Bundle();
                bundle.putInt("from",1);
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
        tricketAdapter = new TricketAdapter(0,this);
        tricketAdapter1 = new TricketAdapter(1,this);
    }

    @Override
    protected void requestData() {

    }


}
