package com.txd.hzj.wjlp.distribution.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.distribution.bean.ExhibitGoodsBean;
import com.txd.hzj.wjlp.mellonLine.gridClassify.TicketGoodsDetialsAty;

import java.util.List;


/**
 * 创建者：zhangyunfei
 * 创建时间：2018/6/13 16:47
 * 功能描述：小店上货适配器
 * 联系方式：
 */
public class ShopExhibitAdapter extends RecyclerView.Adapter<ShopExhibitAdapter.ViewHolder> {
    private List<ExhibitGoodsBean.DataBean.ListBean> datas;
    private Context mContext;
    private OnButtonClickListener mButtonClickListener;

    public void setButtonClickListener(OnButtonClickListener buttonClickListener) {
        mButtonClickListener = buttonClickListener;
    }

    public interface  OnButtonClickListener{
        void buttonClick(int position);
    }
    public ShopExhibitAdapter(List<ExhibitGoodsBean.DataBean.ListBean> datas) {
        this.datas = datas;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        ViewHolder viewHolder=new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.exhibit_fgt_item,parent,false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ExhibitGoodsBean.DataBean.ListBean listBean = datas.get(position);
                Glide.with(mContext)
                .load(listBean.getGoods_img())
                .into(holder.img_product);
        holder.tv_title.setText(listBean.getGoods_name());
        holder.tv_daijinquan.setText("最多可用"+listBean.getDiscount()+"%代金券");
        holder.tv_price.setText("¥"+listBean.getShop_price());

        holder.tv_jifen.setText(Html.fromHtml("<font color=\"#FD8315\">" + listBean.getIntegral() + "</font>积分"));
        holder.sell_num_tv.setText("销量："+listBean.getSell_num()+"件");
        holder.kucun_tv.setText("库存："+listBean.getAll_goods_num()+"件");
        holder.img_shangjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mButtonClickListener!=null){
                    mButtonClickListener.buttonClick(holder.getLayoutPosition());
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("ticket_buy_id", listBean.getGoods_id());
                bundle.putInt("from", 1);
                Intent intent=new Intent();
                intent.setClass(mContext,TicketGoodsDetialsAty.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    public void notifyData(int position){
        if (datas!=null && datas.size()>0){
            datas.remove(position);
            notifyItemRemoved(position);
            if(position != datas.size()){ // 如果移除的是最后一个，忽略
                notifyItemRangeChanged(position, datas.size() - position);
            }
        }
    }


    @Override
    public int getItemCount() {
        return datas.size()>0?datas.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_product;
        private TextView tv_title;
        private TextView tv_daijinquan;
        private TextView tv_price;
        private TextView tv_jifen;
        private TextView sell_num_tv;
        private TextView kucun_tv;
        private ImageView img_shangjia;
        public ViewHolder(View itemView) {
            super(itemView);
            img_product=itemView.findViewById(R.id.product_igv);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_daijinquan=itemView.findViewById(R.id.tv_daijinquan);
            tv_price=itemView.findViewById(R.id.tv_price);
            tv_jifen=itemView.findViewById(R.id.tv_jifen);
            sell_num_tv=itemView.findViewById(R.id.sell_num_tv);
            kucun_tv=itemView.findViewById(R.id.kucun_tv);
            img_shangjia=itemView.findViewById(R.id.img_shangjia);
        }
    }
}
