package com.txd.hzj.wjlp.popAty.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lidroid.xutils.ViewUtils;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.popAty.fgt.RedPacagerFgt;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/24 0024
 * 时间：19:56
 * 描述：
 * ===============Txunda===============
 */

public class RedPackageAdapter extends BaseAdapter {
    private RedPackageAdapter.RPVH rpvh;

    private Context context;

    public RedPackageAdapter(Context context) {
        this.context = context;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_red_package_lv, null);
            rpvh = new RedPackageAdapter.RPVH();
            ViewUtils.inject(rpvh, view);
            view.setTag(rpvh);
        } else {
            rpvh = (RedPackageAdapter.RPVH) view.getTag();
        }
        return view;
    }

    class RPVH {

    }
}
