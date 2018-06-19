package com.txd.hzj.wjlp.distribution.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.distribution.shopFgt.ShopOrderFragment;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;

import java.util.List;

/**
 * 创建者：Qyl
 * 创建时间：2018/6/15 0015 9:22
 * 功能描述：分销订单管理adapter
 * 联系方式：无
 */
public class ShopOrderManageAdapter extends RecyclerView.Adapter {
    private List list;
    private Context context;
    private ChangeTextViewStyle instance;

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
        instance = ChangeTextViewStyle.getInstance();
        MyViewHolder holders = (MyViewHolder) holder;

        instance.forTextColor(context, holders.buyer, "买家：你的长安城", 3, Color.parseColor("#777777"));
        instance.forTextColor(context, holders.supplier, "供货商：123456", 4, Color.parseColor("#777777"));
        instance.forTextColor(context, holders.priFit, "收益：156.00积分", 3, Color.parseColor("#777777"));
        instance.forTextColor(context, holders.vouvherType, "用卷类型：红券", 5, Color.parseColor("#777777"));
        instance.forTextColor(context, holders.proportion, "用卷比例：50%", 5,Color.parseColor("#777777"));

    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView buyer;
        private final TextView supplier;
        private final TextView priFit;
        private final TextView vouvherType;
        private final TextView proportion;

        public MyViewHolder(View itemView) {
            super(itemView);
            //买家
            buyer = itemView.findViewById(R.id.shop_order_item_buyer);
            //供货商
            supplier = itemView.findViewById(R.id.shop_order_item_supplier);
            //收益
            priFit = itemView.findViewById(R.id.shop_order_item_profit);
            //用卷类型
            vouvherType = itemView.findViewById(R.id.shop_order_voucher_type);
            //用卷比例
            proportion = itemView.findViewById(R.id.shop_order_item_voucher_proportion);
        }
    }
}
