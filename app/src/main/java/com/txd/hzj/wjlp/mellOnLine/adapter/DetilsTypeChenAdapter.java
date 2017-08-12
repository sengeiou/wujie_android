package com.txd.hzj.wjlp.mellOnLine.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.view.taobaoprogressbar.CustomProgressBar;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.hous.HouseTypeDetailsHzjAty;

/**
 * ===============Txunda===============
 * 作者：cehne
 * 日期：2017/7/8 0007
 * 时间：上午 12:06
 * 描述：房产购Adapter(10-1汽车购)
 * ===============Txunda===============
 */

public class DetilsTypeChenAdapter extends RecyclerView.Adapter<DetilsTypeChenAdapter.ViewHolder> {

    private Context context;


    public DetilsTypeChenAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_house_type_chen, parent, false);
        ViewHolder holder = new ViewHolder(view);
        ViewUtils.inject(holder, view);
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.house_type_cpb.setMaxProgress(100);
        holder.house_type_cpb.setCurProgress(50);
        /**
         * 点击事件
         *
         * 跳转到详情页
         */
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HouseTypeDetailsHzjAty.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 8;

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View view;

        @ViewInject(R.id.house_type_cpb)
        private CustomProgressBar house_type_cpb;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }
    }
}
