package com.txd.hzj.wjlp.mellOnLine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;

import java.util.List;

import cn.iwgang.countdownview.CountdownView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/7 0007
 * 时间：15:15
 * 描述：限量购适配器
 * ===============Txunda===============
 */

public class LimitAdapter extends BaseAdapter {
    private List<String> list;
    private Context context;
    private LayoutInflater inflater;
    private LimitViewHolder lvh;

    private int type;

    public LimitAdapter(List<String> list, Context context, int type) {
        this.list = list;
        this.context = context;
        this.type = type;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 8;
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.item_purchase_gv, null);
            lvh = new LimitViewHolder();
            ViewUtils.inject(lvh, view);
            view.setTag(lvh);
        } else {
            lvh = (LimitViewHolder) view.getTag();
        }
        lvh.home_count_down_view.setTag("text");
        lvh.home_count_down_view.start(3600000);
        if(1==type){
            lvh.limit_remeber_me_tv.setVisibility(View.VISIBLE);
        } else {
            lvh.limit_remeber_me_tv.setVisibility(View.GONE);
        }
        return view;
    }

    class LimitViewHolder {

        @ViewInject(R.id.home_count_down_view)
        private CountdownView home_count_down_view;

        @ViewInject(R.id.limit_remeber_me_tv)
        private TextView limit_remeber_me_tv;
    }
}