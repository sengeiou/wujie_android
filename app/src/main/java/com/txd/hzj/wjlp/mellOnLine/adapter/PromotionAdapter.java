package com.txd.hzj.wjlp.mellOnLine.adapter;

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
 * 日期：2017/8/31 0031
 * 时间：11:22
 * 描述：
 * ===============Txunda===============
 */

public class PromotionAdapter extends BaseAdapter {
    private Context context;

    private PVH pvh;

    public PromotionAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_promotion_lv_layout, null);
            pvh = new PVH();
            ViewUtils.inject(pvh, view);
            view.setTag(pvh);
        } else {
            pvh = (PVH) view.getTag();
        }
        return view;
    }

    private class PVH {

    }
}
