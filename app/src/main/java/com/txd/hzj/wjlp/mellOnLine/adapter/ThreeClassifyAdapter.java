package com.txd.hzj.wjlp.mellOnLine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lidroid.xutils.ViewUtils;
import com.txd.hzj.wjlp.R;

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/6 0006
 * 时间：09:54
 * 描述：1-2分类，三级分类列表适配器
 * ===============Txunda===============
 */

public class ThreeClassifyAdapter extends BaseAdapter {

    private Context context;
    private List<String> data;
    private LayoutInflater inflater;
    private ThreeViewHolder tvh;

    public ThreeClassifyAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 9;
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(R.layout.item_right_three_level_gv, viewGroup, false);
            tvh = new ThreeViewHolder();
            ViewUtils.inject(tvh, view);
            view.setTag(tvh);
        } else {
            tvh = (ThreeViewHolder) view.getTag();
        }
        return view;
    }

    private class ThreeViewHolder {

    }
}
