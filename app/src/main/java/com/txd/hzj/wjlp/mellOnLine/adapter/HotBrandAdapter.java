package com.txd.hzj.wjlp.mellOnLine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.ViewUtils;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.listener.ItemClickForRecyclerView;

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/6 0006
 * 时间：11:01
 * 描述：三级分类热门品牌适配器
 * ===============Txunda===============
 */

public class HotBrandAdapter extends RecyclerView.Adapter<HotBrandAdapter.HotAdapter> {

    private Context context;
    private List<String> list;

    private LayoutInflater inflater;

    private ItemClickForRecyclerView itemClickForRecyclerView;

    public HotBrandAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public HotAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_hot_brand, parent, false);
        HotAdapter hotAdapter = new HotAdapter(view);
        ViewUtils.inject(hotAdapter, view);
        return hotAdapter;
    }

    @Override
    public void onBindViewHolder(final HotAdapter holder, int position) {
        if (itemClickForRecyclerView != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();
                    itemClickForRecyclerView.OnItemClick(holder.itemView, pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    class HotAdapter extends RecyclerView.ViewHolder {

        public HotAdapter(View itemView) {
            super(itemView);
        }
    }

    public void setItemClickForRecyclerView(ItemClickForRecyclerView itemClickForRecyclerView) {
        this.itemClickForRecyclerView = itemClickForRecyclerView;
    }
}
