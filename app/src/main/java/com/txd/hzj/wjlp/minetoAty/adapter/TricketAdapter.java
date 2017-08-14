package com.txd.hzj.wjlp.minetoAty.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.minetoAty.tricket.MyCouponAty;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/19 0019
 * 时间：10:08
 * 描述：
 * ===============Txunda===============
 */

public class TricketAdapter extends BaseAdapter {
    private MCVH mcvh;
    private int type = 0;
    private Context context;

    public TricketAdapter(int type, Context context) {
        this.type = type;
        this.context = context;
    }

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
            view = LayoutInflater.from(context).inflate(R.layout.item_tricket_lv_hzj, null);
            mcvh = new MCVH();
            ViewUtils.inject(mcvh, view);
            view.setTag(mcvh);
        } else {
            mcvh = (MCVH) view.getTag();
        }
        if (0 == type) {
            mcvh.ticket_lin_layout.setBackgroundResource(R.drawable.icon_valid_ticket_bg_hzj);
        } else {
            // icon_un_valid_ticket_bg_hzj
            mcvh.ticket_lin_layout.setBackgroundResource(R.drawable.icon_no_uses_tick_bg_hzj);
        }
        return view;
    }

    class MCVH {
        @ViewInject(R.id.ticket_lin_layout)
        private LinearLayout ticket_lin_layout;
    }}
