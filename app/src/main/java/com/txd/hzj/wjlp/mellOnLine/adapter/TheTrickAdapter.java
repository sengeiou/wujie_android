package com.txd.hzj.wjlp.mellOnLine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.groupbuy.TicketListBean;

import java.util.List;

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
    private List<TicketListBean> list;

    public TheTrickAdapter(Context context) {
        this.context = context;
    }

    public TheTrickAdapter(Context context, List<TicketListBean> list) {
        this.context = context;
        this.list = list;
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

        TicketListBean ticketListBean = list.get(position);

        holder.value_tv.setText(ticketListBean.getValue());
        holder.condition_tv.setText(ticketListBean.getTicket_name());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TrickViewHolder extends RecyclerView.ViewHolder {

        /**
         * 面值
         */
        @ViewInject(R.id.value_tv)
        private TextView value_tv;
        /**
         * 满足条件
         */
        @ViewInject(R.id.condition_tv)
        private TextView condition_tv;

        TrickViewHolder(View itemView) {
            super(itemView);
        }
    }

}
