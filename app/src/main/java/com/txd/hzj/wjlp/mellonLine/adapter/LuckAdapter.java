package com.txd.hzj.wjlp.mellonLine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.commodity.GroupRankBean;

import java.util.List;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/12/25 9:51
 * 功能描述：
 */
public class LuckAdapter extends BaseAdapter {
    private List<GroupRankBean.RankBean> mList;
    private Context mContext;

    public LuckAdapter(List<GroupRankBean.RankBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size()>0?mList.size():0;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.luck_item,null);
            holder = new ViewHolder();
            ViewUtils.inject(holder, convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        GroupRankBean.RankBean rankBean = mList.get(position);
        if (position == 0){
            holder.numTv.setBackgroundResource(R.drawable.icon_one);
            holder.numTv.setText("");
        }else if (position == 1){
            holder.numTv.setBackgroundResource(R.drawable.icon_two);
            holder.numTv.setText("");
        }else if (position == 2){
            holder.numTv.setBackgroundResource(R.drawable.icon_three);
            holder.numTv.setText("");
        }else {
            holder.numTv.setBackground(null);
            holder.numTv.setText(rankBean.getRank());
        }
        holder.nameTv.setText(rankBean.getUser_name());
        holder.priceTv.setText(rankBean.getUser_count());
        Glide.with(mContext).load(rankBean.getHead_pic()).into(holder.headImg);

        return convertView;
    }

    class ViewHolder{
        @ViewInject(R.id.numTv)
        private TextView numTv;
        @ViewInject(R.id.nameTv)
        private TextView nameTv;
        @ViewInject(R.id.priceTv)
        private TextView priceTv;
        @ViewInject(R.id.headImg)
        private ShapedImageView headImg;
    }
}
