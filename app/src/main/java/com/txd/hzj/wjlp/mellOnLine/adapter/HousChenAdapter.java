package com.txd.hzj.wjlp.mellOnLine.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.CarBean;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.hous.HousDetailsChenAty;

import java.util.ArrayList;
import java.util.List;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * ===============Txunda===============
 * 作者：cehne
 * 日期：2017/7/8 0007
 * 时间：上午 12:06
 * 描述：房产购Adapter(10-1汽车购)
 * ===============Txunda===============
 */

public class HousChenAdapter extends RecyclerView.Adapter<HousChenAdapter.ViewHolder> {

    private Context context;


    public HousChenAdapter(Context context){
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_house_chen,parent,false);
        ViewHolder holder=new ViewHolder(view);
        ViewUtils.inject(holder,view);
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
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
        @ViewInject(R.id.ll_item_hous)
        LinearLayout ll_item_hous;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
