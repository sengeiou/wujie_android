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
import com.txd.hzj.wjlp.mellonLine.gridClassify.hous.HousDetailsChenAty;

import java.util.List;
import java.util.Map;

/**
 * 作者：cehne
 * 日期：2017/7/8 0007
 * 时间：上午 12:06
 * 描述：房产购Adapter(10-1汽车购)
 */

public class HousChenAdapter extends RecyclerView.Adapter<HousChenAdapter.ViewHolder> {

    private Context context;

    private List<Map<String, String>> house;

    private int size = 0;

    public HousChenAdapter(Context context, List<Map<String, String>> house) {
        this.context = context;
        this.house = house;
        size = ToolKit.dip2px(context, 180);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_house_chen, parent, false);
        ViewHolder holder = new ViewHolder(view);
        ViewUtils.inject(holder, view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Map<String, String> map = house.get(position);
        // 距离
        holder.house_distance_tv.setText(map.get("distance"));
        // 图片
        Glide.with(context).load(map.get("house_img"))
                .error(R.drawable.ic_default)
                .placeholder(R.drawable.ic_default)
                .override(size, size)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.house_pic_iv);
        // 名称
        holder.house_name_tv.setText(map.get("house_name"));
        // 开发商
        holder.house_developer_tv.setText(map.get("developer"));
        // 房价
        holder.houst_price_tv.setText(map.get("min_price") + "-" + map.get("max_price"));
        // 在售房源
        holder.house_num_tv.setText(map.get("now_num"));

        /*
         * 点击事件
         *
         * 跳转到详情页
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDetail = new Intent(context, HousDetailsChenAty.class);
                intentDetail.putExtra("house_id",map.get("house_id"));
                context.startActivity(intentDetail);
            }
        });

    }

    @Override
    public int getItemCount() {
        return house.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * 距离
         */
        @ViewInject(R.id.house_distance_tv)
        private TextView house_distance_tv;
        /**
         * 房产图片
         */
        @ViewInject(R.id.house_pic_iv)
        private ImageView house_pic_iv;
        /**
         * 房产名称
         */
        @ViewInject(R.id.house_name_tv)
        private TextView house_name_tv;
        /**
         * 房产开发商
         */
        @ViewInject(R.id.house_developer_tv)
        private TextView house_developer_tv;
        /**
         * 房价
         */
        @ViewInject(R.id.houst_price_tv)
        private TextView houst_price_tv;
        /**
         * 在售房源
         */
        @ViewInject(R.id.house_num_tv)
        private TextView house_num_tv;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
