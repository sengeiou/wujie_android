package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.commodity.GoodsServerBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/30 15:56
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class Service_adp extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<GoodsServerBean> serverList;
    int tpye = 0;
    Context context;

    public Service_adp(List<GoodsServerBean> list, int type, Context context) {
        this.serverList = (ArrayList<GoodsServerBean>) list;
        this.tpye = type;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lh_service, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewholder, int position) {
        String name = "";
        String desc = "";
        ViewHolder holder = (ViewHolder) viewholder;
        if (this.tpye == 0) {
            name = serverList.get(position).getServer_name() + "：";
            desc = serverList.get(position).getDesc();
            SpannableString msp = new SpannableString(name + desc);
            msp.setSpan(new ForegroundColorSpan(Color.parseColor("#F23030")), 0, name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置色
            holder.tv_text.setText(msp);
        } else if (this.tpye == 1) {
            name = serverList.get(position).getServer_name() + "：";
            desc = serverList.get(position).getDesc();
            SpannableString msp = new SpannableString(name + desc);
            msp.setSpan(new ForegroundColorSpan(Color.parseColor("#F23030")), 0, name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置色
            holder.tv_text.setText(msp);
        } else {
            name = serverList.get(position).getServer_name();
            holder.tv_text.setTextColor(Color.parseColor("#F23030"));
        }
        Glide.with(context).load(serverList.get(position).getIcon()).into(holder.im_logo);
    }

    @Override
    public int getItemCount() {
        return serverList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView im_logo;
        TextView tv_text;

        public ViewHolder(View itemView) {
            super(itemView);
            im_logo = (ImageView) itemView.findViewById(R.id.im_logo);
            tv_text = (TextView) itemView.findViewById(R.id.tv_text);
        }
    }
}
