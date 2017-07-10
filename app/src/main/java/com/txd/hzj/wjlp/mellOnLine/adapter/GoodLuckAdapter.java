package com.txd.hzj.wjlp.mellOnLine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ants.theantsgo.listenerForAdapter.AdapterTextViewClickListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;

import java.util.List;

import cn.iwgang.countdownview.CountdownView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/10 0010
 * 时间：11:22
 * 描述：拼团列表适配器
 * ===============Txunda===============
 */

public class GoodLuckAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;
    private LayoutInflater inflater;
    private GoodLuckVH goodLuckVH;

    private AdapterTextViewClickListener adapterTextViewClickListener;

    public void setAdapterTextViewClickListener(AdapterTextViewClickListener adapterTextViewClickListener) {
        this.adapterTextViewClickListener = adapterTextViewClickListener;
    }

    public GoodLuckAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 2;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {

        if (null == view) {
            view = inflater.inflate(R.layout.item_good_luck_lv, viewGroup, false);
            goodLuckVH = new GoodLuckVH();
            ViewUtils.inject(goodLuckVH, view);
            view.setTag(goodLuckVH);
        } else {
            goodLuckVH = (GoodLuckVH) view.getTag();
        }
        goodLuckVH.join_in_group_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapterTextViewClickListener != null) {
                    adapterTextViewClickListener.onTextViewClick(view, i);
                }
            }
        });
        goodLuckVH.good_count_down_view.setTag("GoodLuck");
        goodLuckVH.good_count_down_view.start(24 * 60 * 60 * 1000);

        return view;
    }

    private class GoodLuckVH {

        @ViewInject(R.id.join_in_group_tv)
        private TextView join_in_group_tv;
        /**
         * 倒计时
         */
        @ViewInject(R.id.good_count_down_view)
        private CountdownView good_count_down_view;

    }
}
