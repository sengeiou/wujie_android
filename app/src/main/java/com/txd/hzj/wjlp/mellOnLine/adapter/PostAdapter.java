package com.txd.hzj.wjlp.mellOnLine.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;

import java.util.List;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/8 0008
 * 时间：14:20
 * 描述：驿站适配器
 */

public class PostAdapter extends BaseAdapter {

    private Context context;
    private List<String> data;

    private LayoutInflater inflater;

    private PostVh pvh;

    public PostAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int i) {
        return data;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (null == view) {
            view = inflater.inflate(R.layout.item_wujie_post_lv, viewGroup, false);
            pvh = new PostVh();
            ViewUtils.inject(pvh, view);
            view.setTag(pvh);
        } else {
            pvh = (PostVh) view.getTag();
        }

        ChangeTextViewStyle.getInstance().forTextColor(context, pvh.goods_inventory_tv, "库存15", 2,
                ContextCompat.getColor(context, R.color.theme_color));
        ChangeTextViewStyle.getInstance().forTextColor(context, pvh.goods_distance_tv, "距您15km", 2,
                ContextCompat.getColor(context, R.color.theme_color));

        return view;
    }

    private class PostVh {

        /**
         * 库存
         */
        @ViewInject(R.id.goods_inventory_tv)
        private TextView goods_inventory_tv;
        /**
         * 距离
         */
        @ViewInject(R.id.goods_distance_tv)
        private TextView goods_distance_tv;

    }

}
