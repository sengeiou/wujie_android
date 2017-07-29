package com.txd.hzj.wjlp.popAty.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/25 0025
 * 时间：19:52
 * 描述：福利社领券
 * ===============Txunda===============
 */

public class CouponAdapter extends BaseAdapter {

    private Context context;
    private CouVH couVH;

    private int type;

    public CouponAdapter(Context context, int type) {
        this.context = context;
        this.type = type;
    }

    @Override
    public int getCount() {
        return 6;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_coupon_lv, null);
            couVH = new CouVH();
            ViewUtils.inject(couVH, view);
            view.setTag(couVH);
        } else {
            couVH = (CouVH) view.getTag();
        }

        if (0 == type) {
            couVH.coupon_bg_layout.setBackgroundResource(R.drawable.icon_get_coupon_bg);
            couVH.coupon_already_layout.setVisibility(View.VISIBLE);
        } else {
            couVH.coupon_bg_layout.setBackgroundResource(R.drawable.icon_un_valid_ticket_bg_hzj);
            couVH.coupon_already_layout.setVisibility(View.GONE);
        }

        return view;
    }

    class CouVH {

        @ViewInject(R.id.coupon_bg_layout)
        private LinearLayout coupon_bg_layout;

        @ViewInject(R.id.coupon_already_layout)
        private LinearLayout coupon_already_layout;

    }

}
