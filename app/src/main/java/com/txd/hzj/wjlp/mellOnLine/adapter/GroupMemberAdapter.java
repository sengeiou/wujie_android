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

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/10 0010
 * 时间：14:17
 * 描述：团员适配器
 * ===============Txunda===============
 */

public class GroupMemberAdapter extends RecyclerView.Adapter<GroupMemberAdapter.GMViewHolder> {

    private Context context;
    private List<String> list;
    private LayoutInflater inflater;

    public GroupMemberAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public GMViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_group_member_rv, parent, false);
        GMViewHolder gmViewHolder = new GMViewHolder(view);
        ViewUtils.inject(gmViewHolder, view);
        return gmViewHolder;
    }

    @Override
    public void onBindViewHolder(GMViewHolder holder, int position) {
        if (0 == position) {
            holder.regimental_commander_tv.setVisibility(View.VISIBLE);
        } else {
            holder.regimental_commander_tv.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    class GMViewHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.regimental_commander_tv)
        private TextView regimental_commander_tv;

        public GMViewHolder(View itemView) {
            super(itemView);
        }
    }

}
