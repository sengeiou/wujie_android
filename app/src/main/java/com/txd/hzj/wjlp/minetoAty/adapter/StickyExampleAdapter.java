package com.txd.hzj.wjlp.minetoAty.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.TricketDetailks;
import com.txd.hzj.wjlp.minetoAty.balance.RechargeOffLineAty;

import java.util.List;

public class StickyExampleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int FIRST_STICKY_VIEW = 1;
    public static final int HAS_STICKY_VIEW = 2;
    public static final int NONE_STICKY_VIEW = 3;

    private Context context;
    private List<TricketDetailks> stickyExampleModels;

    public StickyExampleAdapter(Context context, List<TricketDetailks> recyclerViewModels) {
        this.context = context;
        this.stickyExampleModels = recyclerViewModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tricket_rv, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        ViewUtils.inject(recyclerViewHolder, view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof RecyclerViewHolder) {
            // 获取ViewHolder
            RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) viewHolder;
            // 获取明细实体类
            TricketDetailks stickyExampleModel = stickyExampleModels.get(position);
            // 费吸顶文本标题
            recyclerViewHolder.tvName.setText(stickyExampleModel.getName());
            // 交易记录时间
            recyclerViewHolder.tvGender.setText(stickyExampleModel.gender);
            if (position == 0) {// 第一条
                // 显示吸顶文本
                recyclerViewHolder.tvStickyHeader.setVisibility(View.VISIBLE);
                // 设置吸顶文本内容
                recyclerViewHolder.tvStickyHeader.setText(stickyExampleModel.sticky);
                // 给itemView设置Tag
                recyclerViewHolder.itemView.setTag(FIRST_STICKY_VIEW);
            } else {
                if (!TextUtils.equals(stickyExampleModel.sticky, stickyExampleModels.get(position - 1).sticky)) {
                    recyclerViewHolder.tvStickyHeader.setVisibility(View.VISIBLE);
                    recyclerViewHolder.tvStickyHeader.setText(stickyExampleModel.sticky);
                    recyclerViewHolder.itemView.setTag(HAS_STICKY_VIEW);
                } else {
                    recyclerViewHolder.tvStickyHeader.setVisibility(View.GONE);
                    recyclerViewHolder.itemView.setTag(NONE_STICKY_VIEW);
                }
            }
            // 描述
            recyclerViewHolder.itemView.setContentDescription(stickyExampleModel.sticky);
            recyclerViewHolder.check_details_for_balance_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, RechargeOffLineAty.class));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return stickyExampleModels == null ? 0 : stickyExampleModels.size();
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @ViewInject(R.id.tv_sticky_header_view)
        TextView tvStickyHeader;
        @ViewInject(R.id.t_details_title_tv)
        TextView tvName;
        @ViewInject(R.id.t_details_time_tv)
        TextView tvGender;
        @ViewInject(R.id.check_details_for_balance_tv)
        public TextView check_details_for_balance_tv;

        RecyclerViewHolder(View itemView) {
            super(itemView);
        }
    }
}
