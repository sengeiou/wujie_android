package com.txd.hzj.wjlp.distribution.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.distribution.shopFgt.ShopOrderFragment;

import java.util.List;

/**
 * 创建者：Qyl
 * 创建时间：2018/6/15 0015 9:22
 * 功能描述：
 * 联系方式：无
 */
public class ShopOrderManageAdapter extends RecyclerView.Adapter {
    private List list;
    private Context context;

    public ShopOrderManageAdapter(List list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.shop_order_relist_item, null));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
