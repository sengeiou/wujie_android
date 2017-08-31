package com.txd.hzj.wjlp.mellOnLine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.ViewUtils;
import com.txd.hzj.wjlp.R;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/31 0031
 * 时间：10:11
 * 描述：
 * ===============Txunda===============
 */

public class TheTrickAdapter extends RecyclerView.Adapter<TheTrickAdapter.TrickViewHolder> {

    private Context context;

    public TheTrickAdapter(Context context) {
        this.context = context;
    }

    @Override
    public TrickViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ticket_list_layout, null);
        TrickViewHolder trickViewHolder = new TrickViewHolder(view);
        ViewUtils.inject(trickViewHolder, view);
        return trickViewHolder;
    }

    @Override
    public void onBindViewHolder(TrickViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class TrickViewHolder extends RecyclerView.ViewHolder {

        TrickViewHolder(View itemView) {
            super(itemView);
        }
    }

}
