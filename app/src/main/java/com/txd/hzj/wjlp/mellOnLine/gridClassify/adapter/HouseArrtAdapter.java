package com.txd.hzj.wjlp.mellOnLine.gridClassify.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ants.theantsgo.util.JSONUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;

import java.util.List;
import java.util.Map;

/**
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/9/19 0019
 * 时间：14:04
 * 描述：
 *
 */

public class HouseArrtAdapter extends BaseAdapter {

    private List<Map<String, String>> list;

    private LayoutInflater inflater;

    public HouseArrtAdapter(Context context, List<Map<String, String>> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Map<String, String> getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Map<String, String> map = getItem(i);

        HouseVH hvh;
        if (view == null) {
            view = inflater.inflate(R.layout.item_house_arrt_layout, viewGroup, false);
            hvh = new HouseVH();
            ViewUtils.inject(hvh, view);
            view.setTag(hvh);
        } else {
            hvh = (HouseVH) view.getTag();
        }
        hvh.key_tv.setText(map.get("key"));
        if (map.get("key").equals("物业费")) {
            ChangeTextViewStyle.getInstance().forFeeStyle(hvh.value_tv, map.get("value"));
        } else {
            hvh.value_tv.setText(map.get("value"));
        }
        return view;
    }

    private class HouseVH {
        @ViewInject(R.id.key_tv)
        private TextView key_tv;
        @ViewInject(R.id.value_tv)
        private TextView value_tv;
    }

}
