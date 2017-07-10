package com.txd.hzj.wjlp.mellOnLine.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ants.theantsgo.tool.ToolKit;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.hous.HousDetailsChenAty;

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


    public DetilsTypeChenAdapter(Context context){
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_house_type_chen,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        /**
         * 设置RecyclerView间隔
         */
        if(position%2!=0){
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) holder.ll_item_hous.getLayoutParams();
            lp.setMargins(0,ToolKit.dip2px(context,5f), 0,0);
            holder.ll_item_hous.setLayoutParams(lp);
        }

        /**
         * 点击事件
         *
         * 跳转到详情页
         */
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDetail=new Intent(context, HousDetailsChenAty.class);
                context.startActivity(intentDetail);
            }
        });

    }

    @Override
    public int getItemCount() {
            return 8;

    }

    class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        LinearLayout ll_item_hous;

        public ViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            ll_item_hous=itemView.findViewById(R.id.ll_item_hous);
        }
    }
}
