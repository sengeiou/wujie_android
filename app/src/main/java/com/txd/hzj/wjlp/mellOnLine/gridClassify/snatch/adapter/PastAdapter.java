package com.txd.hzj.wjlp.mellOnLine.gridClassify.snatch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;

import java.util.List;
import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/25 0025
 * 时间：15:36
 * 描述：
 * ===============Txunda===============
 */

public class PastAdapter extends RecyclerView.Adapter<PastAdapter.PathViewHolder> {

    private Context context;

    private List<Map<String, String>> data;
    private int size = 0;

    public PastAdapter(Context context, List<Map<String, String>> data) {
        this.context = context;
        this.data = data;
        size = ToolKit.dip2px(context, 60);
    }

    @Override
    public PathViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_path_rv, null);
        PathViewHolder viewHolder = new PathViewHolder(view);
        ViewUtils.inject(viewHolder, view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PathViewHolder holder, int position) {
        Map<String, String> map = data.get(position);
        if (position < data.size() - 1) {
            holder.rv_under_line.setVisibility(View.VISIBLE);
        } else {
            holder.rv_under_line.setVisibility(View.GONE);
        }

        Glide.with(context).load(map.get("head_pic")).centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.drawable.ic_default)
                .placeholder(R.drawable.ic_default)
                .override(size,size)
                .into(holder.out_time_head_iv);

        holder.winer_code_tv.setText("获  得  者：" + map.get("winer_code"));
        // 期        号：333333000345\n揭晓时间：2017-06-15 15:27:00\n幸运号码：10000000001\n本期参与：20人次
        holder.other_info_tv.setText("期        号：" + map.get("time_num") + "\n揭晓时间：" + map.get("end_true_time")
                + "\n幸运号码：" + map.get("winer_code") + "\n本期参与：" + map.get("count"));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class PathViewHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.rv_under_line)
        private View rv_under_line;

        /**
         * 头像
         */
        @ViewInject(R.id.out_time_head_iv)
        private ShapedImageView out_time_head_iv;
        /**
         * 获得者
         */
        @ViewInject(R.id.winer_code_tv)
        private TextView winer_code_tv;

        @ViewInject(R.id.other_info_tv)
        private TextView other_info_tv;

        public PathViewHolder(View itemView) {
            super(itemView);
        }
    }

}
