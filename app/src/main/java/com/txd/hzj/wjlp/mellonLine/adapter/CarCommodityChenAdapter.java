package com.txd.hzj.wjlp.mellonLine.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.txd.hzj.wjlp.mellonLine.gridClassify.CarDetailseAty;

import java.util.List;
import java.util.Map;

/**
 * 作者：chen
 * 日期：2017/7/8 0007
 * 时间：上午 12:06
 * 描述：汽车购车型Adapter(9-1汽车购)
 */

public class CarCommodityChenAdapter extends RecyclerView.Adapter<CarCommodityChenAdapter.ViewHolder> {

    private Context context;
    private List<Map<String, String>> cats;

    private int size = 0;

    private int logo_size = 0;

    public CarCommodityChenAdapter(Context context, List<Map<String, String>> cats) {
        this.context = context;
        this.cats = cats;
        size = ToolKit.dip2px(context, 180);
        logo_size = ToolKit.dip2px(context, 32);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car_commodity_chen, parent, false);
        ViewHolder holder = new ViewHolder(view);
        ViewUtils.inject(holder, view);
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final Map<String, String> map = cats.get(position);
        // 汽车图片
        Glide.with(context).load(map.get("car_img"))
                .override(size, size)
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.car_pic_iv);
        // 汽车名称
        holder.car_name_tv.setText(map.get("car_name"));
        // 代金券
        holder.car_pre_money_tv.setText(map.get("pre_money"));
        holder.textView2.setText("可    抵：¥" + map.get("true_pre_money") + "车款\n车全价：¥" +
                map.get("all_price"));
        // 积分
        holder.car_integral_tv.setText(map.get("integral"));
        // 国旗
        Glide.with(context).load(map.get("brand_logo"))
                .override(logo_size, logo_size)
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.logo_for_cat_iv);
        if (map.get("ticket_discount").equals("0")) {
            holder.use_coupon_tv.setText("不可使用代金券");
            holder.use_coupon_tv.setBackgroundResource(R.drawable.shape_no_coupon_tv);
        } else {
            holder.use_coupon_tv.setText("可使用" + map.get("ticket_discount") + "%代金券");
            holder.use_coupon_tv.setBackgroundResource(R.drawable.shape_tv_bg_by_orange);
        }
        holder.car_distance_tv.setText(map.get("distance"));

        /**
         * 跳转到商品详情页
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String car_id = map.get("car_id");
                Intent intent = new Intent(context, CarDetailseAty.class);
                intent.putExtra("car_id", car_id);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return cats.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View view;

        /**
         * 汽车图片
         */
        @ViewInject(R.id.car_pic_iv)
        private ImageView car_pic_iv;
        /**
         * 距离
         */
        @ViewInject(R.id.car_distance_tv)
        private TextView car_distance_tv;
        /**
         * 汽车名称
         */
        @ViewInject(R.id.car_name_tv)
        private TextView car_name_tv;
        /**
         * 代金券
         */
        @ViewInject(R.id.car_pre_money_tv)
        private TextView car_pre_money_tv;
        /**
         * 其他信息
         */
        @ViewInject(R.id.textView2)
        private TextView textView2;
        /**
         * 积分
         */
        @ViewInject(R.id.car_integral_tv)
        private TextView car_integral_tv;
        /**
         * 汽车图标
         */
        @ViewInject(R.id.logo_for_cat_iv)
        private ImageView logo_for_cat_iv;
        /**
         * 是否使用优惠券
         */
        @ViewInject(R.id.use_coupon_tv)
        private TextView use_coupon_tv;

        public ViewHolder(View itemView) {
            super(itemView);

            view = itemView;
        }
    }
}
