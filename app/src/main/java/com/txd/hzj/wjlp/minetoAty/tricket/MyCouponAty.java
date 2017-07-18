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
        titlt_right_tv.setText("使用明细");
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
        tricketAdapter = new TricketAdapter(0);
        tricketAdapter1 = new TricketAdapter(1);
    }

    @Override
    protected void requestData() {

    }

    private class TricketAdapter extends BaseAdapter {
        private MCVH mcvh;
        private int type = 0;

        public TricketAdapter(int type) {
            this.type = type;
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(MyCouponAty.this).inflate(R.layout.item_tricket_lv_hzj, null);
                mcvh = new MCVH();
                ViewUtils.inject(mcvh, view);
                view.setTag(mcvh);
            } else {
                mcvh = (MCVH) view.getTag();
            }
            if (0 == type) {
                mcvh.ticket_lin_layout.setBackgroundResource(R.drawable.icon_valid_ticket_bg_hzj);
            } else {
                mcvh.ticket_lin_layout.setBackgroundResource(R.drawable.icon_un_valid_ticket_bg_hzj);
            }
            return view;
        }

        class MCVH {
            @ViewInject(R.id.ticket_lin_layout)
            private LinearLayout ticket_lin_layout;
        }
    }

}
