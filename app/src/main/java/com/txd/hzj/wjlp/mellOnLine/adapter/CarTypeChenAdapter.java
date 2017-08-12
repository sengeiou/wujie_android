package com.txd.hzj.wjlp.mellOnLine.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.view.roundImageView.RoundedImageView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.CarBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * ===============Txunda===============
 * 作者：cehne
 * 日期：2017/7/8 0007
 * 时间：上午 12:06
 * 描述：汽车购车型Adapter(9-1汽车购)
 * ===============Txunda===============
 */

public class CarTypeChenAdapter extends RecyclerView.Adapter<CarTypeChenAdapter.ViewHolder> {

    private Context context;
    private int type;//1 车型  2品牌
    private List<CarBean> listSelect;

    public CarTypeChenAdapter(Context context, int type, List<CarBean> listSelect) {
        this.context = context;
        this.type = type;
        this.listSelect = listSelect;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car_type_chen, parent, false);
        ViewHolder holder = new ViewHolder(view);
        ViewUtils.inject(holder, view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final CarBean carBean = listSelect.get(position);
        if (carBean.isSelecet()) {
            holder.iv_type_car.setImageResource(carBean.getSelectIcon());
            holder.tv_type_car.setTextColor(ContextCompat.getColor(context, R.color.theme_color));
        } else {
            holder.iv_type_car.setImageResource(carBean.getUnSelectIcon());
            holder.tv_type_car.setTextColor(ContextCompat.getColor(context, R.color.app_text_color));
        }
        holder.tv_type_car.setText(carBean.getCarname());
        /*
         * 单击
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (carBean.isSelecet()) {
                    //选中
                    carBean.setSelecet(false);
                    holder.iv_type_car.setImageResource(carBean.getUnSelectIcon());
                    holder.tv_type_car.setTextColor(ContextCompat.getColor(context, R.color.app_text_color));
                } else {
                    //未选中
                    carBean.setSelecet(true);
                    holder.iv_type_car.setImageResource(carBean.getSelectIcon());
                    holder.tv_type_car.setTextColor(ContextCompat.getColor(context, R.color.theme_color));
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @ViewInject(R.id.iv_type_car)
        ImageView iv_type_car;
        @ViewInject(R.id.tv_type_car)
        TextView tv_type_car;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
