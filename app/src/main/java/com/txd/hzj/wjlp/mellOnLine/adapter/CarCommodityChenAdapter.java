package com.txd.hzj.wjlp.mellOnLine.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.CarBean;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.CarDetailseAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.LimitGoodsAty;

import java.util.ArrayList;
import java.util.List;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * ===============Txunda===============
 * 作者：chen
 * 日期：2017/7/8 0007
 * 时间：上午 12:06
 * 描述：汽车购车型Adapter(9-1汽车购)
 * ===============Txunda===============
 */

public class CarCommodityChenAdapter extends RecyclerView.Adapter<CarCommodityChenAdapter.ViewHolder> {

    private Context context;


    public CarCommodityChenAdapter(Context context) {
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car_commodity_chen, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        /**
         * 跳转到商品详情页
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CarDetailseAty.class);
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return 4;

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View view;

        public ViewHolder(View itemView) {
            super(itemView);

            view = itemView;
        }
    }
}
