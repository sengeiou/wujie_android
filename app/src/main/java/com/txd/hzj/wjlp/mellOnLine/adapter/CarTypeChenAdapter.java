package com.txd.hzj.wjlp.mellOnLine.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.CarBean;

import java.util.List;

/**
 * 作者：cehne
 * 日期：2017/7/8 0007
 * 时间：上午 12:06
 * 描述：汽车购车型Adapter(9-1汽车购)
 */

public class CarTypeChenAdapter extends RecyclerView.Adapter<CarTypeChenAdapter.ViewHolder> {

    private Context context;
    private int type;//1 车型  2品牌
    private List<CarBean> listSelect;

    private int img_w = 0;
    private int img_h = 0;
    private int img_size = 0;

    public CarTypeChenAdapter(Context context, int type, List<CarBean> listSelect) {
        this.context = context;
        this.type = type;
        this.listSelect = listSelect;
        img_w = ToolKit.dip2px(context, 88);
        img_h = ToolKit.dip2px(context, 32);
        img_size = ToolKit.dip2px(context, 50);
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
        if (position == 0) {
            carBean.setSelecet(true);
        }
        if (1 == type) {
            holder.iv_brand_car.setVisibility(View.GONE);
            holder.iv_type_car.setVisibility(View.VISIBLE);
        } else {
            holder.iv_brand_car.setVisibility(View.VISIBLE);
            holder.iv_type_car.setVisibility(View.GONE);
        }
        if (carBean.isSelecet()) {
            if (1 == type) {
                Glide.with(context).load(carBean.getTrue_style_img())
                        .override(img_w, img_h)
//                        .error(R.drawable.ic_default)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                        .placeholder(R.drawable.ic_default)
                        .centerCrop()
                        .dontAnimate()
                        .into(holder.iv_type_car);
            } else {
                Glide.with(context).load(carBean.getTrue_brand_logo())
                        .override(img_size, img_size)
//                        .error(R.drawable.ic_default)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                        .placeholder(R.drawable.ic_default)
                        .fitCenter()
                        .into(holder.iv_brand_car);
            }
            holder.tv_type_car.setTextColor(ContextCompat.getColor(context, R.color.theme_color));
        } else {
            if (1 == type) {
                Glide.with(context).load(carBean.getStyle_img())
                        .override(img_w, img_h)
//                        .error(R.drawable.ic_default)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                        .placeholder(R.drawable.ic_default)
                        .centerCrop()
                        .dontTransform()
                        .dontAnimate()
                        .into(holder.iv_type_car);
            } else {
                Glide.with(context).load(carBean.getBrand_logo())
                        .override(img_size, img_size)
//                        .error(R.drawable.ic_default)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                        .placeholder(R.drawable.ic_default)
                        .fitCenter()
                        .into(holder.iv_brand_car);
            }
            holder.tv_type_car.setTextColor(ContextCompat.getColor(context, R.color.app_text_color));
        }
        if (1 == type)
            holder.tv_type_car.setText(carBean.getStyle_name());
        else
            holder.tv_type_car.setText(carBean.getBrand_name());
        /*
         * 单击
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (carBean.isSelecet()) {
                    //选中
                    carBean.setSelecet(false);
                } else {
                    //未选中
                    carBean.setSelecet(true);
                }
                // 如果第一个被选中，则设置其他的为未选中，
                // 如果其他的被选中，则设置第一个为未选中
                if (0 == position) {
                    for (int i = 0; i < listSelect.size(); i++) {
                        if (0 != i) {
                            listSelect.get(i).setSelecet(false);
                        }
                    }
                } else {
                    listSelect.get(0).setSelecet(false);
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listSelect.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * 类型图片
         */
        @ViewInject(R.id.iv_type_car)
        ImageView iv_type_car;
        /**
         * 类型，品牌名称
         */
        @ViewInject(R.id.tv_type_car)
        TextView tv_type_car;
        /**
         * 品牌
         */
        @ViewInject(R.id.iv_brand_car)
        private ImageView iv_brand_car;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
