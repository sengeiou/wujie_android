package com.txd.hzj.wjlp.mainfgt.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;

import java.util.List;
import java.util.Map;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/4 0004
 * 时间：14:06
 * 描述：横向的RecyclerView的适配器
 */

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {

    private List<Map<String, String>> list;
    private LayoutInflater mInflater;
    private Context context;

    public HorizontalAdapter(List<Map<String, String>> list, Context context) {
        this.list = list;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_on_line_rv, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        ViewUtils.inject(holder, view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.tv_classify_name_tv.setText(list.get(position).get("short_name"));
        holder.rv_classify_under_line.setBackgroundColor(Color.WHITE);
        if (position == selected) {
            holder.tv_classify_name_tv.setTextColor(ContextCompat.getColor(context, R.color.theme_color));
            holder.rv_classify_under_line.setBackgroundColor(ContextCompat.getColor(context, R.color.theme_color));
        } else {
            holder.tv_classify_name_tv.setTextColor(ContextCompat.getColor(context, R.color.hint_text_color));
            holder.rv_classify_under_line.setBackgroundColor(Color.WHITE);
        }
        // 如果设置了回调，则设置点击事件
        if (itemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    itemClickLitener.onItemClick(holder.itemView, pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.tv_classify_name_tv)
        TextView tv_classify_name_tv;
        @ViewInject(R.id.rv_classify_under_line)
        View rv_classify_under_line;

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }

    private int selected = 0;

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    /**
     * 点击事件接口
     *
     *         2017年3月22日下午5:05:26
     */
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    private OnItemClickLitener itemClickLitener;

    public void setListener(OnItemClickLitener itemClickLitener) {
        this.itemClickLitener = itemClickLitener;
    }
}
