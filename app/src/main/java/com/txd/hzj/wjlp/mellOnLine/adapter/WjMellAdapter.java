package com.txd.hzj.wjlp.mellOnLine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/31 0031
 * 时间：16:07
 * 描述：
 * ===============Txunda===============
 */

public class WjMellAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;

    private WJMVH wjmvh;

    public WjMellAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 10;
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
            view = inflater.inflate(R.layout.item_ticket_gv, null);
            wjmvh = new WJMVH();
            ViewUtils.inject(wjmvh, view);
            view.setTag(wjmvh);
        } else {
            wjmvh = (WJMVH) view.getTag();
        }
        wjmvh.peice_tv.setText("兑换需要5000积分");
        wjmvh.peice_tv.setTextSize(12);
        wjmvh.older_price_tv.setVisibility(View.GONE);
        // 优惠券
        wjmvh.use_coupon_tv.setVisibility(View.GONE);
        wjmvh.jf_layout.setVisibility(View.GONE);
        return view;
    }

    private class WJMVH {
        /**
         * 积分
         */
        @ViewInject(R.id.peice_tv)
        private TextView peice_tv;

        /**
         * 原价
         */
        @ViewInject(R.id.older_price_tv)
        private TextView older_price_tv;

        /**
         * 积分
         */
        @ViewInject(R.id.jf_layout)
        private LinearLayout jf_layout;
        /**
         * 优惠券
         */
        @ViewInject(R.id.use_coupon_tv)
        private TextView use_coupon_tv;

    }
}
