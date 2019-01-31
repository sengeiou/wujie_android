package com.txd.hzj.wjlp.catchDoll.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.tool.glide.GlideUtils;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.catchDoll.bean.MyDollBean;
import com.txd.hzj.wjlp.catchDoll.ui.activity.DollGoodsInfoActivity;
import com.txd.hzj.wjlp.catchDoll.util.Util;

import java.util.List;

import cn.iwgang.countdownview.CountdownView;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：我的娃娃界面列表适配器
 */
public class MyDollItemAdapter extends RecyclerView.Adapter<MyDollItemAdapter.ViewHolder> {

    private List<MyDollBean> list;
    private Context context;

    public MyDollItemAdapter(List<MyDollBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_doll_show, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyDollBean myDollBean = list.get(position);
        GlideUtils.loadUrlImg(context, myDollBean.getImageUrl(), holder.itemMyDoll_image_imgv);
        holder.itemMyDoll_maturity_cdv.start(myDollBean.getMaturityTime());
        holder.itemMyDoll_name_tv.setText(myDollBean.getName());
        holder.itemMyDoll_time_tv.setText(Util.millis2String(myDollBean.getTime(), "yyyy.MM.dd HH:mm:ss"));
        holder.itemMyDoll_convertible_tv.setText(new StringBuffer().append("可兑换银两").append(myDollBean.getConvertible()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) context).startActivity(new Intent(context, DollGoodsInfoActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView itemMyDoll_image_imgv;
        CountdownView itemMyDoll_maturity_cdv;
        TextView itemMyDoll_name_tv;
        TextView itemMyDoll_time_tv;
        TextView itemMyDoll_convertible_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            itemMyDoll_image_imgv = itemView.findViewById(R.id.itemMyDoll_image_imgv);
            itemMyDoll_maturity_cdv = itemView.findViewById(R.id.itemMyDoll_maturity_cdv);
            itemMyDoll_name_tv = itemView.findViewById(R.id.itemMyDoll_name_tv);
            itemMyDoll_time_tv = itemView.findViewById(R.id.itemMyDoll_time_tv);
            itemMyDoll_convertible_tv = itemView.findViewById(R.id.itemMyDoll_convertible_tv);
        }
    }

}
