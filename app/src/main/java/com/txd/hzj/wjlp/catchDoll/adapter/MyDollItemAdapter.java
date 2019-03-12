package com.txd.hzj.wjlp.catchDoll.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
    int type; // 1 寄存 2 待邮寄 3已发货  4已兑换

    public MyDollItemAdapter(List<MyDollBean> list, int type, Context context) {
        this.list = list;
        this.context = context;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_doll_show, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final MyDollBean myDollBean = list.get(position);
        GlideUtils.loadUrlImg(context, myDollBean.getGoods_img(), holder.itemMyDoll_image_imgv);
        if (!TextUtils.isEmpty(myDollBean.getMaturityTime())){
            holder.itemMyDoll_maturity_cdv.start(Long.parseLong(myDollBean.getMaturityTime()));
            holder.itemMyDoll_time_tv.setText(Util.millis2String(Long.parseLong(myDollBean.getMaturityTime()), "yyyy.MM.dd HH:mm:ss"));
        }
        holder.itemMyDoll_name_tv.setText(myDollBean.getName());
        holder.itemMyDoll_num_tv.setText(myDollBean.getGraspingNum()+"/"+myDollBean.getCatcherNum());
        holder.itemMyDoll_convertible_tv.setText(new StringBuffer().append("可兑换银两").append(myDollBean.getExchange_price()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DollGoodsInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("MyDollBean", myDollBean);
                intent.putExtras(bundle);
                context.startActivity(intent);
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
        TextView itemMyDoll_num_tv;
        TextView itemMyDoll_time_tv;
        TextView itemMyDoll_convertible_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            itemMyDoll_image_imgv = itemView.findViewById(R.id.itemMyDoll_image_imgv);
            itemMyDoll_maturity_cdv = itemView.findViewById(R.id.itemMyDoll_maturity_cdv);
            itemMyDoll_name_tv = itemView.findViewById(R.id.itemMyDoll_name_tv);
            itemMyDoll_num_tv = itemView.findViewById(R.id.itemMyDoll_num_tv);
            itemMyDoll_time_tv = itemView.findViewById(R.id.itemMyDoll_time_tv);
            itemMyDoll_convertible_tv = itemView.findViewById(R.id.itemMyDoll_convertible_tv);
        }
    }

}
