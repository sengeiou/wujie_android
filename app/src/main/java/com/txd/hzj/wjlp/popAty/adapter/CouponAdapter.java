package com.txd.hzj.wjlp.popAty.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lidroid.xutils.ViewUtils;
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

    public CouponAdapter(Context context) {
        this.context = context;
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
        return view;
    }
    class CouVH {
    }

}
