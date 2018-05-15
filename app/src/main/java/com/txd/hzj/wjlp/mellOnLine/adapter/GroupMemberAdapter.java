package com.txd.hzj.wjlp.mellOnLine.adapter;

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
import com.txd.hzj.wjlp.bean.commodity.HeadPicBean;
import com.txd.hzj.wjlp.bean.groupbuy.GroupPager;

import java.util.List;
import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/10 0010
 * 时间：14:17
 * 描述：团员适配器
 * ===============Txunda===============
 */

public class GroupMemberAdapter extends RecyclerView.Adapter<GroupMemberAdapter.GMViewHolder> {

    private Context context;
    private  List<HeadPicBean> list;
    private LayoutInflater inflater;

    private int size = 0;

    public GroupMemberAdapter(Context context,  List<HeadPicBean> list) {
        this.context = context;
        this.list = list;
        size = ToolKit.dip2px(context, 60);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public GMViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_group_member_rv, parent, false);
        GMViewHolder gmViewHolder = new GMViewHolder(view);
        ViewUtils.inject(gmViewHolder, view);
        return gmViewHolder;
    }

    @Override
    public void onBindViewHolder(GMViewHolder holder, int position) {
        try {
            HeadPicBean map = list.get(position);
            if (map.getType().equals("1")) {
                holder.regimental_commander_tv.setVisibility(View.VISIBLE);
            } else {
                holder.regimental_commander_tv.setVisibility(View.GONE);
            }
            Glide.with(context).load(map.getPic())
                    .override(size, size)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default)
                    .into(holder.group_member_iv);
        } catch (Exception e) {
            holder.regimental_commander_tv.setVisibility(View.GONE);
            Glide.with(context).load(R.drawable.ic_default)
                    .override(size, size)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default)
                    .into(holder.group_member_iv);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    class GMViewHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.regimental_commander_tv)
        private TextView regimental_commander_tv;
        /**
         * 头像
         */
        @ViewInject(R.id.group_member_iv)
        private ShapedImageView group_member_iv;

        public GMViewHolder(View itemView) {
            super(itemView);
        }
    }

}
