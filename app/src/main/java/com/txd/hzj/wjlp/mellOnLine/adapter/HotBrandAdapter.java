package com.txd.hzj.wjlp.mellOnLine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.tool.ToolKit;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.CateIndex;
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
    private List<CateIndex.Data.TwoCateBean.HostBrandBean> list;

    private LayoutInflater inflater;

    private ItemClickForRecyclerView itemClickForRecyclerView;

    private int size = 0;

    public HotBrandAdapter(Context context, List<CateIndex.Data.TwoCateBean.HostBrandBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
        size = ToolKit.dip2px(context, 70);
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
        CateIndex.Data.TwoCateBean.HostBrandBean hostBrandBean = list.get(position);
        Glide.with(context).load(hostBrandBean.getBrand_logo())
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default)
                .centerCrop()
                .override(size, size)
                .into(holder.host_cate_iv);
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
        return list.size();
    }

    class HotAdapter extends RecyclerView.ViewHolder {

        @ViewInject(R.id.host_cate_iv)
        private ImageView host_cate_iv;

        HotAdapter(View itemView) {
            super(itemView);
        }
    }

    public void setItemClickForRecyclerView(ItemClickForRecyclerView itemClickForRecyclerView) {
        this.itemClickForRecyclerView = itemClickForRecyclerView;
    }
}
