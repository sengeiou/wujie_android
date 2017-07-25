package com.txd.hzj.wjlp.mellOnLine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.mainFgt.adapter.HorizontalAdapter;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/25 0025
 * 时间：17:03
 * 描述：商家详情商品列表
 * ===============Txunda===============
 */

public class MellGoodsAdapter extends RecyclerView.Adapter<MellGoodsAdapter.MellGoodsViewHolder> {

    private Context context;

    /**
     * 0.商家详情
     * 1.线下商城流转商品
     */
    private int from = 0;

    private LayoutInflater inflater;

    public MellGoodsAdapter(Context context, int from) {
        this.context = context;
        this.from = from;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MellGoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_mell_goods_rv, parent, false);
        MellGoodsViewHolder mellGoodsViewHolder = new MellGoodsViewHolder(view);
        ViewUtils.inject(mellGoodsViewHolder, view);
        return mellGoodsViewHolder;
    }

    @Override
    public void onBindViewHolder(final MellGoodsViewHolder holder, int position) {
        if (0 == from) {
            holder.bottom_layout_for_goods.setVisibility(View.GONE);
        } else {
            holder.bottom_layout_for_goods.setVisibility(View.VISIBLE);
        }
        if (itemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();
                    itemClickLitener.onItemClick(holder.itemView, pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 8;
    }


    class MellGoodsViewHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.bottom_layout_for_goods)
        private LinearLayout bottom_layout_for_goods;

        public MellGoodsViewHolder(View itemView) {
            super(itemView);
        }
    }

    private HorizontalAdapter.OnItemClickLitener itemClickLitener;

    public void setListener(HorizontalAdapter.OnItemClickLitener itemClickLitener) {
        this.itemClickLitener = itemClickLitener;
    }

}
