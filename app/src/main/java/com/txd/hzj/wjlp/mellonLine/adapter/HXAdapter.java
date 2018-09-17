package com.txd.hzj.wjlp.mellonLine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/9/26 0026
 * 时间：14:22
 * 描述：
 *
 */

public class HXAdapter extends BaseAdapter {
    private List<Map<String, String>> list;

    private Context context;

    private int headSize = 0;

    public HXAdapter(Context context, List<Map<String, String>> list) {
        this.list = list;
        this.context = context;
        headSize = ToolKit.dip2px(context, 40);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Map<String, String> getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        MessageViewHolder mvh;
        Map<String, String> info = getItem(i);
        if (null == view) {
            view = LayoutInflater.from(context).inflate(R.layout.aty_message_tips, viewGroup, false);
            mvh = new MessageViewHolder();
            ViewUtils.inject(mvh, view);
            view.setTag(mvh);
        } else {
            mvh = (MessageViewHolder) view.getTag();
        }

        if (info.get("msg_count").equals("0")) {
            mvh.new_message_num_tv.setVisibility(View.GONE);
        } else {
            mvh.new_message_num_tv.setVisibility(View.VISIBLE);
            mvh.new_message_num_tv.setText(info.get("msg_count"));
        }
        Glide.with(context).load(info.get("head_pic"))
                .override(headSize, headSize)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default)
                .into(mvh.message_type_icon_iv);

        // 对方昵称
        mvh.message_type_tv.setText(info.get("nickname"));
        // 最后一条消息
        mvh.message_content_tv.setText(info.get("last_content"));
        // 时间
        mvh.order_message_time_tv.setText(info.get("last_time"));
        return view;
    }

    private class MessageViewHolder {

        /**
         * 头像
         */
        @ViewInject(R.id.message_type_icon_iv)
        private ShapedImageView message_type_icon_iv;
        /**
         * 消息来源
         */
        @ViewInject(R.id.message_type_tv)
        private TextView message_type_tv;
        /**
         * 消息时间
         */
        @ViewInject(R.id.order_message_time_tv)
        private TextView order_message_time_tv;
        /**
         * 消息内容
         */
        @ViewInject(R.id.message_content_tv)
        private TextView message_content_tv;
        /**
         * 消息数量
         */
        @ViewInject(R.id.new_message_num_tv)
        private TextView new_message_num_tv;

    }
}
