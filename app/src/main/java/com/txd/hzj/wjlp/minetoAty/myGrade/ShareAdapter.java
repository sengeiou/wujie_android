package com.txd.hzj.wjlp.minetoAty.myGrade;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.txd.hzj.wjlp.R;

import java.util.ArrayList;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * by Txunda_LH on 2018/2/26.
 */

public class ShareAdapter extends BaseAdapter {
    Context context;
    ArrayList<ShareBean> list;
    public  ShareAdapter(Context context,ArrayList<ShareBean> alist){
        this.context=context;
        this.list= alist;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView == null){
            vh = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_share_grade_lv,null);
            vh.rank_item_haed_iv = convertView.findViewById(R.id.rank_item_haed_iv);
            vh.top_three_iv = convertView.findViewById(R.id.top_three_iv);
            vh.top_from_four_to_five_iv = convertView.findViewById(R.id.top_from_four_to_five_iv);
            vh.rank_nickname_Tv = convertView.findViewById(R.id.rank_nickname_Tv);
            vh.rank_num_tv = convertView.findViewById(R.id.rank_num_tv);
            convertView.setTag(vh);

        }else {
            vh = (ViewHolder) convertView.getTag();
        }

        Glide.with(context).load(list.get(position).getHead_pic())
                .override(ToolKit.dip2px(context, 80), ToolKit.dip2px(context, 80))
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(vh.rank_item_haed_iv);

        vh.rank_nickname_Tv.setText(list.get(position).getNickname());

        vh.rank_num_tv.setText(list.get(position).getNum());

        return convertView;
    }

    class ViewHolder{
         ShapedImageView rank_item_haed_iv;
        ImageView top_three_iv,top_from_four_to_five_iv;
        TextView  rank_nickname_Tv,rank_num_tv;

    }
}
