package com.txd.hzj.wjlp.distribution.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.distribution.bean.ExhibitGoodsBean;

import java.util.List;


/**
 * 创建者：zhangyunfei
 * 创建时间：2018/6/13 16:47
 * 功能描述：小店上货适配器
 * 联系方式：
 */
public class ShopExhibitAdapter extends RecyclerView.Adapter<ShopExhibitAdapter.ViewHolder> {
    private List<ExhibitGoodsBean> datas;
    private Context mContext;

    public ShopExhibitAdapter(List<ExhibitGoodsBean> datas) {
        this.datas = datas;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        ViewHolder viewHolder=new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.exhibit_fgt_item,null));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ExhibitGoodsBean exhibitGoodsBean = datas.get(position);
//        Glide.with(mContext)
//                .load(exhibitGoodsBean.getImageUrl())
//                .into(holder.img_product);
//        holder.tv_title.setText(exhibitGoodsBean.getGoodsTitle());
//        holder.tv_daijinquan.setText(exhibitGoodsBean.getDaijinquan());
//        holder.tv_price.setText("¥"+ exhibitGoodsBean.getPrice());
//        holder.tv_jifen.setText(Html.fromHtml("<font color=\"#fffd8214\">" + exhibitGoodsBean.getJifen() + "</font>积分"));
        holder.img_shangjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datas.remove(position);
                notifyDataSetChanged();
            }
        });
    }


    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_product;
        private TextView tv_title;
        private TextView tv_daijinquan;
        private TextView tv_price;
        private TextView tv_jifen;
        private ImageView img_shangjia;
        public ViewHolder(View itemView) {
            super(itemView);
            img_product=itemView.findViewById(R.id.product_igv);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_daijinquan=itemView.findViewById(R.id.tv_daijinquan);
            tv_price=itemView.findViewById(R.id.tv_price);
            tv_jifen=itemView.findViewById(R.id.tv_jifen);
            img_shangjia=itemView.findViewById(R.id.img_shangjia);
        }
    }
}
