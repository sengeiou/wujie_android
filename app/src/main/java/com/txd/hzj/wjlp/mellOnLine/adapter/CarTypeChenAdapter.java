package com.txd.hzj.wjlp.mellOnLine.adapter;

import android.content.Context;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.view.roundImageView.RoundedImageView;
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

    public CarTypeChenAdapter(Context context, int type) {
        this.context = context;
        this.type = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car_type_chen, parent, false);
        ViewHolder holder = new ViewHolder(view);
        initSelect();//select 初始化
        return holder;
    }

    /**
     * select初始化
     */
    private void initSelect() {
        listSelect = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            CarBean carBean = new CarBean();
            carBean.setSelect(false);
            listSelect.add(carBean);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        /**
         * 图片
         */
        if (type == 1) {
            holder.iv_type_car.setImageResource(R.mipmap.icon_car_chen);
        } else {
            holder.iv_type_car.setImageResource(R.mipmap.icon_brand_chen);
        }
        /**
         * 单击
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listSelect.get(position).getSelect()) {
                    //选中
                    listSelect.get(position).setSelect(false);
                } else {
                    //未选中
                    listSelect.get(position).setSelect(true);
                }
                notifyDataSetChanged();
            }
        });

        if (listSelect.get(position).getSelect()) {
            //选中
            holder.iv_type_car.setStroke(context.getResources().getColor(R.color.theme_color),
                    ToolKit.dip2px(context, 2f));//边缘宽度颜色
            holder.tv_type_car.setTextColor(context.getResources().getColor(R.color.theme_color));
        } else {
            //没有选中
            holder.iv_type_car.setStroke(context.getResources().getColor(R.color.transparent),
                    ToolKit.dip2px(context, 2f));//边缘宽度颜色
            holder.tv_type_car.setTextColor(context.getResources().getColor(R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ShapedImageView iv_type_car;
        View view;
        TextView tv_type_car;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_type_car = itemView.findViewById(R.id.iv_type_car);
            tv_type_car = itemView.findViewById(R.id.tv_type_car);
            view = itemView;
        }
    }
}
