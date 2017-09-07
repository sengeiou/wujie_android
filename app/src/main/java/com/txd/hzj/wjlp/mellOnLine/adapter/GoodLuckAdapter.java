package com.txd.hzj.wjlp.mellOnLine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ants.theantsgo.listenerForAdapter.AdapterTextViewClickListener;
import com.ants.theantsgo.tool.ToolKit;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.groupbuy.GroupBean;

import java.util.Calendar;
import java.util.List;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;
import cn.iwgang.countdownview.CountdownView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/10 0010
 * 时间：11:22
 * 描述：拼团列表适配器
 * ===============Txunda===============
 */

public class GoodLuckAdapter extends BaseAdapter {

    private Context context;
    private List<GroupBean> list;
    private LayoutInflater inflater;
    private GoodLuckVH goodLuckVH;

    /**
     * 团长头像大小
     */
    private int size = 0;

    private AdapterTextViewClickListener adapterTextViewClickListener;

    public void setAdapterTextViewClickListener(AdapterTextViewClickListener adapterTextViewClickListener) {
        this.adapterTextViewClickListener = adapterTextViewClickListener;
    }

    public GoodLuckAdapter(Context context, List<GroupBean> list) {
        this.context = context;
        this.list = list;
        size = ToolKit.dip2px(context, 40);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public GroupBean getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        GroupBean groupBean = getItem(i);
        if (null == view) {
            view = inflater.inflate(R.layout.item_good_luck_lv, viewGroup, false);
            goodLuckVH = new GoodLuckVH();
            ViewUtils.inject(goodLuckVH, view);
            view.setTag(goodLuckVH);
        } else {
            goodLuckVH = (GoodLuckVH) view.getTag();
        }
        goodLuckVH.join_in_group_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapterTextViewClickListener != null) {
                    adapterTextViewClickListener.onTextViewClick(view, i);
                }
            }
        });

        // 当前时间
        long now_time = Calendar.getInstance().getTimeInMillis() / 1000;
        // 剩余时间
        long last_time = Long.parseLong(groupBean.getStart_time()) - now_time;
        // 倒计时Tag
        goodLuckVH.good_count_down_view.setTag("GoodLuck" + i);
        // 开始倒计时
        goodLuckVH.good_count_down_view.start(last_time);

        Glide.with(context).load(groupBean.getHead_user().getHead_pic())
                .override(size, size)
                .placeholder(R.drawable.ic_default)
                .centerCrop()
                .error(R.drawable.ic_default)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(goodLuckVH.group_head_pic_iv);
        // 昵称
        goodLuckVH.group_name_tv.setText(groupBean.getHead_user().getNickname());
        //还差。。。人
        goodLuckVH.group_diff_tv.setText(groupBean.getDiff());

        return view;
    }

    private class GoodLuckVH {
        /**
         * 团长头像
         */
        @ViewInject(R.id.group_head_pic_iv)
        private ShapedImageView group_head_pic_iv;

        /**
         * 团长昵称
         */
        @ViewInject(R.id.group_name_tv)
        private TextView group_name_tv;

        @ViewInject(R.id.join_in_group_tv)
        private TextView join_in_group_tv;
        /**
         * 还差多少人
         */
        @ViewInject(R.id.group_diff_tv)
        private TextView group_diff_tv;
        /**
         * 倒计时
         */
        @ViewInject(R.id.good_count_down_view)
        private CountdownView good_count_down_view;

    }
}
