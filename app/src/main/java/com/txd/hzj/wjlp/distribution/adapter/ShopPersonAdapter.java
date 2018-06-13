package com.txd.hzj.wjlp.distribution.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.txd.hzj.wjlp.R;

import java.util.List;

/**
 * 创建者：Qyl
 * 创建时间：2018/6/13 0013 15:03
 * 功能描述：顾客管理adapter
 * 联系方式：无
 */
public class ShopPersonAdapter extends RecyclerView.Adapter {
    private List list;
    private Context context;

    public ShopPersonAdapter(List list,Context context) {
        this.list = list;
        this.context = context;
    }


    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.shop_person_relist_item, parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
