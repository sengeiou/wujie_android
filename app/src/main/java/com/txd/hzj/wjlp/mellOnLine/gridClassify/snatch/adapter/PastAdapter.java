package com.txd.hzj.wjlp.mellOnLine.gridClassify.snatch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;

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

    public PastAdapter(Context context) {
        this.context = context;
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
        if (position < 9) {
            holder.rv_under_line.setVisibility(View.VISIBLE);
        } else {
            holder.rv_under_line.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class PathViewHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.rv_under_line)
        private View rv_under_line;

        public PathViewHolder(View itemView) {
            super(itemView);
        }
    }

}
